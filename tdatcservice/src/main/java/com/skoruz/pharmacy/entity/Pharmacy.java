package com.skoruz.pharmacy.entity;

import java.io.Serializable;
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
@Table(name="pharmacy")
public class Pharmacy  implements   Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	
	

	public Pharmacy() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	public Pharmacy(int id, String name, String city, String state,
			String country, String pinCode, Integer phoneBusiness, Integer fax,
			AccountState isActive, Set<PharmacyBranches> branches) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pinCode = pinCode;
		this.phoneBusiness = phoneBusiness;
		this.fax = fax;
		this.isActive = isActive;
		this.branches = branches;
	}





	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="pharmacy_name")
	private String name;
	
	
	private String city;
	private String state;
	private String country;
	
	@Column(name="pin_code")
	private String pinCode;
	
	@Column(name="phone_business")
	private Integer phoneBusiness;
	private Integer fax;
	
	@Column(name="is_active")
	@Enumerated(EnumType.STRING)
	private AccountState isActive;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="pharmacy", cascade=CascadeType.ALL)
	@JsonManagedReference
	private Set<PharmacyBranches> branches = new HashSet<PharmacyBranches>();
	
	
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
	public Integer getPhoneBusiness() {
		return phoneBusiness;
	}
	public void setPhoneBusiness(Integer phoneBusiness) {
		this.phoneBusiness = phoneBusiness;
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
	public Set<PharmacyBranches> getBranches() {
		return branches;
	}
	public void setBranches(Set<PharmacyBranches> branches) {
		this.branches = branches;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

}
