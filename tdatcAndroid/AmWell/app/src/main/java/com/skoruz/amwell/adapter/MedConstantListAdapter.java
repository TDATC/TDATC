package com.skoruz.amwell.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.patientEntity.Medication;

import java.util.ArrayList;

/**
 * Created by SKoruz-Keerthi on 14-09-2015.
 */
public class MedConstantListAdapter extends RecyclerView.Adapter<MedConstantListAdapter.MedDataListHolder>{

    private ArrayList<Medication> medicationArrayList;
    private OnItemClickListener onItemClickListener;

    public MedConstantListAdapter(ArrayList<Medication> medicationArrayList) {
        this.medicationArrayList = medicationArrayList;
    }

    @Override
    public MedDataListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.medconstantitem,parent,false);
        MedDataListHolder medDataListHolder=new MedDataListHolder(view,this);
        return medDataListHolder;
    }

    @Override
    public void onBindViewHolder(MedDataListHolder holder, int position) {
        holder.medName.setText(medicationArrayList.get(position).getMedicineName());
    }

    @Override
    public int getItemCount() {
        return medicationArrayList.size();
    }

    public static class MedDataListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private MedConstantListAdapter parent;
        TextView medName;
        public MedDataListHolder(View itemView,MedConstantListAdapter parent) {
            super(itemView);
            medName= (TextView) itemView.findViewById(R.id.med_const_name);
            this.parent=parent;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final OnItemClickListener listener = parent.getOnItemClickListener();
            if(listener != null){
                listener.onItemClick(this, getPosition());
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener=listener;
    }

    public OnItemClickListener getOnItemClickListener(){
        return onItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(MedDataListHolder item, int position);
    }
}
