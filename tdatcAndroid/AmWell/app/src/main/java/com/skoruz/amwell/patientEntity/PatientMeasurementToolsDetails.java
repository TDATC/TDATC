package com.skoruz.amwell.patientEntity;

/**
 * Created by Skoruz-Ashish on 9/4/2015.
 */
public class PatientMeasurementToolsDetails {

    private int id;
    private int measurementToolId;
    private int patientId ;
    private String measurementToolsValue;
    private String dateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeasurementToolId() {
        return measurementToolId;
    }

    public void setMeasurementToolId(int measurementToolId) {
        this.measurementToolId = measurementToolId;
    }

    public String getMeasurementToolsValue() {
        return measurementToolsValue;
    }

    public void setMeasurementToolsValue(String measurementToolsValue) {
        this.measurementToolsValue = measurementToolsValue;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
