package com.skoruz.amwell.patient;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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
import com.skoruz.amwell.patientEntity.Medication;
import com.skoruz.amwell.patientEntity.PatientMedicationData;
import com.skoruz.amwell.utils.AppController;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MedicationAdd extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText medicineName,chemicalName,medicineType,fromDate,toDate;
    Spinner interval,mode;
    String[]intervals,modes;
    Calendar from_date,to_date;
    Button addMed;
    SharedPreferences sharedPreferences;
    int patientId;
    private ProgressDialog pDialog;
    Medication med;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_add);
        initView();
        Intent intent=getIntent();
        med=intent.getParcelableExtra("medicine");
        medicineName.setText(med.getMedicineName());
        chemicalName.setText(med.getChemicalName());
        medicineType.setText(med.getMedicineType());
    }

    private void initView(){
        sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        patientId=sharedPreferences.getInt("user_id", 0);
        medicineName= (EditText) findViewById(R.id.medicine_name);
        chemicalName= (EditText) findViewById(R.id.chemical_name);
        medicineType= (EditText) findViewById(R.id.medicine_type);
        fromDate= (EditText) findViewById(R.id.fromDate);
        fromDate.setOnClickListener(this.clickListener);
        toDate= (EditText) findViewById(R.id.toDate);
        toDate.setOnClickListener(this.clickListener);

        interval= (Spinner) findViewById(R.id.intervals);
        interval.setOnItemSelectedListener(this);
        intervals=getResources().getStringArray(R.array.intervals);
        interval.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,intervals));
        mode= (Spinner) findViewById(R.id.modes);
        mode.setOnItemSelectedListener(this);
        modes=getResources().getStringArray(R.array.modes);
        mode.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, modes));

        from_date = Calendar.getInstance();
        /*from_date.set(Calendar.YEAR, 1990);
        from_date.set(Calendar.MONTH, 1);
        from_date.set(Calendar.DAY_OF_MONTH, 1);*/

        to_date = Calendar.getInstance();
        /*to_date.set(Calendar.YEAR, 1990);
        to_date.set(Calendar.MONTH, 1);
        to_date.set(Calendar.DAY_OF_MONTH, 1);*/

        addMed= (Button) findViewById(R.id.add_med);
        addMed.setOnClickListener(this.clickListener);
    }

    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.fromDate:
                    showFromDateDialog();
                    break;
                case R.id.toDate:
                    showToDateDialog();
                    break;
                case R.id.add_med:
                        if (fromDate.getText().toString().trim().length()==0 && toDate.getText().toString().trim().length()==0) {
                            Toast.makeText(getApplicationContext(),"Select FromDate and ToDate...",Toast.LENGTH_SHORT).show();
                        }else if (fromDate.getText().toString().trim().length()==0) {
                            Toast.makeText(getApplicationContext(),"Select FromDate...",Toast.LENGTH_SHORT).show();
                        }else if (toDate.getText().toString().trim().length()==0) {
                            Toast.makeText(getApplicationContext(),"Select ToDate...",Toast.LENGTH_SHORT).show();
                        } else if (interval.getSelectedItem().toString().equalsIgnoreCase("Choose interval")){
                            Toast.makeText(getApplicationContext(),"Choose interval...",Toast.LENGTH_SHORT).show();
                        }else if (mode.getSelectedItem().toString().equalsIgnoreCase("Choose mode")){
                            Toast.makeText(getApplicationContext(),"Choose mode...",Toast.LENGTH_SHORT).show();
                        }else {
                            sendMedicationDataToServer(BaseURL.postMedicationData, patientId);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putBoolean("medicationAdd",true);
                            editor.commit();
                            finish();
                        }
                    break;
                default:
                    break;
            }
        }
    };


    public void sendMedicationDataToServer(String url,int userId) {
        // Tag used to cancel the request
        String TAB_MED_VALUE_UPLOAD = "valueMedUpload";
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Saving...");
        pDialog.show();

        Gson gson = new Gson();

        PatientMedicationData patientMedicationData = new PatientMedicationData();

        JSONObject jsonObject = null;
        try {
            patientMedicationData.setPatientId(userId);
            patientMedicationData.setInstruction(interval.getSelectedItem().toString());
            patientMedicationData.setBeforeAfterFood(mode.getSelectedItem().toString());
            patientMedicationData.setInsertedDate(Constants.dbDateTimeFormat.format(new Date()));

            patientMedicationData.setFromDate(Constants.dbDateFormat.format(Constants.dobDateFormat.parse(fromDate.getText().toString())));
            patientMedicationData.setToDate(Constants.dbDateFormat.format(Constants.dobDateFormat.parse(toDate.getText().toString())));

            Medication medication=new Medication();
            medication.setId(med.getId());
            medication.setMedicineName(med.getMedicineName());
            medication.setChemicalName(med.getChemicalName());
            medication.setMedicineType(med.getMedicineType());

            patientMedicationData.setMedicine(medication);

            String jsonString = gson.toJson(patientMedicationData);

            System.out.println(jsonString);

            jsonObject = new JSONObject(jsonString.toString());
        } catch (Exception e) {

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
        AppController.getInstance().addToRequestQueue(uploadToolValue, TAB_MED_VALUE_UPLOAD);
    }

    private void showFromDateDialog()
    {
        Calendar localCalendar = this.from_date;
        DatePickerDialog localDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                from_date.set(Calendar.YEAR, year);
                from_date.set(Calendar.MONTH, monthOfYear);
                from_date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                fromDate.setText(Constants.dobDateFormat.format(from_date.getTime()));
            }
        }, localCalendar.get(Calendar.YEAR), localCalendar.get(Calendar.MONTH), localCalendar.get(Calendar.DAY_OF_MONTH));
        localDatePickerDialog.getDatePicker().setMinDate(Calendar.getInstance(Locale.US).getTimeInMillis());
        localDatePickerDialog.show();
    }

    private void showToDateDialog()
    {
        Calendar localCalendar = this.to_date;
        DatePickerDialog localDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                to_date.set(Calendar.YEAR, year);
                to_date.set(Calendar.MONTH, monthOfYear);
                to_date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                toDate.setText(Constants.dobDateFormat.format(to_date.getTime()));
            }
        }, localCalendar.get(Calendar.YEAR), localCalendar.get(Calendar.MONTH), localCalendar.get(Calendar.DAY_OF_MONTH));
        localDatePickerDialog.getDatePicker().setMinDate(Calendar.getInstance(Locale.US).getTimeInMillis());
        localDatePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medication_add, menu);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
