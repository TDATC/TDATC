package com.skoruz.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skoruz.hospitals.entity.Hospital;
import com.skoruz.hospitals.entity.HospitalBranches;
import com.skoruz.hospitals.entity.HospitalBranchesDTO;
import com.skoruz.hospitals.service.HospitalServices;

@Component
@Path("/hospitals")
public class HospitalRestServices {
	

	@Autowired
	HospitalServices hospitalService;
	
	@Path("/fetchAll")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hospital> fetchAllHospitals(){
		List<Hospital> hospitalList = hospitalService.fetchAllHospitals();
		return hospitalList;
	}
	
	
	@GET
	@Path("/get/{hospitalId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Hospital getHospital(@PathParam("hospitalId") int hospitalId){
		Hospital hospital = hospitalService.getHospital(hospitalId);
		return hospital;
	}
	
	@POST
	@Path("/put")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String registerHospital(Hospital hospital){

			int i = hospitalService.addHospital(hospital);
			
			if(i > 0){
			return "success";
			}else{
				return "failure";
			}
	}
	
	@GET
	@Path("/getHospital/{branchId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Hospital getBranchHospitalDetails(@PathParam("branchId")int branchId){
		Hospital hospital = hospitalService.getHospitalDetails(branchId);
		return hospital;
		
	}
	
	
	@GET
	@Path("/getBranchDetails/{branchId}") 
	@Produces(MediaType.APPLICATION_JSON)
	public HospitalBranches getBranchDetails(@PathParam("branchId") int branchId){
		HospitalBranches branch = hospitalService.getBranchDetails(branchId);
		return branch;
	}
	
	@GET
	@Path("/getBranchDetailsDTO/{branchId}") 
	@Produces(MediaType.APPLICATION_JSON)
	public HospitalBranchesDTO getBranch(@PathParam("branchId") int branchId){
		HospitalBranchesDTO branch = hospitalService.getBranch(branchId);
		return branch;
	}
	
	
	
	@POST
	@Path("/updateHospitalBranchs")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateHospitalBranchDetails(HospitalBranches hospitalBranches){
	System.out.println("Branch Id **************"+ hospitalBranches.getBranchId() +" Hospital Id*******"+hospitalBranches.getHospital())	;
    return hospitalService.updateHospitalBranchDetails(hospitalBranches);
		
	}
	
}
