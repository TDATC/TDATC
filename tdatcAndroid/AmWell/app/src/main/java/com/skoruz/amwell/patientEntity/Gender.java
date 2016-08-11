package com.skoruz.amwell.patientEntity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by SKoruz-Keerthi on 11-12-2015.
 */
public class Gender implements Parcelable{

    public static final Parcelable.Creator<Gender> CREATOR=new Creator<Gender>() {
        @Override
        public Gender createFromParcel(Parcel source) {
            return new Gender(source);
        }

        @Override
        public Gender[] newArray(int size) {
            return new Gender[0];
        }
    };

    private int gender_id;
    private  String gender;

    public Gender(int gender_id, String gender) {
        this.gender_id = gender_id;
        this.gender = gender;
    }

    public int getGender_id() {
        return gender_id;
    }

    public void setGender_id(int gender_id) {
        this.gender_id = gender_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Gender(){
        this.gender_id=0;
        this.gender="";
    }

    protected Gender(Parcel parcel){
        this.gender_id=0;
        this.gender="";
        this.gender_id=parcel.readInt();
        this.gender=parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.gender_id);
        dest.writeString(this.gender);
    }
}
