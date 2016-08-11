package com.skoruz.users.bean;

import com.skoruz.entity.UserType;

public class LoginStatus {
	
	private int loginId;
	private String loggedInUserType;
	private boolean status;
	
	public LoginStatus(int loginId, String a, boolean status) {
		this.loginId = loginId;
		this.loggedInUserType = a;
		this.status = status;
	}
	
	public int getLoginId() {
		return loginId;
	}
	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}
	public String getLoggedInUserType() {
		return loggedInUserType;
	}
	public void setLoggedInUserType(String loggedInUserType) {
		this.loggedInUserType = loggedInUserType;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	

}
