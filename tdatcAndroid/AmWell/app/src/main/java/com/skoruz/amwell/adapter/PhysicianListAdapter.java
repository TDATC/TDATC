package com.skoruz.amwell.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.skoruz.amwell.R;
import com.skoruz.amwell.physicianEntity.Clinic;
import com.skoruz.amwell.physicianEntity.PhysicianDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SKoruz-Keerthi on 01-10-2015.
 */
public class PhysicianListAdapter extends BaseAdapter implements View.OnClickListener{
    private Activity activity;
    private LayoutInflater inflater;
    ArrayList<PhysicianDetails> physicianDetailsList;

    public PhysicianListAdapter(Activity activity,ArrayList<PhysicianDetails> physicianDetailsList){
        this.physicianDetailsList=physicianDetailsList;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return physicianDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return physicianDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            inflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.item_search_result_list,parent,false);

            viewHolder=new ViewHolder();
            viewHolder.doctorLayout= (LinearLayout) convertView.findViewById(R.id.doctor_layout);
            viewHolder.docName= (TextView) convertView.findViewById(R.id.doctor_name);
            viewHolder.availability= (TextView) convertView.findViewById(R.id.available_today);
            viewHolder.doc_spec= (TextView) convertView.findViewById(R.id.doctor_speciality);
            viewHolder.clinic_name= (TextView) convertView.findViewById(R.id.clinic_name);
            viewHolder.clinic_locality= (TextView) convertView.findViewById(R.id.clinic_locality);
            viewHolder.doc_exp= (TextView) convertView.findViewById(R.id.doctor_experience);
            viewHolder.doc_fees= (TextView) convertView.findViewById(R.id.doctor_fees);
            viewHolder.book_appoint= (Button) convertView.findViewById(R.id.book_appointment);
            viewHolder.add_prefer= (Button) convertView.findViewById(R.id.add_preference);

            viewHolder.doctorLayout.setOnClickListener(this);
            viewHolder.book_appoint.setOnClickListener(this);
            viewHolder.add_prefer.setOnClickListener(this);

            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if(physicianDetailsList.size()>0) {
            PhysicianDetails physicianDetails = physicianDetailsList.get(position);
            if (physicianDetails != null) {
                viewHolder.docName.setText("Dr " + physicianDetails.getUser().getFirstName() + " " + physicianDetails.getUser().getLastName());
                if(physicianDetails.getSpecializations()!=null) {
                    viewHolder.doc_spec.setText(physicianDetails.getSpecializations().getSpecializations());
                }
                if(physicianDetails.getClinic().size()>0) {
                    if (physicianDetails.getClinic() != null) {
                        List<Clinic> clinics = new ArrayList<>(physicianDetails.getClinic());
                        viewHolder.clinic_name.setText(clinics.get(0).getClinicName());
                        viewHolder.doc_fees.setText(this.activity.getResources().getString(R.string.Rs) + String.valueOf(clinics.get(0).getConsultationFees()));
                        viewHolder.clinic_locality.setText(clinics.get(0).getCity());
                    }
                }
                viewHolder.doc_exp.setText(String.valueOf(physicianDetails.getExperienceInYear()) + " yrs exp.");
            }
        }

        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.book_appointment:
                Toast.makeText(activity,"Appointment Button",Toast.LENGTH_SHORT).show();
                break;
            case R.id.doctor_layout:
                Toast.makeText(activity,"Layout",Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_preference:
                Toast.makeText(activity, "Preference Button", Toast.LENGTH_SHORT).show();
                new MaterialDialog.Builder(activity)
                        .title("Title")
                        .content("Content")
                        .positiveText("ok")
                        .negativeText("cancel")
                        .show();
            default:
                break;
        }
    }

    static class ViewHolder{
        LinearLayout doctorLayout;
        TextView docName,availability,doc_spec,clinic_name,clinic_locality,doc_exp,doc_fees;
        Button book_appoint,add_prefer;
    }

}
