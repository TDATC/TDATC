package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="patient_measurement_tools_details")
public class PatientMeasurementToolsDetails    implements  Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	

	public PatientMeasurementToolsDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public PatientMeasurementToolsDetails(int id, int measurementToolId,
			int patientId, String measurementToolsValue, String dateTime) {
		super();
		this.id = id;
		this.measurementToolId = measurementToolId;
		this.patientId = patientId;
		this.measurementToolsValue = measurementToolsValue;
		this.dateTime = dateTime;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="measurement_tool_id")
	private int measurementToolId;
	
	/*@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="patient_id")
	@JsonBackReference(value="patient")
	private  PatientDetailsMeasurementToolsDTO patientDetailsMeasurementToolsDTO ;*/
	
	@Column(name="patient_id")
	private int patientId;
	
	@Column(name="measurement_tools_value")
	private String measurementToolsValue;
	
	@Column(name="date_time")
	//@Temporal(TemporalType.TIMESTAMP)
	private String dateTime;
	

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMeasurementToolId() {
		return measurementToolId;
	}

	public void setMeasurementToolId(int measurementToolId) {
		this.measurementToolId = measurementToolId;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getMeasurementToolsValue() {
		return measurementToolsValue;
	}

	public void setMeasurementToolsValue(String measurementToolsValue) {
		this.measurementToolsValue = measurementToolsValue;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	

	/*public PatientDetailsMeasurementToolsDTO getPatientDetailsMeasurementToolsDTO() {
		return patientDetailsMeasurementToolsDTO;
	}

	public void setPatientDetailsMeasurementToolsDTO(
			PatientDetailsMeasurementToolsDTO patientDetailsMeasurementToolsDTO) {
		this.patientDetailsMeasurementToolsDTO = patientDetailsMeasurementToolsDTO;
	}
*/
	
	

}
