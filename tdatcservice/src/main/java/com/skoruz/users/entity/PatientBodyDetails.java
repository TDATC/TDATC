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
@Table(name="patient_body_details")
public class PatientBodyDetails  implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public PatientBodyDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public PatientBodyDetails(int id,
			PatientDetailsBodyDTO patientDetailsBodyDTO, String dietaryZinc,
			String glycosylatedHemoglobin, String heartRate, String height,
			String leanBodyMass, String peakFlow, String peripheralPerfusion,
			String respiratoryRate, Date dateTime) {
		super();
		this.id = id;
		this.patientDetailsBodyDTO = patientDetailsBodyDTO;
		this.dietaryZinc = dietaryZinc;
		this.glycosylatedHemoglobin = glycosylatedHemoglobin;
		this.heartRate = heartRate;
		this.height = height;
		this.leanBodyMass = leanBodyMass;
		this.peakFlow = peakFlow;
		this.peripheralPerfusion = peripheralPerfusion;
		this.respiratoryRate = respiratoryRate;
		this.dateTime = dateTime;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="patient_id")
	@JsonBackReference
	private PatientDetailsBodyDTO patientDetailsBodyDTO;
	
	@Column(name="dietary_zinc")
	private String dietaryZinc;
	
	@Column(name="glycosylated_hemoglobin")
	private String glycosylatedHemoglobin;
	
	@Column(name="heart_rate")
	private String heartRate;
	
	@Column(name="height")
	private String height;
	
	@Column(name="LBM")
	private String leanBodyMass;
	
	@Column(name="peak_flow")
	private String peakFlow;
	
	@Column(name="peripheral_perfusion")
	private String peripheralPerfusion;
	
	@Column(name="respiratory_rate")
	private String respiratoryRate;
	
	@Column(name="date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDietaryZinc() {
		return dietaryZinc;
	}

	public void setDietaryZinc(String dietaryZinc) {
		this.dietaryZinc = dietaryZinc;
	}

	public String getGlycosylatedHemoglobin() {
		return glycosylatedHemoglobin;
	}

	public void setGlycosylatedHemoglobin(String glycosylatedHemoglobin) {
		this.glycosylatedHemoglobin = glycosylatedHemoglobin;
	}

	public String getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(String heartRate) {
		this.heartRate = heartRate;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getLeanBodyMass() {
		return leanBodyMass;
	}

	public void setLeanBodyMass(String leanBodyMass) {
		this.leanBodyMass = leanBodyMass;
	}

	public String getPeakFlow() {
		return peakFlow;
	}

	public void setPeakFlow(String peakFlow) {
		this.peakFlow = peakFlow;
	}

	public String getPeripheralPerfusion() {
		return peripheralPerfusion;
	}

	public void setPeripheralPerfusion(String peripheralPerfusion) {
		this.peripheralPerfusion = peripheralPerfusion;
	}

	public String getRespiratoryRate() {
		return respiratoryRate;
	}

	public void setRespiratoryRate(String respiratoryRate) {
		this.respiratoryRate = respiratoryRate;
	}

	public PatientDetailsBodyDTO getPatientDetailsBodyDTO() {
		return patientDetailsBodyDTO;
	}

	public void setPatientDetailsBodyDTO(PatientDetailsBodyDTO patientDetailsBodyDTO) {
		this.patientDetailsBodyDTO = patientDetailsBodyDTO;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}


	

	

}
