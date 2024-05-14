package com.FastKart.Dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.WriteAbortedException;

import org.bouncycastle.jcajce.provider.symmetric.AES.Wrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.swing.NaiveUserAgent;

import com.FastKart.Repository.OrderRepository;
import com.FastKart.entities.Order;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class invoiceDao {

	private Logger logger = LoggerFactory.getLogger(invoiceDao.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private orderDao oDao;

	@Autowired
	private  SpringTemplateEngine templateEngine;

	
	public ByteArrayInputStream generateInvoice(int id) {
	    logger.info("Invoice generation process started...");

	    Order order = orderRepository.findById(id).orElse(null);

	    if (order == null) {
	       System.out.println("order is not found");
	        return null;
	    }

	    // Prepare data for invoice template
	    Context context = new Context();
	    context.setVariable("order", order);

	    // Render HTML template using Thymeleaf
	  //  String htmlContent = templateEngine.process("invoice_template", context)
	    String htmlContent = templateEngine.process("invoice", context);
	    logger.info("Generated HTML Content: {}", htmlContent);
	    
	 
	    /* logger.info(htmlContent); */
	    
	    // Convert HTML to PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
try {
			
			
			// now we generate a document object
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, outputStream); // getInstance method of pdfWriter class wrap all the data of document which we store in byteArrayOutputStream 
			
			// now we generate a document 
			document.open();
			
			// Use Flying Saucer or similar library to convert HTML to PDF
            // Example with Flying Saucer:
			
			ITextRenderer renderer = new ITextRenderer();
			/* renderer.setDocumentFromString(htmlContent); */
			renderer.setDocumentFromString(htmlContent, "");
				            
			renderer.layout();
			renderer.createPDF(outputStream);
			
			document.close();
			writer.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	    // Convert HTML content to ByteArrayInputStream
		/*
		 * return new
		 * ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8));
		 */
        return new ByteArrayInputStream(outputStream.toByteArray());
	
	}
	
	
	
	
	
		
		public ByteArrayInputStream htmlToPdf() {
			
			
			logger.info("process start to convert Html to pdf");
			
			// first create a ByteArrayOutputStream  to write a data 
			
			Context context = new Context(); 
			
			String htmlPage = templateEngine.process("invoice_template2", context);
			
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			
			try {
				
				
				// now we generate a document object
				Document document = new Document();
				PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream); // getInstance method of pdfWriter class wrap all the data of document which we store in byteArrayOutputStream 
				
				// now we generate a document 
				document.open();
				
				// Use Flying Saucer or similar library to convert HTML to PDF
	            // Example with Flying Saucer:
				
				ITextRenderer renderer = new ITextRenderer();
				renderer.setDocumentFromString(htmlPage);
				renderer.layout();
				renderer.createPDF(byteArrayOutputStream);
				
				document.close();
				writer.close();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return new  ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			
		}
	

}
