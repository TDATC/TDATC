package com.skoruz.amwell;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.google.gson.Gson;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.constants.Constants;
import com.skoruz.amwell.misc.GeoLocation;
import com.skoruz.amwell.patient.Patient_Home;
import com.skoruz.amwell.patientEntity.PatientDetails;
import com.skoruz.amwell.physician.Physician_Home;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.Utils;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText userName,passWord;
    private Button login;
    private Button signUp;
    private String userStatus,userType;
    private SharedPreferences.Editor editor;
    private Integer regId;
    private ProgressDialog pDialog;
    private Gson gson=new Gson();
    public static final String ACTION_LOCATION_CHANGED = "com.tdatc.synapse.action.CITY";
    private TextView forgotPassword;
    private static final String TAG=LoginActivity.class.getSimpleName();
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login= (Button) findViewById(R.id.btn_login);
        userName= (EditText) findViewById(R.id.input_email);
        passWord= (EditText) findViewById(R.id.input_password);
        signUp= (Button) findViewById(R.id.btn_signUp);
        forgotPassword= (TextView) findViewById(R.id.forgot_password);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(LoginActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setGravity(Gravity.TOP);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.black_holo));
                }


                WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                //layoutParams.x = 100; // right margin
                layoutParams.y = 170; // top margin
                dialog.getWindow().setAttributes(layoutParams);
                // e.g. bottom + left margins:
                /*dialog.getWindow().setGravity(Gravity.BOTTOM|Gravity.LEFT);
                WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                layoutParams.x = 100; // left margin
                layoutParams.y = 170; // bottom margin
                dialog.getWindow().setAttributes(layoutParams);*/

                dialog.setContentView(R.layout.forgot_password);
                final EditText email= (EditText) dialog.findViewById(R.id.forgot_input_mail);
                final Button send_email= (Button) dialog.findViewById(R.id.btn_send_email);
                final TextView login_msg= (TextView) dialog.findViewById(R.id.login_msg);
                send_email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email_id=email.getText().toString();
                        if (Utils.isEmptyString(email_id)){
                            email.setError(getString(R.string.email_error));
                        }else if (!Utils.isValidEmail(email_id)){
                            email.setError(getString(R.string.email_invalid));
                        }else if (Utils.isValidEmail(email_id)){
                            sendMailIdToServer(email_id);
                        }
                    }
                });

                email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId== EditorInfo.IME_ACTION_DONE){
                            String email_id=email.getText().toString();
                            if (Utils.isEmptyString(email_id)){
                                email.setError(getString(R.string.email_error));
                            }else if (!Utils.isValidEmail(email_id)){
                                email.setError(getString(R.string.email_invalid));
                            }else if (Utils.isValidEmail(email_id)){
                                sendMailIdToServer(email_id);
                                return true;
                            }
                        }
                        return false;
                    }
                });

                login_msg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                        }
                    }
                });

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                        }
                    }
                });

                dialog.show();


                /*Intent intent=new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(intent);*/
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userName.getText().toString();
                String password = passWord.getText().toString();
                if (Utils.isEmptyString(email)) {
                    userName.setError(getString(R.string.email_error));
                    userName.requestFocus();
                } else if (!Utils.isValidEmail(email)) {
                    userName.setError(getString(R.string.email_invalid));
                    userName.requestFocus();
                }
                if (Utils.isEmptyString(password)) {
                    passWord.setError(getString(R.string.password_error));
                    passWord.requestFocus();
                }
                if (userName.getText().toString().length() > 0 && passWord.getText().toString().length() > 0) {
                    if (Utils.isValidEmail(email)) {
                        login();
                    }
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(signUpIntent);
            }
        });
    }

    public void sendMailIdToServer(String emailId){


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Sending mail...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        StringRequest mailResponse=new StringRequest(Request.Method.GET, BaseURL.forgotPassword+emailId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Password has been sent to your email Id")){
                //if (response.equalsIgnoreCase("Success")){
                    pDialog.dismiss();
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Password is sent to your entered Email Id", Toast.LENGTH_SHORT).show();
                }
                else if (response.equalsIgnoreCase("Invalid user id")){
                //else if (response.equalsIgnoreCase("Failure")){
                    pDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Please enter valid Email Id", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(AppController.TAG, "Error: " + error.getMessage());
                pDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Something went wrong Please try again...", Toast.LENGTH_SHORT).show();
            }
        });

        mailResponse.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(mailResponse, TAG);
    }

    public void login(){

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Authenticating...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                BaseURL.userDetailsUrl + userName.getText().toString() + "/" + passWord.getText().toString(),null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(AppController.TAG, response.toString());
                        try{
                            if (response.has("status")){
                                if (response.getString("status").equalsIgnoreCase("Invalid User")) {
                                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show();
                                    signUp.setEnabled(true);
                                }
                            }else if (response.has("user")){
                                JSONObject userObject=response.getJSONObject("user");
                                    editor=getApplicationContext().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE).edit();
                                    if (userObject.getString("user_type").equalsIgnoreCase("PT")){
                                        //PatientDetails patientDetails=gson.fromJson(response.toString(),PatientDetails.class);
                                        editor.putString("patient_details",response.toString());
                                    }else if (userObject.getString("user_type").equalsIgnoreCase("PHS")){
                                        editor.putString("physician_details",response.toString());
                                    }

                                    editor.putString("userEmail", userObject.getString("emailAddress"));
                                    editor.putString("userPass", userObject.getString("password"));
                                    editor.putString("phoneNo", userObject.getString("phonePersonel"));
                                    editor.putString("dob", Constants.dobDateFormat.format(Constants.dbDateFormat.parse(userObject.getString("dateOfBirth"))));
                                    editor.putString("name",userObject.getString("firstName")+" "+userObject.getString("lastName"));
                                    //editor.putString("bloodGroup",response.getString("bloodType"));
                                    editor.putInt("user_id", userObject.getInt("user_id"));
                                    editor.putBoolean("isAuth", true);
                                    editor.putString("userType", userObject.getString("user_type"));
                                    editor.putString("gender",userObject.getString("gender"));

                                    userType=userObject.getString("user_type");

                                    if(userType.equalsIgnoreCase("PHS")){
                                        if (response.has("specializations") && !response.isNull("specializations")){
                                            JSONObject specialization=response.getJSONObject("specializations");
                                            editor.putString("specializations", specialization.getString("specializations"));
                                        }
                                        if (response.has("qualification") && !response.isNull("qualification")){
                                            JSONObject qualification=response.getJSONObject("qualification");
                                            editor.putString("qualification",qualification.getString("qualification"));
                                        }
                                        if (response.has("affliation") && !response.isNull("affliation")){
                                            JSONObject affliation=response.getJSONObject("affliation");
                                            editor.putString("universityName", affliation.getString("universityName"));
                                        }
                                        editor.putString("experienceInYear",response.getString("experienceInYear"));
                                    }

                                    editor.commit();
                            /*userStatus = response.getString("status");
                            regId=response.getInt("loginId");*/
                                    storeUserCredentials();
                            }
                        }catch (Exception e){e.printStackTrace();}
                        pDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(AppController.TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Cannot connect to AmWell. Please try later..",
                        Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(jsonObjReq, TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GeoLocation geoLocation=new GeoLocation(LoginActivity.this);
        geoLocation.updateLocationAsynchronous(false, false, null);
    }

    public void storeUserCredentials() {
       /* editor=getApplicationContext().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE).edit();
        editor.putString("userEmail", userName.getText().toString());
        editor.putString("userPass", passWord.getText().toString());
        editor.putInt("user_id", regId);
        editor.putBoolean("isAuth", true);
        editor.putString("userType", userType);

        editor.commit();
        Log.i("Shared Pref", "Committed Successfully");*/




        Intent homeScreen;

        if(userType.equalsIgnoreCase("PT")){
            homeScreen = new Intent(this, Patient_Home.class);
            startActivity(homeScreen);
            finish();
        }else if (userType.equalsIgnoreCase("PHS")){
            homeScreen = new Intent(this, Physician_Home.class);
            startActivity(homeScreen);
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppController.getInstance().getRequestQueue().cancelAll(TAG);
    }
}
