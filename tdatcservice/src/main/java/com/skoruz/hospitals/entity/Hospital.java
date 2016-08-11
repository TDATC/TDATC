package com.skoruz.hospitals.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonManagedReference;

import com.skoruz.entity.AccountState;


@Entity
@Table(name="hospitals")
public class Hospital {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="hospital_name")
	private String name;
	
	private String city;
	private String state;
	private String country;
	
	@Column(name="pin_code")
	private String pinCode;
	
	@Column(name="phone_business")
	private String phoneBusiness;
	
	private String fax;
	
	@Enumerated(EnumType.STRING)
	@Column(name="is_active")
	private AccountState isActive;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="hospital", cascade=CascadeType.ALL)
	@JsonManagedReference
	private Set<HospitalBranches> branches = new HashSet<HospitalBranches>();
	
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getPhoneBusiness() {
		return phoneBusiness;
	}

	public void setPhoneBusiness(String phoneBusiness) {
		this.phoneBusiness = phoneBusiness;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public AccountState getIsActive() {
		return isActive;
	}

	public void setIsActive(AccountState isActive) {
		this.isActive = isActive;
	}

	public Set<HospitalBranches> getBranches() {
		return branches;
	}

	public void setBranches(Set<HospitalBranches> branches) {
		this.branches = branches;
	}
	
	
}
