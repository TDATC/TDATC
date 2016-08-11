package com.skoruz.amwell.patient;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyLog;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.ClinicListAdapter;
import com.skoruz.amwell.common.ActionBarListFragment;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.patient.appointment.AppointmentBookActivity;
import com.skoruz.amwell.patient.appointment.BookAppointmentActivity;
import com.skoruz.amwell.physicianEntity.Clinic;
import com.skoruz.amwell.physicianEntity.PhysicianDetails;
import com.skoruz.amwell.ui.BezelImageView;
import com.skoruz.amwell.ui.materialdesign.MaterialProgressDialog;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDoctorsFragment extends ActionBarListFragment implements AbsListView.OnScrollListener {

    public static MyDoctorsFragment mInstance;
    String idList;
    private boolean isEditMode;
    private boolean isHeaderVieVisible = false;
    private boolean isLoading;
    private boolean isShowAllDoctors;
    private MyDoctorAdapter mAdapter;
    private Context mApplicationContext;
    private int mCurrentScrollState;
    private SharedPreferences.Editor mEditor;
    private ErrorListener mErrorListener;
    private View mHeaderView;
    private ImageLoader mImageFetcher;
    private boolean[] mIsMarkedForDelete;
    /*private LoadMoreQueryHandler mLoadMoreQueryHandler;*/
    private MaterialProgressDialog mProgressDialog;
    private String[] mProjection;
    private int mQueryOffset;
    private View mRootView;
    private String mSelection;
    private String[] mSelectionArgs;
    private SharedPreferences mSharedPreferences;
    private String mSortOrder;
    private Response.Listener<JSONObject> mVerificationListener;
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    private View.OnClickListener onItemClickListener;
    private ArrayList<PhysicianDetails> myPhysicianDetailsList;
    private static final String TAG=MyDoctorsFragment.class.getSimpleName();
    private Gson gson=new Gson();
    private int patientId;
    private Toast mToast;
    public static final String MON="Mon";
    public static final String TUE="Tue";
    public static final String WED="Wed";
    public static final String THU="Thu";
    public static final String FRI="Fri";
    public static final String SAT="Sat";
    public static final String SUN="Sun";

    public MyDoctorsFragment(){
        this.onItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n=MyDoctorsFragment.this.getListView().getPositionForView(v);
                if (n==-1 || MyDoctorsFragment.this.mAdapter==null) return;
                {
                    final PhysicianDetails physicianDetails=MyDoctorsFragment.this.getDoctorBundle(n).getParcelable("bundle_doctor");
                    final Bundle bundle=new Bundle();
                    bundle.putParcelable("bundle_doctor", physicianDetails);
                    switch (v.getId()){
                        default:
                            return;
                        case R.id.book_appointment:
                            /*if (!Utils.isActivityAlive(MyDoctorsFragment.this.getActionBarActivity())) return;
                            Intent intent = new Intent(MyDoctorsFragment.this.getActionBarActivity(),BookAppointmentActivity.class);
                            intent.putExtras(bundle);
                            MyDoctorsFragment.this.getActionBarActivity().startActivity(intent);*/
                            if (physicianDetails.getClinic().size()>0){
                                new MaterialDialog.Builder(MyDoctorsFragment.this.getActionBarActivity())
                                        .title(R.string.select_clinic)
                                        .adapter(new ClinicListAdapter(getActivity().getApplicationContext(), myPhysicianDetailsList.get(n).getClinic()),
                                                new MaterialDialog.ListCallback() {
                                                    @Override
                                                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                                        showToast("Clicked item " + which);
                                                        if (!Utils.isActivityAlive(MyDoctorsFragment.this.getActionBarActivity()))
                                                            return;
                                                        bundle.putInt("clinic_position", which);
                                                    /*Intent intent = new Intent(MyDoctorsFragment.this.getActionBarActivity(), BookAppointmentActivity.class);*/
                                                        Intent intent = new Intent(MyDoctorsFragment.this.getActionBarActivity(), AppointmentBookActivity.class);
                                                        intent.putExtras(bundle);
                                                        MyDoctorsFragment.this.getActionBarActivity().startActivity(intent);
                                                        dialog.dismiss();
                                                    }
                                                })
                                        .show();
                            }else {
                                showToast("No Clinic to book appointment");
                            }
                            return;
                    }
                }
            }
        };
        this.onCheckedChangeListener= new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                int n = MyDoctorsFragment.this.getListView().getPositionForView(compoundButton);
                if (n != -1 && MyDoctorsFragment.this.mAdapter != null) {
                    if (MyDoctorsFragment.this.mIsMarkedForDelete != null) {
                        MyDoctorsFragment.this.mIsMarkedForDelete[n] = isChecked;
                    }
                }
            }
        };

        this.mVerificationListener=new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject!=null && !TextUtils.isEmpty(jsonObject.getString("status")) && jsonObject.getString("status").equalsIgnoreCase("success")){
                        if (Utils.isActivityAlive(MyDoctorsFragment.this.getActionBarActivity()) && MyDoctorsFragment.this.mProgressDialog != null && MyDoctorsFragment.this.mProgressDialog.isShowing()) {
                            MyDoctorsFragment.this.mProgressDialog.dismiss();
                            getMyPhysicianList(BaseURL.getMyPhysicians, patientId);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        this.mErrorListener=new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("MyDoctorsFragment","Failure in deleting doctors" + volleyError.toString());
                if (Utils.isActivityAlive(MyDoctorsFragment.this.getActionBarActivity()) && MyDoctorsFragment.this.mProgressDialog != null && MyDoctorsFragment.this.mProgressDialog.isShowing()) {
                    MyDoctorsFragment.this.mProgressDialog.dismiss();
                }
            }
        };
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

    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(MyDoctorsFragment.this.getActionBarActivity(), message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    private void initCache() {
        this.mImageFetcher = AppController.getInstance().getImageLoader();
    }

    public static MyDoctorsFragment newInstance(Bundle bundle) {
        mInstance = new MyDoctorsFragment();
        mInstance.setArguments(bundle);
        return mInstance;
    }

    public void clearCheckBoxes() {
        for (int i = 0; i < this.mIsMarkedForDelete.length; ++i) {
            this.mIsMarkedForDelete[i] = false;
        }
        this.idList = "";
    }

    public boolean isSomeDoctorChecked() {
        for (boolean b:mIsMarkedForDelete) {
            if (!b) continue;
            return true;
        }
        return false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (Utils.isActivityAlive(this.getActivity())) {
            this.mAdapter=new MyDoctorAdapter(this.getActivity().getApplicationContext(),myPhysicianDetailsList);
            if (!this.isShowAllDoctors) {
                //this.getListView().addHeaderView(this.mHeaderView, this.mRootView, true);
            }
            this.setListAdapter(this.mAdapter);
            if (this.isShowAllDoctors) {
                this.getListView().setOnScrollListener((AbsListView.OnScrollListener)this);
                this.mProgressDialog = new MaterialProgressDialog(this.getActionBarActivity());
                this.mProgressDialog.setMessage(this.getString(R.string.loading));
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utils.isActivityAlive((Activity) this.getActivity())) {
            mSharedPreferences=getActivity().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
            this.mEditor = this.mSharedPreferences.edit();
            this.mApplicationContext = this.getActivity().getApplicationContext();
        }
        this.isShowAllDoctors = this.getArguments().getBoolean("bundle_show_all_doctors", true);
        //super.initCache();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        this.mRootView=inflater.inflate(R.layout.fragment_my_doctors, container, false);
        patientId=mSharedPreferences.getInt("user_id", 0);
        myPhysicianDetailsList=new ArrayList<>();
        //getMyPhysicianList(BaseURL.getMyPhysicians,patientId);
        /*if (!this.isShowAllDoctors) {
            this.mHeaderView = inflater.inflate(2130903238, null, false);
            this.mHeaderView.setVisibility(View.GONE);
        }*/
        return mRootView;
    }

    public void toggleEditMode(boolean bl) {
        this.isEditMode = bl;
        if (this.isEditMode) {
            this.clearCheckBoxes();
        }
        this.mAdapter.notifyDataSetChanged();
    }

    public void removeDoctors() {
        final ArrayMap<String,String> arrayMap=new ArrayMap<String,String>();
        this.idList="";
        for (int i=0;i<this.mIsMarkedForDelete.length;i++){
            if (!mIsMarkedForDelete[i]) continue;
            PhysicianDetails physicianDetails= (PhysicianDetails) this.mAdapter.getItem(i);
            if (physicianDetails==null)return;
            {
                String string=Integer.toString(physicianDetails.getPhysician_id());
                this.idList=this.idList + string + ",";
                continue;
            }
        }
        if (TextUtils.isEmpty(this.idList))return;
        this.idList=this.idList.substring(0,-1+this.idList.length());
        arrayMap.put("doctors_to_delete", this.idList.trim());
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.POST, BaseURL.deleteMappedDoctors + patientId, new JSONObject(arrayMap), mVerificationListener, mErrorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            /*@Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (arrayMap != null) {
                    return arrayMap;
                }
                return super.getParams();
            }*/
        }, TAG);
        if (!Utils.isActivityAlive((Activity)this.getActionBarActivity()) || this.mProgressDialog == null || this.mProgressDialog.isShowing()) {
            return;
        }
        this.mProgressDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMyPhysicianList(BaseURL.getMyPhysicians, patientId);
    }

    public void getMyPhysicianList(String url,int patientId){
        myPhysicianDetailsList.clear();
        String TAB_MAPPED_PHYSICIAN_GET = "mappedPhysicianGet";
        JsonArrayRequest mappedPhysician=new JsonArrayRequest(url + patientId, new Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        PhysicianDetails physicianDetails = gson.fromJson(jsonObject.toString(), PhysicianDetails.class);
                        myPhysicianDetailsList.add(physicianDetails);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                mIsMarkedForDelete = new boolean[myPhysicianDetailsList.size()];
                mAdapter.notifyDataSetChanged();
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
            }
        });

        mappedPhysician.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(mappedPhysician, TAB_MAPPED_PHYSICIAN_GET);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        boolean bl=true;
        Bundle bundle = getDoctorBundle(position);
        if (!this.isEditMode){
            if (Utils.isActivityAlive(this.getActivity())){
                /*Intent intent=new Intent(this.getActivity(),DoctorProfileActivity.class);
                bundle.putBoolean("bundle_is_from_omni_search", bl);
                intent.putExtras(bundle);
                this.startActivity(intent);*/
            }
            return;
        }
        CheckBox checkBox= (CheckBox) v.findViewById(R.id.delete_doctor);
        if (this.mIsMarkedForDelete[position]){
            bl=false;
        }
        checkBox.setChecked(bl);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private class MyDoctorAdapter extends BaseAdapter{
        private Context mContext;
        private LayoutInflater mLayoutInflater;
        private ArrayList<PhysicianDetails> physicianDetailsList;

        public MyDoctorAdapter(Context context,ArrayList<PhysicianDetails> physicianDetailsList){
            this.physicianDetailsList=physicianDetailsList;
            this.mContext=context;
            this.mLayoutInflater= (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                viewHolder=new ViewHolder();
                convertView=mLayoutInflater.inflate(R.layout.item_mydoctor_row,parent,false);

                viewHolder.mDoctorDelete = (CheckBox)convertView.findViewById(R.id.delete_doctor);
                viewHolder.mDoctorImage = (BezelImageView)convertView.findViewById(R.id.doctor_image);
                viewHolder.mDoctorName = (TextView)convertView.findViewById(R.id.doctor_name);
                viewHolder.mDoctorSpeciality = (TextView)convertView.findViewById(R.id.doctor_speciality);
                viewHolder.mDoctorClinic = (TextView)convertView.findViewById(R.id.doctor_clinic_name);
                viewHolder.mBookButton = (Button)convertView.findViewById(R.id.book_appointment);
                viewHolder.mDividerView = convertView.findViewById(R.id.my_doctor_divider);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            if(physicianDetailsList.size()>0) {
                PhysicianDetails physicianDetails = physicianDetailsList.get(position);
                if (physicianDetails != null) {
                    viewHolder.mDoctorName.setText("Dr " + physicianDetails.getUser().getFirstName() + " " + physicianDetails.getUser().getLastName());
                    if(physicianDetails.getSpecializations()!=null) {
                        viewHolder.mDoctorSpeciality.setText(physicianDetails.getSpecializations().getSpecializations());
                    }
                    if(physicianDetails.getClinic().size()>0) {
                        if (physicianDetails.getClinic() != null) {
                            List<Clinic> clinics = new ArrayList<>(physicianDetails.getClinic());
                            viewHolder.mDoctorClinic.setText(clinics.get(0).getClinicName());
                        }
                    }
                }
            }
            viewHolder.mDoctorDelete.setOnCheckedChangeListener(null);
            if (MyDoctorsFragment.this.isEditMode){
                viewHolder.mDoctorDelete.setVisibility(View.VISIBLE);
                viewHolder.mBookButton.setVisibility(View.GONE);
                if (MyDoctorsFragment.this.mIsMarkedForDelete!=null){
                    viewHolder.mDoctorDelete.setChecked(MyDoctorsFragment.this.mIsMarkedForDelete[position]);
                }
                if (position== -1+ MyDoctorsFragment.this.mAdapter.getCount()){
                    viewHolder.mDividerView.setVisibility(View.GONE);
                }else {
                    viewHolder.mDividerView.setVisibility(View.VISIBLE);
                }
            }else{
                viewHolder.mDoctorDelete.setVisibility(View.GONE);
                viewHolder.mDoctorDelete.setChecked(false);
                viewHolder.mBookButton.setVisibility(View.VISIBLE);
                View view=viewHolder.mDividerView;
                int n2=position== -1+ MyDoctorsFragment.this.mAdapter.getCount()?View.GONE:View.VISIBLE;
                view.setVisibility(n2);
            }
            viewHolder.mDoctorDelete.setOnCheckedChangeListener(MyDoctorsFragment.this.onCheckedChangeListener);
            viewHolder.mBookButton.setOnClickListener(MyDoctorsFragment.this.onItemClickListener);
            return convertView;
        }
    }

    private static class ViewHolder{
        Button mBookButton;
        View mDividerView;
        TextView mDoctorClinic;
        CheckBox mDoctorDelete;
        BezelImageView mDoctorImage;
        TextView mDoctorName;
        TextView mDoctorSpeciality;

        private ViewHolder() {
        }
    }
}
