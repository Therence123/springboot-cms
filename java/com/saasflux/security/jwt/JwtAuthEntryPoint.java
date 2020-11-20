package com.saasflux.security.jwt;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


@Configuration
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);
   
   @Override
   public void commence(HttpServletRequest request,
                        HttpServletResponse response,
                        AuthenticationException e) 
                            throws IOException, ServletException {
     
//       logger.error("Unauthorized error. Message - {}", e.getMessage());
       response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized");
   }
}