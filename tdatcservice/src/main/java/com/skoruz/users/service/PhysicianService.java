package com.skoruz.users.service;

import java.util.List;
import java.util.Map;

import com.skoruz.users.entity.PhysicianAvailability;
import com.skoruz.users.entity.PhysicianDetails;
import com.skoruz.users.entity.User;

public interface PhysicianService {
	
	public int addPhysicianDetails(PhysicianDetails physicianDetails );
    public  List<User> getPhysicianDetails(String searchCriteria);
    public int addPhysicianAvailability(PhysicianAvailability availability );
    public PhysicianDetails getPhysicianAvailability(int physicianId);
    public PhysicianDetails getPhysicianDetails(String emailAddress,String password);
    public PhysicianDetails getPhysicianDetail(int id);
	public List<PhysicianDetails> getAllPhysicianList(int patientId);
	public List<PhysicianDetails> getMappedPhysicians(int id);
	public int physicianSignUp(PhysicianDetails physiciandetails);
	public String physicianProfileUpdate(PhysicianDetails physicianDetails);
	public int deleteMappedPhysician(int id, Map<String, String> doctorIdMap);
	
	
	
	
}
