package com.FastKart.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.FastKart.Dao.addressDao;
import com.FastKart.entities.Address;

@Controller
public class addressController {
	
	@Autowired
	private addressDao addao;
	
	@PostMapping("/addAddress")
	public String addAddress(@ModelAttribute Address address, Model m,Principal principal) {
		
		if(principal!=null) {
		Address addAddress = addao.addAddress(address, principal);
		return  "redirect:/checkOut1";
	}
		else {
			return null;
		}

}
}
	