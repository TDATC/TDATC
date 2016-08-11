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
import com.skoruz.amwell.physicianEntity.Specialization;

import java.util.List;

/**
 * Created by SKoruz-Keerthi on 04-11-2015.
 */
public class CityAdapter extends BaseAdapter {

    private Activity activity;
    private List<City> cityList;
    private LayoutInflater inflater;

    public CityAdapter(Activity activity, List<City> cityList) {
        this.activity = activity;
        this.cityList = cityList;
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CityViewHolder cityViewHolder;
        if (convertView == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.medconstantitem, parent, false);
            cityViewHolder = new CityViewHolder();

            cityViewHolder.name = (TextView) convertView.findViewById(R.id.med_const_name);
            convertView.setTag(cityViewHolder);
        } else {
            cityViewHolder = (CityViewHolder) convertView.getTag();
        }

        try {
            City city = cityList.get(position);
            if (city != null) {
                cityViewHolder.name.setText(city.getCityName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    static class CityViewHolder {
        TextView name;
    }
}
