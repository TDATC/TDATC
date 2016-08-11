package com.skoruz.amwell.patient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.google.gson.Gson;
import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.AllergyItemAdapter;
import com.skoruz.amwell.adapter.MedItemAdapter;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.constants.Constants;
import com.skoruz.amwell.patientEntity.PatientAllergyDetails;
import com.skoruz.amwell.patientEntity.PatientMedicationData;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;


public class PersonalHistory extends AppCompatActivity {

    private static final String TAG = UploadValue.class.getSimpleName();
    private RecyclerView medRecyclerView;
    private RecyclerView.Adapter medAdapter,allergyAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<PatientMedicationData> patientMedicationDataList;
    private ArrayList<PatientAllergyDetails> patientAllergyDetailsList;
    private ImageButton fabButton;
    private int patientId;
    SharedPreferences sharedPreferences;
    Gson gson=new Gson();
    String historyName;
    int historyId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_history);
        initView();

        Intent intent=getIntent();
        if(intent!=null){
            historyName=intent.getExtras().getString("historyName", null);
            historyId=intent.getExtras().getInt("historyId",0);
        }

        if(historyName.equalsIgnoreCase("Medication")){
            setTitle(historyName);
            getPatientMedicationList(BaseURL.patientMedicationList, patientId);
            medAdapter=new MedItemAdapter(patientMedicationDataList);
            medRecyclerView.setAdapter(medAdapter);
        }else if (historyName.equalsIgnoreCase("Allergies")){
            setTitle(historyName);
            getPatientAllergyList(BaseURL.patientAllergyList,patientId);
            allergyAdapter=new AllergyItemAdapter(patientAllergyDetailsList);
            medRecyclerView.setAdapter(allergyAdapter);
        }else if (historyName.equalsIgnoreCase("Procedures")){
            setTitle(historyName);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Boolean medicationAdd=sharedPreferences.getBoolean("medicationAdd",false);
        Boolean allergyAdd=sharedPreferences.getBoolean("allergyAdd",false);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if (medicationAdd){
            getPatientMedicationList(BaseURL.patientMedicationList, patientId);
            editor.putBoolean("medicationAdd", false);
            editor.commit();
        }else if (allergyAdd){
            getPatientAllergyList(BaseURL.patientAllergyList, patientId);
            editor.putBoolean("allergyAdd", false);
            editor.commit();
        }
    }

    private void initView(){
        sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        patientId=sharedPreferences.getInt("user_id", 0);
        FrameLayout localFrameLayout = (FrameLayout)findViewById(R.id.med_empty_container);
        patientAllergyDetailsList=new ArrayList<PatientAllergyDetails>();
        patientMedicationDataList=new ArrayList<PatientMedicationData>();
        medRecyclerView= (RecyclerView) findViewById(R.id.personalItem);
        medRecyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        layoutManager=new LinearLayoutManager(this);
        medRecyclerView.setLayoutManager(layoutManager);

        fabButton= (ImageButton) findViewById(R.id.fab_image_button);
        fabButton.setOnClickListener(this.mClickListener);
    }

    private View.OnClickListener mClickListener=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.fab_image_button:
                    Intent addIntent=new Intent(PersonalHistory.this,MedicationSearch.class);
                    addIntent.putExtra("name",historyName);
                    startActivity(addIntent);
                    break;
                default:
                    break;
            }
        }
    };


    public void getPatientMedicationList(String url,int userId){

        String TAB_MED_GET="medGet";

        Date date=new Date();
        String currentDate= Constants.dbDateTimeFormat.format(date);
        currentDate=currentDate.replace(" ","_");

        patientMedicationDataList.clear();

        JsonArrayRequest getPatientMedication=new JsonArrayRequest(url + userId +"/"+currentDate, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        PatientMedicationData medicationData=gson.fromJson(jsonObject.toString(),PatientMedicationData.class);
                        patientMedicationDataList.add(medicationData);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                medAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(getPatientMedication, TAB_MED_GET);
    }

    public void getPatientAllergyList(String url,int userId){

        String TAB_ALLERGY_GET="allergyGet";

        patientAllergyDetailsList.clear();

        JsonArrayRequest getPatientMedication=new JsonArrayRequest(url + userId, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        PatientAllergyDetails allergyData=gson.fromJson(jsonObject.toString(),PatientAllergyDetails.class);
                        patientAllergyDetailsList.add(allergyData);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                allergyAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(getPatientMedication, TAB_ALLERGY_GET);
    }
}
