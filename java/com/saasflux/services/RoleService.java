package com.saasflux.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saasflux.models.Role;
import com.saasflux.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public Role roleCreation(Role role) {
		
		return roleRepository.save(role);
	}

}
