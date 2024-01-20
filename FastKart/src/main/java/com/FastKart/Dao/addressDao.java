package com.FastKart.Dao;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Controller.basicController;
import com.FastKart.Repository.AddressRepository;
import com.FastKart.entities.Address;
import com.FastKart.entities.User;

@Service
public class addressDao {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private userDao udao;
	
//========================================================= ADD ADDRESS METHOD IF USER IS LOGIN ======================================================
	public Address addAddress(Address address, Principal principal) {
		
		User loggedInUser = udao.getLoggedInUser(principal);
		
		if(loggedInUser!=null) {
			
			address.setUser(loggedInUser);
		return addressRepository.save(address);
	}
		else {
			return null;
		}
}
	
//==================================================SHOW ALL ADDRESS ACCORDING TO LOGIN USER ===========================================================
	
	public List<Address> showAllAddress(Principal principal){
		
		User loggedInUser = udao.getLoggedInUser(principal);
		
		return addressRepository.findByUser(loggedInUser);
		
	}
}
