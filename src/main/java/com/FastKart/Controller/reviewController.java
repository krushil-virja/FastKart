package com.FastKart.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FastKart.Dao.reviewsDao;
import com.FastKart.entities.Product;
import com.FastKart.entities.Review;


@Controller
public class reviewController {

	@Autowired
	private reviewsDao rDao;
	
	@PostMapping("/rateProduct")
	public  String productReview(Principal principal, @ModelAttribute Review review,@RequestParam("pid")  int pid, @RequestParam("rating") int rating) {
		 
		rDao.productReviews(review, principal, pid, rating);
		
		return "redirect:/userDashboard";
		
	}
	
	
}
