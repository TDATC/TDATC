package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="patientphysicianmap")
public class PatientPhysicianMap  implements  Serializable  {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public PatientPhysicianMap() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	



	public PatientPhysicianMap(int id, PatientDetails patientId,
			PhysicianDetails physicianId) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.physicianId = physicianId;
	}





	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private  int  id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="patient_id")
	private PatientDetails patientId;
	


	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="physician_id")
	private PhysicianDetails physicianId;


	public PhysicianDetails getPhysicianId() {
		return physicianId;
	}


	public void setPhysicianId(PhysicianDetails physicianId) {
		this.physicianId = physicianId;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public PatientDetails getPatientId() {
		return patientId;
	}
	
	

	public void setPatientId(PatientDetails patientId) {
		this.patientId = patientId;
	}


	

}
