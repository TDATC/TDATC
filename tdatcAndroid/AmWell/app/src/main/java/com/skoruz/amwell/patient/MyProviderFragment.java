package com.skoruz.amwell.patient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
import com.skoruz.amwell.common.ActionBarListFragment;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.physicianEntity.Clinic;
import com.skoruz.amwell.physicianEntity.PhysicianDetails;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyProviderFragment extends ActionBarListFragment implements AbsListView.OnScrollListener{

    private static final String TAG = UploadValue.class.getSimpleName();
    private ListView recentProvider_list;
    private PhysicianListAdapter adapter;
    private ArrayList<PhysicianDetails> allPhysicianDetailsList;
    private Gson gson=new Gson();
    private View.OnClickListener onClickListener;
    private SharedPreferences sharedPreferences;
    private int patientId;
    private ProgressDialog pDialog;
    private String doctorSpecialization;

    public MyProviderFragment(){
        onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n=MyProviderFragment.this.getListView().getPositionForView(v);
                if (n==-1 || MyProviderFragment.this.adapter==null) return;
                {
                    final PhysicianDetails physicianDetails=MyProviderFragment.this.getDoctorBundle(n).getParcelable("bundle_doctor");
                    switch (v.getId()){
                        case R.id.book_appointment:
                            Toast.makeText(MyProviderFragment.this.getActionBarActivity(),"Appointment Button",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.doctor_layout:
                            Toast.makeText(MyProviderFragment.this.getActionBarActivity(),"Layout",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.add_preference:
                            Toast.makeText(MyProviderFragment.this.getActionBarActivity(), "Preference Button", Toast.LENGTH_SHORT).show();
                            new MaterialDialog.Builder(MyProviderFragment.this.getActionBarActivity())
                                    .title(R.string.preferred_doctor)
                                    .content(R.string.preferred_doctor_msg)
                                    .positiveText(R.string.string_ok)
                                    .negativeText(R.string.string_cancel)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                            Toast.makeText(MyProviderFragment.this.getActionBarActivity(), "Positive Button", Toast.LENGTH_SHORT).show();
                                            addToPreferredList(BaseURL.addPreferredDoctor,physicianDetails.getPhysician_id());
                                        }
                                    })
                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                            Toast.makeText(MyProviderFragment.this.getActionBarActivity(), "Negative Button", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .show();
                        default:
                            break;
                    }
                }
            }
        };
    }

    private void addToPreferredList(String url,int physicianId){

        JsonObjectRequest addPatientPhysician=new JsonObjectRequest(Request.Method.POST, url +patientId + "/" + physicianId,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equalsIgnoreCase("success")){
                        getPhysicianList(BaseURL.getAllPhysicians,patientId);
                    }else if (response.getString("status").equalsIgnoreCase("failure")){
                        Toast.makeText(MyProviderFragment.this.getActionBarActivity(), "Some", Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };

        addPatientPhysician.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(addPatientPhysician,TAG);
    }

    private Bundle getDoctorBundle(int n) {
        FragmentActivity fragmentActivity;
        PhysicianDetails cursor = (PhysicianDetails) this.getListView().getAdapter().getItem(n);
        PhysicianDetails doctor = new PhysicianDetails();
        doctor.setPhysician_id(cursor.getPhysician_id());
        doctor.setUser(cursor.getUser());
        doctor.setSpecializations(cursor.getSpecializations());
        doctor.setClinic(cursor.getClinic());
        doctor.setLocation(cursor.getLocation());
        Bundle bundle = new Bundle();
        bundle.putParcelable("bundle_doctor", (Parcelable)doctor);
        //bundle.putParcelable("bundle_clinic",cursor.getClinic().get(n));
        /*String string3 = cursor.getString(cursor.getColumnIndex("practice_city"));
        if (Utils.isEmptyString(string3)) {
            string3 = this.mSharedPreferences.getString("selected_city_name", "bangalore");
        }
        if (Utils.isActivityAlive((Activity)(fragmentActivity = this.getActivity()))) {
            bundle.putString("bundle_currency", new GeoLocation((Context)fragmentActivity).getCurrencyForCity(string3));
        }*/
        return bundle;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (Utils.isActivityAlive(this.getActivity())) {
            this.getListView().setOnScrollListener((AbsListView.OnScrollListener)this);
            this.adapter=new PhysicianListAdapter(this.getActivity(),allPhysicianDetailsList);
            this.setListAdapter(this.adapter);
        }
    }

    public static MyProviderFragment newInstance(Bundle paramBundle)
    {
        MyProviderFragment myProviderFragment = new MyProviderFragment();
        myProviderFragment.setArguments(paramBundle);
        return myProviderFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            doctorSpecialization=getArguments().getString("specialization");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my_provider,container,false);
        sharedPreferences=getActivity().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        patientId=sharedPreferences.getInt("user_id", 0);
        //recentProvider_list= (ListView) view.findViewById(R.id.allProvider);
        allPhysicianDetailsList=new ArrayList<PhysicianDetails>();
        //getPhysicianList(BaseURL.getAllPhysicians,patientId);
        getPhysicianBySpecialization(BaseURL.getPhysicianBySpeciality,patientId,doctorSpecialization);

        //physicianDetailsList=new ArrayList<PhysicianDetails>();
        //adapter=new PhysicianListAdapter(getActivity(),allPhysicianDetailsList);
        //recentProvider_list.setAdapter(adapter);

        return view;
    }

    public void getPhysicianBySpecialization(String url,int patientId,String speciality){

        pDialog = new ProgressDialog(MyProviderFragment.this.getActionBarActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        allPhysicianDetailsList.clear();
        JsonArrayRequest getAllPhysicians=new JsonArrayRequest(url+patientId+"/"+speciality, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                if (jsonArray.length() > 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            PhysicianDetails physicianDetails = gson.fromJson(jsonObject.toString(), PhysicianDetails.class);
                            allPhysicianDetailsList.add(physicianDetails);
                            pDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                adapter.notifyDataSetChanged();
            }
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pDialog.dismiss();
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
            }
        });

        getAllPhysicians.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(getAllPhysicians, TAG);
    }

    public void getPhysicianList(String url,int patientId){

        pDialog = new ProgressDialog(MyProviderFragment.this.getActionBarActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        allPhysicianDetailsList.clear();
        JsonArrayRequest getAllPhysicians=new JsonArrayRequest(url+patientId, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        PhysicianDetails physicianDetails=gson.fromJson(jsonObject.toString(), PhysicianDetails.class);
                        allPhysicianDetailsList.add(physicianDetails);
                        pDialog.dismiss();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pDialog.dismiss();
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
            }
        });

        getAllPhysicians.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(getAllPhysicians, TAG);
    }

    @Override
    public void onStop() {
        super.onStop();
        AppController.getInstance().cancelPendingRequests(TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public class PhysicianListAdapter extends BaseAdapter{
        private Activity activity;
        private LayoutInflater inflater;
        ArrayList<PhysicianDetails> physicianDetailsList;

        public PhysicianListAdapter(Activity activity,ArrayList<PhysicianDetails> physicianDetailsList){
            this.physicianDetailsList=physicianDetailsList;
            this.activity=activity;
        }

        @Override
        public int getCount() {
            return physicianDetailsList.size();
        }

        @Override
        public Object getItem(int position) {
            return physicianDetailsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                inflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView=inflater.inflate(R.layout.item_search_result_list,parent,false);

                viewHolder=new ViewHolder();
                viewHolder.doctorLayout= (LinearLayout) convertView.findViewById(R.id.doctor_layout);
                viewHolder.docName= (TextView) convertView.findViewById(R.id.doctor_name);
                viewHolder.availability= (TextView) convertView.findViewById(R.id.available_today);
                viewHolder.doc_spec= (TextView) convertView.findViewById(R.id.doctor_speciality);
                viewHolder.clinic_name= (TextView) convertView.findViewById(R.id.clinic_name);
                viewHolder.clinic_locality= (TextView) convertView.findViewById(R.id.clinic_locality);
                viewHolder.doc_exp= (TextView) convertView.findViewById(R.id.doctor_experience);
                viewHolder.doc_fees= (TextView) convertView.findViewById(R.id.doctor_fees);
                viewHolder.book_appoint= (Button) convertView.findViewById(R.id.book_appointment);
                viewHolder.add_prefer= (Button) convertView.findViewById(R.id.add_preference);

                viewHolder.doctorLayout.setOnClickListener(MyProviderFragment.this.onClickListener);
                viewHolder.book_appoint.setOnClickListener(MyProviderFragment.this.onClickListener);
                viewHolder.add_prefer.setOnClickListener(MyProviderFragment.this.onClickListener);

                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            if(physicianDetailsList.size()>0) {
                PhysicianDetails physicianDetails = physicianDetailsList.get(position);
                if (physicianDetails != null) {
                    viewHolder.docName.setText("Dr " + physicianDetails.getUser().getFirstName() + " " + physicianDetails.getUser().getLastName());
                    if(physicianDetails.getSpecializations()!=null) {
                        viewHolder.doc_spec.setText(physicianDetails.getSpecializations().getSpecializations());
                    }
                    if(physicianDetails.getClinic().size()>0) {
                        if (physicianDetails.getClinic() != null) {
                            List<Clinic> clinics = new ArrayList<>(physicianDetails.getClinic());
                            viewHolder.clinic_name.setText(clinics.get(0).getClinicName());
                            viewHolder.doc_fees.setText(this.activity.getResources().getString(R.string.Rs) + String.valueOf(clinics.get(0).getConsultationFees()));
                            viewHolder.clinic_locality.setText(clinics.get(0).getCity());
                        }
                    }
                    viewHolder.doc_exp.setText(String.valueOf(physicianDetails.getExperienceInYear()) + " yrs exp.");
                }
            }

            return convertView;
        }
    }

    private static class ViewHolder{
        LinearLayout doctorLayout;
        TextView docName,availability,doc_spec,clinic_name,clinic_locality,doc_exp,doc_fees;
        Button book_appoint,add_prefer;
    }
}
