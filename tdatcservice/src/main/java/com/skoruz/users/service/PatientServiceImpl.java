package com.skoruz.users.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*import org.springframework.transaction.annotation.Transactional;*/



import com.skoruz.common.SendMail;
import com.skoruz.users.dao.PatientDAO;
import com.skoruz.users.entity.AccountState;
import com.skoruz.users.entity.Affliation;
import com.skoruz.users.entity.Allergies;
import com.skoruz.users.entity.Country;
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
import com.skoruz.users.entity.PreferredHospital;
import com.skoruz.users.entity.PreferredPharmacy;
import com.skoruz.users.entity.PreferredPhysician;
import com.skoruz.users.entity.Qualification;
import com.skoruz.users.entity.SavePdf;
import com.skoruz.users.entity.Specialization;
import com.skoruz.users.entity.User;


@Service
public class PatientServiceImpl implements PatientService{
	
	@Autowired
	PatientDAO patientDao; 


	@Override
	@Transactional
	public List<PatientDetails> getAllPatients() {
		// TODO Auto-generated method stub
		List<PatientDetails> patients = patientDao.getAllPatients();
		return patients;
	}
	
	@Override
	@Transactional
	public PatientDetails getPatientDetails(int id) {
		
		// TODO Auto-generated method stub
		PatientDetails patientDetails = patientDao.getPatientDetails(id);
		return patientDetails;
		
		
	}

	
	@Override
	@Transactional
	public void updatePatientStatus(int patientId, boolean status) {
		// TODO Auto-generated method stub
		PatientDetails patientDetails = patientDao.getPatientDetails(patientId);
		
		AccountState accountState=new AccountState();
		if(status){
		
			patientDetails.getUser().setAccountState("ACTIVE");
		}else{
		
			patientDetails.getUser().setAccountState("INACTIVE");
		}
		
		//TODO update the updated object
		patientDao.updatePatientDetails(patientDetails);
		
	}
	
	@Override
	@Transactional
	public int addPatientDetails(PatientDetails patientDetails) {
	int i =	patientDao.savePatientDetails(patientDetails);
		return i;
	}
	
	@Override
	@Transactional
	public int addPatientCarePartner(PatientCarePartner patientCarePartner) {
		int i = patientDao.savePatientCarePartner(patientCarePartner);
		return i;
	}

	@Override
	@Transactional
	public int addPreferredPhysician(PreferredPhysician preferredPhysician) {
		int i = patientDao.addPreferredPhysician(preferredPhysician);
		return i;
	}
	
	@Override
	@Transactional
	public int addPreferredHospital(PreferredHospital preferredHospital) {
        int i = patientDao.addPreferredHospital(preferredHospital);
		return i;		
	}

	@Override
	@Transactional
	public int addPreferredPharmacy(PreferredPharmacy preferredPharmacy) {
		int i = patientDao.addPreferredPharmacy(preferredPharmacy);
		return i;
	}
	
	@Override
	@Transactional
	public int patientDietaryDetails(PatientDietaryDetails dietaryDetails) {
		int i = patientDao.patientDietaryDetails(dietaryDetails);
		return i;
	}

	@Override
	@Transactional
	public int addPatientDietaryFatDetails(PatientDietaryFatDetails fatDetails) {
		int i = patientDao.addPatientDietaryFatDetails(fatDetails);
		return i;
	}

	@Override
	@Transactional
	public int addPatientDietaryAcidDetails(PatientDietaryAcidDetails dietaryAcidDetails ) {
		int i = patientDao.addPatientDietaryAcidDetails(dietaryAcidDetails);
		return i;
		
	}

	@Override
	@Transactional
	public int addPatientVitaminDetails(PatientVitaminDetails vitaminDetails) {
		int i = patientDao.addPatientVitaminDetails(vitaminDetails);
		return i;
		
	}

	@Override
	@Transactional
	public int addPatientBodyDetails(PatientBodyDetails bodyDetails) {
		return patientDao.addPatientBodyDetails(bodyDetails);
	}

	@Override
	@Transactional
	public PatientBodyDetails getBodyDetails(int id) {
		PatientBodyDetails bodyDetails = patientDao.getBodyDetails(id);
		return bodyDetails;
	}

	@Override
	@Transactional
	public PatientDetailsBodyDTO getPatientBodyDetails(int bodyId) {
		PatientDetailsBodyDTO bodyDetails = patientDao.getPatientBodyDetails(bodyId);
		return bodyDetails;
	}

	@Override
	@Transactional
	public PatientVitaminDetails getVitaminDetails(int vitaminId) {
		PatientVitaminDetails vitaminDetails = patientDao.getvitaminDetails(vitaminId);
		return vitaminDetails;
	}

	@Override
	@Transactional
	public PatientDetailsVitaminDTO getPatientVitaminDetails(int vitaminId) {
	PatientDetailsVitaminDTO detailsVitaminDTO = patientDao.getPatientVitamindetails(vitaminId);
		return detailsVitaminDTO;
	}
	
	@Override
	@Transactional
	public PatientDietaryAcidDetails getDietaryAcidDetails(int acidId) {
		PatientDietaryAcidDetails acidDetails = patientDao.getDietaryAcidDetails(acidId);
		return acidDetails;
	}
	
	@Override
	@Transactional
	public PatientDetailsDietaryAcidDTO getPatientDietaryAcidDetails(int acidId) {
	PatientDetailsDietaryAcidDTO acidDTO =	patientDao.getPatientDietaryAcidDetails(acidId);
		return acidDTO;
	}

	@Override
	@Transactional
	public PatientDietaryFatDetails getDietaryFatDetails(int fatId) {
		PatientDietaryFatDetails fatDetails = patientDao.getDietaryFatDetails(fatId);
		return fatDetails;
	}

	@Override
	@Transactional
	public PatientDetailsDietaryFatDTO getPatientDietaryFatDetails(int fatId) {
		PatientDetailsDietaryFatDTO fatDetails = patientDao.getPatientDietaryFatDetails(fatId);
		return fatDetails;
	}

	@Override
	@Transactional
	public PatientDietaryDetails getDietaryDetails(int dietId) {
	PatientDietaryDetails dietaryDetails = patientDao.getDietaryDetails(dietId);
		return dietaryDetails;
	}

	@Override
	@Transactional
	public PatientDetailsDietaryDTO getPatientDietaryDetails(int dietId) {
		PatientDetailsDietaryDTO dietaryDetails = patientDao.getPatientDietaryDetails(dietId);
		return dietaryDetails;
	}

	@Override
	@Transactional
	public int addPatientMedicationDetails(PatientMedicationDetails medicationDetails ) {
		int i = patientDao.addPatientMedicationDetails(medicationDetails);
		return i;
	}
	
	@Override
	@Transactional
	public List<PatientMedicationDetails> getPatientMedicationDetails(int id,String currentDateTime) {
		List<PatientMedicationDetails> medicationDetails = patientDao.getPatientMedicationDetails(id,currentDateTime);
		return medicationDetails;
	}

	@Override
	@Transactional
	public PatientMedicationDetails getMedicationDetails(int id) {
		PatientMedicationDetails medicationDetails = patientDao.getMedicationDetails(id);
		return medicationDetails;
	}
	
	@Override
	@Transactional
	public int addPatientAppointmentDetails(PatientAppointmentDetails appointmentDetails) {
		int i = patientDao.addPatientAppointmentDetails(appointmentDetails);
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<String, Object> getPatientAppointmentdetails(int appointmentId) {
		Map<String,Object> det=new HashMap<String, Object>();
		     det.put("patientAppDet",patientDao.getPatientAppointmentdetails(appointmentId));
		     Map<String,Object> val=(Map<String, Object>) det.get("patientAppDet");
		     det.put("physicianDet", patientDao.getPatientPhysicianAppointmentdetails(Integer.parseInt(val.get("physicianId").toString()))); 
		     det.put("patientDet", patientDao.getPatientPatientAppointmentdetails(Integer.parseInt(val.get("patientId").toString())));
           //System.out.println(appointmentDetails.getPatientDetailsAppointmentDTO().getId())
		return det;
	}

	/*@Override
	@Transactional
	public PhysicianDetailsAppointmentDTO getPatientPhysicianAppointmentdetails(
			int appointmentId) {
		//PatientAppointmentDetails appointmentDetails = patientDao.getPatientPhysicianAppointmentdetails(appointmentId);
		return null; // appointmentDetails.getPhysicianDetailsAppointmentDTO();
	}
	*/
	
	@Override
	@Transactional
	public int addPatientEnergyBloodBodyDetails(PatientEnergyBloodBodyDetails bloodBodyDetails) {
		int i = patientDao.addPatientEnergyBloodBodyDetails(bloodBodyDetails);
		return i;
	}
	
	@Override
	@Transactional
	public PatientDetailsEnergyBloodBodyDTO getPatientEnergyBloodBodydetails(int bloodBodyDetailsId) {
		PatientDetailsEnergyBloodBodyDTO energyBloodBodyDTO = patientDao.getPatientEnergyBloodBodydetails(bloodBodyDetailsId);
		return energyBloodBodyDTO;
	}

	@Override
	@Transactional
	public int addPatientMeasurementToolsDetails(PatientMeasurementToolsDetails patientMeasurementToolsDetails) {
		int i = patientDao.addPatientMeasurementToolsDetails(patientMeasurementToolsDetails);
		return i;
	}
	
	@Override
	@Transactional
	public List<PatientMeasurementToolsDetails> getPatientMeasurementToolsDetails(int measurementToolId, int patientId) {
		List<PatientMeasurementToolsDetails> measurementToolsDetails = patientDao.getPatientMeasurementToolsDetails(measurementToolId, patientId);
		return measurementToolsDetails;
		}
	
	@Override
	@Transactional
	public int deletePatientMeasurementToolsDetails(int Id) {
		int i  = patientDao.deletePatientMeasurementToolsDetails(Id);
		return i;
	}
	
	@Override
	@Transactional
	public String updatePatientImg(String filePath, int id) {
		return patientDao.updatePatientImg(filePath, id);
	}

	@Override
	@Transactional
	public User userDetails(String emailAddress, String password) {
	       User user = patientDao.userDetails(emailAddress, password);
		return user;
	}
	
	@Override
	@Transactional
	public String updatePatientProfile(User user) {
		return patientDao.updatePatientProfile(user);
	}
	

	@Override
	@Transactional
	public List<Medicine> getMedicine() {
	List<Medicine> medicines = patientDao.getMedicine();
		return medicines;
	}
	
	@Override
	@Transactional
	public int addMedicine(Medicine medicine) {
		int i = patientDao.addMedicine(medicine);
		return i;
	}
	
	@Override
	@Transactional
	public List<Allergies> getAllergies() {
	List<Allergies> allergies = patientDao.getAllergies();
		return allergies;
	}
	
	@Override
	@Transactional
	public int savePatientAllergyDetails(PatientAllergyDetails patientAllergyDetails) {
		 int i = patientDao.savePatientAllergyDetails(patientAllergyDetails);
		return i;
	}
	
	@Override
	@Transactional
	public List<PatientAllergyDetails> getPatientAllergyDetails(int patientID) {
	List<PatientAllergyDetails> allergyDetails = patientDao.getPatientAllergyDetails(patientID);
		return allergyDetails;
	}
	

	@Override
	@Transactional
	public List<MedicationProcedure> getMedicationProcedure() {
	List<MedicationProcedure> medicationProcedure = patientDao.getMedicationProcedure();
		return medicationProcedure;
	}
	
	/*@Override
	@Transactional
	public String getMedicationRemainder(Boolean status) {
		return patientDao.getMedicationRemainder(status);
		
	}*/
	
	@Override
	@Transactional
	public int savePDF(SavePdf savePDF) {
		int i = patientDao.savePDF(savePDF);
	    return i;
	}
	
	 @Override
	 @Transactional
	 public List<SavePdf> getPDFDetails(int patientId) {
	  List<SavePdf> list=patientDao.getPDFDetails(patientId) ;
	  return  list;
	 }
	 
	 /*@Override
	 @Transactional
	 public List<Object> getRemainder(int patientId) {
		 List<Object> objects = patientDao.getRemainder(patientId);
		 return objects;

	 }*/

	 @Override
	 @Transactional
	 public List<PatientMedicationDetails> getMedicationRemainder(int patientId,int readUnread) {
		 List<PatientMedicationDetails> medicationremainder = patientDao.getMedicationRemainder(patientId,readUnread);
		 return medicationremainder;
	 }

	 @Override
	 @Transactional
	 public List<SavePdf> getSavePdfRemainder(int patientId,int readUnread) {
		 List<SavePdf> savepdfdetails = patientDao.getSavePdfRemainder(patientId,readUnread);
		 return savepdfdetails;
	 }

	 @Override
	 @Transactional
	 public List<PatientAppointmentDetails> getPatientAppointmentRemainder(int patientId,int readUnread) {
		 List<PatientAppointmentDetails> patientappointments=patientDao.getPatientAppointmentRemainder(patientId,readUnread);
		 return patientappointments;
	 }

	 @Override
	 @Transactional
	 public List<Qualification> getQualification() {
		 List<Qualification> qualification = patientDao.getQualification();
		 return qualification;
	 }
	 
	 @Override
	 @Transactional
	 public String patientMedicationUpdateStatus(PatientMedicationDetails medicationDetails) {
		return  patientDao.patientMedicationUpdateStatus(medicationDetails);
		}
	 
	
	 
	 @Override
	 @Transactional
	 public int patientLocationUpdate(int userId ,double longitude, double lattitude) {
		 int  i=patientDao.patientLocationUpdate(userId,longitude,lattitude);
		 return i;
	 }
	 
	 @Override
	 @Transactional
	 public String patientAppointmentUpdateStatus(PatientAppointmentDetails appointmentDetails) {
		return  patientDao.patientAppointmentUpdateStatus(appointmentDetails);
		}

	 @Override
	 @Transactional
	 public String patientSavePdfUpdateStatus(SavePdf savePdf) {
		return  patientDao.patientSavePdfUpdateStatus(savePdf);
		}
	 
	 @Override
	 @Transactional
	 public List<Affliation> getAffiliation() {
		 List<Affliation> affliation	= patientDao.getAffiliation();
		 return affliation;
	 }
	 
	 @Override
	 @Transactional
		public List<Location> getLocation() {
		List<Location> location =  patientDao.getLocation();
			return location;
		}
	 
	 @Override
	 @Transactional
		public List<Specialization> getSpecialization() {
		List<Specialization> specialization = patientDao.getSpecialization();
			return specialization;
		}
	 
	 @Override
	 @Transactional
	 public String getForgetPassword(String userName) { 
	  Map<String, Object> userDetails=patientDao.getForgetPassword(userName);
	  String result = null;
	  boolean msgStatus;
	  if((userDetails.get("status").toString()).equalsIgnoreCase("valid")){
	   
	   User user=(User) userDetails.get("userDetails");
	   msgStatus=SendMail.sendingMail(user.getEmailAddress(),"RE:TDATC","Hi "+user.getFirstName()+" Your password : "+user.getPassword());
	   if(msgStatus){
	     // result="Password has been sent to your email Id";
		   result = "Success";
	    }else{
	      result="Please enter again";
	    }
	   }else{
	   //result="Invalid user id";
		   result = "Failure";
	   }
	  return result;
	 }
	 
	 
	 

	
	@Override
	@Transactional
	public int savePatientPhysician(int patientId, int physicianId) {
		// TODO Auto-generated method stub
		
		int i=patientDao.savePatientPhysician(patientId,physicianId);
		return i;
	}

	@Override
	@Transactional
	public int savePatientPhysicianObject(PatientPhysicianMap patientphysicianmap) {
		// TODO Auto-generated method stub
		
		int  i=patientDao.savePatientPhysicianObject(patientphysicianmap);
		return i;
	}

	@Override
	@Transactional
	public int deletePatientPhysicianMap(int patientid, int physicianid) {
		// TODO Auto-generated method stub
		
		int  i=patientDao.deletePatientPhysicianMap(patientid,physicianid);
		return i;
	}

	@Override
	@Transactional
	public List<Country> getCountryCodes(String countrycode) {
		// TODO Auto-generated method stub
		List<Country> list=patientDao.getCountryCodes(countrycode);
		
		return list;
	}



	
	
	
	
	
	
}
