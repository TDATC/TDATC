package com.skoruz.hospitals.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skoruz.hospitals.dao.HospitalDAO;
import com.skoruz.hospitals.entity.Hospital;
import com.skoruz.hospitals.entity.HospitalBranches;
import com.skoruz.hospitals.entity.HospitalBranchesDTO;

@Service
public class HospitalServiceImpl implements HospitalServices {

	@Autowired
	HospitalDAO hospitalDao;
	
	@Override
	@Transactional
	public List<Hospital> fetchAllHospitals() {
		List<Hospital> hospitals = hospitalDao.fetchAllHospitals();
		return hospitals;
	}


	@Override
	@Transactional
	public Hospital getHospital(int hospitalId) {
		Hospital hospital = hospitalDao.getHospital(hospitalId);
		return hospital;
	}
	

	@Override
	@Transactional
	public int addHospital(Hospital hospital) {
		int  id = hospitalDao.addHospital(hospital);
		return id;
	}

	@Override
	@Transactional
    public Hospital getHospitalDetails(int branchId){
	       HospitalBranches branches = hospitalDao.getHospitalBranch(branchId);
	       return branches.getHospital();
    }
	
	@Override
	@Transactional
	public HospitalBranches getBranchDetails(int branchId) {
		HospitalBranches branch = hospitalDao.getBranchDetails(branchId);
		return branch;
	}
	
	@Override
	@Transactional
	public HospitalBranchesDTO getBranch(int branchId) {
		HospitalBranchesDTO branchs = hospitalDao.getBranch(branchId);
		return branchs;
	}


	@Override
	@Transactional
	public String updateHospitalBranchDetails(HospitalBranches hospitalBranches) {
		return hospitalDao.updateHospitalBranchDetails(hospitalBranches);
	}


	public HospitalDAO getHospitalDao() {
		return hospitalDao;
	}

	public void setHospitalDao(HospitalDAO hospitalDao) {
		this.hospitalDao = hospitalDao;
	}




}
