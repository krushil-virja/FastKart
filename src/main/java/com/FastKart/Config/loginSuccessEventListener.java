package com.FastKart.Config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.FastKart.Repository.UserRepository;
import com.FastKart.email.emailServices;
import com.FastKart.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// AuthenticationSuccessHandler this interface provide us a permission to do code 

public class loginSuccessEventListener implements AuthenticationSuccessHandler {

	@Autowired
	private emailServices mailEmailServices;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		

		 UserDetails authUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 
		String from = "virjakrushil@gamil.com";
		String to = authUser.getUsername();
		String subject = "Login Successfully";
		String message = "User Login Successfully";
		
		
		mailEmailServices.sendMail(to, subject, message);
		
		String username = authUser.getUsername();
		
        User user = userRepository.getUserByUserName(username);
        
        String role = user.getRole();
        if ("user".equals(role)) {
            response.sendRedirect("home");
        } else {
            response.sendRedirect("/admin/index");
        }
		
	}

}
