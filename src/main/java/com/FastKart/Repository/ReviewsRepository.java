package com.FastKart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FastKart.entities.Product;
import com.FastKart.entities.Review;
import com.FastKart.entities.User;

@Repository
public interface ReviewsRepository  extends JpaRepository<Review, Integer>{
	
	
	Review findByUserAndProduct(User u, Product p);

}
