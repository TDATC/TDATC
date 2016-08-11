package com.skoruz.amwell.physician;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.ClinicTimingsAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerDialogFragment extends DialogFragment implements View.OnClickListener {

    private OnEditFinishedDialog callback;
    private Bundle mBundle;
    private boolean mCompleteSlot = true;
    private TextView mDescriptorTv;
    private LinearLayout mErrorMessageLl;
    private String mFromTime;
    private Button mPositiveButton;
    private int mSession;
    private String[] mTime;
    private TextView mTimeMismatchTv;
    private TimePicker mTimePickerFrom;
    private TimePicker mTimePickerTo;
    private String mToTime;
    private ArrayList<String> newTimingsArrayList;

    public interface OnEditFinishedDialog {
        void onFinishedDialog(ArrayList<String> arrayList, int i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            this.callback=((ClinicTimingsFragment)getTargetFragment());
            this.newTimingsArrayList=new ArrayList<>();
            super.onCreate(savedInstanceState);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_time_picker_dialog, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mBundle=getArguments();
        String str1=getTag();
        this.mTimePickerFrom= (TimePicker) view.findViewById(R.id.time_picker_from);
        this.mTimePickerTo= (TimePicker) view.findViewById(R.id.time_picker_to);
        this.mDescriptorTv= (TextView) view.findViewById(R.id.descriptor);
        this.mTimeMismatchTv= (TextView) view.findViewById(R.id.time_mismatch);
        this.mErrorMessageLl= (LinearLayout) view.findViewById(R.id.time_error_ll);
        this.mPositiveButton= (Button) view.findViewById(R.id.button_submit);
        this.mPositiveButton.setOnClickListener(this);
        view.findViewById(R.id.button_cancel).setOnClickListener(this);
        this.mDescriptorTv.setText(getString(R.string.text_from));
        this.mSession=this.mBundle.getInt(ClinicTimingsAdapter.SESSION);
        getDialog().setTitle(str1.toUpperCase() + " " + getString(R.string.session) + " " + this.mSession);
        this.mTime=this.mBundle.getStringArray(ClinicTimingsAdapter.TIME);
        if (this.mTime == null || this.mTime.length == 0) {
            setTime(this.mTimePickerFrom);
        }else{
            int fromStartHours;
            int fromStartMinutes;
            int oldFromTime = Integer.parseInt(this.mTime[0]);
            if (oldFromTime == 0) {
                fromStartHours = 0;
                fromStartMinutes = 0;
            } else {
                fromStartHours = Integer.parseInt(String.valueOf(oldFromTime).substring(0, 2));
                fromStartMinutes = Integer.parseInt(String.valueOf(oldFromTime).substring(2));
            }
            setTime(this.mTimePickerFrom, fromStartHours, fromStartMinutes);
        }
        setTime(this.mTimePickerTo);
    }

    private void setTime(TimePicker paramTimePicker)
    {
        paramTimePicker.setCurrentHour(Integer.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));
        paramTimePicker.setCurrentMinute(Integer.valueOf(0));
    }

    private void setTime(TimePicker paramTimePicker, int paramInt1, int paramInt2)
    {
        paramTimePicker.setCurrentHour(Integer.valueOf(paramInt1));
        paramTimePicker.setCurrentMinute(Integer.valueOf(paramInt2));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_submit:
                if (this.mErrorMessageLl.getVisibility()==View.VISIBLE){
                    this.mErrorMessageLl.setVisibility(View.INVISIBLE);
                    this.mTimePickerFrom.setVisibility(View.VISIBLE);
                    this.mPositiveButton.setText(R.string.done);
                    this.mDescriptorTv.setText(R.string.text_from);
                    this.mCompleteSlot=true;
                    this.mPositiveButton.setText(R.string.next);
                    if (!TextUtils.isEmpty(this.mFromTime)){
                        setTime(this.mTimePickerFrom,Integer.parseInt((String)this.newTimingsArrayList.get(0)),Integer.parseInt((String)this.newTimingsArrayList.get(1)));
                        return;
                    }
                    return;
                }else if (this.mCompleteSlot){
                    String str3=this.mTimePickerFrom.getCurrentHour().toString();
                    if (str3.length()==1){
                        str3 = (new StringBuilder()).append("0").append(str3).toString();
                    }
                    String str4=this.mTimePickerFrom.getCurrentMinute().toString();
                    if (str4.length()==1){
                        str4=(new StringBuilder()).append("0").append(str4).toString();
                    }
                    this.newTimingsArrayList.add(0,str3);
                    this.newTimingsArrayList.add(1,str4);
                    this.mFromTime=((String)newTimingsArrayList.get(0))+((String)newTimingsArrayList.get(1));
                    this.mCompleteSlot=false;
                    this.mDescriptorTv.setText(R.string.time_to);
                    this.mTimePickerFrom.setVisibility(View.INVISIBLE);
                    this.mTimePickerTo.setVisibility(View.VISIBLE);
                    this.mPositiveButton.setText(R.string.done);
                    if (this.mTime!=null && this.mTime.length>0){
                        int oldToTime = Integer.parseInt(this.mTime[1]);
                        setTime(this.mTimePickerTo, Integer.parseInt(String.valueOf(oldToTime).substring(0, 2)), Integer.parseInt(String.valueOf(oldToTime).substring(2)));
                    }
                    if (!TextUtils.isEmpty(this.mToTime)) {
                        setTime(this.mTimePickerTo, Integer.parseInt((String) this.newTimingsArrayList.get(0)), Integer.parseInt((String) this.newTimingsArrayList.get(1)));
                        return;
                    }
                    return;
                }else {
                    String str1=this.mTimePickerTo.getCurrentHour().toString();
                    if (str1.length()==1){
                        str1=(new StringBuilder()).append("0").append(str1).toString();
                    }
                    String str2=this.mTimePickerTo.getCurrentMinute().toString();
                    if (str2.length()==1){
                        str2=(new StringBuilder()).append("0").append(str2).toString();
                    }
                    this.newTimingsArrayList.add(2,str1);
                    this.newTimingsArrayList.add(3,str2);
                    this.mToTime=((String)newTimingsArrayList.get(2))+((String)newTimingsArrayList.get(3));
                    int compare=Integer.parseInt(this.mToTime)-Integer.parseInt(this.mFromTime);
                    if (compare==0 || compare<0){
                        this.mPositiveButton.setText(R.string.retry);
                        this.mTimePickerTo.setVisibility(View.INVISIBLE);
                        this.mErrorMessageLl.setVisibility(View.VISIBLE);
                        this.mTimeMismatchTv.setText(R.string.timings_conflicting);
                        return;
                    }else if (this.mSession==2) {
                        int[] session1Timings = this.mBundle.getIntArray(ClinicTimingsAdapter.SESSION1_TIMINGS);
                        if (session1Timings != null) {
                            if (((session1Timings[1] < Integer.parseInt(this.mFromTime) ? 1 : 0) ^ (Integer.parseInt(this.mToTime) < session1Timings[0] ? 1 : 0)) != 0) {
                                this.callback.onFinishedDialog(this.newTimingsArrayList, this.mSession);
                                getDialog().dismiss();
                                return;
                            }
                            //this.mDescriptorTv.setText(BuildConfig.FLAVOR);
                            this.mDescriptorTv.setText("");
                            this.mPositiveButton.setText(getString(R.string.retry));
                            this.mTimePickerTo.setVisibility(View.INVISIBLE);
                            this.mErrorMessageLl.setVisibility(View.VISIBLE);
                            this.mTimeMismatchTv.setText(getString(R.string.inter_session_conflict));
                            return;
                        }
                        return;
                    }else {
                        int[] session2Timings = this.mBundle.getIntArray(ClinicTimingsAdapter.SESSION2_TIMINGS);
                        if (session2Timings != null) {
                            if (((Integer.parseInt(this.mFromTime) > session2Timings[1] ? 1 : 0) ^ (session2Timings[0] > Integer.parseInt(this.mToTime) ? 1 : 0)) != 0) {
                                this.callback.onFinishedDialog(this.newTimingsArrayList, this.mSession);
                                getDialog().dismiss();
                                return;
                            }
                            //this.mDescriptorTv.setText(BuildConfig.FLAVOR);
                            this.mDescriptorTv.setText("");
                            this.mPositiveButton.setText(getString(R.string.retry));
                            this.mTimePickerTo.setVisibility(View.INVISIBLE);
                            this.mErrorMessageLl.setVisibility(View.VISIBLE);
                            this.mTimeMismatchTv.setText(getString(R.string.inter_session_conflict));
                            return;
                        }
                        this.callback.onFinishedDialog(this.newTimingsArrayList, this.mSession);
                        getDialog().dismiss();
                        return;
                    }
                }
            case R.id.button_cancel:
                getDialog().dismiss();
                return;
            default:
                return;
        }
    }
}
