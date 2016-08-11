package com.skoruz.amwell.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.constants.Constants;
import com.skoruz.amwell.patientEntity.PatientAllergyDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by SKoruz-Keerthi on 14-09-2015.
 */
public class AllergyItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<PatientAllergyDetails> patientAllergyDetailsList;
    private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.US);
    private static final int EMPTY_VIEW = 10;

    public AllergyItemAdapter(ArrayList<PatientAllergyDetails> patientAllergyDetailsList) {
        this.patientAllergyDetailsList = patientAllergyDetailsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType==EMPTY_VIEW){
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_empty,parent,false);
            EmptyViewHolder emptyViewHolder=new EmptyViewHolder(view);
            return emptyViewHolder;
        }
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.allergy_single_item,parent,false);
        AllergyDataHolder allergyDataHolder=new AllergyDataHolder(view);
        return allergyDataHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof AllergyDataHolder){
            AllergyDataHolder holder= (AllergyDataHolder) viewHolder;
            try {
                holder.allergyName.setText(patientAllergyDetailsList.get(position).getAllergies().getAllergyName());
                holder.allergyType.setText(patientAllergyDetailsList.get(position).getAllergies().getAllergyType());
                holder.allergyDate.setText(Constants.allergyDate.format(dateFormat.parse(patientAllergyDetailsList.get(position).getInsertedDate())));
                holder.allergyStatus.setText(patientAllergyDetailsList.get(position).getStatus());
                holder.allergyLevel.setText(patientAllergyDetailsList.get(position).getSevere());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return patientAllergyDetailsList.size() > 0 ? patientAllergyDetailsList.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (patientAllergyDetailsList.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }

    public static class AllergyDataHolder extends RecyclerView.ViewHolder{

        TextView allergyName,allergyType,allergyDate,allergyStatus,allergyLevel;
        public AllergyDataHolder(View itemView) {
            super(itemView);
            allergyName= (TextView) itemView.findViewById(R.id.allergy_name);
            allergyType= (TextView) itemView.findViewById(R.id.allergy_type);
            allergyDate= (TextView) itemView.findViewById(R.id.allergy_date);
            allergyStatus= (TextView) itemView.findViewById(R.id.allergy_status);
            allergyLevel= (TextView) itemView.findViewById(R.id.allergy_level);
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
