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
@Table(name="patient_dietary_fat_details")
public class PatientDietaryFatDetails  implements  Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	

	public PatientDietaryFatDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public PatientDietaryFatDetails(int id,
			PatientDetailsDietaryFatDTO patientDetailsDietaryFatDTO,
			String dietaryFatPolyunsaturated, String dietaryFatSaturated,
			String dietaryFatTotal, String dietaryFiber, String dietaryFolate,
			String dietaryIodine, String dietaryIron, String dietaryMagnesium,
			String dietaryManganese, String dietaryMolybdenum, Date dateTime) {
		super();
		this.id = id;
		this.patientDetailsDietaryFatDTO = patientDetailsDietaryFatDTO;
		this.dietaryFatPolyunsaturated = dietaryFatPolyunsaturated;
		this.dietaryFatSaturated = dietaryFatSaturated;
		this.dietaryFatTotal = dietaryFatTotal;
		this.dietaryFiber = dietaryFiber;
		this.dietaryFolate = dietaryFolate;
		this.dietaryIodine = dietaryIodine;
		this.dietaryIron = dietaryIron;
		this.dietaryMagnesium = dietaryMagnesium;
		this.dietaryManganese = dietaryManganese;
		this.dietaryMolybdenum = dietaryMolybdenum;
		this.dateTime = dateTime;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="patient_id")
	@JsonBackReference
	private PatientDetailsDietaryFatDTO patientDetailsDietaryFatDTO;
	
	
	@Column(name="dietary_fat_polyunsaturated")
	private String dietaryFatPolyunsaturated;
	
	@Column(name="dietary_fat_saturated")
	private String dietaryFatSaturated;
	
	@Column(name="dietary_fat_total")
	private String dietaryFatTotal;
	
	@Column(name="dietary_fiber")
	private String dietaryFiber;
	
	@Column(name="dietary_folate")
	private String dietaryFolate;
	
	@Column(name="dietary_iodine")
	private String dietaryIodine;
	
	@Column(name="dietary_iron")
	private String dietaryIron;
	
	@Column(name="dietary_magnesium")
	private String dietaryMagnesium;
	
	@Column(name="dietary_manganese")
	private String dietaryManganese;
	
	@Column(name="dietary_molybdenum")
	private String dietaryMolybdenum;

	@Column(name="date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getDietaryFatPolyunsaturated() {
		return dietaryFatPolyunsaturated;
	}

	public void setDietaryFatPolyunsaturated(String dietaryFatPolyunsaturated) {
		this.dietaryFatPolyunsaturated = dietaryFatPolyunsaturated;
	}

	public String getDietaryFatSaturated() {
		return dietaryFatSaturated;
	}

	public void setDietaryFatSaturated(String dietaryFatSaturated) {
		this.dietaryFatSaturated = dietaryFatSaturated;
	}

	public String getDietaryFatTotal() {
		return dietaryFatTotal;
	}

	public void setDietaryFatTotal(String dietaryFatTotal) {
		this.dietaryFatTotal = dietaryFatTotal;
	}

	public String getDietaryFiber() {
		return dietaryFiber;
	}

	public void setDietaryFiber(String dietaryFiber) {
		this.dietaryFiber = dietaryFiber;
	}

	public String getDietaryFolate() {
		return dietaryFolate;
	}

	public void setDietaryFolate(String dietaryFolate) {
		this.dietaryFolate = dietaryFolate;
	}

	public String getDietaryIodine() {
		return dietaryIodine;
	}

	public void setDietaryIodine(String dietaryIodine) {
		this.dietaryIodine = dietaryIodine;
	}

	public String getDietaryIron() {
		return dietaryIron;
	}

	public void setDietaryIron(String dietaryIron) {
		this.dietaryIron = dietaryIron;
	}

	public String getDietaryMagnesium() {
		return dietaryMagnesium;
	}

	public void setDietaryMagnesium(String dietaryMagnesium) {
		this.dietaryMagnesium = dietaryMagnesium;
	}

	public String getDietaryManganese() {
		return dietaryManganese;
	}

	public void setDietaryManganese(String dietaryManganese) {
		this.dietaryManganese = dietaryManganese;
	}

	public String getDietaryMolybdenum() {
		return dietaryMolybdenum;
	}

	public void setDietaryMolybdenum(String dietaryMolybdenum) {
		this.dietaryMolybdenum = dietaryMolybdenum;
	}

	public PatientDetailsDietaryFatDTO getPatientDetailsDietaryFatDTO() {
		return patientDetailsDietaryFatDTO;
	}

	public void setPatientDetailsDietaryFatDTO(
			PatientDetailsDietaryFatDTO patientDetailsDietaryFatDTO) {
		this.patientDetailsDietaryFatDTO = patientDetailsDietaryFatDTO;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	
	
	

}
