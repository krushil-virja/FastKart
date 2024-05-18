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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.FastKart.Dao.categoryDao;
import com.FastKart.Dao.subCategoryDao;
import com.FastKart.Repository.SubCategoryRepository;
import com.FastKart.entities.Category;
import com.FastKart.entities.subCategory;

@Controller
public class subCategoryController {

@Autowired
private subCategoryDao scDao;

@Autowired
private SubCategoryRepository  subCategoryRepository;

@Autowired
private categoryDao cdao;
	
@PostMapping("/insertAddSubCategory")
public String addSubCategory(@ModelAttribute subCategory sc , @RequestParam("cat_image") MultipartFile file, @RequestParam("cid") int cid) {
	 
	
	try {
		
		if(file.isEmpty()) {
			System.out.println("Your File Is Empty");
		}
		else {
			sc.setSub_cat_image(file.getOriginalFilename());
			sc.setCategory(cdao.getCategory(cid));
			
			File saveFile = new ClassPathResource("static/assets1/images").getFile();
			
			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
			
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			System.out.println("file is uploaded");
		}
		
		subCategory addSubCategory = scDao.addSubCategory(sc);
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	
	return "redirect:addSubCategory";
}


//============================================================================ DELETE SUBCATEGORY HANDLER ==============================================

@GetMapping("/deleteSubCategory/{id}")
public String deketeSubCategory(@PathVariable("id") Integer id, Model m) {
	
	scDao.deleteSubCategory(id);
	return "redirect:/subCategory";
	
	
	
}
//============================================================= get subcategory by id =============================================================

@GetMapping("/updateSubCategory/{id}")
public String getSubCategory(@PathVariable("id") int id, Model m ) {
	
	subCategory sc = subCategoryRepository.findById(id).get();
	m.addAttribute("subCategory", sc);
	
	List<Category> showAllCategory = cdao.showAllCategory();
	m.addAttribute("category", showAllCategory);
	
	return "admin/admin-updateSubCategory";
	
}

//============================================================= Update subCategory Handler ===========================================================

@PostMapping("/updateSubCategory")
public String updateSubCategory(@RequestParam("id") int id, @RequestParam("cid") int cid,@RequestParam("sub_cat_name") String sub_cat_name, @RequestParam("cat_image") MultipartFile file) {
	
	subCategory sc = subCategoryRepository.findById(id).get();
	Category c = cdao.getCategory(cid);
	try {
		
		if(file.isEmpty()) {
			
			System.out.print("file is empty");
		}
		
		else {
			sc.setSub_cat_image(file.getOriginalFilename());
			sc.setSub_cat_name(sub_cat_name);
			sc.setCategory(c);
			
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
	return "redirect:/subCategory";
	
}
}
