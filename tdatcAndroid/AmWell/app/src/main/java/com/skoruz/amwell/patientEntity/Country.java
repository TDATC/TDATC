package com.skoruz.amwell.patientEntity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by keerthi on 12/1/16.
 */
public class Country implements Parcelable{

    public static final Parcelable.Creator<Country> CREATOR=new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel source) {
            return new Country(source);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[0];
        }
    };

    private  int  id;
    private  String countrycode;
    private  String countrystate;
    private String countryName;

    public Country(){}

    public Country(Parcel parcel){
        this.id=parcel.readInt();
        this.countrycode=parcel.readString();
        this.countrystate=parcel.readString();
        this.countryName=parcel.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getCountrystate() {
        return countrystate;
    }

    public void setCountrystate(String countrystate) {
        this.countrystate = countrystate;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.countrycode);
        dest.writeString(this.countrystate);
        dest.writeString(this.countryName);
    }
}
