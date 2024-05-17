package com.FastKart.Controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
			@RequestParam(value = "checkbox", defaultValue = "false") boolean checkbox, Model m, HttpSession session,
			RedirectAttributes redirAttrs) {

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
		// Set birthDate to null if it's an empty string
		// Convert empty string birthDate to null
		/*
		 * if (user.getBirthDate() == null || user.getBirthDate().toString().isEmpty())
		 * { user.setBirthDate(null); }
		 */

		// Convert empty string birthDate to null
		/*
		 * if (user.getBirthDate() == null ||
		 * user.getBirthDate().equals(java.sql.Date.valueOf("0000-00-00"))) {
		 * user.setBirthDate(null); }
		 */

		// Set contact to null if it's an empty string
		/*
		 * if (user.getContact() != null && String.valueOf(user.getContact()).isEmpty())
		 * { user.setContact(null); }
		 */

		// mail Configuration

		String form = "virjakrushil@gmail.com";
		String subject = "Register Successfully";
		String message = "user register successfully";

		String To = user.getEmail();

		mailservice.sendMail(To, subject, message);

		User u = udao.userRegister(user);

		if (u != null) {
			System.out.println("User registered successfully: " + u.getName());
			redirAttrs.addFlashAttribute("success", "Register successfully");
		} else {
			System.out.println("Failed to register user");

			redirAttrs.addFlashAttribute("error", "Something went wrong");
		}

		return "redirect:/login";
	}

	/*
	 * @GetMapping("/userDetails/{id}") public String userById(@PathVariable("id")
	 * int id, Model m ) {
	 * 
	 * User user = udao.getUserById(id); m.addAttribute("user", user);
	 * 
	 * return "";
	 * 
	 * }
	 */
	

	@PostMapping("/updateProfile")
	public String userUpdate(@ModelAttribute User u, @RequestParam("name") String name,
	        @RequestParam("email") String email, @RequestParam("password") String password,
	        @RequestParam("contact") Long contact, @RequestParam("gender") String gender,
	        @RequestParam(value = "birthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdate, @RequestParam("address") String address,
	        @RequestParam("pImage") MultipartFile file, // Change the parameter type here
	        @RequestParam("id") int id) {
		
		  
		 System.out.println(name);
		 System.out.println(email);
		 System.out.println(password);
		 System.out.println(contact);
		 System.out.println(gender);
		 System.out.println(address);
	    System.out.println("Birth Date from form: " + birthdate); // Debugging statement
		
		User user = userRepository.findById(id).get();
		  System.out.println(user);
		
		if(user!=null) {			 
			 user.setId(id);
			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);
			user.setContact(contact);
			user.setGender(gender);
			user.setBirthDate(birthdate);
			user.setAddress(address);
			
			
			user.setProfileImage(user.getProfileImage());
			
			userRepository.save(user);
		}
		
		return  "redirect:/userDashboard";
		
	}

}
