package com.FastKart.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FastKart.Dao.checkoutDao;
import com.FastKart.Dao.orderDao;
import com.FastKart.entities.CheckOut;
import com.FastKart.entities.Order;

import jakarta.transaction.Transactional;

@Controller
public class orderController {

	@Autowired
	private orderDao oDao;

	@Autowired
	private checkoutDao chDao;

	/*
	 * @PostMapping("/order") private String doOrder(@ModelAttribute ("order") Order
	 * o,Principal principal,Model m, @RequestParam("checkOut") int checkoutId) {
	 * 
	 * System.out.println("Received Checkout ID in doOrder: " + checkoutId);
	 * 
	 * 
	 * oDao.doOrder(o, principal, checkoutId);
	 * 
	 * return "redirect:/comfirmOrder?checkoutId=" + checkoutId; THIS RETURN FOR THE
	 * HANDLER WICH WE PASS CHECKOUT AS A QUERY PARAMETER
	 * 
	 * return "redirect:/comfirmOrder"; // this return for the handler which we use
	 * httpsession to set checkout id }
	 */
	
	
	@PostMapping("/order")
	@Transactional
	public String doOrder(@ModelAttribute ("order") Order o,Principal principal,Model m,@RequestParam("pid") int pid) {
		
		System.out.println("Received Checkout ID in doOrder: " + o.getCheckOut());

		 
		oDao.doOrder(o, principal, pid);
		
		System.out.println("order successfully and delete assosiate cart with the order");
		
		/* return "redirect:/comfirmOrder?checkoutId=" +  o.getCheckOut();  THIS RETURN FOR THE HANDLER WICH WE PASS CHECKOUT AS A QUERY PARAMETER*/
		
		return "redirect:/comfirmOrder"; // this return for the handler which we use httpsession to set checkout id
	}
	
	
	
	@GetMapping("/activeOrder/{id}")
	public String activeOrder(@PathVariable("id") int id) {
	    oDao.activeOrder(id);
	    return "redirect:/activeOrder";
	}
	
	
	@GetMapping("/shippingOrder/{id}")
	public String shippingOrder(@PathVariable("id") int id) {
		
		oDao.shippingOrder(id);
		
		return "redirect:/shippingOrder";
	}
	
	@GetMapping("/deliveredOrder/{id}")
	public String deliveredOrder(@PathVariable("id") int id) {
		
		oDao.deliveredOrder(id);
		
		return "redirect:/deliveredOrder";
	}
}
