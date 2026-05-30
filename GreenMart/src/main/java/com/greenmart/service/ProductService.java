package com.greenmart.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.greenmart.model.Category;
import com.greenmart.model.Product;
import com.greenmart.model.ProductDetails;
import com.greenmart.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ServletContext servletContext;
    
    public boolean deleteProduct(int productId) {
        return productRepository.deleteProduct(productId);
    }

    private static final List<String> AllowedFileExtensions = Arrays.asList("jpg", "jpeg", "png");

    public Product getProductById(int id) {
        return productRepository.findById(id);
    }

    public ProductDetails getProductDetailsById(int id) {
        return productRepository.findProductDetailsById(id);
    }
	/*
	 * public void updateProduct(Product product, MultipartFile imageFile)
	 * throws IOException { if (imageFile != null && !imageFile.isEmpty()) {
	 * validateImageFile(imageFile); if (product.getImage() != null &&
	 * !product.getImage().isEmpty()) { String oldImagePath =
	 * servletContext.getRealPath("/") +
	 * product.getImage().replace("/resources/uploaded_images/", ""); File
	 * oldImage = new File(oldImagePath); if (oldImage.exists()) {
	 * oldImage.delete(); } }
	 * 
	 * String newImagePath = saveImage(product, imageFile);
	 * product.setImage(newImagePath); }
	 * 
	 * productRepository.updateProduct(product); }
	 * 
	 * public String saveImage(Product product, MultipartFile imageFile) throws
	 * IOException { validateImageFile(imageFile);
	 * 
	 * 
	 * String fileName = imageFile.getOriginalFilename();
	 * 
	 * String realPath =
	 * servletContext.getRealPath("/WEB-INF/resources/uploaded_images/"); File
	 * uploadDir = new File(realPath); if (!uploadDir.exists()) {
	 * uploadDir.mkdirs(); }
	 * 
	 * File destFile = new File(uploadDir, fileName);
	 * imageFile.transferTo(destFile);
	 * 
	 * return "/resources/uploaded_images/" + fileName; }
	 */
    public String saveImage(Product product, MultipartFile imageFile, Model model) throws IOException {
       

        String originalFileName = imageFile.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);

        String productName = product.getProductName();
        String fileName;

        if (productName != null && !productName.trim().isEmpty()) {
            String sanitizedProductName = productName.replaceAll("[^a-zA-Z0-9]", "_");
            fileName = sanitizedProductName + "." + fileExtension;
        } else {
            fileName = originalFileName;
        }

        String realPath = servletContext.getRealPath("/WEB-INF/resources/uploaded_images/");
        File uploadDir = new File(realPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        File destFile = new File(uploadDir, fileName);
        imageFile.transferTo(destFile);

        return "/resources/uploaded_images/" + fileName;
    }



    private void deletePreviousImage(Product product) {
        if (product.getImage() != null && !product.getImage().isEmpty()) {
            String uploadDir = servletContext.getRealPath("/WEB-INF/resources/uploaded_images");
            String fileName = product.getImage().substring(product.getImage().lastIndexOf("/") + 1);
            String oldImagePath = uploadDir + File.separator + fileName;

            System.out.println("Attempting to delete file: " + oldImagePath);
            
            File oldImage = new File(oldImagePath);
            if (oldImage.exists()) {
                if (oldImage.delete()) {
                    System.out.println("Deleted previous image: " + oldImagePath);
                } else {
                    System.out.println("Failed to delete previous image: " + oldImagePath);
                }
            } else {
                System.out.println("Previous image not found: " + oldImagePath);
            }
        } else {
            System.out.println("No previous image to delete for product: " + product.getProductId());
        }
    }

    public void updateProduct(Product product, MultipartFile imageFile, Model model) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            if (!validateImageFile(imageFile, model)) {
                System.out.println("Image validation failed.");
                return;
            }

            System.out.println("Deleting previous image...");
            deletePreviousImage(product);

            System.out.println("Saving new image...");
            String newImagePath = saveImage(product, imageFile, model);
            product.setImage(newImagePath);
        }

        System.out.println("Updating product: " + product);
        productRepository.updateProduct(product);
    }



    public void saveProductWithImage(Product product, MultipartFile imageFile, Model model) throws IOException {
        if (!validateImageFile(imageFile, model)) {
            return;
        }

        String originalFileName = imageFile.getOriginalFilename();
        String fileExtension = originalFileName != null ? getFileExtension(originalFileName) : "";

        String sanitizedProductName = product.getProductName().replaceAll("[^a-zA-Z0-9]", "_");
        String newFileName = sanitizedProductName + (!fileExtension.isEmpty() ? "." + fileExtension : "");

        String realPath = servletContext.getRealPath("/WEB-INF/resources/uploaded_images/");
        File uploadDir = new File(realPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        File destFile = new File(uploadDir, newFileName);
        imageFile.transferTo(destFile);

        product.setImage("/resources/uploaded_images/" + newFileName);
        productRepository.saveProduct(product);
    }

    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot == -1) return "";
        return fileName.substring(lastIndexOfDot + 1);
    }

    public List<Category> getActiveCategories() {
        return productRepository.getActiveCategories();
    }
    private boolean validateImageFile(MultipartFile imageFile, Model model) {
        if (imageFile == null || imageFile.isEmpty()) {
            model.addAttribute("errorMessage", "Please upload an image file.");
            return false;
        }

        String fileExtension = getFileExtension(imageFile.getOriginalFilename());
        if (fileExtension == null || !AllowedFileExtensions.contains(fileExtension.toLowerCase())) {
            model.addAttribute("errorMessage", "Invalid file type. Only JPG, JPEG, and PNG are allowed.");
            return false;
        }

        return true;
    }


    public boolean toggleStatus(int productId) {
        Product product = productRepository.findById(productId);
        if (product == null) {
            return false; 
        }
    	System.out.println("status tiggling in service");

        product.setStatus(product.getStatus() == 1 ? 0 : 1); 
        return productRepository.toggleStatus(product); 
    }


    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }


}
