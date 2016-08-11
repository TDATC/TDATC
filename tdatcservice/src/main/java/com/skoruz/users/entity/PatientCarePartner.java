package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="patient_care_partner")
public class PatientCarePartner  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public PatientCarePartner() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public PatientCarePartner(int id, int patientCarePartnerId,
			String patientRelationship, int patientSubscriberId) {
		super();
		this.id = id;
		this.patientCarePartnerId = patientCarePartnerId;
		this.patientRelationship = patientRelationship;
		this.patientSubscriberId = patientSubscriberId;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="patient_care_partner_id")
	private int patientCarePartnerId;
	
	
	@Column(name="patient_relationship")
	private String patientRelationship;
	
	
	@Column(name="patient_subscriber_id")
	private int patientSubscriberId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPatientCarePartnerId() {
		return patientCarePartnerId;
	}
	public void setPatientCarePartnerId(int patientCarePartnerId) {
		this.patientCarePartnerId = patientCarePartnerId;
	}
	
	public String getPatientRelationship() {
		return patientRelationship;
	}
	public void setPatientRelationship(String patientRelationship) {
		this.patientRelationship = patientRelationship;
	}
	public int getPatientSubscriberId() {
		return patientSubscriberId;
	}
	public void setPatientSubscriberId(int patientSubscriberId) {
		this.patientSubscriberId = patientSubscriberId;
	}
	

}
