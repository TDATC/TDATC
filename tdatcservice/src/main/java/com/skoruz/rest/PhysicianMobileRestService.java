package com.skoruz.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skoruz.common.AppUtil;
import com.skoruz.entity.Status;
import com.skoruz.users.service.PhysicianMobileService;
import com.skoruz.users.entity.PhysicianAvailability;
import com.skoruz.users.entity.PhysicianDetails;

@Component
@Path("/mobile/physician")
public class PhysicianMobileRestService {
	
	@Autowired
	PhysicianMobileService physicianMobileService;
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Status registerPhysicianDetails(PhysicianDetails physicianDetails){
		
	int i=physicianMobileService.addPhysicianDetails(physicianDetails);
		
	Status status = new Status();
	
	if(i>0){
		status.setStatus("success");
		return status;
	} else {
		status.setStatus("failure");
		return status;
	}
	}
	
	@GET
	@Path("/search/{searchCriteria}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PhysicianDetails> searchPhysicians(@PathParam("searchCriteria") String searchCriteria){
		
		List<PhysicianDetails> physicianList =	physicianMobileService.getPhysicianDetails(searchCriteria);
	  
	    return physicianList;
}
	
	@POST
	@Path("/physicianAvailability")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Status physicianAvailability(PhysicianAvailability availability  ){
		int i = physicianMobileService.addPhysicianAvailability(availability);
		
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
	@Path("/getPhysicianAvailability/{physicianId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PhysicianDetails getPhysicianAvailability(@PathParam("physicianId") int physicianId){
		PhysicianDetails physicianDetails = physicianMobileService.getPhysicianAvailability(physicianId);
		return physicianDetails;
	}
	
	@GET
	@Path("/getPhysicianDetails/{emailAddress}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response getPhysicianDetails(@PathParam("emailAddress") String emailAddress, @PathParam("password") String password){
		PhysicianDetails physicianDetails = physicianMobileService.getPhysicianDetails(emailAddress,password);
		return Response.ok(physicianDetails).build();

	}
	
	@GET
	@Path("/getPhysicianDetail/{physicianId}")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response getPhysicianDetail(@PathParam("physicianId") int physicianId){
		PhysicianDetails physicianDetails = physicianMobileService.getPhysicianDetail(physicianId);
		return Response.ok(physicianDetails).build();

	}
	

	@GET
	@Path("/getAllPhysician/{patientId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPhysicians(@PathParam("patientId") int patientId){
		List<PhysicianDetails> physicianDetailsList=physicianMobileService.getPhysicianList(patientId);
		return Response.ok(physicianDetailsList).build();
	}
	
	@GET
	@Path("/getMappedPhysician/{patientId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMappedPhysicians(@PathParam("patientId") int id){
		List<PhysicianDetails> patientPhysicianMaps=physicianMobileService.getMappedPhysicians(id);
		return Response.ok(patientPhysicianMaps).build();
	}
	

	@POST
	@Path("/PhysicianSignUp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  Status  physicianSignUp(PhysicianDetails physiciandetails){

		int  i=physicianMobileService.physicianSignUp(physiciandetails) ;
		Status  status=new Status();
		if(i>0){

			status.setStatus("success");
			return status;
		}else{

			status.setStatus("failure");
			return status;
		}

	}
	

	@PUT
	@Path("/physicianProfileUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public   Status  physicianProfileUpdate(PhysicianDetails physicianDetails){
		physicianMobileService.physicianProfileUpdate(physicianDetails);
		/*return Response.ok(physicianDetails).build();*/
		Status  status=new Status();
		status.setStatus("success");
		return status;
	
}
	
	
	

	@POST
	@Path("/deleteMappedPhysician/{patientId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMappedPhysician(@PathParam("patientId") int id,Map<String, String> doctorIdMap){
		//String doctorIds=doctorIdMap.get("");
		int i = physicianMobileService.deleteMappedPhysician(id, doctorIdMap);
		Status status = new Status();
		
		if(i>0){
			status.setStatus("success");
		} else{
			status.setStatus("failure");
		}
		return Response.ok(status).build();
		
	}
	

	@GET
	@Path("/getSpecializedPhysician/{specialization}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpecializedPhysicianDetails(@PathParam("specialization") String specialization ){
		List<PhysicianDetails> physicianDetails = physicianMobileService.getSpecializedPhysicianDetails(specialization);
		return Response.ok(physicianDetails).build();
		
	}
	
	

	/*@GET
	@Path("/demo")
	@Produces(MediaType.APPLICATION_JSON)
	public String Demo(){
		return "welcome";
	}
	*/
	
	@GET
	@Path("/getPhysicianDetails/{physicianId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPhysicianByPhysicianId(@PathParam("physicianId") int physicianId){
		PhysicianDetails physicianDetails=physicianMobileService.getPhysicianDetail(physicianId);
		if (physicianDetails.getUser()!=null) {
			if (physicianDetails.getUser().getPhotoPath()!=null) {
				if (!physicianDetails.getUser().getPhotoPath().equalsIgnoreCase("null") && !physicianDetails.getUser().getPhotoPath().isEmpty()) {
					physicianDetails.getUser().setPhotoPath(AppUtil.imagePath(physicianDetails.getUser().getPhotoPath()));
				}
			}
		}
		return Response.ok(physicianDetails).build();
	}
}





