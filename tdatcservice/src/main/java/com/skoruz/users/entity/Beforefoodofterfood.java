package com.skoruz.users.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="beforeofterfood")
public class Beforefoodofterfood {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private  int  id;
	
	@Column(name="beforeofterfood")
	private  String  beforeofterfood;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBeforeofterfood() {
		return beforeofterfood;
	}

	public void setBeforeofterfood(String beforeofterfood) {
		this.beforeofterfood = beforeofterfood;
	}
	
	

}
