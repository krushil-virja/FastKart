package com.FastKart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FastKart.Dao.addressDao;
import com.FastKart.Dao.checkoutDao;
import com.FastKart.entities.Address;
import com.FastKart.entities.CheckOut;

import jakarta.servlet.http.HttpSession;

@Controller
public class CheckOutController {

	@Autowired
	private checkoutDao chdao;

	@Autowired
	private addressDao adao;

	/*
	 * USING THIS POST MAPPING ALSO WE FETCH CHECKOUT ID -> including the checkoutId as a query parameter in the redirect.
	 * 
	 * @PostMapping("/checkOut") public String checkOut(@RequestParam("address_id")
	 * int id, Model m ) {
	 * 
	 * Address addressById = adao.getAddressById(id);
	 * 
	 * CheckOut ch = chdao.saveSelectedAddress(addressById);
	 * 
	 * int checkoutId = ch.getId(); m.addAttribute("checkoutId", checkoutId);
	 * 
	 * System.out.println("Checkout ID: " + checkoutId);
	 * 
	 * return "redirect:/comfirmOrder?checkoutId=" + checkoutId; return
	 * "redirect:/comfirmOrder"; }
	 */

	
	// USING THIS POST MAPPING HANDlER WE  FETCH CHECKOUT ID USING HTTPSESSION JUST LIKE WE DO IN JSP SERVLET
	
	@PostMapping("/checkOut")
	public String checkOut(@RequestParam("address_id") int id, Model m, HttpSession session) {
		
		Address addressById = adao.getAddressById(id);
		
		CheckOut ch = chdao.saveSelectedAddress(addressById);
	
	    int checkoutId = ch.getId();
	   session.setAttribute("checkoutId", checkoutId);
	    System.out.println("Checkout ID: " + checkoutId);
	    
	     return "redirect:/comfirmOrder"; 
		
	}
}
