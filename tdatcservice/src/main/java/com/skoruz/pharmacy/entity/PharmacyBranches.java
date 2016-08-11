package com.skoruz.pharmacy.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

import com.skoruz.entity.AccountState;

@Entity
@Table(name="pharmacy_branches")
public class PharmacyBranches  implements  Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	


	public PharmacyBranches() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public PharmacyBranches(int branchId, String branchName, String branchCode,
			String city, String state, String country, Integer phoneBusiness1,
			Integer phoneBusiness2, Integer fax, AccountState isActive,
			Pharmacy pharmacy) {
		super();
		this.branchId = branchId;
		this.branchName = branchName;
		this.branchCode = branchCode;
		this.city = city;
		this.state = state;
		this.country = country;
		this.phoneBusiness1 = phoneBusiness1;
		this.phoneBusiness2 = phoneBusiness2;
		this.fax = fax;
		this.isActive = isActive;
		this.pharmacy = pharmacy;
	}



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
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="pharmacy_id")
	@JsonBackReference
	private Pharmacy pharmacy;


	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

//	public int getPharmacyId() {
//		return pharmacyId;
//	}
//
//	public void setPharmacyId(int pharmacyId) {
//		this.pharmacyId = pharmacyId;
//	}

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

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
}
