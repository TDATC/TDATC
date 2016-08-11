package com.skoruz.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skoruz.users.entity.PatientDetails;
import com.skoruz.users.service.CarePartnerService;



@Component
@Path("/carePartner")
public class CarePartnerRestService {
	
	
	
	
	
	@Autowired
	CarePartnerService carePartnerService;
	
	@GET
	@Path("/get/{patientCarePartnerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PatientDetails> getCarePartnerPatientDetails(@PathParam("patientCarePartnerId") int patientCarePartnerId)
	{
		List<PatientDetails> patientDetails = carePartnerService.getCarePartnerPatientDetails(patientCarePartnerId);
		
		return patientDetails ;
	}
	
	
}
