package com.skoruz.users.service;

import com.skoruz.users.bean.LoginStatus;
import com.skoruz.users.entity.HospitalAdministrator;
import com.skoruz.users.entity.PharmacyAdministrator;

public interface AdminService {
	
	public LoginStatus isValidAdminUser(String userName, String password);
	public HospitalAdministrator getHospitalAdmin(int id);
	public PharmacyAdministrator getPharmacyAdministrator(int id);

}
