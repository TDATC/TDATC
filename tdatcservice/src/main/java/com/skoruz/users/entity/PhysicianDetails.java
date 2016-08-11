package com.skoruz.users.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity
@Table(name = "physician_details")
public class PhysicianDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhysicianDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PhysicianDetails(String branch, User user,
			Specialization specializations, String facebookLink,
			String bloggerLink, String linkedInLink, String flikkerLink,
			boolean notificationAlert, boolean patientRequestAlert,
			Qualification qualification, String description,
			Affliation affliation, Location location, List<Clinic> clinic,
			String registrationNo, String registrationDate, int experienceInYear) {
		super();
		this.branch = branch;
		this.user = user;
		this.specializations = specializations;
		this.facebookLink = facebookLink;
		this.bloggerLink = bloggerLink;
		this.linkedInLink = linkedInLink;
		this.flikkerLink = flikkerLink;
		this.notificationAlert = notificationAlert;
		this.patientRequestAlert = patientRequestAlert;
		this.qualification = qualification;
		this.description = description;
		this.affliation = affliation;
		this.location = location;
		this.clinic = clinic;
		this.registrationNo = registrationNo;
		this.registrationDate = registrationDate;
		this.experienceInYear = experienceInYear;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "physician_id")
	private int physician_id;

	private String branch;

	@OneToOne(fetch = FetchType.EAGER ,cascade  = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "specialization_id")
	private Specialization specializations;

	@Column(name = "facebook_Link")
	private String facebookLink;

	@Column(name = "blogger_link")
	private String bloggerLink;

	@Column(name = "linkedIn_link")
	private String linkedInLink;

	@Column(name = "flikker_link")
	private String flikkerLink;

	@Column(name = "notification_alert")
	private boolean notificationAlert;

	@Column(name = "patient_request_alert")
	private boolean patientRequestAlert;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "qualification_id")
	private Qualification qualification;

	private String description;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "affiliation_id")
	private Affliation affliation;
	// private String affiliation;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "location_id")
	private Location location;

	/*
	 * @OneToMany(mappedBy="physicianDetails", cascade=CascadeType.ALL,
	 * fetch=FetchType.EAGER)
	 * 
	 * @JsonManagedReference private Set<Clinic> clinic = new HashSet<Clinic>();
	 */

	/*
	 * @OneToMany(cascade = { javax.persistence.CascadeType.ALL
	 * },fetch=FetchType.EAGER)
	 * 
	 * @JoinColumn(name = "physicianDetails")
	 */

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "phyId", fetch = FetchType.EAGER)
	private List<Clinic> clinic = new ArrayList<Clinic>();

	/*
	 * @OneToOne(fetch=FetchType.EAGER)
	 * 
	 * @JoinColumn(name="clinic_id") private Clinic clinic;
	 */

	/*
	 * @OneToMany(mappedBy="physicianDetails",fetch=FetchType.EAGER) private
	 * Set<Clinic> clinic = new HashSet<Clinic>();
	 */

	@Column(name = "registration_no")
	private String registrationNo;

	@Column(name = "registration_date")
	private String registrationDate;

	@Column(name = "experience_in_year")
	private int experienceInYear;

	/*
	 * @OneToMany(fetch = FetchType.EAGER, mappedBy = "physicianDetails",
	 * cascade = CascadeType.ALL)
	 * 
	 * @JsonManagedReference private Set<PhysicianAvailability>
	 * physicianAvailabilities = new HashSet<PhysicianAvailability>();
	 */

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getPhysician_id() {
		return physician_id;
	}

	public void setPhysician_id(int physician_id) {
		this.physician_id = physician_id;
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

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Specialization getSpecializations() {
		return specializations;
	}

	public void setSpecializations(Specialization specializations) {
		this.specializations = specializations;
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<Clinic> getClinic() {
		return clinic;
	}

	public void setClinic(List<Clinic> clinic) {
		this.clinic = clinic;
	}

	/*
	 * public Clinic getClinic() { return clinic; } public void setClinic(Clinic
	 * clinic) { this.clinic = clinic; }
	 */

	/*
	 * public Set<PhysicianAvailability> getPhysicianAvailabilities() { return
	 * physicianAvailabilities; } public void setPhysicianAvailabilities(
	 * Set<PhysicianAvailability> physicianAvailabilities) {
	 * this.physicianAvailabilities = physicianAvailabilities; }
	 */
}