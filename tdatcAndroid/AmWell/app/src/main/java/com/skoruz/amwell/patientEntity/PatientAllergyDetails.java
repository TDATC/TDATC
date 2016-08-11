package com.skoruz.amwell.patientEntity;

/**
 * Created by SKoruz-Keerthi on 18-09-2015.
 */
public class PatientAllergyDetails {

    private  int  id;
    private PatientAllergy allergies;
    private  String status;
    private  String insertedDate;
    private String  severe;
    private  int patientId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PatientAllergy getAllergies() {
        return allergies;
    }

    public void setAllergies(PatientAllergy allergies) {
        this.allergies = allergies;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInsertedDate() {
        return insertedDate;
    }

    public void setInsertedDate(String insertedDate) {
        this.insertedDate = insertedDate;
    }

    public String getSevere() {
        return severe;
    }

    public void setSevere(String severe) {
        this.severe = severe;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
