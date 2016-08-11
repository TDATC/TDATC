package com.skoruz.users.service;

import java.util.List;


import com.skoruz.users.dao.PhysicianDAO;
import com.skoruz.users.entity.PhysicianAvailability;
import com.skoruz.users.entity.PhysicianDetails;
import com.skoruz.users.entity.User;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PhysicianServiceImpl implements PhysicianService {
	
	@Autowired
    PhysicianDAO physicianDao;

	@Override
	@Transactional
	public int addPhysicianDetails(PhysicianDetails PhysicianDetails) {
		int i = physicianDao.savePhysicianDetails(PhysicianDetails);
		return i;
	}
	
	@Override
	@Transactional
	public List<User> getPhysicianDetails(String searchCriteria) {
		 List<User> user = physicianDao.getPhysicianDetails(searchCriteria);
		 return user;
	}

	@Override
	@Transactional
	public int addPhysicianAvailability(PhysicianAvailability availability ) {
		int i = physicianDao.addPhysicianAvailability(availability);
		return i;
	}

	@Override
	@Transactional
	public PhysicianDetails getPhysicianAvailability(int physicianId) {
		PhysicianAvailability availability = physicianDao.getPhysicianAvailability(physicianId);		
		return availability.getPhysicianDetails();
	}
	
	@Override
	@Transactional
	public PhysicianDetails getPhysicianDetails(String emailAdress,String password) {
		PhysicianDetails physicianDetails = physicianDao.getPhysicianDetails(emailAdress,password);
		return physicianDetails;
	}
	
	@Override
	@Transactional
	public PhysicianDetails getPhysicianDetail(int id) {
		PhysicianDetails physicianDetails = physicianDao.getPhysicianDetails(id);
		return physicianDetails;
	}
	
	@Override
	@Transactional
	public List<PhysicianDetails> getAllPhysicianList(int patientId) {
		List<PhysicianDetails> physicianDetails=physicianDao.getAllPhysicians(patientId);
		return physicianDetails;
	}
	

	
	
	@Override
	@Transactional
	public List<PhysicianDetails> getMappedPhysicians(int id) {
		List<PhysicianDetails> physicianDetails=physicianDao.getAllMappedPhysicians(id);
		return physicianDetails;
	}
	
	 @Override
	 @Transactional
	 public int physicianSignUp(PhysicianDetails physiciandetails) {
	  int i = physicianDao.physicianSignUp(physiciandetails);
	  return i;
	 }
	 
	 @Override
	 @Transactional
	 public String physicianProfileUpdate(PhysicianDetails physicianDetails) {
		return physicianDao.physicianProfileUpdate(physicianDetails);

	 }
	 
	 
	 @Override
	 @Transactional
	 public int deleteMappedPhysician(int id,Map<String, String> doctorIdMap) {
		int i =  physicianDao.deleteMappedPhysician(id, doctorIdMap);
		return i;

	 }
	 

}
