package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skoruz.entity.AppointmentStatus;


@Entity
@Table(name="patient_appointments")
public class PatientAppointmentDetails  implements  Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PatientAppointmentDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public PatientAppointmentDetails(int id, int patientId, int physicianId,
			String timings, String date, String appointmentDescription,
			AppointmentStatus status, int readUnread) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.physicianId = physicianId;
		this.timings = timings;
		this.date = date;
		this.appointmentDescription = appointmentDescription;
		this.status = status;
		this.readUnread = readUnread;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	/*
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="patient_id")
	@JsonBackReference(value="patient")
	private PatientDetailsAppointmentDTO patientDetailsAppointmentDTO ;
	*/
	/*@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="patient_id")
	private PatientDetails patientDetails;
	*/
	@Column(name="patient_id")
	private int patientId;
	
	@Column(name="physician_id")
	private int physicianId;
	
	/*@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="physician_id")
	@JsonBackReference(value="physician")
	private PhysicianDetailsAppointmentDTO physicianDetailsAppointmentDTO ;
	*/
	@Column(name="timings")
	//@Temporal(TemporalType.TIMESTAMP)
	private String timings;
	
	@Column(name="date")
	//@Temporal(TemporalType.DATE)
	private String date;
	
	@Column(name="appointment_description")
	private String appointmentDescription;
	
	@Enumerated(EnumType.STRING)
	private AppointmentStatus status;
	
	@Column(name="read_unread")
	private int readUnread;

	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*public PatientDetailsAppointmentDTO getPatientDetailsAppointmentDTO() {
		return patientDetailsAppointmentDTO;
	}

	public void setPatientDetailsAppointmentDTO(
			PatientDetailsAppointmentDTO patientDetailsAppointmentDTO) {
		this.patientDetailsAppointmentDTO = patientDetailsAppointmentDTO;
	}*/

	public String getAppointmentDescription() {
		return appointmentDescription;
	}

	public void setAppointmentDescription(String appointmentDescription) {
		this.appointmentDescription = appointmentDescription;
	}

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	/*public PatientDetails getPatientDetails() {
		return patientDetails;
	}

	public void setPatientDetails(PatientDetails patientDetails) {
		this.patientDetails = patientDetails;
	}*/

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public int getReadUnread() {
		return readUnread;
	}

	public void setReadUnread(int readUnread) {
		this.readUnread = readUnread;
	}

	public String getTimings() {
		return timings;
	}

	public void setTimings(String timings) {
		this.timings = timings;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPhysicianId() {
		return physicianId;
	}

	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
	}

	/*public PatientDetails getPatientDetails() {
		return patientDetails;
	}

	public void setPatientDetails(PatientDetails patientDetails) {
		this.patientDetails = patientDetails;
	}
*/
	/*public PhysicianDetailsAppointmentDTO getPhysicianDetailsAppointmentDTO() {
		return physicianDetailsAppointmentDTO;
	}

	public void setPhysicianDetailsAppointmentDTO(
			PhysicianDetailsAppointmentDTO physicianDetailsAppointmentDTO) {
		this.physicianDetailsAppointmentDTO = physicianDetailsAppointmentDTO;
	}*/

	
	
	

}
