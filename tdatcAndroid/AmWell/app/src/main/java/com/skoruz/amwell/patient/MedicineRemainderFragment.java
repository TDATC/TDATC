package com.skoruz.amwell.patient;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.google.gson.Gson;
import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.MedicineRemainderAdapter;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.patientEntity.PatientMedicationData;
import com.skoruz.amwell.utils.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicineRemainderFragment extends Fragment implements CompoundButton.OnCheckedChangeListener,AdapterView.OnItemClickListener{

    private static final String TAG = Remainders.class.getSimpleName();
    List<PatientMedicationData> medRemainderList=new ArrayList<PatientMedicationData>();;
    ListView med_remainder_list;
    Gson gson=new Gson();
    SharedPreferences sharedPreferences;
    int patientId;
    MedicineRemainderAdapter adapter;
    SwitchCompat switchCompat;
    TextView textView;
    int POS;
    AlertDialog d ;
    static int listSize;
    private Activity mActivity;
    public MedicineRemainderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_medicine_remainder, container, false);
        sharedPreferences=mActivity.getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        patientId=sharedPreferences.getInt("user_id", 0);
        textView= (TextView) view.findViewById(R.id.medRemaindersText);
        switchCompat= (SwitchCompat) view.findViewById(R.id.med_switch_compat);
        switchCompat.setOnCheckedChangeListener(this);
        med_remainder_list= (ListView) view.findViewById(R.id.med_remainder_list);
        adapter=new MedicineRemainderAdapter(medRemainderList,mActivity);
        med_remainder_list.setAdapter(adapter);
        med_remainder_list.setOnItemClickListener(this);
        if(sharedPreferences.getBoolean("med_switch_status",false)){
            getPatientMedRemainderList(BaseURL.getMedicineRemainders, patientId, 1);
        }else {
            getPatientMedRemainderList(BaseURL.getMedicineRemainders, patientId, 0);
        }
        switchCompat.setChecked(sharedPreferences.getBoolean("med_switch_status",false));
        return view;
    }

    public void getPatientMedRemainderList(String url,int userId, final int isRead){
        // Tag used to cancel the request
        String TAB_MED_REMAINDER_GET = "medRemainderGet";
        medRemainderList.clear();
        JsonArrayRequest getMedRemainder=new JsonArrayRequest(url+userId+"/"+isRead, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++){
                    try {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            PatientMedicationData patientMedicationData=gson.fromJson(jsonObject.toString(), PatientMedicationData.class);
                            medRemainderList.add(patientMedicationData);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                listSize=medRemainderList.size();
                if(isRead==0){
                    textView.setText(getResources().getString(R.string.remainder_unread) + " (" + listSize+ ")");
                }else{
                    textView.setText(getResources().getString(R.string.remainder_read) + " (" + listSize+ ")");
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
                Toast.makeText(mActivity,
                        "Cannot connect to AmWell. Please try later..",
                        Toast.LENGTH_SHORT).show();
                if(isRead==0){
                    textView.setText(getResources().getString(R.string.remainder_unread) + " (" + 0+ ")");
                }else{
                    textView.setText(getResources().getString(R.string.remainder_read) + " (" + 0+ ")");
                }
                /*Need to handle the error...custom implementation should be done. */
                adapter.notifyDataSetChanged();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(getMedRemainder, TAB_MED_REMAINDER_GET);
    }

    public void sendDataToServer(String url,PatientMedicationData patientMedicationData) {
        // Tag used to cancel the request
        String TAB_STATUS_CHANGE = "statusChange";
        /*pDialog = new ProgressDialog(mActivity);
        pDialog.setMessage("Saving...");
        pDialog.show();*/

        Gson gson = new Gson();

        /*PatientMeasurementToolsDetails patientMeasurementToolsDetails = new PatientMeasurementToolsDetails();

        patientMeasurementToolsDetails.setMeasurementToolId(toolId+1);
        patientMeasurementToolsDetails.setPatientId(userId);
        patientMeasurementToolsDetails.setMeasurementToolsValue(value);
        patientMeasurementToolsDetails.setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));*/

        String jsonString = gson.toJson(patientMedicationData);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest updateStatus = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(AppController.TAG, response.toString());
                        //pDialog.dismiss();
                        try {
                            if (response.getString("status").equalsIgnoreCase("success")) {
                                //Toast.makeText(mActivity,"Uploaded Successfully",Toast.LENGTH_SHORT).show();
                                //values.setText("");
                                getPatientMedRemainderList(BaseURL.getMedicineRemainders, patientId, 0);
                            } else if (response.getString("status").equalsIgnoreCase("failure")) {
                                Toast.makeText(mActivity,"Cannot connect to AmWell. Please try later..",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AppController.TAG, "Error: " + error.getMessage());
                Toast.makeText(mActivity,
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
        AppController.getInstance().addToRequestQueue(updateStatus, TAB_STATUS_CHANGE);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("med_switch_status", isChecked);
        editor.commit();
        if (!isChecked){
            //This is for Unread
            getPatientMedRemainderList(BaseURL.getMedicineRemainders, patientId, 0);
            /*Toast.makeText(mActivity, "checked Unread", Toast.LENGTH_SHORT).show();
            textView.setText(getResources().getString(R.string.remainder_unread) + " (" + listSize + " )");*/
        }else{
            //This is for Read
            getPatientMedRemainderList(BaseURL.getMedicineRemainders, patientId, 1);
            /*Toast.makeText(mActivity, "checked Read", Toast.LENGTH_SHORT).show();
            textView.setText(getResources().getString(R.string.remainder_read)+ " (" + listSize+ " )");*/
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        POS = position;
        show_Dialog(POS);
    }

    protected void show_Dialog(final int arg2) {
        LayoutInflater inflater=(LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.med_dialog, null);
        final TextView name=(TextView) view.findViewById(R.id.medNameValue);
        Button btnOkay = (Button )view.findViewById(R.id.btn_ok);
        TextView chemicalValue,startDateTime,endDateTime,intakeType,modeName,interval;

        chemicalValue = (TextView) view.findViewById(R.id.chemicalValue);
        startDateTime = (TextView) view.findViewById(R.id.startDateTime);
        endDateTime= (TextView) view.findViewById(R.id.endDateTime);
        //intakeType= (TextView) view.findViewById(R.id.intakeType);
        modeName= (TextView) view.findViewById(R.id.modeName);
        interval= (TextView) view.findViewById(R.id.interval);

        name.setText(medRemainderList.get(arg2).getMedicine().getMedicineName());
        chemicalValue.setText(medRemainderList.get(arg2).getMedicine().getChemicalName());
        startDateTime.setText(medRemainderList.get(arg2).getFromDate());
        endDateTime.setText(medRemainderList.get(arg2).getToDate());
        //intakeType.setText(medRemainderList.get(arg2).get);
        modeName.setText(medRemainderList.get(arg2).getBeforeAfterFood());
        interval.setText(medRemainderList.get(arg2).getInstruction());

        AlertDialog.Builder ab=new AlertDialog.Builder(mActivity);
        ab.setView(view);
        ab.setCancelable(true);
        btnOkay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                d.dismiss();
                if (medRemainderList.get(arg2).getReadUnread() == 0) {
                    medRemainderList.get(arg2).setReadUnread(1);
                    sendDataToServer(BaseURL.updateMedicineRemainderStatus,medRemainderList.get(arg2));
                }
            }
        });

        d=ab.create();
        //d.setCanceledOnTouchOutside(true);
        d.show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity=activity;
    }
}
