package com.greenmart.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenmart.model.CartItem;
import com.greenmart.model.CityModel;
import com.greenmart.model.User;
import com.greenmart.repository.UserAccountRepository;
import com.greenmart.service.CartService;

@Controller
@RequestMapping("user")

public class CheckoutController {
	@Autowired
	UserAccountRepository userAccountRepository;
	@Autowired
	CartService cartService;

		@RequestMapping("/checkout")
	public String checkoutPage(HttpSession session, Model model) {
	    String username = (String) session.getAttribute("userName");
	    Integer userId = (Integer) session.getAttribute("userId");

	    if (username == null || userId == null) {
	        session.setAttribute("loginAlert", "Please login first");
	        return "redirect:/user/login";
	    }

	    User user = userAccountRepository.getUserDataById(userId);

	    List<CartItem> cartItems = cartService.fetchCartItems(userId);
	    double totalPrice = 0.0;
	    for (CartItem item : cartItems) {
	        int stock = cartService.getProductStock(item.getProductId());
	        item.setStock(stock);
	        totalPrice += item.getTotalPrice();
	    }

	    model.addAttribute("user", user);
	    model.addAttribute("cartItems", cartItems);
	    model.addAttribute("totalAmount", totalPrice);


	    return "checkout";  
	}
		
	}
