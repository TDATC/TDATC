package com.skoruz.users.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.skoruz.users.entity.Affliation;
import com.skoruz.users.entity.Allergies;
import com.skoruz.users.entity.Country;
import com.skoruz.users.entity.HospitalAdministrator;
import com.skoruz.users.entity.Location;
import com.skoruz.users.entity.MedicationProcedure;
import com.skoruz.users.entity.Medicine;
import com.skoruz.users.entity.PatientAllergyDetails;
import com.skoruz.users.entity.PatientAppointmentDetails;
import com.skoruz.users.entity.PatientBodyDetails;
import com.skoruz.users.entity.PatientCarePartner;
import com.skoruz.users.entity.PatientDetails;
import com.skoruz.users.entity.PatientDetailsBodyDTO;
import com.skoruz.users.entity.PatientDetailsDietaryAcidDTO;
import com.skoruz.users.entity.PatientDetailsDietaryDTO;
import com.skoruz.users.entity.PatientDetailsDietaryFatDTO;
import com.skoruz.users.entity.PatientDetailsEnergyBloodBodyDTO;
import com.skoruz.users.entity.PatientDetailsVitaminDTO;
import com.skoruz.users.entity.PatientDietaryAcidDetails;
import com.skoruz.users.entity.PatientDietaryDetails;
import com.skoruz.users.entity.PatientDietaryFatDetails;
import com.skoruz.users.entity.PatientEnergyBloodBodyDetails;
import com.skoruz.users.entity.PatientMeasurementToolsDetails;
import com.skoruz.users.entity.PatientMedicationDetails;
import com.skoruz.users.entity.PatientPhysicianMap;
import com.skoruz.users.entity.PatientVitaminDetails;
import com.skoruz.users.entity.PharmacyAdministrator;
import com.skoruz.users.entity.PhysicianDetailsAppointmentDTO;
import com.skoruz.users.entity.PreferredHospital;
import com.skoruz.users.entity.PreferredPharmacy;
import com.skoruz.users.entity.PreferredPhysician;
import com.skoruz.users.entity.Qualification;
import com.skoruz.users.entity.SavePdf;
import com.skoruz.users.entity.Specialization;
import com.skoruz.users.entity.User;



@Repository
public class PatientDAOImpl implements PatientDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	private final Logger logger = LoggerFactory.getLogger(PatientDAOImpl.class);
	 
	 

		@Override
		public List<PatientDetails> getAllPatients() {
			
			// TODO Auto-generated method stub
			logger.info("get all patients");
			Session session=this.sessionFactory.getCurrentSession();
			List<PatientDetails> patients = session.createQuery("from PatientDetails").list();
			return patients;
		}


	/*@SuppressWarnings("unchecked")
	@Override
	public List<PatientDetails> getAllPatients() {
	
	
	Session session1 =null;
	List<PatientDetails>  patients=null;
	try{
		session1 = this.sessionFactory.openSession();
  patients = session1.createQuery("from PatientDetails").list();
	logger.info("PatienDetails loaded successfully.");
	}catch(HibernateException e){
		
		System.out.println(e.getMessage());
		
	}finally {
		session1.close();
	}
	return patients;
	}
	*/
	
	
	
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
	
	
	
		
		

	
		
	


	@Override
	public PatientDetails getPatientDetails(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		PatientDetails patientDetails = (PatientDetails)session.get(PatientDetails.class, id);
		if(patientDetails == null){
			patientDetails = (PatientDetails) session.createQuery("from PatientDetails where user.user_id = "+id+" ").uniqueResult();
		}
		logger.info("Patient details loaded successfully");
		
		return patientDetails;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllAdministrators() {
		Session session=null;
		session = this.sessionFactory.getCurrentSession();
		List<User> adminList = session.createQuery("from User where  user_type  in ('A','PHA','HA','PT','PHS','PCP')").list();
	
		return adminList;
		
	}
	
	@Override
	public void updatePatientDetails(PatientDetails patientDetails) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(patientDetails);
	}


	@Override
	public int savePatientDetails(PatientDetails patientDetails) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(patientDetails);
		/*User user=patientDetails.getUser();
		user.setUser_id(i);
		patientDetails.setUser(user);
		int j=(Integer) session.save(patientDetails);*/
		return i;
	}

	
	@Override
	public HospitalAdministrator getHospitalAdmin(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		HospitalAdministrator admin = (HospitalAdministrator) session.get(HospitalAdministrator.class, id);
		return admin;
	}
	
	@Override
	public PharmacyAdministrator getPharmacyAdministrator(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		PharmacyAdministrator admin = (PharmacyAdministrator) session.get(PharmacyAdministrator.class, id);
		return admin;
	}
	
	@Override
	public int savePatientCarePartner(PatientCarePartner patientCarePartner) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(patientCarePartner);
		return i;
	}

	@Override
	public int addPreferredPhysician(PreferredPhysician preferredPhysician) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(preferredPhysician);
		return i;
	}

	@Override
	public int addPreferredHospital(PreferredHospital preferredHospital) {
		Session session = this.sessionFactory.getCurrentSession();
	   int i = (Integer) session.save(preferredHospital);
		return i;
	}

	@Override
	public int addPreferredPharmacy(PreferredPharmacy preferredPharmacy) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(preferredPharmacy);
		return i;
       		
	}

	/*@Override
	public void updatePatientProfile() {
		// TODO Auto-generated method stub
		
	}
	*/
	@Override
	public int patientDietaryDetails(PatientDietaryDetails dietaryDetails) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(dietaryDetails);
		return i;
	}

	@Override
	public int addPatientDietaryFatDetails(PatientDietaryFatDetails fatDetails) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(fatDetails);
		return i;
		
	}
	
	@Override
	public int addPatientDietaryAcidDetails(PatientDietaryAcidDetails dietaryAcidDetails) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(dietaryAcidDetails);
		return i;
		
	}

	@Override
	public int addPatientVitaminDetails(PatientVitaminDetails vitaminDetails) {
		Session session = this.sessionFactory.getCurrentSession();
	    int i = (Integer) session.save(vitaminDetails);
		return i;
		
	}
	
	@Override
	public int addPatientBodyDetails(PatientBodyDetails bodyDetails) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(bodyDetails);
		return i;
		
	}
	
	@Override
	public PatientBodyDetails getBodyDetails(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		PatientBodyDetails bodyDetails = (PatientBodyDetails) session.get(PatientBodyDetails.class, id);
		return bodyDetails;
	}
	
	@Override
	public PatientDetailsBodyDTO getPatientBodyDetails(int bodyId) {
		Session session = this.sessionFactory.getCurrentSession();
	  //  PatientBodyDetails bodyDetails  = (PatientBodyDetails) session.get(PatientBodyDetails.class, bodyId);
	    PatientDetailsBodyDTO bodyDetails = (PatientDetailsBodyDTO) session.get(PatientDetailsBodyDTO.class, bodyId);
	    //System.out.println(bodyDetails1.getId()+"    "+bodyDetails1.getBodyDetails().getHeight());
		return bodyDetails;
	}
	
	@Override
	public PatientVitaminDetails getvitaminDetails(int vitaminId) {
		Session session = this.sessionFactory.getCurrentSession();
		PatientVitaminDetails vitaminDetails = (PatientVitaminDetails) session.get(PatientVitaminDetails.class, vitaminId);
		return vitaminDetails;
	}

	@Override
	public PatientDetailsVitaminDTO getPatientVitamindetails(int vitaminId) {
		Session session = this.sessionFactory.getCurrentSession();
		PatientDetailsVitaminDTO detailsVitaminDTO = (PatientDetailsVitaminDTO) session.get(PatientDetailsVitaminDTO.class, vitaminId);
		return detailsVitaminDTO;
	}

	@Override
	public PatientDietaryAcidDetails getDietaryAcidDetails(int acidId) {
		Session session = this.sessionFactory.getCurrentSession();
		PatientDietaryAcidDetails acidDetails = (PatientDietaryAcidDetails) session.get(PatientDietaryAcidDetails.class, acidId);
		return acidDetails;
	}

	@Override
	public PatientDetailsDietaryAcidDTO getPatientDietaryAcidDetails(int acidId) {
		Session session = this.sessionFactory.getCurrentSession();
		PatientDetailsDietaryAcidDTO acidDTO = (PatientDetailsDietaryAcidDTO) session.get(PatientDetailsDietaryAcidDTO.class, acidId);
		return acidDTO;
	}
	
	@Override
	public PatientDietaryFatDetails getDietaryFatDetails(int fatId) {
		Session session = this.sessionFactory.getCurrentSession();
		PatientDietaryFatDetails fatDetails = (PatientDietaryFatDetails) session.get(PatientDietaryFatDetails.class, fatId);
		return fatDetails;
	}

	@Override
	public PatientDetailsDietaryFatDTO getPatientDietaryFatDetails(int fatId) {
		Session session = this.sessionFactory.getCurrentSession();
		PatientDetailsDietaryFatDTO fatDTO = (PatientDetailsDietaryFatDTO) session.get(PatientDetailsDietaryFatDTO.class, fatId);
		return fatDTO;
	}

	@Override
	public PatientDietaryDetails getDietaryDetails(int dietId) {
		Session session = this.sessionFactory.getCurrentSession();
		PatientDietaryDetails dietaryDetails = (PatientDietaryDetails) session.get(PatientDietaryDetails.class, dietId);
		return dietaryDetails;
	}

	@Override
	public PatientDetailsDietaryDTO getPatientDietaryDetails(int dietId) {
		Session session = this.sessionFactory.getCurrentSession();
		PatientDetailsDietaryDTO detailsDietaryDTO = (PatientDetailsDietaryDTO) session.get(PatientDetailsDietaryDTO.class, dietId);
		return detailsDietaryDTO;
	}

	@Override
	public int addPatientMedicationDetails(PatientMedicationDetails medicationDetails ) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(medicationDetails);
		return i;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PatientMedicationDetails> getPatientMedicationDetails(int id,String currentDateTime) {
		String currentString=currentDateTime.replace("_", " ");
		Session session = this.sessionFactory.getCurrentSession();
		List<PatientMedicationDetails> medicationDetails = session.createQuery("from PatientMedicationDetails pm  where pm.patientId="+id+" and pm.fromDate <= '"+currentString+"' and pm.toDate >= '"+currentString+"' order by insertedDate desc").list();
		
		if(medicationDetails.size()>0){
		return medicationDetails;
		} else{
			return null;
		}
	}
	
	@Override
	public PatientMedicationDetails getMedicationDetails(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		PatientMedicationDetails medicationDetails = (PatientMedicationDetails) session.get(PatientMedicationDetails.class, id);
		return medicationDetails;
	}
	
	@Override
	public int addPatientAppointmentDetails(PatientAppointmentDetails appointmentDetails) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(appointmentDetails);
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getPatientAppointmentdetails(int appointmentId) {
		Session session = this.sessionFactory.getCurrentSession();
		Map<String,Object> appointmentDetails=new HashMap<String,Object>();
		//PatientAppointmentDetails appointmentDetails = (PatientAppointmentDetails) session.get(PatientAppointmentDetails.class, appointmentId);
	    List<Object[]> list=session.createSQLQuery("select patient_id,physician_id,timings,date,appointment_description,status from patient_appointments where patient_id=:id")
	    		          .setParameter("id", appointmentId).list();
	    Iterator<Object[]> obIt=list.iterator();
	    while(obIt.hasNext()){
	    	Object[] i=obIt.next();
	    	appointmentDetails.put("patientId",i[0]);
	    	appointmentDetails.put("physicianId",i[1]);
	    	appointmentDetails.put("timings",i[2]);
	    	appointmentDetails.put("date",i[3]);
	    	appointmentDetails.put("appointment_description",i[4]);
	    	appointmentDetails.put("status",i[5]);
	    	
	    }
		//PatientAppointmentDetails session.createQuery("from PatientAppointmentDetails PA where  PA.id=:appointmentId ").list();
		
		return appointmentDetails;
	}

	@Override
	public PhysicianDetailsAppointmentDTO getPatientPhysicianAppointmentdetails(int physicianId) {
		Session session = this.sessionFactory.getCurrentSession();
		PhysicianDetailsAppointmentDTO appointmentDetails = (PhysicianDetailsAppointmentDTO) session.get(PhysicianDetailsAppointmentDTO.class, physicianId);
		return appointmentDetails;
	}
	
	@Override
	public Object getPatientPatientAppointmentdetails(int patientId){
		Session session = this.sessionFactory.getCurrentSession();
		PatientDetails appointmentDetails = (PatientDetails) session.get(PatientDetails.class, patientId);
		//PatientDetailsAppointmentDTO appointmentDTO = (PatientDetailsAppointmentDTO) session.get(PatientDetailsAppointmentDTO.class, patientId);
		return appointmentDetails;
		//return appointmentDTO;
	}
	
	@Override
	public int addPatientEnergyBloodBodyDetails(PatientEnergyBloodBodyDetails bloodBodyDetails) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(bloodBodyDetails);
		return i;
	}
	
	@Override
	public PatientDetailsEnergyBloodBodyDTO getPatientEnergyBloodBodydetails(int bloodBodyDetailsId) {
		Session session = this.sessionFactory.getCurrentSession();
		PatientDetailsEnergyBloodBodyDTO energyBloodBodyDTO = (PatientDetailsEnergyBloodBodyDTO) session.get(PatientDetailsEnergyBloodBodyDTO.class, bloodBodyDetailsId);
		return energyBloodBodyDTO;
	}

	@Override
	public int addPatientMeasurementToolsDetails(PatientMeasurementToolsDetails patientMeasurementToolsDetails ) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(patientMeasurementToolsDetails);
		return i;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PatientMeasurementToolsDetails> getPatientMeasurementToolsDetails(int measurementToolId, int patientId) {
		Session session = this.sessionFactory.getCurrentSession();
		
		List<PatientMeasurementToolsDetails> measurementToolsDetails = session.createQuery("from PatientMeasurementToolsDetails pm where pm.measurementToolId ="+measurementToolId +"and pm.patientId="+patientId+" order by pm.dateTime DESC").list();
		
		if(measurementToolsDetails.size()>0){
			return measurementToolsDetails;
		}
		else{
			return null;
		}
		//PatientMeasurementToolsDetails measurementToolsDetails = (PatientMeasurementToolsDetails) session.get(PatientMeasurementToolsDetails.class, measurementToolId);
	
	}
	
	@Override
	public int deletePatientMeasurementToolsDetails(int Id) {
		Session session = this.sessionFactory.getCurrentSession();
	    Query query =  session.createQuery("delete from PatientMeasurementToolsDetails where id ="+Id+"");
		int result = query.executeUpdate();
	   
	    return result;
	}

	@Override
	public String updatePatientImg(String filePath, int id) {
		//Session session = this .sessionFactory.openSession();
		//session.beginTransaction();
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update User u set u.photoPath=:photopath where u.user_id=:id ");
		query.setParameter("photopath",filePath);
		query.setParameter("id", id);
		query.executeUpdate();
		//session.getTransaction().commit();
		//session.close();
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public User userDetails(String emailAddress, String password) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query =  session.createQuery("from User u where u.emailAddress='"+emailAddress+"' and u.password='"+password+"'");
		List< User> userList = query.list();
		if(userList.size()>0){
			
		return userList.get(0);
		} else{
			return null;
		}
		
	}
	
	@Override
	public String updatePatientProfile(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		try{
	    session.update(user);
		return "Successfully Updated";
		}
		catch(Exception ex){
			ex.printStackTrace();
	           return " not updated successfully";
		}
		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Medicine> getMedicine() {
		Session session = this.sessionFactory.getCurrentSession();
		//List<Medicine> medicinesList= getHibernateTemplate().find("from " + clazz.getName());
		List<Medicine> medicines = session.createQuery("from Medicine").list();
		
		if(medicines.size()>0){
			return medicines;
		} else{
			return null;
		}
	}
	
	@Override
	public int addMedicine(Medicine medicine) {
		Session session = this.sessionFactory.getCurrentSession();
		int i = (Integer) session.save(medicine);
		return i;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Allergies> getAllergies() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Allergies> Allergies = session.createQuery("from Allergies").list();
		
		if(Allergies.size()>0){
			return Allergies;
		} else{
			return null;
		}
	}
	
	@Override
	public int savePatientAllergyDetails(PatientAllergyDetails patientAllergyDetails) {
		Session session=this.sessionFactory.getCurrentSession();
		int  i=(Integer) session.save(patientAllergyDetails);
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientAllergyDetails> getPatientAllergyDetails(int patientID) {
		Session session = this.sessionFactory.getCurrentSession();
	List<PatientAllergyDetails> allergyDetails = session.createQuery("from PatientAllergyDetails where patientId="+patientID+"").list();
		
	     if(allergyDetails.size()>0){
		    return allergyDetails;
	       } else{
	              return null;
	            }
	        }
	

	@SuppressWarnings("unchecked")
	@Override
	public List<MedicationProcedure> getMedicationProcedure() {
		Session session = this.sessionFactory.getCurrentSession();
	List<MedicationProcedure> medicationProcedure	= session.createQuery("from MedicationProcedure").list();
	
	if(medicationProcedure.size()>0){
		return medicationProcedure;
	} else{
		return null;
	}
	}
	
    // ****************NOT USED ANYMORE************
	/*@SuppressWarnings("unchecked")
	@Override
	public String getMedicationRemainder(Boolean status) {
		Session session = this.sessionFactory.getCurrentSession();
		List<MedicationRemainder> MedicationRemainder = session.createQuery("from MedicationRemainder where status='"+status+"' ").list();
		Iterator<MedicationRemainder> iterator = MedicationRemainder.iterator();
		while(iterator.hasNext()){
		MedicationRemainder remainder = (MedicationRemainder) iterator.next();
		System.out.println(remainder.getStatus());
		
		MedicationRemainder remainder2 = new MedicationRemainder();
		
		 boolean boolresult=false;
		 boolean sts;
		 sts = remainder.getStatus();
		 boolresult = sts;
		 
		 if(boolresult){
			 System.out.println("Successfully Mail send. Seq. No.:" + rs.getInt("Seq_No") + "\tTO(" + To_no + ")\tMessage:" + msgs);
				lStrSQL = "UPDATE tbsms_txn SET Status='Y', LU_Dt=SysDate() WHERE Seq_No =" + rs.getInt("Seq_No");
				if ( stmt.executeUpdate( lStrSQL ) > 0 )
				System.out.println("Successfully Status(Success) Changed.");
				else
				System.out.println("Un-Successfully Status(Success) Changed.");
			 
			 System.out.println("Successfully message sent.Id :"+ remainder2.getId());
			Query query =  session.createQuery("update MedicationRemainder set status=: status,lastUpdatedDate=SysDate() where id=1");
			 query.setParameter("status", status);
			int result = query.executeUpdate();
			
			if(result > 0)
			    System.out.println("Successfully Status(Success) Changed.");
				//return "Successfully Status(Success) Changed.";
				else
				System.out.println("Un-Successfully Status(Success) Changed.");
					//return "Un-Successfully Status(Success) Changed.";
			
			}
		
			}
		return "success";
			 
		}*/
	
	@Override
	public int savePDF(SavePdf savePDF) {
		Session session = this.sessionFactory.getCurrentSession();
		int  i = (Integer) session.save(savePDF);
		return i;
		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<SavePdf> getPDFDetails(int patientId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<SavePdf> pdf = session.createQuery("from SavePdf where patientId="+patientId+"").list();
		
		if(pdf.size()>0){
			return pdf;
		} else{
		return null;
		}
	}
	
	

	/*@SuppressWarnings("unchecked")
	@Override
	public List<Object> getRemainder(int patientId) {
		List<Object> list = new ArrayList<Object>();
		
		Session session = this .sessionFactory.getCurrentSession();
		List<PatientMedicationDetails> patientMedicationDetails = session.createQuery("from PatientMedicationDetails where patientId="+patientId+"").list();
		List<SavePdf> SavePdf = session.createQuery("from SavePdf where patientId="+patientId+"").list();
		List<PatientAppointmentDetails> patientAppointmentDetails = session.createQuery("from PatientAppointmentDetails where patientId="+patientId+"").list();
		//for (Object obj : list) {
		    if (obj.getClass() == String.class) {
		        System.out.println("I found a string :- " + obj);
		        //return obj;
		    }
		if(patientMedicationDetails.size()>0) {
			list.add(patientMedicationDetails);
		}
		if (SavePdf.size()>0) {
			list.add(SavePdf);
		}
		if (patientAppointmentDetails.size()>0) {
			list.add(patientAppointmentDetails);
			
		}
		return list;
		}
*/
	

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientMedicationDetails> getMedicationRemainder(int patientId,int readUnread) {
		Session session = this.sessionFactory.getCurrentSession();
		List<PatientMedicationDetails> medicationremainder = session.createQuery("from PatientMedicationDetails where patientId="+patientId+" and readUnread="+readUnread+" ").list();
		
		if(medicationremainder.size()>0){
		return medicationremainder;
		} else{
			return null;
		}
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<SavePdf> getSavePdfRemainder(int patientId,int readUnread) {
		Session session = this.sessionFactory.getCurrentSession();
		List<SavePdf> savepdfdetails = session.createQuery("from SavePdf where patientId="+patientId+" and readUnread="+readUnread+" ").list();
		
		if(savepdfdetails.size()>0){
			return savepdfdetails;
		} else{
			return null;
		}
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PatientAppointmentDetails> getPatientAppointmentRemainder(int patientId,int readUnread) {
		Session session = this.sessionFactory.getCurrentSession();
		List<PatientAppointmentDetails> patientAppointmentDetails = session.createQuery("from PatientAppointmentDetails where patientId="+patientId+" and readUnread="+readUnread+" ").list();
		
		if(patientAppointmentDetails.size()>0){
			return patientAppointmentDetails;
		} else{
			return null;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Qualification> getQualification() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Qualification> qualifications = session.createQuery("from Qualification").list();
		
		if(qualifications.size()>0){
			return qualifications;
		} else{
		return null;
		}
	}


	@Override
	public String patientMedicationUpdateStatus(PatientMedicationDetails medicationDetails) {
		Session session = this.sessionFactory.getCurrentSession();
		/*String hql = "UPDATE PatientMedicationDetails set status = :pmstatus "  + 
	                 "WHERE patientId = :patientId";
	    Query query = session.createQuery(hql);
	    query.setParameter("pmstatus", status);
	    query.setParameter("patientId", patientId);
	    int result = query.executeUpdate();
	    return result;
		*/
		/*session.update(medicationDetails);
		return "success";*/
		
		try{
			session.update(medicationDetails);
			return "Successfully Updated";
			}
			catch(Exception ex){
				ex.printStackTrace();
		           return " not updated successfully";
			}
	}
	

	

	@Override
	public int patientLocationUpdate(int userId,double longitude, double lattitude) {
		Session  session  =this.sessionFactory.getCurrentSession();
		/*String hql = "UPDATE User u SET " +
	               "u.longitude =:userlongitude" +
	               "u.latitude =:userlatitude " +
	               "WHERE u.user_id =:patientId";*/
		String sql = "UPDATE users SET longitude = "+ longitude +" , latitude="+lattitude+" WHERE user_id = "+ userId + "";
		Query  query=session.createSQLQuery(sql);
		int  result = query.executeUpdate();
		
		if(result > 0){
		System.out.println("No of rows updtaed : "+result);
		return  result;
		} else{
			return 0;
		}
	}
	
	@Override
	public String patientAppointmentUpdateStatus(PatientAppointmentDetails appointmentDetails) {
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(appointmentDetails);
			return "Successfully Updated";
			}
			catch(Exception ex){
				ex.printStackTrace();
		           return " not updated successfully";
			}
	}
	
	@Override
	public String patientSavePdfUpdateStatus(SavePdf savePdf){
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.update(savePdf);
			return "Successfully Updated";
			}
			catch(Exception ex){
				ex.printStackTrace();
		           return " not updated successfully";
			}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Affliation> getAffiliation() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Affliation> affliations = session.createQuery("from Affliation").list();
		
		if(affliations.size() > 0){
			System.out.println("List of rows selected : "+affliations.size());
			return affliations;
		} else{
			return null;
		}
		
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Location> getLocation() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Location> locations = session.createQuery("from Location").list();
		
		if(locations.size()>0){
			System.out.println("No of rows selected : "+locations.size());
			return locations;
		} else{
		return null;
		}
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Specialization> getSpecialization() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Specialization> specializations = session.createQuery("from Specialization").list();
		
		if(specializations.size()>0){
			System.out.println("No of rows selected : "+specializations.size());
		return specializations;
		} else{
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	 @Override
	 public Map<String,Object> getForgetPassword(String userName) {
	  
	  Map<String,Object> userDetails=new HashMap<String,Object>();
	  User user=null;
	  Session session = this.sessionFactory.getCurrentSession();
	  
	  String queryString="from User where emailAddress ='"+userName+"'";
	  
	  String querypsd="select password  where  emailAddress='"+userName+ "'";
	  
	  user  =(User) session.createQuery(queryString).uniqueResult();
	  if(user != null){
	   userDetails.put("userDetails",user);
	   userDetails.put("status","valid");
	  }else{
	   userDetails.put("status","invalid");
	  }
	  
	     return userDetails;

	}

	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override
	public int savePatientPhysician(int patientId, int physicianId) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		/*Query query=session.createQuery("insert  into PatientPhysicianMap   where  physicianId.physician_id='"+physicianId+"'  and patientId.patient_id='"+patientId +"'  ");*/
		
		
		int i= session.createSQLQuery("INSERT INTO patientphysicianmap(patient_id, physician_id) VALUES ( "+patientId+","+physicianId+" )").executeUpdate();
		      
		
		
		return i;
		
		/*if(result > 0){
			return result;
		} else{
			return 0;
		}*/
	}


	@Override

	public int savePatientPhysicianObject(PatientPhysicianMap patientphysicianmap) {
		// TODO Auto-generated method stub
		Session  session=this.sessionFactory.getCurrentSession();
		
		int  i=(Integer) session.save(patientphysicianmap);
				
		return i;
	}


	@Override
	public int deletePatientPhysicianMap(int patientid, int physicianid) {
		// TODO Auto-generated method stub
		Session  session=this.sessionFactory.getCurrentSession();
		
		int  i=session.createSQLQuery("delete from  patientphysicianmap where patient_id="+patientid+" and physician_id="+physicianid+" ").executeUpdate();
		
		return i;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Country> getCountryCodes(String countrycode) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
	/*	List<CountryCodes> list=session.createQuery("from CountryCodes where countrycode in"+countrycode+"'").list();*/
	Query query=session.createQuery("from CountryCodes where countrycode LIKE:countrycode");
		List<Country> countryList = query.setParameter("countrycode", countrycode + "%").list();
		return countryList;
	}








	
	
	

	
	
	
	
}
