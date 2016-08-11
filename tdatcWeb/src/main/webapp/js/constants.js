var Constants = (function() {
	
	 // 'BASE_URL' :'http://app.skoruz.com:5959/tdatc',	
	  //   'BASE_URL' :'http://localhost:8080/tdatc',
	 
     var private = {
    		 
    		 'BASE_URL' :'http://localhost:8080/tdatc',
         'ADMIN_LOGIN_URL':'/rest/patient/validateUser/',
         'ADMIN_LOGIN':'/rest/admin/validatingUser/',
         'MANAGE_PATIENT_URL':'/rest/patient/fetchAll',
         'MANAGE_HOSPITALS_URL':'/rest/hospitals/fetchAll',
         'MANAGE_PHARMACY_URL':'/rest/pharmacy/fetchAll',
         'FETCH_PATIENT_DETAILS':'/rest/patient/get/',
         'FETCH_HOSPITAL_DETAILS':'/rest/hospitals/get/',
         'FETCH_PHARMACY_DETAILS':'/rest/pharmacy/get/',
         'UPDATE_PATIENT_STATE':'/rest/patient/updateStatus/',
         'ADD_HOPITAL':'/rest/hospitals/put',
         'ADD_PHARMACY':'/rest/pharmacy/put',
         'ADD_PATIENT' : '/rest/patient/register',
         'ADD_PATIENT_CARE_PARTNER' : '/rest/patient/registerPatientCarePartner',
         'ADD_PREFERRED_PHYSICIAN' : '/rest/patient/registerPreferredPhysician',
         'ADD_PREFERRED_HOSPITAL' : '/rest/patient/registerPreferredHospital',
         //'ADD_PATIENT_IMAGE' : '/rest/patientImage/uploadImage',
         'UPDATE_HOSPITAL_BRANCH_DETAILS' : '/rest/hospitals/updateHospitalBranchs',
         
         'FETCH_PATIENT_CARE_PARTNER_DETAILS':'/rest/carePartner/get/',
         'GET_PHARMACY_BRANCH_DETAILS':'/rest/pharmacy/getBranchPharmacy/',
         'GET_PHARMACY_ADMIN' : '/rest/patient/getPharmacyAdmin/',
         'GET_HOSPITAL_ADMIN' : '/rest/patient/getHospitalAdmin/',
         'GET_HOSPITAL_Details' : '/rest/hospitals/getHospital/',
         'GET_HOSPITAL_BRANCH' : '/rest/hospitals/getBranchDetails/',
         'GET_PHARMACY_BRANCH' : '/rest/pharmacy/getBranchDetails/',
         'GET_HOSPITAL_BRANCH_DTO' : '/rest/hospitals/getBranchDetailsDTO/',
         'IMAGE_URL' : '/rest/common/image/',
         'GET_PHYSICIAN_DETAILS' : '/rest/physician/getPhysicianDetails/',
         'GET_PHYSICIAN_DETAIL' : '/rest/physician/getPhysicianDetail/',
         'UPDATE_PHYSICIAN_DETAILS' : '/rest/physician/physicianProfileUpdate/',
         'GET_FORGET_PASSWORD' : '/rest/patient/forgetPassword/'
     };
     return {
        get: function(name) { return private[name]; }
    };
})();