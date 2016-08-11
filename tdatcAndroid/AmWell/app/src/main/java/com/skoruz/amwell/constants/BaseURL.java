package com.skoruz.amwell.constants;

/**
 * Created by Skoruz-Ashish on 8/27/2015.
 */
public class BaseURL {

    /*public static final String serverUrl="http://172.16.3.64:8080/tdatc/rest/mobile/";//lakshmi*/
    /*public static final String serverUrl="http://172.16.3.80:8080/tdatc/rest/mobile/";//hanumanth*/
    public static final String serverUrl="http://app.skoruz.com:5959/tdatc/rest/mobile/";


    /*public static final String serverUrlCommon="http://172.16.3.64:8080/tdatc/rest/";//lakshmi*/
    /*public static final String serverUrlCommon="http://172.16.3.80:8080/tdatc/rest/";//hanumanth*/
    public static final String serverUrlCommon="http://app.skoruz.com:5959/tdatc/rest/";

    /*Patient Login*/

    public static final String userDetailsUrl=serverUrl+"patient/isUserExists/";
    public static final String signUpUrl=serverUrl+"patient/register";
    public static final String valueUploadUrl=serverUrl+"patient/measurementTools";
    public static final String valueGetUrl=serverUrl+"patient/getPatientMeasurementToolsDetails/";
    public static final String valueDeleteUrl=serverUrl+"patient/deletePatientMeasurementTools";
    public static final String patientMedicationList=serverUrl+"patient/getPatientMedicationDetails/";
    public static final String medicationConstantList=serverUrl+"patient/getMadicine";
    public static final String postMedicationData=serverUrl+"patient/medicationDetails";
    public static final String allergyConstantList=serverUrl+"patient/getAllergies";
    public static final String patientAllergyList=serverUrl+"patient/getPatientAllergyDetails/";
    public static final String postAllergyData=serverUrl+"patient/savePatientAllergyDetails";
    public static final String uploadPdf=serverUrl+"patient/savePdf";
    public static final String getLabPdfFiles=serverUrl+"patient/getPdfDetails/";
    public static final String getMedicineRemainders=serverUrl+"patient/getMedicationRemainder/";
    public static final String getLabRemainders=serverUrl+"patient/getSavePdfRemainder/";
    public static final String getAppointmentRemainders=serverUrl+"patient/getPatientAppointmentRemainder/";
    public static final String updateMedicineRemainderStatus=serverUrl+"patient/updateMedicationStatus";
    public static final String updateLabRemainderStatus=serverUrl+"patient/updateSavePdfStatus";
    public static final String updateAppRemainderStatus=serverUrl+"patient/updateAppointmentStatus";
    public static final String uploadLatLong=serverUrl+"patient/patientLocationUpdate/";
    public static final String getSpecialization=serverUrl+"patient/getSpecialization";
    public static final String getQualification=serverUrl+"patient/getQualification";
    public static final String getAffiliation=serverUrl+"patient/getAffiliation";
    public static final String getCityList=serverUrl+"patient/getCity";
    public static final String getLocationByCityId=serverUrl+"patient/getLocationByCity/";
    public static final String updateClinicDetails=serverUrl+"patient/updateClinicDetails";
    public static final String createClinicDetails=serverUrl+"patient/saveClinicDetails";
    public static final String deleteMappedDoctors=serverUrl+"physician/deleteMappedPhysician/";
    public static final String addPreferredDoctor=serverUrl+"patient/savePatientPhysician/";
    public static final String getStateBasedCountryCode=serverUrl+"patient/getCountryCodes/";
    public static final String getPhysicianBySpeciality=serverUrl+"patient/getSpecializedPhysician/";
    public static final String updatePatientDetails=serverUrl+"patient/updatePatientDetails";
    public static final String getPatientDetailsByPatientId=serverUrl+"patient/get/";

    /*Physician Login*/

    public static final String getAllPhysicians=serverUrl+"physician/getAllPhysician/";
    public static final String getMyPhysicians=serverUrl+"physician/getMappedPhysician/";
    public static final String updatePhysicianProfile=serverUrl+"physician/physicianProfileUpdate";
    public static final String getPhysicianDetailsByPhysicianId=serverUrl+"physician/getPhysicianDetails/";

    /*Common Login*/

    public static final String forgotPassword=serverUrlCommon+"patient/forgetPassword/";
    public static final String getFileDownload=serverUrl+"common/fileviewer/";
    public static final String uploadImage=serverUrl+"patient/updatePatientMobileImg/";

    /*Not Used*/

    public static final String getLocation=serverUrl+"patient/getLocation";
    public static final String loginUrl=serverUrl+"patient/validateUser/";
    public static final String getClinicDetails=serverUrl+"patient/getClinicDetails/";
}
