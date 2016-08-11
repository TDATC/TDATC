package com.skoruz.amwell.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.physicianEntity.Affliation;
import com.skoruz.amwell.physicianEntity.Qualification;

import java.util.List;

/**
 * Created by SKoruz-Keerthi on 19-10-2015.
 */
public class AffiliationAdapter extends BaseAdapter {

    private Activity activity;
    private List<Affliation> affliationList;
    private LayoutInflater inflater;

    public AffiliationAdapter(Activity activity, List<Affliation> affliationList){
        this.activity=activity;
        this.affliationList=affliationList;
    }

    @Override
    public int getCount() {
        return affliationList.size();
    }

    @Override
    public Object getItem(int position) {
        return affliationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AffiliationViewHolder affiliationViewHolder;
        if(convertView==null){
            inflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.medconstantitem,parent,false);
            affiliationViewHolder=new AffiliationViewHolder();

            affiliationViewHolder.name= (TextView) convertView.findViewById(R.id.med_const_name);
            convertView.setTag(affiliationViewHolder);
        }else{
            affiliationViewHolder= (AffiliationViewHolder) convertView.getTag();
        }

        try {
            Affliation affliation= affliationList.get(position);
            if (affliation != null) {
                affiliationViewHolder.name.setText(affliation.getUniversityName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    static class AffiliationViewHolder{
        TextView name;
    }
}

