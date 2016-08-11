package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name="allergies")
public class Allergies  implements  Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public Allergies() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

	public Allergies(int id, String allergyName, String allergyType) {
		super();
		this.id = id;
		this.allergyName = allergyName;
		this.allergyType = allergyType;
	}




	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="allergy_name")
	private String allergyName;
	
	@Column(name="allergy_type")
	private String allergyType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAllergyName() {
		return allergyName;
	}

	public void setAllergyName(String allergyName) {
		this.allergyName = allergyName;
	}

	public String getAllergyType() {
		return allergyType;
	}

	public void setAllergyType(String allergyType) {
		this.allergyType = allergyType;
	}


}
