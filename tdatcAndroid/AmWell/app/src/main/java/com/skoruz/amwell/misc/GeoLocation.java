package com.skoruz.amwell.misc;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.skoruz.amwell.patientEntity.GeoCode;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.PlayServicesUtils;
import com.skoruz.amwell.utils.Utils;
import android.os.Process;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by keerthi on 23/12/15.
 */
public class GeoLocation {

    String address = null;
    int myLatitude, myLongitude;
    private final int PLAY_SERVICE_RETRY = 3;
    private final String TAG = "Geo Location";
    private final int TIMEOUT_SECONDS = 15;
    private final int TIMESTAMP_TIMEOUT = 1800000;
    private final String URL_GOOGLE_MMAP = "http://www.google.com/glm/mmap";
    private Context context;
    private HashMap<String, String> localCityNameMap = new HashMap();
    private Editor mEditor;
    private String mGeoLatitude;
    private String mGeoLongitude;
    private GoogleApiClient mGoogleApiClient;
    private SharedPreferences mSharedPreferences;
    private Location mTempLocation = null;
    private long mTimeStamp;
    private int retryCount = 0;

    public interface OnLocationUpdateListener {
        void onLocationUpdated(LocationStatus locationStatus);
    }

    public static class LocationStatus {
        public Bundle bundle = null;
        public boolean isNewCityFetched = false;
        public boolean isNewLocationFetched = false;
        public String[] location = null;
    }

    public GeoLocation(Context context) {
        this.context = context;
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mEditor = this.mSharedPreferences.edit();
        initCityNameMapping();
    }

    private void initCityNameMapping() {
        this.localCityNameMap.put("Bengaluru", "Bangalore");
        this.localCityNameMap.put("New Delhi", "Delhi");
    }

    public LocationStatus updateLocationSynchronous(boolean forceNow, boolean liveLocation) {
        boolean isLimitedCity = true;
        LocationStatus status = new LocationStatus();
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            throw new IllegalStateException("Should be invoked from background thread");
        }
        try {
            if (Utils.checkLocationPermission(this.context)) {
                if (forceNow) {
                    this.mEditor.putInt("location_priority", 1);
                    this.mEditor.commit();
                }
                if (this.mSharedPreferences.getInt("location_priority", 1) <= 1 && getCheckedLocation(forceNow, liveLocation)) {
                    String[] locationDetail = resolveLocation(this.mGeoLatitude, this.mGeoLongitude);
                    if (locationDetail == null || locationDetail.length < 2) {
                        this.mEditor.putBoolean("my_location_available", false);
                        this.mEditor.commit();
                    } else {
                        String cityName = locationDetail[0];
                        String selectedCityName = this.mSharedPreferences.getString("selected_city_name", null);
                        status.location = locationDetail;
                        status.isNewLocationFetched = true;
                        if (selectedCityName.equals(cityName)) {
                            saveResolvedLocation(forceNow, isCityAvailable(cityName), cityName, locationDetail[1]);
                        } else {
                            Cursor cursor = getCityCursor(locationDetail[0]);
                            Bundle bundle = null;
                            if (cursor != null && cursor.getCount() > 0) {
                                //bundle = setDefaultCity(cursor);
                                if (bundle != null) {
                                    status.bundle = bundle;
                                }
                            }
                            if (bundle == null) {
                                isLimitedCity = false;
                            }
                            status.isNewCityFetched = true;
                            saveResolvedLocation(forceNow, isLimitedCity, cityName, locationDetail[1]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public void updateLocationAsynchronous(final boolean forceNow, final boolean liveLocation, final OnLocationUpdateListener listener) {
        new Thread(new Runnable() {
            public void run() {
                Process.setThreadPriority(10);
                LocationStatus status = GeoLocation.this.updateLocationSynchronous(forceNow, liveLocation);
                if (listener != null) {
                    listener.onLocationUpdated(status);
                }
            }
        }).start();
    }

    private void saveResolvedLocation(boolean forceNow, boolean isLimitedCity, String cityName, String localityName) throws Exception {
        this.mEditor.putString("selected_city_name", cityName);
        this.mEditor.putString("location_city", cityName);
        this.mEditor.putString("location_locality", localityName);
        if (forceNow || isLimitedCity) {
            this.mEditor.putString("selected_location_name", "Current location");
            this.mEditor.putString("selected_location_type", "geo location");
            this.mEditor.putBoolean("my_location_available", true);
        }
        if (!isLimitedCity) {
            //CityFeatures.setUpFeatureSetting(this.context, cityName);
        }
        this.mEditor.commit();
    }

    private boolean getCheckedLocation(boolean forceNow, boolean liveLocation) throws Exception {
        long timeStamp = this.mSharedPreferences.getLong("location_timestamp", 0);
        if (forceNow || timeStamp == 0 || System.currentTimeMillis() - timeStamp > 1800000) {
            Location location = getAvailableLocation(liveLocation);
            if (location == null) {
                return false;
            }
            Utils.log("Geo Location", "latlng" + location.getLatitude() + " : " + location.getLongitude());
            this.mGeoLatitude = "" + location.getLatitude();
            this.mGeoLongitude = "" + location.getLongitude();
            this.mTimeStamp = System.currentTimeMillis();
            this.mEditor.putString("location_latitude", this.mGeoLatitude);
            this.mEditor.putString("location_longitude", this.mGeoLongitude);
            this.mEditor.putLong("location_timestamp", this.mTimeStamp);
            this.mEditor.commit();
            return true;
        }
        Utils.log("Geo Location", "time stamp alive");
        return false;
    }

    private Location getAvailableLocation(boolean isNeedLiveLoc) throws Exception {
        Location location;
        boolean z = true;
        if (PlayServicesUtils.checkGooglePlaySevices(this.context)) {
            location = getPlayServiceLastLocation(isNeedLiveLoc);
            if (location != null) {
                Utils.log("Geo Location", "play serivce first location returned");
                return location;
            }
        }
        if (PlayServicesUtils.checkGooglePlaySevices(this.context) && isNeedLiveLoc) {
            boolean z2;
            if (isNeedLiveLoc) {
                z2 = false;
            } else {
                z2 = true;
            }
            location = getPlayServiceLastLocation(z2);
            if (location != null) {
                Utils.log("Geo Location", "play serivce second location returned");
                return location;
            }
        }
        location = getTowerLocation();
        if (location != null) {
            Utils.log("Geo Location", "tower location returned");
            return location;
        }
        if (PlayServicesUtils.checkGooglePlaySevices(this.context) && !isNeedLiveLoc) {
            if (isNeedLiveLoc) {
                z = false;
            }
            location = getPlayServiceLastLocation(z);
            if (location != null) {
                Utils.log("Geo Location", "play serivce second location returned");
                return location;
            }
        }
        return location;
    }

    private Location getPlayServiceLastLocation(final boolean isLiveLocation) {
        try {
            final CountDownLatch latch = new CountDownLatch(1);
            this.retryCount = 0;
            this.mTempLocation = null;
            this.mGoogleApiClient = new GoogleApiClient.Builder(this.context).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                public void onConnected(Bundle bundle) {
                    Utils.log("Geo Location", "location connected");
                    if (isLiveLocation) {
                        LocationRequest mLocationRequest = new LocationRequest();
                        mLocationRequest.setInterval(10000);
                        mLocationRequest.setFastestInterval(10000);
                        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        LocationServices.FusedLocationApi.requestLocationUpdates(GeoLocation.this.mGoogleApiClient, mLocationRequest, new LocationListener() {
                            public void onLocationChanged(Location location) {
                                GeoLocation.this.mTempLocation = location;
                                if (GeoLocation.this.mTempLocation != null) {
                                    Utils.log("Geo Location", "play service live location fetched" + GeoLocation.this.mTempLocation.getLatitude() + ":" + GeoLocation.this.mTempLocation.getLongitude());
                                }
                                latch.countDown();
                            }
                        });
                        return;
                    }
                    GeoLocation.this.mTempLocation = LocationServices.FusedLocationApi.getLastLocation(GeoLocation.this.mGoogleApiClient);
                    if (GeoLocation.this.mTempLocation != null) {
                        Utils.log("Geo Location", "play service last location fetched" + GeoLocation.this.mTempLocation.getLatitude() + ":" + GeoLocation.this.mTempLocation.getLongitude());
                    } else {
                        Utils.log("Geo Location", "play service no last know location");
                    }
                    latch.countDown();
                }

                public void onConnectionSuspended(int i) {
                    if (GeoLocation.this.retryCount < 3) {
                        GeoLocation.this.mGoogleApiClient.reconnect();
                        GeoLocation.this.retryCount = GeoLocation.this.retryCount + 1;
                    }
                }
            }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    if (GeoLocation.this.retryCount < 3) {
                        GeoLocation.this.mGoogleApiClient.reconnect();
                        GeoLocation.this.retryCount = GeoLocation.this.retryCount + 1;
                    }
                }
            }).addApi(LocationServices.API).build();
            this.mGoogleApiClient.connect();
            latch.await(15, TimeUnit.SECONDS);
            this.mGoogleApiClient.disconnect();
            this.mGoogleApiClient = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.mTempLocation;
    }

    private boolean isCityAvailable(String city) {
        Cursor cursor = getCityCursor(city);
        if (cursor == null || cursor.getCount() <= 0) {
            return false;
        }
        cursor.close();
        return true;
    }

    private Cursor getCityCursor(String city) {
        Cursor cityCursor = null;
        if (!(city == null || city.isEmpty())) {
            //cityCursor = Cities.getLimitedCityCursor(this.context, city);
            if (cityCursor == null || cityCursor.getCount() == 0) {
                return null;
            }
        }
        return cityCursor;
    }

    private void writeData(OutputStream out, int cid, int lac)
            throws IOException
    {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        dataOutputStream.writeShort(21);
        dataOutputStream.writeLong(0);
        dataOutputStream.writeUTF("en");
        dataOutputStream.writeUTF("Android");
        dataOutputStream.writeUTF("1.0");
        dataOutputStream.writeUTF("Web");
        dataOutputStream.writeByte(27);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(3);
        dataOutputStream.writeUTF("");

        dataOutputStream.writeInt(cid);
        dataOutputStream.writeInt(lac);

        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.flush();
    }

    public class GetLatLng extends AsyncTask<Integer,Boolean,Boolean> {

        @Override
        protected Boolean doInBackground(Integer... params) {
            Boolean result = false;

            String urlmmap = "http://www.google.com/glm/mmap";

            try {
                URL url = new URL(urlmmap);
                URLConnection conn = url.openConnection();
                HttpURLConnection httpConn = (HttpURLConnection) conn;
                httpConn.setRequestMethod("POST");
                httpConn.setDoOutput(true);
                httpConn.setDoInput(true);
                httpConn.connect();

                OutputStream outputStream = httpConn.getOutputStream();
                writeData(outputStream, params[0], params[1]);

                InputStream inputStream = httpConn.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);

                dataInputStream.readShort();
                dataInputStream.readByte();
                int code = dataInputStream.readInt();
                if (code == 0) {
                    myLatitude = dataInputStream.readInt();
                    myLongitude = dataInputStream.readInt();

                    result = true;

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                float lat=(float)myLatitude/1000000;
                float lan=(float)myLongitude/1000000;
                try {
                    resolveLocation(String.valueOf(lat),String.valueOf(lan));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                //textGeo.setText("Can't find Location!");
            }
        }
    }

    private Location getTowerLocation() throws Exception {
        TelephonyManager telephonyManager = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return null;
        }
        int phoneType = telephonyManager.getPhoneType();
        if (phoneType == 2) {
            return getCdmaTowerLocation((CdmaCellLocation) telephonyManager.getCellLocation());
        }
        if (phoneType == 1) {
            return getGsmTowerLocation((GsmCellLocation) telephonyManager.getCellLocation());
        }
        return null;
    }

    private Location getGsmTowerLocation(GsmCellLocation cellLocation) throws Exception {
        if (cellLocation == null) {
            return null;
        }
        int cid = cellLocation.getCid();
        int lac = cellLocation.getLac();
        HttpURLConnection httpConn = null;
        try {
            URL url=new URL("http://www.google.com/glm/mmap");
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.connect();
            writeData(httpConn.getOutputStream(), cid, lac);
            DataInputStream dataInputStream = new DataInputStream(httpConn.getInputStream());
            dataInputStream.readShort();
            dataInputStream.readByte();
            if (dataInputStream.readInt() == 0) {
                double lat = ((double) dataInputStream.readInt()) / 1000000.0d;
                double lon = ((double) dataInputStream.readInt()) / 1000000.0d;
                int i = dataInputStream.readInt();
                int j = dataInputStream.readInt();
                String s = dataInputStream.readUTF();
                dataInputStream.close();
                Location loc = new Location("network");
                loc.setLatitude(lat);
                loc.setLongitude(lon);
                loc.setTime(System.currentTimeMillis());
                if (httpConn == null) {
                    return loc;
                }
                httpConn.disconnect();
                return loc;
            }
            if (httpConn != null) {
                httpConn.disconnect();
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            if (httpConn != null) {
                httpConn.disconnect();
            }
        } catch (Throwable th) {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        return null;
    }

    private Location getCdmaTowerLocation(CdmaCellLocation cdmaCellLocation) throws Exception {
        if (cdmaCellLocation == null) {
            return null;
        }
        Location location = new Location("network");
        location.setLatitude((double) cdmaCellLocation.getBaseStationLatitude());
        location.setLongitude((double) cdmaCellLocation.getBaseStationLongitude());
        return location;
    }

    private String getAddressFromMapApi(String latitude, String longitude) {
        Locale locale = AppController.getInstance().getLocale();

        // Tag used to cancel the request
        String tag_string_req = "string_req";

        StringRequest strReq = new StringRequest(String.format(locale, "http://maps.googleapis.com/maps/api/geocode/json?latlng=%1$s,%2$s&sensor=true&language=" + locale.getCountry(), new Object[]{latitude, longitude}), new Response.Listener<String>() {
            @Override
            public void onResponse(String data) {
                try {
                    Gson gson = new Gson();
                    GeoCode geoCode = gson.fromJson(data, GeoCode.class);
                    if ("OK".equalsIgnoreCase(geoCode.status) && geoCode.results.size() > 0) {
                        GeoCode.Result result = (GeoCode.Result) geoCode.results.get(0);
                        StringBuilder stringBuilder = new StringBuilder();
                        Iterator iterator = result.address_components.iterator();
                        while (iterator.hasNext()) {
                            GeoCode.Result.Component component = (GeoCode.Result.Component) iterator.next();
                            if (component.types.contains("sublocality_level_1")) {
                                stringBuilder.append(component.long_name);
                                stringBuilder.append(", ");
                            }
                            if (component.types.contains("sublocality_level_2")) {
                                stringBuilder.append(component.long_name);
                                stringBuilder.append(", ");
                            }
                            if (Utils.isEmptyString(stringBuilder.toString()) && component.types.contains("sublocality")) {
                                stringBuilder.append(component.long_name);
                            }
                            if (component.types.contains("postal_code")) {
                                stringBuilder.append(component.long_name);
                            }
                        }
                        address = stringBuilder.toString();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(AppController.TAG, "Error: " + volleyError.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        return address;
    }

    private Address getAddress(String latitude, String longitude) throws Exception {
        /*Address address = getAddressFromMapApi(latitude, longitude);
        if (address == null) {
            return getAddressFromGeocoder(latitude, longitude);
        }
        return address;*/
        return getAddressFromGeocoder(latitude, longitude);
    }

    private Address getAddressFromGeocoder(String latitude, String longitude) throws Exception {
        Geocoder geocoder = new Geocoder(this.context);
        List<Address> addresses = null;
        Log.d("Geo Location", "trying geocoder");
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (addresses == null || addresses.size() <= 0) {
            return null;
        }
        return (Address) addresses.get(0);
    }

    private String[] resolveLocation(String latitude, String longitude) throws Exception {
        if (latitude == null || longitude == null || latitude.isEmpty() || longitude.isEmpty()) {
            Log.d("Geo Location", "location cannot be resolved");
            return null;
        }
        Address address = getAddress(latitude, longitude);
        //textGeo.setText(address.getCountryName() + " : " + address.getLocality() + " : " + address.getSubLocality());
        if (address != null) {
            String country = address.getCountryName();
            String city = address.getLocality();
            String locality = address.getSubLocality();
            if (country != null) {
                /*if (country.equals("Singapore")) {
                    if (!isCityAvailable(city)) {
                        city = "Singapore";
                    }
                } else if (country.equals("Philippines")) {
                    if (!isCityAvailable(city)) {
                        city = "Metro Manila";
                    }
                } else if (country.equals("Indonesia") && !TextUtils.isEmpty(city) && city.contains("Jakarta")) {
                    city = "Jakarta";
                }*/
            }
            if (TextUtils.isEmpty(city) || TextUtils.isEmpty(country)) {
                Log.d("Geo Location", "location null city");
                return null;
            }
            String tempCityName = (String) this.localCityNameMap.get(city);
            if (tempCityName != null) {
                city = tempCityName;
            }
            if (locality == null) {
                locality = null;
            }
            Log.d("Geo Location", "location detail" + city + ":" + locality + ":" + country);
            return new String[]{city, locality, country};
        }
        Log.d("Geo Location", "location cannot be resolved");
        return null;
    }
}
