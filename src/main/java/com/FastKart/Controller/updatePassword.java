package com.FastKart.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FastKart.Repository.UserRepository;
import com.FastKart.entities.User;

@Controller
public class updatePassword {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/updatePassword")
	public String UpdatePassword(@RequestParam("newPassword") String newPassword,
	                             @RequestParam("oldPassword") String oldPassword,
	                             Principal principal, RedirectAttributes redirAttrs) {

	    String name = principal.getName(); // we set email as a userName in our security configuration
	    
	    User user = userRepository.getUserByUserName(name);
	    
	    if (user != null) {
	        String password = user.getPassword();
	        
	        if (!passwordEncoder.matches(oldPassword, password)) {
	            redirAttrs.addFlashAttribute("error", "Old password is incorrect");
	            return "redirect:/updatePassword"; // Redirect back to the password change form
	        }
	        
	        if (newPassword.equals(oldPassword)) {
	            redirAttrs.addFlashAttribute("error", "New password cannot be the same as the old password");
	            return "redirect:/updatePassword"; // Redirect back to the password change form
	        }

	        user.setPassword(passwordEncoder.encode(newPassword));
	        userRepository.save(user); // Save the updated user entity with the new password
	        
	        redirAttrs.addFlashAttribute("success", "Your password has been successfully updated");
	        return "redirect:/home"; // Redirect to the dashboard or any other page
	    } else {
	        return "redirect:/login";
	    }
	}
}
	
