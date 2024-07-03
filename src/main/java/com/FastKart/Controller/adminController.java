package com.FastKart.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FastKart.Dao.categoryDao;
import com.FastKart.Dao.couponDao;
import com.FastKart.Dao.orderDao;
import com.FastKart.Dao.productDao;
import com.FastKart.Dao.reviewsDao;
import com.FastKart.Dao.subCategoryDao;
import com.FastKart.Dao.userDao;
import com.FastKart.Repository.CategoryRepository;
import com.FastKart.Repository.OrderRepository;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.entities.Category;
import com.FastKart.entities.Coupon;
import com.FastKart.entities.Order;
import com.FastKart.entities.Product;
import com.FastKart.entities.Review;
import com.FastKart.entities.User;
import com.FastKart.entities.subCategory;

@Controller

public class adminController {

	@Autowired
	private categoryDao cdao;

	@Autowired
	private subCategoryDao scdao;

	@Autowired
	private productDao pdao;

	@Autowired
	private userDao udao;

	@Autowired
	private couponDao coupondao;

	@Autowired
	private orderDao oDao;
	
	@Autowired
	private reviewsDao rdao;

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	


//========================================================== Handler to get Admin index page ==============================================================
	@GetMapping("/admin/index")
	public String adminDashboard(Model m, Principal principal ) {
		
		if(principal!=null) {
		
		int countAllProducts = productRepository.countAllProducts();
		int countAllOrders = orderRepository.countAllBy();
		int countDistinctUsers = orderRepository.countDistinctUsers();
		Double calculateTotalRevenue = orderRepository.calculateTotalRevenue();
		
		
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);

		
		
List<Category> showAllCategory = cdao.showAllCategory();
m.addAttribute("showAllCategory", showAllCategory);
		
List<Order> latestOrders = orderRepository.findLatestOrders();
m.addAttribute("latestOrders", latestOrders);


List<Object[]> topSellingProducts = orderRepository.findTopSellingProducts();
m.addAttribute("topSellingProducts", topSellingProducts);

       m.addAttribute("calculateTotalRevenue", calculateTotalRevenue);
		m.addAttribute("countAllProducts", countAllProducts);
		m.addAttribute("countAllOrders", countAllOrders);
       m.addAttribute("countDistinctUsers", countDistinctUsers);
       
       
       String customImage = udao.customImage(principal);
       m.addAttribute("customImage", customImage);
		return "admin/admin-index";
		}
		else {
			return "redirect:/login";
		}
	}

//========================================================= Handler to get Admin addCategory page =========================================================	
	@GetMapping("/admin/addCategory")
	public String addCategory( Model m,Principal principal ) {
		m.addAttribute("category", new Category());

		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);
		return "admin/admin-addCategory";
	}

//========================================================= Handler to get Admin Category page ===========================================================
	@GetMapping("/admin/category")
	public String category(Model m, Principal principal) {

		List<Category> showAllCategory = cdao.showAllCategory();
		m.addAttribute("category", showAllCategory);
		
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);

		return "admin/admin-category";
	}

//========================================================= Handler to get Admin addSubCategory page =====================================================
	@GetMapping("/admin/addSubCategory")
	public String addSubCategory(Model m, Principal principal) {

		List<Category> showAllCategory = cdao.showAllCategory();
		m.addAttribute("category", showAllCategory);
		m.addAttribute("subCategory", new subCategory());
		
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);
		
		return "admin/admin-addSubCategory";
	}
	
	
	@GetMapping("/admin/updateCategory")
	public String updateCategory(Model m , Principal principal) {
		
		User loggedInUser = udao.getLoggedInUser(principal);
		System.out.println(loggedInUser.getName());
		m.addAttribute("user", loggedInUser);
		
		return "admin/admin-updateCategory";
	}

//========================================================= Handler to get Admin subCategory page=========================================================
	@GetMapping("/admin/subCategory")
	public String subCategory(Model m, Principal principal) {

		List<subCategory> showAllSubCategory = scdao.showAllSubCategory();
		m.addAttribute("subCategory", showAllSubCategory);
		
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);

		return "admin/admin-subCategory";
	}

//========================================================= Handler to get Admin Update subCategory page=========================================================

	@GetMapping("/updateSubCategory")
	public String updateSubCategory(Model m, Principal principal) {
		
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);
		
		return "admin/admin-updateSubCategory";
	}
//========================================================= Handler to get Admin addProduct page =========================================================

	@GetMapping("/admin/addProduct")
	public String addProduct(Model m, Principal principal) {

		List<Category> showAllCategory = cdao.showAllCategory();
		m.addAttribute("category", showAllCategory);

		List<subCategory> showAllSubCategory = scdao.showAllSubCategory();
		m.addAttribute("subCategory", showAllSubCategory);
		
		m.addAttribute("product", new Product());
		
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);
		
		return "admin/admin-addProduct";
	}
	
	@GetMapping("/updateProduct")
	public String updateProduct(Model m, Principal principal) {
		
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);
		
		return "admin/admin-updateProduct";
	}

//========================================================= Handler to get Admin Product page ============================================================
	@GetMapping("/admin/product")
	public String product(Model m, @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "12") Integer pageSize, Principal principal) {

		//List<Product> showAllProduct = pdao.showAllProduct();
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);
		
		 PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize);
		Page<Product> page = productRepository.findAll(pageable);
		
		List<Product> showAllProduct = page.getContent();
		
		m.addAttribute("product", showAllProduct);
		m.addAttribute("totalPages", page.getTotalPages());
		
		m.addAttribute("totalItems", page.getTotalElements());
		  m.addAttribute("currentPage", pageNumber);
		    m.addAttribute("pageSize", pageSize);

		return "admin/admin-product";
	}

//========================================================= Handler to get Admin allUSer page ============================================================
	@GetMapping("/admin/allUser")
	public String allUser(Model m, Principal principal) {

		List<User> showAllUser = udao.ShowAllUser();
		m.addAttribute("user", showAllUser);
		
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);
		return "admin/admin-allUser";
	}

//========================================================== Handler to get Admin Create Coupon page =====================================================
	@GetMapping("/admin/createCoupon")
	public String createCoupon(Model m , Principal principal) {

		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);
		
		return "admin/admin-createCoupon";
	}

//======================================================= Handler to get Admin allCoupon page ============================================================		
	@GetMapping("/admin/allCoupon")
	public String allCoupon(Model m,Principal principal) {

		List<Coupon> allCoupon = coupondao.allCoupon();
		m.addAttribute("allCoupon", allCoupon);
		
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);
		
		return "admin/admin-allCoupon";
	}

//===================================================== Handler to get Admin allOrder page ==============================================================
	@GetMapping("/admin/allOrder")
	public String allOrder(Model m, Principal principal) {

		List<Order> allOrder = oDao.allOrder();

		m.addAttribute("allOrder", allOrder);
		
		
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);

		return "admin/admin-allOrder";
	}

//===================================================== Handler to get Admin activeOrder page ==============================================================
	@GetMapping("/admin/activeOrder")
	public String activeOrder(Model m, Principal principal) {
		
		List<Order> allOrder = oDao.allOrder();

		m.addAttribute("allOrder", allOrder);

		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);

		return "admin/admin-activeOrder";
	}

//===================================================== Handler to get Admin pendingOrder page ==============================================================
	@GetMapping("/admin/pendingOrder")
	public String pendingOrder(Model m, Principal principal) {

		List<Order> allOrder = oDao.allOrder();

		m.addAttribute("allOrder", allOrder);
		
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);

		return "admin/admin-pendingOrder";
	}

//===================================================== Handler to get Admin shippingOrder page ==============================================================
	@GetMapping("/admin/shippingOrder")
	public String shippingOrder(Model m, Principal principal) {

		List<Order> allOrder = oDao.allOrder();

		m.addAttribute("allOrder", allOrder);

		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);
		
		return "admin/admin-shippingOrder";
	}

//===================================================== Handler to get Admin deliveredOrder page ==============================================================
	@GetMapping("/admin/deliveredOrder")
	public String deliveredOrder(Model m, Principal principal) {

		List<Order> allOrder = oDao.allOrder();

		m.addAttribute("allOrder", allOrder);
		
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);

		return "admin/admin-deliveredOrder";
	}

//====================================================== Handler to get Admin orderDetails page ==========================================================
	@GetMapping("/orderDetails")
	public String orderDetails(Model m, Principal principal) {

		
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);
		
		return "admin/admin-orderDetails";
	}

//====================================================== Handler toget Admin orderTracking page =========================================================
	@GetMapping("/orderTracking")
	public String orderTracking(Model m , Principal principal) {

		
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);
		
		return "admin/admin-orderTracking";
	}

//======================================================= Handler to get productReview Page ============================================================
	@GetMapping("/admin/productReview")
	public String productReview(Model m, Principal principal) {
		
		List<Review> showAllreviews = rdao.showAllreviews();
		m.addAttribute("showAllreviews", showAllreviews);
		
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);

		return "admin/admin-productReview";
	}
	
	
	

}
