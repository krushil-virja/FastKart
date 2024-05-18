package com.FastKart.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FastKart.Dao.cartDao;
import com.FastKart.Dao.userDao;
import com.FastKart.Repository.CartRepository;
import com.FastKart.Repository.CouponRepository;
import com.FastKart.entities.Cart;
import com.FastKart.entities.Coupon;
import com.itextpdf.text.log.SysoCounter;

@Controller
public class cartController {

	@Autowired
	private cartDao cdao;
	
	@Autowired
	private userDao udao;
	
	@Autowired
	private cartDao cartdao;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CouponRepository couponRepository;

	 @PostMapping("/addToCart")
	    public String  addToCart(@ModelAttribute Cart cart, @RequestParam("pid") int pid,@RequestParam("quntity") int quntity, Principal principal) {
	   
		
		
	 if (principal != null && udao.isUserLoggedIn(principal)) {
		 cdao.addToCart(cart, pid, quntity, principal);
         return "redirect:/viewCart";
	    } else {
	        // Redirect to the login page
	        return "redirect:/login";	
	    }
}
	 
@GetMapping("/deleteCart/{id}")
public String deleteCart(@PathVariable("id") int id , Model m) {
	
	cartdao.deleteCart(id);
	return "redirect:/viewCart";
}


@PostMapping("/updateCart")
public String updateCart(@RequestParam("id") int id, @RequestParam("quntity") int quntity) {
	
	cartdao.updateCart(id, quntity);
	
	return "redirect:/viewCart"; 
	
}

/*
@PostMapping("/discount") 
public String getDiscount( @RequestParam("couponCode") String couponCode, Principal principal, Model m) {

	 List<Cart> cartList = cartdao.viewCart(principal);

	    // Calculate the total, shippingTotal, totalWithShipping, and discount
	    int totalOfCart = cartdao.getTotalOfCart(cartList);
	    int shippingTotal = cartdao.getShippingTotal(cartList);
	    int totalWithShipping = cartdao.getTotalWithShipping(cartList);
	    int discount = cartdao.getDiscount(couponCode, principal);

	    System.out.println();
	    System.out.println();
	    System.out.println();
	    System.out.println();
	    // Update the model attributes
	    m.addAttribute("subTotalOfCart", totalOfCart);
	    m.addAttribute("shippingTotal", shippingTotal);
	    m.addAttribute("totalWithShipping", totalWithShipping);
	    m.addAttribute("discount", discount);

	    // Calculate the new grandTotal after applying the discount
	    int grandTotal = totalWithShipping - discount;
	    m.addAttribute("grandTotal", grandTotal);

	    // Redirect to the "viewCart" page
	    return "redirect:/viewCart";

}
*/


@PostMapping("/discount") 
public String getDiscount( @RequestParam("couponCode") String couponCode, Principal principal, Model m, RedirectAttributes redirectAttributes) {

	Coupon coupon = couponRepository.findByCouponCode(couponCode);
	
	if(coupon!=null) {
	 List<Cart> cartList = cartdao.viewCart(principal);

	    // Calculate the total, shippingTotal, totalWithShipping, and discount
	    int totalOfCart = cartdao.getTotalOfCart(cartList);
	    int shippingTotal = cartdao.getShippingTotal(cartList);
	    int totalWithShipping = cartdao.getTotalWithShipping(cartList);
	    int discount = cartdao.getDiscount(couponCode, principal);

	  
	    System.out.println(totalOfCart);
	    System.out.println(shippingTotal);
	    System.out.println(totalWithShipping);
	    System.out.println(discount);
	    // Update the model attributes
	    m.addAttribute("subTotalOfCart", totalOfCart);
	    m.addAttribute("shippingTotal", shippingTotal);
	    m.addAttribute("totalWithShipping", totalWithShipping);
	    m.addAttribute("discount", discount);

	    // Calculate the new grandTotal after applying the discount
	    int grandTotal = totalWithShipping - discount;
	    m.addAttribute("grandTotal", grandTotal);

	    
	    // Add flash attribute to show discount applied message
	   
	    redirectAttributes.addFlashAttribute("success", "Coupon Applied");
	    

	    // Redirect to the "viewCart" page
	    return "redirect:/viewCart?subTotalOfCart=" + totalOfCart + "&shippingTotal=" + shippingTotal + "&totalWithShipping=" + totalWithShipping + "&discount=" + discount + "&grandTotal=" + grandTotal;
	}
	else {
		
		  System.out.println("invalid Coupon Code");
		  redirectAttributes.addFlashAttribute("error", "invalid Coupon Code");
		    
	   return "redirect:/viewCart";
	}

}



	 
	 
	
}
