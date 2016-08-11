package com.skoruz.pharmacy.service;

import java.util.List;

import com.skoruz.pharmacy.entity.Pharmacy;
import com.skoruz.pharmacy.entity.PharmacyBranches;

public interface PharmacyService {
	
	public List<Pharmacy> fetchAllPharmacies();
	public Pharmacy getPharmacy(int pharmacyId);
	public int addPharmacy(Pharmacy pharmacy);
	public Pharmacy getBranchPharmacyDetails(int id);
	public PharmacyBranches getBranchDetails(int branchId);


}
