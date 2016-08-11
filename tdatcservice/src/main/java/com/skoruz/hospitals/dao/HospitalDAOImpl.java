package com.skoruz.hospitals.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.skoruz.hospitals.entity.Hospital;
import com.skoruz.hospitals.entity.HospitalBranches;
import com.skoruz.hospitals.entity.HospitalBranchesDTO;

@Repository
public class HospitalDAOImpl implements HospitalDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Hospital> fetchAllHospitals() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Hospital> hospitals = session.createQuery("from Hospital").list();
		return hospitals;
	}


	@Override
	public Hospital getHospital(int hospitalId) {
		Session session = this.sessionFactory.getCurrentSession();
		Hospital hospital = (Hospital)session.get(Hospital.class, hospitalId);
		return hospital;
	}
	
	
	@Override
	public int addHospital(Hospital hospital) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(hospital);
		return i;
	}
	
	@Override
	public HospitalBranches getHospitalBranch(int branchId) {
		Session session = this.sessionFactory.getCurrentSession();
		HospitalBranches branches = (HospitalBranches) session.get(HospitalBranches.class, branchId);
		return branches;
	}
	
	@Override
	public HospitalBranches getBranchDetails(int branchId) {
		Session session = this.sessionFactory.getCurrentSession();
		HospitalBranches branch = (HospitalBranches) session.get(HospitalBranches.class, branchId);
		return branch;
	}

	@Override
	public HospitalBranchesDTO getBranch(int branchId) {
		Session session = this.sessionFactory.getCurrentSession();
		HospitalBranchesDTO branches = (HospitalBranchesDTO) session.get(HospitalBranchesDTO.class, branchId);
		return branches;
	}
	
	@Override
	public String updateHospitalBranchDetails(HospitalBranches hospitalBranches) {
		Session session = this.sessionFactory.getCurrentSession();
		//session.update(hospitalBranches);
		 try
	       {
			 
			 session.update(hospitalBranches);
	          // return "true";
			 return "Hospital Branches information updated successfully";
	       }
	       catch(Exception ex)
	       {
	           ex.printStackTrace();
	           return "Hospital Branches information not updated successfully";
	       }
		
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	

	
}
