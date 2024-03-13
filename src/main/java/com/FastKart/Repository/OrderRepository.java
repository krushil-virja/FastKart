package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FastKart.entities.Order;
import com.FastKart.entities.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	
	int countByUser(User user);
	
	 // Method to count delivered orders for a specific user
    int countByStatusAndUser(int status, User user);
    
    List<Order> getOrdersByUser(User user);
}
