package com.skoruz.amwell.patient.appointment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.misc.SlotDayHolder;
import com.skoruz.amwell.misc.SlotPeriodHolder;
import com.skoruz.amwell.patientEntity.TimeSlots.TimeSlot.Slot.TimeSlotsPerSlot;
import com.skoruz.amwell.ui.RecyclerViewCustom;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.Utils.OnFragmentInteractionListener;
import com.skoruz.amwell.utils.Utils;
import android.view.View.OnClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class TimSlotFragment extends Fragment {
    public final long DEFAULT_DURATION = 300;
    private Bundle args;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", AppController.getInstance().getLocale());
    private SimpleDateFormat headerTimeFormat = new SimpleDateFormat("hh:mm aa", AppController.getInstance().getLocale());
    HashMap<String, ViewGroup> layoutCache = new HashMap();
    private SlotDayHolder mDayHolder;
    private OnFragmentInteractionListener mListener;
    private LinearLayout mParentLinearLayout;
    private ScrollView mScrollView;
    public int mSlotNumbers;
    private View root;
    private SimpleDateFormat slotTimeFormat = new SimpleDateFormat("hh:mm aa", AppController.getInstance().getLocale());

    class MyAdapter extends Adapter<RecyclerViewHolder> {
        private ArrayList<ArrayList<TimeSlotsPerSlot>> mDataset;

        public MyAdapter(ArrayList<ArrayList<TimeSlotsPerSlot>> myDataset) {
            this.mDataset = myDataset;
        }

        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slot, parent, false));
        }

        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            int rem = position % TimSlotFragment.this.mSlotNumbers;
            final TimeSlotsPerSlot slot = (TimeSlotsPerSlot) ((ArrayList) this.mDataset.get(position / TimSlotFragment.this.mSlotNumbers)).get(rem);
            if (slot != null) {
                try {
                    String displayTxt = TimSlotFragment.this.slotTimeFormat.format(TimSlotFragment.this.dateFormat.parse(slot.ts));
                    if (slot.available) {
                        holder.mTextView.setTextColor(TimSlotFragment.this.getResources().getColor(R.color.color_available));
                    } else {
                        holder.mTextView.setTextColor(TimSlotFragment.this.getResources().getColor(R.color.availableDayDiabledColor));
                        holder.mTextView.setEnabled(false);
                    }
                    holder.mTextView.setText(displayTxt);
                    holder.mTextView.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            if (slot != null && slot.available) {
                                TimSlotFragment.this.enableBookAppointment(slot);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public int getItemCount() {
            return this.mDataset.size() * TimSlotFragment.this.mSlotNumbers;
        }
    }

    public static class RecyclerViewHolder extends ViewHolder {
        public TextView mTextView;

        public RecyclerViewHolder(View v) {
            super(v);
            this.mTextView = (TextView) v.findViewById(R.id.slot_info);
        }
    }

    public static TimSlotFragment newInstance(SlotDayHolder slotDayHolder, int n) {
        TimSlotFragment timSlotFragment = new TimSlotFragment();
        Bundle bundle = new Bundle();
        timSlotFragment.mSlotNumbers = n;
        bundle.putInt("slot_numbers", n);
        bundle.putParcelable("bundle_timeslot", (Parcelable)slotDayHolder);
        timSlotFragment.setArguments(bundle);
        return timSlotFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.args = bundle;
        } else {
            this.args = getArguments();
        }
        this.mSlotNumbers = this.args.getInt("slot_numbers", 0);
        this.mDayHolder = (SlotDayHolder) this.args.getParcelable("bundle_timeslot");
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.root = layoutInflater.inflate(R.layout.fragment_tim_slot, viewGroup, false);
        this.mParentLinearLayout = (LinearLayout) this.root.findViewById(R.id.timeslot_llay);
        this.mScrollView = (ScrollView) this.root.findViewById(R.id.timeslot_scroll);
        int openSlotPosition = this.mDayHolder.getOpenSlotPosition();
        if (openSlotPosition == -1) {
            openSlotPosition = 0;
        }
        for (int i = 0; i < this.mDayHolder.getSize(); i++) {
            boolean isExpand;
            if (openSlotPosition == i) {
                isExpand = true;
            } else {
                isExpand = false;
            }
            ViewGroup layout = createView(i, isExpand);
            if (layout != null) {
                this.layoutCache.put(this.mDayHolder.getPeroid(i).layoutId, layout);
                this.mParentLinearLayout.addView(layout);
            }
        }
        View view = this.root;
        return view;
    }

    public ViewGroup createView(int position, boolean expand){
        SlotPeriodHolder periodHolder = this.mDayHolder.getPeroid(position);
        final ViewGroup convertView = (ViewGroup) ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_appointment_group, null);
        if (!periodHolder.isOpenSlotAvailable) {
            return null;
        }
        TextView txtHeaderPrimary = (TextView) convertView.findViewById(R.id.item_apmnt_txt_header_primary);
        TextView txtHeaderSecondary = (TextView) convertView.findViewById(R.id.item_apmnt_txt_header_secondary);
        View topParent = convertView.findViewById(R.id.tops);
        View upperSeparatorView = convertView.findViewById(R.id.v_upper_separator);
        ArrayList<ArrayList<TimeSlotsPerSlot>> hourSlotList = periodHolder.timeslot;
        final RecyclerViewCustom recyclerView = (RecyclerViewCustom) convertView.findViewById(R.id.item_apmnt_recyler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.setHorizontalScrollBarEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), this.mSlotNumbers));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new MyAdapter(hourSlotList));
        int row = hourSlotList.size();
        int headerHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        int contentHeight = (row * getResources().getDimensionPixelSize(R.dimen.doctor_subheader_height)) + Utils.dpToPx(getActivity(), 2.0f);
        int extraMargin = contentHeight - 5;
        LayoutParams laypar_slot = (LayoutParams) recyclerView.getLayoutParams();
        laypar_slot.height = contentHeight;
        if (expand) {
            periodHolder.isExpanded = true;
            periodHolder.mExtraMargin = extraMargin;
            recyclerView.setVisibility(View.VISIBLE);
            upperSeparatorView.setVisibility(View.VISIBLE);
            topParent.setClickable(false);
            topParent.setBackgroundDrawable(null);
        } else {
            periodHolder.isExpanded = false;
            periodHolder.mExtraMargin = extraMargin;
            laypar_slot.bottomMargin = -extraMargin;
            recyclerView.setVisibility(View.INVISIBLE);
            upperSeparatorView.setVisibility(View.INVISIBLE);
            topParent.setClickable(true);
            //topParent.setBackgroundResource(2130837873);
        }
        periodHolder.mContentHeightPixel = contentHeight;
        ImageView img = (ImageView) convertView.findViewById(R.id.item_apmnt_img_header);
        final View view = upperSeparatorView;
        topParent.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                boolean block = false;
                Animation headAnimation = v.getAnimation();
                Animation bodyAnimation = recyclerView.getAnimation();
                if (!(bodyAnimation == null || bodyAnimation.hasEnded())) {
                    block = true;
                }
                if (!block) {
                    Integer intval = (Integer) convertView.getTag();
                    if (!TimSlotFragment.this.mDayHolder.getPeroid(intval.intValue()).isExpanded) {
                        for (int i = 0; i < TimSlotFragment.this.mDayHolder.getSize(); i++) {
                            SlotPeriodHolder pHolder = TimSlotFragment.this.mDayHolder.getPeroid(i);
                            if (pHolder.isExpanded) {
                                View convView = (View) TimSlotFragment.this.layoutCache.get(i + "");
                                if (convView != null) {
                                    RecyclerView sl = (RecyclerView) convView.findViewById(R.id.item_apmnt_recyler);
                                    ViewGroup topLayout = (ViewGroup) convView.findViewById(R.id.tops);
                                    View upperSeparatorView = convView.findViewById(R.id.v_upper_separator);
                                    //topLayout.setBackgroundResource(2130837873);
                                    pHolder.isExpanded = false;
                                    TimSlotFragment.this.collapseView(sl, topLayout, pHolder, upperSeparatorView);
                                }
                            }
                        }
                        TimSlotFragment.this.mDayHolder.getPeroid(intval.intValue()).isExpanded = true;
                        v.setBackgroundDrawable(null);
                        v.setClickable(false);
                        TimSlotFragment.this.expandView(recyclerView, TimSlotFragment.this.mDayHolder.getPeroid(intval.intValue()), view);
                    }
                }
            }
        });
        img.setBackgroundResource(periodHolder.mSlotImage);
        txtHeaderPrimary.setText(periodHolder.primaryHeader);
        txtHeaderSecondary.setText(periodHolder.secondaryHeader);
        convertView.setTag(new Integer(position));
        return convertView;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("slot_numbers", this.mSlotNumbers);
        outState.putParcelable("bundle_timeslot", this.mDayHolder);
    }

    public void expandView(View contentView, SlotPeriodHolder filter, View upperSeparatorView) {
        expandView(contentView, filter, upperSeparatorView, 300);
    }

    public void expandView(final View contentView, final SlotPeriodHolder filter, View upperSeparatorView, long duration) {
        final LinearLayout.LayoutParams mViewLayoutParams = (LayoutParams) contentView.getLayoutParams();
        Animation expandAnim = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime < 1.0f) {
                    mViewLayoutParams.bottomMargin = (int) (((float) (-filter.mExtraMargin)) + (((float) filter.mExtraMargin) * interpolatedTime));
                    contentView.requestLayout();
                }
            }

            public boolean willChangeBounds() {
                return true;
            }
        };
        expandAnim.setDuration(duration);
        final View view = upperSeparatorView;
        final View view2 = contentView;
        final SlotPeriodHolder slotPeriodHolder = filter;
        expandAnim.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            public void onAnimationEnd(Animation animation) {
                new Handler().post(new Runnable() {
                    public void run() {
                        mViewLayoutParams.bottomMargin = 0;
                        view2.setLayoutParams(mViewLayoutParams);
                        view2.requestLayout();
                        view2.setVisibility(View.VISIBLE);
                        int position = 0;
                        int headerHeight = TimSlotFragment.this.getResources().getDimensionPixelSize(R.dimen.header_height) - 5;
                        try {
                            position = Integer.parseInt(slotPeriodHolder.layoutId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (position > 0) {
                            TimSlotFragment.this.mScrollView.smoothScrollTo(0, position * headerHeight);
                        }
                    }
                });
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        contentView.setVisibility(View.VISIBLE);
        contentView.requestLayout();
        contentView.clearAnimation();
        contentView.startAnimation(expandAnim);
    }

    public void collapseView(View view, View view2, SlotPeriodHolder slotPeriodHolder, View view3) {
        this.collapseView(view, view2, slotPeriodHolder, view3, 300);
    }

    public void collapseView(final View view, final View view2, final SlotPeriodHolder slotPeriodHolder, final View view3, long l) {
        final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
        Animation animation = new Animation(){

            protected void applyTransformation(float f, Transformation transformation) {
                if (f < 1.0f) {
                    int n;
                    layoutParams.bottomMargin = n = (int)(f * (float)(- slotPeriodHolder.mExtraMargin));
                    view.requestLayout();
                }
            }

            public void initialize(int n, int n2, int n3, int n4) {
                super.initialize(n, n2, n3, n4);
            }

            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(l);
        animation.setAnimationListener(new Animation.AnimationListener(){

            public void onAnimationEnd(Animation animation) {
                new Handler().post(new Runnable(){

                    public void run() {
                        layoutParams.bottomMargin = - slotPeriodHolder.mExtraMargin;
                        view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                        view.setVisibility(View.INVISIBLE);
                        view.requestLayout();
                        view3.setVisibility(View.INVISIBLE);
                        view2.setClickable(true);
                    }
                });
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

        });
        view.clearAnimation();
        view.startAnimation(animation);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    private void enableBookAppointment(TimeSlotsPerSlot timeSlotsPerSlot) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("bundle_slot", (Parcelable) timeSlotsPerSlot);
        this.mListener.onFragmentInteraction(bundle);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}
