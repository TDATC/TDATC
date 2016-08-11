package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VisibilityTimingId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	
	
	
	public VisibilityTimingId() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public VisibilityTimingId(int clinicId, String timing_day) {
		super();
		this.clinicId = clinicId;
		this.timing_day = timing_day;
	}



	@Column(name="clinicId")
	private int clinicId;
	
	@Column(name="timing_day")
	private String timing_day;
	
	public int getClinicId() {
		return clinicId;
	}
	public void setClinicId(int clinicId) {
		this.clinicId = clinicId;
	}
	public String getTiming_day() {
		return timing_day;
	}
	public void setTiming_day(String timing_day) {
		this.timing_day = timing_day;
	}
}



