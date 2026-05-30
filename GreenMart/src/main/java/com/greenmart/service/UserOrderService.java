package com.greenmart.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenmart.model.CartItem;
import com.greenmart.model.OrderHistoryDTO;
import com.greenmart.model.OrderItemDTO;
import com.greenmart.repository.UserOrderRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class UserOrderService {
    @Autowired
    private UserOrderRepository orderRepository;


    public boolean placeOrder(int userId, String customerName, String phoneNumber, String address, double totalAmount, List<CartItem> cartItems) {
        
    	System.out.println("Placing order for userId: " + userId + ", Total Amount: " + totalAmount);

        
    	int orderId = orderRepository.saveOrder(userId, customerName, phoneNumber, totalAmount, address);
        System.out.println("Generated Order ID: " + orderId); // Debugging the generated order ID

        if (orderId == -1) {
            System.out.println("Failed to generate Order ID");
            return false;
        }

        
        boolean itemsSaved = orderRepository.saveOrderItems(orderId, cartItems,totalAmount);
        System.out.println("Order Items Saved: " + itemsSaved); // Check if order items are saved

        if (!itemsSaved) {
            System.out.println("Failed to save order items");
            return false;
        }

        
        boolean cartItemsRemoved = orderRepository.removeCartItems(userId);
        System.out.println("Cart Items Removed: " + cartItemsRemoved); // Check if cart items are removed

        return cartItemsRemoved;
    }
    public List<OrderHistoryDTO> getOrderHistory(int userId) {
        return orderRepository.getOrderHistory(userId);
    }
    public boolean cancelOrder(int orderId) {
        return orderRepository.cancelOrder(orderId);
    }

    public ByteArrayOutputStream generateSimpleBillPdf(OrderHistoryDTO order) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, new BaseColor(0, 102, 51)); // GreenMart Green
        Font sectionFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.DARK_GRAY);
        Font textFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD, BaseColor.WHITE);
        Font totalFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);

        Paragraph title = new Paragraph("GreenMart - Order Bill\n\n", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph("Order Summary\n\n", sectionFont));
        document.add(new Paragraph("Order ID: " + order.getOrderId(), textFont));
        document.add(new Paragraph("Customer Name: " + order.getCustomerName(), textFont));
        document.add(new Paragraph("Phone: " + order.getPhoneNumber(), textFont));
        document.add(new Paragraph("Order Date: " + order.getOrderDate(), textFont));
        document.add(new Paragraph("Shipping Address: " + order.getAddress(), textFont));
        document.add(new Paragraph("Order Status: " + order.getOrderStatus() + "\n\n", textFont));

        PdfPTable table = new PdfPTable(3); 
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        Stream.of("Product Name", "Quantity", "Price").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(new BaseColor(0, 102, 51)); 
            header.setPadding(8);
            header.setPhrase(new Phrase(columnTitle, headerFont));
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(header);
        });

        for (OrderItemDTO item : order.getItems()) {
            table.addCell(new Phrase(item.getProductName(), textFont));
            table.addCell(new Phrase(String.valueOf(item.getQuantity()), textFont));
            table.addCell(new Phrase("₹" + item.getPrice(), textFont)); // Showing total price per item
        }

        document.add(table);
        document.add(new Paragraph("Payment Method: Cash on Delivery\nEstimated delivery: 5-7 business days.\n", textFont));

        Paragraph totalPrice = new Paragraph("Grand Total: ₹" + order.getTotalAmount(), totalFont);
        totalPrice.setAlignment(Element.ALIGN_RIGHT);
        totalPrice.setSpacingBefore(10f);
        document.add(totalPrice);

        Paragraph thanksNote = new Paragraph("\nThank you for shopping with GreenMart!\n\n" +
                "We hope to serve you again soon.", sectionFont);
        thanksNote.setAlignment(Element.ALIGN_CENTER);
        document.add(thanksNote);

        document.close();
        return byteArrayOutputStream;
    }
}
