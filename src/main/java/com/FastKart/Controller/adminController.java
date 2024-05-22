package com.FastKart.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
	@GetMapping("/index")
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
       
		return "admin/admin-index";
		}
		else {
			return "redirect:/login";
		}
	}

//========================================================= Handler to get Admin addCategory page =========================================================	
	@GetMapping("/addCategory")
	public String addCategory( Model m ) {
		m.addAttribute("category", new Category());

		return "admin/admin-addCategory";
	}

//========================================================= Handler to get Admin Category page ===========================================================
	@GetMapping("/category")
	public String category(Model m) {

		List<Category> showAllCategory = cdao.showAllCategory();
		m.addAttribute("category", showAllCategory);

		return "admin/admin-category";
	}

//========================================================= Handler to get Admin addSubCategory page =====================================================
	@GetMapping("/addSubCategory")
	public String addSubCategory(Model m) {

		List<Category> showAllCategory = cdao.showAllCategory();
		m.addAttribute("category", showAllCategory);
		m.addAttribute("subCategory", new subCategory());
		return "admin/admin-addSubCategory";
	}
	
	
	@GetMapping("/updateCategory")
	public String updateCategory() {
		
		return "admin/admin-updateCategory";
	}

//========================================================= Handler to get Admin subCategory page=========================================================
	@GetMapping("/subCategory")
	public String subCategory(Model m) {

		List<subCategory> showAllSubCategory = scdao.showAllSubCategory();
		m.addAttribute("subCategory", showAllSubCategory);

		return "admin/admin-subCategory";
	}

//========================================================= Handler to get Admin Update subCategory page=========================================================

	@GetMapping("/updateSubCategory")
	public String updateSubCategory() {
		
		return "admin/admin-updateSubCategory";
	}
//========================================================= Handler to get Admin addProduct page =========================================================
	@GetMapping("/addProduct")
	public String addProduct(Model m) {

		List<Category> showAllCategory = cdao.showAllCategory();
		m.addAttribute("category", showAllCategory);

		List<subCategory> showAllSubCategory = scdao.showAllSubCategory();
		m.addAttribute("subCategory", showAllSubCategory);
		
		m.addAttribute("product", new Product());
		return "admin/admin-addProduct";
	}
	
	@GetMapping("/updateProduct")
	public String updateProduct(Model m) {
		
		
		return "admin/admin-updateProduct";
	}

//========================================================= Handler to get Admin Product page ============================================================
	@GetMapping("/product")
	public String product(Model m) {

		List<Product> showAllProduct = pdao.showAllProduct();
		m.addAttribute("product", showAllProduct);

		return "admin/admin-product";
	}

//========================================================= Handler to get Admin allUSer page ============================================================
	@GetMapping("/allUser")
	public String allUser(Model m) {

		List<User> showAllUser = udao.ShowAllUser();
		m.addAttribute("user", showAllUser);
		return "admin/admin-allUser";
	}

//========================================================== Handler to get Admin Create Coupon page =====================================================
	@GetMapping("/createCoupon")
	public String createCoupon() {

		return "admin/admin-createCoupon";
	}

//======================================================= Handler to get Admin allCoupon page ============================================================		
	@GetMapping("/allCoupon")
	public String allCoupon(Model m) {

		List<Coupon> allCoupon = coupondao.allCoupon();
		m.addAttribute("allCoupon", allCoupon);
		return "admin/admin-allCoupon";
	}

//===================================================== Handler to get Admin allOrder page ==============================================================
	@GetMapping("/allOrder")
	public String allOrder(Model m) {

		List<Order> allOrder = oDao.allOrder();

		m.addAttribute("allOrder", allOrder);

		return "admin/admin-allOrder";
	}

//===================================================== Handler to get Admin activeOrder page ==============================================================
	@GetMapping("/activeOrder")
	public String activeOrder(Model m) {
		
		List<Order> allOrder = oDao.allOrder();

		m.addAttribute("allOrder", allOrder);


		return "admin/admin-activeOrder";
	}

//===================================================== Handler to get Admin pendingOrder page ==============================================================
	@GetMapping("/pendingOrder")
	public String pendingOrder(Model m) {

		List<Order> allOrder = oDao.allOrder();

		m.addAttribute("allOrder", allOrder);

		return "admin/admin-pendingOrder";
	}

//===================================================== Handler to get Admin shippingOrder page ==============================================================
	@GetMapping("/shippingOrder")
	public String shippingOrder(Model m) {

		List<Order> allOrder = oDao.allOrder();

		m.addAttribute("allOrder", allOrder);

		return "admin/admin-shippingOrder";
	}

//===================================================== Handler to get Admin deliveredOrder page ==============================================================
	@GetMapping("/deliveredOrder")
	public String deliveredOrder(Model m) {

		List<Order> allOrder = oDao.allOrder();

		m.addAttribute("allOrder", allOrder);

		return "admin/admin-deliveredOrder";
	}

//====================================================== Handler to get Admin orderDetails page ==========================================================
	@GetMapping("/orderDetails")
	public String orderDetails() {

		return "admin/admin-orderDetails";
	}

//====================================================== Handler toget Admin orderTracking page =========================================================
	@GetMapping("/orderTracking")
	public String orderTracking() {

		return "admin/admin-orderTracking";
	}

//======================================================= Handler to get productReview Page ============================================================
	@GetMapping("/productReview")
	public String productReview(Model m) {
		
		List<Review> showAllreviews = rdao.showAllreviews();
		m.addAttribute("showAllreviews", showAllreviews);

		return "admin/admin-productReview";
	}
	
	
	

}
