package com.FastKart.Controller;


import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.FastKart.Dao.invoiceDao;


@Controller
public class generate_invoice {

	@Autowired
	private invoiceDao iDao;
	
	@GetMapping("/invoice/{id}")
	public ResponseEntity<InputStreamResource>  generateInvoice(@PathVariable("id") int id){
		
		ByteArrayInputStream pdf = iDao.generateInvoice(id);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content_Disposition","inline; filename=invoice.pdf");
		
		return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(pdf));
		
	}
	
	
	@GetMapping("/htmlToPdf")
	public ResponseEntity<InputStreamResource>  htmlToPdf(){
		
		ByteArrayInputStream pdf = iDao.htmlToPdf();
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content_Disposition","inline; filename=invoice.pdf");
		
		return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(pdf));
		
	}
	
}
