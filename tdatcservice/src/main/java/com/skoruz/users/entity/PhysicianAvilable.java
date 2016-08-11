package com.skoruz.users.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="physicianavilable")
public class PhysicianAvilable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private  int  id;
	
	@Column(name="physicianavilable")
	private  String physicianavilable;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhysicianavilable() {
		return physicianavilable;
	}

	public void setPhysicianavilable(String physicianavilable) {
		this.physicianavilable = physicianavilable;
	}
	
	
	
	
	

}
