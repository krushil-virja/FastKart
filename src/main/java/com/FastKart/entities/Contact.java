package com.FastKart.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="contact")
public class Contact {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
    
    @NotEmpty(message="Please enter your firstName")
	private String firstName;
    
    @NotEmpty(message="Please enter your lastName")
	private String lastName;
    
    @NotEmpty(message="Please enter your Email")
    @Email(message="Email address is not valid")
	private String email;
	
    @NotNull(message = "Please enter your phone number")
  //  @Digits(integer = 10, fraction = 0, message = "Phone number must have exactly 10 digits")
	private Long number;
	
    @NotEmpty(message="Please enter your Message")
	private String message;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", number=" + number + ", message=" + message + "]";
	}

	public Contact(int id, @NotEmpty(message = "Please enter your firstName") String firstName,
			@NotEmpty(message = "Please enter your lastName") String lastName,
			@NotEmpty(message = "Please enter your Email") @Email(message = "Email address is not valid") String email,
			@NotNull(message = "Please enter your phone number") Long number,
			@NotEmpty(message = "Please enter your Message") String message) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.number = number;
		this.message = message;
	}

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
