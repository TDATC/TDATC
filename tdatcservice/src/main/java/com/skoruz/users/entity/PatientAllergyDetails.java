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
@Table(name="patient_allergy_details")
public class PatientAllergyDetails   implements  Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public PatientAllergyDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public PatientAllergyDetails(int id, Allergies allergies, String status,
			String insertedDate, String severe, int patientId) {
		super();
		this.id = id;
		this.allergies = allergies;
		this.status = status;
		this.insertedDate = insertedDate;
		this.severe = severe;
		this.patientId = patientId;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private  int  id;
	
	@OneToOne(fetch= FetchType.EAGER)
	@JoinColumn(name="allergy_id")
	private Allergies allergies; 

	@Column(name=" status")
	private  String status;
	
	
	@Column(name="inserted_date")
	private  String insertedDate;
	
	@Column(name="severe")
	private String  severe;
	
	@Column(name="patient_id") 
	private  int patientId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Allergies getAllergies() {
		return allergies;
	}

	public void setAllergies(Allergies allergies) {
		this.allergies = allergies;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(String insertedDate) {
		this.insertedDate = insertedDate;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getSevere() {
		return severe;
	}

	public void setSevere(String severe) {
		this.severe = severe;
	}

	
}
