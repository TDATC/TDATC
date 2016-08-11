package com.skoruz.hospitals.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skoruz.entity.AccountState;


@Entity
@Table(name="hospital_branches")
public class HospitalBranchesDTO {
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int branchId;
	
	
	@Column(name="branch_name")
	private String branchName;
	
	@Column(name="branch_code")
	private String branchCode;
	

	private String city;
	private String state;
	private String country;
	
	@Column(name="phone_business1")
	private Integer phoneBusiness1;
	
	@Column(name="phone_business2")
	private Integer phoneBusiness2;
	
	private Integer fax;
	
	@Column(name="is_active")
	@Enumerated(EnumType.STRING)
	private AccountState isActive;

	@Column(name="hospital_id")
	private int hospitalId;

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getPhoneBusiness1() {
		return phoneBusiness1;
	}

	public void setPhoneBusiness1(Integer phoneBusiness1) {
		this.phoneBusiness1 = phoneBusiness1;
	}

	public Integer getPhoneBusiness2() {
		return phoneBusiness2;
	}

	public void setPhoneBusiness2(Integer phoneBusiness2) {
		this.phoneBusiness2 = phoneBusiness2;
	}

	public Integer getFax() {
		return fax;
	}

	public void setFax(Integer fax) {
		this.fax = fax;
	}

	public AccountState getIsActive() {
		return isActive;
	}

	public void setIsActive(AccountState isActive) {
		this.isActive = isActive;
	}

	public int getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}

	
	
	
}
