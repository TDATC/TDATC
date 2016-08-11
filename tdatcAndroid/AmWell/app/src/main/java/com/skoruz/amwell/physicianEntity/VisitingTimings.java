package com.skoruz.amwell.physicianEntity;

import com.skoruz.amwell.patientEntity.VisibilityTimingId;

/**
 * Created by SKoruz-Keerthi on 02-11-2015.
 */
public class VisitingTimings {

    private VisibilityTimingId id;
    private int doctorId;
    private String session1_end_time;
    private String session1_start_time;
    private String session2_end_time;
    private String session2_start_time;

    public VisibilityTimingId getId() {
        return id;
    }

    public void setId(VisibilityTimingId id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getSession1_end_time() {
        return session1_end_time;
    }

    public void setSession1_end_time(String session1_end_time) {
        this.session1_end_time = session1_end_time;
    }

    public String getSession1_start_time() {
        return session1_start_time;
    }

    public void setSession1_start_time(String session1_start_time) {
        this.session1_start_time = session1_start_time;
    }

    public String getSession2_end_time() {
        return session2_end_time;
    }

    public void setSession2_end_time(String session2_end_time) {
        this.session2_end_time = session2_end_time;
    }

    public String getSession2_start_time() {
        return session2_start_time;
    }

    public void setSession2_start_time(String session2_start_time) {
        this.session2_start_time = session2_start_time;
    }
}
