package com.skoruz.amwell.physicianEntity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SKoruz-Keerthi on 24-09-2015.
 */
public class Qualification implements Parcelable{

    public static final Parcelable.Creator<Qualification> CREATOR=new Parcelable.Creator<Qualification>() {
        @Override
        public Qualification createFromParcel(Parcel source) {
            return new Qualification(source);
        }

        @Override
        public Qualification[] newArray(int size) {
            return new Qualification[0];
        }
    };

    public Qualification(){
        this.qualification_id=0;
        this.qualification="";
    }

    public Qualification(Parcel parcel){
        this.qualification_id=0;
        this.qualification="";
        this.qualification_id=parcel.readInt();
        this.qualification=parcel.readString();
    }

    private int qualification_id;
    private String qualification;

    public int getQualification_id() {
        return qualification_id;
    }

    public void setQualification_id(int qualification_id) {
        this.qualification_id = qualification_id;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String toString()
    {
        return qualification;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.qualification_id);
        parcel.writeString(this.qualification);
    }
}
