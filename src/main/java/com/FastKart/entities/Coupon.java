package com.FastKart.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="coupon")
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String coupon_name;
	
	@Column(name="couponCode", unique=true)
	private String couponCode;
	private int discount;
	private int min_cart_subtotal;
	private int max_cart_subtotal;
    private String expiry_date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCoupon_name() {
		return coupon_name;
	}
	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}
	@Column(name="couponCode", unique=true)
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public int getMin_cart_subtotal() {
		return min_cart_subtotal;
	}
	public void setMin_cart_subtotal(int min_cart_subtotal) {
		this.min_cart_subtotal = min_cart_subtotal;
	}
	public int getMax_cart_subtotal() {
		return max_cart_subtotal;
	}
	public void setMax_cart_subtotal(int max_cart_subtotal) {
		this.max_cart_subtotal = max_cart_subtotal;
	}
	public String getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}
	public Coupon(int id, String coupon_name, String couponCode, int discount, int min_cart_subtotal,
			int max_cart_subtotal, String expiry_date) {
		super();
		this.id = id;
		this.coupon_name = coupon_name;
		this.couponCode = couponCode;
		this.discount = discount;
		this.min_cart_subtotal = min_cart_subtotal;
		this.max_cart_subtotal = max_cart_subtotal;
		this.expiry_date = expiry_date;
	}
	public Coupon() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", coupon_name=" + coupon_name + ", couponCode=" + couponCode + ", discount="
				+ discount + ", min_cart_subtotal=" + min_cart_subtotal + ", max_cart_subtotal=" + max_cart_subtotal
				+ ", expiry_date=" + expiry_date + "]";
	}
    
    
    
    

	
}
