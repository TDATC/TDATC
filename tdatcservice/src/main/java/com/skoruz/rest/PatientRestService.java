package com.skoruz.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
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
import com.skoruz.entity.Status;
import com.skoruz.users.bean.LoginStatus;
import com.skoruz.users.entity.Affliation;
import com.skoruz.users.entity.Allergies;
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
import com.skoruz.users.entity.PreferredHospital;
import com.skoruz.users.entity.PreferredPharmacy;
import com.skoruz.users.entity.PreferredPhysician;
import com.skoruz.users.entity.Qualification;
import com.skoruz.users.entity.SavePdf;
import com.skoruz.users.entity.Specialization;
import com.skoruz.users.entity.User;
import com.skoruz.users.service.AdminService;
import com.skoruz.users.service.PatientService;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

//TODO : Use authentication token in ajax header and also userId 

@Component
@Path("/patient")
public class PatientRestService {
	
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	AdminService adminService;
	
	Gson gson = new Gson();
	Status status = new Status();

	@GET
	@Path("/fetchAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PatientDetails> fetchAllPatients(){
	
		List<PatientDetails> patientList = patientService.getAllPatients();
		return patientList;
		
	
	}
	
	@GET
	@Path("/get/{patientId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientDetails getPatientDetails(@PathParam("patientId") int id){
		PatientDetails patientDetails = patientService.getPatientDetails(id);
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
		//URI uri=new URI("http://app.skoruz.com:5959/tdatcWeb/index.html");
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
	public User userDetails(@PathParam("emailAddress") String emailAddress, @PathParam("password") String password){
	       User user = patientService.userDetails(emailAddress, password);
		return user;
		
	}
	
	@GET
	@Path("/updateStatus/{patientId}/{status}")
	public void updatePatientActiveStatus(@PathParam("patientId") int patientId,@PathParam("status") boolean status){
		patientService.updatePatientStatus(patientId, status);
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> registerPatientDetails(PatientDetails patientDetails){ 
		
	Integer i =	patientService.addPatientDetails(patientDetails);
	
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
		int i = patientService.addPatientCarePartner(patientCarePartner);
		
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
		int i = patientService.addPreferredPhysician(preferredPhysician);
		
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
		
		int i = patientService.addPreferredHospital(preferredHospital);
		
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
		
		int i = patientService.addPreferredPharmacy(preferredPharmacy);
		  
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
	       
	       String retMsg = patientService.updatePatientImg(filePath,id);
	       
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
		int i = patientService.addPatientMedicationDetails(medicationDetails);
		
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
		List<PatientMedicationDetails> medication = patientService.getPatientMedicationDetails(id,currentDateTime);
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
		int i = patientService.addPatientAppointmentDetails(appointmentDetails);
		
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
		Map<String,Object> appointmentDetails = patientService.getPatientAppointmentdetails(appointmentId);
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
		int i = patientService.addPatientMeasurementToolsDetails(patientMeasurementToolsDetails);
		
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
		List<PatientMeasurementToolsDetails> toolsDetails  = patientService.getPatientMeasurementToolsDetails(measurementToolId, patientId);
		return Response.ok(toolsDetails).build();
		//return toolsDetails;
		
	}
	
	
	@DELETE
	@Path("/deletePatientMeasurementTools/{Id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Status deletePatientMeasurementToolsDetails(@PathParam("Id") int Id){
	int i  = patientService.deletePatientMeasurementToolsDetails(Id);
	
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
		patientService.updatePatientProfile(user);
		return Response.ok(user).build();
	}
	
	@GET
	@Path("/getMadicine")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMedicine(){
	List<Medicine> medicines = patientService.getMedicine();
	return Response.ok(medicines).build();
	}
	
	@POST
	@Path("/medicine")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status medicine(Medicine medicine){
		int i = patientService.addMedicine(medicine);
		
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
	List<Allergies> allergies	= patientService.getAllergies();
		return Response.ok(allergies).build();
	
	}
	
	 @POST
	 @Path("/savePatientAllergyDetails")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces(MediaType.APPLICATION_JSON)
	 public  Status patientAllergyDetails(PatientAllergyDetails patientAllergyDetails ){
	  
	  int  i=patientService.savePatientAllergyDetails(patientAllergyDetails);
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
		List<PatientAllergyDetails> allergyDetails = patientService.getPatientAllergyDetails(patientID);
		return allergyDetails;
		 
	 }
	 
	 
	 @GET
	 @Path("/getMedicationProcedure")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<MedicationProcedure> getMedicationProcedure(){
		List<MedicationProcedure> medicationProcedure =  patientService.getMedicationProcedure();
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
	  int  i= patientService.savePDF(savePDF);
	  try{
	   
	  FileOutputStream fileOuputStream = new FileOutputStream(ApplicationConstants.DIRECTORY+ApplicationConstants.FILE+savePDF.getFileName()); 
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
	  
	  @GET
	  @Path("/getPdfDetails/{patientId}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public   List<SavePdf> getPdfDetails(@PathParam("patientId")   int patientId){
	   
	  /*String prefixFilePath=ApplicationConstants.DIRECTORY+ApplicationConstants.FILE;
	  InputStream in;
	  Boolean bool = false;*/
	 
	  List<SavePdf> list=patientService.getPDFDetails(patientId);
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
		  List<PatientMedicationDetails> medicationremainder = patientService.getMedicationRemainder(patientId,readUnread);
		  return Response.ok(medicationremainder).build();
	  }
	  
	  
	  @GET
	  @Path("/getSavePdfRemainder/{patientId}/{readUnread}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<SavePdf> getSavePdfRemainder(@PathParam("patientId")  int patientId, @PathParam("readUnread") int readUnread){
      List<SavePdf> savepdfdetails = patientService.getSavePdfRemainder(patientId,readUnread);
      return savepdfdetails;
	  }
	  
	  
	  @GET
	  @Path("/getPatientAppointmentRemainder/{patientId}/{readUnread}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<PatientAppointmentDetails> getPatientAppointmentRemainder(@PathParam("patientId")  int patientId, @PathParam("readUnread") int readUnread){
      List<PatientAppointmentDetails> patientappointments = patientService.getPatientAppointmentRemainder(patientId,readUnread);
      return patientappointments;
	  }
	  
	
	@GET
	@Path("/getQualification")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQualification(){
		List<Qualification> qualification = patientService.getQualification();
		return Response.ok(qualification).build();
	}
	  
	@POST
	@Path("/updateMedicationStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response patientMedicationUpdateStatus(PatientMedicationDetails medicationDetails){
		 patientService.patientMedicationUpdateStatus(medicationDetails);
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
		int  i=patientService.patientLocationUpdate(userId,longitude,lattitude);
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
		 patientService.patientAppointmentUpdateStatus(appointmentDetails);
		  
		Status status2 = new Status();
		
		status2.setStatus("success");
		return Response.ok(status2).build();
		
	}

	@POST
	@Path("/updateSavePdfStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response patientSavePdfUpdateStatus(SavePdf savePdf){
		 patientService.patientSavePdfUpdateStatus(savePdf);
		  
		Status status2 = new Status();
		
		status2.setStatus("success");
		return Response.ok(status2).build();
		
	}
	
	@GET
	@Path("/getAffiliation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAffiliation(){
		List<Affliation> affliation = patientService.getAffiliation();
		return Response.ok(affliation).build();
	}
	
	@GET
	@Path("/getLocation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLocation(){
		List<Location> location = patientService.getLocation();
		return Response.ok(location).build();
	}
	
	@GET
	@Path("/getSpecialization")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpecialization(){
		List<Specialization> specialization = patientService.getSpecialization();
		return Response.ok(specialization).build();
		
	}
	
	@GET
	 @Path("/forgetPassword/{username}")
	 @Produces(MediaType.APPLICATION_JSON )
	 public String forgetPassword(@PathParam("username")  String  userName ){
	  String result=patientService.getForgetPassword(userName);
	  
	     return result;
	     
	 }
	
	
	 
	@POST
	@Path("/savePatientPhysician/{patientId}/{physicianId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  Response savePatientPhysician(@PathParam("patientId")  int patientId,@PathParam("physicianId")int physicianId){
		int  i=patientService.savePatientPhysician(patientId,physicianId);
		
		if(i>0){
			status.setStatus("success");
		}else {
			status.setStatus("failure");
		}
		
	    return Response.ok(status).build();
	
	}
	
	
	/*@POST
	@Path("/savePatientPhysicianObject")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response savePatientPhysicianObject(PatientPhysicianMap patientphysicianmap){
		int  i=patientService.savePatientPhysicianObject(patientphysicianmap);
		
		//Status status = new Status();
		
		if(i>0){
			status.setStatus("success");
		} else{
			status.setStatus("failure");
		}
		return  Response.ok(status).build();
		
		
	}*/
	
	@DELETE
	@Path("/deletePatientPhysicianMap/{patientId}/{physicianId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public  Response deletePatientPhysicianMap(@PathParam("patientId")  int patientid,@PathParam("physicianId")  int physicianid){
		int  i=patientService.deletePatientPhysicianMap(patientid,physicianid);
				
		if(i>0){
			status.setStatus("success");
		} else{
			status.setStatus("failure");
		}
		return Response.ok(status).build();
	
	}
	
	@GET
	@Path("/getCountryCodes/{countrycode}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Country>  getCountryCodes(@PathParam("countrycode")  String countrycode){
		List<Country>  list=patientService.getCountryCodes(countrycode);
		return list;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
	
	