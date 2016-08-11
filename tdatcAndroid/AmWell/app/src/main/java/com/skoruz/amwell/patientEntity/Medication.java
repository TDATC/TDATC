package com.skoruz.amwell.patientEntity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SKoruz-Keerthi on 14-09-2015.
 */
public class Medication implements Parcelable{

    private int id;
    private String medicineName;
    private String chemicalName;
    private String medicineType;

    public Medication() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getChemicalName() {
        return chemicalName;
    }

    public void setChemicalName(String chemicalName) {
        this.chemicalName = chemicalName;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(medicineName);
        dest.writeString(chemicalName);
        dest.writeString(medicineType);
    }

    public Medication(Parcel source){
        id=source.readInt();
        medicineName=source.readString();
        chemicalName=source.readString();
        medicineType=source.readString();
    }

    public static final Parcelable.Creator<Medication> CREATOR=new Parcelable.Creator<Medication>(){

        @Override
        public Medication createFromParcel(Parcel source) {
            return new Medication(source);
        }

        @Override
        public Medication[] newArray(int size) {
            return new Medication[size];
        }
    };
}
