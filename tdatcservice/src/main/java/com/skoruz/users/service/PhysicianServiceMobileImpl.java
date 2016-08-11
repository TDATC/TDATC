package com.skoruz.users.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skoruz.users.dao.PhysicianMobileDAO;
import com.skoruz.users.entity.PhysicianAvailability;
import com.skoruz.users.entity.PhysicianDetails;

@Service
public class PhysicianServiceMobileImpl implements PhysicianMobileService {
	
	@Autowired
	PhysicianMobileDAO physicianMobileDao;

	@Override
	@Transactional
	public int addPhysicianDetails(PhysicianDetails PhysicianDetails) {
		int i = physicianMobileDao.savePhysicianDetails(PhysicianDetails);
		return i;
	}
	
	@Override
	@Transactional
	public List<PhysicianDetails> getPhysicianDetails(String searchCriteria) {
		List<PhysicianDetails> physicianList = physicianMobileDao.getPhysicianDetails(searchCriteria);
		 return physicianList;
	}

	@Override
	@Transactional
	public int addPhysicianAvailability(PhysicianAvailability availability ) {
		int i = physicianMobileDao.addPhysicianAvailability(availability);
		return i;
	}

	@Override
	@Transactional
	public PhysicianDetails getPhysicianAvailability(int physicianId) {
		PhysicianAvailability availability = physicianMobileDao.getPhysicianAvailability(physicianId);		
		return availability.getPhysicianDetails();
	}
	
	@Override
	@Transactional
	public PhysicianDetails getPhysicianDetails(String emailAdress,String password) {
		PhysicianDetails physicianDetails = physicianMobileDao.getPhysicianDetails(emailAdress,password);
		return physicianDetails;
	}
	
	@Override
	@Transactional
	public List<PhysicianDetails> getPhysicianList(int patientId) {
		List<PhysicianDetails> physicianDetails=physicianMobileDao.getAllPhysicians(patientId);
		return physicianDetails;
	}
	

	
	
	@Override
	@Transactional
	public List<PhysicianDetails> getMappedPhysicians(int id) {
		List<PhysicianDetails> physicianDetails=physicianMobileDao.getAllMappedPhysicians(id);
		return physicianDetails;
	}
	
	 @Override
	 @Transactional
	 public int physicianSignUp(PhysicianDetails physiciandetails) {
	  int i = physicianMobileDao.physicianSignUp(physiciandetails);
	  return i;
	 }
	 
	 @Override
	 @Transactional
	 public String physicianProfileUpdate(PhysicianDetails physicianDetails) {
		return physicianMobileDao.physicianProfileUpdate(physicianDetails);
	 }
	 
	 @Override
	 @Transactional
	 public  List<PhysicianDetails> getSpecializedPhysicianDetails(String specialization) {
	 List<PhysicianDetails> physicianDetails =   physicianMobileDao.getSpecializedPhysicianDetails(specialization);
		return physicianDetails;
		

	 }


	@Override
	public int deleteMappedPhysician(int id, Map<String, String> doctorIdMap) {
		int i =  physicianMobileDao.deleteMappedPhysician(id, doctorIdMap);
		return i;
		
	}

	@Override
	public List<PhysicianDetails> getAllPhysicianList(int patientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public PhysicianDetails getPhysicianDetail(int id) {
		// TODO Auto-generated method stub
		PhysicianDetails details=physicianMobileDao.getPhysicianDetails(id);
		return details;
	}

	 

	
	
	
	
	
	


}
