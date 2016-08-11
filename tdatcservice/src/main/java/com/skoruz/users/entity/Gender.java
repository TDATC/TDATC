package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="gender")
public class Gender implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Gender() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Gender(int gender_id, String gender) {
		super();
		this.gender_id = gender_id;
		this.gender = gender;
	}

	public Gender(String gender) {
		super();
		this.gender = gender;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int gender_id;


	@Column(name="gender_value")
	private  String gender;


	public int getGender_id() {
		return gender_id;
	}


	public void setGender_id(int gender_id) {
		this.gender_id = gender_id;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}





}
