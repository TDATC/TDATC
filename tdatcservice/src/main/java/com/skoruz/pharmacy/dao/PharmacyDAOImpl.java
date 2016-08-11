package com.skoruz.pharmacy.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.skoruz.pharmacy.entity.Pharmacy;
import com.skoruz.pharmacy.entity.PharmacyBranches;

@Repository
public class PharmacyDAOImpl implements PharmacyDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Pharmacy> fetchAllPharmacies() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Pharmacy> pharmacies = session.createQuery("from Pharmacy").list();
		return pharmacies;
	}
	

	@Override
	public Pharmacy getPharmacy(int pharmacyId) {
		Session session = this.sessionFactory.getCurrentSession();
		Pharmacy pharmacy = (Pharmacy)session.get(Pharmacy.class, pharmacyId);
		return pharmacy;
	}
	

	@Override
	public int addPharmacy(Pharmacy pharmacy) {
		Session session = this.sessionFactory.getCurrentSession();
		int id = (Integer) session.save(pharmacy);
		return id;
	}
	

	@Override
	public PharmacyBranches getPharmacyBranch(int id) {
		Session session = this.sessionFactory.getCurrentSession();
	    PharmacyBranches branch	= (PharmacyBranches) session.get(PharmacyBranches.class, id);
		return branch;
	}
	
	@Override
	public PharmacyBranches getBranchDetails(int branchId) {
		Session session = this.sessionFactory.getCurrentSession();
		PharmacyBranches branch = (PharmacyBranches) session.get(PharmacyBranches.class, branchId);
		return branch;
	}


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}



}
