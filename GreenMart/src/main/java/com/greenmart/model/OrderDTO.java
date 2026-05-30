package com.greenmart.model;

import lombok.Data;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO {
    private int orderId;
    private String username;
    private String address;
    private Timestamp orderDate;
    private double totalAmount;
    private String status;
    private List<OrderedProductDTO> items = new ArrayList<>();

    public void addItem(OrderedProductDTO item) {
        this.items.add(item);
    }
}
