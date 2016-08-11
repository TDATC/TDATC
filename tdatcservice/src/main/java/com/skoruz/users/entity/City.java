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
@Table(name="city")
public class City  implements  Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	

	public City() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public City(int cityId, String cityName, Country country) {
		super();
		this.cityId = cityId;
		this.cityName = cityName;
		this.country =  country;
		
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="city_id")
	private int cityId;
	
	@Column(name="city_name")
	private String cityName;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="countrycode_id")
	private Country country;
   

	public int getCityId() {
		return cityId;
	}


	public void setCityId(int cityId) {
		this.cityId = cityId;
	}


	public String getCityName() {
		return cityName;
	}


	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Country getCountry() {
		return country;
	}


	public void setCountry(Country country) {
		this.country = country;
	}

	
	
	
	
	/*@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="city")
	@JsonManagedReference
	private Set<Location> location = new HashSet<Location>();*/

	

	/*public Set<Location> getLocation() {
		return location;
	}

	public void setLocation(Set<Location> location) {
		this.location = location;
	}
	*/
	

}
