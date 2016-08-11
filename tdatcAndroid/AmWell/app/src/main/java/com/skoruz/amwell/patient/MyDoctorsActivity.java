package com.skoruz.amwell.patient;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.PhysicianListAdapter;
import com.skoruz.amwell.physicianEntity.PhysicianDetails;
import com.skoruz.amwell.utils.Utils;

import java.util.ArrayList;

public class MyDoctorsActivity extends AppCompatActivity {

    private boolean isInEditMode;
    private Bundle mArgs;
    private TextView mEditDoctors;
    public Button mRemoveDoctors;
    private Toolbar mToolBar;
    private MyDoctorsFragment myDoctorsFragment;

    public void changeEditState() {
        this.myDoctorsFragment.toggleEditMode(this.isInEditMode);
        if (this.isInEditMode) {
            this.enableCancelState();
            this.mRemoveDoctors.setVisibility(View.VISIBLE);
            return;
        }
        this.enableEditState();
        this.mRemoveDoctors.setVisibility(View.GONE);
    }

    public void enableCancelState() {
        this.mEditDoctors.setText(R.string.string_cancel);
    }

    public void enableEditState() {
        this.mEditDoctors.setText(R.string.string_edit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doctors);
        this.mToolBar = (Toolbar) this.findViewById(R.id.toolbar);
        this.mEditDoctors = (TextView) this.findViewById(R.id.edit_my_doctors);
        this.mRemoveDoctors = (Button) this.findViewById(R.id.remove_my_doctors);
        this.mArgs = savedInstanceState == null ? this.getIntent().getExtras() : savedInstanceState;
        this.myDoctorsFragment = (MyDoctorsFragment) this.getSupportFragmentManager().findFragmentByTag("MyDoctorsFragment");
        if (this.myDoctorsFragment == null) {
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean("bundle_show_all_doctors", true);
            this.myDoctorsFragment = MyDoctorsFragment.newInstance(bundle2);
            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,this.myDoctorsFragment, "MyDoctorsFragment");
            fragmentTransaction.commitAllowingStateLoss();
        }
        this.mRemoveDoctors.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (Utils.isNetConnected((Context) MyDoctorsActivity.this)) {
                    if (MyDoctorsActivity.this.myDoctorsFragment.isSomeDoctorChecked()) {
                        MyDoctorsActivity.this.myDoctorsFragment.removeDoctors();
                        return;
                    }
                    Toast.makeText(MyDoctorsActivity.this, R.string.select_doctors, Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MyDoctorsActivity.this, R.string.unable_to_remove_doctors, Toast.LENGTH_SHORT).show();
            }
        });
        this.mEditDoctors.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MyDoctorsActivity myDoctorsActivity = MyDoctorsActivity.this;
                boolean bl = !MyDoctorsActivity.this.isInEditMode;
                myDoctorsActivity.isInEditMode = bl;
                MyDoctorsActivity.this.changeEditState();
            }
        });

        this.mToolBar.setTitle(R.string.title_activity_mydoctors);
        this.mToolBar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        this.mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MyDoctorsActivity.this.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
