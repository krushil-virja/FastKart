package com.FastKart.Controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FastKart.Repository.UserRepository;
import com.FastKart.email.emailServices;
import com.FastKart.entities.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class forgotPasswordController {

	@Autowired
	private emailServices mailService;
	
	@Autowired
	private UserRepository userRepository;

	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	Random random = new Random(100000);

//======================================================== forgotPassword Page Handler ===========================================================
	@GetMapping("/forgotPassword")
	public String forgotPassword() {

		return "forgotPassword";
	}

//========================================================== OTP page Handler ================================================================

	@GetMapping("/otp")
	public String otp() {

		return "otp";
	}

	//========================================================== newPassword page Handler ================================================================

		@GetMapping("/newPassword")
		public String newPassword() {

			return "newPassword";
		}

//======================================== Generate Random otp and send it via mail Handler =========================================================

	@PostMapping("/sendOtp")
	public String sendOtp(@RequestParam("email") String email, HttpSession session,Model m,RedirectAttributes redirAttrs) {

		System.out.println("Email is " + email);

		// generate 6 digit random OTP
		// 100000 is a MIN value of OTP

		// 999999 is a max OTP value. OTP number will be generate between 100000 to
		// 999999
		int otp1 = random.nextInt(10);  // Generate a random digit between 0 (inclusive) and 10 (exclusive)
		int otp2 = random.nextInt(10);  // Generate another random digit
		int otp3 = random.nextInt(10);  // Generate another random digit
		int otp4 = random.nextInt(10);  // Generate another random digit
		int otp5 = random.nextInt(10);  // Generate another random digit
		int otp6 = random.nextInt(10);  // Generate another random digit

		// Concatenate the digits to form a 6-digit OTP
		int generatedOTP = otp1 * 100000 + otp2 * 10000 + otp3 * 1000 + otp4 * 100 + otp5 * 10 + otp6;

		System.out.println("OTP IS " + generatedOTP);

		// send email using this configuration

		String from = "virjakrushil@gmail.com";
		String to = email;
		String subject = "OTP send Successfully";

		String message = "OTP= " + generatedOTP + " </h1>";

		boolean flag = mailService.sendMail(to, subject, message);
		if (flag) {
			
			session.setAttribute("generatedOTP", generatedOTP); 
			session.setAttribute("email",email);
			
				
			
		// we need this OTP while verifying OTP so we can easily fetch this random OTP which send on user email, we match user enter OTP with random OTP; 
			
			// session.setAttribute("message", " We have send OTP to your email id ");
			
			redirAttrs.addFlashAttribute("message", "We have send OTP to your email id");
			
			
			
			return "redirect:/otp";
		} else {
			return "redirect:/forgotPassword";
		}
	}
	
	
//============================================ verifying OTP handler =============================================================================
	@PostMapping("/verify-otp")
	public String verifyOpt(@RequestParam("otp1") int otp1,@RequestParam("otp2") int otp2,@RequestParam("otp3") int otp3,
			@RequestParam("otp4") int otp4,@RequestParam("otp5") int otp5, @RequestParam("otp6") int otp6, HttpSession session,RedirectAttributes redirAttrs){
		
		session.removeAttribute("message");
		
		int generatedOTP = (int) session.getAttribute("generatedOTP");
		String email = (String) session.getAttribute("email");
		
		// convert all 6 input field OTP into 6 digit OTP so we can match generated OTP with user entered OTP
		 int enteredOTP = otp1 * 100000 + otp2 * 10000 + otp3 * 1000 + otp4 * 100 + otp5 * 10 + otp6;

		 
		if(generatedOTP==enteredOTP) {
			
			// check that user or email is in database or not cause we change password so we need user or that email id
			User user = userRepository.getUserByUserName(email);
			
			if(user==null) {
			//	session.setAttribute("error", "user does not exist with this email!!");
				
				redirAttrs.addFlashAttribute("error", "user does not exist with this email!!");
				
				return "redirect:/forgotPassword";
				
			}else {
				
				return "redirect:/newPassword";
			}
			
		}
		else {
			
		//	session.setAttribute("error", "Please, check your otp !!");
			redirAttrs.addFlashAttribute("error", "Please, check your otp !!");
			
			return  "redirect:/otp";
		}
	}
	
	
//=================================================================================== change password handler =======================================================================================
	
	@PostMapping("/change_password")
	public String  changePassword(@RequestParam("newPassword") String newPassword, @RequestParam("comfirmPassword") String comfirmPassword, HttpSession session,RedirectAttributes redirAttrs) {
		
		
		String email = (String) session.getAttribute("email");
		
		User user = userRepository.getUserByUserName(email);
		
		if(newPassword.equals(comfirmPassword)) {
	
		user.setPassword(bCryptPasswordEncoder.encode(newPassword));
		user.setCheckbox(true);
		
		userRepository.save(user);
		
		System.out.println("password successfully changed");
		
		redirAttrs.addFlashAttribute("message", "password successfully changed");
		
		return "redirect:/login";
	}
		else {
			
			//session.setAttribute("message", "password does not match");
			redirAttrs.addFlashAttribute("error", "password does not match");
			
			System.out.println("password does not match");
			
			System.out.print("missmatch password");
			return "redirect:/newPassword";
			
		}
	}
	

	
	
	
	
}
