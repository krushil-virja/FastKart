package com.FastKart.Dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.CategoryRepository;
import com.FastKart.Repository.OrderRepository;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.entities.Category;
import com.FastKart.entities.Product;

@Service
public class productDao {
     
	@Autowired
	private ProductRepository productRepository;

	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Autowired
	private OrderRepository orderRepository;
//=================================================================== ADD PRODUCT METHOD ===============================================================
	public Product addProduct(Product p) {
		
		Product product = productRepository.save(p);
		
		return product;
	}
	
	
//================================================================ SHOW PRODUCT METHOD ================================================================
	
	public List<Product> showAllProduct(){
		
		List<Product> findAllProduct = (List<Product>) productRepository.findAll();
		
		return findAllProduct;
		
	}

//================================================================= DELETE PRODUCT BY ITS ID METHOD ====================================================
	
	public void deleteProduct(int id) {
	
		productRepository.deleteById(id);
	}

//=================================================================FIND CATEGORY WISE PRODUCT METHOD ===========================================================
	
		public List<Product> findProductByCategory(int id){
			
			// first we find category by its id
			Category findCategoryById =  categoryRepository.findById(id).get();
			
			List<Product> findProductByCategory = productRepository.findProductByCategory(findCategoryById); // this method created in productRepository
			return findProductByCategory;
			
		}
		
		
		

//=================================================================FIND by MULTIPLE CATEGORY WISE PRODUCT METHOD  using filter===========================================================
		/*
		 * public Page<Product> findProductByCategories(List<Integer> categoryId,
		 * Pageable pageable) { List<Product> findProductByCategory =
		 * productRepository.findProductsByCategoryIds(categoryId,pageable); return
		 * findProductByCategory; }
		 */
		
		
		public Page<Product> findProductByCategories(List<Integer> categoryId, Pageable pageable) {
		    // Call the repository method that returns a Page<Product>
		    Page<Product> productPage = productRepository.findProductsByCategoryIds(categoryId, pageable);
		    return productPage;
		}
	
		public Page<Product> findProductsByPriceRange(double minPrice, double maxPrice, int pageNumber, int pageSize) {
		    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		    return productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
		}
//=========================================================== Find PRODUCT BY ID ===================================================================
		
		public Product findProductById(int id){
			
			Product productById =productRepository.findById(id).get();
			return productById;
		}
		
		
		public List<Product> filterProductByCategory( Category category){
			
			if(category==null) {
				return   (List<Product>) productRepository.findAll();
			}
			else {
				return productRepository.findProductByCategory(category);
			}
		}
		
	
		
	    public List<Product> getTopSellingProducts(int n) {
	        // Get the top selling products from the repository
	        List<Object[]> topSellingProducts = orderRepository.findTopSellingProducts();

	        // Extract products from the result
	        List<Product> products = topSellingProducts.stream()
	                .map(result -> (Product) result[0])
	                .collect(Collectors.toList());

	        // Return the top N products (in this case, top 10)
	        return products.subList(0, Math.min(n, products.size()));
	    }

//================================================ Find Related Product ==============================================
	    
	    public List<Product> findRelatedProducts(Product p){
	    
	    return productRepository.findProductByCategory(p.getCategory());
		
	    }
		
		
} 
