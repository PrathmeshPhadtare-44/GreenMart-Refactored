package com.greenmart.model;

import lombok.Data;

@Data
public class OrderedProductDTO {
    private int productId;
    private String productName;
    private int quantity;
    private String image;
}
