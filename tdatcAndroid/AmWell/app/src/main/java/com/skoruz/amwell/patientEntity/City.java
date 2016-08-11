package com.skoruz.amwell.patientEntity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SKoruz-Keerthi on 04-11-2015.
 */
public class City implements Parcelable{

    public static final Parcelable.Creator<City> CREATOR=new Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[0];
        }
    };

    private int cityId;
    private String cityName;
    private Country country;

    public City(){}

    public City(Parcel parcel){
       /* this.cityId=0;
        this.cityName="";*/
        this.cityId=parcel.readInt();
        this.cityName=parcel.readString();
        this.country=parcel.readParcelable(Country.class.getClassLoader());
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cityId);
        dest.writeString(this.cityName);
        dest.writeParcelable(this.country,flags);
    }
}
