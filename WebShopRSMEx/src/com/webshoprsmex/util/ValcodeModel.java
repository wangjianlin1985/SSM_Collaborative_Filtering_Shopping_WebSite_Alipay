package com.webshoprsmex.util;

import com.webshoprsmex.model.User;

/**
 * 验证码model类
 */
public class ValcodeModel {

	private User user;
	
	private String valcode;

	public ValcodeModel() {
		super();
	}
	
	public ValcodeModel(User user, String valcode) {
		super();
		this.user = user;
		this.valcode = valcode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getValcode() {
		return valcode;
	}

	public void setValcode(String valcode) {
		this.valcode = valcode;
	}
	
}
