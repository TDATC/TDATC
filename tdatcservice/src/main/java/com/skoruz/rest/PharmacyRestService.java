/**
 * 
 */
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

import com.skoruz.pharmacy.entity.Pharmacy;
import com.skoruz.pharmacy.entity.PharmacyBranches;
import com.skoruz.pharmacy.service.PharmacyService;

@Component
@Path("/pharmacy")
public class PharmacyRestService {

	@Autowired
	PharmacyService pharmacyService;
	
	@GET
	@Path("/fetchAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pharmacy> fetchAllPharmacies(){
		List<Pharmacy> pharmacies = pharmacyService.fetchAllPharmacies();
		return pharmacies;
	}
	
	
	@GET
	@Path("/get/{pharmacyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Pharmacy getPharmacy(@PathParam("pharmacyId") int pharmacyId){
		Pharmacy pharmacy = pharmacyService.getPharmacy(pharmacyId);
		return pharmacy;
	}
	
	
	@POST
	@Path("/put")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String registerPharmacy(Pharmacy pharmacy){
		
		int id = pharmacyService.addPharmacy(pharmacy);
		if(id > 0){
			return "success";
		}else{
			return "failure";
		}
	}
	
	@GET
	@Path("/getBranchPharmacy/{branchId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Pharmacy getBranchPharmacy(@PathParam("branchId") int id){
	Pharmacy pharmacy =	pharmacyService.getBranchPharmacyDetails(id);
		return pharmacy;
	}
	
	
	@GET
	@Path("/getBranchDetails/{branchId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PharmacyBranches getBranchDetails(@PathParam("branchId") int branchId){
	 PharmacyBranches branch =	pharmacyService.getBranchDetails(branchId);
		return branch;
		
	}
}
