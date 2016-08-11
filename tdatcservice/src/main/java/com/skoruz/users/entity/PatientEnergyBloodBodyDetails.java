package com.skoruz.users.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name="patient_energy_blood_body_details")
public class PatientEnergyBloodBodyDetails   implements  Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	

	public PatientEnergyBloodBodyDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public PatientEnergyBloodBodyDetails(int id,
			PatientDetailsEnergyBloodBodyDTO patientDetailsEnergyBloodBodyDTO,
			String activeEnergyBurned, String basalEnergyBurned,
			String bloodGlucose, String bloodOxygenSaturation,
			String bloodPressure, String bodyMassIndex, String bodyFat,
			String bodyTemperature, Date dateTime) {
		super();
		this.id = id;
		this.patientDetailsEnergyBloodBodyDTO = patientDetailsEnergyBloodBodyDTO;
		this.activeEnergyBurned = activeEnergyBurned;
		this.basalEnergyBurned = basalEnergyBurned;
		this.bloodGlucose = bloodGlucose;
		this.bloodOxygenSaturation = bloodOxygenSaturation;
		this.bloodPressure = bloodPressure;
		this.bodyMassIndex = bodyMassIndex;
		this.bodyFat = bodyFat;
		this.bodyTemperature = bodyTemperature;
		this.dateTime = dateTime;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="patient_id")
	@JsonBackReference
	private PatientDetailsEnergyBloodBodyDTO patientDetailsEnergyBloodBodyDTO;

	@Column(name="active_energy_burned")
	private String activeEnergyBurned;
	
	@Column(name="basal_energy_burned")
	private String basalEnergyBurned;
	
	@Column(name="blood_glucose")
	private String bloodGlucose;
	
	@Column(name="blood_oxygen_saturation")
	private String bloodOxygenSaturation;
	
	@Column(name="blood_pressure")
	private String bloodPressure;
	
	@Column(name="BMI")
	private String bodyMassIndex;
	
	@Column(name="body_fat")
    private String bodyFat;
	
	@Column(name="body_temperature")
    private String bodyTemperature;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_time")
    private Date dateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PatientDetailsEnergyBloodBodyDTO getPatientDetailsEnergyBloodBodyDTO() {
		return patientDetailsEnergyBloodBodyDTO;
	}

	public void setPatientDetailsEnergyBloodBodyDTO(
			PatientDetailsEnergyBloodBodyDTO patientDetailsEnergyBloodBodyDTO) {
		this.patientDetailsEnergyBloodBodyDTO = patientDetailsEnergyBloodBodyDTO;
	}

	public String getActiveEnergyBurned() {
		return activeEnergyBurned;
	}

	public void setActiveEnergyBurned(String activeEnergyBurned) {
		this.activeEnergyBurned = activeEnergyBurned;
	}

	public String getBasalEnergyBurned() {
		return basalEnergyBurned;
	}

	public void setBasalEnergyBurned(String basalEnergyBurned) {
		this.basalEnergyBurned = basalEnergyBurned;
	}

	public String getBloodGlucose() {
		return bloodGlucose;
	}

	public void setBloodGlucose(String bloodGlucose) {
		this.bloodGlucose = bloodGlucose;
	}

	public String getBloodOxygenSaturation() {
		return bloodOxygenSaturation;
	}

	public void setBloodOxygenSaturation(String bloodOxygenSaturation) {
		this.bloodOxygenSaturation = bloodOxygenSaturation;
	}

	public String getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public String getBodyMassIndex() {
		return bodyMassIndex;
	}

	public void setBodyMassIndex(String bodyMassIndex) {
		this.bodyMassIndex = bodyMassIndex;
	}

	public String getBodyFat() {
		return bodyFat;
	}

	public void setBodyFat(String bodyFat) {
		this.bodyFat = bodyFat;
	}

	public String getBodyTemperature() {
		return bodyTemperature;
	}

	public void setBodyTemperature(String bodTemperature) {
		this.bodyTemperature = bodTemperature;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}


	
	
    
    
    
    

}
