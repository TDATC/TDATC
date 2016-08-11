package com.skoruz.users.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonBackReference;

import com.skoruz.entity.AppointmentStatus;


@Entity
@Table(name="patient_appointments")
public class PatientAppointmentDetailsDTO {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	/*//private patientid;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="patient_id")
	@JsonBackReference(value="patient")
	private PatientDetailsAppointmentDTO patientDetailsAppointmentDTO ;*/

	//private physicianid;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="physician_id")
	@JsonBackReference(value="physician")
	private PatientDetailsAppointmentDTO patientDetailsAppointmentDTO  ;

	

	@Column(name="timings")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timings;

	@Column(name="date")
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name="appointment_description")
	private String appointmentDescription;

	@Enumerated(EnumType.STRING)
	private AppointmentStatus status;

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

	public Date getTimings() {
		return timings;
	}

	public void setTimings(Date timings) {
		this.timings = timings;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

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

	public PatientDetailsAppointmentDTO getPatientDetailsAppointmentDTO() {
		return patientDetailsAppointmentDTO;
	}

	public void setPatientDetailsAppointmentDTO(
			PatientDetailsAppointmentDTO patientDetailsAppointmentDTO) {
		this.patientDetailsAppointmentDTO = patientDetailsAppointmentDTO;
	}

	/*public PhysicianDetailsAppointmentDTO getPhysicianDetailsAppointmentDTO() {
		return physicianDetailsAppointmentDTO;
	}

	public void setPhysicianDetailsAppointmentDTO(
			PhysicianDetailsAppointmentDTO physicianDetailsAppointmentDTO) {
		this.physicianDetailsAppointmentDTO = physicianDetailsAppointmentDTO;
	}
*/
	



}





