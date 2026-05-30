package com.greenmart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenmart.model.CartItem;
import com.greenmart.repository.CartRepository;

@Service
public class CartService {
	 @Autowired
	    private CartRepository cartRepository;
	
		/*
		 * public boolean addToCart(int userId, int productId) { return
		 * cartRepository.addToCart(userId, productId); }
		 */
	 public boolean addToCart(int userId, int productId, int quantity) {
	        if (cartRepository.checkIfExists(userId, productId)) {
	            return cartRepository.updateQuantity(userId, productId, quantity);
	        } else {
	            return cartRepository.addNewProduct(userId, productId, quantity);
	        }
	    }

	 public int getCartCount(int userId) {
	        return cartRepository.getCartCount(userId);
	    }
	 public List<CartItem> fetchCartItems(int userId) {
		    System.out.println("Fetching cart items for user ID: " + userId);
		    try {
		        List<CartItem> cartItems = cartRepository.getCartItemsByUserId(userId);
		        System.out.println("Fetched Cart Items: " + cartItems);
		        return cartItems;
		    } catch (Exception e) {
		        e.printStackTrace();
		        System.out.println("Error occurred while fetching cart items in service: " + e.getMessage());
		        throw new RuntimeException("Error fetching cart items: " + e.getMessage());
		    }
		}	
	 public void updateCartQuantity(int userId, int productId, String actionType) {
		    int change = actionType.equals("increase") ? 1 : -1;

		    CartItem cartItem = cartRepository.getCartItem(userId, productId);
		    if (cartItem != null) {
		        int newQuantity = cartItem.getQuantity() + change;
		        cartRepository.updateCartQuantity(userId, productId, newQuantity);
		    }
		}

		public void updateProductStock(int productId, int newStock) {
		    cartRepository.updateProductStock(productId, newStock);
		}

		public int getProductStock(int productId) {
		    return cartRepository.getProductStock(productId);
		}




	 public void removeItemFromCart(int productId, int userId) {
		    try {
		        cartRepository.removeItem(productId, userId);
		    } catch (Exception e) {
		        e.printStackTrace();
		        throw new RuntimeException("Error removing item from cart: " + e.getMessage());
		    }
		}

	public CartItem getCartItem(Integer userId, int productId) {
		return cartRepository.getCartItem(userId, productId);
	}

}
