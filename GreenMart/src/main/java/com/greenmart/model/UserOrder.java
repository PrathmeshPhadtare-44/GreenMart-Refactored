package com.greenmart.model;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserOrder {
	 private int orderId;
	    private int userId;
	    private String customerName;
	    private BigDecimal totalAmount;
	    private String orderStatus;
	    private Timestamp orderDate;
}
