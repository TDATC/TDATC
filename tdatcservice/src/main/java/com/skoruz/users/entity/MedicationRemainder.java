package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="medication_remainder")
public class MedicationRemainder  implements  Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public MedicationRemainder() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public MedicationRemainder(int id, PatientDetails patientDetails,
			PatientMedicationDetails medicationDetails, Boolean status,
			String lastUpdatedDate) {
		super();
		this.id = id;
		this.patientDetails = patientDetails;
		this.medicationDetails = medicationDetails;
		this.status = status;
		this.lastUpdatedDate = lastUpdatedDate;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="patient_id")
	private PatientDetails patientDetails;
	//private int patientId;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="patient_medication_id")
	private PatientMedicationDetails medicationDetails;
	//private int medicationId;
	
	private Boolean status;
	
	@Column(name="last_updated_date")
	private String lastUpdatedDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PatientDetails getPatientDetails() {
		return patientDetails;
	}

	public void setPatientDetails(PatientDetails patientDetails) {
		this.patientDetails = patientDetails;
	}

	public PatientMedicationDetails getMedicationDetails() {
		return medicationDetails;
	}

	public void setMedicationDetails(PatientMedicationDetails medicationDetails) {
		this.medicationDetails = medicationDetails;
	}


	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	
	

}
