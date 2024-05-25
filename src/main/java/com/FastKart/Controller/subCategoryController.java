package com.FastKart.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

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
import com.FastKart.Dao.subCategoryDao;
import com.FastKart.Repository.CategoryRepository;
import com.FastKart.Repository.SubCategoryRepository;
import com.FastKart.entities.Category;
import com.FastKart.entities.subCategory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Controller
public class subCategoryController {

	@Autowired
	private subCategoryDao scDao;

	@Autowired
	private SubCategoryRepository subCategoryRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private categoryDao cdao;

	@PostMapping("/admin/insertAddSubCategory")
	public String addSubCategory(@Valid @ModelAttribute subCategory sc, BindingResult result,
			@RequestParam("cat_image") MultipartFile file, @RequestParam("cid") int cid, Model m) {

//  first check that result set object have error if than it show us on page (result set handle all field error including image which we define in model class
		if (result.hasErrors() && cid == 0) {
			System.out.println(result);
			List<Category> showAllCategory = cdao.showAllCategory();
			m.addAttribute("category", showAllCategory);
			return "admin/admin-addSubCategory";
		}

		// second check that image is empty or not
		if (file.isEmpty()) {
			System.out.println("Your File Is Empty");
			// result.rejectValue("sub_cat_image", "error", "Please select an image file");
			List<Category> showAllCategory = cdao.showAllCategory();
			m.addAttribute("category", showAllCategory);
			return "admin/admin-addSubCategory";

		}

		//  this block of code check that category is already exist or not
		boolean existBysub_cat_name = subCategoryRepository.existsBySubCatName(sc.getSub_cat_name());
		System.out.println(existBysub_cat_name);
		if (existBysub_cat_name) {
			result.rejectValue("sub_cat_name", "error", "SubCategory  already exists");
			List<Category> showAllCategory = cdao.showAllCategory();
			m.addAttribute("category", showAllCategory);
			return "admin/admin-addSubCategory";
		}

		try {

			// i have to implement this cause BindingResult does not conduct multipart
			// validation.

			sc.setSub_cat_image(file.getOriginalFilename());
			sc.setCategory(cdao.getCategory(cid));

			File saveFile = new ClassPathResource("static/assets1/images").getFile();

			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			System.out.println("file is uploaded");

			subCategory addSubCategory = scDao.addSubCategory(sc);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return "redirect:/admin/subCategory";
	}

//============================================================================ DELETE SUBCATEGORY HANDLER ==============================================

	@GetMapping("/admin/deleteSubCategory/{id}")
	public String deketeSubCategory(@PathVariable("id") Integer id, Model m) {

		scDao.deleteSubCategory(id);
		return "redirect:/admin/subCategory";

	}
//============================================================= get subcategory by id =============================================================

	@GetMapping("/admin/updateSubCategory/{id}")
	public String getSubCategory(@PathVariable("id") int id, Model m) {

		subCategory sc = subCategoryRepository.findById(id).get();
		m.addAttribute("subCategory", sc);

		List<Category> showAllCategory = cdao.showAllCategory();
		m.addAttribute("category", showAllCategory);

		return "admin/admin-updateSubCategory";

	}

//============================================================= Update subCategory Handler ===========================================================

	@PostMapping("/admin/updateSubCategory")
	public String updateSubCategory(@RequestParam("id") int id, @RequestParam("cid") int cid,
			@RequestParam("sub_cat_name") String sub_cat_name, @RequestParam("cat_image") MultipartFile file) {

		subCategory sc = subCategoryRepository.findById(id).get();
		Category c = cdao.getCategory(cid);
		try {

			
			sc.setSub_cat_name(sub_cat_name);
				sc.setCategory(c);
				
				if(file!=null && !file.isEmpty()) {
					
			
				
				sc.setSub_cat_image(file.getOriginalFilename());

				File saveFile = new ClassPathResource("static/assets1/images").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				System.out.println("file is uploaded");
				System.out.println(path);
				}

			
		} catch (Exception e) {
			e.printStackTrace();
		}

		subCategoryRepository.save(sc);
		return "redirect:/admin/subCategory";

	}
}
