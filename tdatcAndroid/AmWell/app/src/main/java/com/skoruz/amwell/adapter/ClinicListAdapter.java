package com.skoruz.amwell.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.patient.MyDoctorsFragment;
import com.skoruz.amwell.physician.BaseTimingsActivity;
import com.skoruz.amwell.physicianEntity.Clinic;
import com.skoruz.amwell.physicianEntity.VisitingTimings;
import com.skoruz.amwell.utils.Utils;

import java.util.List;

/**
 * Created by SKoruz-Keerthi on 30-11-2015.
 */
public class ClinicListAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private List<Clinic> clinicList;
    private LayoutInflater inflater;

    public ClinicListAdapter(Context mContext, List<Clinic> clinicList) {
        this.mContext = mContext;
        this.clinicList = clinicList;
        this.inflater= (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return clinicList.size();
    }

    @Override
    public Object getItem(int position) {
        return clinicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemHolder listItemHolder;
        if (convertView==null){
            listItemHolder=new ListItemHolder();
            convertView=inflater.inflate(R.layout.clinic_list_selector,parent,false);

            listItemHolder.clinic_name= (TextView) convertView.findViewById(R.id.list_clinic_name);
            listItemHolder.mon_time= (TextView) convertView.findViewById(R.id.mon_time);
            listItemHolder.tue_time= (TextView) convertView.findViewById(R.id.tue_time);
            listItemHolder.wed_time= (TextView) convertView.findViewById(R.id.wed_time);
            listItemHolder.thur_time= (TextView) convertView.findViewById(R.id.thur_time);
            listItemHolder.fri_time= (TextView) convertView.findViewById(R.id.fri_time);
            listItemHolder.sat_time= (TextView) convertView.findViewById(R.id.sat_time);
            listItemHolder.sun_time= (TextView) convertView.findViewById(R.id.sun_time);

            convertView.setTag(listItemHolder);
        }
        else {
            listItemHolder= (ListItemHolder) convertView.getTag();
        }

        if (clinicList.size()>0){
            Clinic clinic=clinicList.get(position);
            if (clinic!=null){
                for (VisitingTimings visitingTimings:clinic.getClinicVisitingTimings()) {
                    if (visitingTimings.getId().getTiming_day().equalsIgnoreCase(BaseTimingsActivity.MONDAY)){
                        listItemHolder.mon_time.setVisibility(View.VISIBLE);
                        if (!Utils.isEmptyString(visitingTimings.getSession1_start_time()) && !Utils.isEmptyString(visitingTimings.getSession1_end_time())
                        && !Utils.isEmptyString(visitingTimings.getSession2_start_time()) && !Utils.isEmptyString(visitingTimings.getSession2_end_time())) {
                            listItemHolder.mon_time.setText(MyDoctorsFragment.MON + " (" + visitingTimings.getSession1_start_time() + " - " + visitingTimings.getSession1_end_time() + ") and "
                                    + " (" + visitingTimings.getSession2_start_time() + " - " + visitingTimings.getSession2_end_time() + ")");
                        }else if (!Utils.isEmptyString(visitingTimings.getSession1_start_time()) && !Utils.isEmptyString(visitingTimings.getSession1_end_time())){
                            listItemHolder.mon_time.setText(MyDoctorsFragment.MON + " (" + visitingTimings.getSession1_start_time() + " - " + visitingTimings.getSession1_end_time() + ")");
                        }else if (!Utils.isEmptyString(visitingTimings.getSession2_start_time()) && !Utils.isEmptyString(visitingTimings.getSession2_end_time())){
                            listItemHolder.mon_time.setText(MyDoctorsFragment.MON + " (" + visitingTimings.getSession2_start_time() + " - " + visitingTimings.getSession2_end_time() + ")");
                        }else {
                            listItemHolder.mon_time.setVisibility(View.GONE);
                        }
                    }
                    else if (visitingTimings.getId().getTiming_day().equalsIgnoreCase(BaseTimingsActivity.TUESDAY)){
                        listItemHolder.tue_time.setVisibility(View.VISIBLE);
                        if (!Utils.isEmptyString(visitingTimings.getSession1_start_time()) && !Utils.isEmptyString(visitingTimings.getSession1_end_time())
                                && !Utils.isEmptyString(visitingTimings.getSession2_start_time()) && !Utils.isEmptyString(visitingTimings.getSession2_end_time())) {
                            listItemHolder.tue_time.setText(MyDoctorsFragment.TUE + " (" + visitingTimings.getSession1_start_time() + " - " + visitingTimings.getSession1_end_time() + ") and "
                                    + " (" + visitingTimings.getSession2_start_time() + " - " + visitingTimings.getSession2_end_time() + ")");
                        }else if (!Utils.isEmptyString(visitingTimings.getSession1_start_time()) && !Utils.isEmptyString(visitingTimings.getSession1_end_time())){
                            listItemHolder.tue_time.setText(MyDoctorsFragment.TUE + " (" + visitingTimings.getSession1_start_time() + " - " + visitingTimings.getSession1_end_time() + ")");
                        }else if (!Utils.isEmptyString(visitingTimings.getSession2_start_time()) && !Utils.isEmptyString(visitingTimings.getSession2_end_time())){
                            listItemHolder.tue_time.setText(MyDoctorsFragment.TUE + " (" + visitingTimings.getSession2_start_time() + " - " + visitingTimings.getSession2_end_time() + ")");
                        }else {
                            listItemHolder.tue_time.setVisibility(View.GONE);
                        }
                    }
                    else if (visitingTimings.getId().getTiming_day().equalsIgnoreCase(BaseTimingsActivity.WEDNESDAY)){
                        listItemHolder.wed_time.setVisibility(View.VISIBLE);
                        if (!Utils.isEmptyString(visitingTimings.getSession1_start_time()) && !Utils.isEmptyString(visitingTimings.getSession1_end_time())
                                && !Utils.isEmptyString(visitingTimings.getSession2_start_time()) && !Utils.isEmptyString(visitingTimings.getSession2_end_time())) {
                            listItemHolder.wed_time.setText(MyDoctorsFragment.WED + " (" + visitingTimings.getSession1_start_time() + " - " + visitingTimings.getSession1_end_time() + ") and "
                                    + " (" + visitingTimings.getSession2_start_time() + " - " + visitingTimings.getSession2_end_time() + ")");
                        }else if (!Utils.isEmptyString(visitingTimings.getSession1_start_time()) && !Utils.isEmptyString(visitingTimings.getSession1_end_time())){
                            listItemHolder.wed_time.setText(MyDoctorsFragment.WED + " (" + visitingTimings.getSession1_start_time() + " - " + visitingTimings.getSession1_end_time() + ")");
                        }else if (!Utils.isEmptyString(visitingTimings.getSession2_start_time()) && !Utils.isEmptyString(visitingTimings.getSession2_end_time())){
                            listItemHolder.wed_time.setText(MyDoctorsFragment.WED + " (" + visitingTimings.getSession2_start_time() + " - " + visitingTimings.getSession2_end_time() + ")");
                        }else {
                            listItemHolder.wed_time.setVisibility(View.GONE);
                        }
                    }else if (visitingTimings.getId().getTiming_day().equalsIgnoreCase(BaseTimingsActivity.THURSDAY)){
                        listItemHolder.thur_time.setVisibility(View.VISIBLE);
                        if (!Utils.isEmptyString(visitingTimings.getSession1_start_time()) && !Utils.isEmptyString(visitingTimings.getSession1_end_time())
                                && !Utils.isEmptyString(visitingTimings.getSession2_start_time()) && !Utils.isEmptyString(visitingTimings.getSession2_end_time())) {
                            listItemHolder.thur_time.setText(MyDoctorsFragment.THU + " (" + visitingTimings.getSession1_start_time() + " - " + visitingTimings.getSession1_end_time() + ") and "
                                    + " (" + visitingTimings.getSession2_start_time() + " - " + visitingTimings.getSession2_end_time() + ")");
                        }else if (!Utils.isEmptyString(visitingTimings.getSession1_start_time()) && !Utils.isEmptyString(visitingTimings.getSession1_end_time())){
                            listItemHolder.thur_time.setText(MyDoctorsFragment.THU + " (" + visitingTimings.getSession1_start_time() + " - " + visitingTimings.getSession1_end_time() + ")");
                        }else if (!Utils.isEmptyString(visitingTimings.getSession2_start_time()) && !Utils.isEmptyString(visitingTimings.getSession2_end_time())){
                            listItemHolder.thur_time.setText(MyDoctorsFragment.THU + " (" + visitingTimings.getSession2_start_time() + " - " + visitingTimings.getSession2_end_time() + ")");
                        }else {
                            listItemHolder.thur_time.setVisibility(View.GONE);
                        }
                    }else if (visitingTimings.getId().getTiming_day().equalsIgnoreCase(BaseTimingsActivity.FRIDAY)){
                        listItemHolder.fri_time.setVisibility(View.VISIBLE);
                        if (!Utils.isEmptyString(visitingTimings.getSession1_start_time()) && !Utils.isEmptyString(visitingTimings.getSession1_end_time())
                                && !Utils.isEmptyString(visitingTimings.getSession2_start_time()) && !Utils.isEmptyString(visitingTimings.getSession2_end_time())) {
                            listItemHolder.fri_time.setText(MyDoctorsFragment.FRI + " (" + visitingTimings.getSession1_start_time() + " - " + visitingTimings.getSession1_end_time() + ") and "
                                    + " (" + visitingTimings.getSession2_start_time() + " - " + visitingTimings.getSession2_end_time() + ")");
                        }else if (!Utils.isEmptyString(visitingTimings.getSession1_start_time()) && !Utils.isEmptyString(visitingTimings.getSession1_end_time())){
                            listItemHolder.fri_time.setText(MyDoctorsFragment.FRI + " (" + visitingTimings.getSession1_start_time() + " - " + visitingTimings.getSession1_end_time() + ")");
                        }else if (!Utils.isEmptyString(visitingTimings.getSession2_start_time()) && !Utils.isEmptyString(visitingTimings.getSession2_end_time())){
                            listItemHolder.fri_time.setText(MyDoctorsFragment.FRI + " (" + visitingTimings.getSession2_start_time() + " - " + visitingTimings.getSession2_end_time() + ")");
                        }else {
                            listItemHolder.fri_time.setVisibility(View.GONE);
                        }
                    }else if (visitingTimings.getId().getTiming_day().equalsIgnoreCase(BaseTimingsActivity.SATURDAY)){
                        listItemHolder.sat_time.setVisibility(View.VISIBLE);
                        if (!Utils.isEmptyString(visitingTimings.getSession1_start_time()) && !Utils.isEmptyString(visitingTimings.getSession1_end_time())
                                && !Utils.isEmptyString(visitingTimings.getSession2_start_time()) && !Utils.isEmptyString(visitingTimings.getSession2_end_time())) {
                            listItemHolder.sat_time.setText(MyDoctorsFragment.SAT + " (" + visitingTimings.getSession1_start_time() + " - " + visitingTimings.getSession1_end_time() + ") and "
                                    + " (" + visitingTimings.getSession2_start_time() + " - " + visitingTimings.getSession2_end_time() + ")");
                        }else if (!Utils.isEmptyString(visitingTimings.getSession1_start_time()) && !Utils.isEmptyString(visitingTimings.getSession1_end_time())){
                            listItemHolder.sat_time.setText(MyDoctorsFragment.SAT + " (" + visitingTimings.getSession1_start_time() + " - " + visitingTimings.getSession1_end_time() + ")");
                        }else if (!Utils.isEmptyString(visitingTimings.getSession2_start_time()) && !Utils.isEmptyString(visitingTimings.getSession2_end_time())){
                            listItemHolder.sat_time.setText(MyDoctorsFragment.SAT + " (" + visitingTimings.getSession2_start_time() + " - " + visitingTimings.getSession2_end_time() + ")");
                        }else {
                            listItemHolder.sat_time.setVisibility(View.GONE);
                        }
                    }
                    else if (visitingTimings.getId().getTiming_day().equalsIgnoreCase(BaseTimingsActivity.SUNDAY)){
                        listItemHolder.sun_time.setVisibility(View.VISIBLE);
                        if (!Utils.isEmptyString(visitingTimings.getSession1_start_time()) && !Utils.isEmptyString(visitingTimings.getSession1_end_time())
                                && !Utils.isEmptyString(visitingTimings.getSession2_start_time()) && !Utils.isEmptyString(visitingTimings.getSession2_end_time())) {
                            listItemHolder.sun_time.setText(MyDoctorsFragment.SUN + " (" + visitingTimings.getSession1_start_time() + " - " + visitingTimings.getSession1_end_time() + ") and "
                                    + " (" + visitingTimings.getSession2_start_time() + " - " + visitingTimings.getSession2_end_time() + ")");
                        }else if (!Utils.isEmptyString(visitingTimings.getSession1_start_time()) && !Utils.isEmptyString(visitingTimings.getSession1_end_time())){
                            listItemHolder.sun_time.setText(MyDoctorsFragment.SUN + " (" + visitingTimings.getSession1_start_time() + " - " + visitingTimings.getSession1_end_time() + ")");
                        }else if (!Utils.isEmptyString(visitingTimings.getSession2_start_time()) && !Utils.isEmptyString(visitingTimings.getSession2_end_time())){
                            listItemHolder.sun_time.setText(MyDoctorsFragment.SUN + " (" + visitingTimings.getSession2_start_time() + " - " + visitingTimings.getSession2_end_time() + ")");
                        }else {
                            listItemHolder.sun_time.setVisibility(View.GONE);
                        }
                    }
                }
                listItemHolder.clinic_name.setText(clinic.getClinicName());
            }
        }

        return convertView;
    }

    @Override
    public void onClick(View v) {

    }

    public static class ListItemHolder{
        TextView clinic_name;
        TextView mon_time;
        TextView tue_time;
        TextView wed_time;
        TextView thur_time;
        TextView fri_time;
        TextView sat_time;
        TextView sun_time;
    }
}
