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
@Table(name="patient_dietary_details")
public class PatientDietaryDetails   implements  Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public PatientDietaryDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public PatientDietaryDetails(int id,
			PatientDetailsDietaryDTO patientDetailsDietaryDTO,
			String dietaryBiotin, String dietaryCaffeine,
			String dietaryCalcium, String dietaryCarbohydrates,
			String dietaryChloride, String dietaryCholesterol,
			String dietaryChromium, String dietaryCopper,
			String dietaryEnergyConsumed, String dietaryFatMonoUnsaturated,
			Date dateTime) {
		super();
		this.id = id;
		this.patientDetailsDietaryDTO = patientDetailsDietaryDTO;
		this.dietaryBiotin = dietaryBiotin;
		this.dietaryCaffeine = dietaryCaffeine;
		this.dietaryCalcium = dietaryCalcium;
		this.dietaryCarbohydrates = dietaryCarbohydrates;
		this.dietaryChloride = dietaryChloride;
		this.dietaryCholesterol = dietaryCholesterol;
		this.dietaryChromium = dietaryChromium;
		this.dietaryCopper = dietaryCopper;
		this.dietaryEnergyConsumed = dietaryEnergyConsumed;
		this.dietaryFatMonoUnsaturated = dietaryFatMonoUnsaturated;
		this.dateTime = dateTime;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="patient_id")
	@JsonBackReference
	private PatientDetailsDietaryDTO patientDetailsDietaryDTO;
	
	@Column(name="dietary_biotin")
	private String dietaryBiotin;
	
	@Column(name="dietary_caffeine")
	private String dietaryCaffeine;
	
	@Column(name="dietary_calcium")
	private String dietaryCalcium;
	
	@Column(name="dietary_carbohydrates")
	private String dietaryCarbohydrates;
	
	@Column(name="dietary_chloride")
	private String dietaryChloride;
	
	@Column(name="dietary_cholesterol")
	private String dietaryCholesterol;
	
	@Column(name="dietary_chromium")
	private String dietaryChromium;
	
	@Column(name="dietary_copper")
	private String dietaryCopper;
	
	@Column(name="dietary_energy_consumed")
	private String dietaryEnergyConsumed;
	
	@Column(name="dietary_fat_mono_unsaturated")
	private String dietaryFatMonoUnsaturated;
	
	@Column(name="date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDietaryBiotin() {
		return dietaryBiotin;
	}

	public void setDietaryBiotin(String dietaryBiotin) {
		this.dietaryBiotin = dietaryBiotin;
	}

	public String getDietaryCaffeine() {
		return dietaryCaffeine;
	}

	public void setDietaryCaffeine(String dietaryCaffeine) {
		this.dietaryCaffeine = dietaryCaffeine;
	}

	public String getDietaryCalcium() {
		return dietaryCalcium;
	}

	public void setDietaryCalcium(String dietaryCalcium) {
		this.dietaryCalcium = dietaryCalcium;
	}

	public String getDietaryCarbohydrates() {
		return dietaryCarbohydrates;
	}

	public void setDietaryCarbohydrates(String dietaryCarbohydrates) {
		this.dietaryCarbohydrates = dietaryCarbohydrates;
	}

	public String getDietaryChloride() {
		return dietaryChloride;
	}

	public void setDietaryChloride(String dietaryChloride) {
		this.dietaryChloride = dietaryChloride;
	}

	public String getDietaryCholesterol() {
		return dietaryCholesterol;
	}

	public void setDietaryCholesterol(String dietaryCholesterol) {
		this.dietaryCholesterol = dietaryCholesterol;
	}

	public String getDietaryChromium() {
		return dietaryChromium;
	}

	public void setDietaryChromium(String dietaryChromium) {
		this.dietaryChromium = dietaryChromium;
	}

	public String getDietaryCopper() {
		return dietaryCopper;
	}

	public void setDietaryCopper(String dietaryCopper) {
		this.dietaryCopper = dietaryCopper;
	}

	public String getDietaryEnergyConsumed() {
		return dietaryEnergyConsumed;
	}

	public void setDietaryEnergyConsumed(String dietaryEnergyConsumed) {
		this.dietaryEnergyConsumed = dietaryEnergyConsumed;
	}

	public String getDietaryFatMonoUnsaturated() {
		return dietaryFatMonoUnsaturated;
	}

	public void setDietaryFatMonoUnsaturated(String dietaryFatMonoUnsaturated) {
		this.dietaryFatMonoUnsaturated = dietaryFatMonoUnsaturated;
	}

	public PatientDetailsDietaryDTO getPatientDetailsDietaryDTO() {
		return patientDetailsDietaryDTO;
	}

	public void setPatientDetailsDietaryDTO(
			PatientDetailsDietaryDTO patientDetailsDietaryDTO) {
		this.patientDetailsDietaryDTO = patientDetailsDietaryDTO;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	
	

}
