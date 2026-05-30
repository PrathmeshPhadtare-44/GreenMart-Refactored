package com.greenmart.controller;

import com.greenmart.model.Category;
import com.greenmart.service.CategoryService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        Map<Integer, Boolean> categoryUsageMap = categoryService.getCategoryUsageMap();

        model.addAttribute("categories", categories);
        model.addAttribute("categoryUsageMap", categoryUsageMap);
        return "adminCategory";
    }



    @PostMapping("categories/addCategory")
    public String addCategory(@RequestParam("categoryName") String categoryName,
                               @RequestParam("status") int status) {
        System.out.println("Adding category: " + categoryName + " with status: " + status);
        Category category = new Category(categoryName, status);
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

	/*
	 * @GetMapping("categories/edit") public String
	 * editCategory(@RequestParam("id") int id, Model model) {
	 * System.out.println("Editing category with id: " + id); Category category
	 * = categoryService.getCategoryById(id); model.addAttribute("category",
	 * category); return "editCategory"; }
	 */

    @GetMapping("categories/delete")
    public String deleteCategory(@RequestParam("id") int id) {
        System.out.println("Deleting category with id: " + id);
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("categories/enableDisable")
    public String enableDisableCategory(@RequestParam("id") int id) {
        System.out.println("Toggling category status for id: " + id);
        categoryService.toggleCategoryStatus(id);
        return "redirect:/admin/categories";
    }

    @PostMapping("categories/update")
    public String updateCategory(Category category) {
        System.out.println("Call the service to update the category");
        categoryService.updateCategory(category);
        System.out.println("Redirect to the category list page after successful update");
        return "redirect:/admin/categories";
    }

	/*
	 * @GetMapping("categories/addCategory") public String addCategory() {
	 * System.out.println("add Category called"); return "addCategory"; }
	 */
}
