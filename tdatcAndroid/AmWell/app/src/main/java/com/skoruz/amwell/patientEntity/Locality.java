package com.skoruz.amwell.patientEntity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SKoruz-Keerthi on 04-11-2015.
 */
public class Locality implements Parcelable{

    public static final Parcelable.Creator<Locality> CREATOR=new Creator<Locality>() {
        @Override
        public Locality createFromParcel(Parcel source) {
            return new Locality(source);
        }

        @Override
        public Locality[] newArray(int size) {
            return new Locality[0];
        }
    };

    public Locality(){
        this.location_id=0;
        this.locationName="";
        this.city=new City();
    }

    public Locality(Parcel parcel){
        this.location_id=0;
        this.locationName="";
        this.city=new City();
        this.location_id=parcel.readInt();
        this.locationName=parcel.readString();
        this.city=parcel.readParcelable(City.class.getClassLoader());
    }

    private int location_id;
    private String locationName;
    private City city;

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.location_id);
        parcel.writeString(this.locationName);
        parcel.writeParcelable(this.city,flags);
    }
}
