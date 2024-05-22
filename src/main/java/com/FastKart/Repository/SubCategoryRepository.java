package com.FastKart.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.FastKart.entities.Category;
import com.FastKart.entities.subCategory;

public interface SubCategoryRepository extends CrudRepository<subCategory, Integer>{
	
	
	/* subCategory findByCategory(int cid); */
  
	 @Query("SELECT CASE WHEN COUNT(sc) > 0 THEN true ELSE false END FROM subCategory sc WHERE sc.sub_cat_name = :subCatName")
	    boolean existsBySubCatName(String subCatName);


}
