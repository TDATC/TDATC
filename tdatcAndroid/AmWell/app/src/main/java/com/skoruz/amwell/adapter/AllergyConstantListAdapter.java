package com.skoruz.amwell.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.patientEntity.PatientAllergy;

import java.util.ArrayList;

/**
 * Created by SKoruz-Keerthi on 14-09-2015.
 */
public class AllergyConstantListAdapter extends RecyclerView.Adapter<AllergyConstantListAdapter.AllergyDataListHolder>{

    private ArrayList<PatientAllergy> patientAllergyList;
    private OnItemClickListener onItemClickListener;

    public AllergyConstantListAdapter(ArrayList<PatientAllergy> patientAllergyList) {
        this.patientAllergyList = patientAllergyList;
    }

    @Override
    public AllergyDataListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.medconstantitem,parent,false);
        AllergyDataListHolder medDataListHolder=new AllergyDataListHolder(view,this);
        return medDataListHolder;
    }

    @Override
    public void onBindViewHolder(AllergyDataListHolder holder, int position) {
        holder.medName.setText(patientAllergyList.get(position).getAllergyName());
    }

    @Override
    public int getItemCount() {
        return patientAllergyList.size();
    }

    public static class AllergyDataListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private AllergyConstantListAdapter parent;
        TextView medName;
        public AllergyDataListHolder(View itemView,AllergyConstantListAdapter parent) {
            super(itemView);
            this.parent=parent;
            medName= (TextView) itemView.findViewById(R.id.med_const_name);
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

    public void setOnItemClickListener(OnItemClickListener myClickListener){
        onItemClickListener=myClickListener;
    }

    public OnItemClickListener getOnItemClickListener(){
        return onItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(AllergyDataListHolder item, int position);
    }
}
