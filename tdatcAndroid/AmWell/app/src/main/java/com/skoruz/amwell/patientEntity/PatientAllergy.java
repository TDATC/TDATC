package com.skoruz.amwell.patientEntity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SKoruz-Keerthi on 18-09-2015.
 */
public class PatientAllergy implements Parcelable{

    private int id;
    private String allergyName;
    private String allergyType;

    public PatientAllergy() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAllergyName() {
        return allergyName;
    }

    public void setAllergyName(String allergyName) {
        this.allergyName = allergyName;
    }

    public String getAllergyType() {
        return allergyType;
    }

    public void setAllergyType(String allergyType) {
        this.allergyType = allergyType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(allergyName);
        dest.writeString(allergyType);
    }

    public PatientAllergy(Parcel source){
        id=source.readInt();
        allergyName=source.readString();
        allergyType=source.readString();
    }

    public static final Parcelable.Creator<PatientAllergy> CREATOR=new Parcelable.Creator<PatientAllergy>(){

        @Override
        public PatientAllergy createFromParcel(Parcel source) {
            return new PatientAllergy(source);
        }

        @Override
        public PatientAllergy[] newArray(int size) {
            return new PatientAllergy[size];
        }
    };
}
