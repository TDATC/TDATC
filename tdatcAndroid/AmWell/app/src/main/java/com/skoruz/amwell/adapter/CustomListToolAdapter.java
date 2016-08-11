package com.skoruz.amwell.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.patientEntity.MeasurementToolOldData;

import java.util.List;

/**
 * Created by Skoruz-Ashish on 9/5/2015.
 */
public class CustomListToolAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<MeasurementToolOldData> measurementToolOldDataList;

    public CustomListToolAdapter(Activity activity, List<MeasurementToolOldData> measurementToolOldDataList) {
        this.activity = activity;
        this.measurementToolOldDataList = measurementToolOldDataList;
    }

    @Override
    public int getCount() {
        return measurementToolOldDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return measurementToolOldDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolderItem;
        if (convertView==null){
            inflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.record_singleitem,parent,false);

            viewHolderItem=new ViewHolderItem();
            viewHolderItem.toolName= (TextView) convertView.findViewById(R.id.toolName);
            viewHolderItem.toolValue= (TextView) convertView.findViewById(R.id.uploaded_value);
            viewHolderItem.uploadedDate= (TextView) convertView.findViewById(R.id.uploaded_date);

            convertView.setTag(viewHolderItem);
        }else{
            viewHolderItem= (ViewHolderItem) convertView.getTag();
        }

        MeasurementToolOldData measurementToolOldData=measurementToolOldDataList.get(position);
        if (measurementToolOldData!=null){
            viewHolderItem.toolName.setText(measurementToolOldData.getToolName());
            viewHolderItem.toolValue.setText(measurementToolOldData.getUploadedValue());
            viewHolderItem.uploadedDate.setText(measurementToolOldData.getUploadedDate());
        }
        return convertView;
    }

    static class ViewHolderItem{
        TextView toolName;
        TextView toolValue;
        TextView uploadedDate;
    }
}
