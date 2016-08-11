package com.skoruz.amwell.physician;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.ui.NetworkImageView;
import com.google.gson.Gson;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.PracticeAdapter;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.patientEntity.PatientDetails;
import com.skoruz.amwell.physicianEntity.Clinic;
import com.skoruz.amwell.physicianEntity.PhysicianDetails;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MyProfileFragment extends BaseFragment implements View.OnClickListener{

    private LinearLayout mContentLl;
    private RelativeLayout mProgressRl;
    private ListView mListView;
    private PracticeAdapter mPracticeAdapter;
    private View mFooterView;
    private View mHeaderView;

    private NetworkImageView mDoctorPhotoIv;

    private View mRootView;
    private TextView mDoctorPrimaryPhoneTv,mDoctorNameTv,mDoctorEmailTv,mDoctorSpecialityTv,mDoctorQualificationTv,mDoctorExperienceTv;
    private SharedPreferences sharedPreferences;
    private PhysicianDetails physicianDetails;
    private Gson gson=new Gson();
    private ArrayList<Clinic> clinicList;

    public static final String DOCTOR_ID = "phyId";
    private static final int REQUEST_EDIT_PRACTICE = 5026;
    private static final int REQUEST_EDIT_DOCTOR = 5025;
    private ImageLoader imageLoader;
    private static final String TAG=MyProfileFragment.class.getSimpleName();
    private String serverUrl= BaseURL.getPhysicianDetailsByPhysicianId;
    private int physician_id;
    private SharedPreferences.Editor editor;

    public static MyProfileFragment newInstance(Bundle args) {
        MyProfileFragment fragment = new MyProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView=inflater.inflate(R.layout.fragment_my_profile, container, false);
        sharedPreferences=getActivity().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        imageLoader= AppController.getInstance().getImageLoader();
        getPhysicianDetails();
        this.mContentLl = ((LinearLayout)mRootView.findViewById(R.id.dashboard_ll_master_content));
        this.mProgressRl = ((RelativeLayout)mRootView.findViewById(R.id.dashboard_rl_content_progress));
        this.mListView = ((ListView)mRootView.findViewById(R.id.dashboard_lv_practice));
        RelativeLayout localRelativeLayout = (RelativeLayout)mRootView.findViewById(R.id.dashboard_rl_content_empty);
        this.mListView.setEmptyView(localRelativeLayout);
        //clinicList=new ArrayList<>(physicianDetails.getClinic());
        //this.mPracticeAdapter=new PracticeAdapter(getActivity(),clinicList,physicianDetails.getUser_id());
        this.mHeaderView=getActivity().getLayoutInflater().inflate(R.layout.list_item_header,null);
        this.mFooterView=getActivity().getLayoutInflater().inflate(R.layout.list_item_empty,null);
        this.mListView.addHeaderView(this.mHeaderView);
        this.mListView.addFooterView(this.mFooterView);
        //this.mListView.setAdapter(this.mPracticeAdapter);
        //initControls();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedPreferences.contains("practice_id")){
            setClinicData();
        }
        initList();
        initListHeader();
        initProfileData();
    }

    private void initList(){
        clinicList=new ArrayList<>(physicianDetails.getClinic());
        this.mPracticeAdapter=new PracticeAdapter(getActivity(),clinicList,physicianDetails.getUser().getUser_id());
        this.mListView.setAdapter(this.mPracticeAdapter);
        this.mPracticeAdapter.notifyDataSetChanged();
    }

    public void getPhysicianDetails(){
        String physicianDetailsJson=sharedPreferences.getString("physician_details",null);
        physicianDetails=gson.fromJson(physicianDetailsJson, PhysicianDetails.class);
        physician_id=physicianDetails.getPhysician_id();
    }

    private void initListHeader(){
        this.mDoctorPhotoIv = ((NetworkImageView)this.mHeaderView.findViewById(R.id.dashboard_iv_doctor_photo));
        this.mDoctorNameTv = (TextView)this.mHeaderView.findViewById(R.id.dashboard_tv_doctor_name);
        this.mDoctorEmailTv = (TextView)this.mHeaderView.findViewById(R.id.dashboard_tv_doctor_primary_email);
        this.mDoctorPrimaryPhoneTv = ((TextView)this.mHeaderView.findViewById(R.id.dashboard_tv_doctor_primary_mobile));
        this.mDoctorSpecialityTv = (TextView)this.mHeaderView.findViewById(R.id.dashboard_tv_doctor_speciality);
        this.mDoctorQualificationTv = (TextView)this.mHeaderView.findViewById(R.id.dashboard_tv_doctor_qualification);
        this.mDoctorExperienceTv = (TextView)this.mHeaderView.findViewById(R.id.dashboard_tv_doctor_experience);
        Button localButton1 = (Button)this.mHeaderView.findViewById(R.id.dashboard_btn_doctor_view);
        Button localButton2 = (Button)this.mHeaderView.findViewById(R.id.dashboard_btn_doctor_edit);
        LinearLayout localLinearLayout1 = (LinearLayout)this.mHeaderView.findViewById(R.id.dashboard_ll_speciality);
        LinearLayout localLinearLayout2 = (LinearLayout)this.mHeaderView.findViewById(R.id.dashboard_ll_degree);
        LinearLayout localLinearLayout3 = (LinearLayout)this.mHeaderView.findViewById(R.id.dashboard_ll_experience);
        Button localButton3 = (Button)this.mFooterView.findViewById(R.id.dashboard_btn_add_practice);
        localButton2.setOnClickListener(this);
        localButton1.setOnClickListener(this);
        localButton3.setOnClickListener(this);
    }

    private void initProfileData(){
        mDoctorNameTv.setText(physicianDetails.getUser().getFirstName()+" "+physicianDetails.getUser().getLastName());
        mDoctorEmailTv.setText(physicianDetails.getUser().getEmailAddress());

        TelephonyManager telephonyManager = (TelephonyManager)getParentActivity().getSystemService(Context.TELEPHONY_SERVICE);
        //GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager.getCellLocation();

        String country=telephonyManager.getSimCountryIso();
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNumber=Utils.parsePhoneNumber(physicianDetails.getUser().getPhonePersonel(),country);
        mDoctorPrimaryPhoneTv.setText(phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164));
        /*try {

            //Phonenumber.PhoneNumber phoneNumber=phoneUtil.parse(physicianDetails.getUser().getPhonePersonel(),country);
            //Toast.makeText(MainActivity.this, phoneNumber.toString(), Toast.LENGTH_SHORT).show();
            System.out.println(PhoneNumberUtil.PhoneNumberFormat.E164);
            System.out.println(phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164));
            //Toast.makeText(getParentActivity(), String.valueOf(PhoneNumberUtil.getInstance().getCountryCodeForRegion(country)), Toast.LENGTH_SHORT).show();
            mDoctorPrimaryPhoneTv.setText(phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //mDoctorPrimaryPhoneTv.setText(physicianDetails.getUser().getPhonePersonel());
        mDoctorSpecialityTv.setText(physicianDetails.getSpecializations().getSpecializations());
        mDoctorQualificationTv.setText(physicianDetails.getQualification().getQualification());
        mDoctorExperienceTv.setText(physicianDetails.getExperienceInYear()+" yrs");
        if (!Utils.isEmptyString(physicianDetails.getUser().getPhotoPath()) && !physicianDetails.getUser().getPhotoPath().equalsIgnoreCase("null")){
            //super.setPicture(Uri.parse(physicianDetails.getUser().getPhotoPath()),mDoctorPhotoIv);
            if (imageLoader==null){
                imageLoader= AppController.getInstance().getImageLoader();
            }
            mDoctorPhotoIv.setImageUrl(physicianDetails.getUser().getPhotoPath(), imageLoader);
        }
    }

   /* private void initControls(){
        mScrollView= (ScrollView) mRootView.findViewById(R.id.scroll_view);
        mDoctorExperienceL1= (LinearLayout) mRootView.findViewById(R.id.doctor_profile_ll_doctor_experience);
        mDoctorFeeL1= (LinearLayout) mRootView.findViewById(R.id.doctor_profile_ll_doctor_fee);
        mDoctorRecommendsL1= (LinearLayout) mRootView.findViewById(R.id.doctor_profile_ll_doctor_recommendation);
        mOtherClinicL1= (LinearLayout) mRootView.findViewById(R.id.doctor_profile_ll_practice_other);
        mPracticeGalleryLl= (LinearLayout) mRootView.findViewById(R.id.doctor_profile_ll_practice_gallery);
        mClinicAddressLl= (LinearLayout) mRootView.findViewById(R.id.doctor_profile_ll_practice_address);

        mDoctorNameTv = ((TextView)this.mRootView.findViewById(R.id.doctor_profile_tv_doctor_name));
        mDoctorSpecialityTv = ((TextView)this.mRootView.findViewById(R.id.doctor_profile_tv_doctor_speciality));
        mDoctorExperienceTv = ((TextView)this.mRootView.findViewById(R.id.doctor_profile_tv_doctor_experience));
        mDoctorQualificationTv = ((TextView)this.mRootView.findViewById(R.id.doctor_profile_tv_doctor_qualification));
        mDoctorRecommendationTv = ((TextView)this.mRootView.findViewById(R.id.doctor_profile_tv_doctor_recommendation));
        mDoctorFeesTv = ((TextView)this.mRootView.findViewById(R.id.doctor_profile_tv_doctor_fee));
        mPrimaryPracticeNameTv = ((TextView)this.mRootView.findViewById(R.id.doctor_profile_tv_practice_primary_name));
        mPrimaryPracticeLocationTv = ((TextView)this.mRootView.findViewById(R.id.doctor_profile_tv_practice_primary_location));
        mClinicAddressTv = ((TextView)this.mRootView.findViewById(R.id.doctor_profile_tv_practice_address));
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dashboard_btn_doctor_edit:
               /* Intent intent=new Intent(getActivity(),EditPhysicianProfileActivity.class);
                startActivity(intent);*/
                startDoctorEditFragment(-1);
                break;
            /*case R.id.dashboard_btn_doctor_view:
                Toast.makeText(v.getContext(), getString(R.string.dashboard_doctor_profile_view_message), Toast.LENGTH_SHORT).show();
                Intent localIntent4 = new Intent(getActivity(), BaseProfileActivity.class);
                startActivity(localIntent4);
                break;*/
            case R.id.dashboard_btn_add_practice:
                startAddPracticeFragment();
                break;
            default:
                break;
        }
    }


    public void startAddPracticeFragment(){
        Intent addPracticeIntent = new Intent(getParentActivity(),BaseEditorActivity.class);
        addPracticeIntent.setAction(BaseEditorActivity.ACTION_PRACTICE_ADD);
        addPracticeIntent.putExtra(DOCTOR_ID,sharedPreferences.getInt("user_id",0));
        startActivityForResult(addPracticeIntent,REQUEST_EDIT_PRACTICE);
    }

    private void startDoctorEditFragment(int focusFieldId) {
        if (Utils.isNetConnected(getParentActivity())) {
            Intent editorIntent = new Intent(getParentActivity(), BaseEditorActivity.class);
            editorIntent.putExtra(DOCTOR_ID, sharedPreferences.getInt("user_id",0));
            editorIntent.setAction(BaseEditorActivity.ACTION_DOCTOR_EDIT);
            if (focusFieldId != -1) {
                editorIntent.putExtra(BaseEditorActivity.EXTRA_FOCUS_FIELD, focusFieldId);
            }
            startActivityForResult(editorIntent, REQUEST_EDIT_DOCTOR);
        }
        //new Snackbar.make(getParentActivity().findViewById(android.R.id.content),R.string.no_network_connection,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_EDIT_DOCTOR:
                    getUpdatedPhysicianDetails(serverUrl, physician_id);
                    return;
                case REQUEST_EDIT_PRACTICE:
                    setClinicData();
                    return;
                default:
                    return;
            }
        }
    }

    public void setData(Bundle data){
        Toast.makeText(getParentActivity(), "Inside SetData", Toast.LENGTH_SHORT).show();
        int clinic_id=data.getInt("practice_id");
        String clinic_details=data.getString("clinic_details"+clinic_id);
        //setClinicData(clinic_details);
    }

    public void setClinicData(){
        int practiceId=sharedPreferences.getInt("practice_id", 0);
        String clinic_details=sharedPreferences.getString("clinic_details" + practiceId, null);
        Clinic clinic=gson.fromJson(clinic_details, Clinic.class);
        Iterator<Clinic> iterator=clinicList.iterator();
        while (iterator.hasNext()){
            Clinic clinic1=iterator.next();
            if (clinic1.getClinic_id()==clinic.getClinic_id()){
                iterator.remove();
            }
        }
        clinicList.add(clinic);
        ArrayList<Clinic> clinicSet = new ArrayList<>(clinicList);
        physicianDetails.setClinic(clinicSet);
        sharedPreferences.edit().putString("physician_details", gson.toJson(physicianDetails)).apply();
        sharedPreferences.edit().remove("practice_id").remove("clinic_details" + practiceId).apply();
        initList();
    }

    private void setUserInfo(PhysicianDetails physicianDetails){

    }

    public void getUpdatedPhysicianDetails(String url,int physicianId){
        JsonObjectRequest physicianDetails=new JsonObjectRequest(Request.Method.GET, url+physicianId,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response!=null){
                    PhysicianDetails physicianDetails=gson.fromJson(response.toString(),PhysicianDetails.class);
                    editor.putString("physician_details",response.toString());
                    editor.commit();
                    Log.d("Physician Details", physicianDetails.toString());
                    //setUserInfo(physicianDetails);
                    getPhysicianDetails();
                    initProfileData();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error:" + error.getMessage());
                getUpdatedPhysicianDetails(serverUrl, physician_id);
            }
        });

        physicianDetails.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(physicianDetails, TAG);
    }

    @Override
    public void onStop() {
        super.onStop();
        AppController.getInstance().cancelPendingRequests(TAG);
    }
}
