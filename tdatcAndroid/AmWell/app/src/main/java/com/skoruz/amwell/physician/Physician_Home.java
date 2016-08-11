package com.skoruz.amwell.physician;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.LocationSource;
import com.skoruz.amwell.LoginActivity;
import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.PhySectionsPagerAdapter;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.service.GPSTracker;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.PlayServicesUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Physician_Home extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationSource.OnLocationChangedListener, LocationListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter adapter;
    //GPSTracker gpsTracker;
    double latitude, longitude;
    private SharedPreferences sharedPreferences;
    private int patientId;
    private FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;
    private GoogleApiClient mLocationClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physician__home);

        sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        patientId = sharedPreferences.getInt("user_id", 0);

        toolbar = (Toolbar) findViewById(R.id.phyToolbar);
        tabLayout = (TabLayout) findViewById(R.id.phyTab_layout);
        viewPager = (ViewPager) findViewById(R.id.phyPager);
        //toolbar.setNavigationIcon(R.drawable.ic_directions_car_black_18dp);
        toolbar.setTitle("TDATC");
        setSupportActionBar(toolbar);


        tabLayout.addTab(tabLayout.newTab().setText("ACCOUNT"));
        tabLayout.addTab(tabLayout.newTab().setText("APPOINTMENTS"));
        tabLayout.addTab(tabLayout.newTab().setText("AUDIO & VIDEO"));
        tabLayout.addTab(tabLayout.newTab().setText("ASSESSMENTS"));
        tabLayout.addTab(tabLayout.newTab().setText("VIDEO CHAT"));

        adapter = new PhySectionsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initLocationRequest() {
        if (!PlayServicesUtils.checkGooglePlaySevices(this)) {
        }
        while ((this.mLocationClient != null) && (this.mLocationClient.isConnected())) {
            return;
        }
        this.mLocationRequest = LocationRequest.create();
        this.mLocationRequest.setInterval(60000L);
        this.mLocationRequest.setNumUpdates(1);
        this.mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.mLocationRequest.setFastestInterval(1000L);
        this.mLocationClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        this.mLocationClient.connect();
    }

    private void stopLocationUpdates() {
        if (this.mLocationClient != null) {
            this.mLocationClient.unregisterConnectionFailedListener(this);
            this.mLocationClient.unregisterConnectionCallbacks(this);
            if (this.mLocationClient.isConnected()) {
                this.mLocationClient.disconnect();
            }
            this.mLocationClient = null;
        }
        /*if (this.mReceiver != null) {
            this.mLocalBroadcastManager.unregisterReceiver(this.mReceiver);
        }*/
    }

    public boolean checkLocationServices() {
        boolean bool1 = false;
        boolean bool2;
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try {
            bool1 = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean bool3 = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            bool2 = bool3;

        } catch (Exception e) {
            e.printStackTrace();
            bool2 = false;
        }
        return bool1 || bool2;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_physician__home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout:
                AlertDialog.Builder logout = new AlertDialog.Builder(Physician_Home.this);
                logout.setTitle("Logout from TDATC");
                logout.setMessage("Logging out will delete all your TDATC data from this device!");
                logout.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE).edit();
                        editor.clear();
                        editor.commit();
                        Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logoutIntent);
                        finish();
                    }
                });
                logout.setNegativeButton("Cancel", null);
                logout.show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(Physician_Home.this, "OnResume", Toast.LENGTH_SHORT).show();
        /*gpsTracker=new GPSTracker(Physician_Home.this);
        if(gpsTracker.canGetLocation()){
            latitude=gpsTracker.getLatitude();
            longitude=gpsTracker.getLongitude();
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            if(latitude!=0.00 && longitude!=0.00){
                updateLatLong(BaseURL.uploadLatLong,patientId,latitude,longitude);
            }
        }else{
            gpsTracker.showSettingsAlert();
        }*/
        initLocationRequest();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    public void updateLatLong(String url, int userId, double lat, double lon) {
        // Tag used to cancel the request
        String TAB_LAT_LON_CHANGE = "latLonChange";
        /*pDialog = new ProgressDialog(mActivity);
        pDialog.setMessage("Saving...");
        pDialog.show();*/

        JsonObjectRequest updateStatus = new JsonObjectRequest(Request.Method.POST,
                url + userId + "/" + lat + "/" + lon, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(AppController.TAG, response.toString());
                        //pDialog.dismiss();
                        try {
                            if (response.getString("status").equalsIgnoreCase("success")) {
                                //getPatientAppRemainderList(BaseURL.getAppointmentRemainders, patientId, 0);
                            } else if (response.getString("status").equalsIgnoreCase("failure")) {
                                Toast.makeText(Physician_Home.this, "Cannot connect to AmWell. Please try later..", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AppController.TAG, "Error: " + error.getMessage());
                Toast.makeText(Physician_Home.this,
                        "Cannot connect to AmWell. Please try later..",
                        Toast.LENGTH_SHORT).show();
                //pDialog.dismiss();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(updateStatus, TAB_LAT_LON_CHANGE);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if ((this.mLocationClient != null) && (this.mLocationRequest != null) && (this.mLocationClient.isConnected())) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            this.fusedLocationProviderApi.requestLocationUpdates(this.mLocationClient, this.mLocationRequest, this);
            if (checkLocationServices()) {
                onLocationChanged(this.fusedLocationProviderApi.getLastLocation(this.mLocationClient));
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (this.mLocationClient.isConnected()) {
            this.fusedLocationProviderApi.removeLocationUpdates(this.mLocationClient, this);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {}
        try
        {
            connectionResult.startResolutionForResult(this, 9000);
            return;
        }
        catch (IntentSender.SendIntentException localSendIntentException)
        {
            localSendIntentException.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + location.getLatitude() + "\nLong: " + location.getLongitude(), Toast.LENGTH_LONG).show();
    }
}
