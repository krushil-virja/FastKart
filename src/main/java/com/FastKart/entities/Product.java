package com.FastKart.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "Please enter product name")
	private String pname;

	@NotNull(message = "Please upload a category image")
	private String pimage;

	@NotNull(message = "Category can't be null")
	@Valid
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cid")
	private Category category;

	@NotNull(message = "subCategory can't be null")
	@Valid
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scid")
	private subCategory subcategory;

	@NotBlank(message = "Please enter brand name")
	@Size(max = 50, message = "Brand must be less than 50 characters")
	private String brand;

	@NotNull(message = "Please enter a prices")
	//@Min(value = 0, message = "Price must be greater than or equal to 0")
	private int price;

	@NotBlank(message = "Please write description")
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPimage() {
		return pimage;
	}

	public void setPimage(String pimage) {
		this.pimage = pimage;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public subCategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(subCategory subcategory) {
		this.subcategory = subcategory;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", pname=" + pname + ", pimage=" + pimage + ", category=" + category
				+ ", subcategory=" + subcategory + ", brand=" + brand + ", price=" + price + ", description="
				+ description + "]";
	}

	public Product(int id, String pname, String pimage, Category category, subCategory subcategory, String brand,
			int price, String description) {
		super();
		this.id = id;
		this.pname = pname;
		this.pimage = pimage;
		this.category = category;
		this.subcategory = subcategory;
		this.brand = brand;
		this.price = price;
		this.description = description;
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

}
