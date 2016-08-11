package com.skoruz.amwell.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.constants.Constants;
import com.skoruz.amwell.patient.LabDetails;
import com.skoruz.amwell.patientEntity.SavePdf;
import com.skoruz.amwell.physicianEntity.Specialization;

import java.util.List;

/**
 * Created by SKoruz-Keerthi on 19-10-2015.
 */
public class SpecializationAdapter extends BaseAdapter {

    private Activity activity;
    private List<Specialization> specializationList;
    private LayoutInflater inflater;

    public SpecializationAdapter(Activity activity,List<Specialization> specializationList){
        this.activity=activity;
        this.specializationList=specializationList;
    }

    @Override
    public int getCount() {
        return specializationList.size();
    }

    @Override
    public Object getItem(int position) {
        return specializationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpecializationViewHolder specializationViewHolder;
        if(convertView==null){
            inflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.medconstantitem,parent,false);
            specializationViewHolder=new SpecializationViewHolder();

            specializationViewHolder.name= (TextView) convertView.findViewById(R.id.med_const_name);
            convertView.setTag(specializationViewHolder);
        }else{
            specializationViewHolder= (SpecializationViewHolder) convertView.getTag();
        }

        try {
            Specialization specialization = specializationList.get(position);
            if (specialization != null) {
                specializationViewHolder.name.setText(specialization.getSpecializations());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    static class SpecializationViewHolder{
        TextView name;
    }
}
