package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * @author Skoruz
 *
 */
@Entity
@Table(name="patient_medication_details")
public class PatientMedicationDetails  implements  Serializable  {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	

	public PatientMedicationDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public PatientMedicationDetails(int id, int patientId, Medicine medicine,
			String instruction, String beforeAfterFood, String insertedDate,
			String fromDate, String toDate, int readUnread) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.medicine = medicine;
		this.instruction = instruction;
		this.beforeAfterFood = beforeAfterFood;
		this.insertedDate = insertedDate;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.readUnread = readUnread;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/*@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="patient_id")
	@JsonBackReference
	private PatientDetailsMedicationDTO patientDetailsMedicationDTO;*/
	
	@Column(name="patient_id")
	private int patientId;
	
	//@OneToOne(cascade=CascadeType.ALL)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="medicine_id")
	//@JsonBackReference
	private Medicine medicine;
	
	//private String diseases;
	
	//private String dosage;
	
	/*@Enumerated(EnumType.STRING)
	private Frequency frequency;*/
	
	private String instruction;
	
	@Column(name="before_after_food")
	//@Enumerated(EnumType.STRING)
	private String beforeAfterFood;
	
	@Column(name="inserted_date")
	private String insertedDate;
	
	@Column(name="from_date")
	private String fromDate;
	
	@Column(name="to_date")
	private String toDate;
	
	@Column(name="read_unread")
	private int readUnread;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getBeforeAfterFood() {
		return beforeAfterFood;
	}

	public void setBeforeAfterFood(String beforeAfterFood) {
		this.beforeAfterFood = beforeAfterFood;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public String getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(String insertedDate) {
		this.insertedDate = insertedDate;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public int getReadUnread() {
		return readUnread;
	}

	public void setReadUnread(int readUnread) {
		this.readUnread = readUnread;
	}


	
	

}
