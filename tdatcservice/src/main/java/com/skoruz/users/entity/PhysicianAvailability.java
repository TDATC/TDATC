 package com.skoruz.users.entity;

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

import com.skoruz.entity.PhysicianAvailable;


@Entity
@Table(name="physician_availability_details")
public class PhysicianAvailability   implements  Serializable {
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public PhysicianAvailability() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	public PhysicianAvailability(int id, PhysicianDetails physicianDetails,
			PhysicianAvailable availableDay, String availableTimeFrom,
			String availableTimeTo) {
		super();
		this.id = id;
		this.physicianDetails = physicianDetails;
		this.availableDay = availableDay;
		this.availableTimeFrom = availableTimeFrom;
		this.availableTimeTo = availableTimeTo;
	}





	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="physician_id")
	@JsonBackReference
	private PhysicianDetails physicianDetails;
	
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="clinic_id")
	@JsonBackReference
	private Clinic clinic;*/
	
	
	@Column(name="available_day")
	@Enumerated(EnumType.STRING)
	private PhysicianAvailable availableDay;
	
	@Column(name="available_time_from")
	//@Temporal(TemporalType.TIME)
	private String availableTimeFrom;
	
	@Column(name="available_time_to")
	//@Temporal(TemporalType.TIME)
	private String availableTimeTo;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public PhysicianAvailable getAvailableDay() {
		return availableDay;
	}
	public void setAvailableDay(PhysicianAvailable availableDay) {
		this.availableDay = availableDay;
	}
	public PhysicianDetails getPhysicianDetails() {
		return physicianDetails;
	}
	public void setPhysicianDetails(PhysicianDetails physicianDetails) {
		this.physicianDetails = physicianDetails;
	}
	public String getAvailableTimeFrom() {
		return availableTimeFrom;
	}
	public void setAvailableTimeFrom(String availableTimeFrom) {
		this.availableTimeFrom = availableTimeFrom;
	}
	public String getAvailableTimeTo() {
		return availableTimeTo;
	}
	public void setAvailableTimeTo(String availableTimeTo) {
		this.availableTimeTo = availableTimeTo;
	}
	/*public Clinic getClinic() {
		return clinic;
	}
	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	*/
	
	

}
