package com.skoruz.amwell.patient;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.skoruz.amwell.R;
import com.skoruz.amwell.constants.BaseURL;

public class DoctorList extends AppCompatActivity {

    private Toolbar mToolBar;
    private String data;
    private MyProviderFragment mProviderFragment;
    private static final String TAG_MY_PROVIDER_FRAGMENT = "my_provider_fragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        Intent intent=getIntent();
        Bundle bundle=getIntent().getExtras();
        if (intent!=null){
            data=intent.getStringExtra("specialization");
        }else {
            data="Doctors";
        }
        this.mToolBar= (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarDetails);
        this.mToolBar.setTitle(data);
        this.mToolBar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        this.mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DoctorList.this.finish();
                //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_right);
            }
        });
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        if (savedInstanceState==null){
            this.mProviderFragment=MyProviderFragment.newInstance(bundle);
        }else{
            this.mProviderFragment= (MyProviderFragment) getSupportFragmentManager().getFragment(savedInstanceState,TAG_MY_PROVIDER_FRAGMENT);
        }
        fragmentTransaction.replace(R.id.doc_container, this.mProviderFragment);
        fragmentTransaction.commit();
/*
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        MyProviderFragment myProviderFragment=new MyProviderFragment();
        fragmentTransaction.add(R.id.doc_container, myProviderFragment, "MyProvider");*/
    }
}
