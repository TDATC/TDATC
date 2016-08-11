package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="qualification")
public class Qualification  implements   Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	

	public Qualification() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Qualification(int qualification_id, String qualification) {
		super();
		this.qualification_id = qualification_id;
		this.qualification = qualification;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int qualification_id;
	
	private String qualification;

	public int getQualification_id() {
		return qualification_id;
	}

	public void setQualification_id(int qualification_id) {
		this.qualification_id = qualification_id;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
}
