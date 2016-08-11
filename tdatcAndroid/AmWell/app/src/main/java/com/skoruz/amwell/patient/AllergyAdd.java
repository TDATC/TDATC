package com.skoruz.amwell.patient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.google.gson.Gson;
import com.skoruz.amwell.R;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.constants.Constants;
import com.skoruz.amwell.patientEntity.PatientAllergy;
import com.skoruz.amwell.patientEntity.PatientAllergyDetails;
import com.skoruz.amwell.utils.AppController;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AllergyAdd extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText allergyName,allergyType;
    Spinner allergyStatus,allergySeverity;
    Button addAllergy;
    String[]status,severity;
    SharedPreferences sharedPreferences;
    int patientId;
    private ProgressDialog pDialog;
    PatientAllergy patientAllergy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy_add);
        initView();
        Intent intent=getIntent();
        patientAllergy=intent.getParcelableExtra("allergy");
        allergyName.setText(patientAllergy.getAllergyName());
        allergyType.setText(patientAllergy.getAllergyType());
    }

    private void initView(){
        sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        patientId=sharedPreferences.getInt("user_id", 0);
        allergyName= (EditText) findViewById(R.id.add_allergy_name);
        allergyType= (EditText) findViewById(R.id.add_allergy_type);
        allergyStatus= (Spinner) findViewById(R.id.add_allergy_status);
        allergyStatus.setOnItemSelectedListener(this);
        status=getResources().getStringArray(R.array.allergy_status);
        allergyStatus.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,status));
        allergySeverity= (Spinner) findViewById(R.id.add_allergy_severity);
        allergySeverity.setOnItemSelectedListener(this);
        severity=getResources().getStringArray(R.array.allergy_severity);
        allergySeverity.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,severity));

        addAllergy= (Button) findViewById(R.id.add_allergy);
        addAllergy.setOnClickListener(this.clickListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_allergy_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.add_allergy:
                    if (allergyStatus.getSelectedItem().toString().equalsIgnoreCase("Choose status")){
                        Toast.makeText(getApplicationContext(), "Choose status...", Toast.LENGTH_SHORT).show();
                    }else if (allergySeverity.getSelectedItem().toString().equalsIgnoreCase("Choose severity")){
                        Toast.makeText(getApplicationContext(),"Choose severity...",Toast.LENGTH_SHORT).show();
                    }else {
                        sendAllergyDataToServer(BaseURL.postAllergyData, patientId);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putBoolean("allergyAdd",true);
                        editor.commit();
                        finish();
                    }
                    break;
                default:
                    break;
            }
        }
    };


    public void sendAllergyDataToServer(String url,int userId) {
        // Tag used to cancel the request
        String TAB_ALLERGY_VALUE_UPLOAD = "valueAllergyUpload";
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Saving...");
        pDialog.show();

        Gson gson = new Gson();

        PatientAllergyDetails patientAllergyDetails = new PatientAllergyDetails();

        JSONObject jsonObject = null;
        try {
            patientAllergyDetails.setPatientId(userId);
            patientAllergyDetails.setStatus(allergyStatus.getSelectedItem().toString());
            patientAllergyDetails.setSevere(allergySeverity.getSelectedItem().toString());
            patientAllergyDetails.setInsertedDate(Constants.dbDateTimeFormat.format(new Date()));

            PatientAllergy patientAllergies=new PatientAllergy();
            patientAllergies.setId(patientAllergy.getId());
            patientAllergies.setAllergyName(patientAllergy.getAllergyName());
            patientAllergies.setAllergyType(patientAllergy.getAllergyType());

            patientAllergyDetails.setAllergies(patientAllergies);

            String jsonString = gson.toJson(patientAllergyDetails);

            System.out.println(jsonString);

            jsonObject = new JSONObject(jsonString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest uploadToolValue = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(AppController.TAG, response.toString());
                        pDialog.dismiss();
                        try {
                            if (response.getString("status").equalsIgnoreCase("success")) {
                                Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            } else if (response.getString("status").equalsIgnoreCase("failure")) {
                                Toast.makeText(getApplicationContext(),"Cannot connect to AmWell. Please try later..",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AppController.TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Cannot connect to AmWell. Please try later..",
                        Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
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
        AppController.getInstance().addToRequestQueue(uploadToolValue, TAB_ALLERGY_VALUE_UPLOAD);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
