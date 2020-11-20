package com.saasflux.models;

import java.util.Set;

public class Signup {

	private String name;
	private String username;
	private String password;
	private String email;
	private Set Role;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set getRole() {
		return Role;
	}
	public void setRole(Set role) {
		Role = role;
	}
	
	
	
	
}
