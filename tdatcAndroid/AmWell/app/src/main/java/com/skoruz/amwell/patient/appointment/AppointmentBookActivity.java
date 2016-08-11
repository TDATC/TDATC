package com.skoruz.amwell.patient.appointment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.NumberedAdapter;
import com.skoruz.amwell.ui.MyTextView;

public class AppointmentBookActivity extends AppCompatActivity implements View.OnClickListener{

    private Bundle args;
    private TextView clinic_name;
    private View dataContainer;
    private View doctor;
    private ImageView doctor_image;
    private TextView doctor_name;
    private Button mBtnNextDay;
    private Button mBtnPreviousDay;
    private View mRlayBackBtn;
    private View mRlayForwardBtn;
    private Toolbar mToolBar;
    private View noDataContainer;
    private View progressContainer;
    private TextView tv_no_data;
    private TextView mTxtDate;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_book);
        this.mToolBar= (Toolbar) findViewById(R.id.toolbar);
        this.mToolBar.setTitle(R.string.title_select_time_slot);
        this.mToolBar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        this.mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AppointmentBookActivity.this.finish();
                //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_right);
            }
        });
        if (savedInstanceState==null){
            this.args=getIntent().getExtras();
        }else {
            this.args=savedInstanceState;
        }
        initControls();
    }

    private void initControls(){
        this.progressContainer = findViewById(R.id.progressContainer);
        this.noDataContainer = findViewById(R.id.noDataContainer);
        this.tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        this.doctor = findViewById(R.id.doctor);
        this.doctor.setOnClickListener(this);
        this.doctor_name = (TextView) findViewById(R.id.doctor_name);
        this.doctor_image = (ImageView) findViewById(R.id.doctor_image);
        this.clinic_name = (TextView) findViewById(R.id.clinic_name);
        //this.mViewPager = (ViewPagerCustom) findViewById(R.id.bookapmnt_pager);
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
        this.dataContainer.setVisibility(View.VISIBLE);
        this.recyclerView= (RecyclerView) findViewById(R.id.slots_recycler_view);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        this.recyclerView.setHorizontalScrollBarEnabled(false);
        this.recyclerView.setVerticalScrollBarEnabled(false);
        this.recyclerView.setAdapter(new NumberedAdapter(30));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_appointment_book, menu);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bookapmnt_btn_slot_forward:
                break;
            case R.id.bookapmnt_btn_slot_back:
                break;
        }
    }
}
