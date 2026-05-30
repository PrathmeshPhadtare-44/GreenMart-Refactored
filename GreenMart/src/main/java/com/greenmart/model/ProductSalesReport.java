package com.greenmart.model;

import lombok.Data;

@Data
public class ProductSalesReport {
	private String image;
    private String productName;
    private String category;
    private int totalQuantitySold;
    private double totalRevenue;
    private double avgPricePerUnit;
    private int totalUniqueCustomers;
    private double averageRating;
    private int currentStock;
}
