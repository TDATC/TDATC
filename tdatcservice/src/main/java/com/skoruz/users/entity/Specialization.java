package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="specialization")
public class Specialization  implements  Serializable {
	
 
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

public Specialization() {
	super();
	// TODO Auto-generated constructor stub
}




public Specialization(int specialization_id, String specializations) {
	super();
	this.specialization_id = specialization_id;
	this.specializations = specializations;
}




@Id
 @GeneratedValue(strategy=GenerationType.AUTO)
 private int specialization_id;
 
 @Column(name="specialization_name")
 private String specializations;

public int getSpecialization_id() {
	return specialization_id;
}

public void setSpecialization_id(int specialization_id) {
	this.specialization_id = specialization_id;
}

public String getSpecializations() {
	return specializations;
}

public void setSpecializations(String specializations) {
	this.specializations = specializations;
}


}