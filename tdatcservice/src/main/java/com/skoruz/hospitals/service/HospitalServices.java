package com.skoruz.hospitals.service;

import java.util.List;

import com.skoruz.hospitals.entity.Hospital;
import com.skoruz.hospitals.entity.HospitalBranches;
import com.skoruz.hospitals.entity.HospitalBranchesDTO;


public interface HospitalServices {
	
	public List<Hospital> fetchAllHospitals();
	public Hospital getHospital(int hospitalId);
	public int addHospital(Hospital hospital);
	public Hospital getHospitalDetails(int branchId);
	public HospitalBranches getBranchDetails(int branchId);
	public String updateHospitalBranchDetails(HospitalBranches hospitalBranches);
	public HospitalBranchesDTO getBranch(int branchId);
}
