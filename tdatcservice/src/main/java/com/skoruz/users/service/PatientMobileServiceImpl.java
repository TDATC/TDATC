
package com.skoruz.users.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skoruz.users.dao.PatientMobileDAO;
import com.skoruz.users.entity.AccountState;
import com.skoruz.users.entity.Affliation;
import com.skoruz.users.entity.Allergies;
import com.skoruz.users.entity.City;
import com.skoruz.users.entity.Clinic;
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
import com.skoruz.users.entity.PatientVitaminDetails;
import com.skoruz.users.entity.PhysicianAvailability;
import com.skoruz.users.entity.PhysicianDetails;
import com.skoruz.users.entity.PreferredHospital;
import com.skoruz.users.entity.PreferredPharmacy;
import com.skoruz.users.entity.PreferredPhysician;
import com.skoruz.users.entity.Qualification;
import com.skoruz.users.entity.SavePdf;
import com.skoruz.users.entity.Specialization;
import com.skoruz.users.entity.User;


@Service
public class PatientMobileServiceImpl implements PatientMobileService{
	
	@Autowired
	PatientMobileDAO patientMobileDao; 


	@Override
	@Transactional
	public List<PatientDetails> getAllPatients() {
		// TODO Auto-generated method stub
		List<PatientDetails> patients = patientMobileDao.getAllPatients();
		return patients;
	}
	
	@Override
	@Transactional
	public PatientDetails getPatientDetails(int id) {
		// TODO Auto-generated method stub
		PatientDetails patientDetails = patientMobileDao.getPatientDetails(id);
		return patientDetails;
	}
	
	@Override
	@Transactional
	public PhysicianDetails getPhysicianDetails(int id) {
		// TODO Auto-generated method stub
		PhysicianDetails physicianDetails = patientMobileDao.getPhysicianDetails(id);
		return physicianDetails;
	}

	
	@SuppressWarnings("unused")
	@Override
	@Transactional
	public void updatePatientStatus(int patientId, boolean status) {
		// TODO Auto-generated method stub
		PatientDetails patientDetails = patientMobileDao.getPatientDetails(patientId);
		
		AccountState accountState=new AccountState();
		if(status){
			
			
			patientDetails.getUser().setAccountState("ACTIVE");
		}else{
			
			
			patientDetails.getUser().setAccountState("INACTIVE");
		}
		
		//TODO update the updated object
		patientMobileDao.updatePatientDetails(patientDetails);
		
	}
	
	@Override
	@Transactional
	public int addPatientDetails(PatientDetails patientDetails) {
	int i =	patientMobileDao.savePatientDetails(patientDetails);
		return i;
	}
	
	@Override
	@Transactional
	public int addPatientCarePartner(PatientCarePartner patientCarePartner) {
		int i = patientMobileDao.savePatientCarePartner(patientCarePartner);
		return i;
	}

	@Override
	@Transactional
	public int addPreferredPhysician(PreferredPhysician preferredPhysician) {
		int i = patientMobileDao.addPreferredPhysician(preferredPhysician);
		return i;
	}
	
	@Override
	@Transactional
	public int addPreferredHospital(PreferredHospital preferredHospital) {
        int i = patientMobileDao.addPreferredHospital(preferredHospital);
		return i;		
	}

	@Override
	@Transactional
	public int addPreferredPharmacy(PreferredPharmacy preferredPharmacy) {
		int i = patientMobileDao.addPreferredPharmacy(preferredPharmacy);
		return i;
	}
	
	@Override
	@Transactional
	public int patientDietaryDetails(PatientDietaryDetails dietaryDetails) {
		int i = patientMobileDao.patientDietaryDetails(dietaryDetails);
		return i;
	}

	@Override
	@Transactional
	public int addPatientDietaryFatDetails(PatientDietaryFatDetails fatDetails) {
		int i = patientMobileDao.addPatientDietaryFatDetails(fatDetails);
		return i;
	}

	@Override
	@Transactional
	public int addPatientDietaryAcidDetails(PatientDietaryAcidDetails dietaryAcidDetails ) {
		int i = patientMobileDao.addPatientDietaryAcidDetails(dietaryAcidDetails);
		return i;
		
	}

	@Override
	@Transactional
	public int addPatientVitaminDetails(PatientVitaminDetails vitaminDetails) {
		int i = patientMobileDao.addPatientVitaminDetails(vitaminDetails);
		return i;
		
	}

	@Override
	@Transactional
	public int addPatientBodyDetails(PatientBodyDetails bodyDetails) {
		return patientMobileDao.addPatientBodyDetails(bodyDetails);
	}

	@Override
	@Transactional
	public PatientBodyDetails getBodyDetails(int id) {
		PatientBodyDetails bodyDetails = patientMobileDao.getBodyDetails(id);
		return bodyDetails;
	}

	@Override
	@Transactional
	public PatientDetailsBodyDTO getPatientBodyDetails(int bodyId) {
		PatientDetailsBodyDTO bodyDetails = patientMobileDao.getPatientBodyDetails(bodyId);
		return bodyDetails;
	}

	@Override
	@Transactional
	public PatientVitaminDetails getVitaminDetails(int vitaminId) {
		PatientVitaminDetails vitaminDetails = patientMobileDao.getvitaminDetails(vitaminId);
		return vitaminDetails;
	}

	@Override
	@Transactional
	public PatientDetailsVitaminDTO getPatientVitaminDetails(int vitaminId) {
	PatientDetailsVitaminDTO detailsVitaminDTO = patientMobileDao.getPatientVitamindetails(vitaminId);
		return detailsVitaminDTO;
	}
	
	@Override
	@Transactional
	public PatientDietaryAcidDetails getDietaryAcidDetails(int acidId) {
		PatientDietaryAcidDetails acidDetails = patientMobileDao.getDietaryAcidDetails(acidId);
		return acidDetails;
	}
	
	@Override
	@Transactional
	public PatientDetailsDietaryAcidDTO getPatientDietaryAcidDetails(int acidId) {
	PatientDetailsDietaryAcidDTO acidDTO =	patientMobileDao.getPatientDietaryAcidDetails(acidId);
		return acidDTO;
	}

	@Override
	@Transactional
	public PatientDietaryFatDetails getDietaryFatDetails(int fatId) {
		PatientDietaryFatDetails fatDetails = patientMobileDao.getDietaryFatDetails(fatId);
		return fatDetails;
	}

	@Override
	@Transactional
	public PatientDetailsDietaryFatDTO getPatientDietaryFatDetails(int fatId) {
		PatientDetailsDietaryFatDTO fatDetails = patientMobileDao.getPatientDietaryFatDetails(fatId);
		return fatDetails;
	}

	@Override
	@Transactional
	public PatientDietaryDetails getDietaryDetails(int dietId) {
	PatientDietaryDetails dietaryDetails = patientMobileDao.getDietaryDetails(dietId);
		return dietaryDetails;
	}

	@Override
	@Transactional
	public PatientDetailsDietaryDTO getPatientDietaryDetails(int dietId) {
		PatientDetailsDietaryDTO dietaryDetails = patientMobileDao.getPatientDietaryDetails(dietId);
		return dietaryDetails;
	}

	@Override
	@Transactional
	public int addPatientMedicationDetails(PatientMedicationDetails medicationDetails ) {
		int i = patientMobileDao.addPatientMedicationDetails(medicationDetails);
		return i;
	}
	
	@Override
	@Transactional
	public List<PatientMedicationDetails> getPatientMedicationDetails(int id,String currentDateTime) {
		List<PatientMedicationDetails> medicationDetails = patientMobileDao.getPatientMedicationDetails(id,currentDateTime);
		return medicationDetails;
	}

	@Override
	@Transactional
	public PatientMedicationDetails getMedicationDetails(int id) {
		PatientMedicationDetails medicationDetails = patientMobileDao.getMedicationDetails(id);
		return medicationDetails;
	}
	
	@Override
	@Transactional
	public int addPatientAppointmentDetails(PatientAppointmentDetails appointmentDetails) {
		int i = patientMobileDao.addPatientAppointmentDetails(appointmentDetails);
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<String, Object> getPatientAppointmentdetails(int appointmentId) {
		Map<String,Object> det=new HashMap<String, Object>();
		     det.put("patientAppDet",patientMobileDao.getPatientAppointmentdetails(appointmentId));
		     Map<String,Object> val=(Map<String, Object>) det.get("patientAppDet");
		     det.put("physicianDet", patientMobileDao.getPatientPhysicianAppointmentdetails(Integer.parseInt(val.get("physicianId").toString()))); 
		     det.put("patientDet", patientMobileDao.getPatientPatientAppointmentdetails(Integer.parseInt(val.get("patientId").toString())));
           //System.out.println(appointmentDetails.getPatientDetailsAppointmentDTO().getId())
		return det;
	}

	/*@Override
	@Transactional
	public PhysicianDetailsAppointmentDTO getPatientPhysicianAppointmentdetails(
			int appointmentId) {
		//PatientAppointmentDetails appointmentDetails = patientDAO.getPatientPhysicianAppointmentdetails(appointmentId);
		return null; // appointmentDetails.getPhysicianDetailsAppointmentDTO();
	}
	*/
	
	@Override
	@Transactional
	public int addPatientEnergyBloodBodyDetails(PatientEnergyBloodBodyDetails bloodBodyDetails) {
		int i = patientMobileDao.addPatientEnergyBloodBodyDetails(bloodBodyDetails);
		return i;
	}
	
	@Override
	@Transactional
	public PatientDetailsEnergyBloodBodyDTO getPatientEnergyBloodBodydetails(int bloodBodyDetailsId) {
		PatientDetailsEnergyBloodBodyDTO energyBloodBodyDTO = patientMobileDao.getPatientEnergyBloodBodydetails(bloodBodyDetailsId);
		return energyBloodBodyDTO;
	}

	@Override
	@Transactional
	public int addPatientMeasurementToolsDetails(PatientMeasurementToolsDetails patientMeasurementToolsDetails) {
		int i = patientMobileDao.addPatientMeasurementToolsDetails(patientMeasurementToolsDetails);
		return i;
	}
	
	@Override
	@Transactional
	public List<PatientMeasurementToolsDetails> getPatientMeasurementToolsDetails(int measurementToolId, int patientId) {
		List<PatientMeasurementToolsDetails> measurementToolsDetails = patientMobileDao.getPatientMeasurementToolsDetails(measurementToolId, patientId);
		return measurementToolsDetails;
		}
	
	@Override
	@Transactional
	public int deletePatientMeasurementToolsDetails(int Id) {
		int i  = patientMobileDao.deletePatientMeasurementToolsDetails(Id);
		return i;
	}
	
	@Override
	@Transactional
	public String updatePatientImg(String filePath, int id) {
		return patientMobileDao.updatePatientImg(filePath, id);
	}

	@Override
	@Transactional
	public User userDetails(String emailAddress, String password) {
	       User user = patientMobileDao.userDetails(emailAddress, password);
		return user;
	}
	
	@Override
	@Transactional
	public String isUserExists(String emailAddress, String password) {
	       String user = patientMobileDao.isUserExists(emailAddress, password);
		return user;
	}
	
	@Override
	@Transactional
	public String updatePatientProfile(User user) {
		return patientMobileDao.updatePatientProfile(user);
	}
	

	@Override
	@Transactional
	public List<Medicine> getMedicine() {
	List<Medicine> medicines = patientMobileDao.getMedicine();
		return medicines;
	}
	
	@Override
	@Transactional
	public int addMedicine(Medicine medicine) {
		int i = patientMobileDao.addMedicine(medicine);
		return i;
	}
	
	@Override
	@Transactional
	public List<Allergies> getAllergies() {
	List<Allergies> allergies = patientMobileDao.getAllergies();
		return allergies;
	}
	
	@Override
	@Transactional
	public int savePatientAllergyDetails(PatientAllergyDetails patientAllergyDetails) {
		 int i = patientMobileDao.savePatientAllergyDetails(patientAllergyDetails);
		return i;
	}
	
	@Override
	@Transactional
	public List<PatientAllergyDetails> getPatientAllergyDetails(int patientID) {
	List<PatientAllergyDetails> allergyDetails = patientMobileDao.getPatientAllergyDetails(patientID);
		return allergyDetails;
	}
	

	@Override
	@Transactional
	public List<MedicationProcedure> getMedicationProcedure() {
	List<MedicationProcedure> medicationProcedure = patientMobileDao.getMedicationProcedure();
		return medicationProcedure;
	}
	
	/*@Override
	@Transactional
	public String getMedicationRemainder(Boolean status) {
		return patientDAO.getMedicationRemainder(status);
		
	}*/
	
	@Override
	@Transactional
	public int savePDF(SavePdf savePDF) {
		int i = patientMobileDao.savePDF(savePDF);
	    return i;
	}
	
	 @Override
	 @Transactional
	 public List<SavePdf> getPDFDetails(int patientId) {
	  List<SavePdf> list=patientMobileDao.getPDFDetails(patientId) ;
	  return  list;
	 }
	 
	 /*@Override
	 @Transactional
	 public List<Object> getRemainder(int patientId) {
		 List<Object> objects = patientDAO.getRemainder(patientId);
		 return objects;

	 }*/

	 @Override
	 @Transactional
	 public List<PatientMedicationDetails> getMedicationRemainder(int patientId,int readUnread) {
		 List<PatientMedicationDetails> medicationremainder = patientMobileDao.getMedicationRemainder(patientId,readUnread);
		 return medicationremainder;
	 }

	 @Override
	 @Transactional
	 public List<SavePdf> getSavePdfRemainder(int patientId,int readUnread) {
		 List<SavePdf> savepdfdetails = patientMobileDao.getSavePdfRemainder(patientId,readUnread);
		 return savepdfdetails;
	 }

	 @Override
	 @Transactional
	 public List<PatientAppointmentDetails> getPatientAppointmentRemainder(int patientId,int readUnread) {
		 List<PatientAppointmentDetails> patientappointments=patientMobileDao.getPatientAppointmentRemainder(patientId,readUnread);
		 return patientappointments;
	 }

	 @Override
	 @Transactional
	 public List<Qualification> getQualification() {
		 List<Qualification> qualification = patientMobileDao.getQualification();
		 return qualification;
	 }
	 
	 @Override
	 @Transactional
	 public String patientMedicationUpdateStatus(PatientMedicationDetails medicationDetails) {
		return  patientMobileDao.patientMedicationUpdateStatus(medicationDetails);
		}
	 
	
	 
	 @Override
	 @Transactional
	 public int patientLocationUpdate(int userId ,double longitude, double lattitude) {
		 int  i=patientMobileDao.patientLocationUpdate(userId,longitude,lattitude);
		 return i;
	 }
	 
	 @Override
	 @Transactional
	 public String patientAppointmentUpdateStatus(PatientAppointmentDetails appointmentDetails) {
		return  patientMobileDao.patientAppointmentUpdateStatus(appointmentDetails);
		}

	 @Override
	 @Transactional
	 public String patientSavePdfUpdateStatus(SavePdf savePdf) {
		return  patientMobileDao.patientSavePdfUpdateStatus(savePdf);
		}
	 
	 @Override
	 @Transactional
	 public List<Affliation> getAffiliation() {
		 List<Affliation> affliation	= patientMobileDao.getAffiliation();
		 return affliation;
	 }
	 
	 @Override
	 @Transactional
		public List<Location> getLocation() {
		List<Location> location =  patientMobileDao.getLocation();
			return location;
		}
	 
	 @Override
	 @Transactional
	 public List<Specialization> getSpecialization() {
		 List<Specialization> specialization = patientMobileDao.getSpecialization();
		 return specialization;
	 }


	 @Override
	 @Transactional
	 public String updateClinicDetails(Clinic clinic) {
		return patientMobileDao.updateClinicDetails(clinic);

	 }
	 
	 @Override
	 @Transactional
	 public Clinic saveClinicDetails(Clinic clinic) {
		 Clinic clinicDetails=patientMobileDao.saveClinicDetails(clinic);
		 return clinicDetails;
	 }

	 @Override
	 @Transactional
	 public int savePhysicianAvilabilityDetails(PhysicianAvailability physicianavailability) {
		 int  i=patientMobileDao.saveClinicDetails(physicianavailability);
		 return i;
	 }
	 
	 @Override
	 @Transactional
	 public String updatePhysicianAvailabilityDetails(PhysicianAvailability physicianAvailability) {
        return patientMobileDao.updatePhysicianAvailabilityDetails(physicianAvailability);

	 }
	 
	 @Override
	 @Transactional
	 public int savePatientAppointmentDetails(PatientAppointmentDetails patientAppointmentDetails) {
		 int  i = patientMobileDao.savePatientAppointmentDetails(patientAppointmentDetails);
		 return i;
	 }
	 
	 @Override
	 @Transactional
	 public List<PatientAppointmentDetails> getPatientAppointmentDetails(String appointmentTime) {
	  List<PatientAppointmentDetails> list=patientMobileDao.getPatientAppointmentDetails(appointmentTime);
	  return list;
	 }
	 
	 @Override
	 @Transactional
	 public Clinic getClinicDetails(int clinicId) {
            Clinic clinic  = patientMobileDao.getClinicDetails(clinicId);
            return clinic;
	 }
	 
	 @Override
	 @Transactional
	 public List<Location> getCity(int cityId) {
		List<Location> locations = patientMobileDao.getCity(cityId);
		return locations;

	 }
	 
	 @Override
	 @Transactional
	 public List<City> getCity() {
		 List<City> city =  patientMobileDao.getCity();
		 return city;
	 }

	 
	 
	 @Override
	 @Transactional
		public List<PatientAppointmentDetails> getAppointmentTimingDetails(
				int doctorId, int clinicId, int appointmenttime) {
		 
		 List<PatientAppointmentDetails>  list=patientMobileDao.getAppointmentTimingDetails(doctorId,clinicId,appointmenttime);
		
		return list;
		}
	 
	 @Override
	 @Transactional
		public List<PatientAppointmentDetails> getAppointmentTimingDetails(
				int doctorId, int clinicId) {
		 
		 List<PatientAppointmentDetails>  list=patientMobileDao.getAppointmentTimingDetails(doctorId,clinicId);
		
		return list;
		}
	 

     @Override
	@Transactional
	public List<Country> getCountryCodes(String countrycode) {
		// TODO Auto-generated method stub
		List<Country> list=patientMobileDao.getCountryCodes(countrycode);
		
		return list;
	}

     @Override
 	@Transactional
 	public int savePatientPhysician(int patientId, int physicianId) {
 		int i=patientMobileDao.savePatientPhysician(patientId,physicianId);
 		return i;
 	}
     
     @Override
	 @Transactional
	 public  List<PhysicianDetails> getSpecializedPhysicianDetails(int patientId,String specialization) {
	 List<PhysicianDetails> physicianDetails =   patientMobileDao.getSpecializedPhysicianDetails(patientId,specialization);
		return physicianDetails;
		

	 }
	 
	 
	 
	 
	@Override
	public int savePatientDetails(PatientDetails patientdetails) {
		// TODO Auto-generated method stub
		
		 return patientMobileDao.savePatientDetails(patientdetails);
		
	}

	@Override
	@Transactional
	public PatientDetails updatePatientDetails(PatientDetails patientdetails) {
		// TODO Auto-generated method stub
		return patientMobileDao.updatePatientDetails(patientdetails);
	}

	

	

	

	
	
}
