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
import com.FastKart.Dao.userDao;
import com.FastKart.Repository.OrderRepository;
import com.FastKart.email.emailServices;
import com.FastKart.entities.CheckOut;
import com.FastKart.entities.Order;
import com.FastKart.entities.User;

import jakarta.transaction.Transactional;

@Controller
public class orderController {

	@Autowired
	private orderDao oDao;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private userDao uDao;

	@Autowired
	private checkoutDao chDao;

	@Autowired
	private emailServices mailservice;

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
	public String doOrder(@ModelAttribute("order") Order o, Principal principal, Model m, @RequestParam("pid") int pid,
			@RequestParam("quantity") int quantity, @RequestParam("subtotal") double subtotal,
			@RequestParam("shipping") double shipping, @RequestParam("discount") double disount,
			@RequestParam("total") double total) {

		System.out.println("Received Checkout ID in doOrder: " + o.getCheckOut());

		oDao.doOrder(o, principal, pid, quantity, subtotal, shipping, disount, total);

		System.out.println("order successfully and delete assosiate cart with the order");

		/*
		 * return "redirect:/comfirmOrder?checkoutId=" + o.getCheckOut(); THIS RETURN
		 * FOR THE HANDLER WICH WE PASS CHECKOUT AS A QUERY PARAMETER
		 */

		return "redirect:/userDashboard"; // this return for the handler which we use httpsession to set checkout id
	}

	@GetMapping("/activeOrder/{id}")
	public String activeOrder(@PathVariable("id") int id, Order o) {

		Order order = orderRepository.findById(id).get();

		String email = order.getUser().getEmail();

		String form = "virjakrushil@gmail.com";
		String subject = "Your Order Hase been Activated";
		String message = "Your Order is acceepted ";

		String To = email;

		mailservice.sendMail(To, subject, message);

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

		Order order = orderRepository.findById(id).get();

		String email = order.getUser().getEmail();

		String form = "virjakrushil@gmail.com";
		String subject = "Order delivered";
		String message = "Your Order  has been delivered ";

		String To = email;

		mailservice.sendMail(To, subject, message);

		oDao.deliveredOrder(id);

		return "redirect:/deliveredOrder";
	}

}
