package com.skoruz.amwell.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.skoruz.amwell.physician.MyAVFragment;
import com.skoruz.amwell.physician.MyAppointmentFragment;
import com.skoruz.amwell.physician.MyAssessmentFragment;
import com.skoruz.amwell.physician.MyProfileFragment;
import com.skoruz.amwell.physician.MyVideoFragment;

/**
 * Created by Skoruz-Ashish on 9/2/2015.
 */
public class PhySectionsPagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public PhySectionsPagerAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs=mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle=new Bundle();
        switch (position) {
            case 0:
                return MyProfileFragment.newInstance(bundle);
            case 1:
                return MyAppointmentFragment.newInstance(bundle);
            case 2:
                return MyAVFragment.newInstance(bundle);
            case 3:
                return MyAssessmentFragment.newInstance(bundle);
            case 4:
                return MyVideoFragment.newInstance(bundle);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
