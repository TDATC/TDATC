package com.skoruz.amwell.physician;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.ClinicTimingsAdapter;
import com.skoruz.amwell.physicianEntity.TimingsViewModel;
import com.skoruz.amwell.adapter.ClinicTimingsAdapter.OnTimeSlotPicked;
import com.skoruz.amwell.physician.TimePickerDialogFragment.OnEditFinishedDialog;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ClinicTimingsFragment extends Fragment implements
        OnCheckedChangeListener,OnTimeSlotPicked, OnEditFinishedDialog{

    private static final int LOADER_ID = 5020;
    public static final String SESSION = "session";
    public static final String TIME = "time";
    private LinearLayoutManager linearLayoutManager;
    private ClinicTimingsAdapter mAdapter;
    private String mClinicTimings="";
    private Cursor mCursor;
    private int mDoctorFabricId;
    private int mRelationId;

    @Override
    public void onCreate(Bundle paramBundle) {
        Bundle localBundle = getArguments();
        this.mDoctorFabricId = localBundle.getInt("phyId");
        this.mRelationId = localBundle.getInt("relation_fabric_id");
        this.mClinicTimings = localBundle.getString(TIME);
        super.onCreate(paramBundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if (TextUtils.isEmpty(this.mClinicTimings)) {
           /* if (this.mRelationId > 0) {
                initLoader(LOADER_ID, Boolean.FALSE.booleanValue(), this);
            }*/
            this.mAdapter = new ClinicTimingsAdapter(this.mCursor, (OnTimeSlotPicked) this);
        } else {
            this.mAdapter = new ClinicTimingsAdapter(this.mClinicTimings, (OnTimeSlotPicked) this);
        }
        this.linearLayoutManager = new LinearLayoutManager(getActivity());
        View view = inflater.inflate(R.layout.fragment_clinic_timings, container, false);
        ((Switch) view.findViewById(R.id.switch_weekdays)).setOnCheckedChangeListener(this);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.days_recycler);
        mRecyclerView.setLayoutManager(this.linearLayoutManager);
        mRecyclerView.setAdapter(this.mAdapter);
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mAdapter.toggleDays(isChecked, linearLayoutManager);
    }

    @Override
    public void onFinishedDialog(ArrayList<String> paramArrayList, int paramInt) {
        this.mAdapter.updateTimings(paramArrayList, paramInt);
    }

    @Override
    public void showDialog(Bundle paramBundle, String paramString) {
        TimePickerDialogFragment localTimePickerDialogFragment = new TimePickerDialogFragment();
        localTimePickerDialogFragment.setArguments(paramBundle);
        localTimePickerDialogFragment.setTargetFragment(this, 0);
        localTimePickerDialogFragment.show(getActivity().getSupportFragmentManager(), paramString);
    }

    public JSONObject getUpdatedTimings() throws JSONException {
        JSONObject timingsObject = new JSONObject();
        if (this.mAdapter.mMasterDataSet != null) {
            for (int i = 0; i < this.mAdapter.mMasterDataSet.size(); i++) {
                timingsObject.put(ClinicTimingsAdapter.days[i + 1].toLowerCase(), ((TimingsViewModel) this.mAdapter.mMasterDataSet.get(i)).toJSON());
            }
        }
        return timingsObject;
    }
}
