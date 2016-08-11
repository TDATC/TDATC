package com.skoruz.amwell.patient;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
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
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.gson.Gson;
import com.skoruz.amwell.LoginActivity;
import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.SectionsPagerAdapter;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.patientEntity.PatientAppointment;
import com.skoruz.amwell.service.GPSTracker;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.CheckConnectivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Patient_Home extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    CheckConnectivity receiver;
    private static final String TAG = Patient_Home.class.getSimpleName();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters

    GPSTracker gpsTracker;
    double latitude, longitude;
    private SharedPreferences sharedPreferences;
    private int patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__home);

        sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        patientId = sharedPreferences.getInt("user_id", 0);

       /* // First we need to check availability of play services
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
            createLocationRequest();
        }*/

        //registerReceiver(new CheckConnectivity(getApplicationContext()),new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_directions_car_black_18dp);
        toolbar.setTitle("TDATC");
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("HEALTH"));
        tabLayout.addTab(tabLayout.newTab().setText("PROVIDERS"));
        tabLayout.addTab(tabLayout.newTab().setText("ACCOUNT"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patient__home, menu);
        return true;
    }

    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     * */
    /*private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            //return false;
        }
        return true;
    }*/
    private boolean checkPlayServices() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                showErrorDialog(status);
            } else {
                Toast.makeText(this, "This device is not supported.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    void showErrorDialog(int code) {
        GooglePlayServicesUtil.getErrorDialog(code, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* checkPlayServices();
        // Resuming the periodic location updates
        if (mGoogleApiClient!=null) {
            if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
                startLocationUpdates();
            }
        }*/
        gpsTracker = new GPSTracker(Patient_Home.this);
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            if (latitude != 0.00 && longitude != 0.00) {
                updateLatLong(BaseURL.uploadLatLong, patientId, latitude, longitude);
            }
        } else {
            gpsTracker.showSettingsAlert();
        }
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
                                Toast.makeText(Patient_Home.this, "Cannot connect to AmWell. Please try later..", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AppController.TAG, "Error: " + error.getMessage());
                Toast.makeText(Patient_Home.this,
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PLAY_SERVICES_RESOLUTION_REQUEST:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Google Play Services must be installed and up-to-date.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
/*
            case REQUEST_CODE_PICK_ACCOUNT:
                if (resultCode == RESULT_OK) {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    AccountUtils.setAccountName(this, accountName);
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "This application requires a Google account.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
*/
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
       /* if(mGoogleApiClient.isConnected()) {
            mRequestingLocationUpdates = false;
            stopLocationUpdates();
        }*/
    }

    /**
     * Creating location request object
     * */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    /**
     * Starting the location updates
     * */
    protected void startLocationUpdates() {
        mRequestingLocationUpdates = true;
        if (mGoogleApiClient != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        mRequestingLocationUpdates = false;
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout:
                AlertDialog.Builder logout = new AlertDialog.Builder(Patient_Home.this);
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
        /*if (id == R.id.action_logout) {
            AlertDialog.Builder logout=new AlertDialog.Builder(Patient_Home.this);
            logout.setTitle("Logout from AmWell");
            logout.setMessage("Logging out will delete all your AmWell data from this device!");
            logout.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE).edit();
                    editor.clear();
                    editor.commit();
                    Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(logoutIntent);
                    finish();
                }
            });
            logout.setNegativeButton("Cancel",null);
            logout.show();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(receiver);
    }

    @Override
    public void onConnected(Bundle bundle) {
        /*displayLocation();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }*/
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }

    /**
     * Method to display the location on UI
     * */
    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            Toast.makeText(Patient_Home.this, "Latitude is "+ String.valueOf(latitude) + "and Longitude is "+ String.valueOf(longitude), Toast.LENGTH_SHORT).show();
        } else {
            startLocationUpdates();
            //Toast.makeText(Patient_Home.this, "Couldn't get the location. Make sure location is enabled on the device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        /*stopLocationUpdates();
        // Assign the new location
        mLastLocation = location;

        Toast.makeText(getApplicationContext(), "Location changed!",
                Toast.LENGTH_SHORT).show();

        // Displaying the new location on UI
        displayLocation();*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*if (mGoogleApiClient!=null) {
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }*/
    }
}
