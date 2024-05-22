package com.FastKart.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import com.FastKart.Repository.CategoryRepository;
import com.FastKart.entities.Category;
import com.itextpdf.text.log.SysoCounter;

import jakarta.validation.Valid;

@Controller
public class categoryController {

	@Autowired
	private categoryDao cdao;

	@Autowired
	private CategoryRepository categoryRepository;

	@PostMapping("/insertCategory")
	public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult result,
			@RequestParam("cat_image") MultipartFile file, Model model) {

		if (result.hasErrors()) {
	        return "admin/admin-addCategory";
	    }

	    boolean existsByCname = categoryRepository.existsByCname(category.getCname());
	    if (existsByCname) {
	        result.rejectValue("cname", "error.category", "Category already exists");
	        return "admin/admin-addCategory";
	    }

	    try {
	        if (file.isEmpty()) {
	            System.out.println("Your File Is Empty");
	            result.rejectValue("cimage", "error.category", "Please select an image file");
	            return "admin/admin-addCategory";
	        } else {
	            String filename = file.getOriginalFilename();
	            category.setCimage(filename);
	            File saveFile = new ClassPathResource("static/assets1/images").getFile();

	            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

	            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

	            System.out.println("File is uploaded");
	        }

	        cdao.addCategory(category);
			// Save category to the database (cdao.addCategory(category))
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:category";
	}

//===================================================== DELETE CATEGORY HANDLER =========================================================================
	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable("id") Integer id, Model m) {

		cdao.deleteCategory(id);
		return "redirect:/category";
	}

//======================================================== get Category detail by Id =============================================================

	@GetMapping("/updateCategory/{id}")
	public String findCategoryById(@PathVariable("id") int id, Model m) {

		Category category = cdao.getCategory(id);
		m.addAttribute("category", category);

		return "admin/admin-updateCategory";
	}

//========================================================== Update Category ================================================================
	@PostMapping("/updateCategory")
	public String updateCategory(@RequestParam("id") int id, @RequestParam("cname") String cname,
			@RequestParam("cat_image") MultipartFile file) {

		Category category = cdao.getCategory(id);

		if (category != null) {

			try {

				if (file.isEmpty()) {

				} else {

					category.setCname(cname);
					category.setCimage(file.getOriginalFilename());

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
		return "redirect:/category";

	}

}
