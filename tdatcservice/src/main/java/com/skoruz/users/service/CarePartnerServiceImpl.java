package com.skoruz.users.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skoruz.users.dao.CarePartnerDAO;
import com.skoruz.users.dao.PatientDAO;
import com.skoruz.users.entity.PatientCarePartner;
import com.skoruz.users.entity.PatientDetails;

@Service
public class CarePartnerServiceImpl implements CarePartnerService {

	@Autowired
	CarePartnerDAO carePartnerDao;
	@Autowired
	PatientDAO patientDao;

	@Override
	@Transactional
	public List<PatientDetails> getCarePartnerPatientDetails(int patientCarePrtnerId) {
		
		List<PatientCarePartner> PatientCarePartner = carePartnerDao.getCarePartnerPatientDetails(patientCarePrtnerId);
		
		List<PatientDetails> patientDetails = new ArrayList<PatientDetails>();
		
		for(PatientCarePartner carePartner: PatientCarePartner)
		{
			int subscriberId = carePartner.getPatientSubscriberId();
			patientDetails.add(patientDao.getPatientDetails(subscriberId));
		}
		
		return patientDetails;
	}
	


	
}
