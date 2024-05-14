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

	private boolean userHasBoughtProduct(Principal principal, int pid) {

		// first we find that order who contain that product but according to user
		// call user method that fetch currunt user login

		User user = uDao.getLoggedInUser(principal);

		List<Order> order = orderRepository.getOrdersByUser(user);
		// now apply for loop so we get all the details or product

		for (Order o : order) {

			// get the product ID of the current order's product
			/* int productId = o.getCart().getProduct().getId(); */

			int productId = o.getProduct().getId();

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

	public Review productReviews(Review review, Principal principal, int pid, int rating) {

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
				review.setRating(rating);
				review.setReviewdate(LocalDate.now());

				Review r = reviewsRepository.save(review);
				return r;
			} else {
				System.out.println("user only give a review one time to a perticular order");
			}

		}
		return null;

	}

	/*
	 * public int averageOfProductRating( int pid) {
	 * 
	 * 
	 * int countReviewsByProductId = reviewsRepository.countReviewsByProductId(pid);
	 * 
	 * int sumRatingByProductId = reviewsRepository.sumRatingByProductId(pid);
	 * 
	 * double averageRatingOfProduct = sumRatingByProductId/countReviewsByProductId;
	 * return sumRatingByProductId; }
	 */

	/*
	 * public double averageOfProductRating(int pid) { int countReviewsByProductId =
	 * reviewsRepository.countReviewsByProductId(pid); int sumRatingByProductId =
	 * reviewsRepository.sumRatingByProductId(pid);
	 * 
	 * // Ensure we don't divide by zero and perform division as double double
	 * averageRatingOfProduct = 0.0;
	 * 
	 * if (countReviewsByProductId == 0 || sumRatingByProductId ==0) { return
	 * averageRatingOfProduct; // No reviews available, return default value }
	 * 
	 * 
	 * if (countReviewsByProductId != 0 && sumRatingByProductId!=0 ) {
	 * averageRatingOfProduct = (double) sumRatingByProductId /
	 * countReviewsByProductId; }
	 * 
	 * 
	 * averageRatingOfProduct = (double) sumRatingByProductId /
	 * countReviewsByProductId;
	 * 
	 * return averageRatingOfProduct; }
	 */
	
	public double averageOfProductRating(int pid) { 
	    int countReviewsByProductId = reviewsRepository.countReviewsByProductId(pid);
	    int sumRatingByProductId = reviewsRepository.sumRatingByProductId(pid);
	    
	    // Ensure we don't divide by zero and perform division as double
	    double averageRatingOfProduct = 0.0;
	    
	    if (countReviewsByProductId == 0 || sumRatingByProductId == 0) {
	        return averageRatingOfProduct; // No reviews available, return default value
	    } 
	    
	    averageRatingOfProduct = (double) sumRatingByProductId / countReviewsByProductId;
	    
	    return averageRatingOfProduct;
	}


}
