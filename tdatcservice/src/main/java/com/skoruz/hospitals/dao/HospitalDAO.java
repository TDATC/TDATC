package com.skoruz.hospitals.dao;

import java.util.List;

import com.skoruz.hospitals.entity.Hospital;
import com.skoruz.hospitals.entity.HospitalBranches;
import com.skoruz.hospitals.entity.HospitalBranchesDTO;

public interface HospitalDAO {

	public List<Hospital> fetchAllHospitals();
	public Hospital getHospital(int hospitalId);
	public int addHospital(Hospital hospital);
	public HospitalBranches getHospitalBranch(int branchId);
	public HospitalBranches getBranchDetails(int branchId);
	public HospitalBranchesDTO getBranch(int branchId);
	public String updateHospitalBranchDetails(HospitalBranches hospitalBranches);
}
