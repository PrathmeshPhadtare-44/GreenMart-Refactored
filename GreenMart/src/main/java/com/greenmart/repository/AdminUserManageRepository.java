package com.greenmart.repository;

import com.greenmart.db.DBConfig;
import com.greenmart.model.ManagedUser;
import com.greenmart.model.UserFeedback;
import com.greenmart.model.UserOrder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminUserManageRepository {

    private DBConfig dbConfig = new DBConfig(); // Using your custom DBConfig class

    public List<ManagedUser> getAllUsers() {
        List<ManagedUser> users = new ArrayList<>();
        String sql = "SELECT user_id, user_name, email, role, status FROM user_tbl WHERE role = 'user'";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                ManagedUser user = new ManagedUser();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getInt("status"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void updateUserStatus(int userId, int status) {
        String sql = "UPDATE user_tbl SET status = ? WHERE user_id = ?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, status);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ManagedUser getUserProfile(int userId) {
        String sql = "SELECT user_id, user_name, email, role, status FROM user_tbl WHERE user_id = ?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ManagedUser user = new ManagedUser();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUserName(rs.getString("user_name"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    user.setStatus(rs.getInt("status"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UserOrder> getUserOrderHistory(int userId) {
        List<UserOrder> orders = new ArrayList<>();
        String sql = "SELECT * FROM order_bill_tbl WHERE user_id = ?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UserOrder order = new UserOrder();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setCustomerName(rs.getString("customer_name"));
                    order.setTotalAmount(rs.getBigDecimal("total_amount"));
                    order.setOrderStatus(rs.getString("order_status"));
                    order.setOrderDate(rs.getTimestamp("order_date"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<UserFeedback> getUserFeedback(int userId) {
        List<UserFeedback> feedbacks = new ArrayList<>();
        String sql = "SELECT * FROM feedback_tbl WHERE user_id = ?";
        
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UserFeedback feedback = new UserFeedback();
                    feedback.setFeedbackId(rs.getInt("feedback_id"));
                    feedback.setUserId(rs.getInt("user_id"));
                    feedback.setProductId(rs.getInt("product_id"));
                    feedback.setRating(rs.getInt("rating"));
                    feedback.setComment(rs.getString("comment"));
                    feedback.setFeedbackDate(rs.getTimestamp("feedback_date"));
                    feedbacks.add(feedback);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbacks;
    }
}
