package com.skoruz.users.dao;

import java.util.List;

import com.skoruz.users.entity.User;

public interface AdminDAO {
	
	public List<User> getAllAdministrators();

}
