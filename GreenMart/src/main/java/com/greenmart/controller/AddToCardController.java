package com.greenmart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenmart.model.CartItem;
import com.greenmart.service.CartService;

@Controller
@RequestMapping("user")
public class AddToCardController {
	@Autowired
    private CartService cartService;
	

	
	@RequestMapping("/addToCart")
    public String addToCart(@RequestParam("productId") int productId, 
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                            HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null) {
            return "redirect:/user/login";
        }

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }
        int currentStock = cartService.getProductStock(productId);
        cartService.updateProductStock(productId, currentStock-quantity);

        boolean isAdded = cartService.addToCart(userId, productId, quantity);
        if (isAdded) {
            int cartCount = cartService.getCartCount(userId);
            session.setAttribute("cartCount", cartCount);
        }

        return "redirect:/";
    }

//

	@RequestMapping("/addToCartFromProductDetails")
    public String addToCartFromProductDetails(@RequestParam("productId") int productId, 
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                            HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null) {
            return "redirect:/user/login";
        }

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }
        int currentStock = cartService.getProductStock(productId);
        cartService.updateProductStock(productId, currentStock-quantity);

        boolean isAdded = cartService.addToCart(userId, productId, quantity);
        if (isAdded) {
            int cartCount = cartService.getCartCount(userId);
            session.setAttribute("cartCount", cartCount);
        }

        return "redirect:/user/productDetail?productId="+productId;
    }
	@RequestMapping("/viewCart")
	public String viewCart(HttpSession session, Model model) {
	    String username = (String) session.getAttribute("userName");
	    Integer userId = (Integer) session.getAttribute("userId");
	    System.out.println("Username: " + username);
	    System.out.println("User ID: " + userId);

	    if (username == null || userId == null) {
	        System.out.println("User is not logged in, redirecting to login.");
	        return "redirect:/user/login";
	    }
	    double totalPrice = 0.0;

	    try {
	        List<CartItem> cartItems = cartService.fetchCartItems(userId);
	        System.out.println("Cart Items: " + cartItems);

	        for (CartItem item : cartItems) {
	            int stock = cartService.getProductStock(item.getProductId());
	            item.setStock(stock);  
	            totalPrice += item.getTotalPrice();
	        }
	        
	        model.addAttribute("cartItems", cartItems);
	        model.addAttribute("totalAmount", totalPrice);
	        return "viewCart";
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        System.out.println("Error occurred while fetching cart items: " + ex.getMessage());
	        model.addAttribute("errorMessage", "Error fetching cart details.");
	        return "errorPage";
	    }
	}


	@RequestMapping("/updateCart")
	public String updateCart(@RequestParam("action") String action, HttpSession session) {
	    String username = (String) session.getAttribute("userName");
	    Integer userId = (Integer) session.getAttribute("userId");

	    System.out.print("Username: " + username);
	    System.out.print("User ID: " + userId);

	    if (username == null || userId == null) {
	        System.out.print("User is not logged in, redirecting to login.");
	        return "redirect:/user/login";
	    }

	    try {
	        String[] actionParts = action.split("-");
	        String actionType = actionParts[0];
	        int productId = Integer.parseInt(actionParts[1]);

	        System.out.print("Action Type: " + actionType);
	        System.out.print("Product ID: " + productId);

	        int currentStock = cartService.getProductStock(productId);
	        CartItem cartItem = cartService.getCartItem(userId, productId);

	        int change = actionType.equals("increase") ? 1 : -1;

	        System.out.print("Current Stock: " + currentStock);
	        System.out.print("Cart Item Quantity: " + cartItem.getQuantity());

	        if (change == 1 && currentStock <= 0) {
	            System.out.print("No stock to increase.");
	            return "redirect:/user/viewCart";
	        }

	        if (change == -1 && cartItem.getQuantity() <= 1) {
	            System.out.print("Quantity cannot go below 1.");
	            return "redirect:/user/viewCart";
	        }

	        cartService.updateCartQuantity(userId, productId, actionType);
	        cartService.updateProductStock(productId, currentStock - change);

	        System.out.print("Cart and stock updated.");
	        

	        return "redirect:/user/viewCart";
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.print("Error occurred: " + e.getMessage());
	        return "errorPage";
	    }
	}



	@RequestMapping("/removeItem")
	public String removeItem(@RequestParam("productId") int productId,@RequestParam("quantity") int quantity, HttpSession session) {
	    try {
	        int userId = (int) session.getAttribute("userId");
	        System.out.print("Quantity"+quantity+"\n");
	        int currentStock = cartService.getProductStock(productId);
	         
		        System.out.print("currect stock "+currentStock+"\t after delete product stock will be :"+currentStock+quantity);
		        cartService.updateProductStock(productId, currentStock+quantity);

	        cartService.removeItemFromCart(productId, userId);
	        //currentStock = cartService.getProductStock(productId);

	        //System.out.print("updated stock"+currentStock);

	        int cartCount = cartService.getCartCount(userId);
	        session.setAttribute("cartCount", cartCount);
	        return "redirect:/user/viewCart";  
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "errorPage";  
	    }
	}


}
