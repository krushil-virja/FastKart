package com.FastKart.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FastKart.Dao.categoryDao;
import com.FastKart.Dao.productDao;
import com.FastKart.Dao.reviewsDao;
import com.FastKart.Dao.subCategoryDao;
import com.FastKart.Repository.CategoryRepository;
import com.FastKart.Repository.OrderRepository;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.Repository.ReviewsRepository;
import com.FastKart.Repository.SubCategoryRepository;
import com.FastKart.entities.Category;
import com.FastKart.entities.Product;
import com.FastKart.entities.subCategory;

@Controller
public class productController {

	@Autowired
	private productDao pdao;

	@Autowired
	private categoryDao cdao;

	@Autowired
	private subCategoryDao scdao;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	
	@Autowired
	private ReviewsRepository reviewsRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private reviewsDao rdao;

	@PostMapping("insertProduct")
	private String addProduct(@ModelAttribute Product product, @RequestParam("image") MultipartFile file,
			@RequestParam("cid") int cid, @RequestParam("scid") int scid) {

		try {
			if (file.isEmpty()) {

				System.out.println("Your File is Empty");
			}

			else {
				product.setPimage(file.getOriginalFilename());

				product.setCategory(cdao.getCategory(cid));
				product.setSubcategory(scdao.getSubCategory(scid));

				File saveFile = new ClassPathResource("static/assets1/images").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				System.out.println("File is uplosded");
			}

			pdao.addProduct(product);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return "redirect:addProduct";

	}

//============================================================= DELETE PRODUCT HANDLER =================================================================

	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable("id") int id, Model m) {

		pdao.deleteProduct(id);
		return "redirect:/product";
	}

//========================================================= FIND CATEGORY WISE PRODUCT HANDLER ========================================================	
	@GetMapping("/findProductByCategory/{id}")
	public String findProductByCategory(@PathVariable("id") int id, Model model) {
		
		
		List<Product> productsByCategory = pdao.findProductByCategory(id);
		model.addAttribute("productByCategories", productsByCategory);
		
		List<Category> category = cdao.showAllCategory();
		model.addAttribute("category", category);
		
		int countProductsInRange0To1000 = productRepository.countProductsInRange0To1000();
		model.addAttribute("countProductsInRange0To1000", countProductsInRange0To1000);
		
		int countProductsInRange1000To10000 = productRepository.countProductsInRange1000To10000();
		model.addAttribute("countProductsInRange1000To10000", countProductsInRange1000To10000);
		
		int countProductsInRange10000To100000 = productRepository.countProductsInRange10000To100000();
		model.addAttribute("countProductsInRange10000To100000", countProductsInRange10000To100000);
	
		return "shop"; // Assuming your view name is "home.html"
	}

//=====================================================SHow PRODUCT DETAILS HANDLER====================================================================

	@GetMapping("/productDetails/{id}")
	public String productDetails(@PathVariable("id") int id, Model m) {
		Product findProductById = pdao.findProductById(id);

		m.addAttribute("productDetails", findProductById);
		
		
		double averageOfProductRating = rdao.averageOfProductRating(id);
		m.addAttribute("averageOfProductRating", averageOfProductRating);
		
		double fiveStarPercentage  = (averageOfProductRating*5)/100;
		m.addAttribute("fiveStarPercentage", fiveStarPercentage);
		
		double fourStarPercentage = (averageOfProductRating*4)/100;
		m.addAttribute("fourStarPercentage", fourStarPercentage);
		
		double threeStarPercentage = (averageOfProductRating*3)/100;
		m.addAttribute("threeStarPercentage", threeStarPercentage);
		
		double twoStarPercentage = (averageOfProductRating*2)/100;
		m.addAttribute("twoStarPercentage", twoStarPercentage);
		
		double oneStarPercentage = (averageOfProductRating*1)/100;
		m.addAttribute("oneStarPercentage", oneStarPercentage);
		
		Integer countReview = reviewsRepository.countReviewsByProductId(id);
		m.addAttribute("countReview", countReview);
		
		System.out.println(countReview);
		
		List<Object[]> topSellingProducts = orderRepository.findTopSellingProducts();
		m.addAttribute("topSellingProducts", topSellingProducts);
		
		
		List<Product> relatedProducts = pdao.findRelatedProducts(findProductById);
		m.addAttribute("relatedProducts", relatedProducts);

		
		return "productDetails";
	}

//========================================get Product Details for Update ========================================================================
	@GetMapping("/updateProduct/{id}")
	public String getProductDetails(@PathVariable("id") int id, Model m) {
		
		Product product = productRepository.findById(id).get();
		m.addAttribute("product", product);
		
		List<Category> showAllCategory = cdao.showAllCategory();
		m.addAttribute("category", showAllCategory);
		
		List<subCategory> showAllSubCategory = scdao.showAllSubCategory();
		m.addAttribute("subCategory", showAllSubCategory);
		
		return "admin/admin-updateProduct";
		
	}

//================================================= Update Product Handler ===============================================================
	@PostMapping("updateProduct")
	public String updateProduct(@RequestParam("id") int id,@RequestParam("pname") String pname, @RequestParam("cid") int cid, @RequestParam("scid") int scid,
			@RequestParam("brand") String brand, @RequestParam("price") int price, @RequestParam("description") String description, @RequestParam("image") MultipartFile file
			, RedirectAttributes redirectAttributes)
	
	{
		Product product = productRepository.findById(id).get();
		Category category = categoryRepository.findById(cid).get();
	   subCategory subCategory = subCategoryRepository.findById(scid).get();
	   
		try {
			
			if(file.isEmpty()) {
				
				System.out.println("file is empty");
				
				redirectAttributes.addFlashAttribute("error", "Please select a file");
			
				
			}
			
			else {
				
				product.setPname(pname);
				product.setCategory(category);
				product.setSubcategory(subCategory);
				product.setBrand(brand);
				product.setPrice(price);
				product.setDescription(description);
				product.setPimage(file.getOriginalFilename());
				
				File saveFile = new ClassPathResource("static/assets1/images").getFile();
				
				Path path = Paths.get(saveFile.getAbsolutePath() +File.separator + file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING );
				
				System.out.println("file is uploaded");
				
				System.out.println(path);
				
				productRepository.save(product);
				
				redirectAttributes.addFlashAttribute("success", "product details updated");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/product";
		
	}

}