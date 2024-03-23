package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.Category;
import com.FastKart.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
	
	// to show product by its category (one category multiple Product)
	List<Product> findProductByCategory(Category category);
	
	 // Custom query to find products by multiple category IDs
	@Query("SELECT p FROM Product p JOIN p.category c WHERE c.id IN :categoryIds")
	List<Product> findProductsByCategoryIds(@Param("categoryIds") List<Integer> categoryIds);

	  
	int countByCategory(Category category);
}
