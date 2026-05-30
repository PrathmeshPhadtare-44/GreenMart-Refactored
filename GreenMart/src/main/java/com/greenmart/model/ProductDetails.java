package com.greenmart.model;

import java.util.List;

import lombok.Data;

@Data
public class ProductDetails {
	private int productId;
    private String productName;
    private String description;
    private double price;
    private int stock;
    private int status;
    private int categoryId;
    private String image;
    private String categoryName;
    private List<String> userNames;
    private List<Integer> ratings;
    private List<String> comments;
    private double averageRating;
}
