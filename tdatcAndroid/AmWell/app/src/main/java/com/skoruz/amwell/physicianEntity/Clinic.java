package com.skoruz.amwell.physicianEntity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by SKoruz-Keerthi on 24-09-2015.
 */
public class Clinic implements Parcelable{

    public static final Parcelable.Creator<Clinic> CREATOR=new Creator<Clinic>() {
        @Override
        public Clinic createFromParcel(Parcel source) {
            return new Clinic(source);
        }

        @Override
        public Clinic[] newArray(int size) {
            return new Clinic[0];
        }
    };

    public Clinic(){
        this.clinic_id=0;
        this.clinicName="";
        this.city="";
        this.locality="";
        this.contactNumber="";
        this.streetAddress="";
        this.specialization="";
        this.consultationFees=0;
        this.phyId=0;
        this.appointment_duration=0;
        this.clinicVisitingTimings=new ArrayList<>();
    }

    public Clinic(Parcel parcel){
        this.clinic_id=0;
        this.clinicName="";
        this.city="";
        this.locality="";
        this.contactNumber="";
        this.streetAddress="";
        this.specialization="";
        this.consultationFees=0;
        this.phyId=0;
        this.appointment_duration=0;
        this.clinicVisitingTimings=new ArrayList<>();
        this.clinic_id=parcel.readInt();
        this.clinicName=parcel.readString();
        this.city=parcel.readString();
        this.locality=parcel.readString();
        this.contactNumber=parcel.readString();
        this.streetAddress=parcel.readString();
        this.specialization=parcel.readString();
        this.consultationFees=parcel.readInt();
        this.phyId=parcel.readInt();
        this.appointment_duration=parcel.readInt();
        this.clinicVisitingTimings=new ArrayList<>();
        if (parcel.readByte()==1){
            parcel.readList(this.clinicVisitingTimings,VisitingTimings.class.getClassLoader());
        }
    }

    private int clinic_id;
    private String clinicName;
    private String city;
    private String locality;
    private String contactNumber;
    private String streetAddress;
    private String specialization;
    private int consultationFees;
    private int phyId;
    private int appointment_duration;
    private ArrayList<VisitingTimings> clinicVisitingTimings;

    public int getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(int clinic_id) {
        this.clinic_id = clinic_id;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public ArrayList<VisitingTimings> getClinicVisitingTimings() {
        return clinicVisitingTimings;
    }

    public void setClinicVisitingTimings(ArrayList<VisitingTimings> clinicVisitingTimings) {
        this.clinicVisitingTimings = clinicVisitingTimings;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getConsultationFees() {
        return consultationFees;
    }

    public void setConsultationFees(int consultationFees) {
        this.consultationFees = consultationFees;
    }

    public int getPhyId() {
        return phyId;
    }

    public void setPhyId(int phyId) {
        this.phyId = phyId;
    }

    public int getAppointment_duration() {
        return appointment_duration;
    }

    public void setAppointment_duration(int appointment_duration) {
        this.appointment_duration = appointment_duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        this.clinic_id=0;
        this.clinicName="";
        this.city="";
        this.locality="";
        this.contactNumber="";
        this.streetAddress="";
        this.specialization="";
        this.consultationFees=0;
        this.phyId=0;
        this.appointment_duration=0;
        this.clinicVisitingTimings=new ArrayList<>();

        parcel.writeInt(this.clinic_id);
        parcel.writeString(this.clinicName);
        parcel.writeString(this.city);
        parcel.writeString(this.locality);
        parcel.writeString(this.contactNumber);
        parcel.writeString(this.streetAddress);
        parcel.writeString(this.streetAddress);
        parcel.writeString(this.specialization);
        parcel.writeInt(this.consultationFees);
        parcel.writeInt(this.phyId);
        parcel.writeInt(this.appointment_duration);
        if (this.clinicVisitingTimings!=null){
            parcel.writeByte((byte) 1);
            parcel.writeList(this.clinicVisitingTimings);
        }else {
            parcel.writeByte((byte) 0);
        }
    }
}
