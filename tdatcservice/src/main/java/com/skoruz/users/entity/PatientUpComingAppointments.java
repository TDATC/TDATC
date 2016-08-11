package com.skoruz.users.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="patient_upcoming_appointments")
public class PatientUpComingAppointments  implements   Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public PatientUpComingAppointments() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public PatientUpComingAppointments(int id, int patientId, int physicianId,
			Date date, Date timings, String checkUpType, String notes,
			String message) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.physicianId = physicianId;
		this.date = date;
		this.timings = timings;
		this.checkUpType = checkUpType;
		this.notes = notes;
		this.message = message;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private int patientId;
	private int physicianId;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timings;
	
	@Column(name="check_up_type")
	private String checkUpType;
	
	private String notes;
	private String message;
	
	
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
	public int getPhysicianId() {
		return physicianId;
	}
	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getTimings() {
		return timings;
	}
	public void setTimings(Date timings) {
		this.timings = timings;
	}
	public String getCheckUpType() {
		return checkUpType;
	}
	public void setCheckUpType(String checkUpType) {
		this.checkUpType = checkUpType;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
