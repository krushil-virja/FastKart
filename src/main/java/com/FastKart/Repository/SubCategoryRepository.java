package com.FastKart.Repository;

import org.springframework.data.repository.CrudRepository;

import com.FastKart.entities.Category;
import com.FastKart.entities.subCategory;

public interface SubCategoryRepository extends CrudRepository<subCategory, Integer>{
	
	
	/* subCategory findByCategory(int cid); */

	
}
