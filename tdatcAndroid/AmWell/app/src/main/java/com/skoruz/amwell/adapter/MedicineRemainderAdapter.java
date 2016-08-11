package com.skoruz.amwell.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.patientEntity.PatientMedicationData;

import java.util.List;

/**
 * Created by SKoruz-Keerthi on 12-10-2015.
 */
public class MedicineRemainderAdapter extends BaseAdapter {

    private Activity activity;
    private List<PatientMedicationData> patientMedicationDataArrayList;
    private LayoutInflater inflater;

    public MedicineRemainderAdapter(List<PatientMedicationData> patientMedicationDataArrayList, Activity activity) {
        this.patientMedicationDataArrayList = patientMedicationDataArrayList;
        this.activity=activity;
    }

    @Override
    public int getCount() {
       return patientMedicationDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return patientMedicationDataArrayList.get(position);
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


        if (patientMedicationDataArrayList.size()>0){
            PatientMedicationData patientMedicationData=patientMedicationDataArrayList.get(position);
            viewHolder.name.setText(patientMedicationData.getMedicine().getMedicineName());
        }

        return convertView;
    }

    static class ViewHolder{
        TextView name;
    }
}
