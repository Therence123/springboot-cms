package com.saasflux.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saasflux.models.User;
import com.saasflux.repository.UserRepository;

@Service("userService")
public class UserServiceImpl {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User findUserByToken(String token){
		return userRepository.findByToken(token);
	}

	
	public void save(User user) {
		userRepository.save(user);
	}
}
