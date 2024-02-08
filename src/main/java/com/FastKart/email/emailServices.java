package com.FastKart.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class emailServices {

	@Autowired
	private JavaMailSender javaMailSender;  //The JavaMailSender interface is a part of Spring's email support. It provides methods for sending Emails.
	
	public boolean sendMail(String to, String subject, String message) {
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();   //new instance of SimpleMailMessage, which is a simple implementation of the MailMessage interface provided by Spring.
		
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);
		
		javaMailSender.send(simpleMailMessage);
		return true;
	}
}
