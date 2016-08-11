package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="patient_hospital_preferences")
public class PreferredHospital  implements  Serializable {
	
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PreferredHospital() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	

	public PreferredHospital(int id, int patientId, int hospitalBranchId) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.hospitalBranchId = hospitalBranchId;
	}








	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="patient_id")
	private int patientId;
	
	@Column(name="hospital_branch_id")
	private int hospitalBranchId;

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

	public int getHospitalBranchId() {
		return hospitalBranchId;
	}

	public void setHospitalBranchId(int hospitalBranchId) {
		this.hospitalBranchId = hospitalBranchId;
	}
	
	
	

}
