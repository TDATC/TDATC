package com.skoruz.amwell.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.patientEntity.PatientMedicationData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by SKoruz-Keerthi on 14-09-2015.
 */
public class MedItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<PatientMedicationData> patientMedicationDataList;
    private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    private static final int EMPTY_VIEW = 10;

    public MedItemAdapter(ArrayList<PatientMedicationData> patientMedicationDataList) {
        this.patientMedicationDataList = patientMedicationDataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == EMPTY_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_empty, parent, false);
            EmptyViewHolder evh = new EmptyViewHolder(view);
            return evh;
        }

        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_single_item,parent,false);
        MedDataHolder medDataHolder=new MedDataHolder(view);
        return medDataHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MedDataHolder)
        {
            MedDataHolder medDataHolder= (MedDataHolder) holder;
            medDataHolder.medName.setText(patientMedicationDataList.get(position).getMedicine().getMedicineName());
            medDataHolder.medType.setText("(" + patientMedicationDataList.get(position).getMedicine().getMedicineType() + ")");
        try {
            Date fromDate = dateFormat.parse(patientMedicationDataList.get(position).getFromDate());
            Date toDate = dateFormat.parse(patientMedicationDataList.get(position).getToDate());
            long days = (toDate.getTime() - fromDate.getTime()) / (24 * 60 * 60 * 1000);
            if (days > 0 && days < 2) {
                medDataHolder.medDuration.setText(String.valueOf(days) + " day");
            } else {
                medDataHolder.medDuration.setText(String.valueOf(days) + " days");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            medDataHolder.medInterval.setText("(" + patientMedicationDataList.get(position).getInstruction() + ")");
            medDataHolder.medIntakeType.setText(patientMedicationDataList.get(position).getBeforeAfterFood());
    }
    }

    @Override
    public int getItemCount() {
        return patientMedicationDataList.size() > 0 ? patientMedicationDataList.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (patientMedicationDataList.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }

    public static class MedDataHolder extends RecyclerView.ViewHolder{

        TextView medName,medType,medDuration,medInterval,medIntakeType;
        public MedDataHolder(View itemView) {
            super(itemView);
            medName= (TextView) itemView.findViewById(R.id.med_name);
            medType= (TextView) itemView.findViewById(R.id.med_type);
            medDuration= (TextView) itemView.findViewById(R.id.med_duration);
            medInterval= (TextView) itemView.findViewById(R.id.med_interval);
            medIntakeType= (TextView) itemView.findViewById(R.id.med_intake_mode);
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
