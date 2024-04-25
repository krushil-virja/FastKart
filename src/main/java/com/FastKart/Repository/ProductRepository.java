package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.Category;
import com.FastKart.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	// to show product by its category (one category multiple Product)
	List<Product> findProductByCategory(Category category);
	
	 // Custom query to find products by multiple category IDs
		/*
		 * @Query("SELECT p FROM Product p JOIN p.category c WHERE c.id IN :categoryIds"
		 * ) List<Product> findProductsByCategoryIds(@Param("categoryIds") List<Integer>
		 * categoryIds,Pageable pageable);
		 */

	@Query("SELECT p FROM Product p JOIN p.category c WHERE c.id IN :categoryIds")
	Page<Product> findProductsByCategoryIds(@Param("categoryIds") List<Integer> categoryIds,Pageable pageable);
	  
	int countByCategory(Category category);
}
