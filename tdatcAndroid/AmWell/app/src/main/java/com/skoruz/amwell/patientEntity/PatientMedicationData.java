package com.skoruz.amwell.patientEntity;

/**
 * Created by SKoruz-Keerthi on 14-09-2015.
 */
public class PatientMedicationData {

    private int id;
    private int patientId;
    private Medication medicine;
    private String instruction;
    private String beforeAfterFood;
    private String insertedDate;
    private String fromDate;
    private String toDate;
    private int readUnread;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Medication getMedicine() {
        return medicine;
    }

    public void setMedicine(Medication medicine) {
        this.medicine = medicine;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getBeforeAfterFood() {
        return beforeAfterFood;
    }

    public void setBeforeAfterFood(String beforeAfterFood) {
        this.beforeAfterFood = beforeAfterFood;
    }

    public String getInsertedDate() {
        return insertedDate;
    }

    public void setInsertedDate(String insertedDate) {
        this.insertedDate = insertedDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public int getReadUnread() {
        return readUnread;
    }

    public void setReadUnread(int readUnread) {
        this.readUnread = readUnread;
    }
}
