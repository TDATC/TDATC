package com.skoruz.amwell.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestTickle;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.VolleyTickle;
import com.google.gson.Gson;
import com.skoruz.amwell.BuildConfig;
import com.skoruz.amwell.LoginActivity;
import com.skoruz.amwell.patientEntity.GeoCode;
import com.skoruz.amwell.patientEntity.GeoCode.Result;
import com.skoruz.amwell.patientEntity.GeoCode.Result.Component;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.Utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class SynapseService extends IntentService {

    public static final String ACTION_GEO_CODING = (AppController.BASE_APPLICATION_ID + "action.GEO_CODING");
    public static final String EXTRA_BUNDLE = (AppController.BASE_APPLICATION_ID + "extra.BUNDLE");
    public static final String CITY = "city";
    public static final String LOCALITY = "locality";
    public static final String REQUEST_PARAM_DOCTOR_LOCATION = "location";
    String address = null;
    private LocalBroadcastManager mLocalBroadcastManager;

    public SynapseService() {
        super(SynapseService.class.getName());
    }

    public void onCreate() {
        super.onCreate();
        //this.mEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        this.mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent!=null){
            String action=intent.getAction();
            if (ACTION_GEO_CODING.equals(action)){
                handleGeocoding(intent.getBundleExtra(EXTRA_BUNDLE));
            }
        }
    }

    private void handleGeocoding(Bundle bundleExtra) {
        Location location = (Location) bundleExtra.getParcelable(REQUEST_PARAM_DOCTOR_LOCATION);
        Intent intent = new Intent(LoginActivity.ACTION_LOCATION_CHANGED);
        String streetAddress = getAddressFromMapApi(location.getLatitude() + BuildConfig.FLAVOR, location.getLongitude() + BuildConfig.FLAVOR);
        if (streetAddress != null) {
            intent.putExtra(LOCALITY, streetAddress);
            this.mLocalBroadcastManager.sendBroadcast(intent);
        } else {
            List<Address> addresses = null;
            try {
                addresses = new Geocoder(this, Locale.getDefault()).getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (IllegalArgumentException illegalArgumentException) {
                illegalArgumentException.printStackTrace();
            }
            if (addresses != null && addresses.size() > 0) {
                Address address = (Address) addresses.get(0);
                if (address != null) {
                    intent.putExtra(CITY, address.getLocality());
                    StringBuilder sb = new StringBuilder();
                    int max = address.getMaxAddressLineIndex();
                    for (int i = 0; i < max - 2; i++) {
                        sb.append(address.getAddressLine(i)).append(" ");
                    }
                    intent.putExtra(LOCALITY, sb.toString());
                }
            }
        }
        this.mLocalBroadcastManager.sendBroadcast(intent);
    }

    /*private String getAddressFromMapApi(String latitude, String longitude) {
        Locale locale = AppController.getInstance().getLocale();
        String  tag_string_req = "string_req";

        StringRequest strReq=new StringRequest(String.format(locale, "http://maps.googleapis.com/maps/api/geocode/json?latlng=%1$s,%2$s&sensor=true&language=" + locale.getCountry(), new Object[]{latitude, longitude}), new Response.Listener<String>() {
            @Override
            public void onResponse(String data) {
                try{
                    Gson gson=new Gson();
                    GeoCode geoCode = gson.fromJson(data,GeoCode.class);
                    if ("OK".equalsIgnoreCase(geoCode.status) && geoCode.results.size() > 0) {
                        Result result = (Result) geoCode.results.get(0);
                        StringBuilder stringBuilder = new StringBuilder();
                        Iterator iterator = result.address_components.iterator();
                        while (iterator.hasNext()) {
                            Component component = (Component) iterator.next();
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
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(AppController.TAG, "Error: " + volleyError.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(strReq,tag_string_req);

        return address;

        RequestTickle mRequestTickle = VolleyTickle.newRequestTickle(this);
        mRequestTickle.add(new StringRequest(String.format(locale, "http://maps.googleapis.com/maps/api/geocode/json?latlng=%1$s,%2$s&sensor=true&language=" + locale.getCountry(), new Object[]{latitude, longitude}), null, null));
        NetworkResponse response = mRequestTickle.start();
        String data = BuildConfig.FLAVOR;
        try {
            if (response.statusCode == 200) {
                data = VolleyTickle.parseResponse(response);
                Gson gson = new Gson();
                Class cls = GeoCode.class;
                //GeoCode geoCode = !(gson instanceof Gson) ? gson.fromJson(data, cls) : GsonInstrumentation.fromJson(gson, data, cls);
                GeoCode geoCode = gson.fromJson(data,GeoCode.class);
                if ("OK".equalsIgnoreCase(geoCode.status) && geoCode.results.size() > 0) {
                    Result result = (Result) geoCode.results.get(0);
                    StringBuilder stringBuilder = new StringBuilder();
                    Iterator i$ = result.address_components.iterator();
                    while (i$.hasNext()) {
                        Component component = (Component) i$.next();
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private String getAddressFromMapApi(String latitude, String longitude) {
        String address = null;
        Locale locale = AppController.getInstance().getLocale();
        RequestTickle mRequestTickle = VolleyTickle.newRequestTickle(this);
        mRequestTickle.add(new StringRequest(String.format(locale, "http://maps.googleapis.com/maps/api/geocode/json?latlng=%1$s,%2$s&sensor=true&language=" + locale.getCountry(), new Object[]{latitude, longitude}), null, null));
        NetworkResponse response = mRequestTickle.start();
        String data = "";
        try {
            if (response.statusCode == 200) {
                data = VolleyTickle.parseResponse(response);
                Gson gson = new Gson();
                //Class cls = GeoCode.class;
                GeoCode geoCode = gson.fromJson(data,GeoCode.class);
                //GeoCode geoCode = !(gson instanceof Gson) ? gson.fromJson(data, cls) : GsonInstrumentation.fromJson(gson, data, cls);
                if ("OK".equalsIgnoreCase(geoCode.status) && geoCode.results.size() > 0) {
                    Result result = (Result) geoCode.results.get(0);
                    StringBuilder stringBuilder = new StringBuilder();
                    Iterator i$ = result.address_components.iterator();
                    while (i$.hasNext()) {
                        Component component = (Component) i$.next();
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }


    public static void startGeocoding(Context context, Location location) {
        Intent intent = new Intent(context, SynapseService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(REQUEST_PARAM_DOCTOR_LOCATION, location);
        intent.putExtra(EXTRA_BUNDLE, bundle);
        intent.setAction(ACTION_GEO_CODING);
        context.startService(intent);
    }
}
