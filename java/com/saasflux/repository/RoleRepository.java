package com.saasflux.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saasflux.models.Role;
import com.saasflux.models.RoleName;



@Repository
public interface RoleRepository extends JpaRepository<Role, String>{

	 Optional<Role> findByName(RoleName roleName);
	 
}
