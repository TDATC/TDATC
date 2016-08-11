package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="medicine")
public class Medicine  implements  Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public Medicine() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Medicine(int id, String medicineName, String chemicalName,
			String medicineType) {
		super();
		this.id = id;
		this.medicineName = medicineName;
		this.chemicalName = chemicalName;
		this.medicineType = medicineType;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="medicine_name")
	private String medicineName;
	
	@Column(name="chemical_name")
	private String chemicalName;
	
	@Column(name="medicine_type")
	private String medicineType;
	
	/*@OneToOne(fetch = FetchType.EAGER, mappedBy = "medicine", cascade = CascadeType.ALL)
	@JsonManagedReference
	private PatientMedicationDetails patientMedicationDetails;
*/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public String getChemicalName() {
		return chemicalName;
	}

	public void setChemicalName(String chemicalName) {
		this.chemicalName = chemicalName;
	}

	public String getMedicineType() {
		return medicineType;
	}

	public void setMedicineType(String medicineType) {
		this.medicineType = medicineType;
	}

	/*public PatientMedicationDetails getPatientMedicationDetails() {
		return patientMedicationDetails;
	}

	public void setPatientMedicationDetails(
			PatientMedicationDetails patientMedicationDetails) {
		this.patientMedicationDetails = patientMedicationDetails;
	}*/
	
	
	
	

}
