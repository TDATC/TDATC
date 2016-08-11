package com.skoruz.amwell.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.patient.ListItem;
import com.skoruz.amwell.physicianEntity.PhysicianDetails;

import java.util.ArrayList;

/**
 * Created by keerthi on 30/12/15.
 */
public class CustomAdapter extends BaseAdapter implements Filterable {

    Context context;
    int layoutResourceId;
    ListItem data[] = null;
    private ArrayList<PhysicianDetails> originalList;
    private ArrayList<PhysicianDetails> searchList;
    private SearchFilter filter;

    public CustomAdapter(Context context, int layoutResourceId, ListItem data[],ArrayList<PhysicianDetails> detailsArrayList) {
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
            this.searchList = new ArrayList<PhysicianDetails>();
            this.searchList.addAll(detailsArrayList);
            this.originalList = new ArrayList<PhysicianDetails>();
            this.originalList.addAll(detailsArrayList);
    }

    @Override
    public int getCount() {
        if (originalList.size()>0){
            return originalList.size();
        }else {
            return data.length;
        }
    }

    @Override
    public Object getItem(int position) {
        if (originalList.size()>0){
            return originalList.get(position);
        }else {
            return data[position];
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        if (filter==null){
            filter=new SearchFilter();
        }
        return filter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        CustomHolder customHolder = null;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layoutResourceId, parent, false);

            customHolder = new CustomHolder();
            customHolder.imageIcon = (ImageView) view.findViewById(R.id.imgIcon);
            customHolder.doc_name = (TextView) view.findViewById(R.id.doc_search_name);
            customHolder.doc_speciality= (TextView) view.findViewById(R.id.doc_search_speciality);
            customHolder.doc_type= (TextView) view.findViewById(R.id.doc_search_type);
            view.setTag(customHolder);
        } else {
            customHolder = (CustomHolder) view.getTag();
        }

        if (originalList.size()>0){
            customHolder.doc_speciality.setVisibility(View.VISIBLE);
            customHolder.doc_type.setVisibility(View.VISIBLE);
            PhysicianDetails physicianDetails=originalList.get(position);
            if (physicianDetails!=null){
                customHolder.imageIcon.setVisibility(View.GONE);
            }
        }

        if (data.length>0){
            ListItem listItem = data[position];
            if (listItem!=null){
                if (customHolder.imageIcon.getVisibility()!=View.GONE){
                    customHolder.imageIcon.setImageResource(listItem.icon);
                }else {
                    customHolder.imageIcon.setVisibility(View.VISIBLE);
                    customHolder.imageIcon.setImageResource(listItem.icon);
                }
                customHolder.doc_name.setText(listItem.title);
            }
        }

        return view;
    }

    static class CustomHolder {
        ImageView imageIcon;
        TextView doc_name;
        TextView doc_speciality;
        TextView doc_type;
    }

    public class SearchFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint=constraint.toString().toLowerCase();
            FilterResults results=new FilterResults();
            if (constraint!=null && constraint.toString().length()>0){
                ArrayList<PhysicianDetails> details=new ArrayList<>();
                for (int i=0,l=originalList.size();i<l;i++){
                    PhysicianDetails physicianDetails=originalList.get(i);
                    if (physicianDetails.toString().toLowerCase().contains(constraint)){
                        details.add(physicianDetails);
                    }
                }
                results.count=details.size();
                results.values=details;
            }else {
                synchronized (this){
                    results.count=originalList.size();
                    results.values=originalList;
                }
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            searchList=(ArrayList<PhysicianDetails>)results.values;
            notifyDataSetChanged();
        }
    }
}
