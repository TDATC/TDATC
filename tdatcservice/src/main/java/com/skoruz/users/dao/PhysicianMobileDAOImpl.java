package com.skoruz.users.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.skoruz.users.entity.PhysicianAvailability;
import com.skoruz.users.entity.PhysicianDetails;

@Repository
public class PhysicianMobileDAOImpl implements PhysicianMobileDAO{
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	@Override
	public int savePhysicianDetails(PhysicianDetails physicianDetails) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(physicianDetails);
		return i;
	}
	
	@Override
	public List<PhysicianDetails> getPhysicianDetails(String searchCriteria) {
	Session session = this.sessionFactory.getCurrentSession();
		
	Criteria criteria = session.createCriteria(PhysicianDetails.class);
	criteria.add(
			Restrictions.or(
					Restrictions.or(Restrictions.like("firstName", searchCriteria+"%"),Restrictions.like("emailAddress",searchCriteria+"%")),
					Restrictions.or(Restrictions.like("Specilization",searchCriteria+"%"),Restrictions.like("Branch",searchCriteria+"%"))
					)
			);
	
	@SuppressWarnings("unchecked")
	List<PhysicianDetails> physicianList = criteria.list();
	
		return physicianList;
	}

	@Override
	public int addPhysicianAvailability(PhysicianAvailability availability) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(availability);
		return i;
		
	}
	
	@Override
	public PhysicianAvailability getPhysicianAvailability(int physicainId) {
		Session session = this.sessionFactory.getCurrentSession();
		PhysicianAvailability availability = (PhysicianAvailability) session.get(PhysicianAvailability.class, physicainId);
		return availability;
	}
	
	@SuppressWarnings("unchecked")
	 @Override
	 public PhysicianDetails getPhysicianDetails(String emailAddress,String password){
	  Session session = this.sessionFactory.getCurrentSession();
	  Query query = session.createQuery("from User where emailAddress='"+emailAddress+"' and password='"+password+"'");
	  List<PhysicianDetails> physicianList = query.list();
	  
	  if(physicianList.size()>0){
		   return physicianList.get(0);
		  } else{
		  return null;
		  }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PhysicianDetails> getAllPhysicians() {
		Session session=this.sessionFactory.getCurrentSession();
		Query query =session.createQuery("from PhysicianDetails");
		List<PhysicianDetails> physicianDetails=query.list();
		if(physicianDetails.size()>0){
			return physicianDetails;
		}else{
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	  @Override
	  public PhysicianDetails getPhysicianDetails(int physicianId) {
	   Session session = this.sessionFactory.getCurrentSession();
	   Query query = session.createQuery("from PhysicianDetails where id="+physicianId+"");
	   List<PhysicianDetails> physicianList = query.list();
	   
	   if(physicianList.size()>0){
	     return physicianList.get(0);
	    } else{
	    return null;
	    }
	 }
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<PhysicianDetails> getAllMappedPhysicians(int id){
		ArrayList<PhysicianDetails> details=new ArrayList<PhysicianDetails>();
		  Session session=sessionFactory.getCurrentSession();
		  /*List<PatientPhysicianMap> list=session.createQuery("from PatientPhysicianMap pm where pm.patientId.id=:id").setParameter("id",id).list();*/
		  
		  ArrayList<Integer>  list=(ArrayList<Integer>) session.createSQLQuery("select physician_id from patientphysicianmap  where patient_id="+id+"").list();
		  //list.setParameter("id" ,id);
		  
		  if (list.size()>0) {
		   for (int i = 0; i < list.size(); i++) {
		    PhysicianDetails physicianDetails=getPhysicianDetails(list.get(i));
		    details.add(physicianDetails);
		   }
		  }

		  return details;
		 }

	@Override
	public int physicianSignUp(PhysicianDetails physiciandetails) {
		Session session = this.sessionFactory.getCurrentSession();
		int  i=(Integer) session.save(physiciandetails);
		return i;
	}
	
	@Override
	public String physicianProfileUpdate(PhysicianDetails physicianDetails) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(physicianDetails);
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PhysicianDetails> getSpecializedPhysicianDetails(String specialization) {
		Session session = this.sessionFactory.getCurrentSession();
	Query query  = session.createQuery("from PhysicianDetails where specializations.specializations='"+specialization+"' ");
	List<PhysicianDetails> physicianDetails =	query.list();
	
	if(physicianDetails.size()> 0){
		return physicianDetails;
	} else{
		return null;
	}
	}
	
	
	


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public int deleteMappedPhysician(int id, Map<String, String> doctorIdMap) {
		String string=doctorIdMap.get("doctors_to_delete");
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete from PatientPhysicianMap  where patientId.patient_id="+id+" and physicianId.physician_id IN ("+string+")");
		//Foo.executeQuery("select p from p where p.bar in (:mapObj)", [mapObj: aMap.keySet().toList()])
		int result = query.executeUpdate();
		
	     return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhysicianDetails> getAllPhysicians(int patientId) {
		Session session=this.sessionFactory.getCurrentSession();
		//Query query =session.createQuery("from  where physician_id="+patientId+" ");
		List<PhysicianDetails> details;
		
		ArrayList<Integer>  list=(ArrayList<Integer>) session.createSQLQuery("select physician_id from PatientPhysicianMap  where patient_id="+patientId+"").list();
		
		if(list.size() > 0){

			String hql = "from  PhysicianDetails where physician_id NOT IN (:names)";
			Query query = session.createQuery(hql);
			query.setParameterList("names", list);
			details=query.list();
		} else{
			details=session.createQuery("from  PhysicianDetails").list();
		}
	 
		//Query query =session.createQuery("from  PhysicianDetails where physician_id NOT IN "+list+" ");
		
		
		if(details.size()>0){
			
			
			return details;
		}else{
			return null;
		}
	}

	
	

	

	
	

	

	

}
