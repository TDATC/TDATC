package com.skoruz.amwell.patient;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.google.gson.Gson;
import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.CustomAdapter;
import com.skoruz.amwell.adapter.PhysicianListAdapter;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.physicianEntity.PhysicianDetails;
import com.skoruz.amwell.utils.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorTypeList extends Fragment implements AdapterView.OnItemClickListener{

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView general_doc_list;
    private String [] titles;
    private TypedArray iconList;
    private EditText doc_search;
    private View mView;
    private static final String TAG=DoctorTypeList.class.getSimpleName();
    private PhysicianListAdapter adapter;
    private ArrayList<PhysicianDetails> allPhysicianDetailsList;
    private Gson gson=new Gson();
    private CustomAdapter customAdapter;
    private SharedPreferences sharedPreferences;
    private int patientId;
    private ListItem listItem[];
    public DoctorTypeList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_doctor_type_list, container, false);
        initView();
        sharedPreferences=getActivity().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        patientId=sharedPreferences.getInt("user_id", 0);
        getPhysicianList(BaseURL.getAllPhysicians, patientId);
        return mView;
    }

    private void initView(){
        allPhysicianDetailsList=new ArrayList<>();
        doc_search= (EditText) mView.findViewById(R.id.doc_search);
        doc_search.addTextChangedListener(new MyTextWatcher(doc_search));
        general_doc_list= (ListView) mView.findViewById(R.id.general_doc_list);
        general_doc_list.setOnItemClickListener(this);
        iconList=getResources().obtainTypedArray(R.array.doctor_icons);
        titles=getResources().getStringArray(R.array.specialization);
        int iconSize=iconList.length();
        listItem=new ListItem[iconSize];
        for (int i=0;i<iconSize;i++){
            listItem[i]=new ListItem(iconList.getResourceId(i, 0),titles[i]);
        }
        iconList.recycle();
        customAdapter=new CustomAdapter(getActivity(),R.layout.custom_view,listItem,allPhysicianDetailsList);
        general_doc_list.setAdapter(customAdapter);
        general_doc_list.setTextFilterEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getContext(),DoctorList.class);
        intent.putExtra("specialization",titles[position]);
        startActivity(intent);
    }

    public class MyTextWatcher implements TextWatcher{

        private View view;

        public MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            customAdapter=new CustomAdapter(getActivity(),R.layout.custom_view,listItem,allPhysicianDetailsList);
            if (customAdapter!=null){
                customAdapter.getFilter().filter(s);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()){
                case R.id.doc_search:
                    comparingString(doc_search.getText().toString());
                    break;
                default:
                    break;
            }
        }
    }

    public void comparingString(String s){
        ArrayList<PhysicianDetails> details=new ArrayList<>();
    }

    public void getPhysicianList(String url,int patientId){

        allPhysicianDetailsList.clear();
        JsonArrayRequest getAllPhysicians=new JsonArrayRequest(url+patientId, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        PhysicianDetails physicianDetails=gson.fromJson(jsonObject.toString(), PhysicianDetails.class);
                        allPhysicianDetailsList.add(physicianDetails);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                //adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
            }
        });

        getAllPhysicians.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(getAllPhysicians, TAG);
    }

}
