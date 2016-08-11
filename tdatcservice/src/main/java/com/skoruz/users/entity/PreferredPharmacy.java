package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="patient_pharmacy_preferences")
public class PreferredPharmacy   implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public PreferredPharmacy() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public PreferredPharmacy(int id, int patientId, int pharmacyBranchId) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.pharmacyBranchId = pharmacyBranchId;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="patient_id")
	private int patientId;
	
	@Column(name="pharmacy_branch_id")
	private int pharmacyBranchId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public int getPharmacyBranchId() {
		return pharmacyBranchId;
	}

	public void setPharmacyBranchId(int pharmacyBranchId) {
		this.pharmacyBranchId = pharmacyBranchId;
	}
	

}
