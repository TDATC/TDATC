package com.skoruz.users.service;

import java.util.List;

import com.skoruz.users.entity.PatientCarePartner;
import com.skoruz.users.entity.PatientDetails;

public interface CarePartnerService {
	
	public List<PatientDetails> getCarePartnerPatientDetails(int patientCarePrtnerId);
	

}
