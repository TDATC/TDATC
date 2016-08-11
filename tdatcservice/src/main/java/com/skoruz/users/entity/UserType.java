package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usertype")
public class UserType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int usertype_id;

	@Column(name = "usertype_value")
	private String userType;

	public UserType() {
		super();
	}

	public UserType(int usertype_id, String userType) {
		super();
		this.usertype_id = usertype_id;
		this.userType = userType;
	}

	public UserType(String userType) {
		super();
		this.userType = userType;
	}

	public int getUsertype_id() {
		return usertype_id;
	}

	public void setUsertype_id(int usertype_id) {
		this.usertype_id = usertype_id;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
