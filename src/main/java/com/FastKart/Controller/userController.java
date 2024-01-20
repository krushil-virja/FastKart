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

	@PostMapping("/userRegister")
	public String userRegister(@Valid @ModelAttribute User user, BindingResult result,
			@RequestParam(value = "checkbox", defaultValue = "false") boolean checkbox, Model m,HttpSession session,RedirectAttributes redirAttrs) {

		if (result.hasErrors()) {

			System.out.println(result);
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
		String message = ""
				+ "<table border='0' cellpadding='0' cellspacing='0' width='100%'>"
				+ "<tr>"
				+ "<td bgcolor='#F9FAFC'>"
				+ "<div align='center' style='padding:5px 0;'>"
				+ "<table border='0' cellpadding='0' cellspacing='0' style='font-family:Arial,Helvetica,sans-serif;font-size:16px;line-height:1.5em;max-width: 500px;'>"
				+ "<thead>"
				+ "<tr>"
				+ "<td style='text-align: center;'>"
				+ "<img src='https://i0.wp.com/www.writefromscratch.com/wp-content/uploads/2018/12/demo-logo.png?ssl=1' style='margin-bottom: 1rem; width: 110px;' alt=''>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td style='background-color: #1f74ca; color: white; padding: 0 20px; border-radius: 15px 15px 0 0;'>"
				+ "<h2 align='center'>Thank You</h2>"
				+ "</td>"
				+ "</tr>"
				+ "</thead>"
				+ "<tbody style='background-color: white;padding: 40px 20px;border-radius: 0 0 15px 15px;display: block;box-shadow: 0 10px 30px -30px black;'>"
				+ "<tr>"
				+ "<td>"
				+ "<p align='center' style='margin: 0; color: #475467;'>Hey,<strong>User</strong></p>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>"
				+ "<p align='center' style='color: #7a899f;margin-bottom: 0;font-size: 14px;'>Thank you for registered.We're so excited to share the latest news and updates about our product with you.If you'd like to learn more, follow us on social media!</p>"
				+ "</td>"
				+ "</tr>"
				+ "</tbody>"
				+ "<tfoot>"
				+ "<tr>"
				+ "<td>"
				+ "<p align='center'>"
				+ "<small style='color:#7a899f;'>&copy;2023 Copyright <a href='#' target='_blank' style='text-decoration: none; color: #1f74ca;'>Virgin Mobile</a>. All Rights Reserved.</small>"
				+ "</p>" + "</td>" + "</tr>" + "</tfoot>" + "</table>" + "</div>" + "</td>"
				+ "</tr>" + "</table>";
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
