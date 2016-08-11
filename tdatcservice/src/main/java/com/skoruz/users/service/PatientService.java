package com.skoruz.users.service;

import java.util.List;
import java.util.Map;

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

public interface PatientService {
	
	public List<PatientDetails> getAllPatients();
	public PatientDetails getPatientDetails(int id);
	public void updatePatientStatus(int patientId, boolean status);
	public int addPatientDetails(PatientDetails patientDetails);
	public int addPatientCarePartner(PatientCarePartner patientCarePartner);
	public int addPreferredPhysician(PreferredPhysician preferredPhysician);
	public int addPreferredHospital(PreferredHospital preferredHospital);
    public int addPreferredPharmacy(PreferredPharmacy preferredPharmacy );
	public int patientDietaryDetails(PatientDietaryDetails dietaryDetails);
	public int addPatientDietaryFatDetails(PatientDietaryFatDetails fatDetails);
	public int addPatientDietaryAcidDetails(PatientDietaryAcidDetails dietaryAcidDetails);
	public int addPatientVitaminDetails(PatientVitaminDetails vitaminDetails);
	public int addPatientBodyDetails(PatientBodyDetails bodyDetails);
	public PatientBodyDetails getBodyDetails(int id);
	public PatientDetailsBodyDTO getPatientBodyDetails(int bodyId);
	public PatientVitaminDetails getVitaminDetails(int vitaminId);
	public PatientDetailsVitaminDTO getPatientVitaminDetails(int vitaminId);
	public PatientDietaryAcidDetails getDietaryAcidDetails(int acidId);
	public PatientDetailsDietaryAcidDTO getPatientDietaryAcidDetails(int acidId);
	public PatientDietaryFatDetails getDietaryFatDetails(int fatId);
	public PatientDetailsDietaryFatDTO getPatientDietaryFatDetails(int fatId);
	public PatientDietaryDetails getDietaryDetails(int dietId);
	public PatientDetailsDietaryDTO getPatientDietaryDetails(int dietId);
	public int addPatientMedicationDetails(PatientMedicationDetails medicationDetails);
	public List<PatientMedicationDetails> getPatientMedicationDetails(int id,String currentDateTime);
	public PatientMedicationDetails getMedicationDetails(int id);
	public int addPatientAppointmentDetails(PatientAppointmentDetails appointmentDetails );
	public Map<String, Object> getPatientAppointmentdetails(int appointmentId);
	//public PhysicianDetailsAppointmentDTO getPatientPhysicianAppointmentdetails(int appointmentId);
	public int addPatientEnergyBloodBodyDetails(PatientEnergyBloodBodyDetails bloodBodyDetails );
	public PatientDetailsEnergyBloodBodyDTO getPatientEnergyBloodBodydetails(int bloodBodyDetailsId);
	public int addPatientMeasurementToolsDetails(PatientMeasurementToolsDetails patientMeasurementToolsDetails );
	public List<PatientMeasurementToolsDetails> getPatientMeasurementToolsDetails(int measurementToolId, int patientID);
	public int deletePatientMeasurementToolsDetails(int Id);
	public String updatePatientImg(String filePath, int id);
	public User userDetails( String userName,String password);
	public String updatePatientProfile(User user);
	public List<Medicine> getMedicine();
	public int addMedicine(Medicine medicine);
	public List<Allergies> getAllergies();
	public int savePatientAllergyDetails(PatientAllergyDetails patientAllergyDetails);
	public List<PatientAllergyDetails> getPatientAllergyDetails(int patientID);
	public List<MedicationProcedure> getMedicationProcedure();
	//public String getMedicationRemainder(Boolean status);
	public int savePDF(SavePdf savepdf);
	public List<SavePdf> getPDFDetails(int patientId);
	//public List<Object> getRemainder(int patientId);
	public List<Qualification> getQualification();
	public List<PatientMedicationDetails> getMedicationRemainder(int patientId, int readUnread);
	public List<SavePdf> getSavePdfRemainder(int patientId,int readUnread);
	public List<PatientAppointmentDetails> getPatientAppointmentRemainder(int patientId,int readUnread);
	public String patientMedicationUpdateStatus(PatientMedicationDetails medicationDetails);
	public int patientLocationUpdate(int userId,double longitude, double lattitude);
	public String patientAppointmentUpdateStatus(PatientAppointmentDetails appointmentDetails);
	public String patientSavePdfUpdateStatus(SavePdf savePdf);
	public List<Affliation> getAffiliation();
	public List<Location> getLocation();
	public List<Specialization> getSpecialization();
	public String getForgetPassword(String userName);
	public int savePatientPhysician(int patientId, int physicianId);
	public int savePatientPhysicianObject(PatientPhysicianMap patientphysicianmap);
	public int deletePatientPhysicianMap(int patientid, int physicianid);
	public List<Country> getCountryCodes(String countrycode);
	
	
	
	
	

}
