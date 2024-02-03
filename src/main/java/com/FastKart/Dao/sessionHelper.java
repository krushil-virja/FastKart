package com.FastKart.Dao;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Service
public class sessionHelper {

	public void removeSession() {

		try {

			HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
			
			session.removeAttribute("message");
			session.removeAttribute("error");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
