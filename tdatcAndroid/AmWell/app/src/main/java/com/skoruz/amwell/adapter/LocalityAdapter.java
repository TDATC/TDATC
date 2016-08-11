package com.skoruz.amwell.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.patientEntity.City;
import com.skoruz.amwell.patientEntity.Locality;

import java.util.List;

/**
 * Created by SKoruz-Keerthi on 04-11-2015.
 */
public class LocalityAdapter extends BaseAdapter {

    private Activity activity;
    private List<Locality> localityList;
    private LayoutInflater inflater;

    public LocalityAdapter(Activity activity, List<Locality> localityList) {
        this.activity = activity;
        this.localityList = localityList;
    }

    @Override
    public int getCount() {
        return localityList.size();
    }

    @Override
    public Object getItem(int position) {
        return localityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LocalityViewHolder localityViewHolder;
        if (convertView == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.medconstantitem, parent, false);
            localityViewHolder = new LocalityViewHolder();

            localityViewHolder.name = (TextView) convertView.findViewById(R.id.med_const_name);
            convertView.setTag(localityViewHolder);
        } else {
            localityViewHolder = (LocalityViewHolder) convertView.getTag();
        }

        try {
            Locality locality = localityList.get(position);
            if (locality != null) {
                localityViewHolder.name.setText(locality.getLocationName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    static class LocalityViewHolder {
        TextView name;
    }
}
