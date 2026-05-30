package com.greenmart.model;

import lombok.Data;

@Data
public class CartItem {
	private int productId;
    private int quantity;
    private String productName;
    private double singlePrice;
    private String image;  
    private double totalPrice;
    private int stock;
    private int cartId; 
	/*
	 * public double getTotalAmount() {
	 * 
	 * return totalPrice; }
	 */
    }
