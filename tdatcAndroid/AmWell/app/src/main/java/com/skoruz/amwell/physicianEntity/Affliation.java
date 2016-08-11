package com.skoruz.amwell.physicianEntity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SKoruz-Keerthi on 24-09-2015.
 */
public class Affliation implements Parcelable{

    public static final Parcelable.Creator<Affliation> CREATOR=new Parcelable.Creator<Affliation>() {
        @Override
        public Affliation createFromParcel(Parcel source) {
            return new Affliation(source);
        }

        @Override
        public Affliation[] newArray(int size) {
            return new Affliation[0];
        }
    };

    public Affliation(){
        this.affiliation_id=0;
        this.universityName="";
    }

    public Affliation(Parcel parcel){
        this.affiliation_id=0;
        this.universityName="";
        this.affiliation_id=parcel.readByte();
        this.universityName=parcel.readString();
    }

    private int affiliation_id;
    private  String universityName;

    public int getAffiliation_id() {
        return affiliation_id;
    }

    public void setAffiliation_id(int affiliation_id) {
        this.affiliation_id = affiliation_id;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String toString()
    {
        return universityName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.affiliation_id);
        parcel.writeString(this.universityName);
    }
}
