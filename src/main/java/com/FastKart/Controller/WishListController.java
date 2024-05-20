package com.FastKart.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FastKart.Dao.WishListDao;
import com.FastKart.Repository.ProductRepository;
import com.FastKart.Repository.UserRepository;
import com.FastKart.Repository.WishListRepository;
import com.FastKart.entities.Product;
import com.FastKart.entities.User;
import com.FastKart.entities.WishList;

@Controller
public class WishListController {

	@Autowired
	private WishListDao wdao;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private WishListRepository wishListRepository;
	

//=============================================== ADD TO WISHIST HANDLER IF USER IS LOGIN ===========================================================
	@PostMapping("/addToWishList")
	public String addToWishList(@ModelAttribute WishList w, Principal principal, @RequestParam("pid") int pid, RedirectAttributes redirectAttributes) {
	    if (principal != null) {
	        Product product = productRepository.findById(pid).orElse(null);
	        String name = principal.getName();
	        User user = userRepository.getUserByUserName(name);
	        
	        if (product != null && user != null) {
	            boolean existsWishList = wishListRepository.existsByUserAndProduct(user, product);
	            
	            if (!existsWishList) {
	                wdao.addToWishList(w, pid, principal);
	                redirectAttributes.addFlashAttribute("success", "Wishlist added successfully");
	            } else {
	                redirectAttributes.addFlashAttribute("wishlisterror", "Wishlist already exists");
	            }
	            return "redirect:/wishList";
	        } else {
	            // Handle case where product or user is null
	            redirectAttributes.addFlashAttribute("error", "Product or user not found");
	            return "redirect:/wishList"; // or redirect to an error page
	        }
	    } else {
	        // Redirect to the login page
	        return "redirect:/login";
	    }
	}

//============================================== DELETE WISHLIST ITEM HANDLER =========================================================================
	
	@GetMapping("/deleteWishList/{id}")
	public String deleteWishList(@PathVariable("id") int id, Model m,  @RequestParam(value="source", required =  false) String source) {
		
		wdao.deleteWishList(id);
		 if ("userDashboard".equals(source)) {
		        return "redirect:/userDashboard";
		    } else {
		        return "redirect:/wishList";
		    }
	}
	
	
	
	
}
