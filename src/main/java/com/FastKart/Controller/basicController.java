package com.FastKart.Controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.PageRanges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FastKart.Dao.WishListDao;
import com.FastKart.Dao.addressDao;
import com.FastKart.Dao.cartDao;
import com.FastKart.Dao.categoryDao;
import com.FastKart.Dao.orderDao;
import com.FastKart.Dao.productDao;
import com.FastKart.Dao.reviewsDao;
import com.FastKart.Dao.userDao;
import com.FastKart.Repository.CartRepository;
import com.FastKart.Repository.OrderRepository;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.Repository.ReviewsRepository;
import com.FastKart.Repository.UserRepository;
import com.FastKart.Repository.WishListRepository;
import com.FastKart.entities.Address;
import com.FastKart.entities.Cart;
import com.FastKart.entities.Category;
import com.FastKart.entities.Order;
import com.FastKart.entities.Product;
import com.FastKart.entities.User;
import com.FastKart.entities.WishList;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller

public class basicController {

	@Autowired
	private productDao pdao;

	@Autowired
	private categoryDao cdao;

	@Autowired
	private reviewsDao rdao;

	@Autowired
	private userDao udao;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ReviewsRepository reviewsRepository;

	@Autowired
	private cartDao cartdao;

	@Autowired
	private WishListDao wdao;

	@Autowired
	private addressDao addao;

	@Autowired
	private orderDao oDao;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private WishListRepository wishListRepository;

	@Autowired
	private OrderRepository orderRepository;

//===================================================== HANDLER TO FETCH LOGIN USER====================================================================
	// in any situation i want to find something related to user (ex addToCart if
	// user login) than i will use this method
	// this method is check for user is login or not
	// also this handler used in all handler where you need to find that user is
	// login or not

	@ModelAttribute("loggedInUser")
	public User getLoggedInUser(Principal principal) {
		if (principal != null) {
			String username = principal.getName();
			return userRepository.getUserByUserName(username);
		}
		return null;
	}

//========================================================= HOME PAGE METHOD ==========================================================================	
	@GetMapping("/home")
	public String home(Model m, Principal principal) {

		List<Category> showAllCategory = cdao.showAllCategory();
		m.addAttribute("category", showAllCategory);

		int id1 = 1;
		List<Product> product1 = pdao.findProductByCategory(id1);
		m.addAttribute("product1", product1);

		int id2 = 2;
		List<Product> product2 = pdao.findProductByCategory(id2);
		m.addAttribute("product2", product2);

		int id3 = 3;
		List<Product> product3 = pdao.findProductByCategory(id3);
		m.addAttribute("product3", product3);

		int id4 = 4;
		List<Product> product4 = pdao.findProductByCategory(id4);
		m.addAttribute("product4", product4);

		int id5 = 5;
		List<Product> product5 = pdao.findProductByCategory(id5);
		m.addAttribute("product5", product5);

		int id6 = 6;
		List<Product> product6 = pdao.findProductByCategory(id6);
		m.addAttribute("product6", product6);

		int id7 = 7;
		List<Product> product7 = pdao.findProductByCategory(id7);
		m.addAttribute("product7", product7);

		int id8 = 8;
		List<Product> product8 = pdao.findProductByCategory(id8);
		m.addAttribute("product8", product8);

		boolean userLoggedIn = udao.isUserLoggedIn(principal);
		m.addAttribute("userLoggedIn", userLoggedIn);

		User loggedInUser = getLoggedInUser(principal);
		m.addAttribute("loggedInUser", loggedInUser);

		List<Product> topSellingProducts = pdao.getTopSellingProducts(10);

		m.addAttribute("topSellingProducts", topSellingProducts);

		return "home";

	}
//========================================================== REGISTER PAGE METHOD ======================================================================	

	@GetMapping("/register")
	public String register(Model m) {

		m.addAttribute("user", new User());
		return "register";
	}

//======================================================== LOGIN PAGE METHOD =========================================================================

	@GetMapping("/login")
	public String login() {

		return "login";
	}
//======================================================= SHOP PAGE Handler ============================================================================	

	@GetMapping("/shop")
	public String shop(Model m, @RequestParam(required = false, name = "category") List<Integer> categoryId,
			@RequestParam(required = false, name = "filterByPrice") List<Integer> filterByPrice,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
			@RequestParam(value = "paeSize", required = false, defaultValue = "12") Integer pageSize

	) {
		int minPrice = 0;
		int maxPrice = 0;

		if (filterByPrice != null) {
			if (filterByPrice.contains(1)) {

				minPrice = 0;
				maxPrice = 1000;
			}

			if (filterByPrice.contains(2)) {

				if (!filterByPrice.contains(1)) {
					minPrice = 1000;

				}

				maxPrice = 10000;
			}

			if (filterByPrice.contains(3)) {

				if (!filterByPrice.contains(1) && !filterByPrice.contains(2)) {
					minPrice = 10000;

				}

				maxPrice = 100000;
			}
		}

		List<Category> category = cdao.showAllCategory();
		m.addAttribute("category", category);

		System.out.println("category id is" + categoryId);

		if (categoryId != null && filterByPrice != null) {
			PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize);

			Page<Product> page = productRepository.findByCategoryIdsAndPriceBetween(categoryId, minPrice, maxPrice,
					pageable);
			List<Product> byCategoryIdsAndPriceBetween = page.getContent();

			m.addAttribute("productByCategories", byCategoryIdsAndPriceBetween);

			m.addAttribute("currentPage", pageNumber);
			System.out.println(pageNumber);
			m.addAttribute("totalPages", page.getTotalElements());
			System.out.println(page.getTotalElements());
			m.addAttribute("totalPages", page.getTotalPages());
			System.out.println(page.getTotalPages());

			m.addAttribute("pageSize", pageSize);
		} else if (categoryId != null) {

			PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize);

			Page<Product> page = pdao.findProductByCategories(categoryId, pageable);

			List<Product> productByCategories = page.getContent();

			m.addAttribute("productByCategories", productByCategories);

			m.addAttribute("currentPage", pageNumber);
			System.out.println(pageNumber);
			m.addAttribute("totalPages", page.getTotalElements());
			System.out.println(page.getTotalElements());
			m.addAttribute("totalPages", page.getTotalPages());
			System.out.println(page.getTotalPages());

			m.addAttribute("pageSize", pageSize);

		} else if (filterByPrice != null) {
			PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize);

			Page<Product> page = productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
			List<Product> productByPrices = page.getContent();

			m.addAttribute("productByCategories", productByPrices);

			m.addAttribute("currentPage", pageNumber);
			System.out.println(pageNumber);
			m.addAttribute("totalPages", page.getTotalElements());
			System.out.println(page.getTotalElements());
			m.addAttribute("totalPages", page.getTotalPages());
			System.out.println(page.getTotalPages());

			m.addAttribute("pageSize", pageSize);
		} else {

			// show product using pagination 1 page - 12 product
			Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
			Page<Product> page = productRepository.findAll(pageable);
			List<Product> product = page.getContent();

			m.addAttribute("products", product);
			m.addAttribute("totalPages", page.getTotalPages());

			System.out.println(page.getTotalPages());

			m.addAttribute("totalItems", page.getTotalElements());
			System.out.println(page.getTotalElements());
			m.addAttribute("currentPage", pageNumber);

			m.addAttribute("pageSize", pageSize);
			System.out.println(pageNumber);

		}

	
		return "shop";
	}

//======================================================= ABOUT PAGE Handler ============================================================================

	@GetMapping("/about")
	public String about() {

		return "about-us";
	}

//======================================================= CONTACT PAGE Handler ============================================================================
	@GetMapping("/contact")
	public String contact() {

		return "contact-us";
	}

//======================================================= VIEWCART PAGE METHOD ============================================================================	
	/*
	 * @GetMapping("/viewCart") public String showCart(Model m, Principal principal,
	 * Cart cart) {
	 * 
	 * if (principal != null) { List<Cart> viewCart = cartdao.viewCart(principal);
	 * m.addAttribute("cart", viewCart);
	 * 
	 * int subTotalOfCart = cartdao.getTotalOfCart(viewCart);
	 * m.addAttribute("subTotalOfCart", subTotalOfCart);
	 * 
	 * int totalWithShipping = cartdao.getTotalWithShipping(viewCart);
	 * m.addAttribute("grandTotal", totalWithShipping);
	 * 
	 * int shippingTotal = cartdao.getShippingTotal(viewCart);
	 * m.addAttribute("shippingTotal", shippingTotal);
	 * 
	 * m.addAttribute("discount", 0); return "Cart"; } else { // Redirect to the
	 * login page return "redirect:/login"; } }
	 */

	@GetMapping("/viewCart")
	public String showCart(Model m, Principal principal, Cart cart, HttpServletRequest request, HttpSession session) {

		if (principal != null) {
			int subTotalOfCart = 0;
			int totalWithShipping = 0;
			int shippingTotal = 0;
			int discount = 0;
			int grandTotal = 0;

			if (request.getParameter("subTotalOfCart") != null && request.getParameter("shippingTotal") != null
					&& request.getParameter("totalWithShipping") != null && request.getParameter("discount") != null
					&& request.getParameter("grandTotal") != null) {

				subTotalOfCart = Integer.parseInt(request.getParameter("subTotalOfCart"));
				shippingTotal = Integer.parseInt(request.getParameter("shippingTotal"));
				totalWithShipping = Integer.parseInt(request.getParameter("totalWithShipping"));
				discount = Integer.parseInt(request.getParameter("discount"));
				grandTotal = Integer.parseInt(request.getParameter("grandTotal"));
			} else {
				List<Cart> viewCart = cartdao.viewCart(principal);
				subTotalOfCart = cartdao.getTotalOfCart(viewCart);
				grandTotal = cartdao.getTotalWithShipping(viewCart);
				shippingTotal = cartdao.getShippingTotal(viewCart);
				System.out.println(shippingTotal);
				m.addAttribute("cart", viewCart);
				// Add attributes to the model

			}

			// Set session attributes
			session.setAttribute("subTotalOfCart", subTotalOfCart);
			session.setAttribute("totalWithShipping", totalWithShipping);
			session.setAttribute("shippingTotal", shippingTotal);
			session.setAttribute("discount", discount);
			session.setAttribute("grandTotal", grandTotal);

			m.addAttribute("subTotalOfCart", subTotalOfCart);
			m.addAttribute("grandTotal", grandTotal);
			m.addAttribute("shippingTotal", shippingTotal);
			m.addAttribute("discount", discount);

			return "Cart";
		} else {
			// Redirect to the login page
			return "redirect:/login";
		}
	}

//======================================================= CHECKOUT PAGE METHOD ============================================================================
	@GetMapping("/checkOut")
	public String checkOut() {

		return "checkOut";
	}

	@GetMapping("/checkOut1")
	public String CHECKOUT(Model m, Principal principal) {

		if (principal != null) {
			List<Address> showAllAddress = addao.showAllAddress(principal);
			m.addAttribute("showAllAddress", showAllAddress);

			List<Cart> viewCart = cartdao.viewCart(principal);
			m.addAttribute("viewCart", viewCart);

			int subTotalOfCart = cartdao.getTotalOfCart(viewCart);
			m.addAttribute("subTotalOfCart", subTotalOfCart);

			int shippingTotal = cartdao.getShippingTotal(viewCart);
			m.addAttribute("shippingTotal", shippingTotal);

			int totalWithShipping = cartdao.getTotalWithShipping(viewCart);
			m.addAttribute("grandTotal", totalWithShipping);

			return "CKECHOUT";
		} else {
			return null;
		}

	}

//======================================================= WISHLIST PAGE METHOD ============================================================================	
	@GetMapping("/wishList")
	public String wishList(Principal principal, Model m) {
		if (principal != null) {
			List<WishList> viewWishList = wdao.viewWishList(principal);
			m.addAttribute("viewWishList", viewWishList);
			return "wishList";
		} else {
			return "redirect:/login";
		}
	}

//======================================================= USERDASHBOARD PAGE METHOD ============================================================================
	@GetMapping("/userDashboard")
	public String userDashboard(Model m, Principal principal) {

		if(principal!=null) {
		User loggedInUser = udao.getLoggedInUser(principal);
		m.addAttribute("user", loggedInUser);
		/*
		 * List<Order> orders = oDao.allOrder(); m.addAttribute("orders", orders);
		 */

		List<Order> orders = orderRepository.getOrdersByUser(loggedInUser);
		m.addAttribute("orders", orders);

		List<Address> showAllAddress = addao.showAllAddress(principal);
		m.addAttribute("showAllAddress", showAllAddress);

		List<WishList> viewWishList = wdao.viewWishList(principal);
		m.addAttribute("viewWishList", viewWishList);

		return "userDashboard";
		}
		else {
			return "redirect:/login";
		}
	}

	@GetMapping("invoice_template")
	public String invoice_template() {

		return "invoice_template";
	}

//======================================================= PRODUCTDETAILS PAGE METHOD ============================================================================	
	@GetMapping("/productDetails")
	public String productDetails(Model model, @RequestParam("pid") int pid) {

		return "productDetails";
	}

//======================================================= updatePassword PAGE METHOD ============================================================================	
	@GetMapping("/updatePassword")
	public String updatePassword() {

		return "updatePassword";
	}

//======================================================= GET ALL PRODUCT PAGE METHOD ============================================================================
	@GetMapping("/getProduct")
	public String product(Model m) {

		List<Product> showAllProduct = pdao.showAllProduct();
		m.addAttribute("showAllProduct", showAllProduct);

		List<Category> showAllCategory = cdao.showAllCategory();
		m.addAttribute("showAllCategory", showAllCategory);

		return "product";
	}

//==================================================== COMFIRM ORDER PAGE HANDLER ================================================================

	// fetch checkout id through query parameter

	/*
	 * @GetMapping("/comfirmOrder") public String
	 * comfirmOrder(@RequestParam("checkoutId") int checkoutId, Principal principal,
	 * Model m) { List<Cart> viewCart = cartdao.viewCart(principal);
	 * m.addAttribute("viewCart", viewCart);
	 * 
	 * int subTotalOfCart = cartdao.getTotalOfCart(viewCart);
	 * m.addAttribute("subTotalOfCart", subTotalOfCart);
	 * 
	 * int shippingTotal = cartdao.getShippingTotal(viewCart);
	 * m.addAttribute("shippingTotal", shippingTotal);
	 * 
	 * System.out.println("comfirmOrder method called with checkoutId: " +
	 * checkoutId);
	 * 
	 * m.addAttribute("checkoutId", checkoutId);
	 * 
	 * return "ORDER"; }
	 */

// fetch checkout through HttpSession 	
	@GetMapping("/comfirmOrder")
	public String comfirmOrder(HttpSession session, Principal principal, Model m) {
		List<Cart> viewCart = cartdao.viewCart(principal);
		m.addAttribute("viewCart", viewCart);

		int subTotalOfCart = cartdao.getTotalOfCart(viewCart);
		m.addAttribute("subTotalOfCart", subTotalOfCart);

		int shippingTotal = cartdao.getShippingTotal(viewCart);
		m.addAttribute("shippingTotal", shippingTotal);

		int totalWithShipping = cartdao.getTotalWithShipping(viewCart);
		m.addAttribute("grandTotal", totalWithShipping);

		Integer checkoutId = (Integer) session.getAttribute("checkoutId");
		System.out.println("comfirmOrder method called with checkoutId: " + checkoutId);

		m.addAttribute("checkoutId", checkoutId);

		User user = userRepository.getUserByUserName(principal.getName());
		m.addAttribute("user", user);

		return "ORDER";
	}

	@GetMapping("trackOrder")
	public String trackOrder() {

		return "orderTracking";
	}

	@GetMapping("/successOrder")
	public String successOrder() {

		return "order_success";
	}

	@GetMapping("/invoice")
	public String invoice() {

		return "invoice";
	}

	/*
	 * @GetMapping("/rateProduct") public String rateProduct( ) {
	 * 
	 * return "rating"; }
	 */

	@GetMapping("/rateProduct/{id}")
	public String rateProduct2(@PathVariable("id") int id, Model m) {

		Product p = productRepository.findById(id).orElseThrow();
		m.addAttribute("product", p.getId());

		return "rating";
	}

//============================================================== ALL MODEL ==========================================================================

	// The reason to create model is when i have to fetch one functionality in more
	// page i will use this model Instead of call method in every URL

	@ModelAttribute("cartItemCount")
	public int countCartByUser(Principal principal) {

		User loggedInUser = getLoggedInUser(principal);
		return cartRepository.countByUser(loggedInUser);
	}

	@ModelAttribute("wishListCount")
	public int countWishListByUser(Principal principal) {

		User loggedInUser = getLoggedInUser(principal);
		return wishListRepository.countByUser(loggedInUser);
	}

	// this model is for if wishList item ==0 than itemCount not show instead of 0
	@ModelAttribute("wishList")
	public List<WishList> getUserWishList(Principal principal) {
		User loggedInUser = getLoggedInUser(principal);
		return wishListRepository.findByUser(loggedInUser);

	}

	// in any condition user is login and his cart item (Cart size) is 0 than we do
	// not show cartitemOut instead of 0
	@ModelAttribute("cart")
	public List<Cart> viewCart(Principal principal) {

		User loggedInUser = getLoggedInUser(principal);

		return cartRepository.findByUser(loggedInUser);

	}

	@ModelAttribute("orderCount")
	public int countOrderByUser(Principal principal) {

		User loggedInUser = getLoggedInUser(principal);

		return orderRepository.countByUser(loggedInUser);

	}

	@ModelAttribute("deliveredOrder")
	public int deliveredOrder(Principal principal) {

		User loggedInUser = getLoggedInUser(principal);

		int deliveredStatus = 4;

		return orderRepository.countByStatusAndUser(deliveredStatus, loggedInUser);

	}

	@ModelAttribute("countProductsInRange0To1000")
	public int countProductsInRange0To1000() {

		int countProductsInRange0To1000 = productRepository.countProductsInRange0To1000();
		return countProductsInRange0To1000;
	}

	@ModelAttribute("countProductsInRange1000To10000")
	public int countProductsInRange1000To10000() {

		int countProductsInRange1000To10000 = productRepository.countProductsInRange1000To10000();
		return countProductsInRange1000To10000;
	}

	@ModelAttribute("countProductsInRange10000To100000")
	public int countProductsInRange10000To100000() {

		int countProductsInRange10000To100000 = productRepository.countProductsInRange10000To100000();
		return countProductsInRange10000To100000;
	}

	@ModelAttribute("/allCustomer")
	public int countCustomer() {

		int countDistinctUsers = orderRepository.countDistinctUsers();
		return countDistinctUsers;
	}

	/*
	 * @ModelAttribute("countRatingByProduct") public int
	 * countOfRatingByProduct( @RequestParam("pid") int pid) {
	 * 
	 * 
	 * Product product = pdao.findProductById(pid);
	 * 
	 * return reviewsRepository.countByProduct(product); }
	 */

}
