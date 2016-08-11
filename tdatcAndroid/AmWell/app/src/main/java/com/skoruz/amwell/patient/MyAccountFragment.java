package com.skoruz.amwell.patient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.i18n.phonenumbers.Phonenumber;
import com.skoruz.amwell.R;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.constants.Constants;
import com.skoruz.amwell.patientEntity.PatientDetails;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.Utils;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;


public class MyAccountFragment extends Fragment {

    private NetworkImageView mUserImage;
    private TextView mUserName;
    private TextView mEmail;
    private EditText mNumber;
    private RelativeLayout mTelLayout;
    private TextView mCountryCode;
    private RelativeLayout mEditLayout;
    private View mDivider;
    private RelativeLayout mDobLayout;
    private TextView mDoB;
    private RelativeLayout mBloodLayout;
    private TextView mBloodGroup;
    private SharedPreferences sharedPreferences;
    private PatientDetails patientDetails;
    private Gson gson=new Gson();
    View mView;
    private Resources mResources;
    private ImageLoader imageLoader;
    private static final String TAG=MyAccountFragment.class.getSimpleName();
    private String serverUrl=BaseURL.getPatientDetailsByPatientId;
    private int patient_id;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedPreferences=getActivity().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        mView=inflater.inflate(R.layout.fragment_my_account, container, false);
        initView();
        return mView;
    }

    private void initView(){
        imageLoader= AppController.getInstance().getImageLoader();
        this.mUserImage= (NetworkImageView) this.mView.findViewById(R.id.user_image);
        this.mUserName = (TextView) this.mView.findViewById(R.id.user_name);
        this.mEmail = (TextView) this.mView.findViewById(R.id.email);
        this.mNumber = (EditText) this.mView.findViewById(R.id.tel_num);
        this.mNumber.setEnabled(false);
        this.mNumber.setClickable(false);
        this.mNumber.setFocusable(false);
        this.mNumber.setFocusableInTouchMode(false);
        this.mTelLayout = (RelativeLayout) this.mView.findViewById(R.id.layout_tel);
        this.mCountryCode = (TextView) this.mView.findViewById(R.id.country_code);
        this.mEditLayout = (RelativeLayout) this.mView.findViewById(R.id.layout_edit);
        this.mEditLayout.setOnClickListener(this.clickListener);
        this.mTelLayout.setOnClickListener(this.clickListener);
        this.mDivider = this.mView.findViewById(R.id.divider);
        this.mDobLayout = (RelativeLayout) this.mView.findViewById(R.id.layout_dob);
        this.mDoB = (TextView) this.mView.findViewById(R.id.et_dob);
        this.mBloodLayout = (RelativeLayout) this.mView.findViewById(R.id.layout_blood);
        this.mBloodGroup = (TextView) this.mView.findViewById(R.id.bloodgroup);
    }

    private void setUserInfo(PatientDetails patientDetails){
            if (patientDetails!=null){
                if(TextUtils.isEmpty(patientDetails.getUser().getFirstName()) && TextUtils.isEmpty(patientDetails.getUser().getLastName())){
                    this.mUserName.setVisibility(View.GONE);
                }else{
                    String name=patientDetails.getUser().getFirstName()+" "+patientDetails.getUser().getLastName();
                    this.mUserName.setText(name);
                    this.mUserName.setVisibility(View.VISIBLE);
                    this.mUserName.setTextColor(getResources().getColor(R.color.black));
                }
                this.mEmail.setText(patientDetails.getUser().getEmailAddress());
                if (TextUtils.isEmpty(patientDetails.getUser().getPhonePersonel())){
                    this.mNumber.setText(R.string.add_number);
                    this.mCountryCode.setVisibility(View.GONE);
                    this.mDivider.setVisibility(View.GONE);
                }else {
                    Phonenumber.PhoneNumber phoneNumber= Utils.parsePhoneNumber(patientDetails.getUser().getPhonePersonel(), "IN");
                    if (phoneNumber!=null){
                        this.mNumber.setText(Long.toString(phoneNumber.getNationalNumber()));
                        String cc=Integer.toString(phoneNumber.getCountryCode());
                        if (!cc.startsWith("+")){
                            cc= "+" + cc;
                        }
                        this.mCountryCode.setText(cc);
                    }
                    this.mNumber.setTextColor(getResources().getColor(R.color.black));
                    this.mCountryCode.setVisibility(View.VISIBLE);
                    this.mDivider.setVisibility(View.VISIBLE);
                }
                if (TextUtils.isEmpty(patientDetails.getUser().getDateOfBirth())){
                    this.mDobLayout.setVisibility(View.GONE);
                }else {
                    try {
                        this.mDoB.setText(Constants.dobDateFormat.format(Constants.dbDateFormat.parse(patientDetails.getUser().getDateOfBirth())));
                        this.mDoB.setTextColor(getResources().getColor(R.color.black));
                        this.mDobLayout.setVisibility(View.VISIBLE);
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                }
                if (TextUtils.isEmpty(patientDetails.getBloodType())) {
                    this.mBloodLayout.setVisibility(View.GONE);
                }else {
                    this.mBloodGroup.setText(patientDetails.getBloodType());
                    this.mBloodLayout.setVisibility(View.VISIBLE);
                }
                AppController.getInstance().getRequestQueue().getCache().clear();
                if (!Utils.isEmptyString(patientDetails.getUser().getPhotoPath()) && !patientDetails.getUser().getPhotoPath().equalsIgnoreCase("null")){
                    if (imageLoader==null){
                        imageLoader=AppController.getInstance().getImageLoader();
                    }
                    mUserImage.setImageUrl(patientDetails.getUser().getPhotoPath(), imageLoader);
                }
            }
    }

    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.layout_edit:
                    Intent editProfile=new Intent(getActivity(),EditUserProfileActivity.class);
                    MyAccountFragment.this.startActivity(editProfile);
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        String patientDetailJson=sharedPreferences.getString("patient_details", null);
        patientDetails=gson.fromJson(patientDetailJson,PatientDetails.class);
        patient_id=patientDetails.getPatient_id();
        getUpdatedPatientDetails(this.serverUrl,patient_id);
        //setUserInfo(patientDetails);
    }

    public void getUpdatedPatientDetails(String url,int patientId){
        JsonObjectRequest patientDetails=new JsonObjectRequest(Request.Method.GET, url+patientId,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response!=null){
                    PatientDetails patientDetails=gson.fromJson(response.toString(),PatientDetails.class);
                    editor.putString("patient_details",response.toString());
                    editor.commit();
                    Log.d("Patient Details",patientDetails.toString());
                    setUserInfo(patientDetails);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG,"Error:"+error.getMessage());
                //getUpdatedPatientDetails(serverUrl,patient_id);
            }
        });

        patientDetails.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(patientDetails,TAG);
    }

    @Override
    public void onStop() {
        super.onStop();
        AppController.getInstance().cancelPendingRequests(TAG);
    }
}
