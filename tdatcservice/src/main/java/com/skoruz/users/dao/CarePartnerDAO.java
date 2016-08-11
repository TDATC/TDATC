package com.skoruz.users.dao;

import java.util.List;

import com.skoruz.users.entity.PatientCarePartner;

public interface CarePartnerDAO {
	
	public List<PatientCarePartner> getCarePartnerPatientDetails(int patientCarePartnerId);
	
}
