package com.FastKart.Dao;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.OrderRepository;
import com.FastKart.entities.CheckOut;
import com.FastKart.entities.Order;
import com.FastKart.entities.User;

@Service
public class orderDao {

	@Autowired
	private OrderRepository orderRepository;

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
	

	public Order doOrder(Order o, Principal principal) {

		User user = uDao.getLoggedInUser(principal);

	

		if (user != null) {

			o.setUser(user);
		
			o.setOrderDate(LocalDate.now());
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
