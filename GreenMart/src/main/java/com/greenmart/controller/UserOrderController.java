package com.greenmart.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greenmart.model.CartItem;
import com.greenmart.model.OrderHistoryDTO;
import com.greenmart.service.CartService;
import com.greenmart.service.UserOrderService;

@Controller
@RequestMapping("user")
public class UserOrderController {
    @Autowired
    CartService cartService;
    
    @Autowired 
     UserOrderService orderService;

    @PostMapping("/placeOrder")
    public String placeOrder(
            @RequestParam String customerName,
            @RequestParam String phoneNumber,
            @RequestParam String flat,
            @RequestParam String area,
            @RequestParam String landmark,
            @RequestParam String city,
            @RequestParam String state,
            @RequestParam String pincode,
            HttpSession session,
            Model model) {

        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            model.addAttribute("error", "Session expired. Please log in again.");
            return "redirect:/user/login";
        }

        List<CartItem> cartItems = cartService.fetchCartItems(userId);
        if (cartItems == null || cartItems.isEmpty()) {
            model.addAttribute("error", "Your cart is empty.");
            return "checkout";
        }

        double totalPrice = 0.0;
        for (CartItem item : cartItems) {
            int stock = cartService.getProductStock(item.getProductId());
            if (item.getQuantity() > stock) {
                model.addAttribute("error", "Insufficient stock for " + item.getProductName());
                return "checkout";
            }
            totalPrice += item.getTotalPrice();
        }
System.out.println("Total price :"+totalPrice);
        String fullAddress = flat + ", " + area + ", " + landmark + ", " + city + ", " + state + " - " + pincode;

        boolean orderPlaced = orderService.placeOrder(userId, customerName, phoneNumber, fullAddress, totalPrice, cartItems);

        if (orderPlaced) {
            session.removeAttribute("cartItems");
            List<OrderHistoryDTO> orders=orderService.getOrderHistory(userId);
            model.addAttribute("orders", orders);
            model.addAttribute("success", "Order placed successfully.");
            return "redirect:/user/orders";     
        } else {
            model.addAttribute("error", "Failed to place order.");
            return "checkout";     
        }
    }
    @RequestMapping("/orders")
    public String ordersPage(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            model.addAttribute("error", "Session expired. Please log in again.");
            return "redirect:/user/login";
        }

        List<OrderHistoryDTO> orders = orderService.getOrderHistory(userId);
        model.addAttribute("orders", orders);
        System.out.println("Orders List: " + orders);

        return "orderHistory";
    }

    @PostMapping("/cancelOrder")
    public String cancelOrder(@RequestParam("orderId") int orderId,
                              @RequestParam("products") List<String> products,
                              RedirectAttributes redirectAttributes) {

        System.out.println("Order ID: " + orderId);

        for (String productEntry : products) {
            try {
                String[] parts = productEntry.split("-");
                int productId = Integer.parseInt(parts[0]);
                int quantity = Integer.parseInt(parts[1]);

                System.out.println("✅ Product ID: " + productId + " | Quantity: " + quantity);

                // Get current stock and update it
                int currentStock = cartService.getProductStock(productId);
                cartService.updateProductStock(productId, currentStock + quantity);

                System.out.println("✅ Updated Stock for Product " + productId + ": " + (currentStock + quantity));

            } catch (Exception e) {
                System.out.println("❌ Invalid Product Data: " + productEntry);
            }
        }

        boolean isCancelled = orderService.cancelOrder(orderId);

        if (isCancelled) {
            redirectAttributes.addFlashAttribute("success", "✅ Order has been successfully canceled.");
        } else {
            redirectAttributes.addFlashAttribute("error", "❌ You can only cancel an order while it is in 'Processing' status.");
        }

        return "redirect:/user/orders";
    }

    @RequestMapping("orders/{orderId}/download")
    public void downloadOrderBill(@PathVariable("orderId") int orderId, HttpServletResponse response,HttpSession session) throws IOException {
    	Integer userId = (Integer) session.getAttribute("userId");

		
    	try {
            OrderHistoryDTO order = orderService.getOrderHistory(userId).stream()
                    .filter(o -> o.getOrderId() == orderId)
                    .findFirst()
                    .orElse(null);

            if (order == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found.");
                return;
            }

            ByteArrayOutputStream outputStream = orderService.generateSimpleBillPdf(order);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=GreenMart_Order_" + orderId + ".pdf");
            response.getOutputStream().write(outputStream.toByteArray());
            response.getOutputStream().flush();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating bill.");
        }
    }

    
    
}
