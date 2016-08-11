package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="medication_procedure")
public class MedicationProcedure   implements  Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public MedicationProcedure() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public MedicationProcedure(int id, String procedureName) {
		super();
		this.id = id;
		this.procedureName = procedureName;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="procedure_name")
	private String procedureName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}
	
	

}
