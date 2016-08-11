package com.skoruz.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skoruz.users.bean.LoginStatus;
import com.skoruz.users.service.AdminService;
import com.skoruz.users.service.PatientService;

@Component
@Path("/admin")
public class AdminRestService {
	
	@Autowired
	AdminService adminService;
	@Autowired
	PatientService patientService;
	
	
	
	@GET
	@Path("/validatingUser/{userName}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public LoginStatus validateAdmin(@PathParam("userName") String userName, @PathParam("password") String password,@Context HttpServletRequest req){
		
		HttpSession session = req.getSession(true);
		
		System.out.println("session id 2 "+session.getId());
		
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
	
	


}
