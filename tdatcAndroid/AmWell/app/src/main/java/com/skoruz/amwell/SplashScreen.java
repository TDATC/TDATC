package com.skoruz.amwell;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.skoruz.amwell.patient.Patient_Home;
import com.skoruz.amwell.physician.Physician_Home;
import com.skoruz.amwell.utils.CheckConnectivity;

public class SplashScreen extends AppCompatActivity {

    protected Thread splashThread;
    public static SharedPreferences amWellSettings;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        amWellSettings= getApplicationContext().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        final Boolean isAuth= amWellSettings.getBoolean("isAuth", false);
        final String userType=amWellSettings.getString("userType",null);
        CheckConnectivity checkConnectivity = new CheckConnectivity(getApplicationContext());
        if(checkConnectivity.isNetworkAvailable())
        {
            splashThread = new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000); //Display splashscreen for 2 sec
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    } finally {
                        finish();
                        if(isAuth && userType.equalsIgnoreCase("PT")){
                            Intent patient_home = new Intent(getApplicationContext(),Patient_Home.class);
                            finish();
                            startActivity(patient_home);
                        }
                        else if (isAuth && userType.equalsIgnoreCase("PHS")){
                            Intent patient_home = new Intent(getApplicationContext(),Physician_Home.class);
                            finish();
                            startActivity(patient_home);
                        }
                        else{
                            Intent showLoginOrRegister = new Intent(getApplicationContext(),LoginActivity.class);
                            finish();
                            startActivity(showLoginOrRegister);
                        }
                    }
                }
            };
            splashThread.start();

        } else {
            AlertDialog.Builder connectionErrorDialog = new AlertDialog.Builder(this);
            connectionErrorDialog.setTitle("Oops!");
            connectionErrorDialog.setMessage("Please make sure that you have an active data connection.").setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog,int id)
                {
                    finish();
                }
            });
            AlertDialog alert = connectionErrorDialog.create();
            alert.show();
        }//else
    }}
