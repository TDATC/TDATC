package com.skoruz.users.dao;

import java.util.ArrayList;
import java.util.List;

import com.skoruz.users.entity.PhysicianAvailability;
import com.skoruz.users.entity.PhysicianDetails;
import com.skoruz.users.entity.User;

import java.util.Map;

public interface PhysicianDAO {
	
	public int savePhysicianDetails(PhysicianDetails physicianDetails);
	public List<User> getPhysicianDetails(String searchCriteria);
	public int addPhysicianAvailability(PhysicianAvailability availability);
	public PhysicianAvailability getPhysicianAvailability(int physicainId);
	public PhysicianDetails getPhysicianDetails(String emailAddress,String password);
	public List<PhysicianDetails> getAllPhysicians(int patientId);
	public ArrayList<PhysicianDetails> getAllMappedPhysicians(int id);
	public PhysicianDetails getPhysicianDetails(int physicianId);
	public int physicianSignUp(PhysicianDetails physiciandetails);
	public String physicianProfileUpdate(PhysicianDetails physicianDetails);
	
	public int deleteMappedPhysician(int id,Map<String, String> doctorIdMap);
	
	


}
