package com.greenmart.model;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class OrderHistoryDTO {
    private int orderId;
    private String customerName;
    private String phoneNumber;
    private double totalAmount;
    private String orderStatus;
    private String address;
    private Timestamp orderDate;
    private List<OrderItemDTO> items = new ArrayList<>();

    public void addItem(OrderItemDTO item) {
        this.items.add(item);
    }
}
