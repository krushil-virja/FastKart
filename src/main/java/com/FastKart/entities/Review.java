package com.FastKart.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="review")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="pid")
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="uid")
	private User user;
	
	private String reviewtitle;
	private int rating;
	private String message;
	private LocalDate reviewdate;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getReviewtitle() {
		return reviewtitle;
	}
	public void setReviewtitle(String reviewtitle) {
		this.reviewtitle = reviewtitle;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDate getReviewdate() {
		return reviewdate;
	}
	public void setReviewdate(LocalDate reviewdate) {
		this.reviewdate = reviewdate;
	}
	
	
	@Override
	public String toString() {
		return "Review [id=" + id + ", product=" + product + ", user=" + user + ", reviewtitle=" + reviewtitle
				+ ", rating=" + rating + ", message=" + message + ", reviewdate=" + reviewdate + "]";
	}
	
	
	public Review(int id, Product product, User user, String reviewtitle, int rating, String message,
			LocalDate reviewdate) {
		super();
		this.id = id;
		this.product = product; 
		this.user = user;
		this.reviewtitle = reviewtitle;
		this.rating = rating;
		this.message = message;
		this.reviewdate = reviewdate;
	}
	
	
	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
}
