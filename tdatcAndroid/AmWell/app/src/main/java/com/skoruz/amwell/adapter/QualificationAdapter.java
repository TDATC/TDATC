package com.skoruz.amwell.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.physicianEntity.Qualification;

import java.util.List;

/**
 * Created by SKoruz-Keerthi on 19-10-2015.
 */
public class QualificationAdapter extends BaseAdapter {

    private Activity activity;
    private List<Qualification> qualificationList;
    private LayoutInflater inflater;

    public QualificationAdapter(Activity activity, List<Qualification> qualificationList){
        this.activity=activity;
        this.qualificationList=qualificationList;
    }

    @Override
    public int getCount() {
        return qualificationList.size();
    }

    @Override
    public Object getItem(int position) {
        return qualificationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QualificationViewHolder qualificationViewHolder;
        if(convertView==null){
            inflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.medconstantitem,parent,false);
            qualificationViewHolder=new QualificationViewHolder();

            qualificationViewHolder.name= (TextView) convertView.findViewById(R.id.med_const_name);
            convertView.setTag(qualificationViewHolder);
        }else{
            qualificationViewHolder= (QualificationViewHolder) convertView.getTag();
        }

        try {
            Qualification qualification= qualificationList.get(position);
            if (qualification != null) {
                qualificationViewHolder.name.setText(qualification.getQualification());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    static class QualificationViewHolder{
        TextView name;
    }
}
