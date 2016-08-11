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
@Table(name="patient_dietary_acid_details")
public class PatientDietaryAcidDetails   implements  Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public PatientDietaryAcidDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public PatientDietaryAcidDetails(int id,
			PatientDetailsDietaryAcidDTO detailsDietaryAcidDTO,
			String dietaryNiacin, String dietaryPantothenicAcid,
			String dietaryPhosphorus, String dietaryPotassium,
			String dietaryProtein, String dietaryRiboflavin,
			String dietarySelenium, String dietarySodium, String dietarySugar,
			String dietaryThiamin, Date dateTime) {
		super();
		this.id = id;
		this.detailsDietaryAcidDTO = detailsDietaryAcidDTO;
		this.dietaryNiacin = dietaryNiacin;
		this.dietaryPantothenicAcid = dietaryPantothenicAcid;
		this.dietaryPhosphorus = dietaryPhosphorus;
		this.dietaryPotassium = dietaryPotassium;
		this.dietaryProtein = dietaryProtein;
		this.dietaryRiboflavin = dietaryRiboflavin;
		this.dietarySelenium = dietarySelenium;
		this.dietarySodium = dietarySodium;
		this.dietarySugar = dietarySugar;
		this.dietaryThiamin = dietaryThiamin;
		this.dateTime = dateTime;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="patient_id")
	@JsonBackReference
	private PatientDetailsDietaryAcidDTO detailsDietaryAcidDTO;
	
	@Column(name="dietary_niacin")
	private String dietaryNiacin;
	
	@Column(name="dietary_pantothenic_acid")
	private String dietaryPantothenicAcid;
	
	@Column(name="dietary_phosphorus")
	private String dietaryPhosphorus;
	
	@Column(name="dietary_potassium")
	private String dietaryPotassium;
	
	@Column(name="dietary_protein")
	private String dietaryProtein;
	
	@Column(name="dietary_riboflavin")
	private String dietaryRiboflavin;
	
	@Column(name="dietary_selenium")
	private String dietarySelenium;
	
	@Column(name="dietary_sodium")
	private String dietarySodium;
	
	@Column(name="dietary_sugar")
	private String dietarySugar;
	
	@Column(name="dietary_thiamin")
	private String dietaryThiamin;
	
	@Column(name="date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDietaryNiacin() {
		return dietaryNiacin;
	}

	public void setDietaryNiacin(String dietaryNiacin) {
		this.dietaryNiacin = dietaryNiacin;
	}

	public String getDietaryPantothenicAcid() {
		return dietaryPantothenicAcid;
	}

	public void setDietaryPantothenicAcid(String dietaryPantothenicAcid) {
		this.dietaryPantothenicAcid = dietaryPantothenicAcid;
	}

	public String getDietaryPhosphorus() {
		return dietaryPhosphorus;
	}

	public void setDietaryPhosphorus(String dietaryPhosphorus) {
		this.dietaryPhosphorus = dietaryPhosphorus;
	}

	public String getDietaryPotassium() {
		return dietaryPotassium;
	}

	public void setDietaryPotassium(String dietaryPotassium) {
		this.dietaryPotassium = dietaryPotassium;
	}

	public String getDietaryProtein() {
		return dietaryProtein;
	}

	public void setDietaryProtein(String dietaryProtein) {
		this.dietaryProtein = dietaryProtein;
	}

	public String getDietaryRiboflavin() {
		return dietaryRiboflavin;
	}

	public void setDietaryRiboflavin(String dietaryRiboflavin) {
		this.dietaryRiboflavin = dietaryRiboflavin;
	}

	public String getDietarySelenium() {
		return dietarySelenium;
	}

	public void setDietarySelenium(String dietarySelenium) {
		this.dietarySelenium = dietarySelenium;
	}

	public String getDietarySodium() {
		return dietarySodium;
	}

	public void setDietarySodium(String dietarySodium) {
		this.dietarySodium = dietarySodium;
	}

	public String getDietarySugar() {
		return dietarySugar;
	}

	public void setDietarySugar(String dietarySugar) {
		this.dietarySugar = dietarySugar;
	}

	public String getDietaryThiamin() {
		return dietaryThiamin;
	}

	public void setDietaryThiamin(String dietaryThiamin) {
		this.dietaryThiamin = dietaryThiamin;
	}

	public PatientDetailsDietaryAcidDTO getDetailsDietaryAcidDTO() {
		return detailsDietaryAcidDTO;
	}

	public void setDetailsDietaryAcidDTO(
			PatientDetailsDietaryAcidDTO detailsDietaryAcidDTO) {
		this.detailsDietaryAcidDTO = detailsDietaryAcidDTO;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	

}
