package com.skoruz.amwell.patientEntity;

import java.io.Serializable;

/**
 * Created by Skoruz-Ashish on 8/25/2015.
 */
public class PatientDetails implements Serializable{

    private int patient_id;
    private User user;
    private boolean notificationAlert;
    private boolean isPrimarySubscriber;
    private boolean assesmentCompletionAlert;
    private boolean assesmentNotificationAlert;
    private Integer totalAmountPaid;
    private int healthPlanId;
    private String bloodType;

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isNotificationAlert() {
        return notificationAlert;
    }

    public void setNotificationAlert(boolean notificationAlert) {
        this.notificationAlert = notificationAlert;
    }

    public boolean isPrimarySubscriber() {
        return isPrimarySubscriber;
    }

    public void setIsPrimarySubscriber(boolean isPrimarySubscriber) {
        this.isPrimarySubscriber = isPrimarySubscriber;
    }

    public boolean isAssesmentCompletionAlert() {
        return assesmentCompletionAlert;
    }

    public void setAssesmentCompletionAlert(boolean assesmentCompletionAlert) {
        this.assesmentCompletionAlert = assesmentCompletionAlert;
    }

    public boolean isAssesmentNotificationAlert() {
        return assesmentNotificationAlert;
    }

    public void setAssesmentNotificationAlert(boolean assesmentNotificationAlert) {
        this.assesmentNotificationAlert = assesmentNotificationAlert;
    }

    public Integer getTotalAmountPaid() {
        return totalAmountPaid;
    }

    public void setTotalAmountPaid(Integer totalAmountPaid) {
        this.totalAmountPaid = totalAmountPaid;
    }

    public int getHealthPlanId() {
        return healthPlanId;
    }

    public void setHealthPlanId(int healthPlanId) {
        this.healthPlanId = healthPlanId;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
