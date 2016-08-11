package com.skoruz.users.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.skoruz.users.entity.User;

@Repository
public class AdminDAOImpl implements AdminDAO{
	
	private final Logger logger = LoggerFactory.getLogger(AdminDAOImpl.class);
	
	@Autowired
    SessionFactory sessionFactory;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllAdministrators() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> adminList = session.createQuery("from User where userType in ('A','PHA','HA','PT','PHS')").list();
		return adminList;
	}

	
	

}
