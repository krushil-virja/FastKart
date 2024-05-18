package com.FastKart.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@Controller
public class categoryController {

	@Autowired
	private categoryDao cdao;

	@Autowired
	private CategoryRepository categoryRepository;

	@PostMapping("/insertCategory")
	public String addCategory(@ModelAttribute Category category, @RequestParam("cat_image") MultipartFile file) {

		try {

			if (file.isEmpty()) {

				System.out.println("your file is empty");
			} else {
				category.setCimage(file.getOriginalFilename());
				File saveFile = new ClassPathResource("static/assets1/images").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				System.out.println(path);
				System.out.println("file is uploaded");
			}

			Category c = cdao.addCategory(category);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:addCategory";
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
					
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING );
					
					System.out.println(path);
					System.out.println("file is uploaded");
				}
				
				categoryRepository.save(category);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		else {
			System.out.print("category is not found");
		}
		return "redirect:/category";

	}

}
