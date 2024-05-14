package com.FastKart.Config;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomAuthenticationFailureHandler  implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		String errorMessage = "Invalid email or password. Please try again.";

        if (exception instanceof BadCredentialsException) {
            errorMessage = "Invalid email or password. Please check your credentials and try again.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "Email address not found. Please check your email address and try again.";
        }

        
       HttpSession session = request.getSession();
       session.setAttribute("errorMessage", errorMessage);

        // Redirect the user back to the login page with the error message.
        response.sendRedirect("/login");
	}

}
