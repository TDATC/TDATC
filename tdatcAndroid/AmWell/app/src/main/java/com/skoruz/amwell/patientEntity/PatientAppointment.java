package com.skoruz.amwell.patientEntity;

import com.skoruz.amwell.constants.AppointmentStatus;

import java.util.Date;

/**
 * Created by SKoruz-Keerthi on 13-10-2015.
 */
public class PatientAppointment {
    private int id;
    private int patientId;
    private int physicianId;
    private String timings;
    private String date;
    private String appointmentDescription;
    private AppointmentStatus status;
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

    public int getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(int physicianId) {
        this.physicianId = physicianId;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public int getReadUnread() {
        return readUnread;
    }

    public void setReadUnread(int readUnread) {
        this.readUnread = readUnread;
    }
}
