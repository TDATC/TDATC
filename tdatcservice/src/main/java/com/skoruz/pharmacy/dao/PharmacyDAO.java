package com.skoruz.pharmacy.dao;

import java.util.List;

import com.skoruz.pharmacy.entity.Pharmacy;
import com.skoruz.pharmacy.entity.PharmacyBranches;

public interface PharmacyDAO {
	
	public List<Pharmacy> fetchAllPharmacies();
	public Pharmacy getPharmacy(int pharmacyId);
	public int addPharmacy(Pharmacy pharmacy);
	public PharmacyBranches getPharmacyBranch(int id);
	public PharmacyBranches getBranchDetails(int branchId);

}
