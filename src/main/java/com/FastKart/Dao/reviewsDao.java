package com.FastKart.Dao;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.OrderRepository;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.Repository.ReviewsRepository;
import com.FastKart.Repository.UserRepository;
import com.FastKart.entities.Order;
import com.FastKart.entities.Product;
import com.FastKart.entities.Review;
import com.FastKart.entities.User;

@Service
public class reviewsDao {

	@Autowired
	private userDao uDao;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	// only rate product who order that product so first we fetch user bought that
	// product or not. if yes than it able to rate else show message
	/*
	 * private boolean userHasBoughtProduct(Principal principal, Product p) {
	 * 
	 * // first we find that order who contain that product but according to user //
	 * call user method that fetch currunt user login
	 * 
	 * User user = uDao.getLoggedInUser(principal);
	 * 
	 * List<Order> order = orderRepository.getOrdersByUserId(user); // now apply for
	 * loop so we get all the details or product
	 * 
	 * for(Order o : order) {
	 * 
	 * // get the product ID of the current order's product Product product =
	 * o.getCart().getProduct();
	 * 
	 * if(product.equals(p)) { // if the product ID matches, return true indicating
	 * the user has bought this product return true; } } // if the loop completes
	 * without finding a matching product ID, return false return false; }
	 * 
	 * 
	 * 
	 * 
	 * @Autowired private ReviewsRepository reviewsRepository;
	 * 
	 * public Review productReviews(Review review, Principal principal, Product
	 * product) {
	 * 
	 * User user = uDao.getLoggedInUser(principal); // // Product product =
	 * productRepository.findById(pid).get(); //
	 * 
	 * if(userHasBoughtProduct(principal, product)) {
	 * 
	 * Review existingRating = reviewsRepository.findByUserAndProduct(user,
	 * product);
	 * 
	 * if(existingRating!=null) { //review.setProduct(product);
	 * review.setUser(user); review.setReviewdate(LocalDate.now());
	 * 
	 * Review r = reviewsRepository.save(review); return r; } else {
	 * System.out.println("user has not bought the product"); }
	 * 
	 * 
	 * 
	 * } return null;
	 * 
	 * }
	 */

	private boolean userHasBoughtProduct(Principal principal, int pid) {

		// first we find that order who contain that product but according to user
		// call user method that fetch currunt user login

		User user = uDao.getLoggedInUser(principal);

		List<Order> order = orderRepository.getOrdersByUser(user);
		// now apply for loop so we get all the details or product

		for (Order o : order) {

			// get the product ID of the current order's product
			int productId = o.getCart().getProduct().getId();

			if (productId == pid) {
				// if the product ID matches, return true indicating the user has bought this
				// product
				return true;
			}
		}
		// if the loop completes without finding a matching product ID, return false
		return false;
	}

	@Autowired
	private ReviewsRepository reviewsRepository;

	public Review productReviews(Review review, Principal principal, int pid) {

		// User user = uDao.getLoggedInUser(principal);

		String name = principal.getName();
		User user = userRepository.getUserByUserName(name);
		System.out.println("Logged in User: " + user); // Log logged in user

		if (userHasBoughtProduct(principal, pid)) {

			Product product = productRepository.findById(pid).get();
			System.out.println("Product: " + product); // Log product

			Review existingRating = reviewsRepository.findByUserAndProduct(user, product);
			System.out.println("Existing Rating: " + existingRating); // Log existing rating

			if (existingRating == null) {
			 review.setProduct(product);
				review.setUser(user);
				review.setReviewdate(LocalDate.now());

				Review r = reviewsRepository.save(review);
				return r;
			} else {
				System.out.println("user has not bought the product");
			}

		}
		return null;

	}

}
