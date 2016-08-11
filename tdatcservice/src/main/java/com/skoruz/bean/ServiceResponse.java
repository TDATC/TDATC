package com.skoruz.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.skoruz.users.entity.HospitalAdministrator;
import com.skoruz.users.entity.PatientDetails;

//JOB : service response for all the web services


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({
	PatientDetails.class, HospitalAdministrator.class
})
public class ServiceResponse<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T content;
	private String errorMsg;
	
	
	public T getContent() {
		return content;
	}
	public void setContent(T content) {
		this.content = content;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	

}
