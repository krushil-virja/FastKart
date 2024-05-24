
package com.FastKart.Dao;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.FastKart.Repository.CartRepository;
import com.FastKart.Repository.OrderRepository;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.entities.Cart;
import com.FastKart.entities.CheckOut;
import com.FastKart.entities.Order;
import com.FastKart.entities.Product;
import com.FastKart.entities.User;

import jakarta.transaction.Transactional;

@Service
public class orderDao {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private userDao uDao;

	@Autowired
	private checkoutDao chDao;
	/*
	 * public Order doOrder(Order o, Principal principal, int checkoutId) {
	 * 
	 * User user = uDao.getLoggedInUser(principal);
	 * 
	 * CheckOut checkOutById = chDao.getCheckOutById(checkoutId);
	 * 
	 * if (user != null) {
	 * 
	 * o.setUser(user); o.setCheckOut(checkOutById);
	 * o.setOrderDate(LocalDate.now()); return orderRepository.save(o); } else {
	 * return null; }
	 * 
	 * }
	 */
	
	
	/*
	 * public Order doOrder(Order o, Principal principal) {
	 * 
	 * User user = uDao.getLoggedInUser(principal);
	 * 
	 * 
	 * 
	 * if (user != null) {
	 * 
	 * 
	 * o.setUser(user);
	 * 
	 * o.setOrderDate(LocalDate.now());
	 * 
	 * Cart cart = o.getCart();
	 * 
	 * 
	 * if(cart!=null) {
	 * 
	 * // cartRepository.delete(cart); // Delete the cart item associated with the
	 * user and product cartRepository.deleteByUserAndProduct(user,
	 * cart.getProduct());
	 * 
	 * }
	 * 
	 * return orderRepository.save(o); } else { return null; }
	 * 
	 * }
	 */	
	
	
	public Order doOrder(Order o, Principal principal,int pid , int quantity, double subtotal, double shipping, double discount, double total ) {

		User user = uDao.getLoggedInUser(principal);
		Product product = productRepository.findById(pid).get();

		if (user != null) {
      
			 
			o.setQuantity(quantity);
			o.setUser(user);
		   o.setProduct(product);
			o.setOrderDate(LocalDate.now());
			o.setSubtotal(subtotal);
			o.setShipping(shipping);
			o.setDiscount(discount);
			o.setTotal(total);
			
			      //	cartRepository.delete(cart);
				  // Delete the cart item associated with the user and product
	            cartRepository.deleteByUserAndProduct(user, product);
				
			
			
			return orderRepository.save(o);
		} else {
			return null;
		}

	}

	
	public List<Order> allOrder(){
		
		List<Order> allOrder = orderRepository.findAll();
		return allOrder;
	}
	
	
	public  void activeOrder(int id) {
		
		Order order = orderRepository.findById(id).get();
		
		order.setStatus(2);
		
		orderRepository.save(order);
	}
	
	
	public void shippingOrder(int id) {
		
		Order order = orderRepository.findById(id).get();
		
		order.setStatus(3);
		
		orderRepository.save(order);
	}
	
	public void deliveredOrder(int id) {
		
		Order order = orderRepository.findById(id).get();
		
		order.setStatus(4);
		
		orderRepository.save(order);
	}
	
	
	
	  
	}
	
	
