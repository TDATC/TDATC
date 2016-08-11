package com.skoruz.users.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.skoruz.users.entity.PhysicianAvailability;
import com.skoruz.users.entity.PhysicianDetails;
import com.skoruz.users.entity.User;

@Repository
public class PhysicianDAOImpl implements PhysicianDAO{
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	@Override
	public int savePhysicianDetails(PhysicianDetails physicianDetails) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(physicianDetails);
		return i;
	}
	
	

	/*@Override
	public int savePhysicianDetails(PhysicianDetails physicianDetails) {
	//	Session session = this.sessionFactory.getCurrentSession();
		Session session =null;
		int i=0;
		try{
		session = this.sessionFactory.openSession();
		i = (Integer) session.save(physicianDetails);
		}catch(HibernateException e){
			System.out.println(e.getMessage());
		}finally {
			session.close();
		}
		return i;
	}*/
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getPhysicianDetails(String searchCriteria) {
	Session session = this.sessionFactory.getCurrentSession();
		
	/*Criteria criteria = session.createCriteria(PhysicianDetails.class);
	criteria.add(
				Restrictions.like("user.user_id",25+"%")
			);
	
	,Restrictions.like("emailAddress",""+"%"),Restrictions.like("user_type",searchCriteria+"%"))
	Restrictions.or(Restrictions.like("Specilization",searchCriteria+"%"),Restrictions.like("Branch",searchCriteria+"%"))
	Restrictions.or(Restrictions.like("Specilization",searchCriteria+"%"),Restrictions.like("Branch",searchCriteria+"%"))*/
	
	@SuppressWarnings("unchecked")
	List<PhysicianDetails> physicianList = null;//= session.createQuery("from PhysicianDetails id where user.firstName like '%ra%'").list();
	 
	List<User> user=session.createQuery("from User us where us.firstName like :firstname and user_type=:user_type  ")
			        .setParameter("firstname","%"+searchCriteria+"%")
			        
			        .setParameter("user_type","PHS").list();
		return user;
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
	  Query query = session.createQuery("from PhysicianDetails where user.emailAddress='"+emailAddress+"' and user.password='"+password+"'");
	  List<PhysicianDetails> physicianList = query.list();
	  
	  if(physicianList.size()>0){
		   return physicianList.get(0);
		  } else{
		  return null;
		  }
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
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
	
	
	@SuppressWarnings("unchecked")
	  @Override
	  public PhysicianDetails getPhysicianDetails(int physicianId) {
	   Session session = this.sessionFactory.getCurrentSession();
	   Query query = session.createQuery("from PhysicianDetails where physician_id="+physicianId+"");
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
		  
		  ArrayList<Integer>  list=(ArrayList<Integer>) session.createSQLQuery("select physician_id from PatientPhysicianMap  where patient_id="+id+"").list();
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
		try
	       {
			 
			 session.merge(physicianDetails);
			 
	          // return "true";
			 return "Hospital Branches information updated successfully";
	       }
	       catch(Exception ex)
	       {
	           ex.printStackTrace();
	           return "Hospital Branches information not updated successfully";
	       }
		
		//session.update(physicianDetails);
		//return "success";
	}
	
	
	@Override
	public int deleteMappedPhysician(int id,Map<String, String> doctorIdMap) {
	
		String string=doctorIdMap.get("doctors_to_delete");
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete from PatientPhysicianMap  where patientId.patient_id="+id+" and physicianId.physician_id IN ("+string+")");
		//Foo.executeQuery("select p from p where p.bar in (:mapObj)", [mapObj: aMap.keySet().toList()])
		int result = query.executeUpdate();
		
	     return result;
	}




		
}
