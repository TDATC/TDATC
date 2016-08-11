package com.skoruz.users.dao;

import java.util.List;
import java.util.Map;

import com.skoruz.users.entity.Affliation;
import com.skoruz.users.entity.Allergies;
import com.skoruz.users.entity.City;
import com.skoruz.users.entity.Clinic;
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
import com.skoruz.users.entity.PatientVitaminDetails;
import com.skoruz.users.entity.PharmacyAdministrator;
import com.skoruz.users.entity.PhysicianAvailability;
import com.skoruz.users.entity.PhysicianDetails;
import com.skoruz.users.entity.PhysicianDetailsAppointmentDTO;
import com.skoruz.users.entity.PreferredHospital;
import com.skoruz.users.entity.PreferredPharmacy;
import com.skoruz.users.entity.PreferredPhysician;
import com.skoruz.users.entity.Qualification;
import com.skoruz.users.entity.SavePdf;
import com.skoruz.users.entity.Specialization;
import com.skoruz.users.entity.User;

public interface PatientMobileDAO {
	
	public List<PatientDetails> getAllPatients();
	public PatientDetails getPatientDetails(int id);
	public PhysicianDetails getPhysicianDetails(int id);
		
	//TODO Move this to seperate service
	public List<User> getAllAdministrators();
	public HospitalAdministrator getHospitalAdmin(int id);
	public PharmacyAdministrator getPharmacyAdministrator(int id);
	
	public int savePatientDetails(PatientDetails patientDetails);
	public int savePatientCarePartner(PatientCarePartner patientCarePartner);
	public int addPreferredPhysician(PreferredPhysician preferredPhysician);
	public int addPreferredHospital(PreferredHospital preferredHospital);
	public int addPreferredPharmacy(PreferredPharmacy preferredPharmacy);
	public int patientDietaryDetails(PatientDietaryDetails dietaryDetails);
	public int addPatientDietaryFatDetails(PatientDietaryFatDetails fatDetails);
    public int addPatientDietaryAcidDetails(PatientDietaryAcidDetails dietaryAcidDetails);
    public int addPatientVitaminDetails(PatientVitaminDetails vitaminDetails);
    public int addPatientBodyDetails(PatientBodyDetails bodyDetails);
    public PatientBodyDetails getBodyDetails(int id);
    public PatientDetailsBodyDTO getPatientBodyDetails(int bodyId);
    public PatientVitaminDetails getvitaminDetails(int vitaminId);
    public PatientDetailsVitaminDTO getPatientVitamindetails(int vitaminId);
    public PatientDietaryAcidDetails getDietaryAcidDetails(int acidId);
    public PatientDetailsDietaryAcidDTO getPatientDietaryAcidDetails(int acidId);
    public PatientDietaryFatDetails getDietaryFatDetails(int fatId);
    public PatientDetailsDietaryFatDTO getPatientDietaryFatDetails(int fatId);
    public PatientDietaryDetails getDietaryDetails(int dietId);
    public PatientDetailsDietaryDTO getPatientDietaryDetails(int dietId);
    public int addPatientMedicationDetails(PatientMedicationDetails medicationDetails);
    public List<PatientMedicationDetails> getPatientMedicationDetails(int id,String currentDateTime);
    public PatientMedicationDetails getMedicationDetails(int id);
    public int addPatientAppointmentDetails(PatientAppointmentDetails appointmentDetails);
    public Map<String,Object> getPatientAppointmentdetails(int appointmentId);
    public PhysicianDetailsAppointmentDTO getPatientPhysicianAppointmentdetails(int physicianId);
	public Object getPatientPatientAppointmentdetails(int parseInt);
	public int addPatientEnergyBloodBodyDetails(PatientEnergyBloodBodyDetails bloodBodyDetails);
	public PatientDetailsEnergyBloodBodyDTO getPatientEnergyBloodBodydetails(int bloodBodyDetailsId);
	public int addPatientMeasurementToolsDetails(PatientMeasurementToolsDetails patientMeasurementToolsDetails);
	public List<PatientMeasurementToolsDetails> getPatientMeasurementToolsDetails(int measurementToolId, int patientId);
	public int deletePatientMeasurementToolsDetails(int Id);
	public String updatePatientImg(String filePath, int id);
	public User userDetails(String emailAddress, String password);
	public String isUserExists(String emailAddress, String password);
	public String updatePatientProfile(User user);
	public List<Medicine> getMedicine();
	public int addMedicine(Medicine medicine);
	public List<Allergies> getAllergies();
	public int savePatientAllergyDetails(PatientAllergyDetails patientAllergyDetails);
	public List<PatientAllergyDetails> getPatientAllergyDetails(int patientID);
	public List<MedicationProcedure> getMedicationProcedure();
	//public String getMedicationRemainder(Boolean status);
	public int savePDF(SavePdf savePDF);
	public List<SavePdf> getPDFDetails(int patientId);
	//public List<Object> getRemainder(int patientId);
	public List<Qualification> getQualification();
	public List<PatientMedicationDetails> getMedicationRemainder(int patientId,int status);
	public List<SavePdf> getSavePdfRemainder(int patientId,int status);
	public List<PatientAppointmentDetails> getPatientAppointmentRemainder(int patientId,int status);
	public String patientMedicationUpdateStatus(PatientMedicationDetails medicationDetails);
	public int patientLocationUpdate(int userId, double longitude, double lattitude);
	public String patientAppointmentUpdateStatus(PatientAppointmentDetails appointmentDetails);
	public String patientSavePdfUpdateStatus(SavePdf savePdf);
	public List<Affliation> getAffiliation();
	public List<Location> getLocation();
	public List<Specialization> getSpecialization();
	public String updateClinicDetails(Clinic clinic);
	public Clinic saveClinicDetails(Clinic clinic);
	public int saveClinicDetails(PhysicianAvailability physicianavailability);
	public String updatePhysicianAvailabilityDetails(PhysicianAvailability physicianAvailability);
	public int savePatientAppointmentDetails(PatientAppointmentDetails patientAppointmentDetails);
	public List<PatientAppointmentDetails> getPatientAppointmentDetails(String appointmentTime);
	public Clinic getClinicDetails(int clinicId);
	public List<Location> getCity(int cityId);
	public List<City> getCity();
	public int saveClinicVisitingTimings(int clinicId);
	public List<PatientAppointmentDetails> getAppointmentTimingDetails(int doctorId, int clinicId, int appointmenttime);
	public List<PatientAppointmentDetails> getAppointmentTimingDetails(int doctorId, int clinicId);
	public List<Country> getCountryCodes(String countrycode);
	public int savePatientPhysician(int patientId, int physicianId);
	public List<PhysicianDetails> getSpecializedPhysicianDetails(int patientId,String specialization);
	public PatientDetails updatePatientDetails(PatientDetails patientdetails);
	

	
    
}
