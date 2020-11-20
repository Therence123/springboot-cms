package com.saasflux.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saasflux.repository.UserRepository;
import com.saasflux.models.*;
import com.saasflux.repository.*;
import com.saasflux.security.jwt.*;

@RestController
@RequestMapping("/api/v1/")
public class UserAuthController {
	
	@Autowired
	  AuthenticationManager authenticationManager;
	 
	  @Autowired
	  UserRepository userRepository;
	  
	  
	  @Autowired
	  RoleRepository roleRepository;
	 
	  @Autowired
	  PasswordEncoder encoder;
	 
	  @Autowired
	  JwtProvider jwtProvider;
	  
		@Autowired
		public JavaMailSender javaMailSender;
	  
	  
	 //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')") 
	  @PostMapping("/signin")
	  public ResponseEntity authenticateUser(@RequestBody Login loginRequest) {
	 
	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	 
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	 
	    String jwt = jwtProvider.generateJwtToken(authentication);
	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	 
	    return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	  }

	  
	  // For registering new users
//	@PreAuthorize("hasRole('ADMIN')") 
	  @PostMapping("/signup")
	  public ResponseEntity registerUser(@RequestBody Signup signUpRequest) {
	    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
	      return new ResponseEntity<>(new ResponseMessage("Fail -> This username is already taken!"),
	          HttpStatus.BAD_REQUEST);
	    }
	 
	    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
	      return new ResponseEntity<>(new ResponseMessage("Fail -> This email is already taken!"),
	          HttpStatus.BAD_REQUEST);
	    }
	 
	   
	 // Creating user's account
	    User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
	        encoder.encode(signUpRequest.getPassword()));
	    
	    
	 		Set<String> strRoles = signUpRequest.getRole();
	 		Set<Role> roles = new HashSet<>();


	 
	    strRoles.forEach(role -> {
	      switch (role) {
	      case "admin":
	        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
	            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	        roles.add(adminRole);
	 
	      default:
	        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
	            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	        roles.add(userRole);
	      }
	    });
	 
	    user.setRoles(roles);
	    userRepository.save(user);
	    
	    //Send mail notification
	      User userObj = null;	    
  	String tempEmailId = signUpRequest.getEmail();
  	String url = "http://localhost:4200/cmsapp/password-reset";

	    
	    SimpleMailMessage message = new SimpleMailMessage();
  	message.setTo(tempEmailId);
  	message.setSubject("TMG-GHANA CMS");
  	message.setText("Welcome to CMS, You have been setup by the administrator\n"+"Kindly proceed by tapping on the link below to reset your password "+
  	url); 
  	
  	javaMailSender.send(message);
	 
	    return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	  }
	  
	  
	  //This will be used to push the roles into the database via API in production
	  @PostMapping("/createrole")
	  public Role addNewRole(@RequestBody Role role) {
		  return roleRepository.save(role);
	  }
	
	  
}	    

