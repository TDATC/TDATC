package com.skoruz.amwell.adapter;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skoruz.amwell.BuildConfig;
import com.skoruz.amwell.R;
import com.skoruz.amwell.physicianEntity.TimingsViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by SKoruz-Keerthi on 28-10-2015.
 */
public class ClinicTimingsAdapter extends Adapter<ClinicTimingsAdapter.ViewHolder> {

    private static final int ALL_WEEKDAYS_SAME = 1;
    public static final String DAY = "day";
    public static final String SESSION = "session";
    public static final String SESSION1_END_TIME = "session1_end_time";
    public static final String SESSION1_START_TIME = "session1_start_time";
    public static final String SESSION1_TIMINGS = "session1_timings";
    public static final String SESSION2_END_TIME = "session2_end_time";
    public static final String SESSION2_START_TIME = "session2_start_time";
    public static final String SESSION2_TIMINGS = "session2_timings";
    public static final String TIME = "time";
    private static final int WEEKDAYS_DIFFERENT=0;
    public static final String[] days = { "Mon-Fri", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
    private ViewHelper helper;
    private Cursor mCursor;
    private ArrayList<TimingsViewModel> mDataSet;
    private DataSetObserver mDataSetObserver;
    public ArrayList<TimingsViewModel> mMasterDataSet;
    private OnTimeSlotPicked mOnTimeSlotPicked;
    private int mWeekdaysToggleSwitchStatus = 0;

    public interface OnTimeSlotPicked {
        void showDialog(Bundle bundle, String str);
    }

    private class ViewHelper {
        public ViewHolder helperHolder;
        public int session;
        public int viewPosition;

        private ViewHelper() {
        }
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mAddSlot1Tv;
        public TextView mAddSlot2Tv;
        public ImageView mCloseSlot1;
        public ImageView mCloseSlot2;
        public TextView mDay1From;
        public TextView mDay1To;
        public TextView mDay2From;
        public TextView mDay2To;
        public LinearLayout mDayLl;
        public TextView mDayTv;
        public LinearLayout mSlot1Ll;
        public LinearLayout mSlot2Ll;

        public ViewHolder(View paramView) {
            super(paramView);
            this.mDayTv = ((TextView) paramView.findViewById(R.id.day_description));
            this.mAddSlot1Tv = ((TextView) paramView.findViewById(R.id.add_slot_1));
            this.mAddSlot2Tv = ((TextView) paramView.findViewById(R.id.add_slot_2));
            this.mSlot1Ll = ((LinearLayout) paramView.findViewById(R.id.day_ll_1));
            this.mSlot2Ll = ((LinearLayout) paramView.findViewById(R.id.day_ll_2));
            this.mDay1From = ((TextView) paramView.findViewById(R.id.day_1_from));
            this.mDay2From = ((TextView) paramView.findViewById(R.id.day_2_from));
            this.mDay1To = ((TextView) paramView.findViewById(R.id.day_1_to));
            this.mDay2To = ((TextView) paramView.findViewById(R.id.day_2_to));
            this.mCloseSlot1 = ((ImageView) paramView.findViewById(R.id.close_slot_1));
            this.mCloseSlot2 = ((ImageView) paramView.findViewById(R.id.close_slot_2));
            this.mDayLl = ((LinearLayout) paramView.findViewById(R.id.ll_day));
            this.mAddSlot1Tv.setOnClickListener(this);
            this.mAddSlot2Tv.setOnClickListener(this);
            this.mCloseSlot1.setOnClickListener(this);
            this.mCloseSlot2.setOnClickListener(this);
            this.mSlot1Ll.setOnClickListener(this);
            this.mSlot2Ll.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ViewHolder localViewHolder = (ViewHolder)v.getTag();
            int position = localViewHolder.getAdapterPosition();
            Bundle bundle;
            OnTimeSlotPicked onTimeSlotPicked;
            String[] strArr;
            String fromTime;
            String toTime;
            switch (v.getId()){
                case R.id.ll_day:
                    if ((localViewHolder.mAddSlot1Tv.getVisibility()!=View.GONE) || (localViewHolder.mAddSlot2Tv.getVisibility()!=View.GONE)){
                        localViewHolder.mAddSlot1Tv.setVisibility(View.VISIBLE);
                        return;
                    }
                    return;
                case R.id.add_slot_1:
                    ClinicTimingsAdapter.this.helper =new ViewHelper();
                    bundle=new Bundle();
                    bundle.putInt(ClinicTimingsAdapter.SESSION, ClinicTimingsAdapter.ALL_WEEKDAYS_SAME);
                    bundle.putStringArray(ClinicTimingsAdapter.TIME, new String[0]);
                    ClinicTimingsAdapter.this.helper.helperHolder=localViewHolder;
                    ClinicTimingsAdapter.this.helper.viewPosition=position;
                    ClinicTimingsAdapter.this.helper.session=ClinicTimingsAdapter.ALL_WEEKDAYS_SAME;
                    if (ClinicTimingsAdapter.this.mWeekdaysToggleSwitchStatus==ClinicTimingsAdapter.ALL_WEEKDAYS_SAME){
                        onTimeSlotPicked=ClinicTimingsAdapter.this.mOnTimeSlotPicked;
                        strArr=ClinicTimingsAdapter.days;
                        if (position!=0){
                            position+=5 * ClinicTimingsAdapter.this.mWeekdaysToggleSwitchStatus;
                        }
                        onTimeSlotPicked.showDialog(bundle, strArr[position]);
                        return;
                    }
                    ClinicTimingsAdapter.this.mOnTimeSlotPicked.showDialog(bundle, ClinicTimingsAdapter.days[(position + 1)]);
                    return;
                case R.id.day_ll_1:
                    ClinicTimingsAdapter.this.helper=new ViewHelper();
                    ClinicTimingsAdapter.this.helper.helperHolder=localViewHolder;
                    ClinicTimingsAdapter.this.helper.viewPosition=position;
                    ClinicTimingsAdapter.this.helper.session=ClinicTimingsAdapter.ALL_WEEKDAYS_SAME;
                    bundle=new Bundle();
                    bundle.putInt(ClinicTimingsAdapter.SESSION,ClinicTimingsAdapter.ALL_WEEKDAYS_SAME);
                    fromTime=TextUtils.join(BuildConfig.FLAVOR,ClinicTimingsAdapter.this.helper.helperHolder.mDay1From.getText().toString().split(":"));
                    toTime= TextUtils.join(BuildConfig.FLAVOR,ClinicTimingsAdapter.this.helper.helperHolder.mDay1To.getText().toString().split(":"));
                    bundle.putStringArray(ClinicTimingsAdapter.TIME,new String[]{fromTime,toTime});
                    if (!TextUtils.isEmpty(ClinicTimingsAdapter.this.helper.helperHolder.mDay2From.getText().toString())) {
                        bundle.putIntArray(ClinicTimingsAdapter.SESSION2_TIMINGS, new int[]{Integer.parseInt(TextUtils.join(BuildConfig.FLAVOR, ClinicTimingsAdapter.this.helper.helperHolder.mDay2From.getText().toString().split(":"))), Integer.parseInt(TextUtils.join(BuildConfig.FLAVOR, ClinicTimingsAdapter.this.helper.helperHolder.mDay2To.getText().toString().split(":")))});
                    }
                    ClinicTimingsAdapter.this.mOnTimeSlotPicked.showDialog(bundle, ClinicTimingsAdapter.days[(ClinicTimingsAdapter.this.mWeekdaysToggleSwitchStatus * 5) + position]);
                    return;
                case R.id.close_slot_1:
                    if (ClinicTimingsAdapter.this.mWeekdaysToggleSwitchStatus==ClinicTimingsAdapter.this.ALL_WEEKDAYS_SAME && position==0){
                        ClinicTimingsAdapter.this.resetWeekdays(ClinicTimingsAdapter.ALL_WEEKDAYS_SAME);
                    }
                    if (TextUtils.isEmpty(((TimingsViewModel)ClinicTimingsAdapter.this.mDataSet.get(position)).getSlot2From())){
                        ((TimingsViewModel)ClinicTimingsAdapter.this.mDataSet.get(position)).setSlot1From(BuildConfig.FLAVOR);
                        ((TimingsViewModel)ClinicTimingsAdapter.this.mDataSet.get(position)).setSlot1To(BuildConfig.FLAVOR);
                        ((TimingsViewModel)ClinicTimingsAdapter.this.mDataSet.get((ClinicTimingsAdapter.this.mWeekdaysToggleSwitchStatus*4)+position)).setSlot1From(BuildConfig.FLAVOR);
                        ((TimingsViewModel)ClinicTimingsAdapter.this.mDataSet.get((ClinicTimingsAdapter.this.mWeekdaysToggleSwitchStatus*4)+position)).setSlot1To(BuildConfig.FLAVOR);
                        localViewHolder.mDay1From.setText(BuildConfig.FLAVOR);
                        localViewHolder.mDay1To.setText(BuildConfig.FLAVOR);
                        localViewHolder.mSlot1Ll.setVisibility(View.GONE);
                        localViewHolder.mAddSlot2Tv.setVisibility(View.GONE);
                        localViewHolder.mAddSlot1Tv.setVisibility(View.VISIBLE);
                        return;
                    }
                    String from2 = ((TimingsViewModel) ClinicTimingsAdapter.this.mDataSet.get(position)).getSlot2From();
                    String to2 = ((TimingsViewModel) ClinicTimingsAdapter.this.mDataSet.get(position)).getSlot2To();
                    ((TimingsViewModel) ClinicTimingsAdapter.this.mDataSet.get(position)).setSlot1From(from2);
                    ((TimingsViewModel) ClinicTimingsAdapter.this.mDataSet.get(position)).setSlot1To(to2);
                    ((TimingsViewModel) ClinicTimingsAdapter.this.mDataSet.get(position)).setSlot2From(BuildConfig.FLAVOR);
                    ((TimingsViewModel) ClinicTimingsAdapter.this.mDataSet.get(position)).setSlot2To(BuildConfig.FLAVOR);
                    ((TimingsViewModel) ClinicTimingsAdapter.this.mMasterDataSet.get((ClinicTimingsAdapter.this.mWeekdaysToggleSwitchStatus * 4) + position)).setSlot1From(from2);
                    ((TimingsViewModel) ClinicTimingsAdapter.this.mMasterDataSet.get((ClinicTimingsAdapter.this.mWeekdaysToggleSwitchStatus * 4) + position)).setSlot1To(to2);
                    ((TimingsViewModel) ClinicTimingsAdapter.this.mMasterDataSet.get((ClinicTimingsAdapter.this.mWeekdaysToggleSwitchStatus * 4) + position)).setSlot2From(BuildConfig.FLAVOR);
                    ((TimingsViewModel) ClinicTimingsAdapter.this.mMasterDataSet.get((ClinicTimingsAdapter.this.mWeekdaysToggleSwitchStatus * 4) + position)).setSlot2To(BuildConfig.FLAVOR);
                    localViewHolder.mDay1From.setText(from2);
                    localViewHolder.mDay1To.setText(to2);
                    localViewHolder.mSlot2Ll.setVisibility(View.GONE);
                    this.mAddSlot2Tv.setVisibility(View.VISIBLE);
                    return;
                case R.id.add_slot_2 /*2131689809*/:
                    ClinicTimingsAdapter.this.helper = new ViewHelper();
                    bundle = new Bundle();
                    bundle.putInt(ClinicTimingsAdapter.SESSION, 2);
                    bundle.putStringArray(ClinicTimingsAdapter.TIME, new String[0]);
                    ClinicTimingsAdapter.this.helper.helperHolder = localViewHolder;
                    String session1From = ClinicTimingsAdapter.this.helper.helperHolder.mDay1From.getText().toString();
                    String session1To = ClinicTimingsAdapter.this.helper.helperHolder.mDay1To.getText().toString();
                    int[] session1Timings = new int[]{Integer.parseInt(TextUtils.join(BuildConfig.FLAVOR, session1From.split(":"))), Integer.parseInt(TextUtils.join(BuildConfig.FLAVOR, session1To.split(":")))};
                    ClinicTimingsAdapter.this.helper.viewPosition = position;
                    ClinicTimingsAdapter.this.helper.session = 2;
                    bundle.putIntArray(ClinicTimingsAdapter.SESSION1_TIMINGS, session1Timings);
                    if (ClinicTimingsAdapter.this.mWeekdaysToggleSwitchStatus == ClinicTimingsAdapter.ALL_WEEKDAYS_SAME) {
                        onTimeSlotPicked = ClinicTimingsAdapter.this.mOnTimeSlotPicked;
                        strArr = ClinicTimingsAdapter.days;
                        if (position != 0) {
                            position += ClinicTimingsAdapter.this.mWeekdaysToggleSwitchStatus * 5;
                        }
                        onTimeSlotPicked.showDialog(bundle, strArr[position]);
                        return;
                    }
                    ClinicTimingsAdapter.this.mOnTimeSlotPicked.showDialog(bundle, ClinicTimingsAdapter.days[position + ClinicTimingsAdapter.ALL_WEEKDAYS_SAME]);
                    return;
                case R.id.day_ll_2 /*2131689810*/:
                    ClinicTimingsAdapter.this.helper = new ViewHelper();
                    ClinicTimingsAdapter.this.helper.helperHolder = localViewHolder;
                    ClinicTimingsAdapter.this.helper.viewPosition = position;
                    ClinicTimingsAdapter.this.helper.session = 2;
                    bundle = new Bundle();
                    bundle.putInt(ClinicTimingsAdapter.SESSION, 2);
                    fromTime = TextUtils.join(BuildConfig.FLAVOR, ClinicTimingsAdapter.this.helper.helperHolder.mDay2From.getText().toString().split(":"));
                    toTime = TextUtils.join(BuildConfig.FLAVOR, ClinicTimingsAdapter.this.helper.helperHolder.mDay2To.getText().toString().split(":"));
                    bundle.putStringArray(ClinicTimingsAdapter.TIME, new String[]{fromTime, toTime});
                    bundle.putIntArray(ClinicTimingsAdapter.SESSION1_TIMINGS, new int[]{Integer.parseInt(TextUtils.join(BuildConfig.FLAVOR, ClinicTimingsAdapter.this.helper.helperHolder.mDay1From.getText().toString().split(":"))), Integer.parseInt(TextUtils.join(BuildConfig.FLAVOR, ClinicTimingsAdapter.this.helper.helperHolder.mDay1To.getText().toString().split(":")))});
                    ClinicTimingsAdapter.this.mOnTimeSlotPicked.showDialog(bundle, ClinicTimingsAdapter.days[(ClinicTimingsAdapter.this.mWeekdaysToggleSwitchStatus * 5) + position]);
                    return;
                case R.id.close_slot_2 /*2131689813*/:
                    if (ClinicTimingsAdapter.this.mWeekdaysToggleSwitchStatus == ClinicTimingsAdapter.ALL_WEEKDAYS_SAME) {
                        if (position == 0) {
                            ClinicTimingsAdapter.this.resetWeekdays(2);
                        }
                        ((TimingsViewModel) ClinicTimingsAdapter.this.mDataSet.get(position)).setSlot2From(BuildConfig.FLAVOR);
                        ((TimingsViewModel) ClinicTimingsAdapter.this.mDataSet.get(position)).setSlot2To(BuildConfig.FLAVOR);
                    } else {
                        ((TimingsViewModel) ClinicTimingsAdapter.this.mMasterDataSet.get(position)).setSlot2From(BuildConfig.FLAVOR);
                        ((TimingsViewModel) ClinicTimingsAdapter.this.mMasterDataSet.get(position)).setSlot2To(BuildConfig.FLAVOR);
                    }
                    localViewHolder.mDay2From.setText(BuildConfig.FLAVOR);
                    localViewHolder.mDay2To.setText(BuildConfig.FLAVOR);
                    localViewHolder.mSlot2Ll.setVisibility(View.GONE);
                    localViewHolder.mAddSlot2Tv.setVisibility(View.VISIBLE);
                    return;
                default:
                    return;
            }
        }
    }

    public ClinicTimingsAdapter(Cursor cursor, OnTimeSlotPicked onTimeSlotPicked) {
        this.mWeekdaysToggleSwitchStatus = 0;
        this.mOnTimeSlotPicked = onTimeSlotPicked;
        this.mCursor = cursor;
        this.mDataSetObserver = new DataSetObserver() {
            public void onChanged() {
                super.onChanged();
            }

            public void onInvalidated() {
                super.onInvalidated();
            }
        };
        if (this.mCursor != null) {
            this.mCursor.registerDataSetObserver(this.mDataSetObserver);
            this.mDataSet = createTimings(this.mCursor);
        } else {
            this.mDataSet = createTimings();
        }
        this.mMasterDataSet = new ArrayList(this.mDataSet);
    }

    public ClinicTimingsAdapter(String timings, OnTimeSlotPicked onTimeSlotPicked) {
        this.mWeekdaysToggleSwitchStatus = 0;
        this.mDataSet = new ArrayList();
        this.mOnTimeSlotPicked = onTimeSlotPicked;
        try {
            JSONObject timingsObject = new JSONObject(timings);
            for (int i = 0; i < timingsObject.length(); i += ALL_WEEKDAYS_SAME) {
                JSONObject day = timingsObject.getJSONObject(days[i + ALL_WEEKDAYS_SAME].toLowerCase());
                this.mDataSet.add(new TimingsViewModel(day.getString(SESSION1_START_TIME), day.getString(SESSION1_END_TIME), day.getString(SESSION2_START_TIME), day.getString(SESSION2_END_TIME), days[i + ALL_WEEKDAYS_SAME]));
            }
            this.mMasterDataSet = new ArrayList(this.mDataSet);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<TimingsViewModel> createTimings() {
        ArrayList<TimingsViewModel> dataSet = new ArrayList();
        for (int i = ALL_WEEKDAYS_SAME; i < days.length; i += ALL_WEEKDAYS_SAME) {
            dataSet.add(i - 1, new TimingsViewModel(days[i].substring(0, 3)));
        }
        return dataSet;
    }

    private ArrayList<TimingsViewModel> createTimings(Cursor paramCursor)
    {
        HashMap<String,TimingsViewModel> localHashMap = new HashMap();
        ArrayList<TimingsViewModel> localArrayList = new ArrayList();
        paramCursor.moveToFirst();
        int i = paramCursor.getColumnIndex(ClinicTimingsAdapter.DAY);
        int j = paramCursor.getColumnIndex(ClinicTimingsAdapter.SESSION1_START_TIME);
        int k = paramCursor.getColumnIndex(ClinicTimingsAdapter.SESSION2_START_TIME);
        int m = paramCursor.getColumnIndex(ClinicTimingsAdapter.SESSION1_END_TIME);
        int n = paramCursor.getColumnIndex(ClinicTimingsAdapter.SESSION2_END_TIME);
        do{
            localHashMap.put(paramCursor.getString(i), new TimingsViewModel(paramCursor.getString(j), paramCursor.getString(m), paramCursor.getString(k), paramCursor.getString(n), paramCursor.getString(i)));
        } while (paramCursor.moveToNext());
        for (int i1 = 0; i1 < localHashMap.size(); i1 += ALL_WEEKDAYS_SAME) {
            localArrayList.add(localHashMap.get(days[(i1 + ALL_WEEKDAYS_SAME)]));
        }
        return localArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_day_ll, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        clearViews(holder);
        TimingsViewModel data = (TimingsViewModel) this.mDataSet.get(position);
        if (TextUtils.isEmpty(data.getSlot1From())) {
            holder.mAddSlot1Tv.setVisibility(View.VISIBLE);
        } else {
            holder.mAddSlot1Tv.setVisibility(View.GONE);
            holder.mSlot1Ll.setVisibility(View.VISIBLE);
            holder.mDay1From.setText(data.getSlot1From());
            holder.mDay1To.setText(data.getSlot1To());
            holder.mAddSlot2Tv.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(data.getSlot2From())) {
            holder.mAddSlot2Tv.setVisibility(View.GONE);
            holder.mSlot2Ll.setVisibility(View.VISIBLE);
            holder.mDay2From.setText(data.getSlot2From());
            holder.mDay2To.setText(data.getSlot2To());
        }
        if (data.getDay().contains("-")) {
            holder.mDayTv.setText(data.getDay().toUpperCase());
        } else {
            holder.mDayTv.setText(data.getDay().substring(0, 3).toUpperCase());
        }
        holder.mAddSlot1Tv.setTag(holder);
        holder.mAddSlot2Tv.setTag(holder);
        holder.mSlot1Ll.setTag(holder);
        holder.mSlot2Ll.setTag(holder);
        holder.mDay1From.setTag(holder);
        holder.mDay2From.setTag(holder);
        holder.mDay1To.setTag(holder);
        holder.mDay2To.setTag(holder);
        holder.mDayTv.setTag(holder);
        holder.mCloseSlot1.setTag(holder);
        holder.mCloseSlot2.setTag(holder);
        holder.mDayLl.setTag(holder);
    }

    private void clearViews(ViewHolder paramViewHolder)
    {
        paramViewHolder.mAddSlot1Tv.setVisibility(View.GONE);
        paramViewHolder.mAddSlot2Tv.setVisibility(View.GONE);
        paramViewHolder.mSlot1Ll.setVisibility(View.GONE);
        paramViewHolder.mSlot2Ll.setVisibility(View.GONE);
        paramViewHolder.mDay1From.setText(BuildConfig.FLAVOR);
        paramViewHolder.mDay2From.setText(BuildConfig.FLAVOR);
        paramViewHolder.mDay1To.setText(BuildConfig.FLAVOR);
        paramViewHolder.mDay2To.setText(BuildConfig.FLAVOR);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == this.mCursor) {
            return null;
        }
        Cursor oldCursor = this.mCursor;
        if (!(oldCursor == null || this.mDataSetObserver == null)) {
            oldCursor.unregisterDataSetObserver(this.mDataSetObserver);
        }
        this.mCursor = newCursor;
        if (this.mCursor != null) {
            if (this.mDataSetObserver != null) {
                this.mCursor.registerDataSetObserver(this.mDataSetObserver);
            }
            this.mDataSet = createTimings(this.mCursor);
            this.mMasterDataSet = new ArrayList(this.mDataSet);
        }
        notifyDataSetChanged();
        return oldCursor;
    }

    private boolean isSameForWeekdays(ArrayList<TimingsViewModel> masterData, ArrayList<TimingsViewModel> data)
    {
        boolean isIt = false;
        if (data.size() >= 7) {
            return false;
        }
        for (int i = 0; i < masterData.size() - 3; i += ALL_WEEKDAYS_SAME) {
            if (!((TimingsViewModel) masterData.get(i)).getSlot1From().equalsIgnoreCase(((TimingsViewModel) masterData.get(i + ALL_WEEKDAYS_SAME)).getSlot1From())) {
                return false;
            }
            if (!((TimingsViewModel) masterData.get(i)).getSlot2From().equalsIgnoreCase(((TimingsViewModel) masterData.get(i + ALL_WEEKDAYS_SAME)).getSlot2From())) {
                return false;
            }
            if (!((TimingsViewModel) masterData.get(i)).getSlot1To().equalsIgnoreCase(((TimingsViewModel) masterData.get(i + ALL_WEEKDAYS_SAME)).getSlot1To())) {
                return false;
            }
            if (((TimingsViewModel) masterData.get(i)).getSlot1To().equalsIgnoreCase(((TimingsViewModel) masterData.get(i + ALL_WEEKDAYS_SAME)).getSlot1To())) {
                isIt = true;
            }
        }
        return isIt;
    }

    public void updateTimings(ArrayList<String> newTimings, int session)
    {
        if (session == ALL_WEEKDAYS_SAME) {
            this.helper.helperHolder.mAddSlot1Tv.setVisibility(View.GONE);
            this.helper.helperHolder.mSlot1Ll.setVisibility(View.VISIBLE);
        } else {
            this.helper.helperHolder.mAddSlot2Tv.setVisibility(View.GONE);
            this.helper.helperHolder.mSlot2Ll.setVisibility(View.VISIBLE);
        }
        String newFrom = makeTimeString((String) newTimings.get(0), (String) newTimings.get(ALL_WEEKDAYS_SAME));
        String newTo = makeTimeString((String) newTimings.get(2), (String) newTimings.get(3));
        if (session == ALL_WEEKDAYS_SAME) {
            this.helper.helperHolder.mDay1From.setText(newFrom);
            this.helper.helperHolder.mDay1To.setText(newTo);
            ((TimingsViewModel) this.mDataSet.get(this.helper.viewPosition)).setSlot1From(newFrom);
            ((TimingsViewModel) this.mDataSet.get(this.helper.viewPosition)).setSlot1To(newTo);
        } else {
            this.helper.helperHolder.mDay2From.setText(newFrom);
            this.helper.helperHolder.mDay2To.setText(newTo);
            ((TimingsViewModel) this.mDataSet.get(this.helper.viewPosition)).setSlot2From(newFrom);
            ((TimingsViewModel) this.mDataSet.get(this.helper.viewPosition)).setSlot2To(newTo);
        }
        if (this.helper.helperHolder.mSlot2Ll.getVisibility() == View.GONE) {
            this.helper.helperHolder.mAddSlot2Tv.setVisibility(View.VISIBLE);
        } else {
            this.helper.helperHolder.mAddSlot2Tv.setVisibility(View.GONE);
        }
        if (this.helper.viewPosition == 0) {
            setWeekdays(this.mWeekdaysToggleSwitchStatus, session, newFrom, newTo);
        }
        this.mMasterDataSet.set((this.mWeekdaysToggleSwitchStatus * 4) + this.helper.viewPosition, new TimingsViewModel((TimingsViewModel) this.mDataSet.get(this.helper.viewPosition)));
        this.helper = null;
    }

    private void setWeekdays(int switchValue, int session, String from, String to){
        if (switchValue == ALL_WEEKDAYS_SAME) {
            for (int i = 0; i < 5; i += ALL_WEEKDAYS_SAME) {
                if (session == ALL_WEEKDAYS_SAME) {
                    if (!TextUtils.isEmpty(from)) {
                        ((TimingsViewModel) this.mMasterDataSet.get(i)).setSlot1From(from);
                    }
                    if (!TextUtils.isEmpty(to)) {
                        ((TimingsViewModel) this.mMasterDataSet.get(i)).setSlot1To(to);
                    }
                } else {
                    if (!TextUtils.isEmpty(from)) {
                        ((TimingsViewModel) this.mMasterDataSet.get(i)).setSlot2From(from);
                    }
                    if (!TextUtils.isEmpty(to)) {
                        ((TimingsViewModel) this.mMasterDataSet.get(i)).setSlot2To(to);
                    }
                }
            }
        }
    }

    private void resetWeekdays(int session) {
        String from2 = null;
        String to2 = null;
        if (!TextUtils.isEmpty(((TimingsViewModel) this.mDataSet.get(0)).getSlot2From())) {
            from2 = ((TimingsViewModel) this.mDataSet.get(0)).getSlot2From();
            to2 = ((TimingsViewModel) this.mDataSet.get(0)).getSlot2To();
        }
        for (int i = 0; i < 5; i += ALL_WEEKDAYS_SAME) {
            if (session != ALL_WEEKDAYS_SAME) {
                ((TimingsViewModel) this.mMasterDataSet.get(i)).setSlot2From(BuildConfig.FLAVOR);
                ((TimingsViewModel) this.mMasterDataSet.get(i)).setSlot2To(BuildConfig.FLAVOR);
                notifyDataSetChanged();
            } else if (TextUtils.isEmpty(from2)) {
                ((TimingsViewModel) this.mMasterDataSet.get(i)).setSlot1From(BuildConfig.FLAVOR);
                ((TimingsViewModel) this.mMasterDataSet.get(i)).setSlot1To(BuildConfig.FLAVOR);
            } else {
                ((TimingsViewModel) this.mMasterDataSet.get(i)).setSlot1From(from2);
                ((TimingsViewModel) this.mMasterDataSet.get(i)).setSlot1To(to2);
                ((TimingsViewModel) this.mMasterDataSet.get(i)).setSlot2From(BuildConfig.FLAVOR);
                ((TimingsViewModel) this.mMasterDataSet.get(i)).setSlot2To(BuildConfig.FLAVOR);
            }
        }
        notifyDataSetChanged();
    }

    public void toggleDays(boolean toggle, LinearLayoutManager manager) {
        int i;
        if (toggle) {
            this.mWeekdaysToggleSwitchStatus = ALL_WEEKDAYS_SAME;
            this.mDataSet.add(0, new TimingsViewModel(days[0]));
            notifyItemInserted(0);
            manager.scrollToPosition(0);
            for (i = ALL_WEEKDAYS_SAME; i < days.length - 2; i += ALL_WEEKDAYS_SAME) {
                this.mDataSet.remove(ALL_WEEKDAYS_SAME);
                notifyItemRemoved(ALL_WEEKDAYS_SAME);
            }
            if (isSameForWeekdays(this.mMasterDataSet, this.mDataSet)) {
                this.mDataSet.set(0, new TimingsViewModel((TimingsViewModel) this.mMasterDataSet.get(0)));
                ((TimingsViewModel) this.mDataSet.get(0)).setDay(days[0]);
                notifyDataSetChanged();
                return;
            }
            return;
        }
        this.mWeekdaysToggleSwitchStatus = 0;
        for (i = 0; i < 3; i += ALL_WEEKDAYS_SAME) {
            this.mDataSet.remove(0);
            notifyItemRemoved(0);
        }
        for (i = 0; i < this.mMasterDataSet.size(); i += ALL_WEEKDAYS_SAME) {
            this.mDataSet.add(i, new TimingsViewModel((TimingsViewModel) this.mMasterDataSet.get(i)));
            ((TimingsViewModel) this.mDataSet.get(i)).setDay(days[i + ALL_WEEKDAYS_SAME]);
            notifyItemInserted(i);
        }
    }

    private String makeTimeString(String s, String s1)
    {
        return (new StringBuilder()).append(s).append(":").append(s1).toString();
    }
}
