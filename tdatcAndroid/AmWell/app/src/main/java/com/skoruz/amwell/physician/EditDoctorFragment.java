package com.skoruz.amwell.physician;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestTickle;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.VolleyTickle;
import com.android.volley.ui.NetworkImageView;
import com.github.mrengineer13.snackbar.SnackBar;
import com.google.gson.Gson;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.skoruz.amwell.BuildConfig;
import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.AffiliationAdapter;
import com.skoruz.amwell.adapter.QualificationAdapter;
import com.skoruz.amwell.adapter.SpecializationAdapter;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.customViews.CircleImageView;
import com.skoruz.amwell.misc.FileUtils;
import com.skoruz.amwell.patientEntity.UserImage;
import com.skoruz.amwell.physicianEntity.Affliation;
import com.skoruz.amwell.physicianEntity.PhysicianDetails;
import com.skoruz.amwell.physicianEntity.Qualification;
import com.skoruz.amwell.physicianEntity.Specialization;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.InputFilterMinMax;
import com.skoruz.amwell.utils.PreferenceUtils;
import com.skoruz.amwell.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditDoctorFragment extends BaseFragment implements View.OnClickListener,TextView.OnEditorActionListener,TextWatcher,AdapterView.OnItemClickListener{

    private ArrayList<Qualification> qualificationList=new ArrayList<>();
    private ArrayList<Specialization> specializationList=new ArrayList<>();
    private ArrayList<Affliation> affliationList=new ArrayList<>();
    private static final String TAG=EditDoctorFragment.class.getSimpleName();
    private CircleImageView profileImage;
    private Gson gson=new Gson();
    private SpecializationAdapter specializationAdapter;
    private QualificationAdapter qualificationAdapter;
    private AffiliationAdapter affiliationAdapter;
    private boolean sBoolean,qBoolean,aBoolean;
    private AlertDialog registrationDialog;
    private AlertDialog specializationDialog;
    private AlertDialog qualificationDialog;
    private SharedPreferences sharedPreferences;
    private HashMap<Integer, String> mSelectedQualificationMap=new HashMap<>();
    private HashMap<Integer, String> mSelectedRegistrationCouncilMap=new HashMap<>();;
    private HashMap<Integer, String> mSelectedSpecializationMap=new HashMap<>();
    private PhysicianDetails physicianDetails;
    private AppCompatActivity mActivity;

    private LinearLayout mContentLl;
    private int mDoctorExperience;
    private EditText mDoctorExperienceEt;
    private int mDoctorFabricId;
    private EditText mDoctorNameEt;
    private Button mDoctorPhotoEditBtn;
    private NetworkImageView mDoctorPhotoIv;
    private String mDoctorPhotoNewUrl;
    private String mDoctorPhotoOldUrl;
    private EditText mDoctorPrimaryEmailEt;
    private String mDoctorPrimaryNumber;
    private EditText mDoctorPrimaryNumberEt;
    private EditText mDoctorQualificationEt;
    private ArrayList<String> mDoctorQualificationFabricList;
    private ArrayList<String> mDoctorQualificationMasterList;
    private EditText mDoctorRegistrationCouncilEt;
    private ArrayList<String> mDoctorRegistrationFabricList;
    private EditText mDoctorRegistrationNumberEt;
    private EditText mDoctorRegistrationYearEt;
    private EditText mDoctorSpecializationEt;
    private ArrayList<String> mDoctorSpecializationFabricList;
    private ArrayList<String> mDoctorSpecializationMasterList;
    private boolean mHasRegistrations;
    private boolean mIsEditing;
    private JSONObject mPatchJsonObject;
    private MaterialDialog mProfileUpdateDialog;
    private RelativeLayout mProgressRl;
    private ScrollView mScrollView;
    private HashMap<Long, String> mSelectedCityMap;
    private Uri mSelectedImageUri;
    public static final int FOCUS_FIELD_DOCTOR_PHOTOS = 5;
    public static final int FOCUS_FIELD_EXPERIENCE = 8;
    public static final int FOCUS_FIELD_QUALIFICATION = 7;
    public static final int FOCUS_FIELD_SPECIALIZATION = 6;
    public static final String COUNTRY_INDIA = "india";
    SharedPreferences.Editor editor;
    private TextView country_code;
    private TelephonyManager telephonyManager;
    private GsmCellLocation cellLocation;
    private android.support.v7.app.AlertDialog mRequestPhotoDialog;

    private static String TAG_PHY_UPDATE="phyUpdate";

    private Uri profileFileUri;
    private String selected_profile_image_path;
    private String selected_profile_image_name;
    private ImageLoader imageLoader;
    private Resources mResources;
    public static final String KEY_DOCTOR_PHOTO_URL = "photoPath";
    public static final String KEY_DOCTOR_EXPERIENCE="experienceInYear";
    public static final String KEY_DOCTOR_ID="physician_id";
    public static final String KEY_DOCTOR_FIRST_NAME="firstName";
    public static final String KEY_DOCTOR_LAST_NAME="lastName";
    public static final String KEY_PHONE_PERSONAL="phonePersonel";
    public static final String KEY_USER_OBJECT="user";
    public static final String KEY_SPECIALIZATION_OBJECT="specializations";
    public static final String KEY_SPECIALIZATION_ID="specialization_id";
    public static final String KEY_SPECIALIZATION_VALUE="specializations";
    public static final String KEY_QUALIFICATION_OBJECT="qualification";
    public static final String KEY_QUALIFICATION_ID="qualification_id";
    public static final String KEY_QUALIFICATION_VALUE="qualification";
    public static final String KEY_AFFILIATION_OBJECT="affliation";
    public static final String KEY_AFFILIATION_ID="affiliation_id";
    public static final String KEY_AFFILIATION_VALUE="universityName";
    public static final String KEY_REGISTRATION_NO="registrationNo";
    public static final String KEY_REGISTRATION_DATE="registrationDate";


    private static ErrorListener errorListener = new ErrorListener() {
        public void onErrorResponse(VolleyError error) {
            VolleyLog.d(TAG, "Error: " + error.getMessage());
        }
    };

    public EditDoctorFragment() {
        // Required empty public constructor
    }

    public static EditDoctorFragment newInstance(Bundle paramBundle)
    {
        EditDoctorFragment localEditDoctorFragment = new EditDoctorFragment();
        localEditDoctorFragment.setArguments(paramBundle);
        return localEditDoctorFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View editDoctorView=inflater.inflate(R.layout.fragment_edit_doctor, container, false);
        sharedPreferences=getParentActivity().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        super.initEditToolbar((Toolbar) editDoctorView.findViewById(R.id.toolbarDetails), getString(R.string.edit_public_profile), false);
        imageLoader= AppController.getInstance().getImageLoader();
        this.mDoctorPhotoIv = (NetworkImageView) editDoctorView.findViewById(R.id.edit_doctor_iv_photo);
        this.country_code= (TextView) editDoctorView.findViewById(R.id.country_code);
        this.mDoctorNameEt = (EditText) editDoctorView.findViewById(R.id.edit_doctor_et_name);
        this.mDoctorPrimaryEmailEt = (EditText) editDoctorView.findViewById(R.id.edit_doctor_et_primary_email);
        this.mDoctorPrimaryNumberEt = (EditText) editDoctorView.findViewById(R.id.edit_doctor_et_primary_mobile);
        this.mDoctorSpecializationEt = (EditText) editDoctorView.findViewById(R.id.edit_doctor_et_specialization);
        this.mDoctorQualificationEt = (EditText) editDoctorView.findViewById(R.id.edit_doctor_et_qualification);
        this.mDoctorExperienceEt = (EditText) editDoctorView.findViewById(R.id.edit_doctor_et_experience);
        this.mDoctorRegistrationYearEt = (EditText) editDoctorView.findViewById(R.id.edit_doctor_et_registration_year);
        this.mDoctorRegistrationCouncilEt = (EditText) editDoctorView.findViewById(R.id.edit_doctor_et_registration_council);
        this.mDoctorRegistrationNumberEt = (EditText) editDoctorView.findViewById(R.id.edit_doctor_et_registration_number);
        this.mDoctorExperienceEt.setOnEditorActionListener(this);
        this.mDoctorExperienceEt.setFilters(new InputFilter[]{new InputFilterMinMax("0", "75")});
        this.mDoctorPhotoEditBtn = (Button) editDoctorView.findViewById(R.id.edit_doctor_btn_photo);
        this.mDoctorPhotoIv.setOnClickListener(this);
        this.mDoctorQualificationEt.setOnClickListener(this);
        this.mDoctorSpecializationEt.setOnClickListener(this);
        this.mDoctorPhotoEditBtn.setOnClickListener(this);
        this.mDoctorRegistrationYearEt.setOnClickListener(this);
        this.mDoctorRegistrationCouncilEt.setOnClickListener(this);
        this.mDoctorNameEt.addTextChangedListener(this);
        this.mContentLl = (LinearLayout) editDoctorView.findViewById(R.id.edit_doctor_ll_content);
        this.mProgressRl = (RelativeLayout) editDoctorView.findViewById(R.id.edit_doctor_rl_content_progress);
        this.mScrollView = (ScrollView) editDoctorView.findViewById(com.rengwuxian.materialedittext.R.id.scrollView);
        setHasOptionsMenu(Boolean.TRUE.booleanValue());
        initProfileData();
        initData();
        specializationAdapter=new SpecializationAdapter(getParentActivity(),specializationList);
        qualificationAdapter=new QualificationAdapter(getParentActivity(),qualificationList);
        affiliationAdapter=new AffiliationAdapter(getParentActivity(),affliationList);

        return editDoctorView;
    }

  /*  public class GetLatLng extends AsyncTask<Integer,Boolean,Boolean> {

        @Override
        protected Boolean doInBackground(Integer... params) {
            Boolean result = false;

            String urlmmap = "http://www.google.com/glm/mmap";

            try {
                URL url = new URL(urlmmap);
                URLConnection conn = url.openConnection();
                HttpURLConnection httpConn = (HttpURLConnection) conn;
                httpConn.setRequestMethod("POST");
                httpConn.setDoOutput(true);
                httpConn.setDoInput(true);
                httpConn.connect();

                OutputStream outputStream = httpConn.getOutputStream();
                WriteData(outputStream, params[0], params[1]);

                InputStream inputStream = httpConn.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);

                dataInputStream.readShort();
                dataInputStream.readByte();
                int code = dataInputStream.readInt();
                if (code == 0) {
                    myLatitude = dataInputStream.readInt();
                    myLongitude = dataInputStream.readInt();

                    result = true;

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return result;
        }
    }

    private void WriteData(OutputStream out, int cid, int lac)
            throws IOException
    {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        dataOutputStream.writeShort(21);
        dataOutputStream.writeLong(0);
        dataOutputStream.writeUTF("en");
        dataOutputStream.writeUTF("Android");
        dataOutputStream.writeUTF("1.0");
        dataOutputStream.writeUTF("Web");
        dataOutputStream.writeByte(27);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(3);
        dataOutputStream.writeUTF("");

        dataOutputStream.writeInt(cid);
        dataOutputStream.writeInt(lac);

        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.flush();
    }*/

    private void initProfileData(){

        //retrieve a reference to an instance of TelephonyManager
        telephonyManager = (TelephonyManager)getParentActivity().getSystemService(Context.TELEPHONY_SERVICE);
        cellLocation = (GsmCellLocation) telephonyManager.getCellLocation();

        int cid = cellLocation.getCid();
        int lac = cellLocation.getLac();

        //String phoneNo=telephonyManager.getLine1Number();
        String country=telephonyManager.getSimCountryIso();

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try {
            Phonenumber.PhoneNumber phoneNumber=phoneUtil.parse("9964960805",country);
            //Toast.makeText(MainActivity.this, phoneNumber.toString(), Toast.LENGTH_SHORT).show();
            System.out.println(phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164));
            //Toast.makeText(getParentActivity(), String.valueOf(PhoneNumberUtil.getInstance().getCountryCodeForRegion(country)), Toast.LENGTH_SHORT).show();
            country_code.setText("+"+String.valueOf(PhoneNumberUtil.getInstance().getCountryCodeForRegion(country)));
        } catch (Exception e) {
            e.printStackTrace();
        }

       /* int mcc=0,mnc=0;
        if (telephonyManager.getNetworkOperator()!=null){
            mcc = Integer.parseInt(telephonyManager.getNetworkOperator().substring(0, 3));
            mnc = Integer.parseInt(telephonyManager.getNetworkOperator().substring(3));
        }

        Integer[] data=new Integer[2];
        data[0]=cid;
        data[1]=lac;


        new GetLatLng().execute(data);*/
        mProgressRl.setVisibility(View.GONE);
        mContentLl.setVisibility(View.VISIBLE);
        String physicianDetailsJson=sharedPreferences.getString("physician_details",null);
        physicianDetails=gson.fromJson(physicianDetailsJson,PhysicianDetails.class);
        mDoctorFabricId=physicianDetails.getPhysician_id();
        String name=physicianDetails.getUser().getFirstName() + " " + physicianDetails.getUser().getLastName();
        mDoctorNameEt.setText(name);
        mDoctorNameEt.setSelection(name.length());
        mDoctorPrimaryEmailEt.setText(physicianDetails.getUser().getEmailAddress());
        String consultPrimaryPhoneNo=physicianDetails.getUser().getPhonePersonel();
        if (!Utils.isEmptyString(consultPrimaryPhoneNo)){
            consultPrimaryPhoneNo = Utils.getPhoneNumberWithoutCountryCode(consultPrimaryPhoneNo);
            this.mDoctorPrimaryNumberEt.setText(consultPrimaryPhoneNo);
        }if (physicianDetails.getSpecializations()!=null){
            mDoctorSpecializationEt.setText(physicianDetails.getSpecializations().getSpecializations());
            mSelectedSpecializationMap.put(physicianDetails.getSpecializations().getSpecialization_id(), physicianDetails.getSpecializations().getSpecializations());
        }if (physicianDetails.getQualification()!=null){
            mDoctorQualificationEt.setText(physicianDetails.getQualification().getQualification());
            mSelectedQualificationMap.put(physicianDetails.getQualification().getQualification_id(), physicianDetails.getQualification().getQualification());
        }if (physicianDetails.getAffliation()!=null){
            mDoctorRegistrationCouncilEt.setText(physicianDetails.getAffliation().getUniversityName());
            mSelectedRegistrationCouncilMap.put(physicianDetails.getAffliation().getAffiliation_id(), physicianDetails.getAffliation().getUniversityName());
        }

        int doctorExperience=physicianDetails.getExperienceInYear();
        mDoctorExperienceEt.setText(doctorExperience + BuildConfig.FLAVOR);
        mDoctorRegistrationNumberEt.setText(physicianDetails.getRegistrationNo());
        mDoctorRegistrationYearEt.setText(physicianDetails.getRegistrationDate());
        AppController.getInstance().getRequestQueue().getCache().clear();
        if (!Utils.isEmptyString(physicianDetails.getUser().getPhotoPath()) && !physicianDetails.getUser().getPhotoPath().equalsIgnoreCase("null")){
            //super.setPicture(Uri.parse(physicianDetails.getUser().getPhotoPath()),mDoctorPhotoIv);
            if (imageLoader==null){
                imageLoader=AppController.getInstance().getImageLoader();
            }
            mDoctorPhotoIv.setImageUrl(physicianDetails.getUser().getPhotoPath(), imageLoader);
        }

    }

    private void initData(){
        getSpecializationList(BaseURL.getSpecialization);
        getQualificationList(BaseURL.getQualification);
        getAffiliationList(BaseURL.getAffiliation);
    }

    public void updatePhysicianProfileDetails(){
        Utils.hideKeyboard(getParentActivity());
        boolean isValidInput = true;
        String doctorName = this.mDoctorNameEt.getText().toString().trim();
        String doctorPrimaryNumber = this.mDoctorPrimaryNumberEt.getText().toString();
        String doctorExperience = this.mDoctorExperienceEt.getText().toString();
        this.mPatchJsonObject = new JSONObject();
        try {
            String country=telephonyManager.getSimCountryIso();
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber phoneNumber=Utils.parsePhoneNumber(doctorPrimaryNumber, country);

            this.mPatchJsonObject.put(KEY_DOCTOR_ID,mDoctorFabricId);

            if (Utils.isEmptyString(doctorExperience)) {
                this.mDoctorExperienceEt.setError(getString(R.string.edit_doctor_error_experience));
                this.mDoctorExperienceEt.requestFocus();
                isValidInput = false;
            } else {
                //physicianDetails.setExperienceInYear(Integer.parseInt(doctorExperience));
                this.mPatchJsonObject.put(KEY_DOCTOR_EXPERIENCE, Integer.parseInt(doctorExperience));
            }

            JSONObject addQualificationObject;
            Qualification qualification=new Qualification();
            if (!Utils.isEmptyMap(this.mSelectedQualificationMap)) {
                addQualificationObject=new JSONObject(KEY_QUALIFICATION_OBJECT);
                for (Map.Entry<Integer,String> entry:this.mSelectedQualificationMap.entrySet()){
                    addQualificationObject.put(KEY_QUALIFICATION_ID,entry.getKey());
                    addQualificationObject.put(KEY_QUALIFICATION_VALUE,entry.getValue());
                    /*qualification.setQualification_id(entry.getKey());
                    qualification.setQualification(entry.getValue());
                    physicianDetails.setQualification(qualification);*/
                }
                if (addQualificationObject.length()>0){
                    this.mPatchJsonObject.put("",addQualificationObject);
                }
            }else{
                this.mDoctorQualificationEt.setError(getString(R.string.edit_doctor_error_qualification));
                this.mScrollView.fullScroll(130);
                isValidInput = false;
            }

            JSONObject addAffiliationObject;
            Affliation affliation=new Affliation();
            if (!Utils.isEmptyMap(this.mSelectedRegistrationCouncilMap)) {
                addAffiliationObject=new JSONObject(KEY_AFFILIATION_OBJECT);
                for (Map.Entry<Integer,String> entry:this.mSelectedRegistrationCouncilMap.entrySet()){
                    addAffiliationObject.put(KEY_AFFILIATION_ID,entry.getKey());
                    addAffiliationObject.put(KEY_AFFILIATION_VALUE,entry.getValue());
                    /*affliation.setAffiliation_id(entry.getKey());
                    affliation.setUniversityName(entry.getValue());
                    physicianDetails.setAffliation(affliation);*/
                }
                if (addAffiliationObject.length()>0){
                    this.mPatchJsonObject.put("",addAffiliationObject);
                }
            }else{
                this.mDoctorRegistrationCouncilEt.setError(getString(R.string.edit_doctor_error_registration_council));
                this.mScrollView.fullScroll(130);
                isValidInput = false;
            }

            JSONObject addSpecializationObject;
            Specialization specialization=new Specialization();
            if (!Utils.isEmptyMap(this.mSelectedSpecializationMap)) {
                addSpecializationObject=new JSONObject(KEY_SPECIALIZATION_OBJECT);
                for (Map.Entry<Integer,String> entry:this.mSelectedSpecializationMap.entrySet()){
                    addSpecializationObject.put(KEY_SPECIALIZATION_ID,entry.getKey());
                    addSpecializationObject.put(KEY_SPECIALIZATION_VALUE,entry.getValue());
                    /*specialization.setSpecialization_id(entry.getKey());
                    specialization.setSpecializations(entry.getValue());
                    physicianDetails.setSpecializations(specialization);*/
                }
                if (addSpecializationObject.length()>0){
                    this.mPatchJsonObject.put("",addSpecializationObject);
                }
            }else{
                this.mDoctorSpecializationEt.setError(getString(R.string.edit_doctor_error_specialization));
                this.mScrollView.fullScroll(130);
                isValidInput = false;
            }

            if(Utils.isEmptyString(mDoctorRegistrationNumberEt.getText().toString())){
                mDoctorRegistrationNumberEt.setError(getString(R.string.edit_doctor_error_registration_number));
                mDoctorRegistrationNumberEt.requestFocus();
                this.mScrollView.fullScroll(130);
                isValidInput = false;
            } else {
                this.mPatchJsonObject.put(KEY_REGISTRATION_NO, mDoctorRegistrationNumberEt.getText().toString());
                //physicianDetails.setRegistrationNo(mDoctorRegistrationNumberEt.getText().toString());
            }

            String registrationYear = this.mDoctorRegistrationYearEt.getText().toString();
            if (Utils.isEmptyString(registrationYear)) {
                this.mDoctorRegistrationYearEt.setError(getString(R.string.edit_doctor_error_registration_year));
                this.mScrollView.fullScroll(130);
                isValidInput = false;
            }else{
                this.mPatchJsonObject.put(KEY_REGISTRATION_DATE,mDoctorRegistrationYearEt.getText().toString());
                //physicianDetails.setRegistrationDate(mDoctorRegistrationYearEt.getText().toString());
            }

            if (Utils.isEmptyString(doctorPrimaryNumber)) {
                this.mDoctorPrimaryNumberEt.setError(getString(R.string.edit_doctor_error_primary_mobile));
                this.mDoctorPrimaryNumberEt.requestFocus();
                isValidInput = false;
            } else {
                if (Utils.isValidMobile(doctorPrimaryNumber, Utils.getCountryCode(country))) {
                    this.mPatchJsonObject.put(KEY_PHONE_PERSONAL, phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164));
                    //physicianDetails.getUser().setPhonePersonel(phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164));
                } else {
                    this.mDoctorPrimaryNumberEt.setError(getString(R.string.edit_error_primary_mobile_invalid));
                    this.mDoctorPrimaryNumberEt.requestFocus();
                    isValidInput = false;
                }
            }

            if (Utils.isEmptyString(doctorName)) {
                this.mDoctorNameEt.setError(getString(R.string.edit_doctor_error_name));
                this.mDoctorNameEt.requestFocus();
                isValidInput = false;
            } else if (doctorName.length() < 3 || doctorName.length() > 50) {
                this.mDoctorNameEt.setError(Html.fromHtml(getString(R.string.edit_name_error_length)));
                this.mDoctorNameEt.requestFocus();
                isValidInput = false;
            } else {
                if(doctorName.contains(" ")){
                    String[] names=doctorName.split(" ");
                    this.mPatchJsonObject.put(KEY_DOCTOR_FIRST_NAME, names[0]);
                    this.mPatchJsonObject.put(KEY_DOCTOR_LAST_NAME, names[1]);
                    /*physicianDetails.getUser().setFirstName(names[0]);
                    physicianDetails.getUser().setLastName(names[1]);*/
                }else{
                    this.mPatchJsonObject.put(KEY_DOCTOR_FIRST_NAME,doctorName);
                    //physicianDetails.getUser().setFirstName(doctorName);
                }
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        if (!isValidInput) {
            return;
        }
        if (!Utils.isNetConnected(getActivity())){
            new SnackBar.Builder(getActivity()).withMessageId(R.string.no_network_connection).withActionMessageId(R.string.ok).show();
        }else if (this.mSelectedImageUri!=null){
            PhotoUploadTask photoUploadTask = new PhotoUploadTask();
            Uri[] uriArr = new Uri[1];
            uriArr[0] = this.mSelectedImageUri;
            photoUploadTask.execute(uriArr);
        }else {
            ProfileUpdateTask profileUpdateTask = new ProfileUpdateTask(Boolean.TRUE.booleanValue());
            JSONObject[] jSONObjectArr = new JSONObject[1];
            jSONObjectArr[0] = this.mPatchJsonObject;
            profileUpdateTask.execute(jSONObjectArr);
        }
        //patchPhysicianDetails(BaseURL.updatePhysicianProfile, physicianDetails);
    }

    private void requestFocus() {
        if (getArguments() != null) {
            switch (getArguments().getInt(BaseEditorActivity.EXTRA_FOCUS_FIELD)) {
                case FOCUS_FIELD_DOCTOR_PHOTOS:
                    this.mDoctorPhotoEditBtn.performClick();
                    break;
                case FOCUS_FIELD_SPECIALIZATION:
                    this.mDoctorSpecializationEt.performClick();
                    break;
                case FOCUS_FIELD_QUALIFICATION:
                    this.mDoctorQualificationEt.performClick();
                    break;
                case FOCUS_FIELD_EXPERIENCE:
                    this.mDoctorExperienceEt.requestFocus();
                    break;
            }
            getArguments().remove(BaseEditorActivity.EXTRA_FOCUS_FIELD);
        }
    }


    public void patchPhysicianDetails(String url,PhysicianDetails Details){
        final Gson gson=new  Gson();
        final String jsonData=gson.toJson(Details);
        JSONObject object=null;
        try {
            object=new JSONObject(jsonData.toString());
        }catch (JSONException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest patchPhysicianDetails=new JsonObjectRequest(Request.Method.PUT, url, object, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("status").equalsIgnoreCase("success")){
                        Toast.makeText(getParentActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                        editor.putString("physician_details", jsonData.toString());
                        editor.commit();
                        if (!Utils.isEmptyString(selected_profile_image_name)) {
                            saveImageToServer(BaseURL.uploadImage, physicianDetails.getPhysician_id());
                        }else{
                            getParentActivity().setResult(Activity.RESULT_OK);
                            getParentActivity().finish();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },errorListener);

        patchPhysicianDetails.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(patchPhysicianDetails, TAG);

    }

    public void saveImageToServer(String url,int userId){
        UserImage userImage=new UserImage();
        userImage.setFileName(selected_profile_image_name);
        try {
            InputStream inputStream = new FileInputStream(new File(selected_profile_image_path));
            byte[] fileData = this.convertToByteArray(inputStream);
            userImage.setFileData(fileData);
        }catch (Exception e){
            e.printStackTrace();
        }
        String jsonString = gson.toJson(userImage);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest sendUserImage = new JsonObjectRequest(Request.Method.POST,
                url+userId, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(AppController.TAG, response.toString());
                        try {
                            if (response.getString("status").equalsIgnoreCase("success")) {
                                Toast.makeText(getParentActivity(),"Uploaded Successfully",Toast.LENGTH_SHORT).show();
                                editor.putString("physician_image_path",selected_profile_image_path);
                                editor.commit();
                                getParentActivity().setResult(Activity.RESULT_OK);
                                getParentActivity().finish();
                            } else if (response.getString("status").equalsIgnoreCase("failure")) {
                                Toast.makeText(getParentActivity(),"Cannot connect to AmWell. Please try later..",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AppController.TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Cannot connect to AmWell. Please try later..",
                        Toast.LENGTH_SHORT).show();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };

        sendUserImage.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sendUserImage, TAG);
    }

    @Override
    public void onStop() {
        super.onStop();
        AppController.getInstance().cancelPendingRequests(TAG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_doctor_iv_photo:
            case R.id.edit_doctor_btn_photo:
                showAddPhotoDialog();
                return;
            case R.id.edit_doctor_et_specialization:
                showSpecializationDialog();
                return;
            case R.id.edit_doctor_et_qualification:
                showQualificationDialog();
                return;
            case R.id.edit_doctor_et_registration_council:
                showRegistrationDialog();
                return;
            case R.id.edit_doctor_et_registration_year:
                showYearPickerDialog();
                return;
            /*case R.id.button_camera:
                this.mRequestPhotoDialog.dismiss();
                Activity activity = getActivity();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
                    Uri uploadFileUri = createImageFile();
                    if (uploadFileUri != null) {
                        cameraIntent.putExtra("output", uploadFileUri);
                        startActivityForResult(cameraIntent, REQUEST_CAPTURE_IMAGE);
                        return;
                    }
                    Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.can_not_capture_photo), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            }).show();
                    //new SnackBar.Builder(getActivity()).withMessageId(R.string.can_not_capture_photo).withActionMessageId(R.string.ok).show();
                    return;
                }
                Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.no_camera), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                //new SnackBar.Builder(getActivity()).withMessageId(R.string.no_camera).withActionMessageId(R.string.ok).show();
                //changeProfileImage();
                return;
            case R.id.button_gallery:
                this.mRequestPhotoDialog.dismiss();
                Intent intentSelect = new Intent(Intent.ACTION_GET_CONTENT);
                intentSelect.setType(MIME_TYPE_IMAGE);
                startActivityForResult(Intent.createChooser(intentSelect, getString(R.string.select_picture)), REQUEST_SELECT_IMAGE);
                return;*/
            default:
                super.onClick(v);
                return;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_base_editor, menu);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString("current_path", mCurrentPhotoPath);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_SELECT_IMAGE:
                if (resultCode== getParentActivity().RESULT_OK){
                    this.mSelectedImageUri = data.getData();
                    super.setPicture(this.mSelectedImageUri, this.mDoctorPhotoIv);
                    return;
                }
                return;
            case REQUEST_CAPTURE_IMAGE:
                if (resultCode == getParentActivity().RESULT_OK && !TextUtils.isEmpty(this.mCurrentPhotoPath)) {
                    try {
                        this.mSelectedImageUri = Uri.parse(this.mCurrentPhotoPath);
                        super.setPicture(this.mSelectedImageUri, this.mDoctorPhotoIv);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return;
                }
                return;
        }

        /*if (resultCode==getParentActivity().RESULT_OK){
            switch (requestCode){
                case REQUEST_SELECT_IMAGE:
                        this.mSelectedImageUri=data.getData();
                        String path = FileUtils.getPath(getActivity(), this.mSelectedImageUri);
                        this.selected_profile_image_path=path;
                        this.selected_profile_image_name=path.substring(1 + path.lastIndexOf("/"));
                        super.setPicture(this.mSelectedImageUri,this.mDoctorPhotoIv);
                        return;
                case REQUEST_CAPTURE_IMAGE:
                    String current_path=sharedPreferences.getString("current_path",null);
                    if (current_path!=null || !TextUtils.isEmpty(current_path)) {
                        this.selected_profile_image_name=current_path.substring(1 + current_path.lastIndexOf("/"));
                        this.selected_profile_image_path=current_path;
                        this.mSelectedImageUri = Uri.parse(current_path);
                        super.setPicture(this.mSelectedImageUri, this.mDoctorPhotoIv);
                        return;
                    }
                    return;
            }
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_practiceDetails:
                updatePhysicianProfileDetails();
                break;
            case android.R.id.home:
                Activity activity=getActivity();
                activity.setResult(Activity.RESULT_CANCELED);
                activity.finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class PhotoUploadTask extends AsyncTask<Uri,Void,String> implements SnackBar.OnMessageClickListener{
        private static final String TEMP_USER_FILE_NAME = "temp_user_name";
        private File mProfileCacheFolder;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (Utils.isActivityAlive(EditDoctorFragment.this.getParentActivity())){
                EditDoctorFragment.this.mProfileUpdateDialog=new Builder(EditDoctorFragment.this.getActivity()).content
                        (EditDoctorFragment.this.getString(R.string.edit_profile_update_progress)).progress(true, 0).cancelable
                        (false).autoDismiss(false).show();
            }
        }

        @Override
        protected String doInBackground(Uri... params) {
            String path = FileUtils.getPath(EditDoctorFragment.this.getParentActivity(), params[0]);
            createFolder();
            if (this.mProfileCacheFolder != null && this.mProfileCacheFolder.exists()) {
                File uploadFile = getPath(this.mProfileCacheFolder.getPath(), false);
                if (!(TextUtils.isEmpty(path) || TextUtils.isEmpty(uploadFile.getPath()))) {
                    path = Utils.decodeSampledBitmap(path, uploadFile.getPath(), 768, 768, true);
                }
            }
            return uploadPhoto(path,EditDoctorFragment.this.mDoctorFabricId);
        }

        @Override
        protected void onPostExecute(String result) {
            if (Utils.isActivityAlive(EditDoctorFragment.this.getParentActivity())){
                EditDoctorFragment.this.mDoctorPhotoNewUrl=result;
                if (Utils.isEmptyString(EditDoctorFragment.this.mDoctorPhotoNewUrl)){
                    EditDoctorFragment.this.mProfileUpdateDialog.dismiss();
                    if (!Utils.isEmptyString(EditDoctorFragment.this.mDoctorPhotoOldUrl)){
                        EditDoctorFragment.this.mDoctorPhotoIv.setResetImageUrl(EditDoctorFragment.this.mDoctorPhotoOldUrl, AppController.getInstance().getImageLoader());
                    }
                    new SnackBar.Builder(EditDoctorFragment.this.getActivity()).withMessageId(R.string.edit_profile_update_failure).withActionMessageId(R.string.retry).withStyle(SnackBar.Style.INFO).withOnClickListener(this).show();
                }else {
                    try {
                        String url = EditDoctorFragment.this.mDoctorPhotoNewUrl;
                        JSONObject addPhotosObject = new JSONObject();
                        addPhotosObject.put(EditDoctorFragment.KEY_DOCTOR_PHOTO_URL, url);
                        JSONArray addedPhotoJsonArray = new JSONArray();
                        addedPhotoJsonArray.put(addPhotosObject);
                        JSONArray updatedPhotosJsonArray = new JSONArray();
                        JSONArray deletedPhotosJsonArray = new JSONArray();
                        JSONObject newPhotos = new JSONObject();
                        /*newPhotos.put(EditDoctorFragment.KEY_ADDED, addedPhotoJsonArray);
                        newPhotos.put(EditDoctorFragment.KEY_UPDATED, updatedPhotosJsonArray);
                        newPhotos.put(EditDoctorFragment.KEY_SOFT_DELETED, deletedPhotosJsonArray);
                        EditDoctorFragment.this.mPatchJsonObject.put(EditDoctorFragment.KEY_DOCTOR_PHOTOS, newPhotos);*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ProfileUpdateTask profileUpdateTask = new ProfileUpdateTask(Boolean.FALSE.booleanValue());
                    JSONObject[] jSONObjectArr = new JSONObject[]{EditDoctorFragment.this.mPatchJsonObject};
                    profileUpdateTask.execute(jSONObjectArr);
                }
                super.onPostExecute(result);
            }
        }

        private void createFolder() {
            this.mProfileCacheFolder = com.android.volley.misc.Utils.getDiskCacheDir(EditDoctorFragment.this.getParentActivity(), AppController.IMAGE_PRACTICE_CACHE_DIR);
            if (!this.mProfileCacheFolder.exists()) {
                this.mProfileCacheFolder.mkdirs();
            }
        }

        public void onMessageClick(Parcelable parcelable) {
            if (Utils.isNetConnected(EditDoctorFragment.this.getParentActivity())) {
                PhotoUploadTask photoUploadTask = new PhotoUploadTask();
                Uri[] uriArr = new Uri[]{EditDoctorFragment.this.mSelectedImageUri};
                photoUploadTask.execute(uriArr);
                return;
            }
            new SnackBar.Builder(EditDoctorFragment.this.getParentActivity()).withMessageId(R.string.no_network_connection).show();
        }
    }

    public class ProfileUpdateTask extends AsyncTask<JSONObject, Void, Boolean> implements SnackBar.OnMessageClickListener {
        private boolean mInitProgressDialog;
        private boolean mIsMobileUpdated = false;

        public ProfileUpdateTask(boolean initProgressDialog) {
            this.mInitProgressDialog = initProgressDialog;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            if (Utils.isActivityAlive(EditDoctorFragment.this.getParentActivity()) && this.mInitProgressDialog) {
                EditDoctorFragment.this.mProfileUpdateDialog = new Builder(EditDoctorFragment.this.getActivity()).content
                        (EditDoctorFragment.this.getString(R.string.edit_profile_update_progress)).progress(true, 0).cancelable
                        (false).autoDismiss(false).show();
            }
        }

        @Override
        protected Boolean doInBackground(JSONObject... params) {
            if (!EditDoctorFragment.this.mDoctorPrimaryNumberEt.getText().toString().equals(EditDoctorFragment.this.mDoctorPrimaryNumber)) {
                this.mIsMobileUpdated = true;
            }
            return patchDoctor(EditDoctorFragment.this.mDoctorFabricId, params[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            final Activity activity = EditDoctorFragment.this.getParentActivity();
            if (Utils.isActivityAlive(activity)){
                EditDoctorFragment.this.mProfileUpdateDialog.dismiss();
                if (result){
                    if (!Utils.isEmptyString(EditDoctorFragment.this.mDoctorPhotoNewUrl)) {
                        AppController.getInstance().getImageLoader().set(EditDoctorFragment.this.mDoctorPhotoNewUrl, EditDoctorFragment.this.mDoctorPhotoIv, ((BitmapDrawable) EditDoctorFragment.this.mDoctorPhotoIv.getDrawable()).getBitmap());
                    }
                    if (PreferenceUtils.getBooleanPrefs(activity, PreferenceUtils.DONT_SHOW_EDIT_SUCCESS_DIALOG).booleanValue()) {
                        new SnackBar.Builder(EditDoctorFragment.this.getActivity()).withMessageId(R.string.edit_profile_update_success).show();
                        activity.setResult(-1);
                        activity.finish();
                    } else {
                        Builder materialDialog = new Builder(activity);
                        materialDialog.title((int) R.string.edit_profile_update_success).customView((int) R.layout.dialog_edit_success, false).positiveText((int) R.string.ok).callback(new MaterialDialog.ButtonCallback() {
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                PreferenceUtils.set(activity, PreferenceUtils.DONT_SHOW_EDIT_SUCCESS_DIALOG, Boolean.valueOf(((CheckBox) dialog.findViewById(com.rengwuxian.materialedittext.R.id.checkbox)).isChecked()));
                                dialog.dismiss();
                                activity.setResult(-1);
                                activity.finish();
                            }
                        });
                        materialDialog.cancelable(false);
                        materialDialog.show();
                    }
                }else {
                    new SnackBar.Builder(EditDoctorFragment.this.getActivity()).withMessageId(R.string.edit_profile_update_failure).withActionMessageId(R.string.retry).withStyle(SnackBar.Style.INFO).withOnClickListener(this).show();
                }
                super.onPostExecute(result);
            }
        }

        @Override
        public void onMessageClick(Parcelable token) {
            if (Utils.isNetConnected(EditDoctorFragment.this.getParentActivity())) {
                ProfileUpdateTask profileUpdateTask = new ProfileUpdateTask(Boolean.TRUE.booleanValue());
                JSONObject[] jSONObjectArr = new JSONObject[]{EditDoctorFragment.this.mPatchJsonObject};
                    profileUpdateTask.execute(jSONObjectArr);
                    return;
            }
            new SnackBar.Builder(EditDoctorFragment.this.getParentActivity()).withMessageId(R.string.no_network_connection).show();
        }
    }


    public String uploadPhoto(String fileUrl, int fabricId) {
        if (fileUrl == null || TextUtils.isEmpty(fileUrl)) {
            return BuildConfig.FLAVOR;
        }

        SimpleMultiPartRequest multiPartRequest=new SimpleMultiPartRequest(Request.Method.POST, BaseURL.uploadImage+fabricId, null, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG,error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        multiPartRequest.addFile("photoFile",fileUrl);
        multiPartRequest.setFixedStreamingMode(true);
        RequestTickle volleyTickle = AppController.getInstance().getRequestTickle();
        volleyTickle.add(multiPartRequest);
        NetworkResponse response = volleyTickle.start();
        if (!isSuccessfulResponse(response)) {
            return BuildConfig.FLAVOR;
        }
        try {
            String parseResponse = VolleyTickle.parseResponse(response);
            return parseResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return BuildConfig.FLAVOR;
        }
    }


    public boolean patchDoctor(int doctorFabricId, JSONObject patchData) {
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.PATCH,BaseURL.updatePhysicianProfile+doctorFabricId,patchData,null,null);
        RequestTickle volleyTickle = AppController.getInstance().getRequestTickle();
        volleyTickle.add(jsonObjectRequest);
        NetworkResponse response = volleyTickle.start();
        if (isSuccessfulResponse(response)) {
            Gson gson = new Gson();
            try {
                String parseResponse = VolleyTickle.parseResponse(response);
                Class cls = PhysicianDetails.class;
                //insertOrUpdateDoctor(!(gson instanceof Gson) ? gson.fromJson(parseResponse, cls) : GsonInstrumentation.fromJson(gson, parseResponse, cls), UPDATE);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private void showYearPickerDialog()
    {
        Calendar localCalendar = Calendar.getInstance();
        int i = localCalendar.get(Calendar.YEAR);
        String str = this.mDoctorExperienceEt.getText().toString();
        if (!TextUtils.isEmpty(str)) {
            this.mDoctorExperience = Integer.parseInt(str);
        }
        MaterialDialog localMaterialDialog = new MaterialDialog.Builder(getParentActivity()).title(R.string.edit_profile_select_year).customView(R.layout.dialog_date_picker, false).positiveText(R.string.done).negativeText(R.string.cancel).callback(new MaterialDialog.ButtonCallback() {
            public void onNegative(MaterialDialog paramAnonymousMaterialDialog) {
                super.onNegative(paramAnonymousMaterialDialog);
                //mDocRegistrationYearEt.setText(String.valueOf(mDoctorExperience));
            }

            public void onPositive(MaterialDialog paramAnonymousMaterialDialog) {
                super.onPositive(paramAnonymousMaterialDialog);
                NumberPicker localNumberPicker = (NumberPicker) paramAnonymousMaterialDialog.findViewById(R.id.year_number_picker);
                mDoctorRegistrationYearEt.setText(String.valueOf(localNumberPicker.getValue()));
                paramAnonymousMaterialDialog.dismiss();
            }
        }).build();
        NumberPicker localNumberPicker = (NumberPicker)localMaterialDialog.getCustomView().findViewById(R.id.year_number_picker);
        localNumberPicker.setMaxValue(i);
        if (this.mDoctorExperience > 0) {
            localCalendar.add(Calendar.YEAR, -(1 + this.mDoctorExperience));
        }
        localNumberPicker.setValue(localCalendar.get(Calendar.YEAR));
        localNumberPicker.setMinValue(1900);
        localMaterialDialog.show();
    }

    private void showSpecializationDialog(){
        sBoolean=true;
        LayoutInflater inflater=(LayoutInflater) getParentActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.generallistview, null);
        ListView listView= (ListView) view.findViewById(R.id.generalListView);
        listView.setOnItemClickListener(this);
        listView.setAdapter(specializationAdapter);
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getParentActivity());
        alertDialog.setView(view);
        alertDialog.setCancelable(true);
        specializationDialog=alertDialog.create();
        specializationDialog.show();
    }

    private void showQualificationDialog(){
        qBoolean=true;
        LayoutInflater inflater=(LayoutInflater) getParentActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.generallistview, null);
        ListView listView= (ListView) view.findViewById(R.id.generalListView);
        listView.setOnItemClickListener(this);
        listView.setAdapter(qualificationAdapter);
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getParentActivity());
        alertDialog.setView(view);
        alertDialog.setCancelable(true);
        qualificationDialog=alertDialog.create();
        qualificationDialog.show();
    }

    private void showRegistrationDialog(){
        aBoolean=true;
        LayoutInflater inflater=(LayoutInflater) getParentActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.generallistview, null);
        ListView listView= (ListView) view.findViewById(R.id.generalListView);
        listView.setOnItemClickListener(this);
        listView.setAdapter(affiliationAdapter);
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getParentActivity());
        alertDialog.setView(view);
        alertDialog.setCancelable(true);
        registrationDialog=alertDialog.create();
        registrationDialog.show();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE)
        {
            updatePhysicianProfileDetails();
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestFocus();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void getSpecializationList(String url){
        specializationList.clear();
        JsonArrayRequest getPatientSpecializationList=new JsonArrayRequest(url, new Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Specialization specialization=gson.fromJson(jsonObject.toString(), Specialization.class);
                        specializationList.add(specialization);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                specializationAdapter.notifyDataSetChanged();
            }
        },errorListener);

        getPatientSpecializationList.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(getPatientSpecializationList, TAG);
    }
    public void getQualificationList(String url){
        qualificationList.clear();
        JsonArrayRequest getPatientQualificationList=new JsonArrayRequest(url, new Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Qualification qualification=gson.fromJson(jsonObject.toString(), Qualification.class);
                        qualificationList.add(qualification);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                qualificationAdapter.notifyDataSetChanged();
            }
        }, errorListener);

        getPatientQualificationList.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(getPatientQualificationList, TAG);
    }

    public void getAffiliationList(String url){
        affliationList.clear();
        JsonArrayRequest getPatientAffiliation=new JsonArrayRequest(url, new Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Affliation affliation=gson.fromJson(jsonObject.toString(), Affliation.class);
                        affliationList.add(affliation);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                affiliationAdapter.notifyDataSetChanged();
            }
        }, errorListener);

        getPatientAffiliation.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(getPatientAffiliation, TAG);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (sBoolean){
            if (mSelectedSpecializationMap.size()>0){
                mSelectedSpecializationMap.clear();
            }
            mSelectedSpecializationMap.put(specializationList.get(position).getSpecialization_id(), specializationList.get(position).getSpecializations());
            mDoctorSpecializationEt.setText(specializationList.get(position).getSpecializations());
            specializationDialog.dismiss();
            sBoolean=false;
        }else if (qBoolean){
            if (mSelectedQualificationMap.size()>0){
                mSelectedQualificationMap.clear();
            }
            mSelectedQualificationMap.put(qualificationList.get(position).getQualification_id(), qualificationList.get(position).getQualification());
            mDoctorQualificationEt.setText(qualificationList.get(position).getQualification());
            qualificationDialog.dismiss();
            qBoolean=false;
        }else if(aBoolean){
            if (mSelectedRegistrationCouncilMap.size()>0){
                mSelectedRegistrationCouncilMap.clear();
            }
            mSelectedRegistrationCouncilMap.put(affliationList.get(position).getAffiliation_id(), affliationList.get(position).getUniversityName());
            mDoctorRegistrationCouncilEt.setText(affliationList.get(position).getUniversityName());
            registrationDialog.dismiss();
            aBoolean=false;
        }
    }
}
