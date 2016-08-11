package com.skoruz.users.entity;

import java.io.Serializable;

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
@Table(name="location")
public class Location  implements Serializable  {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Location(int location_id, String locationName, City city) {
		super();
		this.location_id = location_id;
		this.locationName = locationName;
		this.city = city;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int location_id;
	
	@Column(name="location_name")
	private String locationName;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="city_id")
	//@JsonBackReference
	private City city;

	public int getLocation_id() {
		return location_id;
	}

	public void setLocation_id(int location_id) {
		this.location_id = location_id;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	
	
	

}
