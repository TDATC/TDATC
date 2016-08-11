package com.skoruz.amwell.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skoruz.amwell.R;
import com.skoruz.amwell.physician.BaseEditorActivity;
import com.skoruz.amwell.physicianEntity.Clinic;
import com.skoruz.amwell.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by SKoruz-Keerthi on 26-10-2015.
 */
public class PracticeAdapter extends BaseAdapter implements View.OnClickListener {

    private ArrayList<Clinic> clinicList;
    private Activity activity;
    private LayoutInflater inflater;
    private int doctor_id;
    private int practice_id;

    public PracticeAdapter(Activity activity,ArrayList<Clinic> clinicList,int doctor_id){
        this.clinicList=clinicList;
        this.activity=activity;
        this.doctor_id=doctor_id;
    }

    @Override
    public int getCount() {
        return clinicList.size();
    }

    @Override
    public Clinic getItem(int position) {
        return clinicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PracticeViewHolder viewHolder;
        if (convertView==null){
            inflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_item_practice,parent,false);
            viewHolder=new PracticeViewHolder();

            viewHolder.mEditPracticeDetails= (Button) convertView.findViewById(R.id.list_btn_practice_edit);
            viewHolder.mEditPracticeDetails.setOnClickListener(this);
            viewHolder.mPracticeAddressLl= (LinearLayout) convertView.findViewById(R.id.list_ll_practice_address);
            viewHolder.mPracticeAddressTv= (TextView) convertView.findViewById(R.id.list_tv_practice_address);
            viewHolder.mPracticeConsultationFeeLl= (LinearLayout) convertView.findViewById(R.id.list_ll_practice_consultation_fee);
            viewHolder.mPracticeDoctorConsultationFeeTv= (TextView) convertView.findViewById(R.id.list_tv_practice_doctor_consultation_fee);
            viewHolder.mPracticeNameTv= (TextView) convertView.findViewById(R.id.list_tv_practice_name);
            viewHolder.mPracticePhotoIv= (ImageView) convertView.findViewById(R.id.list_iv_practice_image);
            viewHolder.mPracticeSpecialityLl= (LinearLayout) convertView.findViewById(R.id.list_ll_practice_speciality);
            viewHolder.mPracticeSpecialityTv= (TextView) convertView.findViewById(R.id.list_tv_practice_speciality);
            viewHolder.mPracticeStreetAddressLl= (LinearLayout) convertView.findViewById(R.id.list_ll_practice_street_address);
            viewHolder.mPracticeStreetAddressTv= (TextView) convertView.findViewById(R.id.list_tv_practice_street_address);
            viewHolder.mViewPracticeDetails= (Button) convertView.findViewById(R.id.list_btn_practice_view);
            viewHolder.mViewPracticeDetails.setOnClickListener(this);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (PracticeViewHolder) convertView.getTag();
        }
        try {

            Clinic clinic=clinicList.get(position);
            if (clinic!=null) {

                viewHolder.mPracticePhotoIv.setImageResource(R.drawable.background_default_no_practice);

                practice_id=clinic.getClinic_id();
                if (!TextUtils.isEmpty(clinic.getClinicName())) {
                    viewHolder.mPracticeNameTv.setText(clinic.getClinicName());
                }
                String address = "";
                if (!Utils.isEmptyString(clinic.getCity())) {
                    address = address + clinic.getCity();
                }
                if (!Utils.isEmptyString(clinic.getLocality())) {
                    address = address + ", "+clinic.getLocality();
                }

                if (!Utils.isEmptyString(address)) {
                    viewHolder.mPracticeAddressLl.setVisibility(View.VISIBLE);
                    viewHolder.mPracticeAddressTv.setText(address);
                }else {
                    viewHolder.mPracticeAddressLl.setVisibility(View.GONE);
                }

                String streetAddress="";
                if (!Utils.isEmptyString(clinic.getStreetAddress())) {
                    streetAddress = streetAddress + clinic.getStreetAddress() + ", ";
                }
                if (!Utils.isEmptyString(clinic.getLocality())) {
                    streetAddress = streetAddress+clinic.getLocality();
                }

                if (!Utils.isEmptyString(streetAddress)){
                    viewHolder.mPracticeStreetAddressLl.setVisibility(View.VISIBLE);
                    viewHolder.mPracticeStreetAddressTv.setText(streetAddress);
                }else {
                    viewHolder.mPracticeStreetAddressLl.setVisibility(View.GONE);
                }

                String practiceConsultationFee=String.valueOf(clinic.getConsultationFees());
                if (Utils.isEmptyString(practiceConsultationFee)){
                    viewHolder.mPracticeConsultationFeeLl.setVisibility(View.GONE);
                }else{
                    viewHolder.mPracticeConsultationFeeLl.setVisibility(View.VISIBLE);
                    if (clinic.getConsultationFees() == 0) {
                        practiceConsultationFee = "Free";
                    }
                    viewHolder.mPracticeDoctorConsultationFeeTv.setText(this.activity.getResources().getString(R.string.Rs) + practiceConsultationFee);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        viewHolder.mEditPracticeDetails.setTag(null);
        viewHolder.mEditPracticeDetails.setTag(R.string.tag_practice_position, Integer.valueOf(position));

        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.list_btn_practice_edit:
                int practiceId=clinicList.get(((Integer) v.getTag(R.string.tag_practice_position)).intValue()).getClinic_id();
                Toast.makeText(activity, "Edit", Toast.LENGTH_SHORT).show();
                BaseEditorActivity.editPractice(activity, practiceId, doctor_id);
                break;
            case R.id.list_btn_practice_view:
                Toast.makeText(activity, "View", Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
    }

    public static class PracticeViewHolder
    {
        private Button mEditPracticeDetails;
        private LinearLayout mPracticeAddressLl;
        private TextView mPracticeAddressTv;
        private LinearLayout mPracticeConsultationFeeLl;
        private TextView mPracticeDoctorConsultationFeeTv;
        private TextView mPracticeNameTv;
        private ImageView mPracticePhotoIv;
        private LinearLayout mPracticeSpecialityLl;
        private TextView mPracticeSpecialityTv;
        private LinearLayout mPracticeStreetAddressLl;
        private TextView mPracticeStreetAddressTv;
        private Button mViewPracticeDetails;
    }
}
