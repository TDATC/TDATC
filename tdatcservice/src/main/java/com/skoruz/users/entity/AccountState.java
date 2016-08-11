package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="accountstate")
public class AccountState implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	

	public AccountState() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public AccountState(int account_id, String status) {
		super();
		this.account_id = account_id;
		this.status = status;
	}

	public AccountState(String status) {
		super();
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private  int  account_id;
	
	@Column(name="account_status")
	private  String status;

	public int getAccount_id() {
		return account_id;
	}


	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
