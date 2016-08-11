package com.skoruz.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skoruz.entity.UserType;
import com.skoruz.users.bean.LoginStatus;
import com.skoruz.users.dao.AdminDAO;
import com.skoruz.users.dao.PatientDAO;
import com.skoruz.users.entity.HospitalAdministrator;
import com.skoruz.users.entity.PharmacyAdministrator;
import com.skoruz.users.entity.User;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	PatientDAO patientDao;
	
	@Autowired
	AdminDAO adminDao;  
	
	@Override
	@Transactional
	public LoginStatus isValidAdminUser(String userName, String password) {
		List<User> adminList = patientDao.getAllAdministrators();
		//List<User> adminList = adminDAO.getAllAdministrators();
		for(User user : adminList){
			if(user.getEmailAddress().equals(userName) && user.getPassword().equals(password)){
				return new LoginStatus(user.getUser_id(), user.getUser_type(), true);
			}
		}
		//TODO have unknown as userType and pass here
		return new LoginStatus(0, "A", false);
	}
	

	@Override
	@Transactional
	public HospitalAdministrator getHospitalAdmin(int id) {
		// TODO Auto-generated method stub
		HospitalAdministrator admin = patientDao.getHospitalAdmin(id);
		return admin;
	}
	
	@Override
	@Transactional
	public PharmacyAdministrator getPharmacyAdministrator(int id) {
		PharmacyAdministrator admin = patientDao.getPharmacyAdministrator(id);
		return admin;
	}

	

	
	
	
}
