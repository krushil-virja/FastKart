package com.FastKart.entities;

import java.sql.Date;

import org.hibernate.annotations.Parent;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="user")
public class User {

	
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotBlank(message="Please enter your name")
	private String name;
	
	@NotBlank(message="please enter your Email")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Invalid email format. Must be in the 'example@gmail.com' format.")
	private String email;
	
	@NotBlank(message="Please enter your password")
	@Pattern(
	    regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
	    message = "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one digit, and one special character."
	)
	private String password;
	private String role;
	@JoinColumn(name="profileImage")
	private String profileImage;
	private Long contact;
	@JoinColumn(name="birthDate")
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date birthDate;
	private String gender;
	
	private String address;
	
	  @AssertTrue(message = "Please accept the terms and conditions")
	private boolean checkbox;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public Long getContact() {
		return contact;
	}

	public void setContact(Long contact) {
		this.contact = contact;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", profileImage=" + profileImage + ", contact=" + contact + ", birthDate=" + birthDate + ", gender="
				+ gender + ", address=" + address + ", checkbox=" + checkbox + "]";
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int id,
			@NotBlank(message = "User name can not be null !!") @Size(min = 3, max = 10, message = "User name must be between 3-12 characters !!") String name,
			@NotBlank(message = "Email is neccessary") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Invalid email format. Must be in the 'example@gmail.com' format.") String email,
			@NotBlank(message = "Password is menatory") String password, String role, String profileImage, Long contact,
			Date birthDate, String gender, String address, @AssertTrue boolean checkbox) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.profileImage = profileImage;
		this.contact = contact;
		this.birthDate = birthDate;
		this.gender = gender;
		this.address = address;
		this.checkbox = checkbox;
	}

	
	

	
	
}
