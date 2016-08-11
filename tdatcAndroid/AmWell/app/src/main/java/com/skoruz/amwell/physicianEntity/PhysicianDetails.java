package com.skoruz.amwell.physicianEntity;

import android.os.Parcel;
import android.os.Parcelable;

import com.skoruz.amwell.patientEntity.Locality;
import com.skoruz.amwell.patientEntity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by SKoruz-Keerthi on 24-09-2015.
 */
public class PhysicianDetails implements Parcelable{

    public static final Parcelable.Creator<PhysicianDetails> CREATOR=new Parcelable.Creator<PhysicianDetails>(){

        @Override
        public PhysicianDetails createFromParcel(Parcel source) {
            return new PhysicianDetails(source);
        }

        @Override
        public PhysicianDetails[] newArray(int n) {
            return new PhysicianDetails[n];
        }
    };

    public PhysicianDetails(){}

    private int physician_id;
    private String branch;
    private User user;
    private Specialization specializations;
    private String facebookLink;
    private String bloggerLink;
    private String linkedInLink;
    private String flikkerLink;
    private boolean notificationAlert;
    private boolean patientRequestAlert;
    private Qualification qualification;
    private String description;
    private Affliation affliation;
    private Locality location;
    private List<Clinic> clinic=new ArrayList<>();
    private String registrationNo;
    private String registrationDate;
    private int experienceInYear;

   /* public PhysicianDetails() {
        this.physician_id=0;
        this.branch="";
        this.user=new User();
        this.specializations=new Specialization();
        this.facebookLink="";
        this.bloggerLink="";
        this.linkedInLink="";
        this.flikkerLink="";
        this.notificationAlert=false;
        this.patientRequestAlert=false;
        this.qualification=new Qualification();
        this.description="";
        this.affliation=new Affliation();
        this.location=new Locality();
        this.clinic=new ArrayList<>();
        this.registrationNo="";
        this.registrationDate="";
        this.experienceInYear=0;
    }*/

    protected PhysicianDetails(Parcel parcel){
        /*this.physician_id=0;
        this.branch="";
        this.user=new User();
        this.specializations=new Specialization();
        this.facebookLink="";
        this.bloggerLink="";
        this.linkedInLink="";
        this.flikkerLink="";
        this.notificationAlert=false;
        this.patientRequestAlert=false;
        this.qualification=new Qualification();
        this.description="";
        this.affliation=new Affliation();
        this.location=new Locality();
        this.clinic=new ArrayList<>();
        this.registrationNo="";
        this.registrationDate="";
        this.experienceInYear=0;*/
        this.physician_id=parcel.readInt();
        this.branch=parcel.readString();
        this.user= parcel.readParcelable(User.class.getClassLoader());
        this.specializations= parcel.readParcelable(Specialization.class.getClassLoader());
        this.facebookLink=parcel.readString();
        this.bloggerLink=parcel.readString();
        this.linkedInLink=parcel.readString();
        this.flikkerLink=parcel.readString();
        this.notificationAlert=(parcel.readInt()!=0);
        this.patientRequestAlert=(parcel.readInt()!=0);
        this.qualification=parcel.readParcelable(Qualification.class.getClassLoader());
        this.description=parcel.readString();
        this.affliation= parcel.readParcelable(Affliation.class.getClassLoader());
        this.location=parcel.readParcelable(Locality.class.getClassLoader());
        this.clinic=new ArrayList<Clinic>();
        parcel.readTypedList(this.clinic, Clinic.CREATOR);
        this.registrationNo=parcel.readString();
        this.registrationDate=parcel.readString();
        this.experienceInYear=parcel.readInt();
    }

    public int getPhysician_id() {
        return physician_id;
    }

    public void setPhysician_id(int physician_id) {
        this.physician_id = physician_id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Specialization getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Specialization specializations) {
        this.specializations = specializations;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getBloggerLink() {
        return bloggerLink;
    }

    public void setBloggerLink(String bloggerLink) {
        this.bloggerLink = bloggerLink;
    }

    public String getLinkedInLink() {
        return linkedInLink;
    }

    public void setLinkedInLink(String linkedInLink) {
        this.linkedInLink = linkedInLink;
    }

    public String getFlikkerLink() {
        return flikkerLink;
    }

    public void setFlikkerLink(String flikkerLink) {
        this.flikkerLink = flikkerLink;
    }

    public boolean isNotificationAlert() {
        return notificationAlert;
    }

    public void setNotificationAlert(boolean notificationAlert) {
        this.notificationAlert = notificationAlert;
    }

    public boolean isPatientRequestAlert() {
        return patientRequestAlert;
    }

    public void setPatientRequestAlert(boolean patientRequestAlert) {
        this.patientRequestAlert = patientRequestAlert;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Affliation getAffliation() {
        return affliation;
    }

    public void setAffliation(Affliation affliation) {
        this.affliation = affliation;
    }

    public Locality getLocation() {
        return location;
    }

    public void setLocation(Locality location) {
        this.location = location;
    }

    public List<Clinic> getClinic() {
        return clinic;
    }

    public void setClinic(List<Clinic> clinic) {
        this.clinic = clinic;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getExperienceInYear() {
        return experienceInYear;
    }

    public void setExperienceInYear(int experienceInYear) {
        this.experienceInYear = experienceInYear;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.physician_id);
        parcel.writeString(this.branch);
        parcel.writeParcelable(this.user, n);
        parcel.writeParcelable(this.specializations,n);
        parcel.writeString(this.facebookLink);
        parcel.writeString(this.bloggerLink);
        parcel.writeString(this.linkedInLink);
        parcel.writeString(this.flikkerLink);
        parcel.writeInt(this.notificationAlert ? 1 : 0);
        parcel.writeInt(this.patientRequestAlert ? 1 : 0);
        parcel.writeParcelable(this.qualification,n);
        parcel.writeString(this.description);
        parcel.writeParcelable(this.affliation,n);
        parcel.writeParcelable(this.location,n);
        parcel.writeTypedList(this.clinic);
        parcel.writeString(this.registrationNo);
        parcel.writeString(this.registrationDate);
        parcel.writeInt(this.experienceInYear);
    }
}
