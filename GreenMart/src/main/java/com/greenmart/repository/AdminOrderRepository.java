package com.greenmart.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.springframework.stereotype.Repository;

import com.greenmart.db.DBConfig;
import com.greenmart.model.OrderDTO;
import com.greenmart.model.OrderedProductDTO;

@Repository
public class AdminOrderRepository {

	public List<OrderDTO> findAllOrders() {
        Map<Integer, OrderDTO> ordersMap = new HashMap<>();
        String query = "SELECT o.order_id, u.user_name,o.address, o.order_date, o.total_amount, o.order_status, " +
                "oi.product_id, oi.quantity, p.product_name, p.image " +  
                "FROM order_bill_tbl o " +
                "JOIN user_tbl u ON o.user_id = u.user_id " +
                "JOIN order_item_tbl oi ON o.order_id = oi.order_id " +
                "JOIN product_tbl p ON oi.product_id = p.product_id " +
                "ORDER BY o.order_date DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        DBConfig dbconfig = new DBConfig();

        try {
            conn = dbconfig.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");

                
                OrderDTO order = ordersMap.get(orderId);
                if (order == null) {
                    order = new OrderDTO();
                    order.setOrderId(orderId);
                    order.setUsername(rs.getString("user_name")); 
                    order.setAddress(rs.getString("address"));
                    order.setOrderDate(rs.getTimestamp("order_date"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setStatus(rs.getString("order_status")); 
                    ordersMap.put(orderId, order);
                }

                OrderedProductDTO item = new OrderedProductDTO();
                item.setProductId(rs.getInt("product_id"));
                item.setProductName(rs.getString("product_name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setImage(rs.getString("image"));

                order.addItem(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }

        return new ArrayList<>(ordersMap.values());
    }

    public void updateStatus(int orderId, String status) {
        String sql = "UPDATE order_bill_tbl SET order_status = ? WHERE order_id = ?"; // Fixed table name
        Connection conn = null;
        PreparedStatement stmt = null;
        DBConfig dbconfig = new DBConfig();

        try {
            conn = dbconfig.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    public boolean deleteOrder(int orderId) {
        boolean isDeleted = false;
        Connection conn = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        DBConfig dbconfig = new DBConfig();

        try {
            conn = dbconfig.getConnection();
            conn.setAutoCommit(false); 
            
            String query1 = "DELETE FROM order_item_tbl WHERE order_id = ?";
            stmt1 = conn.prepareStatement(query1);
            stmt1.setInt(1, orderId);
            stmt1.executeUpdate();
          String query2 = "DELETE FROM order_bill_tbl WHERE order_id = ?";
            stmt2 = conn.prepareStatement(query2);
            stmt2.setInt(1, orderId);
            int rowsAffected = stmt2.executeUpdate();

            if (rowsAffected > 0) {
                isDeleted = true;
                conn.commit(); 
                } else {
                conn.rollback(); 
                }
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt1, null);
            closeResources(null, stmt2, null);
        }
        return isDeleted;
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
