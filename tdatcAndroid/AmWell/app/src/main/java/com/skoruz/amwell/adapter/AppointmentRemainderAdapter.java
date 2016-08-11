package com.skoruz.amwell.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.patientEntity.PatientAppointment;

import java.util.ArrayList;

/**
 * Created by SKoruz-Keerthi on 13-10-2015.
 */
public class AppointmentRemainderAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<PatientAppointment> patientAppointmentArrayList;
    private LayoutInflater inflater;

    public AppointmentRemainderAdapter(ArrayList<PatientAppointment> patientAppointmentArrayList, Activity activity) {
        this.patientAppointmentArrayList = patientAppointmentArrayList;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return patientAppointmentArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return patientAppointmentArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            inflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.medconstantitem,parent,false);

            viewHolder=new ViewHolder();
            viewHolder.name= (TextView) convertView.findViewById(R.id.med_const_name);

            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }


        if (patientAppointmentArrayList.size()>0){
            PatientAppointment patientAppointment=patientAppointmentArrayList.get(position);
            viewHolder.name.setText(patientAppointment.getAppointmentDescription());
        }

        return convertView;
    }

    static class ViewHolder{
        TextView name;
    }
}
