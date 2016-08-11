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
import com.skoruz.amwell.adapter.LabRemainderAdapter;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.patientEntity.SavePdf;
import com.skoruz.amwell.utils.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabRemainderFragment extends Fragment implements CompoundButton.OnCheckedChangeListener,AdapterView.OnItemClickListener{

    private static final String TAG = Remainders.class.getSimpleName();
    ArrayList<SavePdf> pdfRemainderList=new ArrayList<SavePdf>();
    ListView pdf_remainder_list;
    Gson gson=new Gson();
    SharedPreferences sharedPreferences;
    int patientId;
    LabRemainderAdapter adapter;
    SwitchCompat switchCompat;
    TextView textView;
    int POS;
    AlertDialog d ;
    static int listSize;
    private Activity mActivity;
    public LabRemainderFragment() {
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
        View view=inflater.inflate(R.layout.fragment_lab_remainder, container, false);
        sharedPreferences=mActivity.getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        patientId=sharedPreferences.getInt("user_id", 0);
        textView= (TextView) view.findViewById(R.id.labRemaindersText);
        switchCompat= (SwitchCompat) view.findViewById(R.id.lab_switch_compat);
        switchCompat.setOnCheckedChangeListener(this);
        pdf_remainder_list= (ListView) view.findViewById(R.id.lab_remainder_list);
        adapter=new LabRemainderAdapter(pdfRemainderList,mActivity);
        pdf_remainder_list.setAdapter(adapter);
        pdf_remainder_list.setOnItemClickListener(this);
        if(switchCompat.isChecked()){
            getPatientLabRemainderList(BaseURL.getLabRemainders, patientId, 1);
        }else {
            getPatientLabRemainderList(BaseURL.getLabRemainders, patientId, 0);
        }
        return view;
    }

    public void getPatientLabRemainderList(String url,int userId, final int isRead){
        // Tag used to cancel the request
        String TAB_LAB_REMAINDER_GET = "labRemainderGet";
        pdfRemainderList.clear();
        url=url+userId+"/"+isRead;

        JsonArrayRequest getLabRemainder=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        SavePdf savePdf=gson.fromJson(jsonObject.toString(), SavePdf.class);
                        pdfRemainderList.add(savePdf);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                listSize=pdfRemainderList.size();
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
        AppController.getInstance().addToRequestQueue(getLabRemainder, TAB_LAB_REMAINDER_GET);
    }

    public void sendDataToServer(String url,SavePdf savePdf) {
        // Tag used to cancel the request
        String TAB_LAB_STATUS_CHANGE = "labStatusChange";
        /*pDialog = new ProgressDialog(mActivity);
        pDialog.setMessage("Saving...");
        pDialog.show();*/

        Gson gson = new Gson();

        String jsonString = gson.toJson(savePdf);
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
                                getPatientLabRemainderList(BaseURL.getLabRemainders, patientId, 0);
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
        AppController.getInstance().addToRequestQueue(updateStatus, TAB_LAB_STATUS_CHANGE);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked){
            //This is for Unread
            getPatientLabRemainderList(BaseURL.getLabRemainders, patientId, 0);
        }else{
            //This is for Read
            getPatientLabRemainderList(BaseURL.getLabRemainders, patientId, 1);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        POS = position;
        show_Dialog(POS);
    }

    protected void show_Dialog(final int arg2) {
        LayoutInflater inflater=(LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.lab_dialog, null);
        Button btnOkay = (Button )view.findViewById(R.id.btn_ok);
        TextView fileNameValue,uploadedDateValue;

        fileNameValue = (TextView) view.findViewById(R.id.fileNameValue);
        uploadedDateValue = (TextView) view.findViewById(R.id.uploadedDateValue);

        fileNameValue.setText(pdfRemainderList.get(arg2).getFileName());
        uploadedDateValue.setText(pdfRemainderList.get(arg2).getUploadDate());

        AlertDialog.Builder ab=new AlertDialog.Builder(mActivity);
        ab.setView(view);
        ab.setCancelable(true);
        btnOkay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                d.dismiss();
                if (pdfRemainderList.get(arg2).getReadUnread() == 0) {
                    pdfRemainderList.get(arg2).setReadUnread(1);
                    sendDataToServer(BaseURL.updateLabRemainderStatus,pdfRemainderList.get(arg2));
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
