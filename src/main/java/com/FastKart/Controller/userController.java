package com.FastKart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FastKart.Dao.userDao;
import com.FastKart.Repository.UserRepository;
import com.FastKart.email.emailServices;
import com.FastKart.entities.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class userController {

	@Autowired
	private userDao udao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private emailServices mailservice;
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/userRegister")
	public String userRegister(@Valid @ModelAttribute User user, BindingResult result,
			@RequestParam(value = "checkbox", defaultValue = "false") boolean checkbox, Model m,HttpSession session,RedirectAttributes redirAttrs) {

		if (result.hasErrors()) {

			System.out.println(result);
			return "register";
		}

   		
		boolean existsByEmail = userRepository.existsByEmail(user.getEmail());
		
		/*
		 * if(existsByEmail) {
		 * 
		 * System.out.println("Email is already taken"); m.addAttribute("error",
		 * "Email is already taken"); return "register"; }
		 */
		
		 if (existsByEmail) {
		        result.rejectValue("email", "error.user", "Email already exists");
		        return "register";
		    }
		
	 		// if checkbox is not checked by user than this if condition is executed
		if (!checkbox) {

			System.out.println("you have not checked terms & condition");
			return "register";

		}

		user.setRole("user");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	
		// mail Configuration
		
		String form="virjakrushil@gmail.com";
		String subject = "Register Successfully";
		String message = "user register successfully";
				
		String To=user.getEmail();
		
		mailservice.sendMail(To, subject, message);
		
		
		
		User u = udao.userRegister(user);

		
		 if (u != null) {
			 System.out.println("User registered successfully: " + u.getName());
			 redirAttrs.addFlashAttribute("success",  "Register successfully"); 
			 }
		      else {
		 System.out.println("Failed to register user");
		 
		 redirAttrs.addFlashAttribute("error", "Something went wrong"); }
		
		return "redirect:/login";
	}
}
