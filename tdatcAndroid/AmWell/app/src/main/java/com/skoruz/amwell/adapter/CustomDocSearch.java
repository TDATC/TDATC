package com.skoruz.amwell.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.patient.ListItem;
import com.skoruz.amwell.physicianEntity.PhysicianDetails;

import java.util.ArrayList;

/**
 * Created by keerthi on 4/1/16.
 */
public class CustomDocSearch extends ArrayAdapter<PhysicianDetails> {

    private ArrayList<PhysicianDetails> physicianDetails;
    private Context context;
    private int layoutResourceId;

    public CustomDocSearch(Context context, int resource, ArrayList<PhysicianDetails> physicianDetails,int layoutResourceId) {
        super(context, resource);
        this.physicianDetails = physicianDetails;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public int getCount() {
        return physicianDetails.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        CustomDocSearchHolder customDocSearchHolder = null;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layoutResourceId, parent, false);

            customDocSearchHolder = new CustomDocSearchHolder();
            customDocSearchHolder.doc_name = (TextView) view.findViewById(R.id.doc_search_name);
            customDocSearchHolder.doc_speciality = (TextView) view.findViewById(R.id.doc_search_speciality);
            customDocSearchHolder.doc_type= (TextView) view.findViewById(R.id.doc_search_type);

            view.setTag(customDocSearchHolder);
        } else {
            customDocSearchHolder = (CustomDocSearchHolder) view.getTag();
        }

        PhysicianDetails details = physicianDetails.get(position);
        if (details!=null){
            customDocSearchHolder.doc_name.setText(context.getString(R.string.doc_prefix)+details.getUser().getFirstName()+" "+details.getUser().getLastName());
            customDocSearchHolder.doc_speciality.setText(details.getSpecializations().getSpecializations());
        }

        return view;
    }

    static class CustomDocSearchHolder{
        TextView doc_name;
        TextView doc_speciality;
        TextView doc_type;
    }
}
