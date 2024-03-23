package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.Cart;
import com.FastKart.entities.Product;
import com.FastKart.entities.User;

public interface CartRepository extends CrudRepository<Cart, Integer> {


	//to get all the cart by its user 
	List<Cart> findByUser(User user);
	
	// this method automatically count all the cart item according to login user
	
	// count item in the cart
	int countByUser(User user);
	
	// method to get cart by its user and product
	Cart findByUserAndProduct(User u , Product p);
	
	
	void deleteByUserAndProduct(User u , Product p);
	
}


