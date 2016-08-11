package com.skoruz.amwell.patientEntity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Skoruz-Ashish on 9/5/2015.
 */
public class MeasurementToolOldData implements Parcelable{

    private int id;
    private int toolId;
    private int patientId;
    private String toolName;
    private String uploadedDate;
    private String uploadedValue;

    public MeasurementToolOldData(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getToolId() {
        return toolId;
    }

    public void setToolId(int toolId) {
        this.toolId = toolId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(String uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public String getUploadedValue() {
        return uploadedValue;
    }

    public void setUploadedValue(String uploadedValue) {
        this.uploadedValue = uploadedValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uploadedDate);
        dest.writeString(uploadedValue);
    }

    public MeasurementToolOldData(Parcel source){
        uploadedDate=source.readString();
        uploadedValue=source.readString();
    }

    public static final Parcelable.Creator<MeasurementToolOldData> CREATOR=new Parcelable.Creator<MeasurementToolOldData>(){

        @Override
        public MeasurementToolOldData createFromParcel(Parcel source) {
            return new MeasurementToolOldData(source);
        }

        @Override
        public MeasurementToolOldData[] newArray(int size) {
            return new MeasurementToolOldData[size];
        }
    };
}
