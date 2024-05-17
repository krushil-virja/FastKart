package com.FastKart.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FastKart.Dao.addressDao;
import com.FastKart.Repository.AddressRepository;
import com.FastKart.entities.Address;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.websocket.server.PathParam;

@Controller
public class addressController {
	
	@Autowired
	private addressDao addao;
	
	@Autowired
	private AddressRepository addressRepository;

//=========================================== ADD ADDRESS METHOD IS USER IS LOGIN ===============================================================
	@PostMapping("/addAddress")
	public String addAddress(@ModelAttribute Address address, Model m,Principal principal, RedirectAttributes reAttributes) {
		
		if(principal!=null) {
		Address addAddress = addao.addAddress(address, principal);
		
		reAttributes.addFlashAttribute("error", "Address successfully added");
		return  "redirect:/checkOut1";
	}
		else {
			return null;
		}

}
	
//================================================= ADD ADDRESS MODULE WHILE USER ADDED ADDRESS USING USER DESHBOARD =============================
	@PostMapping("/newAddress")
	public String addAddressInDashboard(@ModelAttribute Address address, Model m,Principal principal, RedirectAttributes reAttributes) {
		
		if(principal!=null) {
		Address addAddress = addao.addAddress(address, principal);
		
		reAttributes.addFlashAttribute("error", "Address successfully added");
		return  "redirect:/userDashboard";
	}
		else {
			return null;
		}

}

//========================================================== DELETE ADDRESS BY ID ========================================================
	@GetMapping("/deleteAddress/{id}")
	public String deleteAddress(@PathVariable("id") int id, Model m) {
		
		addao.deleteAddress(id);
		return "redirect:/checkOut1";
		
	
	}
	
//========================================================== EDITE ADDRESS BY ID ========================================================
	@GetMapping("/address/{id}")
  public String getAddressDetailById(@PathVariable("id") int id, Model m) {
	  
		Address address = addressRepository.findById(id).get();
		
		m.addAttribute("address", address);
		
		return "";
  }
	
	
	
	
	
	
}
	