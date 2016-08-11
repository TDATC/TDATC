package com.skoruz.users.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "clinic")
public class Clinic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Clinic() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Clinic(int clinic_id, String clinicName, String city,
			String locality, String contactNumber, String streetAddress,
			String specialization, int consultationFees, int phyId,
			Integer appointment_duration,
			List<ClinicVisitingTiming> clinicVisitingTimings) {
		super();
		this.clinic_id = clinic_id;
		this.clinicName = clinicName;
		this.city = city;
		this.locality = locality;
		this.contactNumber = contactNumber;
		this.streetAddress = streetAddress;
		this.specialization = specialization;
		this.consultationFees = consultationFees;
		this.phyId = phyId;
		this.appointment_duration = appointment_duration;
		this.clinicVisitingTimings = clinicVisitingTimings;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int clinic_id;

	@Column(name = "clinic_name")
	private String clinicName;

	@Column(name = "city")
	private String city;

	@Column(name = "locality")
	private String locality;

	@Column(name = "contact_number")
	private String contactNumber;

	@Column(name = "street_address")
	private String streetAddress;

	private String specialization;

	@Column(name = "consultationfees")
	private int consultationFees;

	@Column(name = "phyId")
	private int phyId;

	@Column(name = "appointment_duration")
	private Integer appointment_duration;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "clinic")
	private List<ClinicVisitingTiming> clinicVisitingTimings = new ArrayList<ClinicVisitingTiming>();

	public int getClinic_id() {
		return clinic_id;
	}

	public void setClinic_id(int clinic_id) {
		this.clinic_id = clinic_id;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public int getConsultationFees() {
		return consultationFees;
	}

	public void setConsultationFees(int consultationFees) {
		this.consultationFees = consultationFees;
	}

	public int getPhyId() {
		return phyId;
	}

	public void setPhyId(int phyId) {
		this.phyId = phyId;
	}

	

	public Integer getAppointment_duration() {
		return appointment_duration;
	}

	public void setAppointment_duration(Integer appointment_duration) {
		this.appointment_duration = appointment_duration;
	}

	public List<ClinicVisitingTiming> getClinicVisitingTimings() {
		return clinicVisitingTimings;
	}

	public void setClinicVisitingTimings(
			List<ClinicVisitingTiming> clinicVisitingTimings) {
		this.clinicVisitingTimings = clinicVisitingTimings;
	}

}
