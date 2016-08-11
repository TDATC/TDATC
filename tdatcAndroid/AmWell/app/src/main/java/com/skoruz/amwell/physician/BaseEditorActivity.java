package com.skoruz.amwell.physician;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.skoruz.amwell.R;

public class BaseEditorActivity extends AppCompatActivity{

    public static final String ACTION = "action";
    public static final String ACTION_DOCTOR_EDIT = "com.tdatc.synapse.doctor.EDIT";
    public static final String ACTION_PRACTICE_ADD = "com.tdatc.synapse.practice.ADD";
    public static final String ACTION_PRACTICE_EDIT = "com.tdatc.synapse.practice.EDIT";
    public static final String CALLER_CONSULT = "caller_consult";
    public static final String EXTRA_CALLER = "caller";
    public static final String EXTRA_DOCTOR_ID = "phyId";
    public static final String EXTRA_FOCUS_FIELD = "focus_field";
    public static final String EXTRA_PRACTICE_FABRIC_IDS = "practice_id_list";
    public static final String EXTRA_PRACTICE_ID = "clinic_id";
    public static final String EXTRA_RELATION_ID = "relation_id";
    public static final int NONE = -1;
    private static final String TAG_DOCTOR_FRAGMENT = "doctor_fragment";
    private static final String TAG_PRACTICE_FRAGMENT = "clinic_fragment";
    private EditDoctorFragment mEditDoctorFragment;
    private EditPracticeFragment mEditPracticeFragment;

    public static void editPractice(Context paramContext, int paramInt1, int paramInt2)
    {
        Intent localIntent = new Intent(paramContext, BaseEditorActivity.class);
        localIntent.putExtra(EXTRA_PRACTICE_ID, paramInt1);
        localIntent.putExtra(EXTRA_DOCTOR_ID, paramInt2);
        localIntent.setAction(ACTION_PRACTICE_EDIT);
        paramContext.startActivity(localIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_editor);
        Bundle bundle=getIntent().getExtras();
        String str=getIntent().getAction();
        bundle.putString(ACTION,str);
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        if (str.equals(ACTION_DOCTOR_EDIT)){
            if (savedInstanceState == null) {
                this.mEditDoctorFragment = EditDoctorFragment.newInstance(bundle);
                this.mEditPracticeFragment = null;
            } else {
                this.mEditDoctorFragment = (EditDoctorFragment) getSupportFragmentManager().getFragment(savedInstanceState, TAG_DOCTOR_FRAGMENT);
                this.mEditPracticeFragment = null;
            }
            fragmentTransaction.replace(R.id.container, this.mEditDoctorFragment);
        }
        else{
            if (savedInstanceState==null){
                this.mEditPracticeFragment=EditPracticeFragment.newInstance(bundle);
                this.mEditDoctorFragment=null;
            }else {
                this.mEditPracticeFragment= (EditPracticeFragment) getSupportFragmentManager().getFragment(savedInstanceState,TAG_PRACTICE_FRAGMENT);
                this.mEditDoctorFragment=null;
            }
            fragmentTransaction.replace(R.id.container, this.mEditPracticeFragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.mEditDoctorFragment != null && this.mEditDoctorFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, TAG_DOCTOR_FRAGMENT, this.mEditDoctorFragment);
        }
        if (this.mEditPracticeFragment != null && this.mEditPracticeFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, TAG_PRACTICE_FRAGMENT, this.mEditPracticeFragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
