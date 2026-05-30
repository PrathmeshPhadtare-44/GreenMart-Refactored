package com.greenmart.service;

import com.greenmart.model.Category;
import com.greenmart.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        System.out.println("Fetching all categories from the repository..."); // Debugging line
        return categoryRepository.getAllCategories();
    }

    public void addCategory(Category category) {
        System.out.println("Adding category to the repository: " + category.getCategoryName()); // Debugging line
        categoryRepository.addCategory(category);
    }

    public Category getCategoryById(int id) {
        System.out.println("Fetching category by id: " + id); // Debugging line
        return categoryRepository.getCategoryById(id);
    }

    public void deleteCategory(int id) {
        System.out.println("Deleting category by id: " + id); // Debugging line
        categoryRepository.deleteCategory(id);
    }

    public void toggleCategoryStatus(int id) {
        System.out.println("Toggling category status for id: " + id); // Debugging line
        Category category = categoryRepository.getCategoryById(id);
        if (category != null) {
            int newStatus = (category.getStatus() == 1) ? 0 : 1;
            category.setStatus(newStatus);
            categoryRepository.updateCategoryStatus(category);  
        }
    }
    public void updateCategory(Category category) {
        categoryRepository.editCategory(category);
    }
    public Map<Integer, Boolean> getCategoryUsageMap() {
        List<Integer> linkedCategoryIds = categoryRepository.getLinkedCategoryIds();
        Map<Integer, Boolean> usageMap = new HashMap<>();
        for (Integer id : linkedCategoryIds) {
            usageMap.put(id, true);
        }
        return usageMap;
    }


}
