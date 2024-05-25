package com.FastKart.Dao;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.FastKart.Repository.UserRepository;
import com.FastKart.entities.User;

@Service
public class userDao {

	@Autowired
	private UserRepository userRepository;
	
	
	@ModelAttribute("loggedInUser")
	public User getLoggedInUser(Principal principal) {
		if (principal != null) {
			String username = principal.getName();
			return userRepository.getUserByUserName(username);
		}
		return null;
	}
	
	
	

//========================================= method for check if user is login or not====================================================================
	 public boolean isUserLoggedIn(Principal principal) {
	       
		 if (principal != null && principal.getName() != null && !principal.getName().isEmpty()) {
		        String name = principal.getName();
		        User user = userRepository.getUserByUserName(name);
		        // Perform any additional checks or operations if needed 
		        return true;
		    }
		    return false;
	    }
	 
//===============================================	Add User Method ===================================================================================
	public User userRegister(User u) {
		
		User user = userRepository.save(u);
		return user;

	}
	
	
//=================================================== Show All User Method =========================================================================
	
	public List<User> ShowAllUser(){
		
		List<User> findAll = (List<User>) userRepository.findAll();
		
		return findAll;
	}
	
	
//=================================================== get UserBy Id  Method =========================================================================

	public User getUserById(int id) {
		
		User u = userRepository.findById(id).get();
		return u;
	}
//=================================================== Update User Profile Method =========================================================================
			
	
	public User updateUser(User u) {
		
		return userRepository.save(u);
	}
	
	public String customImage(Principal principal) {
		
		User user = getLoggedInUser(principal);
		
		String userName = user.getName();
		
		String[] splitName = userName.split(" ");
		
		String word = "";
		
		for(String s: splitName) {
			
			word += s.charAt(0);
			
		}
		
		String imageUsingUserFirstNameChar = "";
		
		if(word.length()>=2) {
		 imageUsingUserFirstNameChar = word.substring(0, 2);
		}
		else {
			 imageUsingUserFirstNameChar = word.substring(0);
		}
		
		return imageUsingUserFirstNameChar;
		
	}
	
	
}
