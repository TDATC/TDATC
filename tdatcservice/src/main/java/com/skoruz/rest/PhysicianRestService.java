package com.skoruz.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skoruz.common.AppUtil;
import com.skoruz.entity.Status;
import com.skoruz.users.entity.PhysicianAvailability;
import com.skoruz.users.entity.PhysicianDetails;
import com.skoruz.users.entity.User;
import com.skoruz.users.service.PhysicianService;

@Component
@Path("/physician")
public class PhysicianRestService {
	
	@Autowired
	PhysicianService physicianService;
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Status registerPhysicianDetails(PhysicianDetails physicianDetails){
		
	int i=physicianService.addPhysicianDetails(physicianDetails);
		
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
	public List<User> searchPhysicians(@PathParam("searchCriteria") String searchCriteria){
		
		List<User> user =	physicianService.getPhysicianDetails(searchCriteria);
	  
	    return user;
}
	
	@POST
	@Path("/physicianAvailability")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Status physicianAvailability(PhysicianAvailability availability  ){
		int i = physicianService.addPhysicianAvailability(availability);
		
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
		PhysicianDetails physicianDetails = physicianService.getPhysicianAvailability(physicianId);
		return physicianDetails;
	}
	
	@GET
	@Path("/getPhysicianDetails/{emailAddress}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response getPhysicianDetails(@PathParam("emailAddress") String emailAddress, @PathParam("password") String password){
		PhysicianDetails physicianDetails = physicianService.getPhysicianDetails(emailAddress,password);
		return Response.ok(physicianDetails).build();

	}
	
	
	@GET
	@Path("/getPhysicianDetail/{physicianId}")
	@Produces(MediaType.APPLICATION_JSON)
	public  Response getPhysicianDetail(@PathParam("physicianId") int physicianId){
		PhysicianDetails physicianDetails = physicianService.getPhysicianDetail(physicianId);
		if (physicianDetails.getUser()!=null) {
			if (physicianDetails.getUser().getPhotoPath()!=null) {
				if (!physicianDetails.getUser().getPhotoPath().equalsIgnoreCase("null") && !physicianDetails.getUser().getPhotoPath().isEmpty()) {
					physicianDetails.getUser().setPhotoPath(AppUtil.imagePath(physicianDetails.getUser().getPhotoPath()));
				}
			}
		}
		return Response.ok(physicianDetails).build();

	}
	
	
	
	
	
	
	

	@GET
	@Path("/getAllPhysician/{patientId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPhysicians(@PathParam("patientId") int patientId){
		List<PhysicianDetails> physicianDetailsList=physicianService.getAllPhysicianList(patientId);
		return Response.ok(physicianDetailsList).build();
	}
	
	@GET
	@Path("/getMappedPhysician/{patientId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMappedPhysicians(@PathParam("patientId") int id){
		List<PhysicianDetails> patientPhysicianMaps=physicianService.getMappedPhysicians(id);
		return Response.ok(patientPhysicianMaps).build();
	}
	

	@POST
	@Path("/PhysicianSignUp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  Status  physicianSignUp(PhysicianDetails physiciandetails){

		int  i=physicianService.physicianSignUp(physiciandetails) ;
		Status  status=new Status();
		if(i>0){

			status.setStatus("success");
			return status;
		}else{

			status.setStatus("failure");
			return status;
		}

	}
	

	@POST
	@Path("/physicianProfileUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String physicianProfileUpdate(PhysicianDetails physicianDetails){
		//physicianService.physicianProfileUpdate(physicianDetails);
		//return Response.ok(physicianDetails).build();
		return physicianService.physicianProfileUpdate(physicianDetails);
		
	}
	
/*
	@POST
	@Path("/updateHospitalBranchs")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateHospitalBranchDetails(HospitalBranches hospitalBranches){
	System.out.println("Branch Id **************"+ hospitalBranches.getBranchId() +" Hospital Id*******"+hospitalBranches.getHospital())	;
    return physicianService.updateHospitalBranchDetails(hospitalBranches);
		
	}
*/
	

	
}





