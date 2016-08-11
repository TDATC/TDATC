package com.skoruz.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skoruz.pharmacy.dao.PharmacyDAO;
import com.skoruz.pharmacy.entity.Pharmacy;
import com.skoruz.pharmacy.entity.PharmacyBranches;

@Service
public class PharmacyServiceImpl implements PharmacyService{

	@Autowired
	PharmacyDAO pharmacyDao;
	
	@Override
	@Transactional
	public List<Pharmacy> fetchAllPharmacies() {
		List<Pharmacy> pharmacies = pharmacyDao.fetchAllPharmacies();
		return pharmacies;
	}

	@Override
	@Transactional
	public Pharmacy getPharmacy(int pharmacyId) {
		Pharmacy pharmacy = (Pharmacy)pharmacyDao.getPharmacy(pharmacyId);
		return pharmacy;
	}
	
	
	@Override
	@Transactional
	public int addPharmacy(Pharmacy pharmacy) {
		return pharmacyDao.addPharmacy(pharmacy);
	}
	
	@Override
	@Transactional
	public Pharmacy getBranchPharmacyDetails(int id) {
		PharmacyBranches branches = pharmacyDao.getPharmacyBranch(id);
		return branches.getPharmacy();
	}


	@Override
	@Transactional
	public PharmacyBranches getBranchDetails(int branchId) {
		PharmacyBranches branch = pharmacyDao.getBranchDetails(branchId);
		return branch;
	}


	/*public PharmacyDAO getPharmacyDAO() {
		return pharmacyDao;
	}

	public void setPharmacyDAO(PharmacyDAO pharmacyDao) {
		this.pharmacyDao = pharmacyDao;
	}*/

	
}
