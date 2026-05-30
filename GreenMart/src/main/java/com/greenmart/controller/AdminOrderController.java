package com.greenmart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greenmart.model.OrderDTO;
import com.greenmart.service.AdminOrderService;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {

    @Autowired
    private AdminOrderService adminOrderService;

    @GetMapping("/orders")
    public String viewOrders(Model model) {
        List<OrderDTO> orders = adminOrderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "adminViewOrders";
    }

    @PostMapping("/updateOrderStatus")
    public String updateOrderStatus(@RequestParam("orderId") int orderId, 
                                    @RequestParam("status") String status, 
                                    HttpServletRequest request) {
        adminOrderService.updateOrderStatus(orderId, status);
        request.getSession().setAttribute("success", "Order status updated successfully.");
        return "redirect:/admin/orders";
    }
    @PostMapping("/orders/delete")
    public String deleteOrder(@RequestParam("orderId") int orderId, RedirectAttributes redirectAttributes) {
       
    	
    	boolean isDeleted = adminOrderService.deleteOrder(orderId);
        

        if (isDeleted) {
            redirectAttributes.addFlashAttribute("success", "✅ Order deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "❌ Order deletion failed. Please try again.");
        }

        return "redirect:/admin/orders";
    }

}    