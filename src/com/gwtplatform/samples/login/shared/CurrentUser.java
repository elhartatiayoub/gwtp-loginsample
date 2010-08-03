package com.gwtplatform.samples.login.shared;

import java.io.Serializable;
import java.util.List;

public class CurrentUser implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;
	private List<String> roles;

	public CurrentUser() {
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
