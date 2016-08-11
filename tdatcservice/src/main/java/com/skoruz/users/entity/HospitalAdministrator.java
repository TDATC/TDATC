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
@Table(name="hospital_admins")

public class HospitalAdministrator  implements Serializable  {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public HospitalAdministrator() {
		super();
		// TODO Auto-generated constructor stub
	}




	public int getHospadmin_id() {
		return hospadmin_id;
	}




	public void setHospadmin_id(int hospadmin_id) {
		this.hospadmin_id = hospadmin_id;
	}




	public HospitalAdministrator(User user, int hospitalBranchId,
			boolean notificationAlert) {
		super();
		this.user = user;
		this.hospitalBranchId = hospitalBranchId;
		this.notificationAlert = notificationAlert;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="hospadmin_id")
	private int  hospadmin_id;

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
	@Column(name="hospital_branch_id")
	private int hospitalBranchId;

	@Column(name="notification_alert")
	private boolean notificationAlert;


	public int getHospitalBranchId() {
		return hospitalBranchId;
	}
	public void setHospitalBranchId(int hospitalBranchId) {
		this.hospitalBranchId = hospitalBranchId;
	}
	public boolean isNotificationAlert() {
		return notificationAlert;
	}
	public void setNotificationAlert(boolean notificationAlert) {
		this.notificationAlert = notificationAlert;
	}





}
