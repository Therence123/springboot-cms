package com.saasflux.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.saasflux.models.User;
import com.saasflux.repository.UserRepository;

@Configuration
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	  UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username).orElseThrow(
		        () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
		 
		    return UserPrinciple.build(user);
	}
	


}
