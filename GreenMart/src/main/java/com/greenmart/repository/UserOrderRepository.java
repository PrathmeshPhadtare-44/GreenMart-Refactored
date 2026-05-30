package com.greenmart.repository;

import com.greenmart.db.DBConfig;
import com.greenmart.model.CartItem;
import com.greenmart.model.OrderHistoryDTO;
import com.greenmart.model.OrderItemDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public class UserOrderRepository {

	 public int saveOrder(int userId, String customerName, String phoneNumber, double totalAmount, String address) {
	        int orderId = -1;
	        String query = "INSERT INTO order_bill_tbl (user_id, customer_name, phone_number, total_amount, address) VALUES (?, ?, ?, ?, ?)";
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        DBConfig dbconfig = new DBConfig();

	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            conn = dbconfig.getConnection();
	            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	            stmt.setInt(1, userId);
	            stmt.setString(2, customerName);
	            stmt.setString(3, phoneNumber);
	            stmt.setDouble(4, totalAmount);
	            stmt.setString(5, address);

	            int rows = stmt.executeUpdate();
	            System.out.println("Rows inserted in order_bill_tbl: " + rows); // Debugging

	            if (rows > 0) {
	                rs = stmt.getGeneratedKeys();
	                if (rs.next()) {
	                    orderId = rs.getInt(1);
	                    System.out.println("Generated Order ID: " + orderId); // Debugging
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            closeResources(conn, stmt, rs);
	        }
	        return orderId;
	    }

	    public boolean saveOrderItems(int orderId, List<CartItem> cartItems,double totalPrice) {
	        boolean isSuccess = false;
	        String query = "INSERT INTO order_item_tbl (order_id, product_id, quantity, price, total_price) VALUES (?, ?, ?, ?, ?)";
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        DBConfig dbconfig = new DBConfig();

	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            conn = dbconfig.getConnection();
	            stmt = conn.prepareStatement(query);
	            System.out.println("Saving order items for Order ID: " + orderId); // Debugging
	            for (CartItem item : cartItems) {
	                System.out.println("Inserting item with productId: " + item.getProductId() + " quantity: " + item.getQuantity() + " price: " + item.getSinglePrice()); // Debugging
	                stmt.setInt(1, orderId);
	                stmt.setInt(2, item.getProductId());
	                stmt.setInt(3, item.getQuantity());
	                stmt.setDouble(4, item.getSinglePrice());
	                stmt.setDouble(5, totalPrice);
	                stmt.addBatch();
	            }
	            int[] rows = stmt.executeBatch();
	            System.out.println("Rows inserted in order_item_tbl: " + rows.length); // Debugging
	            if (rows.length > 0) {
	                isSuccess = true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            closeResources(conn, stmt, null);
	        }
	        return isSuccess;
	    }

	    public boolean removeCartItems(int userId) {
	        boolean isRemoved = false;
	        String query = "DELETE FROM cart_tbl WHERE user_id = ?";
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        DBConfig dbconfig = new DBConfig();

	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            conn = dbconfig.getConnection();
	            stmt = conn.prepareStatement(query);
	            stmt.setInt(1, userId);

	            int rows = stmt.executeUpdate();
	            System.out.println("Rows deleted from cart_tbl: " + rows); 
	            if (rows > 0) {
	                isRemoved = true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            closeResources(conn, stmt, null);
	        }
	        return isRemoved;
	    }
	    public List<OrderHistoryDTO> getOrderHistory(int userId) {
	        Map<Integer, OrderHistoryDTO> ordersMap = new HashMap<>();
	        String query = "SELECT o.order_id, o.user_id, o.customer_name, o.phone_number, o.total_amount, o.order_status, o.address, " +
	                "o.order_date, oi.product_id, oi.quantity, oi.price, oi.total_price, p.product_name, p.image, p.status AS product_status, " +
	                "f.rating, f.comment " +
	                "FROM order_bill_tbl o " +
	                "JOIN order_item_tbl oi ON o.order_id = oi.order_id " +
	                "JOIN product_tbl p ON oi.product_id = p.product_id " +
	                "LEFT JOIN feedback_tbl f ON oi.product_id = f.product_id AND o.user_id = f.user_id " +
	                "WHERE o.user_id = ?";

	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        DBConfig dbconfig = new DBConfig();

	        try {
	            conn = dbconfig.getConnection();
	            stmt = conn.prepareStatement(query);
	            stmt.setInt(1, userId);
	            rs = stmt.executeQuery();

	            while (rs.next()) {
	                int orderId = rs.getInt("order_id");

	                OrderHistoryDTO order = ordersMap.get(orderId);
	                if (order == null) {
	                    order = new OrderHistoryDTO();
	                    order.setOrderId(orderId);
	                    order.setCustomerName(rs.getString("customer_name"));
	                    order.setPhoneNumber(rs.getString("phone_number"));
	                    order.setTotalAmount(rs.getDouble("total_amount"));
	                    order.setOrderStatus(rs.getString("order_status"));
	                    order.setAddress(rs.getString("address"));
	                    order.setOrderDate(rs.getTimestamp("order_date"));
	                    ordersMap.put(orderId, order);
	                }

	                OrderItemDTO item = new OrderItemDTO();
	                item.setProductId(rs.getInt("product_id"));
	                item.setProductName(rs.getString("product_name"));
	                item.setQuantity(rs.getInt("quantity"));
	                item.setPrice(rs.getDouble("price"));
	                item.setTotalPrice(rs.getDouble("total_price"));
	                item.setImage(rs.getString("image"));
	                item.setProductStatus(rs.getInt("product_status"));
	                item.setRating(rs.getInt("rating"));
	                item.setComment(rs.getString("comment"));
	                order.addItem(item);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            closeResources(conn, stmt, rs);
	        }

	        return new ArrayList<>(ordersMap.values());
	    }
	    public boolean cancelOrder(int orderId) {
	    	boolean isCancelled=false;
	        String query = "UPDATE order_bill_tbl SET order_status = 'canceled' WHERE order_id = ?";
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        DBConfig dbconfig = new DBConfig();

	        try {
	            conn = dbconfig.getConnection();
	            stmt = conn.prepareStatement(query);
	            stmt.setInt(1, orderId);

	            int rowsAffected = stmt.executeUpdate();
	            if (rowsAffected > 0) {
	                isCancelled = true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            closeResources(conn, stmt, null);
	        }
	        return isCancelled;
	    }

	    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
