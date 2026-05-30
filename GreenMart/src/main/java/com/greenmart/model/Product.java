package com.greenmart.model;

import lombok.Data;

@Data
public class Product {
    private int productId;
    private String productName;
    private String description;
    private double price;
    private int stock;
    private int status;
    private int categoryId;
    private String image;
    private String categoryName;

}
