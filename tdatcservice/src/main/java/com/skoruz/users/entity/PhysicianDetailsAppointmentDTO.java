package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="physician_details")
@PrimaryKeyJoinColumn(name="physician_id")
public class PhysicianDetailsAppointmentDTO  implements  Serializable  {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public PhysicianDetailsAppointmentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

	public PhysicianDetailsAppointmentDTO(User user, String branch,
			Integer specilization, String facebookLink, String bloggerLink,
			String linkedInLink, String flikkerLink, boolean notificationAlert,
			boolean patientRequestAlert, Integer location,
			Integer qualification, String description, Integer affiliation) {
		super();
		this.user = user;
		Branch = branch;
		this.specilization = specilization;
		this.facebookLink = facebookLink;
		this.bloggerLink = bloggerLink;
		this.linkedInLink = linkedInLink;
		this.flikkerLink = flikkerLink;
		this.notificationAlert = notificationAlert;
		this.patientRequestAlert = patientRequestAlert;
		this.location = location;
		this.qualification = qualification;
		this.description = description;
		this.affiliation = affiliation;
	}




	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User  user;

	@Column(name="branch")
	private String Branch;

	@Column(name="specialization_id")
	private Integer specilization;

	@Column(name="facebook_Link")
	private String facebookLink;

	@Column(name="blogger_link")
	private String bloggerLink;

	@Column(name="linkedIn_link")
	private String linkedInLink;

	@Column(name="flikker_link")
	private String flikkerLink;

	@Column(name="notification_alert")
	private boolean notificationAlert;

	@Column(name="patient_request_alert")
	private boolean patientRequestAlert;
	 
	 @Column(name="location_id")
	 private Integer location;

	@Column(name="qualification_id")
	private Integer qualification;

	private String description;

	@Column(name="affiliation_id")
	private Integer affiliation;

	public String getBranch() {
		return Branch;
	}

	public void setBranch(String branch) {
		Branch = branch;
	}

	public Integer getSpecilization() {
		return specilization;
	}

	public void setSpecilization(Integer specilization) {
		this.specilization = specilization;
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

	public Integer getLocation() {
		return location;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

	public Integer getQualification() {
		return qualification;
	}

	public void setQualification(Integer qualification) {
		this.qualification = qualification;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(Integer affiliation) {
		this.affiliation = affiliation;
	}


	
}



