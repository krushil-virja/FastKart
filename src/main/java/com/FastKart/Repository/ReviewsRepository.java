package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.FastKart.entities.Product;
import com.FastKart.entities.Review;
import com.FastKart.entities.User;

@Repository
public interface ReviewsRepository  extends JpaRepository<Review, Integer>{
	
	
	Review findByUserAndProduct(User u, Product p);
	
	
	/*
	 * @Query("SELECT r.product, COUNT(r) FROM Review r GROUP BY r.product")
	 * List<Object[]> countReviewsByProduct();
	 */
    
	/*
	 * @Query("SELECT COUNT(r) FROM Review r WHERE r.product.id = :pid") int
	 * countReviewsByProductId(@Param("pid")int pid);
	 * 
	 * @Query("SELECT SUM(r.rating) FROM Review r WHERE r.product.id = :pid") int
	 * sumRatingByProductId(@Param("pid") int pid);
	 */
    
    
	@Query("SELECT COUNT(r) FROM Review r WHERE r.product.id = :pid")
	Integer countReviewsByProductId(@Param("pid") int pid);

	@Query("SELECT SUM(r.rating) FROM Review r WHERE r.product.id = :pid")
	Integer sumRatingByProductId(@Param("pid") int pid);


}
