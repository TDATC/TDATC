package com.skoruz.amwell.patient;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.google.gson.Gson;
import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.CustomListToolAdapter;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.constants.Constants;
import com.skoruz.amwell.patientEntity.MeasurementToolOldData;
import com.skoruz.amwell.patientEntity.PatientMeasurementToolsDetails;
import com.skoruz.amwell.utils.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UploadValue extends AppCompatActivity {

    // Log tag
    private static final String TAG = UploadValue.class.getSimpleName();
    TextView toolNameText;
    EditText values;
    Button uploadValue;
    ListView oldRecordList;
    String toolName=null;
    int tool_id,patientId;
    SharedPreferences sharedPreferences;
    LinearLayout pastRecordLayout;
    private ProgressDialog pDialog;
    ArrayList<MeasurementToolOldData> patientMeasurementToolsDetails=new ArrayList<MeasurementToolOldData>();
    CustomListToolAdapter adapter;
    String[] measurementTools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_value);

        measurementTools=getResources().getStringArray(R.array.measurementTools);

        sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        patientId=sharedPreferences.getInt("user_id", 0);

        toolNameText= (TextView) findViewById(R.id.tool_Name);
        values= (EditText) findViewById(R.id.value);
        uploadValue= (Button) findViewById(R.id.upload_value);

        oldRecordList= (ListView) findViewById(R.id.oldRecord_list);

        adapter=new CustomListToolAdapter(this,patientMeasurementToolsDetails);
        oldRecordList.setAdapter(adapter);

        pastRecordLayout= (LinearLayout) findViewById(R.id.pastRecords_layout);
        Intent getData=getIntent();
        toolName=getData.getExtras().getString("toolName");
        tool_id=getData.getExtras().getInt("toolId");

        getDataFromServer(BaseURL.valueGetUrl,tool_id,patientId);

        toolNameText.setText(toolName);

        uploadValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(values.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(),"Enter Value for " + toolName,Toast.LENGTH_SHORT).show();
                }else {
                    sendDataToServer(BaseURL.valueUploadUrl, tool_id, values.getText().toString(),patientId);
                }
            }
        });

        oldRecordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int toolPKId=patientMeasurementToolsDetails.get(position).getId();
                //Toast.makeText(getApplicationContext(),String.valueOf(patientMeasurementToolsDetails.get(position).getId()),Toast.LENGTH_SHORT).show();
                AlertDialog.Builder healthPlanDialog = new AlertDialog.Builder(UploadValue.this);
                healthPlanDialog.setItems(Constants.options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        //healthPlan.setText(Constants.healthPlan[position]);
                        switch (position){
                            case 0:
                                deleteOldRecord(BaseURL.valueDeleteUrl,toolPKId);
                                break;
                            case 1:
                                break;
                            case 2:
                                Intent lineGraph=new Intent(UploadValue.this,LineGraph.class);
                                Bundle bundle=new Bundle();
                                String toolName=measurementTools[tool_id];
                                bundle.putString("toolName",toolName);
                                bundle.putParcelableArrayList("toolList",patientMeasurementToolsDetails);
                                lineGraph.putExtras(bundle);
                                startActivity(lineGraph);
                                break;
                            default:
                                break;
                        }
                    }
                });
                AlertDialog healthPlanAlertDialog=healthPlanDialog.create();
                healthPlanAlertDialog.show();
            }
        });
    }

    public void deleteOldRecord(String url,int id){

        String TAB_DELETE = "delete";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE,
                url + "/"+ id,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(AppController.TAG, response.toString());
                        try{
                            if (response.getString("status").equalsIgnoreCase("success")) {
                                getDataFromServer(BaseURL.valueGetUrl, tool_id, patientId);
                            } else if (response.getString("status").equalsIgnoreCase("failure")) {
                                Toast.makeText(getApplicationContext(),
                                        "Cannot connect to AmWell. Please try later..",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AppController.TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Cannot connect to AmWell. Please try later..",
                        Toast.LENGTH_SHORT).show();
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
        AppController.getInstance().addToRequestQueue(jsonObjReq, TAB_DELETE);
    }

    public void sendDataToServer(String url,int toolId,String value,int userId) {
        // Tag used to cancel the request
        String TAB_VALUE_UPLOAD = "valueUpload";
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Saving...");
        pDialog.show();

        Gson gson = new Gson();

        PatientMeasurementToolsDetails patientMeasurementToolsDetails = new PatientMeasurementToolsDetails();

        patientMeasurementToolsDetails.setMeasurementToolId(toolId+1);
        patientMeasurementToolsDetails.setPatientId(userId);
        patientMeasurementToolsDetails.setMeasurementToolsValue(value);
        patientMeasurementToolsDetails.setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        String jsonString = gson.toJson(patientMeasurementToolsDetails);
        JSONObject jsonObject = null;
        try {
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
                                Toast.makeText(getApplicationContext(),"Uploaded Successfully",Toast.LENGTH_SHORT).show();
                                values.setText("");
                                getDataFromServer(BaseURL.valueGetUrl, tool_id, patientId);
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
        AppController.getInstance().addToRequestQueue(uploadToolValue, TAB_VALUE_UPLOAD);
    }

    public void getDataFromServer(String url,int toolId,int userId){
        // Tag used to cancel the request
        String TAB_VALUE_GET = "valueGet";
        toolId=toolId+1;

        patientMeasurementToolsDetails.clear();
        JsonArrayRequest getToolValue=new JsonArrayRequest(url+ toolId + "/" + userId, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        MeasurementToolOldData measurementToolOldData=new MeasurementToolOldData();
                        measurementToolOldData.setId(jsonObject.getInt("id"));
                        measurementToolOldData.setToolId(jsonObject.getInt("patientId"));
                        measurementToolOldData.setToolName(measurementTools[jsonObject.getInt("measurementToolId") - 1]);
                        measurementToolOldData.setUploadedValue(jsonObject.getString("measurementToolsValue"));
                        Date uploadDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(jsonObject.getString("dateTime"));

                        measurementToolOldData.setUploadedDate(new SimpleDateFormat("MMM dd, yyyy hh:mm a").format(uploadDate));

                        patientMeasurementToolsDetails.add(measurementToolOldData);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(patientMeasurementToolsDetails.size()>0){
                pastRecordLayout.setVisibility(View.VISIBLE);}else{pastRecordLayout.setVisibility(View.GONE);}
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
                /*Toast.makeText(getApplicationContext(),
                        "Cannot connect to AmWell. Please try later..",
                        Toast.LENGTH_SHORT).show();*/
                /*Need to handle the error...custom implementation should be done. */
                if(patientMeasurementToolsDetails.size()>0){
                    pastRecordLayout.setVisibility(View.VISIBLE);}else{pastRecordLayout.setVisibility(View.GONE);}
                adapter.notifyDataSetChanged();
            }
        });

        getToolValue.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(getToolValue, TAB_VALUE_GET);
    }
}
