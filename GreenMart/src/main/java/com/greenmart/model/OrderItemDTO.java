package com.greenmart.model;
import lombok.Data;

@Data
public class OrderItemDTO {
    private int productId;
    private String productName;
    private int quantity;
    private double price;
    private double totalPrice;
    private String image;
    private int productStatus;
    private int rating;  
    private String comment;
}
