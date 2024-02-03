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
	@JoinColumn(name="cartid")
	private Cart cart;
	
	@ManyToOne
	@JoinColumn(name="checkoutid")
	private CheckOut checkOut;
	
	private int status;
	
	@JoinColumn(name = "date")
    private LocalDate orderDate;

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

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
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

	public Order(int id, User user, Cart cart, CheckOut checkOut, int status, LocalDate orderDate) {
		super();
		this.id = id;
		this.user = user;
		this.cart = cart;
		this.checkOut = checkOut;
		this.status = status;
		this.orderDate = orderDate;
	}

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", user=" + user + ", cart=" + cart + ", checkOut=" + checkOut + ", status=" + status
				+ ", orderDate=" + orderDate + "]";
	}

	
}
