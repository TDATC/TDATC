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
@Table(name="patient_vitamin_details")
public class PatientVitaminDetails  implements  Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public PatientVitaminDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public PatientVitaminDetails(int id,
			PatientDetailsVitaminDTO patientDetailsVitaminDTO,
			String dietaryVitamin_A, String dietaryVitamin_B12,
			String dietaryVitamin_B6, String dietaryVitamin_C,
			String dietaryVitamin_D, String dietaryVitamin_E,
			String dietaryVitamin_K, Date dateTime) {
		super();
		this.id = id;
		this.patientDetailsVitaminDTO = patientDetailsVitaminDTO;
		this.dietaryVitamin_A = dietaryVitamin_A;
		this.dietaryVitamin_B12 = dietaryVitamin_B12;
		this.dietaryVitamin_B6 = dietaryVitamin_B6;
		this.dietaryVitamin_C = dietaryVitamin_C;
		this.dietaryVitamin_D = dietaryVitamin_D;
		this.dietaryVitamin_E = dietaryVitamin_E;
		this.dietaryVitamin_K = dietaryVitamin_K;
		this.dateTime = dateTime;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="patient_id")
	@JsonBackReference
	private PatientDetailsVitaminDTO patientDetailsVitaminDTO;
	
	@Column(name="dietary_vitamin_A")
	private String dietaryVitamin_A;
	
	@Column(name="dietary_vitamin_B12")
	private String dietaryVitamin_B12;
	
	@Column(name="dietary_vitamin_B6")
	private String dietaryVitamin_B6;
	
	@Column(name="dietary_vitamin_C")
	private String dietaryVitamin_C;
	
	@Column(name="dietary_vitamin_D")
	private String dietaryVitamin_D;
	
	@Column(name="dietary_vitamin_E")
	private String dietaryVitamin_E;
	
	@Column(name="dietary_vitamin_K")
	private String dietaryVitamin_K;
	
	@Column(name="date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDietaryVitamin_A() {
		return dietaryVitamin_A;
	}

	public void setDietaryVitamin_A(String dietaryVitamin_A) {
		this.dietaryVitamin_A = dietaryVitamin_A;
	}

	public String getDietaryVitamin_B12() {
		return dietaryVitamin_B12;
	}

	public void setDietaryVitamin_B12(String dietaryVitamin_B12) {
		this.dietaryVitamin_B12 = dietaryVitamin_B12;
	}

	public String getDietaryVitamin_B6() {
		return dietaryVitamin_B6;
	}

	public void setDietaryVitamin_B6(String dietaryVitamin_B6) {
		this.dietaryVitamin_B6 = dietaryVitamin_B6;
	}

	public String getDietaryVitamin_C() {
		return dietaryVitamin_C;
	}

	public void setDietaryVitamin_C(String dietaryVitamin_C) {
		this.dietaryVitamin_C = dietaryVitamin_C;
	}

	public String getDietaryVitamin_D() {
		return dietaryVitamin_D;
	}

	public void setDietaryVitamin_D(String dietaryVitamin_D) {
		this.dietaryVitamin_D = dietaryVitamin_D;
	}

	public String getDietaryVitamin_E() {
		return dietaryVitamin_E;
	}

	public void setDietaryVitamin_E(String dietaryVitamin_E) {
		this.dietaryVitamin_E = dietaryVitamin_E;
	}

	public String getDietaryVitamin_K() {
		return dietaryVitamin_K;
	}

	public void setDietaryVitamin_K(String dietaryVitamin_K) {
		this.dietaryVitamin_K = dietaryVitamin_K;
	}


	public PatientDetailsVitaminDTO getPatientDetailsVitaminDTO() {
		return patientDetailsVitaminDTO;
	}

	public void setPatientDetailsVitaminDTO(
			PatientDetailsVitaminDTO patientDetailsVitaminDTO) {
		this.patientDetailsVitaminDTO = patientDetailsVitaminDTO;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		//System.out.println("date object  "+dateTime);
		this.dateTime = dateTime;
	}

	
	
	
}
