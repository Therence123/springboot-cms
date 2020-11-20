package com.saasflux.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.saasflux.models.User;
import com.saasflux.repository.UserRepository;


@RestController
@RequestMapping("api/usercontrol")
public class PasswordResetController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public JavaMailSender javaMailSender;
	
	  @Autowired
	 private PasswordEncoder encoder;

	

	       // Display the form
	        @GetMapping("/forgot-password")
	       public ModelAndView displayResetPassword(ModelAndView modelAndView, User user) {
	           modelAndView.addObject("user", user);
	           modelAndView.setViewName("forgotPassword");
	           return modelAndView;
	       }
	

	
	    // Receive the address and send an email

	        @PostMapping("/confirm-email")
	       public ModelAndView forgotUserPassword(@RequestParam String email,ModelAndView modelAndView) {
	    	   
	        	                 
                  if (email != null && userRepository.existsByEmail(email)) {
	        	   
                	  User user = userRepository.findByEmail(email);
                	 
                	  
	        	// Generate random 36-character string token for reset password 
	   			user.setToken(UUID.randomUUID().toString());
   			

	   			// Save token to database
	   			userRepository.save(user);

	               
	               User userObj = null;	    
	             	String tempEmailId = user.getEmail();
	             		               
	               // Create the email
	               SimpleMailMessage message = new SimpleMailMessage();
	               message.setTo(tempEmailId);
	               message.setSubject("PASSWORD RESET CONFIRMATION");
	               message.setText("To complete the password reset process, please click here: "
	                 + "http://localhost:8083/api/usercontrol/reset-password?token=" + user.getToken());

	               // Send the email
	               javaMailSender.send(message);
	               
	               
	               modelAndView.addObject("message", "Request to reset password received. Check your inbox for the reset link.");
	               modelAndView.setViewName("successForgotPassword");

	           } else {
	               modelAndView.addObject("message", "This email address does not exist!");
	               modelAndView.setViewName("error");
	           }
	           return modelAndView;
	       }
	        
	        
	        
	        // Endpoint to confirm the token

	        @GetMapping("/reset-password")
	        public ModelAndView validateResetToken(@RequestParam("token") String token ,ModelAndView modelAndView) {
	        	
	        	User user = userRepository.findByToken(token);
	        	     String email = user.getEmail();
            	       	   
	            if (token != null || userRepository.existsByToken(token) || userRepository.existsByEmail(email)) {           	            	 
	             	            	            
	            	      
	                modelAndView.addObject("email", email);
	                modelAndView.setViewName("resetPassword");
	            } else {
	                modelAndView.addObject("message", "The link is invalid or broken!");
	                modelAndView.setViewName("error");
	            }
	            return modelAndView;
	        }
	        
	        
	        // Endpoint to update a user's password     	        
	        @PostMapping("/reset-password")
	        public String postrResetPasswordForm(@RequestParam("email") String email, @RequestParam("password") String password){
	            User user = userRepository.findByEmail(email);
	                	            
	            if(user != null){
	                user.setPassword(encoder.encode(password));
	                 userRepository.save(user);
//	                user.setToken("null");
	            }
	               
	                           
             	String tempEmailId = user.getEmail();
	            
	            // Create the email to alert user of successful password reset
	               SimpleMailMessage alert = new SimpleMailMessage();
	               alert.setTo(tempEmailId);
	               alert.setSubject("SUCCESSFUL PASSWORD RESET");
	               alert.setText("Your account password has been successfully updated , please click here: "
	                 + "http://localhost:8083/api/v1/login " +" to login" );

	               // Send the email
	               javaMailSender.send(alert);
	               
	               return "Password Reset is Successful!";   
	        }
}
	
	
