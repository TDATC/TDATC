package com.skoruz.rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.skoruz.common.ApplicationConstants;
import com.skoruz.common.UserImage;
import com.skoruz.entity.Status;
import com.skoruz.users.bean.LoginStatus;
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
import com.skoruz.users.entity.PatientCarePartner;
import com.skoruz.users.entity.PatientDetails;
import com.skoruz.users.entity.PatientMeasurementToolsDetails;
import com.skoruz.users.entity.PatientMedicationDetails;
import com.skoruz.users.entity.PharmacyAdministrator;
import com.skoruz.users.entity.PhysicianAvailability;
import com.skoruz.users.entity.PhysicianDetails;
import com.skoruz.users.entity.PreferredHospital;
import com.skoruz.users.entity.PreferredPharmacy;
import com.skoruz.users.entity.PreferredPhysician;
import com.skoruz.users.entity.Qualification;
import com.skoruz.users.entity.SavePdf;
import com.skoruz.users.entity.Specialization;
import com.skoruz.users.entity.User;
import com.skoruz.users.service.AdminService;
import com.skoruz.users.service.PatientMobileService;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import sun.misc.IOUtils;

//TODO : Use authentication token in ajax header and also userId 

@Component
@Path("/mobile/patient")
public class PatientMobileRestService {


	@Autowired
	PatientMobileService patientMobileService;
	
	@Autowired
	AdminService adminService;
	
	Gson gson = new Gson();
	Status status = new Status();
	
	
	
	@GET
	@Path("/fetchAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PatientDetails> fetchAllPatients(){
		List<PatientDetails> patientList = patientMobileService.getAllPatients();
		List<PatientDetails> patientDetails = new ArrayList<PatientDetails>();
		for(PatientDetails details : patientList ){
			if (details.getUser()!=null) {
				details.getUser().setPhotoPath(ApplicationConstants.FILE_URL+ApplicationConstants.IMG_URL+details.getUser().getPhotoPath());
				patientDetails.add(details);
			}
		}
		return patientDetails;
		}
	
	
	


	

	@GET
	@Path("/get/{patientId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientDetails getPatientDetails(@PathParam("patientId") int id){
		PatientDetails patientDetails = patientMobileService.getPatientDetails(id);
			if (patientDetails.getUser()!=null) {
				patientDetails.getUser().setPhotoPath(ApplicationConstants.FILE_URL+ApplicationConstants.IMG_URL+patientDetails.getUser().getPhotoPath());
			}
		return patientDetails;
	}


	// Need to write one more sevice for @GET
	//@GET
	@POST
	@Path("/validateUser/{userName}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public LoginStatus validateAdmin(@PathParam("userName") String userName, @PathParam("password") String password,@Context HttpServletRequest req){

		HttpSession session = req.getSession(true);

		System.out.println("session id "+session.getId());

		LoginStatus loginStatus = adminService.isValidAdminUser(userName, password);
		if(loginStatus.isStatus()){
			session.setAttribute("userId", loginStatus.getLoginId());
			session.setAttribute("userName", userName);
			//	session.setAttribute("password", password);
			return loginStatus;
		}else{
			return loginStatus;
		}
	}


	@POST
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateAdmin(@Context HttpServletRequest req,@Context HttpServletResponse res) throws URISyntaxException{
		System.out.println("logout session");
		HttpSession session = req.getSession(false);
		URI uri=new URI("http://localhost:8080/tdatcWeb/index.html");
		if(session==null  || session.getAttribute("userId")==null){

			//return gson.toJson("Session Expired");

			return Response.temporaryRedirect(uri).build();
		}else{
			session.invalidate();
			return Response.temporaryRedirect(uri).build();
		}
	}


	@POST
	@Path("/userDetails/{emailAddress}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response userDetails(@PathParam("emailAddress") String emailAddress, @PathParam("password") String password){
		User user = patientMobileService.userDetails(emailAddress, password);
		return   Response.ok().build();

	}

	@POST
	@Path("/isUserExists/{emailAddress}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isUserExists(@PathParam("emailAddress") String emailAddress, @PathParam("password") String password){
		String user = patientMobileService.isUserExists(emailAddress, password);
		if (!user.equalsIgnoreCase("Invalid User")) {
			String[] userValues=user.split(" ");
			String userType=userValues[0];
			int user_id=Integer.parseInt(userValues[1]);
			if (userType.equalsIgnoreCase("PT")) {
				PatientDetails patientDetails=patientMobileService.getPatientDetails(user_id);
				if (patientDetails.getUser()!=null) {
					if (patientDetails.getUser().getPhotoPath()!=null) {
						if (!patientDetails.getUser().getPhotoPath().equalsIgnoreCase("null") && !patientDetails.getUser().getPhotoPath().isEmpty()) {
							patientDetails.getUser().setPhotoPath(ApplicationConstants.FILE_URL+ApplicationConstants.IMG_URL+patientDetails.getUser().getPhotoPath());
						}
					}
				}
				return Response.ok(patientDetails).build();
			}else {
				PhysicianDetails physicianDetails=patientMobileService.getPhysicianDetails(user_id);
				if (physicianDetails.getUser()!=null) {
					if (physicianDetails.getUser().getPhotoPath()!=null) {
						if (!physicianDetails.getUser().getPhotoPath().equalsIgnoreCase("null") && !physicianDetails.getUser().getPhotoPath().isEmpty()) {
							physicianDetails.getUser().setPhotoPath(ApplicationConstants.FILE_URL+ApplicationConstants.IMG_URL+physicianDetails.getUser().getPhotoPath());
						}
					}
				}
				return Response.ok(physicianDetails).build();
			}
		}else{
			return Response.ok("Invalid User").build();
		}
	}

	@GET
	@Path("/updateStatus/{patientId}/{status}")
	public void updatePatientActiveStatus(@PathParam("patientId") int patientId,@PathParam("status") boolean status){
		patientMobileService.updatePatientStatus(patientId, status);
	}

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> registerPatientDetails(PatientDetails patientDetails){ 

		Integer i =	patientMobileService.addPatientDetails(patientDetails);

		/*Status status = new Status(); 

	if(i>0){
		status.setStatus("success");
		return status;
	}else{
		status.setStatus("failure");
			return status;
		}

	}	*/

		Map<String ,Object>  status=new  HashMap<String, Object>();
		if(i>0){
			status.put("status","success");
			status.put("id", i);
			return  status;
		}else{
			status.put("status","failure");
			return status;
		}

	}

	@GET
	@Path("/getHospitalAdmin/{adminId}")
	@Produces(MediaType.APPLICATION_JSON)
	public HospitalAdministrator getHospitalAdmin(@PathParam("adminId") int id){
		HospitalAdministrator adminDetails = adminService.getHospitalAdmin(id);
		return adminDetails;
	}

	@GET
	@Path("/getPharmacyAdmin/{adminId}")                              
	@Produces(MediaType.APPLICATION_JSON)
	public PharmacyAdministrator getPharmacyAdmin(@PathParam("adminId") int id){
		PharmacyAdministrator admin	= adminService.getPharmacyAdministrator(id);
		return admin; 

	}

	@POST
	@Path("/registerPatientCarePartner")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public  String registerPatientCarePartner(PatientCarePartner patientCarePartner)
	{
		int i = patientMobileService.addPatientCarePartner(patientCarePartner);

		if(i>0){
			return "success";
		} else{
			return "failure";
		}

	}

	@POST
	@Path("/registerPreferredPhysician")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String registerPreferredPhysician(PreferredPhysician preferredPhysician)
	{
		int i = patientMobileService.addPreferredPhysician(preferredPhysician);

		if(i>0){
			return "success";
		} else{
			return "failure";
		}

	}

	@POST
	@Path("/registerPreferredHospital")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String registerPreferredHospital(PreferredHospital preferredHospital){

		int i = patientMobileService.addPreferredHospital(preferredHospital);

		if(i>0){
			return "success";
		} else{
			return "failure";
		}
	}

	@POST
	@Path("/registerPreferredPharmacy")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String registerPreferredPharmacy(PreferredPharmacy preferredPharmacy ){

		int i = patientMobileService.addPreferredPharmacy(preferredPharmacy);

		if(i>0){
			return "success";
		} else{
			return "failure";
		}
	}


	@SuppressWarnings("unused")
	@POST
	@Path("/updatePatientImg/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public String updatePatientImg(@DefaultValue("true") @FormDataParam("enabled") boolean enabled,@FormDataParam("photoFile") InputStream fileObject,
			@FormDataParam("photoFile") FormDataContentDisposition fileDetail,@PathParam("id") int id,@Context HttpServletRequest req)  {

		{
			System.out.println("update id "+id+"    context path "+req.getContextPath());
			String folderPath=ApplicationConstants.DIRECTORY+ApplicationConstants.PHOTO;
			String filePath=id+fileDetail.getFileName();
			System.out.println(fileDetail.getFileName().substring(fileDetail.getFileName().indexOf("."),fileDetail.getFileName().length()));
			System.out.println(folderPath+filePath);

			try 
			{
				int read = 0;
				byte[] bytes = new byte[1024];
				File path=new File(folderPath);
				if(!path.exists())
					if(!path.mkdirs())
						System.out.println("Already is there");

				File  patientImg= new File(folderPath,filePath);   
				OutputStream out = new FileOutputStream(patientImg);  
				while ((read = fileObject.read(bytes)) != -1) 
				{
					out.write(bytes, 0, read);
				}
				out.flush();
				out.close();

				String retMsg = patientMobileService.updatePatientImg(filePath,id);

				System.out.println("Is file or what  "+patientImg.isFile());
			}catch (IOException e){
				System.out.println("Exe "+e.getMessage());
			}
		}
		return  gson.toJson("success") ;
	}

	/*
	@POST
	@Path("/dietaryDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String patientDietaryDetails(PatientDietaryDetails dietaryDetails){
		int i = patientService.patientDietaryDetails(dietaryDetails);

		if(i>0){
			return "success";
		}else{
			return "failure";
		}

	}

	@POST
	@Path("/dietaryFatDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String patientDietaryFatDetails(PatientDietaryFatDetails fatDetails){
		int i = patientService.addPatientDietaryFatDetails(fatDetails);

		if(i>0){
		return "success";
	}else {
		return "failure";
	}

	}	


	@POST
	@Path("/dietaryAcidDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String patientDietaryAcidDetails(PatientDietaryAcidDetails dietaryAcidDetails ){
		int i = patientService.addPatientDietaryAcidDetails(dietaryAcidDetails);

		if(i>0){
		return "success";
	} else{
		return "failure";
	      }
    }

	@POST
	@Path("/vitaminDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String patientVitaminDetails(PatientVitaminDetails vitaminDetails){
		System.out.println("date 1  "+vitaminDetails.getDateTime());
		int i = patientService.addPatientVitaminDetails(vitaminDetails);

		if(i>0){
		  return "success";
		} else{
			return "failure";
		}

	}

	@POST
	@Path("/bodyDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String patientBodyDetails(PatientBodyDetails bodyDetails){
		int i = patientService.addPatientBodyDetails(bodyDetails);

		if(i>0){
			return "success";
		} else{
		    return "failure";
	    }


	}

	@GET
	@Path("/getBodyDetails/{bodyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientBodyDetails getBodyDetails(@PathParam("bodyId") int id){
		PatientBodyDetails patientDetails = patientService.getBodyDetails(id);
		return patientDetails;
	}

	@GET
	@Path("/getPatientBodyDetails/{bodyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientDetailsBodyDTO getPatientBodydetails(@PathParam("bodyId") int bodyId){
		PatientDetailsBodyDTO details= patientService.getPatientBodyDetails(bodyId);
		return details;
	}

	@GET
	@Path("/getVitaminDetails/{vitaminId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientVitaminDetails getVitaminDetails(@PathParam("vitaminId") int vitaminId){
		PatientVitaminDetails vitaminDetails = patientService.getVitaminDetails(vitaminId);
		return vitaminDetails;
	}

	@GET
	@Path("/getPatientVitaminDetails/{vitaminId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientDetailsVitaminDTO getPatientVitaminDetails(@PathParam("vitaminId") int vitaminId ){
		PatientDetailsVitaminDTO details = patientService.getPatientVitaminDetails(vitaminId);
		return details;

	}

	@GET
	@Path("/getDietaryAcidDetails/{acidId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientDietaryAcidDetails getDietaryAcidDetails(@PathParam("acidId") int acidId){
		PatientDietaryAcidDetails acidDetails = patientService.getDietaryAcidDetails(acidId);

		return acidDetails;

	}

	@GET
	@Path("/getPatientDietaryAcidDetails/{acidId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientDetailsDietaryAcidDTO getPatientDietaryAcidDetails(@PathParam("acidId") int acidId){
		PatientDetailsDietaryAcidDTO acidDetails = patientService.getPatientDietaryAcidDetails(acidId);
		return acidDetails;

	}
	@GET
	@Path("/getDietaryFatDetails/{fatId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientDietaryFatDetails getDietaryFatDetails(@PathParam("fatId") int fatId){
		PatientDietaryFatDetails fatDetails = patientService.getDietaryFatDetails(fatId);
		return fatDetails;

	}

	@GET
	@Path("/getPatientDietaryFatDetails/{fatId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientDetailsDietaryFatDTO getPatientDietaryFatDetails(@PathParam("fatId") int fatId){
		PatientDetailsDietaryFatDTO fatDetails = patientService.getPatientDietaryFatDetails(fatId);
		return fatDetails;

	}

	@GET
	@Path("/getDietaryDetails/{dietId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientDietaryDetails getDietaryDetails(@PathParam("dietId") int dietId){
		PatientDietaryDetails dietaryDetails = patientService.getDietaryDetails(dietId);
		return dietaryDetails;

	}

	@GET
	@Path("/getPatientDietaryDetails/{dietId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientDetailsDietaryDTO getPatientDietaryDetails(@PathParam("dietId") int dietId){
		PatientDetailsDietaryDTO dietaryDetails = patientService.getPatientDietaryDetails(dietId);
		return dietaryDetails;

	}*/

	@POST
	@Path("/medicationDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status patientMedicationDetails(PatientMedicationDetails medicationDetails ){
		int i = patientMobileService.addPatientMedicationDetails(medicationDetails);

		Status status = new Status();

		if(i>0){
			status.setStatus("success");
			return status;
		} else{
			status.setStatus("failure");
			return status;
		}
	}
	@GET
	@Path("/getPatientMedicationDetails/{patientId}/{currentDateTime}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPatientMedicationDetails(@PathParam("patientId") int id,@PathParam("currentDateTime") String currentDateTime){
		List<PatientMedicationDetails> medication = patientMobileService.getPatientMedicationDetails(id,currentDateTime);
		return Response.ok(medication).build();
		//return medication;

	}

	/*@GET
	@Path("/getMedicationDetails/{medicationId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientMedicationDetails getMedicationDetails(@PathParam("medicationId") int id){
		PatientMedicationDetails medicationDetails = patientService.getMedicationDetails(id);
		return medicationDetails;
	}
	 */

	@POST
	@Path("/patientAppoitment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String patientAppointmentDetails(PatientAppointmentDetails appointmentDetails){
		int i = patientMobileService.addPatientAppointmentDetails(appointmentDetails);

		if(i>0){
			return "success";
		} else{
			return "failure";
		}

	}

	@GET
	@Path("/getPatientAppointment/{appointmentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> getPatientAppointmentdetails(@PathParam("appointmentId") int appointmentId){
		Map<String,Object> appointmentDetails = patientMobileService.getPatientAppointmentdetails(appointmentId);
		return appointmentDetails;

	}

	//This was related to getPatientAppointmentdetails method which not required 
	/*@GET 
	@Path("/getPatientPhysicianAppointment/{appointmentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PhysicianDetailsAppointmentDTO getPatientPhysicianAppointmentdetails(@PathParam("appointmentId") int appointmentId){
		PhysicianDetailsAppointmentDTO appointmentDetails = patientService.getPatientPhysicianAppointmentdetails(appointmentId);
		return appointmentDetails;

	}*/

	/*@POST
	@Path("/patientBloodBodyDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String patientEnergyBloodBodyDetails(PatientEnergyBloodBodyDetails bloodBodyDetails ){
		int i = patientService.addPatientEnergyBloodBodyDetails(bloodBodyDetails);

		if(i>0){
		return "success";
    	} else{
    		return "failure";
    	}

	}

	@GET
	@Path("/getPatientBloodBodyDetails/{bloodBodyDetailsId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientDetailsEnergyBloodBodyDTO getPatientEnergyBloodBodydetails(@PathParam("bloodBodyDetailsId") int bloodBodyDetailsId){
		PatientDetailsEnergyBloodBodyDTO energyBloodBodyDTO = patientService.getPatientEnergyBloodBodydetails(bloodBodyDetailsId);
		return energyBloodBodyDTO;

	}*/

	@POST
	@Path("/measurementTools")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Status patientMeasurementToolsDetails(PatientMeasurementToolsDetails patientMeasurementToolsDetails){
		int i = patientMobileService.addPatientMeasurementToolsDetails(patientMeasurementToolsDetails);

		Status status = new Status();

		if(i>0){

			status.setStatus("success");
			return status;
		} else{
			status.setStatus("failure");
			return status;
		}

	}

	@GET
	@Path("/getPatientMeasurementToolsDetails/{measurementToolId}/{patientId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPatientMeasurementToolsDetails(@PathParam("measurementToolId") int measurementToolId , @PathParam("patientId") int patientId){
		List<PatientMeasurementToolsDetails> toolsDetails  = patientMobileService.getPatientMeasurementToolsDetails(measurementToolId, patientId);
		return Response.ok(toolsDetails).build();
		//return toolsDetails;

	}


	@DELETE
	@Path("/deletePatientMeasurementTools/{Id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Status deletePatientMeasurementToolsDetails(@PathParam("Id") int Id){
		int i  = patientMobileService.deletePatientMeasurementToolsDetails(Id);

		Status status = new Status();

		if(i>0){

			status.setStatus("success");
			return status;
		} else{
			status.setStatus("failure");
			return status;
		}

	}

	@POST
	@Path("/updateProfile")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePatientProfile(User user){
		patientMobileService.updatePatientProfile(user);
		return Response.ok(user).build();
	}

	@GET
	@Path("/getMadicine")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMedicine(){
		List<Medicine> medicines = patientMobileService.getMedicine();
		return Response.ok(medicines).build();
	}

	@POST
	@Path("/medicine")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status medicine(Medicine medicine){
		int i = patientMobileService.addMedicine(medicine);

		Status status = new Status();

		if(i>0){
			status.setStatus("success");
			return status;
		} else{
			status.setStatus("failure");
			return status;
		}

	}

	@GET
	@Path("/getAllergies")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllergies(){
		List<Allergies> allergies	= patientMobileService.getAllergies();
		return Response.ok(allergies).build();

	}

	@POST
	@Path("/savePatientAllergyDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public  Status patientAllergyDetails(PatientAllergyDetails patientAllergyDetails ){

		int  i=patientMobileService.savePatientAllergyDetails(patientAllergyDetails);
		Status  status=new Status ();

		if(i>0){
			status.setStatus("success");
			return  status;
		} else{
			status.setStatus("failure");
			return  status;
		}

	}

	@GET
	@Path("/getPatientAllergyDetails/{patientID}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PatientAllergyDetails> getPatientAllergyDetails(@PathParam("patientID") int patientID){
		List<PatientAllergyDetails> allergyDetails = patientMobileService.getPatientAllergyDetails(patientID);
		return allergyDetails;

	}


	@GET
	@Path("/getMedicationProcedure")
	@Produces(MediaType.APPLICATION_JSON)
	public List<MedicationProcedure> getMedicationProcedure(){
		List<MedicationProcedure> medicationProcedure =  patientMobileService.getMedicationProcedure();
		return medicationProcedure;

	}

	// As of now no use
	/* @GET
	 @Path("/getMedicationRemainder/{status}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response getMedicationRemainder(@PathParam("status") Boolean status){
		 patientService.getMedicationRemainder(status);
		 return Response.ok(status).build();
	 }*/

	@POST
	@Path("/savePdf")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Map<String, Object>  savePdf(SavePdf savePDF){
		int  i= patientMobileService.savePDF(savePDF);
		try{
			InputStream fileObject=new ByteArrayInputStream(savePDF.getFileData());
			String folderPath=ApplicationConstants.DIRECTORY+ApplicationConstants.FILE;
			String filePath=savePDF.getPatientId()+savePDF.getFileName();
			System.out.println(savePDF.getFileName().substring(savePDF.getFileName().indexOf("."),savePDF.getFileName().length()));
			System.out.println(folderPath+filePath);
			
			int read = 0;
			byte[] bytes = new byte[1024];
			File path=new File(folderPath);
			if(!path.exists())
				if(!path.mkdirs())
					System.out.println("Already is there");

			File  patientImg= new File(folderPath,filePath);   
			OutputStream out = new FileOutputStream(patientImg);  
			while ((read = fileObject.read(bytes)) != -1) 
			{
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			
			FileOutputStream fileOuputStream = new FileOutputStream(folderPath+filePath); 
			fileOuputStream.write(savePDF.getFileData());

			fileOuputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}

		Map<String ,Object>  status=new  HashMap<String, Object>();
		if(i>0){
			status.put("status","success");
			status.put("id", i);
			return  status;
		}else{
			status.put("status","failure");
			return status;
		} 
	}
	
	
	
	@POST
	@Path("/updatePatientMobileImg/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMobilePatientImg(UserImage userImage,@PathParam("id") int id){
		String retMsg = null;
			InputStream fileObject = new ByteArrayInputStream(userImage.getFileData());
			String folderPath=ApplicationConstants.DIRECTORY+ApplicationConstants.PHOTO;
			String filePath=id+userImage.getFileName();
			System.out.println(userImage.getFileName().substring(userImage.getFileName().indexOf("."),userImage.getFileName().length()));
			System.out.println(folderPath+filePath);

			try 
			{
				int read = 0;
				byte[] bytes = new byte[10485760];
				File path=new File(folderPath);
				if(!path.exists())
					if(!path.mkdirs())
						System.out.println("Already is there");

				File  patientImg= new File(folderPath,filePath);   
				OutputStream out = new FileOutputStream(patientImg);  
				while ((read = fileObject.read(bytes)) != -1) 
				{
					out.write(bytes, 0, read);
				}
				out.flush();
				out.close();
				
				FileOutputStream fileOuputStream = new FileOutputStream(folderPath+filePath); 
				fileOuputStream.write(userImage.getFileData());

				fileOuputStream.close();
				
				/*FileOutputStream fileOuputStream = new FileOutputStream(folderPath+filePath); 
				fileOuputStream.write(userImage.getFileData());

				fileOuputStream.close();
*/
				retMsg = patientMobileService.updatePatientImg(userImage.getFileName(),id);
			}catch (IOException e){
				System.out.println("Exe "+e.getMessage());
			}
			Status status=new Status();
			if(retMsg.equals("success")){
				status.setStatus("success");
			}else{
				status.setStatus("failure");
			} 
			
			return Response.ok(status).build();
	}

	
	
	

	@GET
	@Path("/getPdfDetails/{patientId}")
	@Produces(MediaType.APPLICATION_JSON)
	public   List<SavePdf> getPdfDetails(@PathParam("patientId")   int patientId){

		/*String prefixFilePath=ApplicationConstants.DIRECTORY+ApplicationConstants.FILE;
	  InputStream in;
	  Boolean bool = false;*/

		List<SavePdf> list=patientMobileService.getPDFDetails(patientId);
		/*try{
	  if(list!=null){
	   for (SavePdf savePdf : list) {
	    if(prefixFilePath!=null && (prefixFilePath.contains("http://") || prefixFilePath.contains("https://"))) {
	                 URL url = new URL (prefixFilePath + savePdf.getFileName());
	                 in = url.openStream();
	             }else{
	              File f = new File(prefixFilePath + savePdf.getFileName());
	              System.out.println("file :" +savePdf.getFileName());
	              bool = f.exists();


	              if(bool)
	              {
	               prefixFilePath = f.getAbsolutePath();
	               System.out.println("path" + prefixFilePath);

	              }
	                 if(f.exists())
	                  in = new FileInputStream(f.getAbsoluteFile());
	             }
	   }
	  }
	  }catch(Exception e){
	   e.printStackTrace();
	  }*/
		return list;
	}

	/*@GET
	@Path("/getRemainder/{patientId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRemainder(@PathParam("patientId") int patientId){
		List<Object> objects = patientService.getRemainder(patientId);
		return Response.ok(objects).build();

	}*/


	@GET
	@Path("/getMedicationRemainder/{patientId}/{readUnread}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMedicationRemainder(@PathParam("patientId") int patientId, @PathParam("readUnread") int readUnread){
		List<PatientMedicationDetails> medicationremainder = patientMobileService.getMedicationRemainder(patientId,readUnread);
		return Response.ok(medicationremainder).build();
	}


	@GET
	@Path("/getSavePdfRemainder/{patientId}/{readUnread}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SavePdf> getSavePdfRemainder(@PathParam("patientId")  int patientId, @PathParam("readUnread") int readUnread){
		List<SavePdf> savepdfdetails = patientMobileService.getSavePdfRemainder(patientId,readUnread);
		return savepdfdetails;
	}


	@GET
	@Path("/getPatientAppointmentRemainder/{patientId}/{readUnread}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PatientAppointmentDetails> getPatientAppointmentRemainder(@PathParam("patientId")  int patientId, @PathParam("readUnread") int readUnread){
		List<PatientAppointmentDetails> patientappointments = patientMobileService.getPatientAppointmentRemainder(patientId,readUnread);
		return patientappointments;
	}


	@GET
	@Path("/getQualification")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQualification(){
		List<Qualification> qualification = patientMobileService.getQualification();
		return Response.ok(qualification).build();
	}

	@POST
	@Path("/updateMedicationStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response patientMedicationUpdateStatus(PatientMedicationDetails medicationDetails){
		patientMobileService.patientMedicationUpdateStatus(medicationDetails);
		//return Response.ok(medicationDetails).build();
		Status status2 = new Status();

		status2.setStatus("success");
		return Response.ok(status2).build();

		/*if (i>0){
			status2.setStatus("success");
			return Response.ok(status2).build();
		} else{
			status2.setStatus("failure");
			return Response.ok(status2).build();
		}*/

	}


	@POST
	@Path("/patientLocationUpdate/{userId}/{longitude}/{lattitude}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response    patientLocationUpdate(@PathParam("userId")  int  userId ,@PathParam("longitude") double longitude,@PathParam("lattitude") double lattitude ){
		int  i=patientMobileService.patientLocationUpdate(userId,longitude,lattitude);
		Status  status=new Status();

		if(i>0){
			status.setStatus("success");

			return Response.ok(status).build();
		}else{

			status.setStatus("failure");
			return Response.ok(status).build();

		}

	}

	@POST
	@Path("/updateAppointmentStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response patientAppointmentUpdateStatus(PatientAppointmentDetails appointmentDetails){
		patientMobileService.patientAppointmentUpdateStatus(appointmentDetails);

		Status status2 = new Status();

		status2.setStatus("success");
		return Response.ok(status2).build();

	}

	@POST
	@Path("/updateSavePdfStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response patientSavePdfUpdateStatus(SavePdf savePdf){
		patientMobileService.patientSavePdfUpdateStatus(savePdf);

		Status status2 = new Status();

		status2.setStatus("success");
		return Response.ok(status2).build();

	}

	@GET
	@Path("/getAffiliation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAffiliation(){
		List<Affliation> affliation = patientMobileService.getAffiliation();
		return Response.ok(affliation).build();
	}

	@GET
	@Path("/getLocation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLocation(){
		List<Location> location = patientMobileService.getLocation();
		return Response.ok(location).build();
	}

	@GET
	@Path("/getSpecialization")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpecialization(){
		List<Specialization> specialization = patientMobileService.getSpecialization();
		return Response.ok(specialization).build();

	}


	@PUT
	@Path("/updateClinicDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateClinicDetails(Clinic clinic){
		System.out.println(clinic);
		patientMobileService.updateClinicDetails(clinic);


		Status status = new Status();
		status.setStatus("success");
		return Response.ok(clinic).build();

	}

	@POST
	@Path("/saveClinicDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response  saveClinicDetails(Clinic clinic ){
		Clinic clinicDetails= patientMobileService.saveClinicDetails(clinic);
		return Response.ok(clinicDetails).build();
	}

	@POST
	@Path("/savePhysicianAvilabilityDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response savePhysicianAvilabilityDetails(PhysicianAvailability physicianavailability){

		int  i=patientMobileService.savePhysicianAvilabilityDetails(physicianavailability);

		Status status=new Status();

		if(i>0){
			status.setStatus("success");
			return Response.ok(status).build();
		} else{
			status.setStatus("failure");
			return Response.ok(status).build() ;
		}

	}


	@POST
	@Path("/updatePhysicianAvailabilityDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePhysicianAvailabilityDetails(PhysicianAvailability physicianAvailability){
		patientMobileService.updatePhysicianAvailabilityDetails(physicianAvailability);

		Status status = new Status();
		status.setStatus("success");
		return Response.ok(status).build();

	}

	@POST
	@Path("/savePatientAppointmentDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response savePatientAppointmentDetails(PatientAppointmentDetails patientAppointmentDetails){
		int  i = patientMobileService.savePatientAppointmentDetails(patientAppointmentDetails);

		Status status = new Status();

		if(i>0){
			status.setStatus("success");
			return Response.ok(status).build();
		} else {
			status.setStatus("failure");
			return Response.ok(status).build();
		}
	}

	@GET
	@Path("/getPatientAppointmentDetails/{appointmentTime}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response  getPatientAppointmentDetails(@PathParam("appointmentTime")  String appointmentTime ){
		List<PatientAppointmentDetails>  list=patientMobileService.getPatientAppointmentDetails(appointmentTime);
		return Response.ok(list).build();

	}


	@GET
	@Path("/getClinicDetails/{clinicId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClinicDetails(@PathParam("clinicId") int clinicId){
		Clinic clinic = patientMobileService.getClinicDetails(clinicId);
		return Response.ok(clinic).build();
	}

	@GET
	@Path("/getLocationByCity/{cityId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCity(@PathParam("cityId") int cityId){
		List<Location> locations = patientMobileService.getCity(cityId);
		return Response.ok(locations).build();

	}

	@GET
	@Path("/getCity")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCity(){
		List<City> city = patientMobileService.getCity();
		return Response.ok(city).build();
	}


	@GET
	@Path("/getAppointmentTimingDetails/{doctorId}/{clinicId}/{CurrentDateTime}")
	@Produces(MediaType.APPLICATION_JSON)  

	public  List<PatientAppointmentDetails>  appointmentTimingDetails(@PathParam("doctorId")  int  doctorId,@PathParam("clinicId")   int  clinicId,@PathParam("appointmenttime")    int  appointmenttime){

		List<PatientAppointmentDetails> list=patientMobileService.getAppointmentTimingDetails(doctorId,clinicId,appointmenttime);
		return list;

	}

	@GET
	@Path("/getAppointmentTimingDetails/{doctorId}/{clinicId}")
	@Produces(MediaType.APPLICATION_JSON)  
	public  List<PatientAppointmentDetails>  appointmentTimingDetail(@PathParam("doctorId")  int  doctorId,@PathParam("clinicId")   int  clinicId){

		List<PatientAppointmentDetails> list=patientMobileService.getAppointmentTimingDetails(doctorId,clinicId);
		return list;

	}


	@GET
	@Path("/getCountryCodes/{countrycode}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Country>  getCountryCodes(@PathParam("countrycode")  String countrycode){
		List<Country>  list=patientMobileService.getCountryCodes(countrycode);
		return list;
	}


	@POST
	@Path("/savePatientPhysician/{patientId}/{physicianId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  Response savePatientPhysician(@PathParam("patientId")  int patientId,@PathParam("physicianId")int physicianId){
		int  i=patientMobileService.savePatientPhysician(patientId,physicianId);

		if(i>0){
			status.setStatus("success");
		}else {
			status.setStatus("failure");
		}

		return Response.ok(status).build();

	}

	@GET
	@Path("/getSpecializedPhysician/{patientId}/{specialization}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpecializedPhysicianDetails(@PathParam("patientId")int patientId,@PathParam("specialization") String specialization ){
		List<PhysicianDetails> physicianDetails = patientMobileService.getSpecializedPhysicianDetails(patientId,specialization);
 		return Response.ok(physicianDetails).build();

	}


	@POST
	@Path("/updatePatientDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Status  updatePatientDetails  (PatientDetails patientdetails){


		PatientDetails details=patientMobileService.updatePatientDetails(patientdetails);
		Status  status=new Status();
		if (details!=null) {
			status.setStatus("success"); 	
		}else{
			status.setStatus("failure"); 
		}
		return status;
	}



}

