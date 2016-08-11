package com.skoruz.amwell.patientEntity;

import com.skoruz.amwell.physicianEntity.PhysicianDetails;

import java.io.Serializable;

/**
 * Created by keerthi on 28/12/15.
 */
public class PatientPhysicianMap implements Serializable {

    private  int  id;
    private PatientDetails patientId;
    private PhysicianDetails physicianId;

    public PatientPhysicianMap() {
    }

    public PatientPhysicianMap(int id, PatientDetails patientId, PhysicianDetails physicianId) {
        this.id = id;
        this.patientId = patientId;
        this.physicianId = physicianId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PatientDetails getPatientId() {
        return patientId;
    }

    public void setPatientId(PatientDetails patientId) {
        this.patientId = patientId;
    }

    public PhysicianDetails getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(PhysicianDetails physicianId) {
        this.physicianId = physicianId;
    }
}
