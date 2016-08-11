package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="affiliation")
public class Affliation implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public Affliation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

	public Affliation(int affiliation_id, String universityName) {
		super();
		this.affiliation_id = affiliation_id;
		this.universityName = universityName;
	}




	@Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 private int affiliation_id;
	 
	 @Column(name="university_name")
	 private  String universityName;



	public int getAffiliation_id() {
		return affiliation_id;
	}




	public void setAffiliation_id(int affiliation_id) {
		this.affiliation_id = affiliation_id;
	}




	public String getUniversityName() {
		return universityName;
	}




	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	 
	 

}
