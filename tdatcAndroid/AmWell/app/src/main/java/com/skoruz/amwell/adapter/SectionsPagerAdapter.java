package com.skoruz.amwell.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.skoruz.amwell.patient.DoctorTypeList;
import com.skoruz.amwell.patient.MyAccountFragment;
import com.skoruz.amwell.patient.MyHealthFragment;
import com.skoruz.amwell.patient.MyProviderFragment;

/**
 * Created by Skoruz-Ashish on 9/2/2015.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public SectionsPagerAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs=mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MyHealthFragment();
            case 1:
                return new DoctorTypeList();
            case 2:
                return new MyAccountFragment();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
