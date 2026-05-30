package com.greenmart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenmart.model.Product;
import com.greenmart.model.ProductDetails;
import com.greenmart.service.CartService;
import com.greenmart.service.HomePageService;
import com.greenmart.service.ProductService;
@Controller
public class HomePageController {
	 @Autowired
	  private ProductService productService;
	@Autowired
	private HomePageService homePageService;
	@Autowired
    private CartService cartService;
	@RequestMapping("/")
	public String homePage(Model model, HttpSession session) {
	    System.out.println("homePage: Entering controller...");

	    List<Product> products = homePageService.getAllProducts();
	    model.addAttribute("products", products);
	    System.out.println("homePage: Products fetched: " + products);

	    Object userIdObj = session.getAttribute("userId");
	    System.out.println("homePage: userId from session: " + userIdObj);

	    if (userIdObj instanceof Integer) {
	        Integer userId = (Integer) userIdObj;
	        System.out.println("homePage: Parsed userId: " + userId);

	        try {
	            int cartCount = cartService.getCartCount(userId);
	            System.out.println("homePage: Cart Count for userId " + userId + ": " + cartCount);
	            session.setAttribute("cartCount", cartCount);
	        } catch (Exception e) {
	            System.out.println("homePage: Error fetching cart count: " + e.getMessage());
	            session.setAttribute("cartCount", 0); 
	        }
	    } else {
	        System.out.println("homePage: userId is not an Integer or is null, skipping cart count.");
	        session.setAttribute("cartCount", 0);
	    }

	    System.out.println("homePage: Exiting controller...");
	    return "homePage";
	}
	@RequestMapping("/user/productDetail")
	public String getProductDetails(@RequestParam("productId") int productId, Model model) {
        ProductDetails productDetails = productService.getProductDetailsById(productId);
        model.addAttribute("productDetails", productDetails);
        return "productDetail";
    }



}
