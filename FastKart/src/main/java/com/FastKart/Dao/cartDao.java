package com.FastKart.Dao;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.CartRepository;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.Repository.UserRepository;
import com.FastKart.entities.Cart;
import com.FastKart.entities.Product;
import com.FastKart.entities.User;


@Service
public class cartDao {
	
@Autowired
private  CartRepository cartRepository;

@Autowired
private ProductRepository productRepository;

@Autowired
private UserRepository userRepository;
	
public Cart addToCart( Cart cart ,int pid, int quntity, Principal principal) {
	
// find pid for check that product is null or not
Product product = 	productRepository.findById(pid).get();

// find that user is login or not, if user is null in that case we redirect user to login page.
String name =	principal.getName();
User user = userRepository.getUserByUserName(name);


   	

   if (product != null && user != null) {
	   
	   cart.setQuntity(quntity);
	   cart.setTotal(quntity*product.getPrice());
	   cart.setProduct(product);
       cart.setUser(user);
       Cart saveCarts = cartRepository.save(cart);
         
       return saveCarts;
       
   } else {
	   
	   
       return null;
   }
   
}

//================================================================== VIEW CART METHOD IF USER LOGIN ====================================================
public List<Cart> viewCart(Principal principal){
	
	String name = principal.getName();
	
	User user = userRepository.getUserByUserName(name);
	
	
	 return cartRepository.findByUser(user);

}


//===================================================== DELETE CART METHOD =============================================================================
public void deleteCart(int id) {
	
	cartRepository.deleteById(id);
}


/*
 * public int countCartItem(User user) {
 * 
 * return cartRepository.countByUser(user); }
 */


}
