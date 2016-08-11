package com.skoruz.amwell.physician;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.skoruz.amwell.R;
import com.skoruz.amwell.utils.OnFragmentInteractionListener;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseTimingsActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    public static final String DAY = "day";
    public static final String FRIDAY = "friday";
    public static final String MONDAY = "monday";
    public static final String SATURDAY = "saturday";
    public static final String SUNDAY = "sunday";
    public static final String THURSDAY = "thursday";
    public static final String TUESDAY = "tuesday";
    public static final String VISITTIMINGS = "clinicVisitingTimings";
    public static final String WEDNESDAY = "wednesday";
    ClinicTimingsFragment mTimingsFragment;

    public void onFragmentInteraction(Bundle paramBundle) {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_fix);
        setContentView(R.layout.activity_base_timings);
        this.mTimingsFragment=new ClinicTimingsFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        this.mTimingsFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.timingContainer,mTimingsFragment);
        fragmentTransaction.commit();
        setToolbar(getString(R.string.edit_timings_title));
    }

    private void setToolbar(String paramString){
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigation_close);
        getSupportActionBar().setTitle(paramString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_fix, R.anim.slide_out_down);
                return true;
            case R.id.save_practiceDetails:
                try {
                    JSONObject timingsObject = this.mTimingsFragment.getUpdatedTimings();
                    Intent intent = getIntent();
                    intent.putExtra(VISITTIMINGS, !(timingsObject instanceof JSONObject) ? timingsObject.toString() : timingsObject.toString());
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_fix, R.anim.slide_out_down);
                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_fix, R.anim.slide_out_down);
    }
}
