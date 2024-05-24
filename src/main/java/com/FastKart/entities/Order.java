package com.FastKart.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "`order`")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="uid")
	
	private User user;
	
	@ManyToOne
	@JoinColumn(name="pid")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="checkoutid")
	private CheckOut checkOut;
	
	private int status;
	
	@JoinColumn(name = "date")
    private LocalDate orderDate;
	
	@JoinColumn(name="quantity")
	private int quantity;
	
	@JoinColumn(name="subtotal")
	private double subtotal;
	@JoinColumn(name="shipping")
	private double shipping;
	@JoinColumn(name="discount")
	private double discount;
	@JoinColumn(name="total")
	private double total;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public CheckOut getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(CheckOut checkOut) {
		this.checkOut = checkOut;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public double getShipping() {
		return shipping;
	}
	public void setShipping(double shipping) {
		this.shipping = shipping;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", user=" + user + ", product=" + product + ", checkOut=" + checkOut + ", status="
				+ status + ", orderDate=" + orderDate + ", quantity=" + quantity + ", subtotal=" + subtotal
				+ ", shipping=" + shipping + ", discount=" + discount + ", total=" + total + "]";
	}
	public Order(int id, User user, Product product, CheckOut checkOut, int status, LocalDate orderDate, int quantity,
			double subtotal, double shipping, double discount, double total) {
		super();
		this.id = id;
		this.user = user;
		this.product = product;
		this.checkOut = checkOut;
		this.status = status;
		this.orderDate = orderDate;
		this.quantity = quantity;
		this.subtotal = subtotal;
		this.shipping = shipping;
		this.discount = discount;
		this.total = total;
	}
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	
	
}
