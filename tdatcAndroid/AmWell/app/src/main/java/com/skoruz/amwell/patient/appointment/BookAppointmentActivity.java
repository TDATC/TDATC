package com.skoruz.amwell.patient.appointment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.error.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.skoruz.amwell.R;
import com.skoruz.amwell.misc.SlotDayHolder;
import com.skoruz.amwell.patientEntity.TimeSlots;
import com.skoruz.amwell.patientEntity.TimeSlots.TimeSlot.Slot.TimeSlotsPerSlot;
import com.skoruz.amwell.physicianEntity.Clinic;
import com.skoruz.amwell.physicianEntity.PhysicianDetails;
import com.skoruz.amwell.ui.MyTextView;
import com.skoruz.amwell.ui.ViewPagerCustom;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BookAppointmentActivity extends AppCompatActivity implements View.OnClickListener,Utils.OnFragmentInteractionListener{

    private Bundle args;
    private boolean availableSelected = false;
    private int availableSlot = 0;
    private android.widget.TextView clinic_name;
    private View dataContainer;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", AppController.getInstance().getLocale());
    private SimpleDateFormat dateFormatDateBar = new SimpleDateFormat("EEEE, d MMM", AppController.getInstance().getLocale());
    private SimpleDateFormat dateFormatInput = new SimpleDateFormat("yyyy-MM-dd HH:mm", AppController.getInstance().getLocale());
    private SparseArray<String> date_list;
    private View doctor;
    private ImageView doctor_image;
    private TextView doctor_name;
    private Button mBtnNextDay;
    private Button mBtnPreviousDay;
    private Calendar mCalendar = Calendar.getInstance();
    private ArrayList<SlotDayHolder> mDayHolderList;
    private PhysicianDetails mDoctor;
    private Clinic mClinic;
    private int mClinicPosition;
    private ImageLoader mImageFetcher;
    private View mInternetConatiner;
    private int mLastOpenedDay = -1;
    //private RequestTickle mRequestTickle;
    private View mRetryButton;
    private View mRlayBackBtn;
    private View mRlayForwardBtn;
    private TimeSlots.TimeSlot.Slot.TimeSlotsPerSlot mSlot;
    public TimeSlots mTimeSlots;
    private Toolbar mToolBar;
    private TextView mTxtDate;
    private ViewPagerCustom mViewPager;
    private View noDataContainer;
    private View progressContainer;
    private android.widget.TextView tv_no_data;

    public class TimeslotAdapter extends FragmentPagerAdapter {
        public TimeslotAdapter(FragmentManager fm) {
            super(fm);
        }

        public CharSequence getPageTitle(int position) {
            return (CharSequence) BookAppointmentActivity.this.date_list.get(position);
        }

        public int getCount() {
            return BookAppointmentActivity.this.mDayHolderList.size();
        }

        public Fragment getItem(int position) {
            int slotColumn = 1;
            if (BookAppointmentActivity.this.mTimeSlots.relation.getAppointment_duration() < 60) {
                slotColumn = 60 / BookAppointmentActivity.this.mTimeSlots.relation.getAppointment_duration();
            }
            return TimSlotFragment.newInstance((SlotDayHolder) BookAppointmentActivity.this.mDayHolderList.get(position), slotColumn);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_book_appointment);
        this.mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.mToolBar);
        //initMixpanel();
        /*getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        this.mToolBar.setTitle(R.string.title_select_time_slot);
        this.mToolBar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        this.mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BookAppointmentActivity.this.finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_right);
            }
        });
        //this.mRequestTickle = VolleyTickle.newRequestTickle(getApplicationContext());
        if (bundle == null) {
            this.args = getIntent().getExtras();
        } else {
            this.args = bundle;
        }
        this.mDoctor = this.args.getParcelable("bundle_doctor");
        this.mClinicPosition=this.args.getInt("clinic_position");
        this.mClinic = mDoctor.getClinic().get(mClinicPosition);
        this.mTimeSlots = (TimeSlots) this.args.getParcelable("bundle_timeslot");
        this.mLastOpenedDay = this.args.getInt("last_opened_position", 0);
        initCache();
        initControls();
        initData();
    }

    private void initCache() {
        this.mImageFetcher = AppController.getInstance().getImageLoader();
    }

    private void initData() {
        this.doctor_name.setText(this.mDoctor.getUser().getFirstName()+" "+this.mDoctor.getUser().getLastName());
        /*this.clinic_name.setText(new StringBuilder(this.mDoctor.clinic_name).append(", ").append(this.mDoctor.locality).toString());*/
        this.clinic_name.setText(this.mClinic.getClinicName());
        if (!this.mDoctor.getUser().getPhotoPath().isEmpty()) {
            String doctorImageUrl = this.mDoctor.getUser().getPhotoPath();
            if (doctorImageUrl.isEmpty()) {
                //doctorImageUrl = Utils.getDefaultDoctorImageOtherUrl(this.mDoctor.photos);
            }
            //this.mImageFetcher.get(doctorImageUrl, this.doctor_image, Utils.getDoctorDefaultImageIndex(this.mDoctor));
        }
        if (this.mTimeSlots == null) {
            callTimeSlot();
        } else {
            onResponse(this.mTimeSlots);
        }
    }

    private void initControls() {
        this.progressContainer = findViewById(R.id.progressContainer);
        this.noDataContainer = findViewById(R.id.noDataContainer);
        this.tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        this.doctor = findViewById(R.id.doctor);
        this.doctor.setOnClickListener(this);
        this.doctor_name = (TextView) findViewById(R.id.doctor_name);
        this.doctor_image = (ImageView) findViewById(R.id.doctor_image);
        this.clinic_name = (TextView) findViewById(R.id.clinic_name);
        this.mViewPager = (ViewPagerCustom) findViewById(R.id.bookapmnt_pager);
        this.mTxtDate = (MyTextView) findViewById(R.id.bookapmnt_txt_date);
        this.dataContainer = findViewById(R.id.bookapmnt_llay_content);
        this.mBtnNextDay = (Button) findViewById(R.id.bookapmnt_btn_slot_forward);
        this.mBtnPreviousDay = (Button) findViewById(R.id.bookapmnt_btn_slot_back);
        this.mRlayBackBtn = findViewById(R.id.bookapmnt_rlay_backbtn);
        this.mRlayForwardBtn = findViewById(R.id.bookapmnt_rlay_forwardbtn);
        this.mBtnNextDay.setOnClickListener(this);
        this.mBtnPreviousDay.setOnClickListener(this);
        this.mRlayBackBtn.setOnClickListener(this);
        this.mRlayForwardBtn.setOnClickListener(this);
        this.mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                int itemCount = BookAppointmentActivity.this.mDayHolderList != null ? BookAppointmentActivity.this.mDayHolderList.size() : 0;
                if (itemCount > 0) {
                    BookAppointmentActivity.this.updateDateBar(position, itemCount);
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        this.mViewPager.setOffscreenPageLimit(7);
        this.mInternetConatiner = findViewById(R.id.internet_container);
        this.mRetryButton = findViewById(R.id.btn_retry);
        this.mRetryButton.setOnClickListener(this);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putAll(getIntent().getExtras());
        outState.putParcelable("bundle_timeslot", this.mTimeSlots);
        if (this.mViewPager != null) {
            int postion = this.mViewPager.getCurrentItem();
            if (postion > -1) {
                outState.putInt("last_opened_position", postion);
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.mToolBar.setTitle(R.string.title_select_time_slot);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    private void timeSlotSync() {
        this.progressContainer.setVisibility(View.VISIBLE);
        NetworkResponse response = new NetworkResponse(0, null, null, false);
        if (this.mTimeSlots == null) {
            this.mCalendar.setTime(new Date());
            String fromDate = this.dateFormatInput.format(this.mCalendar.getTime());
            this.mCalendar.add(Calendar.DAY_OF_MONTH,Calendar.DAY_OF_WEEK);
            String toDate = this.dateFormatInput.format(this.mCalendar.getTime());
            ArrayMap<String, String> param = new ArrayMap();
            param.put("mobile", "true");
            param.put("with_relation", "true");
            param.put("group_by_hour", "true");
            //AppController.getInstance().addToRequestQueue(new PractoGsonRequest(0, Utils.API_URL + "/practicedoctors/" + this.mDoctor.id + "/slots", TimeSlots.class, GCMUtils.getAuthToken(this), param, this, this), "BookAppointmentActivity");
        }
    }

    public void onResponse(TimeSlots timeSlots) {
        if (Utils.isActivityAlive(this) && Utils.isNetConnected(this)) {
            this.mTimeSlots = timeSlots;
            if (this.mTimeSlots != null) {
                //this.mDayHolderList = getFormattedTimeSlots();
            }
            if (Utils.isActivityAlive(this)) {
                this.progressContainer.setVisibility(View.GONE);
                if (this.mDayHolderList == null || this.mDayHolderList.size() <= 0) {
                    //this.tv_no_data.setText(getString(2131165848));
                    this.noDataContainer.setVisibility(View.VISIBLE);
                    return;
                }
                this.mViewPager.setAdapter(new TimeslotAdapter(getSupportFragmentManager()));
                int slotOpenDay = 0;
                for (int i = 0; i < this.mDayHolderList.size(); i++) {
                    if (((SlotDayHolder) this.mDayHolderList.get(i)).getOpenSlotPosition() != -1) {
                        slotOpenDay = i;
                        break;
                    }
                }
                this.mViewPager.setCurrentItem(slotOpenDay);
                updateDateBar(slotOpenDay, this.mDayHolderList.size());
                this.dataContainer.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
        this.progressContainer.setVisibility(View.GONE);
        this.noDataContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFragmentInteraction(Bundle bundle) {
        this.mSlot = (TimeSlots.TimeSlot.Slot.TimeSlotsPerSlot) bundle.getParcelable("bundle_slot");
        if (this.mSlot != null) {
            bundle.putString("bundle_slot", this.mSlot.ts);
            Log.v("BookAppointmentActivity", "Id :" + this.mTimeSlots.relation.getClinic_id());
            bundle.putParcelable("bundle_doctor", this.mDoctor);
            /*bundle.putString("bundle_appointment_token", Utils.getAppointmentToken(this.mTimeSlots.relation.getClinic_id()));
            Intent bookAppointmentIntent = new Intent(this, AppointmentActivity.class);
            bookAppointmentIntent.putExtras(bundle);
            startActivityForResult(bookAppointmentIntent, 200);
            overridePendingTransition(2130968613, 2130968616);*/
        }
    }

    public void moveViewPager(boolean isForward) {
        int itemCount = this.mDayHolderList != null ? this.mDayHolderList.size() : 0;
        int currentItem = this.mViewPager.getCurrentItem();
        if (isForward) {
            if (currentItem + 1 < itemCount) {
                this.mViewPager.setCurrentItem(currentItem + 1, true);
            }
        } else if (currentItem - 1 >= 0) {
            this.mViewPager.setCurrentItem(currentItem - 1, true);
        }
    }

    public void updateDateBar(int currentPostion, int itemcount) {
        this.mBtnPreviousDay.setVisibility(View.INVISIBLE);
        this.mBtnNextDay.setVisibility(View.INVISIBLE);
        if (currentPostion + 1 < itemcount) {
            this.mBtnNextDay.setVisibility(View.VISIBLE);
        }
        if (currentPostion - 1 > -1) {
            this.mBtnPreviousDay.setVisibility(View.VISIBLE);
        }
        SlotDayHolder currentDayInfo = (SlotDayHolder) this.mDayHolderList.get(currentPostion);
        if (currentDayInfo.isToday()) {
            this.mTxtDate.setText(String.format(getString(R.string.date_string_today), new Object[]{currentDayInfo.getDate()}));
        } else if (currentDayInfo.isTomorrow()) {
            this.mTxtDate.setText(String.format(getString(R.string.date_string_tom), new Object[]{currentDayInfo.getDate()}));
        } else {
            this.mTxtDate.setText(currentDayInfo.getDayAndDate());
        }
    }

    public TimeSlotsPerSlot generateDisabledSlot(Calendar calendar, int index, int minsToAdd) {
        TimeSlotsPerSlot disabledSlot = new TimeSlotsPerSlot();
        Calendar cal = Calendar.getInstance();
        cal.setTime(calendar.getTime());
        cal.add(Calendar.MINUTE, minsToAdd);
        disabledSlot.ts = this.dateFormat.format(cal.getTime());
        disabledSlot.available = false;
        disabledSlot.index = index;
        return disabledSlot;
    }

    private void callTimeSlot() {
        if (Utils.isNetConnected(this)) {
            timeSlotSync();
            this.mInternetConatiner.setVisibility(View.GONE);
            return;
        }
        this.mInternetConatiner.setVisibility(View.VISIBLE);
    }
}
