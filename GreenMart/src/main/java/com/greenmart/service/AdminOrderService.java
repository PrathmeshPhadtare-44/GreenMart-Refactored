package com.greenmart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenmart.model.OrderDTO;
import com.greenmart.repository.AdminOrderRepository;

@Service
public class AdminOrderService {

    @Autowired
    private AdminOrderRepository adminOrderRepository;

    public List<OrderDTO> getAllOrders() {
        return adminOrderRepository.findAllOrders();
    }

    public void updateOrderStatus(int orderId, String status) {
        adminOrderRepository.updateStatus(orderId, status);
    }
    public boolean deleteOrder(int orderId) {
        return adminOrderRepository.deleteOrder(orderId);
    }
}
