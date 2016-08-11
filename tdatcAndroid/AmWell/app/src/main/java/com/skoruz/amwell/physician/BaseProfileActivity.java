package com.skoruz.amwell.physician;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.ui.NetworkImageView;
import com.skoruz.amwell.R;

public class BaseProfileActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private LinearLayout mDoctorExperienceLl,mDoctorFeeLl,mDoctorRecommendsLl,mOtherClinicLl,mPracticeGalleryLl,mClinicAddressLl;
    private TextView mDoctorExperienceTv,mDoctorFeesTv,mDoctorRecommendationTv,mClinicAddressTv;
    private LinearLayoutCompat mPrimaryPracticeLl;
    private TextView mDoctorSpecialityTv,mDoctorNameTv,mDoctorQualificationTv,mPrimaryPracticeLocationTv,mPrimaryPracticeNameTv,mToolBarTitle;
    private ScrollView mScrollView;
    private View mDoctorDetailsLl;
    private ImageView mDoctorPhotoIv;
    private NetworkImageView mHeaderPictureIv;
    private FrameLayout mHeaderFl;
    private RecyclerView mPracticeLv;
    private View mFragmentContainer;
    private ListView mOtherPracticeLv;
    private View mDoctorProfileNoPracticeRl;
    private View mDoctorProfileContentLl;
    private int mCurrentSelectedPracticePosition;
    private OtherPracticeAdapter mOtherPracticeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_profile);
        initControls();
    }

    private void initControls(){
        mScrollView= (ScrollView) findViewById(R.id.scroll_view);
        mDoctorExperienceLl= (LinearLayout) findViewById(R.id.doctor_profile_ll_doctor_experience);
        mDoctorFeeLl= (LinearLayout) findViewById(R.id.doctor_profile_ll_doctor_fee);
        mDoctorRecommendsLl= (LinearLayout) findViewById(R.id.doctor_profile_ll_doctor_recommendation);
        mOtherClinicLl= (LinearLayout) findViewById(R.id.doctor_profile_ll_practice_other);
        mPracticeGalleryLl= (LinearLayout) findViewById(R.id.doctor_profile_ll_practice_gallery);
        mClinicAddressLl= (LinearLayout) findViewById(R.id.doctor_profile_ll_practice_address);

        mDoctorNameTv = ((TextView) findViewById(R.id.doctor_profile_tv_doctor_name));
        mDoctorSpecialityTv = ((TextView) findViewById(R.id.doctor_profile_tv_doctor_speciality));
        mDoctorExperienceTv = ((TextView) findViewById(R.id.doctor_profile_tv_doctor_experience));
        mDoctorQualificationTv = ((TextView)findViewById(R.id.doctor_profile_tv_doctor_qualification));
        mDoctorRecommendationTv = ((TextView)findViewById(R.id.doctor_profile_tv_doctor_recommendation));
        mDoctorFeesTv = ((TextView)findViewById(R.id.doctor_profile_tv_doctor_fee));
        mPrimaryPracticeNameTv = ((TextView) findViewById(R.id.doctor_profile_tv_practice_primary_name));
        mPrimaryPracticeLocationTv = ((TextView) findViewById(R.id.doctor_profile_tv_practice_primary_location));
        mClinicAddressTv = ((TextView) findViewById(R.id.doctor_profile_tv_practice_address));

        this.mHeaderPictureIv = ((NetworkImageView)findViewById(R.id.doctor_profile_iv_header));
        this.mHeaderFl = ((FrameLayout) findViewById(R.id.doctor_profile_fl_header));
        this.mPracticeLv = ((RecyclerView) findViewById(R.id.doctor_profile_rr_practice_photo));
        this.mPracticeLv.setHasFixedSize(true);
        this.mPracticeLv.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.mFragmentContainer = findViewById(R.id.fragment_container);
        this.mOtherPracticeLv = ((ListView)findViewById(R.id.profile_doctor_lv_other_practice));
        this.mOtherPracticeLv.setOnItemClickListener(this);
        this.mOtherPracticeLv.setScrollContainer(false);
        this.mOtherPracticeAdapter = new OtherPracticeAdapter(BaseProfileActivity.this,mCurrentSelectedPracticePosition);
        this.mOtherPracticeLv.setAdapter(mOtherPracticeAdapter);
        this.mOtherPracticeAdapter.notifyDataSetChanged();
        this.mDoctorProfileNoPracticeRl = findViewById(R.id.doctor_profile_rl_no_practice);
        this.mDoctorProfileContentLl = findViewById(R.id.doctor_profile_ll_content);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private class OtherPracticeAdapter extends BaseAdapter{

        private Context mContext;
        private int mCurrentSelectedPracticePosition;

        public OtherPracticeAdapter(Context paramContext,int paramInt)
        {
            this.mContext = paramContext;
            this.mCurrentSelectedPracticePosition = paramInt;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (position == this.mCurrentSelectedPracticePosition) {
                return LayoutInflater.from(this.mContext).inflate(R.layout.list_item_blank, parent, false);
            }
            View localView = LayoutInflater.from(this.mContext).inflate(R.layout.list_item_doctor_practice, parent, false);
            TextView localTextView1 = (TextView)localView.findViewById(R.id.other_clinic_name_tv);
            TextView localTextView2 = (TextView)localView.findViewById(R.id.other_clinic_location_tv);
            return null;
        }
    }
}

