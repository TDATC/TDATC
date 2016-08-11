package com.skoruz.amwell.physician;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skoruz.amwell.R;

public class MyAssessmentFragment extends Fragment {

    public static MyAssessmentFragment newInstance(Bundle args) {
        MyAssessmentFragment fragment = new MyAssessmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_assessment, container, false);
    }
}
