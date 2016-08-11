package com.skoruz.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.skoruz.users.bean.LoginStatus;
import com.skoruz.users.dao.PatientMobileDAO;
import com.skoruz.users.entity.HospitalAdministrator;
import com.skoruz.users.entity.PharmacyAdministrator;
import com.skoruz.users.entity.User;

/*@Service*/
public class AdminMobileServiceImpl implements AdminMobileService {

	@Autowired
	PatientMobileDAO patientMobileDao;
	/*@Autowired
	AdminMobileDAO adminMobileDao;  
	*/
	@Override
	@Transactional
	public LoginStatus isValidAdminUser(String userName, String password) {
		List<User> adminList = patientMobileDao.getAllAdministrators();
		//List<User> adminList = adminDAO.getAllAdministrators();
		for(User user : adminList){
			if(user.getEmailAddress().equals(userName) && user.getPassword().equals(password)){
				return new LoginStatus(user.getUser_id(), user.getUser_type(), true);
			}
		}
		//TODO have unknown as userType and pass here
		return new LoginStatus(0, "A", false);
		//return null;
	}
	

	@Override
	@Transactional
	public HospitalAdministrator getHospitalAdmin(int id) {
		// TODO Auto-generated method stub
		HospitalAdministrator admin = patientMobileDao.getHospitalAdmin(id);
		return admin;
	}
	
	@Override
	@Transactional
	public PharmacyAdministrator getPharmacyAdministrator(int id) {
		PharmacyAdministrator admin = patientMobileDao.getPharmacyAdministrator(id);
		return admin;
	}

	
}
