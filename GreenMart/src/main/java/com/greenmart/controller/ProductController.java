package com.greenmart.controller;

import com.greenmart.model.Product;
import com.greenmart.model.Category;
import com.greenmart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    private ProductService productService;

   
    @GetMapping("/addProduct")
    public String addProductPage(Model model) {
        System.out.println("Navigating to the Add Product page...");
        List<Category> categories = productService.getActiveCategories();
        model.addAttribute("categories", categories);

        return "addProduct";
    }

   
    @PostMapping("/saveNewProduct")
    public String saveProduct(@RequestParam("productName") String productName,
                              @RequestParam("description") String description,
                              @RequestParam("price") double price,
                              @RequestParam("stock") int stock,
                              @RequestParam("status") int status,
                              @RequestParam("categoryId") int categoryId,
                              @RequestParam("image") MultipartFile imageFile,
                              Model model,
                              RedirectAttributes redirectAttributes) {  // Added RedirectAttributes

        System.out.println("----- Starting saveProduct Method -----");
        System.out.println("Received Product Details:");
        System.out.println("Product Name: " + productName);
        System.out.println("Category ID: " + categoryId);
        System.out.println("Description: " + description);
        System.out.println("Price: " + price);
        System.out.println("Stock: " + stock);
        System.out.println("Status: " + status);

        if (imageFile.isEmpty()) {
            System.out.println("Error: No file uploaded or file is empty.");
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add product. No image uploaded.");
            return "redirect:/admin/products"; 
        }

        System.out.println("Uploaded Image Name: " + imageFile.getOriginalFilename());

        try {
            Product product = new Product();
            product.setProductName(productName);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            product.setStatus(status);
            product.setCategoryId(categoryId);

            productService.saveProductWithImage(product, imageFile, model);
            System.out.println("Product and image saved successfully.");

            redirectAttributes.addFlashAttribute("successMessage", "New product added successfully!");
            return "redirect:/admin/products"; 
        } catch (Exception e) {
            System.out.println("Error occurred while saving product:");
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while adding the product.");
            return "redirect:/admin/products";  
        } finally {
            System.out.println("----- End of saveProduct Method -----");
        }
    }

    
    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("productId") int id) {
        if (productService.deleteProduct(id)) {
        	return "redirect:/admin/products";        } else {
            return "errorPage";
        }
    }

    
    @RequestMapping("/products")
    public String product(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product";
    }

    /**
     * Edits product details.
     *
     * @param productId The ID of the product to edit.
     * @param model     The model to pass data to the view.
     * @return The view for editing product details.
     */
    @GetMapping("/editProductAndSave")
    public String editProduct(@RequestParam("productId") int productId, Model model) {
        System.out.println("Editing product with ID: " + productId);
        Product product = productService.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found with ID: " + productId);
            model.addAttribute("error", "Product not found.");
            return "errorPage";
        }

        List<Category> categories = productService.getActiveCategories();
        System.out.println("Fetched " + categories.size() + " categories.");
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        return "productEditPage";
    }

   
    @PostMapping("/updateProductDetails")
    public String updateProduct(@RequestParam("productId") int productId,
                                @RequestParam("productName") String productName,
                                @RequestParam("description") String description,
                                @RequestParam("price") double price,
                                @RequestParam("stock") int stock,
                                @RequestParam("status") int status,
                                @RequestParam("categoryId") int categoryId,
                                @RequestParam("image") MultipartFile imageFile,
                                Model model,RedirectAttributes redirectAttributes) {
    	
        System.out.println("Updating product with ID: " + productId+"Catrgory id "+categoryId);
        try {
            Product product = productService.getProductById(productId);
            if (product != null) {
                System.out.println("Product found, updating details...");
                product.setProductName(productName);
                product.setDescription(description);
                product.setPrice(price);
                product.setStock(stock);
                product.setStatus(status);
                product.setCategoryId(categoryId);
                System.out.println("Updating product with ID: " + product.getProductId()+"Catrgory id "+product.getCategoryId());

                productService.updateProduct(product, imageFile,model);
                model.addAttribute("message", "Product updated successfully.");
                System.out.println("Product updated successfully!");
            } else {
                model.addAttribute("error", "Product not found.");
                System.out.println("Product not found with ID: " + productId);
            }
            redirectAttributes.addFlashAttribute("successMessage", " Product Updated successfully!");

            return "redirect:/admin/products";
        } catch (Exception e) {
            model.addAttribute("error", "Error updating product: " + e.getMessage());
            System.out.println("Error updating product: " + e.getMessage());
            return "productEditPage";
        }
    }

    
    @RequestMapping("/enabledisableProduct")
    public String toggleProductStatus(@RequestParam("productId") int productId) {
        System.out.println("Id to toggle is " + productId);
        boolean isUpdated = productService.toggleStatus(productId);
        if (isUpdated) {
            return "redirect:/admin/products";
        } else {
            return "errorPage";
        }
    }
    
    
}
