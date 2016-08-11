package com.skoruz.amwell;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.google.gson.Gson;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.constants.Constants;
import com.skoruz.amwell.patientEntity.AccountState;
import com.skoruz.amwell.patientEntity.Gender;
import com.skoruz.amwell.patientEntity.PatientDetails;
import com.skoruz.amwell.patientEntity.UserType;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.Utils;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SignUp extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mDob;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mSpecialization;
    private EditText mTaxId;
    private RadioGroup mGenderGroup;
    private EditText mUserType;
    private EditText mHealthPlan;
    private TextView mLoginBtn;
    private Button mSignUp;
    private String mGenderName;
    private SharedPreferences.Editor mEditor;
    private ProgressDialog pDialog;
    private Calendar mSelectedDob;
    private static final String PATIENT="Patient";
    private static final String PHYSICIAN="Physician";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.mSelectedDob = Calendar.getInstance();
        this.mSelectedDob.set(Calendar.YEAR, 1990);
        this.mSelectedDob.set(Calendar.MONTH, 1);
        this.mSelectedDob.set(Calendar.DAY_OF_MONTH, 1);

        mFirstName= (EditText) findViewById(R.id.first_name);
        mLastName= (EditText) findViewById(R.id.last_name);
        mDob= (EditText) findViewById(R.id.dob);
        mEmail= (EditText) findViewById(R.id.input_email);
        mPassword= (EditText) findViewById(R.id.input_password);
        mConfirmPassword= (EditText) findViewById(R.id.confirm_password);
        mSpecialization= (EditText) findViewById(R.id.specialization);
        mTaxId= (EditText) findViewById(R.id.tax_id);

        mLoginBtn= (TextView) findViewById(R.id.link_login);

        mGenderGroup= (RadioGroup) findViewById(R.id.genderGroup);

        mHealthPlan= (EditText) findViewById(R.id.health_plan);
        mUserType= (EditText) findViewById(R.id.user_type);

        mSignUp= (Button) findViewById(R.id.btn_reg_signUp);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(SignUp.this, LoginActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(login);
            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                boolean isValidInput = true;
                String mEmailTxt = mEmail.getText().toString();
                String mFirstNameTxt = mFirstName.getText().toString();
                String mLastNameTxt = mLastName.getText().toString();
                String mPasswordTxt = mPassword.getText().toString();
                String mConfirmPasswordTxt = mConfirmPassword.getText().toString();
                String mDobTxt = mDob.getText().toString();
                String mUserTypeTxt = mUserType.getText().toString();
                int mGenderGroupId = mGenderGroup.getCheckedRadioButtonId();
                String mHealthPlanTxt = mHealthPlan.getText().toString();
                String mSpecializationTxt = mSpecialization.getText().toString();
                String mTaxIdTxt = mTaxId.getText().toString();

                if (Utils.isEmptyString(mConfirmPasswordTxt)) {
                    mConfirmPassword.setError(getString(R.string.confirm_password_error));
                    mConfirmPassword.requestFocus();
                    isValidInput = false;
                }
                if (Utils.isEmptyString(mPasswordTxt)) {
                    mPassword.setError(getString(R.string.password_error));
                    mPassword.requestFocus();
                    isValidInput = false;
                }
                if (!mPasswordTxt.equals(mConfirmPasswordTxt)) {
                    mConfirmPassword.setError(getString(R.string.password_match_error));
                    mConfirmPassword.requestFocus();
                    isValidInput = false;
                }

                if (Utils.isEmptyString(mEmailTxt)) {
                    mEmail.setError(getString(R.string.email_error));
                    mEmail.requestFocus();
                    isValidInput = false;
                } else if (!Utils.isValidEmail(mEmailTxt)) {
                    mEmail.setError(getString(R.string.email_invalid));
                    mEmail.requestFocus();
                    isValidInput = false;
                }
                if (Utils.isEmptyString(mFirstNameTxt)) {
                    mFirstName.setError(getString(R.string.firstName_error));
                    mFirstName.requestFocus();
                    isValidInput = false;
                }
                if (Utils.isEmptyString(mLastNameTxt)) {
                    mLastName.setError(getString(R.string.lastName_error));
                    mLastName.requestFocus();
                    isValidInput = false;
                }
                if (Utils.isEmptyString(mDobTxt)) {
                    mDob.setError(getString(R.string.dob_error));
                    isValidInput = false;
                }

                if (mGenderGroupId == -1) {
                    Toast.makeText(SignUp.this, getString(R.string.gender_error), Toast.LENGTH_SHORT).show();
                    isValidInput = false;
                }

                if (Utils.isEmptyString(mUserTypeTxt)) {
                    mUserType.setError(getString(R.string.userType_error));
                    isValidInput = false;
                } else if (mUserTypeTxt.equalsIgnoreCase(PATIENT)) {
                    if (Utils.isEmptyString(mHealthPlanTxt)) {
                        mHealthPlan.setError(getString(R.string.healthPlan_error));
                        isValidInput = false;
                    }
                } else if (mUserTypeTxt.equalsIgnoreCase(PHYSICIAN)) {
                    if (Utils.isEmptyString(mSpecializationTxt)) {
                        mSpecialization.setError(getString(R.string.specialization_error));
                        isValidInput = false;
                    }
                    if (Utils.isEmptyString(mTaxIdTxt)) {
                        mTaxId.setError(getString(R.string.taxId_error));
                        isValidInput = false;
                    }
                }

                if (!isValidInput){
                    return;
                }else {
                    postTheData();
                }
            }

        });

        mUserType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder userTypeDialog = new AlertDialog.Builder(SignUp.this);
                userTypeDialog.setItems(Constants.userTypes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        mUserType.setText(Constants.userTypes[position]);
                        if (mUserType.getText().toString().equalsIgnoreCase(PATIENT)) {
                            mHealthPlan.setVisibility(View.VISIBLE);
                            mSpecialization.setVisibility(View.GONE);
                            mTaxId.setVisibility(View.GONE);
                        } else if (mUserType.getText().toString().equalsIgnoreCase(PHYSICIAN)) {
                            mSpecialization.setVisibility(View.VISIBLE);
                            mTaxId.setVisibility(View.VISIBLE);
                            mHealthPlan.setVisibility(View.GONE);
                        } else {
                            mHealthPlan.setVisibility(View.GONE);
                            mSpecialization.setVisibility(View.GONE);
                            mTaxId.setVisibility(View.GONE);
                        }
                    }
                });
                AlertDialog userTypeAlertDialog=userTypeDialog.create();
                userTypeAlertDialog.show();
            }
        });

        mHealthPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder healthPlanDialog = new AlertDialog.Builder(SignUp.this);
                healthPlanDialog.setItems(Constants.healthPlan, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        mHealthPlan.setText(Constants.healthPlan[position]);
                    }
                });
                AlertDialog healthPlanAlertDialog = healthPlanDialog.create();
                healthPlanAlertDialog.show();
            }
        });

        mDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectDate(v);
                showDateOfBirthDialog();
            }
        });

        mGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mGenderName = ((RadioButton) findViewById(checkedId)).getText().toString();
            }
        });
    }


    private void showDateOfBirthDialog()
    {
        Calendar localCalendar = this.mSelectedDob;
        DatePickerDialog localDatePickerDialog = new DatePickerDialog(this, this, localCalendar.get(Calendar.YEAR), localCalendar.get(Calendar.MONTH), localCalendar.get(Calendar.DAY_OF_MONTH));
        localDatePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance(Locale.US).getTimeInMillis());
        localDatePickerDialog.show();
    }

    public void postTheData(){
        // Tag used to cancel the request
        String TAB_SIGN_UP = "signUp";
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Registering...");
        pDialog.show();

        Gson gson=new Gson();

        PatientDetails patientDetails=new PatientDetails();

        patientDetails.getUser().setFirstName(mFirstName.getText().toString());
        patientDetails.getUser().setLastName(mLastName.getText().toString());
        patientDetails.getUser().setEmailAddress(mEmail.getText().toString());
        patientDetails.getUser().setPassword(mPassword.getText().toString());
        patientDetails.getUser().setAccountState("ACTIVE");
        patientDetails.getUser().setGender(mGenderName);
        /*if(mGenderName.equalsIgnoreCase("male")){
            gender.setGender_id(1);
            gender.setGender("M");
            patientDetails.getUser().setGender(gender);
        }else if (mGenderName.equalsIgnoreCase("female")){
            gender.setGender_id(2);
            gender.setGender("F");
            patientDetails.getUser().setGender(gender);
        }else {
            gender.setGender_id(3);
            gender.setGender("TG");
            patientDetails.getUser().setGender(gender);
        }*/

        if (mUserType.getText().toString().equalsIgnoreCase("Patient")){
            patientDetails.getUser().setUser_type("PT");
        }else if (mUserType.getText().toString().equalsIgnoreCase("Physician")){
            patientDetails.getUser().setUser_type("PHS");
        }

        patientDetails.setTotalAmountPaid(100);

        try {
            patientDetails.getUser().setDateOfBirth(Constants.dbDateFormat.format(Constants.dobDateFormat.parse(mDob.getText().toString())));
            patientDetails.getUser().setResgistrationDate(Constants.dbDateTimeFormat.format(new Date()));
        }catch (Exception e){
            e.printStackTrace();
        }

        String jsonString=gson.toJson(patientDetails);
        JSONObject jsonObject = null;
        try{
            jsonObject=new JSONObject(jsonString.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                BaseURL.signUpUrl, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(AppController.TAG, response.toString());
                        try{
                            if (response.getString("status").equalsIgnoreCase("success")) {
                                storeUserCredentials();
                            } else if (response.getString("status").equalsIgnoreCase("failure")) {
                                Toast.makeText(getApplicationContext(),
                                        "Cannot connect to AmWell. Please try later..",
                                        Toast.LENGTH_SHORT).show();
                                mSignUp.setEnabled(true);
                            }
                        }catch (Exception e){e.printStackTrace();}
                        pDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AppController.TAG, "Error: " + error.getMessage());
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

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, TAB_SIGN_UP);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.mSelectedDob.set(Calendar.YEAR, year);
        this.mSelectedDob.set(Calendar.MONTH, monthOfYear);
        this.mSelectedDob.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        this.mDob.setText(Constants.dobDateFormat.format(this.mSelectedDob.getTime()));
    }

    public void storeUserCredentials() {
        mEditor= getApplicationContext().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE).edit();
        mEditor.putString("userEmail", mEmail.getText().toString());
        mEditor.putString("userPass", mPassword.getText().toString());

        mEditor.commit();

        Log.i("Shared Pref", "Committed Successfully");
        Toast.makeText(getApplicationContext(), "Registered Successfully!",Toast.LENGTH_SHORT).show();
        Intent login = new Intent(this, LoginActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        login.putExtra("from", "signup");
        startActivity(login);
        finish();
    }
}
