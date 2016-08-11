package com.skoruz.amwell.patient;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.ui.NetworkImageView;
import com.google.gson.Gson;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.skoruz.amwell.R;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.constants.Constants;
import com.skoruz.amwell.misc.FileUtils;
import com.skoruz.amwell.patientEntity.PatientDetails;
import com.skoruz.amwell.patientEntity.UserImage;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditUserProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
        ,DatePickerDialog.OnDateSetListener,View.OnClickListener{

    private static final int REQUEST_CAMERA=0;
    private static final int SELECT_FILE=1;
    private RelativeLayout mTelLayout,mNameLayout,mBloodGroupLayout,mDobLayout;
    private Spinner mBloodGroup;
    private RadioButton mFemaleRadio,mMaleRadio,mOthersRadio;
    private MaterialEditText mName;
    private TextView mDob,mEmail,mNumber;
    private SharedPreferences.Editor editor;
    private String[] mBloodGroups;
    private String mBloodGroupString;
    private Calendar selected_dob_calendar;
    private SharedPreferences sharedPreferences;
    private String mGenderString;
    private ImageView image_edit;
    private NetworkImageView mUserImage;
    private ImageLoader imageLoader;
    private Resources mResources;
    private Button saveDetails;
    private ProgressDialog pDialog;
    private String gender_name;
    private PatientDetails patientDetails;
    private Gson gson=new Gson();
    private Toolbar mToolBar;
    private TextView mCountryCode;
    private View mDivider;
    private String mUserNameString;
    private String mMobileString;
    private String mDobString;
    private Date toDay;
    private Date mDobValue;
    private String dob;
    private static final String TAG=EditUserProfileActivity.class.getSimpleName();
    private android.support.v7.app.AlertDialog mRequestPhotoDialog;
    protected static final int REQUEST_CAPTURE_IMAGE = 5011;
    protected static final int REQUEST_SELECT_IMAGE = 5007;
    public static final String MIME_TYPE_IMAGE = "image/*";
    private Bitmap bitmap;
    public String photoFileName="photo.jpg";
    public final String APP_TAG="TDATC";
    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE=1;
    private String selectedImagePath;
    private UserImage imageDetails;
    private File profileCacheFolder;
    private File uploadFile;
    private Uri profileFileUri;
    private String selected_profile_image_path;
    private String selected_profile_image_name;
    private String serverUrl=BaseURL.getPatientDetailsByPatientId;
    private int patient_id;

        public void onClick(View v) {
            switch (v.getId()){
                case R.id.layout_bloodgrp:
                    EditUserProfileActivity.this.mBloodGroup.performClick();
                    break;
                case R.id.layout_dob:
                    EditUserProfileActivity.this.showDateOfBirthDialog();
                    break;
                case R.id.image_edit:
                    showAddPhotoDialog();
                    break;
                case R.id.save_patientDetails:
                    EditUserProfileActivity.this.updatePatientProfileDetails();
                    break;
                case R.id.button_camera:
                    this.mRequestPhotoDialog.dismiss();
                   /* Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri=Utils.getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, REQUEST_CAMERA);*/
                    EditUserProfileActivity.this.changeProfileImage();
                    return;
                case R.id.button_gallery:
                    this.mRequestPhotoDialog.dismiss();
                    Intent intentSelect = new Intent(Intent.ACTION_GET_CONTENT);
                    intentSelect.setType(MIME_TYPE_IMAGE);
                    startActivityForResult(Intent.createChooser(intentSelect, "Select File"),SELECT_FILE);
                    return;
                default:
                    break;
            }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        initView();
        String patientDetailJson=sharedPreferences.getString("patient_details", null);
        patientDetails=gson.fromJson(patientDetailJson, PatientDetails.class);
        patient_id=patientDetails.getPatient_id();
        //getUpdatedPatientDetails(this.serverUrl, patient_id);
        setUserInfo(patientDetails);
    }

    protected void showAddPhotoDialog() {
        View dialogView = EditUserProfileActivity.this.getLayoutInflater().inflate(R.layout.dialog_add_photo, null);
        dialogView.findViewById(R.id.button_camera).setOnClickListener(this);
        dialogView.findViewById(R.id.button_gallery).setOnClickListener(this);
        this.mRequestPhotoDialog = new android.support.v7.app.AlertDialog.Builder(EditUserProfileActivity.this).setView(dialogView).create();
        this.mRequestPhotoDialog.show();
    }

    /*public void getUpdatedPatientDetails(String url,int patientId){
        JsonObjectRequest patientDetails=new JsonObjectRequest(Request.Method.GET, url+patientId,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response!=null){
                    PatientDetails patientDetails=gson.fromJson(response.toString(),PatientDetails.class);
                    Log.d("Patient Details",patientDetails.toString());
                    setUserInfo(patientDetails);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG,"Error:"+error.getMessage());
                getUpdatedPatientDetails(serverUrl,patient_id);
            }
        });

        patientDetails.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(patientDetails, TAG);
    }*/

    private void setUserInfo(PatientDetails patientDetails){
        if (patientDetails!=null){
            if(TextUtils.isEmpty(patientDetails.getUser().getFirstName()) && TextUtils.isEmpty(patientDetails.getUser().getLastName())){
                this.mName.setVisibility(View.GONE);
            }else{
                String name=patientDetails.getUser().getFirstName()+" "+patientDetails.getUser().getLastName();
                this.mName.setText(name);
                this.mName.setVisibility(View.VISIBLE);
                this.mName.setSelection((patientDetails.getUser().getFirstName() + " " + patientDetails.getUser().getLastName()).length());
                this.mName.setHideUnderline(true);
                this.mName.setTextColor(getResources().getColor(R.color.black));
            }
            this.mEmail.setText(patientDetails.getUser().getEmailAddress());
            if (TextUtils.isEmpty(patientDetails.getUser().getPhonePersonel())){
                this.mNumber.setText(R.string.add_number);
                this.mCountryCode.setVisibility(View.GONE);
                this.mDivider.setVisibility(View.GONE);
            }else {
                PhoneNumber phoneNumber= Utils.parsePhoneNumber(patientDetails.getUser().getPhonePersonel(),"IN");
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
            if (!TextUtils.isEmpty(patientDetails.getUser().getGender())){
                if (patientDetails.getUser().getGender().equalsIgnoreCase("M")){
                    this.mMaleRadio.setChecked(true);
                    this.mGenderString= "M";
                }else if (patientDetails.getUser().getGender().equalsIgnoreCase("F")){
                    this.mFemaleRadio.setChecked(true);
                    this.mGenderString= "F";
                }else if (patientDetails.getUser().getGender().equalsIgnoreCase("TG")){
                    this.mOthersRadio.setChecked(true);
                    this.mGenderString= "TG";
                }
            }
            if (!TextUtils.isEmpty(patientDetails.getUser().getDateOfBirth())){
                try {
                    this.mDob.setText(Constants.dobDateFormat.format(Constants.dbDateFormat.parse(patientDetails.getUser().getDateOfBirth())));
                    this.mDob.setTextColor(getResources().getColor(R.color.black));
                    this.selected_dob_calendar.setTime(Constants.dobDateFormat.parse(Constants.dobDateFormat.format(Constants.dbDateFormat.parse(patientDetails.getUser().getDateOfBirth()))));
                }catch (ParseException e){
                    e.printStackTrace();
                }
            }
            if (TextUtils.isEmpty(patientDetails.getBloodType())) {
                this.mBloodGroup.setSelection(0);
                return;
            }
            for (int i = 0; i < this.mBloodGroups.length; i++) {
                String bloodGroup = this.mBloodGroups[i];
                if (patientDetails.getBloodType().toLowerCase().equals(bloodGroup.toLowerCase())) {
                    this.mBloodGroup.setSelection(i);
                    this.mBloodGroupString = bloodGroup;
                }
            }
            if (!Utils.isEmptyString(patientDetails.getUser().getPhotoPath()) && !patientDetails.getUser().getPhotoPath().equalsIgnoreCase("null")){
                if (imageLoader==null){
                    imageLoader=AppController.getInstance().getImageLoader();
                }
                mUserImage.setImageUrl(patientDetails.getUser().getPhotoPath(),imageLoader);
            }
        }
    }

    private void initView(){
        imageLoader= AppController.getInstance().getImageLoader();
        sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        mName= (MaterialEditText) findViewById(R.id.name);
        mName.setHideUnderline(true);
        mTelLayout= (RelativeLayout) findViewById(R.id.layout_tel);
        mNameLayout= (RelativeLayout) findViewById(R.id.layout_name);
        mBloodGroupLayout= (RelativeLayout) findViewById(R.id.layout_bloodgrp);
        mBloodGroupLayout.setOnClickListener(this);
        mDob= (TextView) findViewById(R.id.et_dob);
        mDobLayout= (RelativeLayout) findViewById(R.id.layout_dob);
        mDobLayout.setOnClickListener(this);
        mBloodGroup= (Spinner) findViewById(R.id.bloodgroup);
        mBloodGroup.setOnItemSelectedListener(this);
        mBloodGroups=getResources().getStringArray(R.array.blood_groups);
        mBloodGroup.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, mBloodGroups));
        this.selected_dob_calendar = Calendar.getInstance();
        this.selected_dob_calendar.set(Calendar.YEAR, 1990);
        this.selected_dob_calendar.set(Calendar.MONTH, 1);
        this.selected_dob_calendar.set(Calendar.DAY_OF_MONTH, 1);
        mEmail= (TextView) findViewById(R.id.email);
        this.mNumber= (TextView) findViewById(R.id.tel_num);
        mMaleRadio= (RadioButton) findViewById(R.id.radio_male);
        mFemaleRadio= (RadioButton) findViewById(R.id.radio_female);
        mOthersRadio= (RadioButton) findViewById(R.id.radio_other);
        image_edit= (ImageView) findViewById(R.id.image_edit);
        image_edit.setOnClickListener(this);
        mUserImage= (NetworkImageView) findViewById(R.id.user_image);
        mCountryCode= (TextView) findViewById(R.id.country_code);
        mDivider=findViewById(R.id.divider);
        mToolBar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        setActionBar();
        //saveDetails= (Button) findViewById(R.id.savePatientDetails);
        //saveDetails.setOnClickListener(this.mClickListener);
    }

    private void setActionBar() {
        setSupportActionBar(this.mToolBar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.mToolBar.setTitle(R.string.edit_profile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_user_profile, menu);
        MenuItemCompat.getActionView(menu.findItem(R.id.save_patientDetails)).setOnClickListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_patientDetails:
                updatePatientProfileDetails();
                return true;
            case android.R.id.home:
                finishActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void finishActivity(){
        finish();
    }

    public void updatePatientProfileDetails(){
        this.mUserNameString=this.mName.getText().toString().trim();
        if (this.mUserNameString.length()<3 || this.mUserNameString.length()>50){
            //this.mName.setError(Html.fromHtml(String.format(getString(R.string.patient_name_invalid), new Object[]{getResources().getString(R.string.patient_name_invalid)})));
            this.mName.setError(Html.fromHtml(getString(R.string.edit_name_error_length)));
            return;
        }
        String countryCode=this.mCountryCode.getText().toString();
        if (!countryCode.startsWith("+")){
            countryCode= "+" + countryCode;
        }
        this.mMobileString = countryCode + this.mNumber.getText().toString();
        this.mDobString = this.mDob.getText().toString();
        if (!TextUtils.isEmpty(this.mDobString)) {
            try {
                this.dob = Constants.dbDateFormat.format(Constants.dobDateFormat.parse(this.mDobString));
                this.mDobValue = Constants.dbDateFormat.parse(this.dob);
                this.toDay = Constants.dbDateFormat.parse(Constants.dbDateFormat.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        patientDetails.getUser().setPhonePersonel(this.mMobileString);
        patientDetails.getUser().setDateOfBirth(this.dob);
        patientDetails.getUser().setGender(this.mGenderString);
        if (!TextUtils.isEmpty(this.mBloodGroupString)){
            if (this.mBloodGroupString.equalsIgnoreCase("Choose blood group")) {
                patientDetails.setBloodType(null);
            } else {
                patientDetails.setBloodType(this.mBloodGroupString);
            }
        }
        if (mUserNameString.contains(" ")){
            String[] names=mName.getText().toString().split(" ");
            patientDetails.getUser().setFirstName(names[0]);
            patientDetails.getUser().setLastName(names[1]);
        }else{
            patientDetails.getUser().setFirstName(mName.getText().toString());
        }

        if (Utils.isNetConnected(this)){
            handelActionEditProfile(patientDetails);
        }else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void handelActionEditProfile(final PatientDetails patientDetails) {
        final String jsonData=gson.toJson(patientDetails);
        JSONObject object=null;
        try {
            object=new JSONObject(jsonData.toString());
        }catch (JSONException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        JsonObjectRequest patientDetailsUpdate=new JsonObjectRequest(Request.Method.POST, BaseURL.updatePatientDetails, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equalsIgnoreCase("success")){
                        Toast.makeText(EditUserProfileActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                        editor.putString("patient_details", jsonData.toString());
                        editor.commit();
                        EditUserProfileActivity.this.saveImageToServer(BaseURL.uploadImage, patientDetails.getPatient_id());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        patientDetailsUpdate.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(patientDetailsUpdate, TAG);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.selected_dob_calendar.set(Calendar.YEAR, year);
        this.selected_dob_calendar.set(Calendar.MONTH, monthOfYear);
        this.selected_dob_calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        this.mDob.setText(Constants.dobDateFormat.format(this.selected_dob_calendar.getTime()));
        this.mDob.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case REQUEST_CAMERA:
                    ProfileImageTask profileImageTask = new ProfileImageTask();
                    Void[] voidArr = new Void[0];
                    profileImageTask.execute(voidArr);
                    break;
                case SELECT_FILE:
                    Uri targetUri = data.getData();
                    ProfileImageGalleryTask profileImageGalleryTask = new ProfileImageGalleryTask();
                    Uri[] uriArr = new Uri[]{targetUri};
                    profileImageGalleryTask.execute(uriArr);
                    break;
                default:
                    break;
            }
        }
    }


    private class ProfileImageGalleryTask extends AsyncTask<Uri, Void, String>{
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(Uri... params) {
            String path = FileUtils.getPath(EditUserProfileActivity.this, params[0]);
            EditUserProfileActivity.this.createFolder();
            if (EditUserProfileActivity.this.profileCacheFolder == null || !EditUserProfileActivity.this.profileCacheFolder.exists()) {
                return path;
            }
            EditUserProfileActivity.this.uploadFile = EditUserProfileActivity.this.getPath(EditUserProfileActivity.this.profileCacheFolder.getPath(), false);
            if (TextUtils.isEmpty(path) || TextUtils.isEmpty(EditUserProfileActivity.this.uploadFile.getPath())) {
                return path;
            }
            return Utils.decodeSampledBitmap(path, EditUserProfileActivity.this.uploadFile.getPath(), 780, 780, true);
        }

        protected void onPostExecute(String result) {
            if (Utils.isActivityAlive(EditUserProfileActivity.this)) {
                EditUserProfileActivity.this.selected_profile_image_path = result;
                EditUserProfileActivity.this.selected_profile_image_name=result.substring(1 + result.lastIndexOf("/"));
                if (!TextUtils.isEmpty(result)) {
                    BitmapDrawable drawable = new BitmapDrawable(EditUserProfileActivity.this.mResources, Utils.decodeSampledBitmap(result, 780, 780));
                    if (EditUserProfileActivity.this.mUserImage != null) {
                        EditUserProfileActivity.this.mUserImage.setVisibility(View.VISIBLE);
                        EditUserProfileActivity.this.mUserImage.setImageDrawable(drawable);
                    }
                }
                super.onPostExecute(result);
            }
        }
    }

    private class ProfileImageTask extends AsyncTask<Void, Void, String>{
        private ProfileImageTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(Void... params) {
            String path = null;
            EditUserProfileActivity.this.createFolder();
            if (EditUserProfileActivity.this.profileCacheFolder == null || !EditUserProfileActivity.this.profileCacheFolder.exists()) {
                return path;
            }
            File originalFile = EditUserProfileActivity.this.getPath(EditUserProfileActivity.this.profileCacheFolder.getPath(), true);
            EditUserProfileActivity.this.uploadFile = EditUserProfileActivity.this.getPath(EditUserProfileActivity.this.profileCacheFolder.getPath(), false);
            return Utils.decodeSampledBitmap(originalFile.getPath(), EditUserProfileActivity.this.uploadFile.getPath(), 780, 780, true);
        }

        protected void onPostExecute(String result) {
            if (Utils.isActivityAlive(EditUserProfileActivity.this)) {
                EditUserProfileActivity.this.selected_profile_image_path = result;
                EditUserProfileActivity.this.selected_profile_image_name=result.substring(1 + result.lastIndexOf("/"));
                if (!TextUtils.isEmpty(result)) {
                    BitmapDrawable drawable = new BitmapDrawable(EditUserProfileActivity.this.mResources, Utils.decodeSampledBitmap(result, 780, 780));
                    if (EditUserProfileActivity.this.mUserImage != null) {
                        EditUserProfileActivity.this.mUserImage.setVisibility(View.VISIBLE);
                        EditUserProfileActivity.this.mUserImage.setImageDrawable(drawable);
                    }
                }
                super.onPostExecute(result);
            }
        }
    }


    public void changeProfileImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            createFolder();
            File profileImagePath = getPath(this.profileCacheFolder.getPath(), true);
            getPath(this.profileCacheFolder.getPath(), false);
            this.profileFileUri = Uri.fromFile(profileImagePath);
            Utils.log("File URI = " + this.profileFileUri.toString());
            if (this.profileFileUri == null) {
                Utils.log("Cannot find a place to put photo");
                return;
            }
            intent.putExtra("output", this.profileFileUri);
            startActivityForResult(intent, REQUEST_CAMERA);
            return;
        }
        Utils.log("Cannot find a place to put photo");
    }

    private File getPath(String path, boolean original) {
        File imageFolder = new File(path + File.separator + "bundle_user_file_name" + File.separator + (original ? "original" : "upload"));
        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        }
        return new File(imageFolder.getPath() + File.separator + "bundle_user_file_name" + ".jpg");
    }

    private void createFolder() {
        this.profileCacheFolder = getDiskCacheDir(this, "profile_files" + File.separator + this.sharedPreferences.getInt("user_id", 0));
        if (!this.profileCacheFolder.exists()) {
            this.profileCacheFolder.mkdirs();
        }
    }


    public static File getDiskCacheDir(Context context, String uniqueName) {
        final String cachePath;
        if (isExternalMounted() && null != getExternalCacheDir(context)) {
            cachePath = getExternalCacheDir(context).getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        Log.i("Cache dir", cachePath + File.separator + uniqueName);
        return new File(cachePath + File.separator + uniqueName);
    }

    private static File getExternalCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    @SuppressLint("NewApi")
    private static boolean isExternalMounted() {
        if (hasGingerbread()) {
            return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable();
        }
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
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
                                Toast.makeText(getApplicationContext(),"Uploaded Successfully",Toast.LENGTH_SHORT).show();
                                editor.putString("patient_image_path",selected_profile_image_path);
                                editor.commit();
                                finishActivity();
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

    private byte[] convertToByteArray(InputStream inputStream) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int next = inputStream.read();
        while (next > -1) {
            bos.write(next);
            next = inputStream.read();
        }

        bos.flush();

        return bos.toByteArray();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mBloodGroupString=((String)parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showDateOfBirthDialog()
    {
        Calendar localCalendar = this.selected_dob_calendar;
        DatePickerDialog localDatePickerDialog = new DatePickerDialog(this, this, localCalendar.get(Calendar.YEAR), localCalendar.get(Calendar.MONTH), localCalendar.get(Calendar.DAY_OF_MONTH));
        localDatePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance(Locale.US).getTimeInMillis());
        localDatePickerDialog.show();
    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.radio_male:
                if (checked) {
                    this.mGenderString = "M";
                    return;
                }
            case R.id.radio_female:
                if (checked) {
                    this.mGenderString = "F";
                    return;
                }
            case R.id.radio_other:
                if (checked) {
                    this.mGenderString = "TG";
                    return;
                }
            default:
                return;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppController.getInstance().cancelPendingRequests(TAG);
    }
}
