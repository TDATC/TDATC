package com.skoruz.users.entity;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="appointmentstatus")
public class Appointmentstatus {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private  int  id;
	
	@Column(name="appointmentstatus")
	private  String   appointmentstatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppointmentstatus() {
		return appointmentstatus;
	}

	public void setAppointmentstatus(String appointmentstatus) {
		this.appointmentstatus = appointmentstatus;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
