package com.FastKart.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "subcategory")
public class subCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotEmpty(message = "Please enter a subCategory name")
	@Column(name = "subCatName")
	private String sub_cat_name;

	@Column(name = "subCat_Image")
	@NotBlank(message = "Please select an image")
	private String sub_cat_image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cid")
	@NotNull(message = "Please select a category")
	private Category category;

	public subCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public subCategory(int id, String sub_cat_name, String sub_cat_image, Category category) {
		super();
		this.id = id;
		this.sub_cat_name = sub_cat_name;
		this.sub_cat_image = sub_cat_image;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSub_cat_name() {
		return sub_cat_name;
	}

	public void setSub_cat_name(String sub_cat_name) {
		this.sub_cat_name = sub_cat_name;
	}

	public String getSub_cat_image() {
		return sub_cat_image;
	}

	public void setSub_cat_image(String sub_cat_image) {
		this.sub_cat_image = sub_cat_image;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "subCategory [id=" + id + ", sub_cat_name=" + sub_cat_name + ", sub_cat_image=" + sub_cat_image
				+ ", category=" + category + "]";
	}

}
