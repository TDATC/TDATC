package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

import com.google.gson.Gson;


@Entity
@Table(name="clinic_visiting_timing")
public class ClinicVisitingTiming  implements  Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public ClinicVisitingTiming() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public ClinicVisitingTiming(VisibilityTimingId id, int doctorId,
			Clinic clinic, String session1_end_time,
			String session1_start_time, String session2_end_time,
			String session2_start_time) {
		super();
		this.id = id;
		this.doctorId = doctorId;
		this.clinic = clinic;
		this.session1_end_time = session1_end_time;
		this.session1_start_time = session1_start_time;
		this.session2_end_time = session2_end_time;
		this.session2_start_time = session2_start_time;
	}


	public ClinicVisitingTiming(String grupoAplicacaoJSON) {
	    Gson gson = new Gson();
	    ClinicVisitingTiming clinicVisitingTiming = gson.fromJson(grupoAplicacaoJSON, ClinicVisitingTiming.class);
	    this.id=clinicVisitingTiming.getId();
	    this.doctorId=clinicVisitingTiming.getDoctorId();
	    this.session1_end_time=clinicVisitingTiming.getSession1_end_time();
	    this.session1_start_time=clinicVisitingTiming.getSession1_start_time();
	    this.session2_end_time=clinicVisitingTiming.getSession2_end_time();
	    this.session2_start_time=clinicVisitingTiming.getSession2_start_time();
	}

	
	@EmbeddedId
	/*@AttributeOverrides({@AttributeOverride(name="clinicId",column=@Column(name="clinicId")),
	  @AttributeOverride(name="timing_day",column=@Column(name="timing_day"))})*/
	private VisibilityTimingId id;

	private int doctorId;
	
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="clinicId",insertable=false,updatable=false)
    @JsonBackReference
	private Clinic clinic;
    
    /*@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="appointmentid")
    private  PatientAppointmentDetails PatientAppointmentDetails;*/
    
    
	
	/*public PatientAppointmentDetails getPatientAppointmentDetails() {
		return PatientAppointmentDetails;
	}


	public void setPatientAppointmentDetails(
			PatientAppointmentDetails patientAppointmentDetails) {
		PatientAppointmentDetails = patientAppointmentDetails;
	}*/


	@Column(name="session1_end_time")
	private String session1_end_time;
	
	@Column(name="session1_start_time")
	private String session1_start_time;
	
	@Column(name="session2_end_time")
	private String session2_end_time;
	
	@Column(name="session2_start_time")
	private String session2_start_time;
	
	public VisibilityTimingId getId() {
		return id;
	}

	public void setId(VisibilityTimingId id) {
		this.id = id;
	}
	
	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getSession1_end_time() {
		return session1_end_time;
	}

	public void setSession1_end_time(String session1_end_time) {
		this.session1_end_time = session1_end_time;
	}

	public String getSession1_start_time() {
		return session1_start_time;
	}

	public void setSession1_start_time(String session1_start_time) {
		this.session1_start_time = session1_start_time;
	}

	public String getSession2_end_time() {
		return session2_end_time;
	}

	public void setSession2_end_time(String session2_end_time) {
		this.session2_end_time = session2_end_time;
	}

	public String getSession2_start_time() {
		return session2_start_time;
	}

	public void setSession2_start_time(String session2_start_time) {
		this.session2_start_time = session2_start_time;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

}
