package com.saasflux.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saasflux.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	Optional<User> findByUsername(String username);
	
    Boolean existsByUsername(String username);
    
    Boolean existsByEmail(String email);
    
    Boolean existsByToken(String token);
    
    User findByEmail(String email);

	User findByToken(String token);
	
	User findByPassword(String password);
	
}
