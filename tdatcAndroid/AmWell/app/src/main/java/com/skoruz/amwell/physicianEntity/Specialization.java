package com.skoruz.amwell.physicianEntity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SKoruz-Keerthi on 24-09-2015.
 */
public class Specialization implements Parcelable{

    public static final Parcelable.Creator<Specialization> CREATOR= new Creator<Specialization>() {
        @Override
        public Specialization createFromParcel(Parcel source) {
            return new Specialization(source);
        }

        @Override
        public Specialization[] newArray(int size) {
            return new Specialization[0];
        }
    };

    public Specialization(){
        this.specialization_id=0;
        this.specializations="";
    }

    public Specialization(Parcel parcel){
        this.specialization_id=0;
        this.specializations="";
        this.specialization_id=parcel.readInt();
        this.specializations=parcel.readString();
    }

    private int specialization_id;
    private String specializations;

    public int getSpecialization_id() {
        return specialization_id;
    }

    public void setSpecialization_id(int specialization_id) {
        this.specialization_id = specialization_id;
    }

    public String getSpecializations() {
        return specializations;
    }

    public void setSpecializations(String specializations) {
        this.specializations = specializations;
    }

    public String toString()
    {
        return specializations;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.specialization_id);
        parcel.writeString(this.specializations);
    }
}
