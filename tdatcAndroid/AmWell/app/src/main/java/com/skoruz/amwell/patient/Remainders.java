package com.skoruz.amwell.patient;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.ViewPagerAdapter;

public class Remainders extends AppCompatActivity {

    private Toolbar rem_toolbar;
    private TabLayout rem_tabLayout;
    private ViewPager rem_viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainders);

        rem_toolbar = (Toolbar) findViewById(R.id.remainder_toolbar);
        setSupportActionBar(rem_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rem_viewPager = (ViewPager) findViewById(R.id.remainder_pager);
        setupViewPager(rem_viewPager);

        rem_tabLayout = (TabLayout) findViewById(R.id.remainder_tabs);
        rem_tabLayout.setupWithViewPager(rem_viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MedicineRemainderFragment(), "MEDICINE");
        adapter.addFragment(new LabRemainderFragment(), "LABS");
        adapter.addFragment(new AppointmentRemainderFragment(), "APPOINTMENTS");
        viewPager.setAdapter(adapter);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_remainders, menu);
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
    }*/
}
