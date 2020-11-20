package com.saasflux.security.jwt;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
 
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private String username;
  private Collection authorities;
  
  
  
public JwtResponse(String token, String username, Collection authorities) {
	this.token = token;
	this.username = username;
	this.authorities = authorities;
}



public String getToken() {
	return token;
}



public void setToken(String token) {
	this.token = token;
}


public String getUsername() {
	return username;
}



public void setUsername(String username) {
	this.username = username;
}



public Collection getAuthorities() {
	return authorities;
}



public void setAuthorities(Collection authorities) {
	this.authorities = authorities;
}
  
  
}