package com.skoruz.users.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="country")

public class Country {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private  int  id;
	
	@Column(name="country_code")
	private  String countrycode;
	
	@Column(name="country_state")
	private  String countrystate;
	
	@Column(name="country_name")
	private String countryName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getCountrystate() {
		return countrystate;
	}

	public void setCountrystate(String countrystate) {
		this.countrystate = countrystate;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	/*@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="location_id")
	private Location location;*/

	
	
	
	

}
