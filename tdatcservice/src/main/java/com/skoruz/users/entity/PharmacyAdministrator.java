package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="pharmacy_admins")

public class PharmacyAdministrator implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	
	public int getPharadmin_id() {
		return pharadmin_id;
	}



	public void setPharadmin_id(int pharadmin_id) {
		this.pharadmin_id = pharadmin_id;
	}



	public PharmacyAdministrator() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public PharmacyAdministrator(User user, Integer pharmacyBranchId,
			boolean notificationAlert) {
		super();
		this.user = user;
		this.pharmacyBranchId = pharmacyBranchId;
		this.notificationAlert = notificationAlert;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pharadmin_id")
	private  int  pharadmin_id;



	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name="pharmacy_branch_id")
	private Integer pharmacyBranchId;
	
	@Column(name="notification_alert")
	private boolean notificationAlert;

	public Integer getPharmacyBranchId() {
		return pharmacyBranchId;
	}

	public void setPharmacyBranchId(Integer pharmacyBranchId) {
		this.pharmacyBranchId = pharmacyBranchId;
	}

	public boolean isNotificationAlert() {
		return notificationAlert;
	}

	public void setNotificationAlert(boolean notificationAlert) {
		this.notificationAlert = notificationAlert;
	}
	
	

}
