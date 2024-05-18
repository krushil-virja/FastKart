package com.FastKart.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FastKart.entities.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer > {

	
	  Coupon findByCouponCode(String couponCode);
	  
	  boolean existsByCouponCode(String couponCode);
}
