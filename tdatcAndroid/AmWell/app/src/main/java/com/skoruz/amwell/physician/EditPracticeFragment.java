package com.skoruz.amwell.physician;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.ButtonCallback;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestTickle;
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
import com.google.gson.JsonParser;
import com.skoruz.amwell.BuildConfig;
import com.skoruz.amwell.LoginActivity;
import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.CityAdapter;
import com.skoruz.amwell.adapter.ClinicTimingsAdapter;
import com.skoruz.amwell.adapter.LocalityAdapter;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.misc.FileUtils;
import com.skoruz.amwell.misc.GeoLocation;
import com.skoruz.amwell.patientEntity.City;
import com.skoruz.amwell.patientEntity.Locality;
import com.skoruz.amwell.patientEntity.VisibilityTimingId;
import com.skoruz.amwell.physicianEntity.Clinic;
import com.skoruz.amwell.physicianEntity.PhysicianDetails;
import com.skoruz.amwell.physicianEntity.VisitingTimings;
import com.skoruz.amwell.service.SynapseService;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.InputFilterMinMax;
import com.skoruz.amwell.utils.PreferenceUtils;
import com.skoruz.amwell.utils.TimeUtils;
import com.skoruz.amwell.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditPracticeFragment extends BaseFragment implements View.OnClickListener,LocationListener,
        AdapterView.OnItemClickListener{
    public static final String BUNDLE_ADDRESS = "address";
    public static final String BUNDLE_CITY = "city";
    public static final String BUNDLE_LOCALITY = "locality";
    public static final String BUNDLE_PRIMARY_NUMBER = "primary_number";
    public static final int FOCUS_FIELD_CONSULTATION_FEE = 3;
    public static final int FOCUS_FIELD_LOCALITY = 4;
    public static final int FOCUS_FIELD_PRIMARY_NUMBER = 1;
    public static final String KEY_ADDED = "added";
    public static final String KEY_CREATE_OWNER = "create_owner";
    public static final String KEY_DOCTOR_ID = "phyId";
    public static final String KEY_PRACTICE_CITY = "city_id";
    public static final String KEY_PRACTICE_LOCALITY = "locality_id";
    public static final String KEY_PRACTICE_NAME = "clinicName";
    public static final String KEY_PRACTICE_PHOTOS = "photos";
    public static final String KEY_PRACTICE_PHOTO_LOGO = "logo";
    public static final String KEY_PRACTICE_PHOTO_URL = "url";
    public static final String KEY_PRACTICE_PRIMARY_NUMBER = "contactNumber";
    public static final String KEY_PRACTICE_SPECIALITIES = "specialities";
    public static final String KEY_PRACTICE_SPECIALITY_DELETED_ID = "id";
    public static final String KEY_PRACTICE_SPECIALITY_ID = "speciality_id";
    public static final String KEY_PRACTICE_STREET_ADDRESS = "streetAddress";
    public static final String KEY_RELATION_CONSULTATION_FEE = "consultationFees";
    public static final String KEY_RELATION_FREE_CONSULTATION = "free_consultation";
    public static final String KEY_RELATION_PROFILE_PUBLISHED = "profile_published";
    public static final String KEY_SOFT_DELETED = "soft_deleted";
    public static final String KEY_STATUS = "status";
    public static final String KEY_UPDATED = "updated";
    private static final int REQUEST_SELECT_LOCALITY = 5008;
    private static final int REQUEST_SELECT_SPECIALITY = 5009;
    private LinearLayout mContentLl;
    private int mDoctorFabricId;
    private boolean mHasPhoto;
    private IntentFilter mIntentFilter;
    private boolean mIsEditing;
    private LocalBroadcastManager mLocalBroadcastManager;
    private ImageView mLocateImageButton;
    private EditText mPracticeCityEt;
    private EditText mPracticeLocalityEt;
    private EditText mPracticeDoctorFeeEt;
    private int mPracticeFabricId;
    private int mPracticeId;
    private EditText mPracticeNameEt;
    private String mPracticeNewPhotoFilePath;
    private NetworkImageView mPracticePhotoIv;
    private String mPracticePhotoNewUrl;
    private String mPracticePhotoOldUrl;
    private JSONObject mPracticePostOrPatchData;
    private EditText mPracticePrimaryNumberEt;
    private EditText mPracticeSpecialityEt;
    private ArrayList<String> mPracticeSpecialityFabricList;
    private ArrayList<String> mPracticeSpecialityMasterList;
    private EditText mPracticeStreetAddressEt;
    private MaterialDialog mProfileUpdateDialog;
    private RelativeLayout mProgressRl;
    //private BackgroundReceiver mReceiver;
    private int mRelationFabricId;
    private JSONObject mRelationPostOrPatchData;
    private ScrollView mScrollView;
    private HashMap<Integer, String> mSelectedCityMap;
    private Uri mSelectedImageUri;
    private HashMap<Integer, String> mSelectedLocalityMap;
    private HashMap<Long, String> mSelectedSpecialityMap;
    public static final String KEY_PRACTICE_ID = "clinic_id";
    private ArrayList<Integer> mPracticeIdList;
    private EditText mPracticeTimingsEt;
    private ImageButton mPracticePhotoEditIb;
    private ProgressBar mLocationProgressBar;
    private String mAction;
    private String mClinicTimings;
    public static final int REQUEST_CLINIC_TIMINGS = 5009;
    private static final String BC = "</b>";
    private static final String BO = "<b>";
    private static final String BR = "<br/>";
    private static final String CLOSED = "CLOSED";
    private static final String COMMA = ", ";
    public static final String COUNTRY_INDIA = "india";
    private AppCompatActivity mActivity;
    private AlertDialog mRequestPhotoDialog;
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    protected File mUploadCacheFolder;
    protected String mOriginalImagePathString;
    protected String mUploadImagePathString;
    protected static final int REQUEST_CAPTURE_IMAGE = 5011;
    protected static final int REQUEST_SELECT_IMAGE = 5007;
    public static final String MIME_TYPE_IMAGE = "image/*";
    private String mSelectedImagePath;
    private ArrayList<Clinic> clinicList;
    private PhysicianDetails physicianDetails;
    private Gson gson=new Gson();
    private SharedPreferences sharedPreferences;
    public static final String FREE = "Free";
    public static final String ZERO = "0";
    private BackgroundReceiver mReceiver;
    private ArrayList<City> citiesList;
    private ArrayList<Locality> localityList;
    private static final String TAG=EditPracticeFragment.class.getSimpleName();
    private CityAdapter cityAdapter;
    private LocalityAdapter localityAdapter;
    private AlertDialog cityDialog=null;
    private AlertDialog localityDialog=null;
    private Boolean cBoolean,lBoolean;
    private int cityId;
    private SharedPreferences.Editor editor;
    private TelephonyManager telephonyManager;
    private GsmCellLocation cellLocation;
    private String countryCode;
    private ImageLoader imageLoader;
    private static final long MAX_FILE_SIZE_KB = 10485760;
    private File uploadFile;
    private Uri profileFileUri;
    private String selected_profile_image_path;
    private String selected_profile_image_name;

    private class BackgroundReceiver extends BroadcastReceiver {
        private BackgroundReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(LoginActivity.ACTION_LOCATION_CHANGED)) {
                String locality = intent.getStringExtra(BUNDLE_LOCALITY);
                if (Utils.isEmptyString(locality)) {
                    Toast.makeText(EditPracticeFragment.this.getParentActivity(), "No precise location found!", Toast.LENGTH_SHORT).show();
                } else {
                    EditPracticeFragment.this.mPracticeStreetAddressEt.setText(locality);
                }
                EditPracticeFragment.this.mLocationProgressBar.setVisibility(View.GONE);
                EditPracticeFragment.this.mLocateImageButton.setVisibility(View.VISIBLE);
            }
        }
    }

    public EditPracticeFragment() {
        // Required empty public constructor
    }

    public static EditPracticeFragment newInstance(Bundle paramBundle)
    {
        EditPracticeFragment localEditPracticeFragment = new EditPracticeFragment();
        localEditPracticeFragment.setArguments(paramBundle);
        return localEditPracticeFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        this.mIntentFilter = new IntentFilter();
        this.mIntentFilter.addAction(LoginActivity.ACTION_LOCATION_CHANGED);
        this.mReceiver = new BackgroundReceiver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View editPracticeView=inflater.inflate(R.layout.fragment_edit_practice, container, false);
        telephonyManager = (TelephonyManager)getParentActivity().getSystemService(Context.TELEPHONY_SERVICE);
        countryCode=telephonyManager.getSimCountryIso();
        if (countryCode!=null){
            getStatesFromCountryCode(BaseURL.getStateBasedCountryCode,countryCode);
        }
        Bundle bundle=getArguments();
        if (bundle!=null){
            this.mPracticeId=bundle.getInt(KEY_PRACTICE_ID,0);
            this.mDoctorFabricId=bundle.getInt(KEY_DOCTOR_ID,0);
            this.mRelationFabricId=bundle.getInt(BaseEditorActivity.EXTRA_RELATION_ID,0);
            this.mPracticeIdList = bundle.getIntegerArrayList(BaseEditorActivity.EXTRA_PRACTICE_FABRIC_IDS);
            this.mAction = bundle.getString(BaseEditorActivity.ACTION, BuildConfig.FLAVOR);
        }
        String title=getString(R.string.edit_practice_title);
        if (mPracticeId<=0){
            title=getString(R.string.add_practice_title);
        }
        super.initEditToolbar((Toolbar) editPracticeView.findViewById(R.id.toolbarDetails), title, false);
        this.mPracticeNameEt = (EditText) editPracticeView.findViewById(R.id.edit_practice_et_name);
        this.mPracticePrimaryNumberEt = (EditText) editPracticeView.findViewById(R.id.edit_practice_et_primary_mobile);
        this.mPracticeCityEt = (EditText) editPracticeView.findViewById(R.id.edit_practice_et_city);
        this.mPracticeLocalityEt= (EditText) editPracticeView.findViewById(R.id.edit_practice_et_locality);
        this.mPracticeTimingsEt = (EditText) editPracticeView.findViewById(R.id.edit_practice_et_timings);
        this.mPracticeStreetAddressEt = (EditText) editPracticeView.findViewById(R.id.edit_practice_et_street_address);
        this.mPracticeDoctorFeeEt = (EditText) editPracticeView.findViewById(R.id.edit_practice_et_fees);
        EditText editText = this.mPracticeDoctorFeeEt;
        InputFilter[] inputFilterArr = new InputFilter[FOCUS_FIELD_PRIMARY_NUMBER];
        inputFilterArr[0] = new InputFilterMinMax("1", "1000000");
        editText.setFilters(inputFilterArr);
        this.mPracticePhotoIv = (NetworkImageView) editPracticeView.findViewById(R.id.edit_practice_iv_photo);
        this.mPracticePhotoEditIb = (ImageButton) editPracticeView.findViewById(R.id.edit_practice_btn_photo);
        this.mPracticeCityEt.setOnClickListener(this);
        this.mPracticeLocalityEt.setOnClickListener(this);
        this.mPracticePhotoEditIb.setOnClickListener(this);
        this.mPracticePhotoIv.setOnClickListener(this);
        this.mPracticeTimingsEt.setOnClickListener(this);
        setHasOptionsMenu(Boolean.TRUE.booleanValue());
        this.mLocateImageButton = (ImageView) editPracticeView.findViewById(R.id.edit_practice_ib_locate);
        this.mLocateImageButton.setOnClickListener(this);
        this.mLocationProgressBar = (ProgressBar) editPracticeView.findViewById(R.id.progress_bar_locate);
        this.mContentLl = (LinearLayout) editPracticeView.findViewById(R.id.edit_practice_ll_content);
        //this.mSuggestionProgressBar = (ProgressBar) editPracticeView.findViewById(R.id.progress_bar_suggest);
        this.mProgressRl = (RelativeLayout) editPracticeView.findViewById(R.id.edit_practice_rl_content_progress);
        this.mScrollView = (ScrollView) editPracticeView.findViewById(R.id.scroll_view);
        mSelectedCityMap=new HashMap<>();
        mSelectedLocalityMap=new HashMap<>();

        sharedPreferences=getActivity().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        String physicianDetailsJson=sharedPreferences.getString("physician_details", null);
        physicianDetails=gson.fromJson(physicianDetailsJson, PhysicianDetails.class);
        //cityAdapter=new CityAdapter(getParentActivity(),citiesList);
        if (getArguments()!=null){
            mDoctorFabricId=getArguments().getInt(BaseEditorActivity.EXTRA_DOCTOR_ID);
            mPracticeId=getArguments().getInt(BaseEditorActivity.EXTRA_PRACTICE_ID);
        }
        if (this.mPracticeId <=0) {
            this.mProgressRl.setVisibility(View.GONE);
        } else {
            //initLoader(LOADER_ID, Boolean.FALSE.booleanValue(), this);
            initData();
        }
        return editPracticeView;
    }

    public void getStatesFromCountryCode(String url,String cCode){
        JsonArrayRequest stateRequest=new JsonArrayRequest(url + cCode, new Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        stateRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(stateRequest, TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestFocus();
        this.mLocalBroadcastManager.registerReceiver(this.mReceiver, this.mIntentFilter);
    }

    public void onPause() {
        super.onPause();
        stopLocationUpdate();
        if (this.mReceiver != null) {
            this.mLocalBroadcastManager.unregisterReceiver(this.mReceiver);
        }
    }

    private void requestFocus() {
        if (getArguments() != null) {
            switch (getArguments().getInt(BaseEditorActivity.EXTRA_FOCUS_FIELD)) {
                case FOCUS_FIELD_PRIMARY_NUMBER:
                    this.mPracticePrimaryNumberEt.requestFocus();
                    break;
                case FOCUS_FIELD_CONSULTATION_FEE:
                    this.mPracticeDoctorFeeEt.requestFocus();
                    break;
                case FOCUS_FIELD_LOCALITY:
                    this.mPracticeStreetAddressEt.requestFocus();
                    break;
            }
            getArguments().remove(BaseEditorActivity.EXTRA_FOCUS_FIELD);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_base_editor, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==getParentActivity().RESULT_OK){
        switch (requestCode) {
            case REQUEST_CLINIC_TIMINGS:
                    this.mClinicTimings = data.getStringExtra(BaseTimingsActivity.VISITTIMINGS);
                    if (this.mClinicTimings == null) {
                        this.mClinicTimings = BuildConfig.FLAVOR;
                        return;
                    }
                    try {
                        setTimingsEditText(new JSONObject(this.mClinicTimings), this.mPracticeTimingsEt);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }
                return;
            case REQUEST_SELECT_IMAGE:
                this.mSelectedImageUri = data.getData();
                super.setPicture(this.mSelectedImageUri, this.mPracticePhotoIv);
                return;
            case REQUEST_CAPTURE_IMAGE:
                if (!TextUtils.isEmpty(this.mCurrentPhotoPath)) {
                    this.mSelectedImageUri = Uri.parse(this.mCurrentPhotoPath);
                    super.setPicture(this.mSelectedImageUri, this.mPracticePhotoIv);
                    return;
                }
                return;
        }
    }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_practiceDetails:
                updatePracticeDetails();
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

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        if (savedInstanceState != null) {
            this.mPracticeNameEt.setText(savedInstanceState.getString(KEY_PRACTICE_NAME));
            this.mPracticePrimaryNumberEt.setText(savedInstanceState.getString(BUNDLE_PRIMARY_NUMBER));
            this.mPracticeCityEt.setText(savedInstanceState.getString(BUNDLE_CITY));
            this.mPracticeLocalityEt.setText(savedInstanceState.getString(BUNDLE_LOCALITY));
            this.mPracticeStreetAddressEt.setText(savedInstanceState.getString(BUNDLE_ADDRESS));
            this.mPracticeDoctorFeeEt.setText(savedInstanceState.getString(KEY_RELATION_CONSULTATION_FEE));
            this.mUploadImagePathString = savedInstanceState.getString("bundle_upload_image_path");
            this.mOriginalImagePathString = savedInstanceState.getString("bundle_original_image_path");
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_PRACTICE_NAME, this.mPracticeNameEt.getText().toString());
        outState.putString(BUNDLE_PRIMARY_NUMBER, this.mPracticePrimaryNumberEt.getText().toString());
        outState.putString(BUNDLE_CITY, this.mPracticeCityEt.getText().toString());
        outState.putString(BUNDLE_LOCALITY,this.mPracticeLocalityEt.getText().toString());
        outState.putString(BUNDLE_ADDRESS, this.mPracticeStreetAddressEt.getText().toString());
        outState.putString(KEY_RELATION_CONSULTATION_FEE, this.mPracticeDoctorFeeEt.getText().toString());
        if (!TextUtils.isEmpty(this.mOriginalImagePathString) && !TextUtils.isEmpty(this.mUploadImagePathString)) {
            outState.putString("bundle_original_image_path", this.mOriginalImagePathString);
            outState.putString("bundle_upload_image_path", this.mUploadImagePathString);
        }
    }

    private void updatePracticeDetails(){
        Utils.hideKeyboard(getParentActivity());
        boolean isValidInput = true;
        String practiceName = this.mPracticeNameEt.getText().toString();
        String practicePrimaryNumber = this.mPracticePrimaryNumberEt.getText().toString();
        String practiceStreetAddress = this.mPracticeStreetAddressEt.getText().toString();
        String doctorConsultationFee = this.mPracticeDoctorFeeEt.getText().toString();
        if (doctorConsultationFee.equals("Free")) {
            doctorConsultationFee = "0";
        }
        this.mPracticePostOrPatchData = new JSONObject();
        this.mRelationPostOrPatchData = new JSONObject();
        try {
            //mPracticePostOrPatchData.put(KEY_PRACTICE_ID,mPracticeId);
            if (Utils.isEmptyString(practiceStreetAddress)) {
                this.mPracticeStreetAddressEt.setError(getString(R.string.edit_practice_error_address));
                this.mPracticeStreetAddressEt.requestFocus();
                isValidInput = false;
            } else {
                this.mPracticePostOrPatchData.put(KEY_PRACTICE_STREET_ADDRESS, practiceStreetAddress);
            }
            /*if (Utils.isEmptyMap(this.mSelectedCityMap)) {
                this.mPracticeCityEt.setError(getString(R.string.edit_practice_error_locality));
                this.mScrollView.fullScroll(130);
                isValidInput = false;
            } else {
                if (!Utils.isEmptyMap(this.mSelectedLocalityMap)) {
                    this.mPracticePostOrPatchData.put(KEY_PRACTICE_LOCALITY, this.mSelectedLocalityMap.keySet().toArray()[0]);
                }
                this.mPracticePostOrPatchData.put(KEY_PRACTICE_CITY, this.mSelectedCityMap.keySet().toArray()[0]);
            }*/
            String city=mPracticeCityEt.getText().toString();
            if (Utils.isEmptyString(city)){
                this.mPracticeCityEt.setError(getString(R.string.edit_doctor_error_city));
                this.mScrollView.fullScroll(130);
                isValidInput=false;
            }else {
                this.mPracticePostOrPatchData.put(BUNDLE_CITY,city);
            }

            String locality=mPracticeLocalityEt.getText().toString();
            if (Utils.isEmptyString(locality)){
                this.mPracticeLocalityEt.setError(getString(R.string.edit_doctor_error_locality));
                this.mScrollView.fullScroll(130);
                isValidInput=false;
            }else {
                this.mPracticePostOrPatchData.put(BUNDLE_LOCALITY,locality);
            }

            if (Utils.isEmptyString(practicePrimaryNumber)) {
                this.mPracticePrimaryNumberEt.setError(getString(R.string.edit_practice_error_primary_mobile));
                this.mPracticePrimaryNumberEt.requestFocus();
                isValidInput = false;
            } else {
                if (Utils.isValidNumber(practicePrimaryNumber,Utils.getCountryCode(COUNTRY_INDIA))) {
                    this.mPracticePostOrPatchData.put(KEY_PRACTICE_PRIMARY_NUMBER, practicePrimaryNumber);
                } else {
                    this.mPracticePrimaryNumberEt.setError(getString(R.string.edit_error_primary_number_invalid));
                    this.mPracticePrimaryNumberEt.requestFocus();
                    isValidInput = false;
                }
            }
            if (Utils.isEmptyString(practiceName)) {
                this.mPracticeNameEt.setError(getString(R.string.edit_practice_error_name));
                this.mPracticeNameEt.requestFocus();
                isValidInput = false;
            } else if (practiceName.length() < FOCUS_FIELD_CONSULTATION_FEE || practiceName.length() > 50) {
                this.mPracticeNameEt.setError(Html.fromHtml(getString(R.string.edit_name_error_length)));
                this.mPracticeNameEt.requestFocus();
                isValidInput = false;
            } else {
                this.mPracticePostOrPatchData.put(KEY_PRACTICE_NAME, practiceName);
            }
            if (!Utils.isEmptyString(doctorConsultationFee) && !doctorConsultationFee.equals("0")) {
                this.mPracticePostOrPatchData.put(KEY_RELATION_CONSULTATION_FEE, doctorConsultationFee);
            } else if (doctorConsultationFee.equals("0")) {
                this.mPracticePostOrPatchData.put(KEY_RELATION_CONSULTATION_FEE, 0);
            } else {
                this.mPracticePostOrPatchData.putOpt(KEY_RELATION_CONSULTATION_FEE, JSONObject.NULL);
            }
            this.mPracticePostOrPatchData.put(KEY_DOCTOR_ID, this.mDoctorFabricId);
            if (this.mClinicTimings != null) {
                //this.mPracticePostOrPatchData.put(BaseTimingsActivity.VISITTIMINGS, new JSONObject(this.mClinicTimings));
                if (mPracticeId>0){
                    JSONArray jsonArray=getTimingData(this.mClinicTimings,mPracticeId,mDoctorFabricId);
                    mPracticePostOrPatchData.put(BaseTimingsActivity.VISITTIMINGS,jsonArray);
                }
            }
            if (!isValidInput) {
                return;
            }
            /*ProfileUpdateTask profileUpdateTask=new ProfileUpdateTask(Boolean.TRUE.booleanValue());
            JSONObject[] jSONObjectArr = new JSONObject[]{this.mPracticePostOrPatchData, this.mRelationPostOrPatchData};
            profileUpdateTask.execute(jSONObjectArr);*/

            if (EditPracticeFragment.this.mPracticeId <= 0) {
                postPracticeDetails(this.mPracticePostOrPatchData);
            } else {
                this.mPracticePostOrPatchData.put(KEY_PRACTICE_ID, mPracticeId);
                patchPracticeDetails(this.mPracticePostOrPatchData,mPracticeId);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public JSONArray getTimingData(String clinicTimings,int practiceId,int doctorId){
        JSONArray jsonArray=new JSONArray();
        Gson gson=new Gson();
        JsonParser jsonParser = new JsonParser();
        try {
            JSONObject jsonObject=new JSONObject(clinicTimings);
            //ArrayList<VisitingTimings> visitingTimingsList=new ArrayList<>();
            JSONObject mondayJsonObject=jsonObject.getJSONObject(BaseTimingsActivity.MONDAY);
            JSONObject tuesdayJsonObject=jsonObject.getJSONObject(BaseTimingsActivity.TUESDAY);
            JSONObject wednesdayJsonObject=jsonObject.getJSONObject(BaseTimingsActivity.WEDNESDAY);
            JSONObject thursdayJsonObject=jsonObject.getJSONObject(BaseTimingsActivity.THURSDAY);
            JSONObject fridayJsonObject=jsonObject.getJSONObject(BaseTimingsActivity.FRIDAY);
            JSONObject saturdayJsonObject=jsonObject.getJSONObject(BaseTimingsActivity.SATURDAY);
            JSONObject sundayJsonObject=jsonObject.getJSONObject(BaseTimingsActivity.SUNDAY);

            if (mondayJsonObject!=null){
                VisitingTimings visitingTimings=new VisitingTimings();
                VisibilityTimingId visibilityTimingId=new VisibilityTimingId();
                visibilityTimingId.setClinicId(practiceId);
                visibilityTimingId.setTiming_day(BaseTimingsActivity.MONDAY);
                visitingTimings.setId(visibilityTimingId);
                visitingTimings.setDoctorId(doctorId);
                visitingTimings.setSession1_start_time(mondayJsonObject.getString(ClinicTimingsAdapter.SESSION1_START_TIME));
                visitingTimings.setSession1_end_time(mondayJsonObject.getString(ClinicTimingsAdapter.SESSION1_END_TIME));
                visitingTimings.setSession2_start_time(mondayJsonObject.getString(ClinicTimingsAdapter.SESSION2_START_TIME));
                visitingTimings.setSession2_end_time(mondayJsonObject.getString(ClinicTimingsAdapter.SESSION2_END_TIME));
                String mondayString=gson.toJson(visitingTimings);
                JSONObject mondayJson=new JSONObject(mondayString);
                jsonArray.put(mondayJson);
            }
            if (tuesdayJsonObject!=null){
                VisitingTimings visitingTimings=new VisitingTimings();
                VisibilityTimingId visibilityTimingId=new VisibilityTimingId();
                visibilityTimingId.setClinicId(practiceId);
                visibilityTimingId.setTiming_day(BaseTimingsActivity.TUESDAY);
                visitingTimings.setId(visibilityTimingId);
                visitingTimings.setDoctorId(doctorId);
                visitingTimings.setSession1_start_time(tuesdayJsonObject.getString(ClinicTimingsAdapter.SESSION1_START_TIME));
                visitingTimings.setSession1_end_time(tuesdayJsonObject.getString(ClinicTimingsAdapter.SESSION1_END_TIME));
                visitingTimings.setSession2_start_time(tuesdayJsonObject.getString(ClinicTimingsAdapter.SESSION2_START_TIME));
                visitingTimings.setSession2_end_time(tuesdayJsonObject.getString(ClinicTimingsAdapter.SESSION2_END_TIME));
                String mondayString=gson.toJson(visitingTimings);
                JSONObject mondayJson=new JSONObject(mondayString);
                jsonArray.put(mondayJson);
            }
            if (wednesdayJsonObject!=null){
                VisitingTimings visitingTimings=new VisitingTimings();
                VisibilityTimingId visibilityTimingId=new VisibilityTimingId();
                visibilityTimingId.setClinicId(practiceId);
                visibilityTimingId.setTiming_day(BaseTimingsActivity.WEDNESDAY);
                visitingTimings.setId(visibilityTimingId);
                visitingTimings.setDoctorId(doctorId);
                visitingTimings.setSession1_start_time(wednesdayJsonObject.getString(ClinicTimingsAdapter.SESSION1_START_TIME));
                visitingTimings.setSession1_end_time(wednesdayJsonObject.getString(ClinicTimingsAdapter.SESSION1_END_TIME));
                visitingTimings.setSession2_start_time(wednesdayJsonObject.getString(ClinicTimingsAdapter.SESSION2_START_TIME));
                visitingTimings.setSession2_end_time(wednesdayJsonObject.getString(ClinicTimingsAdapter.SESSION2_END_TIME));
                String mondayString=gson.toJson(visitingTimings);
                JSONObject mondayJson=new JSONObject(mondayString);
                jsonArray.put(mondayJson);
            }
            if (thursdayJsonObject!=null){
                VisitingTimings visitingTimings=new VisitingTimings();
                VisibilityTimingId visibilityTimingId=new VisibilityTimingId();
                visibilityTimingId.setClinicId(practiceId);
                visibilityTimingId.setTiming_day(BaseTimingsActivity.THURSDAY);
                visitingTimings.setId(visibilityTimingId);
                visitingTimings.setDoctorId(doctorId);
                visitingTimings.setSession1_start_time(thursdayJsonObject.getString(ClinicTimingsAdapter.SESSION1_START_TIME));
                visitingTimings.setSession1_end_time(thursdayJsonObject.getString(ClinicTimingsAdapter.SESSION1_END_TIME));
                visitingTimings.setSession2_start_time(thursdayJsonObject.getString(ClinicTimingsAdapter.SESSION2_START_TIME));
                visitingTimings.setSession2_end_time(thursdayJsonObject.getString(ClinicTimingsAdapter.SESSION2_END_TIME));
                String thursdayString=gson.toJson(visitingTimings);
                JSONObject thursdayJson=new JSONObject(thursdayString);
                jsonArray.put(thursdayJson);
            }
            if (fridayJsonObject!=null){
                VisitingTimings visitingTimings=new VisitingTimings();
                VisibilityTimingId visibilityTimingId=new VisibilityTimingId();
                visibilityTimingId.setClinicId(practiceId);
                visibilityTimingId.setTiming_day(BaseTimingsActivity.FRIDAY);
                visitingTimings.setId(visibilityTimingId);
                visitingTimings.setDoctorId(doctorId);
                visitingTimings.setSession1_start_time(fridayJsonObject.getString(ClinicTimingsAdapter.SESSION1_START_TIME));
                visitingTimings.setSession1_end_time(fridayJsonObject.getString(ClinicTimingsAdapter.SESSION1_END_TIME));
                visitingTimings.setSession2_start_time(fridayJsonObject.getString(ClinicTimingsAdapter.SESSION2_START_TIME));
                visitingTimings.setSession2_end_time(fridayJsonObject.getString(ClinicTimingsAdapter.SESSION2_END_TIME));
                String fridayString=gson.toJson(visitingTimings);
                JSONObject fridayJson=new JSONObject(fridayString);
                jsonArray.put(fridayJson);
            }
            if (saturdayJsonObject!=null){
                VisitingTimings visitingTimings=new VisitingTimings();
                VisibilityTimingId visibilityTimingId=new VisibilityTimingId();
                visibilityTimingId.setClinicId(practiceId);
                visibilityTimingId.setTiming_day(BaseTimingsActivity.SATURDAY);
                visitingTimings.setId(visibilityTimingId);
                visitingTimings.setDoctorId(doctorId);
                visitingTimings.setSession1_start_time(saturdayJsonObject.getString(ClinicTimingsAdapter.SESSION1_START_TIME));
                visitingTimings.setSession1_end_time(saturdayJsonObject.getString(ClinicTimingsAdapter.SESSION1_END_TIME));
                visitingTimings.setSession2_start_time(saturdayJsonObject.getString(ClinicTimingsAdapter.SESSION2_START_TIME));
                visitingTimings.setSession2_end_time(saturdayJsonObject.getString(ClinicTimingsAdapter.SESSION2_END_TIME));
                String saturdayString=gson.toJson(visitingTimings);
                JSONObject saturdayJson=new JSONObject(saturdayString);
                jsonArray.put(saturdayJson);
            }
            if (sundayJsonObject!=null){
                VisitingTimings visitingTimings=new VisitingTimings();
                VisibilityTimingId visibilityTimingId=new VisibilityTimingId();
                visibilityTimingId.setClinicId(practiceId);
                visibilityTimingId.setTiming_day(BaseTimingsActivity.SUNDAY);
                visitingTimings.setId(visibilityTimingId);
                visitingTimings.setDoctorId(doctorId);
                visitingTimings.setSession1_start_time(sundayJsonObject.getString(ClinicTimingsAdapter.SESSION1_START_TIME));
                visitingTimings.setSession1_end_time(sundayJsonObject.getString(ClinicTimingsAdapter.SESSION1_END_TIME));
                visitingTimings.setSession2_start_time(sundayJsonObject.getString(ClinicTimingsAdapter.SESSION2_START_TIME));
                visitingTimings.setSession2_end_time(sundayJsonObject.getString(ClinicTimingsAdapter.SESSION2_END_TIME));
                String sundayString=gson.toJson(visitingTimings);
                JSONObject sundayJson=new JSONObject(sundayString);
                jsonArray.put(sundayJson);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    @Override
    public void onStop() {
        super.onStop();
        AppController.getInstance().cancelPendingRequests(TAG);
    }

    private ErrorListener errorListener = new ErrorListener() {
        public void onErrorResponse(VolleyError error) {
            VolleyLog.d(TAG, "Error: " + error.getMessage());
            EditPracticeFragment.this.mProfileUpdateDialog.dismiss();
        }
    };

    public void postPracticeDetails(final JSONObject patchData){
        final JSONObject jsonData=patchData;
        final Gson gson=new Gson();
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.POST, BaseURL.createClinicDetails, patchData, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    Clinic clinic = gson.fromJson(jsonObject.toString(), Clinic.class);
                    JSONArray jsonArray = getTimingData(mClinicTimings, clinic.getClinic_id(), clinic.getPhyId());
                    patchPracticeDetails(jsonObject.put("clinicVisitingTimings", jsonArray), clinic.getClinic_id());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        }, TAG);
    }

    public void patchPracticeDetails(JSONObject patchData,int practiceId){
        final int clinic_id=practiceId;
        final JSONObject jsonData=patchData;
        if (Utils.isActivityAlive(EditPracticeFragment.this.getParentActivity())) {
            CharSequence progressMessage = EditPracticeFragment.this.getString(R.string.edit_profile_update_progress);
            if (EditPracticeFragment.this.mPracticeId <= 0) {
                progressMessage = EditPracticeFragment.this.getString(R.string.edit_practice_create_progress);
            }
            EditPracticeFragment.this.mProfileUpdateDialog = new Builder(EditPracticeFragment.this.getParentActivity()).content(progressMessage).progress(true, 0).cancelable(false).autoDismiss(false).show();
        }
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.PUT, BaseURL.updateClinicDetails, patchData, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                EditPracticeFragment.this.mProfileUpdateDialog.dismiss();
                editor = getParentActivity().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE).edit();
                editor.putString("clinic_details" + clinic_id, jsonData.toString());
                editor.putInt("practice_id", clinic_id);
                editor.commit();
                Bundle bundle = new Bundle();
                bundle.putInt("practice_id", clinic_id);
                bundle.putString("clinic_details" + clinic_id, jsonData.toString());
                CharSequence successMessage = EditPracticeFragment.this.getString(R.string.edit_practice_update_success);
                if (clinic_id <= 0) {
                    successMessage = EditPracticeFragment.this.getString(R.string.edit_practice_create_success);
                }
                Builder materialDialog = new Builder(getParentActivity());
                materialDialog.title(successMessage).customView((int) R.layout.dialog_edit_success, false).positiveText((int) R.string.ok).callback(new ButtonCallback() {
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        PreferenceUtils.set(getParentActivity(), PreferenceUtils.DONT_SHOW_EDIT_SUCCESS_DIALOG, Boolean.valueOf(((CheckBox) dialog.findViewById(com.rengwuxian.materialedittext.R.id.checkbox)).isChecked()));
                        dialog.dismiss();
                        getParentActivity().setResult(Activity.RESULT_OK);
                        getParentActivity().finish();
                    }
                });
                materialDialog.cancelable(false);
                materialDialog.show();
                //onFragmentCommunication.updateData(bundle);
                //getParentActivity().finish();
            }
        }, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        }, TAG);
    }

     private void initData(){
         GeoLocation geoLocation=new GeoLocation(getParentActivity());
         this.mProgressRl.setVisibility(View.GONE);
         this.mContentLl.setVisibility(View.VISIBLE);
        clinicList=new ArrayList<>(physicianDetails.getClinic());
        for (Clinic clinic:clinicList) {
            String practicePrimaryMobileNumber=clinic.getContactNumber();
            if (clinic.getClinic_id()==mPracticeId){
                mPracticeFabricId=clinic.getClinic_id();
                    if (!Utils.isEmptyString(clinic.getClinicName())) {
                        this.mPracticeNameEt.setText(clinic.getClinicName());
                        this.mPracticeNameEt.setSelection(clinic.getClinicName().length());
                    }
                    if (!Utils.isEmptyString(practicePrimaryMobileNumber)) {
                        //practicePrimaryMobileNumber = Utils.getPhoneNumberWithoutCountryCode(practicePrimaryMobileNumber);
                        this.mPracticePrimaryNumberEt.setText(practicePrimaryMobileNumber);
                    }
                    /*String address = "";
                    if (!Utils.isEmptyString(clinic.getCity())) {
                        address = address + clinic.getCity();
                    }
                    if (!Utils.isEmptyString(clinic.getLocality())) {
                        address = address + clinic.getLocality();
                    }*/
                    String streetAddress = "";
                    if (!Utils.isEmptyString(clinic.getStreetAddress())){
                        streetAddress=streetAddress+clinic.getStreetAddress();
                    }
                    if (!Utils.isEmptyString(streetAddress)) {
                        mPracticeStreetAddressEt.setText(streetAddress);
                    }
                    if (!Utils.isEmptyString(clinic.getCity())) {
                        mPracticeCityEt.setText(clinic.getCity());
                    }
                    if (!Utils.isEmptyString(clinic.getLocality())){
                        mPracticeLocalityEt.setText(clinic.getLocality());
                    }

                    String strFees;
                    if (clinic.getConsultationFees() == 0) {
                        strFees = "Free";
                    } else {
                        strFees = String.valueOf(clinic.getConsultationFees());
                    }
                mPracticeDoctorFeeEt.setText(strFees);
                try {
                    if (clinic.getClinicVisitingTimings().size()>0){
                        JSONObject timingsObject = getTimingsJSONObject(clinic.getClinicVisitingTimings());
                        String jSONObjectInstrumentation=timingsObject.toString();
                        this.mClinicTimings = jSONObjectInstrumentation;
                        setTimingsEditText(timingsObject, this.mPracticeTimingsEt);
                    }
                } catch (Exception e) {
                   e.printStackTrace();
                }
                //getCitiesData(BaseURL.getCityList);
                //startCitySelection();
            }
        }
    }

    private JSONObject getTimingsJSONObject(ArrayList<VisitingTimings> visitingTimingsSet) throws JSONException {
        if (visitingTimingsSet.size() == 0) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();

        for (VisitingTimings visitingTimings:visitingTimingsSet) {
            VisibilityTimingId visibilityTimingId=visitingTimings.getId();
            JSONObject dayObject = new JSONObject();
            dayObject.put(ClinicTimingsAdapter.SESSION1_START_TIME, visitingTimings.getSession1_start_time());
            dayObject.put(ClinicTimingsAdapter.SESSION1_END_TIME, visitingTimings.getSession1_end_time());
            dayObject.put(ClinicTimingsAdapter.SESSION2_START_TIME, visitingTimings.getSession2_start_time());
            dayObject.put(ClinicTimingsAdapter.SESSION2_END_TIME, visitingTimings.getSession2_end_time());
            String dayString = visibilityTimingId.getTiming_day();
            if (dayString != null) {
                jSONObject.put(dayString.toLowerCase(), dayObject);
            }
        }
        for (int i = 0; i < 7; i += FOCUS_FIELD_PRIMARY_NUMBER) {
            if (!jSONObject.has(ClinicTimingsAdapter.days[i + FOCUS_FIELD_PRIMARY_NUMBER].toLowerCase())) {
                jSONObject.put(ClinicTimingsAdapter.days[i + FOCUS_FIELD_PRIMARY_NUMBER].toLowerCase(), emptyDayObject());
            }
        }
        return jSONObject;
    }

    private JSONObject emptyDayObject() throws JSONException {
        JSONObject dayObject = new JSONObject();
        dayObject.put(ClinicTimingsAdapter.SESSION1_START_TIME, BuildConfig.FLAVOR);
        dayObject.put(ClinicTimingsAdapter.SESSION1_END_TIME, BuildConfig.FLAVOR);
        dayObject.put(ClinicTimingsAdapter.SESSION2_START_TIME, BuildConfig.FLAVOR);
        dayObject.put(ClinicTimingsAdapter.SESSION2_END_TIME, BuildConfig.FLAVOR);
        return dayObject;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Activity activity = getActivity();
            if (Utils.isActivityAlive(activity)) {
                SynapseService.startGeocoding(activity, location);
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void stopLocationUpdate() {
        try{
            this.mLocationProgressBar.setVisibility(View.GONE);
            this.mLocateImageButton.setVisibility(View.VISIBLE);
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                locationManager.removeUpdates(this);
            }
        }catch (SecurityException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_practice_iv_photo:
            case R.id.edit_practice_btn_photo:
                this.mIsEditing = true;
                showAddPhotoDialog();
                return;
            case R.id.edit_practice_ib_locate:
                startLocationRequest();
                return;
            case R.id.edit_practice_et_city:
                startCitySelection();
                //showCityDialog();
                /*this.mIsEditing = true;
                Intent citySelectionIntent = new Intent(getActivity(), BaseSelectionActivity.class);
                citySelectionIntent.putExtra(BaseSelectionActivity.SELECTION_TYPE, FOCUS_FIELD_PRIMARY_NUMBER);
                citySelectionIntent.putExtra(BaseSelectionActivity.SELECTION, this.mSelectedCityMap);
                citySelectionIntent.putExtra(BaseSelectionActivity.SUB_SELECTION, this.mSelectedLocalityMap);
                startActivityForResult(citySelectionIntent, REQUEST_SELECT_LOCALITY);*/
                return;
            case R.id.edit_practice_et_locality:
                if (cityId<=0){
                    mPracticeCityEt.setError(getString(R.string.select_city));
                    //mPracticeLocalityEt.requestFocus();
                    return;
                }
                startLocalitySelection();
                return;
            case R.id.edit_practice_et_timings:
                this.mIsEditing = true;
                Intent localIntent2 = new Intent(getActivity(), BaseTimingsActivity.class);
                localIntent2.putExtra("phyId", physicianDetails.getUser().getUser_id());
                localIntent2.putExtra("relation_fabric_id", this.mRelationFabricId);
                if (!TextUtils.isEmpty(this.mClinicTimings)) {
                    localIntent2.putExtra("time", this.mClinicTimings);
                }
                startActivityForResult(localIntent2, REQUEST_CLINIC_TIMINGS);
                return;
            /*case R.id.button_camera:
                this.mRequestPhotoDialog.dismiss();
                *//*Activity activity = getActivity();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
                    Uri uploadFileUri = createImageFile();
                    if (uploadFileUri != null) {
                        cameraIntent.putExtra("output", uploadFileUri);
                        startActivityForResult(cameraIntent, REQUEST_CAPTURE_IMAGE);
                        return;
                    }
                    //Snackbar.make(getView(), R.string.can_not_capture_photo, -1).show();
                    return;
                }*//*
                changeProfileImage();
                //Snackbar.make(getView(), R.string.no_camera, -1).show();
                return;
            case R.id.button_gallery:
                this.mRequestPhotoDialog.dismiss();
                *//*unsetImagePaths();
                Intent intentSelect = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
                intentSelect.setType(MIME_TYPE_IMAGE);
                startActivityForResult(Intent.createChooser(intentSelect, getString(R.string.select_picture)), REQUEST_SELECT_IMAGE);*//*
                Intent intentSelect = new Intent(Intent.ACTION_GET_CONTENT);
                intentSelect.setType(MIME_TYPE_IMAGE);
                startActivityForResult(Intent.createChooser(intentSelect, "Select File"), REQUEST_SELECT_IMAGE);
                return;*/
            default:
                super.onClick(v);
                return;
        }
    }

    public void startCitySelection(){
        getCitiesData(BaseURL.getCityList);
        cityAdapter=new CityAdapter(getParentActivity(),citiesList);
        //showCityDialog();
    }
    public void getCitiesData(String url){
        String TAB_CITY_CONST_GET="cityConstGet";
        citiesList=new ArrayList<>();
        citiesList.clear();
        JsonArrayRequest getPatientCityList=new JsonArrayRequest(url, new Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        City specialization=gson.fromJson(jsonObject.toString(), City.class);
                        citiesList.add(specialization);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                cityAdapter.notifyDataSetChanged();
                showCityDialog();
            }
        }, errorListener);

        getPatientCityList.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(getPatientCityList, TAG);
    }

    public void startLocalitySelection(){
        getLocalityData(BaseURL.getLocationByCityId, cityId);
        localityAdapter=new LocalityAdapter(getParentActivity(),localityList);
        //showLocalityDialog();
    }

    public void getLocalityData(String url,int cityId){
        String TAB_LOC_CONST_GET="locConstGet";
        localityList=new ArrayList<>();
        localityList.clear();
        JsonArrayRequest getPatientLocality=new JsonArrayRequest(url+cityId, new Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Locality locality=gson.fromJson(jsonObject.toString(), Locality.class);
                        localityList.add(locality);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                localityAdapter.notifyDataSetChanged();
                showLocalityDialog();
            }
        }, errorListener);

        getPatientLocality.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(getPatientLocality, TAG);
    }

    private void showCityDialog(){
        cBoolean=true;
        LayoutInflater inflater=(LayoutInflater) getParentActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.generallistview, null);
        ListView listView= (ListView) view.findViewById(R.id.generalListView);
        listView.setOnItemClickListener(this);
        listView.setAdapter(cityAdapter);
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getParentActivity());
        alertDialog.setView(view);
        alertDialog.setCancelable(true);
        cityDialog=alertDialog.create();
        cityDialog.show();
    }

    private void showLocalityDialog(){
        lBoolean=true;
        LayoutInflater inflater=(LayoutInflater) getParentActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.generallistview, null);
        ListView listView= (ListView) view.findViewById(R.id.generalListView);
        listView.setOnItemClickListener(this);
        listView.setAdapter(localityAdapter);
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getParentActivity());
        alertDialog.setView(view);
        alertDialog.setCancelable(true);
        localityDialog=alertDialog.create();
        localityDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (cBoolean){
            cityDialog.dismiss();
            mPracticeCityEt.setText(citiesList.get(position).getCityName());
            mSelectedCityMap.put(citiesList.get(position).getCityId(), citiesList.get(position).getCityName());
            cityId=citiesList.get(position).getCityId();
            //getLocalityData(BaseURL.getLocationByCityId,cityId);
            //startLocalitySelection();
            cBoolean=false;
        }else if (lBoolean){
            localityDialog.dismiss();
            mPracticeLocalityEt.setText(localityList.get(position).getLocationName());
            mSelectedLocalityMap.put(localityList.get(position).getLocation_id(),localityList.get(position).getLocationName());
            lBoolean=false;
        }
    }

    private void startLocationRequest(){
        Activity activity = getActivity();
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;
        try {
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex2) {
            ex2.printStackTrace();
        }
        try {
            if (networkEnabled) {
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
                this.mLocateImageButton.setVisibility(View.GONE);
                this.mLocationProgressBar.setVisibility(View.VISIBLE);
            } else if (gpsEnabled) {
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);
                this.mLocateImageButton.setVisibility(View.GONE);
                this.mLocationProgressBar.setVisibility(View.VISIBLE);
            } else {
                Builder materialDialog = new Builder(activity);
                materialDialog.title((int) R.string.use_location).content((int) R.string.location_disabled_message).negativeText((int) R.string.cancel).positiveText((int) R.string.enable).callback(new ButtonCallback() {
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialog.dismiss();
                        EditPracticeFragment.this.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
                materialDialog.cancelable(false);
                materialDialog.show();
            }
        }catch (SecurityException e){
            e.printStackTrace();
        }catch (Exception e){
           e.printStackTrace();
        }
    }


    public void setTimingsEditText(JSONObject timingsObject,EditText editText) throws JSONException{
        JSONObject mondayObject=timingsObject.getJSONObject(BaseTimingsActivity.MONDAY);
        JSONObject tuesdayObject=timingsObject.getJSONObject(BaseTimingsActivity.TUESDAY);
        JSONObject wednesdayObject=timingsObject.getJSONObject(BaseTimingsActivity.WEDNESDAY);
        JSONObject thursdayObject=timingsObject.getJSONObject(BaseTimingsActivity.THURSDAY);
        JSONObject fridayObject=timingsObject.getJSONObject(BaseTimingsActivity.FRIDAY);
        JSONObject saturdayObject=timingsObject.getJSONObject(BaseTimingsActivity.SATURDAY);
        JSONObject sundayObject=timingsObject.getJSONObject(BaseTimingsActivity.SUNDAY);
        String mondayString= TimeUtils.csvTimings(mondayObject);
        String tuesdayString=TimeUtils.csvTimings(tuesdayObject);
        String wednesdayString=TimeUtils.csvTimings(wednesdayObject);
        String thursdayString=TimeUtils.csvTimings(thursdayObject);
        String fridayString=TimeUtils.csvTimings(fridayObject);
        String saturdayString=TimeUtils.csvTimings(saturdayObject);
        String sundayString=TimeUtils.csvTimings(sundayObject);
        mondayString = TimeUtils.csvTOAmPmString(mondayString);
        tuesdayString = TimeUtils.csvTOAmPmString(tuesdayString);
        wednesdayString = TimeUtils.csvTOAmPmString(wednesdayString);
        thursdayString = TimeUtils.csvTOAmPmString(thursdayString);
        fridayString = TimeUtils.csvTOAmPmString(fridayString);
        saturdayString = TimeUtils.csvTOAmPmString(saturdayString);
        sundayString = TimeUtils.csvTOAmPmString(sundayString);
        ArrayMap<String, String> dayStringArrayMap = new ArrayMap();
        dayStringArrayMap.put(BaseTimingsActivity.MONDAY, mondayString);
        dayStringArrayMap.put(BaseTimingsActivity.TUESDAY, tuesdayString);
        dayStringArrayMap.put(BaseTimingsActivity.WEDNESDAY, wednesdayString);
        dayStringArrayMap.put(BaseTimingsActivity.THURSDAY, thursdayString);
        dayStringArrayMap.put(BaseTimingsActivity.FRIDAY, fridayString);
        dayStringArrayMap.put(BaseTimingsActivity.SATURDAY, saturdayString);
        dayStringArrayMap.put(BaseTimingsActivity.SUNDAY, sundayString);
        ArrayMap<String, Boolean> isSelectedArrayMap = new ArrayMap();
        int i = FOCUS_FIELD_PRIMARY_NUMBER;
        while (true) {
            int length = ClinicTimingsAdapter.days.length;
            if (i >= length) {
                break;
            }
            isSelectedArrayMap.put(ClinicTimingsAdapter.days[i].toLowerCase(), Boolean.FALSE);
            i += FOCUS_FIELD_PRIMARY_NUMBER;
        }
        ArrayMap<List<String>, String> timingsListArrayMap = new ArrayMap();
        for (i = 0; i < dayStringArrayMap.size(); i += FOCUS_FIELD_PRIMARY_NUMBER) {
            String key = ClinicTimingsAdapter.days[i + FOCUS_FIELD_PRIMARY_NUMBER].toLowerCase();
            String value = (String) dayStringArrayMap.get(key);
            if (!((Boolean) isSelectedArrayMap.get(key)).booleanValue()) {
                List<String> list = new LinkedList();
                list.add(key.substring(0, FOCUS_FIELD_CONSULTATION_FEE).toUpperCase());
                isSelectedArrayMap.put(key, Boolean.TRUE);
                for (int j = i + FOCUS_FIELD_PRIMARY_NUMBER; j < dayStringArrayMap.size(); j += FOCUS_FIELD_PRIMARY_NUMBER) {
                    if (((String) dayStringArrayMap.get(ClinicTimingsAdapter.days[j + FOCUS_FIELD_PRIMARY_NUMBER].toLowerCase())).equals(value)) {
                        list.add(ClinicTimingsAdapter.days[j + FOCUS_FIELD_PRIMARY_NUMBER].substring(0, FOCUS_FIELD_CONSULTATION_FEE).toUpperCase());
                        isSelectedArrayMap.put(ClinicTimingsAdapter.days[j + FOCUS_FIELD_PRIMARY_NUMBER].toLowerCase(), Boolean.TRUE);
                    }
                }
                timingsListArrayMap.put(list, value);
            }
        }
        StringBuilder htmlString = new StringBuilder();
        if (timingsListArrayMap.size() == FOCUS_FIELD_PRIMARY_NUMBER && TextUtils.isEmpty((String) timingsListArrayMap.valueAt(0))) {
            editText.setText(BuildConfig.FLAVOR);
            return;
        }
        for (Entry<List<String>, String> entry : timingsListArrayMap.entrySet()) {
            if (TextUtils.isEmpty((String) entry.getValue())) {
                htmlString.append(BO).append(TextUtils.join(COMMA, (Iterable) entry.getKey())).append(BC).append(BR).append(CLOSED).append(BR);
            } else {
                htmlString.append(BO).append(TextUtils.join(COMMA, (Iterable) entry.getKey())).append(BC).append(BR).append((String) entry.getValue()).append(BR);
            }
        }
        editText.setText(Html.fromHtml(htmlString.toString()));
    }


    private class PhotoUploadTask extends AsyncTask<Uri, Void, String> implements SnackBar.OnMessageClickListener{
        private File mProfileCacheFolder;
        private PhotoUploadTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            if (Utils.isActivityAlive(EditPracticeFragment.this.getActivity())) {
                CharSequence progressMessage = EditPracticeFragment.this.getString(R.string.edit_profile_update_progress);
                if (EditPracticeFragment.this.mPracticeId <= 0) {
                    progressMessage = EditPracticeFragment.this.getString(R.string.edit_practice_create_progress);
                }
                EditPracticeFragment.this.mProfileUpdateDialog = new Builder(EditPracticeFragment.this.getActivity()).content
                        (progressMessage).progress(true, 0).cancelable
                        (false).autoDismiss(false).show();
            }
        }

        protected String doInBackground(Uri... params) {
            String path = FileUtils.getPath(getParentActivity(), params[0]);
            createFolder();
            if (this.mProfileCacheFolder != null && this.mProfileCacheFolder.exists()) {
                File uploadFile = getPath(this.mProfileCacheFolder.getPath(), false);
                if (!(TextUtils.isEmpty(path) || TextUtils.isEmpty(uploadFile.getPath()))) {
                    EditPracticeFragment.this.mPracticeNewPhotoFilePath = Utils.decodeSampledBitmap(path, uploadFile.getPath(), 768, 768, true);
                }
            }
            if (EditPracticeFragment.this.mPracticeFabricId > 0) {
                return uploadPhoto(EditPracticeFragment.this.mPracticeNewPhotoFilePath, EditPracticeFragment.this.mPracticeFabricId);
            }
            return null;
        }

        protected void onPostExecute(String result) {
            if (Utils.isActivityAlive(getParentActivity())) {
                EditPracticeFragment.this.mPracticePhotoNewUrl = result;
                ProfileUpdateTask profileUpdateTask;
                JSONObject[] jSONObjectArr;
                if (EditPracticeFragment.this.mPracticeFabricId <= 0) {
                    profileUpdateTask = new ProfileUpdateTask(Boolean.FALSE.booleanValue());
                    jSONObjectArr = new JSONObject[]{EditPracticeFragment.this.mPracticePostOrPatchData, EditPracticeFragment.this.mRelationPostOrPatchData};
                    profileUpdateTask.execute(jSONObjectArr);
                }else if (Utils.isEmptyString(EditPracticeFragment.this.mPracticePhotoNewUrl)) {
                    EditPracticeFragment.this.mProfileUpdateDialog.dismiss();
                    if (EditPracticeFragment.this.mHasPhoto) {
                        EditPracticeFragment.this.mPracticePhotoIv.setResetImageUrl(EditPracticeFragment.this.mPracticePhotoOldUrl, AppController.getInstance().getPracticeLoader());
                    } else {
                        EditPracticeFragment.this.mPracticePhotoIv.setImageDrawable(ResourcesCompat.getDrawable(EditPracticeFragment.this.getResources(), R.drawable.background_default_add_practice, null));
                    }
                    String errorMessage = EditPracticeFragment.this.getString(R.string.edit_practice_update_failure);
                    if (EditPracticeFragment.this.mPracticeId <= 0) {
                        errorMessage = EditPracticeFragment.this.getString(R.string.edit_practice_create_failure);
                    }
                    new SnackBar.Builder(EditPracticeFragment.this.getParentActivity()).withMessage(errorMessage).withActionMessageId(R.string.retry).withStyle(SnackBar.Style.INFO).withOnClickListener(this).show();
                }else {
                    try {
                        JSONObject addPhotosObject = new JSONObject();
                        addPhotosObject.put(EditPracticeFragment.KEY_PRACTICE_PHOTO_URL, EditPracticeFragment.this.mPracticePhotoNewUrl);
                        addPhotosObject.put(EditPracticeFragment.KEY_PRACTICE_PHOTO_LOGO, Boolean.FALSE);
                        JSONArray addedPhotoJsonArray = new JSONArray();
                        addedPhotoJsonArray.put(addPhotosObject);
                        if (!Utils.isEmptyString(EditPracticeFragment.this.mPracticePhotoNewUrl)) {
                            JSONArray updatedPhotosJsonArray = new JSONArray();
                            JSONArray deletedPhotosJsonArray = new JSONArray();
                            JSONObject newPhotos = new JSONObject();
                            newPhotos.put(EditPracticeFragment.KEY_ADDED, addedPhotoJsonArray);
                            newPhotos.put(EditPracticeFragment.KEY_UPDATED, updatedPhotosJsonArray);
                            newPhotos.put(EditPracticeFragment.KEY_SOFT_DELETED, deletedPhotosJsonArray);
                            EditPracticeFragment.this.mPracticePostOrPatchData.put(EditPracticeFragment.KEY_PRACTICE_PHOTOS, newPhotos);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    profileUpdateTask = new ProfileUpdateTask(Boolean.FALSE.booleanValue());
                    jSONObjectArr = new JSONObject[]{EditPracticeFragment.this.mPracticePostOrPatchData, EditPracticeFragment.this.mRelationPostOrPatchData};
                        profileUpdateTask.execute(jSONObjectArr);
                }
                /*if (!TextUtils.isEmpty(result)) {
                    BitmapDrawable drawable = new BitmapDrawable(getResources(), Utils.decodeSampledBitmap(result, 780, 780));
                    if (mPracticePhotoIv != null) {
                        mPracticePhotoIv.setVisibility(View.VISIBLE);
                        mPracticePhotoIv.setImageDrawable(drawable);
                    }
                }*/
                super.onPostExecute(result);
            }
        }

        private void createFolder() {
            this.mProfileCacheFolder = com.android.volley.misc.Utils.getDiskCacheDir(EditPracticeFragment.this.getParentActivity(), AppController.IMAGE_PRACTICE_CACHE_DIR);
            if (!this.mProfileCacheFolder.exists()) {
                this.mProfileCacheFolder.mkdirs();
            }
        }

        private File getPath(String path, boolean original) {
            File imageFolder = new File(path + File.separator + EditPracticeFragment.BUNDLE_USER_FILE_NAME + File.separator + (original ? "original" : "upload"));
            if (!imageFolder.exists()) {
                imageFolder.mkdirs();
            }
            return new File(imageFolder.getPath() + File.separator + EditPracticeFragment.BUNDLE_USER_FILE_NAME + ".jpg");
        }

        @Override
        public void onMessageClick(Parcelable token) {
            if (Utils.isNetConnected(EditPracticeFragment.this.getParentActivity())) {
                PhotoUploadTask photoUploadTask = new PhotoUploadTask();
                Uri[] uriArr = new Uri[EditPracticeFragment.FOCUS_FIELD_PRIMARY_NUMBER];
                uriArr[0] = EditPracticeFragment.this.mSelectedImageUri;
                photoUploadTask.execute(uriArr);
                return;
            }
            new SnackBar.Builder(EditPracticeFragment.this.getParentActivity()).withMessageId(R.string.no_network_connection).show();
        }
    }

    private class ProfileUpdateTask extends AsyncTask<JSONObject, Void, Boolean> implements SnackBar.OnMessageClickListener {
        private boolean mInitProgressDialog;

        public ProfileUpdateTask(boolean initProgressDialog) {
            this.mInitProgressDialog = initProgressDialog;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            if (Utils.isActivityAlive(EditPracticeFragment.this.getParentActivity()) && this.mInitProgressDialog) {
                CharSequence progressMessage = EditPracticeFragment.this.getString(R.string.edit_profile_update_progress);
                if (EditPracticeFragment.this.mPracticeId <= 0) {
                    progressMessage = EditPracticeFragment.this.getString(R.string.edit_practice_create_progress);
                }
                EditPracticeFragment.this.mProfileUpdateDialog = new Builder(EditPracticeFragment.this.getActivity()).content
                        (progressMessage).progress(true, 0).cancelable
                        (true).autoDismiss(true).show();
            }
        }

        @Override
        protected Boolean doInBackground(JSONObject... params) {
            boolean result;
            try {
                JSONObject patchData = params[0];
                JSONObject relationPatchData = params[EditPracticeFragment.FOCUS_FIELD_PRIMARY_NUMBER];
                if (EditPracticeFragment.this.mPracticeId <= 0) {
                    patchData.put(EditPracticeFragment.KEY_CREATE_OWNER, Boolean.TRUE);
                    result = postPractice(patchData, relationPatchData, EditPracticeFragment.this.mPracticeNewPhotoFilePath);
                } else {
                    result = patchRelation(EditPracticeFragment.this.mRelationFabricId, relationPatchData);
                    if (result) {
                        result = patchPractice(EditPracticeFragment.this.mPracticeFabricId, patchData);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = false;
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {
            final Activity activity = EditPracticeFragment.this.getActivity();
            if (Utils.isActivityAlive(activity)) {
                EditPracticeFragment.this.mProfileUpdateDialog.dismiss();
                String errorMessage = EditPracticeFragment.this.getString(R.string.edit_practice_update_failure);
                String successMessage = EditPracticeFragment.this.getString(R.string.edit_practice_update_success);
                if (EditPracticeFragment.this.mPracticeId <= 0) {
                    errorMessage = EditPracticeFragment.this.getString(R.string.edit_practice_create_failure);
                    successMessage = EditPracticeFragment.this.getString(R.string.edit_practice_create_success);
                }
                if (result) {
                    if (!Utils.isEmptyString(EditPracticeFragment.this.mPracticePhotoNewUrl)) {
                        AppController.getInstance().getPracticeLoader().set(EditPracticeFragment.this.mPracticePhotoNewUrl, EditPracticeFragment.this.mPracticePhotoIv, ((BitmapDrawable) EditPracticeFragment.this.mPracticePhotoIv.getDrawable()).getBitmap());
                    }
                    if (PreferenceUtils.getBooleanPrefs(activity, PreferenceUtils.DONT_SHOW_EDIT_SUCCESS_DIALOG).booleanValue()) {
                        new SnackBar.Builder(activity).withMessage(successMessage).show();
                        activity.setResult(-1);
                        activity.finish();
                        return;
                    }
                    Builder materialDialog = new Builder(activity);
                    materialDialog.title(successMessage).customView((int) R.layout.dialog_edit_success, false).positiveText((int) R.string.ok).callback(new ButtonCallback() {
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
                    return;
                }
                new SnackBar.Builder(activity).withMessage(errorMessage).withActionMessageId(R.string.retry).withStyle(SnackBar.Style.INFO).withOnClickListener(this).show();
            }
        }

        @Override
        public void onMessageClick(Parcelable token) {
            if (Utils.isNetConnected(EditPracticeFragment.this.getParentActivity())) {
                ProfileUpdateTask profileUpdateTask = new ProfileUpdateTask(Boolean.TRUE.booleanValue());
                JSONObject[] jSONObjectArr = new JSONObject[]{EditPracticeFragment.this.mPracticePostOrPatchData, EditPracticeFragment.this.mRelationPostOrPatchData};
                profileUpdateTask.execute(jSONObjectArr);
                return;
            }
            new SnackBar.Builder(EditPracticeFragment.this.getParentActivity()).withMessageId(R.string.no_network_connection).show();
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

    public boolean postPractice(JSONObject postData, JSONObject relationPostData, String photoFilePath) {
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.PATCH,BaseURL.createClinicDetails,postData,null,null);
        RequestTickle volleyTickle = AppController.getInstance().getRequestTickle();
        volleyTickle.add(request);
        NetworkResponse response = volleyTickle.start();
        if (isSuccessfulResponse(response)) {
            Gson gson = new Gson();
            try {
                String parseResponse = VolleyTickle.parseResponse(response);
                Clinic clinic=gson.fromJson(parseResponse,Clinic.class);
                if (clinic!=null){
                    JSONArray jsonArray = getTimingData(mClinicTimings, clinic.getClinic_id(), clinic.getPhyId());
                    //patchPracticeDetails(jsonObject.put("clinicVisitingTimings", jsonArray), clinic.getClinic_id());
                    postRelation(relationPostData,clinic.getClinic_id());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean postRelation(JSONObject postData,int clinic_id) {
        if (Utils.isNetConnected(EditPracticeFragment.this.getParentActivity())){
            try {
                postData.put("",clinic_id);
            }catch (JSONException e){
                e.printStackTrace();
            }
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.PATCH,BaseURL.updateClinicDetails,postData,null,null);
            RequestTickle volleyTickle=AppController.getInstance().getRequestTickle();
            volleyTickle.add(request);
            NetworkResponse response=volleyTickle.start();
            if (isSuccessfulResponse(response)){
                editor = getParentActivity().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE).edit();
                //editor.putString("clinic_details" + clinic_id, jsonData.toString());
                editor.putInt("practice_id", clinic_id);
                editor.commit();
            }
        }
        return false;
    }

    public boolean patchRelation(int relationFabricId, JSONObject patchData) {
        return false;
    }

    public boolean patchPractice(int practiceFabricId, JSONObject patchData) {
        return false;
    }
}
