package com.FastKart.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.FastKart.Dao.categoryDao;
import com.FastKart.Dao.userDao;
import com.FastKart.Repository.CategoryRepository;
import com.FastKart.entities.Category;
import com.FastKart.entities.User;
import com.itextpdf.text.log.SysoCounter;

import jakarta.validation.Valid;

@Controller
public class categoryController {

	@Autowired
	private categoryDao cdao;
	
	@Autowired
	private userDao udao;

	@Autowired
	private CategoryRepository categoryRepository;

	@PostMapping("/admin/insertCategory")
	public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult result,
			@RequestParam("cat_image") MultipartFile file, Model model) {


	    if (file.isEmpty()) {
	        result.rejectValue("cimage", "error.category", "Please upload a category image");
	        
	        
	    }

	    if (result.hasErrors()) {
	        return "admin/admin-addCategory";
	    }

	    boolean existsByCname = categoryRepository.existsByCname(category.getCname());
	    System.out.println(existsByCname);
	    if (existsByCname) {
	        result.rejectValue("cname", "error.category", "Category already exists");
	        return "admin/admin-addCategory";
	    }

	    try { 
	      
	            
	            String filename = file.getOriginalFilename();
	            category.setCimage(filename);
	            File saveFile = new ClassPathResource("static/assets1/images").getFile();

	            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

	            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

	            System.out.println("File is uploaded");
	       

	        cdao.addCategory(category);
			// Save category to the database (cdao.addCategory(category))
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/admin/category";
	}

//===================================================== DELETE CATEGORY HANDLER =========================================================================
	@GetMapping("/admin/deleteCategory/{id}")
	public String deleteCategory(@PathVariable("id") Integer id, Model m) {

		cdao.deleteCategory(id);
		return "redirect:/admin/category";
	}

//======================================================== get Category detail by Id =============================================================

	@GetMapping("/admin/updateCategory/{id}")
	public String findCategoryById(@PathVariable("id") int id, Model m, Principal principal) {

		Category category = cdao.getCategory(id);
		m.addAttribute("category", category);
		
		  User loggedInUser = udao.getLoggedInUser(principal);
		    if (loggedInUser != null) {
		        System.out.println("Logged In User: " + loggedInUser.getName());
		        m.addAttribute("user", loggedInUser);
		    } else {
		        System.out.println("User is null");
		    }

		return "admin/admin-updateCategory";
	}

//========================================================== Update Category ================================================================
	@PostMapping("/admin/updateCategory")
	public String updateCategory(@RequestParam("id") int id, @RequestParam("cname") String cname,
			@RequestParam(value="cat_image", required = false ) MultipartFile file) {

		Category category = cdao.getCategory(id);

		if (category != null) {

			try {

					category.setCname(cname);
					
					//  Check if a new image file is provided
					if(file!=null && !file.isEmpty()) {
						
						// Set the new image filename
					category.setCimage(file.getOriginalFilename());

					 // Save the file to the server
					File saveFile = new ClassPathResource("static/assets1/images").getFile();

					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

					System.out.println(path);
					System.out.println("file is uploaded");
					}

				categoryRepository.save(category);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out.print("category is not found");
		}
		
		return "redirect:/admin/category";
	}

}
