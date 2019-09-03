package com.ovit.jcw.model;

public class LoginUserAuth {
	private String authKey;
	private BasicUser userInfo;

	public LoginUserAuth(String authKey, BasicUser userInfo) {
		this.authKey = authKey;

		this.userInfo = userInfo;
	}

	public BasicUser getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(BasicUser userInfo) {
		this.userInfo = userInfo;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
}