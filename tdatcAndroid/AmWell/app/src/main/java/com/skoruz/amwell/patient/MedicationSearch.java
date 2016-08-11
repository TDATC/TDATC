package com.skoruz.amwell.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.google.gson.Gson;
import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.AllergyConstantListAdapter;
import com.skoruz.amwell.adapter.MedConstantListAdapter;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.patientEntity.Medication;
import com.skoruz.amwell.patientEntity.PatientAllergy;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MedicationSearch extends AppCompatActivity implements MedConstantListAdapter.OnItemClickListener,AllergyConstantListAdapter.OnItemClickListener{

    private static final String TAG = UploadValue.class.getSimpleName();
    private RecyclerView medList;
    private MedConstantListAdapter medListAdapter;
    private AllergyConstantListAdapter allergyListAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private EditText searchMed;
    private ArrayList<Medication> medicationList;
    private ArrayList<PatientAllergy> patientAllergyList;
    private ArrayList<Medication> newGrid;
    private ArrayList<PatientAllergy> newGridList;
    Gson gson=new Gson();
    private String headerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_search);

        initView();

        Intent intent=getIntent();
        if (intent!=null){
            headerName=intent.getExtras().getString("name",null);

            if (headerName.equalsIgnoreCase("Medication")){
                setTitle("Medication Search");
                getMedicationConstantList(BaseURL.medicationConstantList);
                medListAdapter=new MedConstantListAdapter(medicationList);
                medListAdapter.setOnItemClickListener(this);
                medList.setAdapter(medListAdapter);
                searchMed.setHint("Medicine name, Substance name");
            }else if (headerName.equalsIgnoreCase("Allergies")){
                setTitle("Allergy Search");
                getAllergyConstantList(BaseURL.allergyConstantList);
                allergyListAdapter=new AllergyConstantListAdapter(patientAllergyList);
                allergyListAdapter.setOnItemClickListener(this);
                medList.setAdapter(allergyListAdapter);
                searchMed.setHint("Allergy name");
            }
        }


        /*((MedConstantListAdapter)medListAdapter).setOnItemClickListener(new MedConstantListAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Medication med=null;
                if(newGrid!=null && newGrid.size()>0){
                    med=newGrid.get(position);
                }else if(medicationList!=null && medicationList.size()>0){
                    med=medicationList.get(position);
                }
                Intent addIntent=new Intent(MedicationSearch.this,MedicationAdd.class);
                addIntent.putExtra("medicine",med);
                startActivity(addIntent);
                finish();
            }
        });

        ((AllergyConstantListAdapter)allergyListAdapter).setOnItemClickListener(new AllergyConstantListAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                PatientAllergy patientAllergy = null;
                if (newGridList != null && newGridList.size() > 0) {
                    patientAllergy = newGridList.get(position);
                } else if (medicationList != null && medicationList.size() > 0) {
                    patientAllergy = patientAllergyList.get(position);
                }
                Intent addIntent = new Intent(MedicationSearch.this, MedicationAdd.class);
                addIntent.putExtra("allergy", patientAllergy);
                startActivity(addIntent);
                finish();
            }
        });*/

        searchMed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                comparing(s.toString());
            }
        });
    }

    private void initView(){
        patientAllergyList=new ArrayList<PatientAllergy>();
        medicationList=new ArrayList<Medication>();
        medList= (RecyclerView) findViewById(R.id.medList);
        medList.addItemDecoration(new DividerItemDecoration(this, null));
        layoutManager=new LinearLayoutManager(this);
        medList.setLayoutManager(layoutManager);
        searchMed= (EditText) findViewById(R.id.med_search);
    }

    public void comparing(String name) {
        if (headerName.equalsIgnoreCase("Medication")){
            newGrid = new ArrayList<Medication>();
            newGrid.clear();
            for (int i = 0; i < medicationList.size(); i++) {
                if ((medicationList.get(i).getMedicineName().toLowerCase()).contains(name.toString().toLowerCase())) {
                    Medication images = new Medication();
                    images.setId(medicationList.get(i).getId());
                    images.setMedicineName(medicationList.get(i).getMedicineName());
                    images.setMedicineType(medicationList.get(i).getMedicineType());
                    images.setChemicalName(medicationList.get(i).getChemicalName());
                    newGrid.add(images);
                }
            }
            medListAdapter=new MedConstantListAdapter(newGrid);
            medList.setAdapter(medListAdapter);
            medListAdapter.notifyDataSetChanged();
        }else if (headerName.equalsIgnoreCase("Allergies")){
            newGridList = new ArrayList<PatientAllergy>();
            newGridList.clear();
            for (int i = 0; i < patientAllergyList.size(); i++) {
                if ((patientAllergyList.get(i).getAllergyName().toLowerCase()).contains(name.toString().toLowerCase())) {
                    PatientAllergy images = new PatientAllergy();
                    images.setId(patientAllergyList.get(i).getId());
                    images.setAllergyName(patientAllergyList.get(i).getAllergyName());
                    images.setAllergyType(patientAllergyList.get(i).getAllergyType());
                    newGridList.add(images);
                }
            }
            allergyListAdapter=new AllergyConstantListAdapter(newGridList);
            medList.setAdapter(allergyListAdapter);
            allergyListAdapter.notifyDataSetChanged();
        }
    }

    public void getMedicationConstantList(String url){

        String TAB_MED_CONST_GET="medConstGet";

        medicationList.clear();

        JsonArrayRequest getPatientMedication=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Medication medication=gson.fromJson(jsonObject.toString(),Medication.class);
                        medicationList.add(medication);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                medListAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(getPatientMedication, TAB_MED_CONST_GET);
    }

    public void getAllergyConstantList(String url){

        String TAB_ALLERGY_CONST_GET="allergyConstGet";

        patientAllergyList.clear();

        JsonArrayRequest getPatientMedication=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        PatientAllergy patientAllergy=gson.fromJson(jsonObject.toString(),PatientAllergy.class);
                        patientAllergyList.add(patientAllergy);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                allergyListAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(getPatientMedication, TAB_ALLERGY_CONST_GET);
    }

    @Override
    public void onItemClick(MedConstantListAdapter.MedDataListHolder item, int position) {
        Medication med=null;
        if(newGrid!=null && newGrid.size()>0){
            med=newGrid.get(position);
        }else if(medicationList!=null && medicationList.size()>0){
            med=medicationList.get(position);
        }
        Intent addIntent=new Intent(MedicationSearch.this,MedicationAdd.class);
        addIntent.putExtra("medicine", med);
        startActivity(addIntent);
        finish();
    }

    @Override
    public void onItemClick(AllergyConstantListAdapter.AllergyDataListHolder item, int position) {
        PatientAllergy patientAllergy = null;
        if (newGridList != null && newGridList.size() > 0) {
            patientAllergy = newGridList.get(position);
        } else if (patientAllergyList != null && patientAllergyList.size() > 0) {
            patientAllergy = patientAllergyList.get(position);
        }
        Intent addIntent = new Intent(MedicationSearch.this, AllergyAdd.class);
        addIntent.putExtra("allergy", patientAllergy);
        startActivity(addIntent);
        finish();
    }

    /*@Override
    public void onItemClick(int position, View v) {
        if (headerName.equalsIgnoreCase("Medication")){
            Medication med=null;
            if(newGrid!=null && newGrid.size()>0){
                med=newGrid.get(position);
            }else if(medicationList!=null && medicationList.size()>0){
                med=medicationList.get(position);
            }
            Intent addIntent=new Intent(MedicationSearch.this,MedicationAdd.class);
            addIntent.putExtra("medicine", med);
            startActivity(addIntent);
            finish();
        }else if (headerName.equalsIgnoreCase("Allergies")){
            PatientAllergy patientAllergy = null;
            if (newGridList != null && newGridList.size() > 0) {
                patientAllergy = newGridList.get(position);
            } else if (medicationList != null && medicationList.size() > 0) {
                patientAllergy = patientAllergyList.get(position);
            }
            Intent addIntent = new Intent(MedicationSearch.this, MedicationAdd.class);
            addIntent.putExtra("allergy", patientAllergy);
            startActivity(addIntent);
            finish();
        }
    }*/
}
