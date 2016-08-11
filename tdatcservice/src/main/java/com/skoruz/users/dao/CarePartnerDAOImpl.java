package com.skoruz.users.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.skoruz.users.entity.PatientCarePartner;

@Repository
public class CarePartnerDAOImpl implements CarePartnerDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PatientCarePartner> getCarePartnerPatientDetails(int patientCarePartnerId) {
	   Session session = this.sessionFactory.getCurrentSession();
	  Query query = session.createQuery("from PatientCarePartner pc where patientCarePartnerId="+patientCarePartnerId);
	  List<PatientCarePartner> partnerList = query.list();
		return partnerList;
	}
	


}
