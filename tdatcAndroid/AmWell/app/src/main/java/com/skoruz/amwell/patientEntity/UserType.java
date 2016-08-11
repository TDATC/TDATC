package com.skoruz.amwell.patientEntity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by SKoruz-Keerthi on 11-12-2015.
 */
public class UserType implements Parcelable{

    public static final Parcelable.Creator<UserType> CREATOR=new Creator<UserType>() {
        @Override
        public UserType createFromParcel(Parcel source) {
            return new UserType(source);
        }

        @Override
        public UserType[] newArray(int size) {
            return new UserType[0];
        }
    };

    private int usertype_id;
    private String userType;

    public UserType(int usertype_id, String userType) {
        this.usertype_id = usertype_id;
        this.userType = userType;
    }

    public int getUsertype_id() {
        return usertype_id;
    }

    public void setUsertype_id(int usertype_id) {
        this.usertype_id = usertype_id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public UserType(){
        this.usertype_id=0;
        this.userType="";
    }

    protected UserType(Parcel parcel){
        this.usertype_id=0;
        this.userType="";
        this.usertype_id=parcel.readInt();
        this.userType=parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.usertype_id);
        dest.writeString(this.userType);
    }
}
