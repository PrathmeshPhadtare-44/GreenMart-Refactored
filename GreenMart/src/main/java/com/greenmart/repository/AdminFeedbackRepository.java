package com.greenmart.repository;

import java.sql.*;
import java.util.*;

import org.springframework.stereotype.Repository;

import com.greenmart.db.DBConfig;
import com.greenmart.model.AdminFeedback;

@Repository
public class AdminFeedbackRepository {
	public List<AdminFeedback> getAllFeedback() {
        List<AdminFeedback> feedbackList = new ArrayList<>();
        String query = "SELECT f.feedback_id, f.user_id, u.user_name, f.product_id, p.product_name, " +
                "f.rating, f.comment, f.feedback_date " +
                "FROM feedback_tbl f " +
                "JOIN user_tbl u ON f.user_id = u.user_id " +
                "JOIN product_tbl p ON f.product_id = p.product_id " +
                "ORDER BY f.feedback_date DESC";

        
        DBConfig dbConfig = new DBConfig();
        Connection conn = dbConfig.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                AdminFeedback feedback = new AdminFeedback();
                feedback.setFeedbackId(rs.getInt("feedback_id"));
                feedback.setUserId(rs.getInt("user_id"));
                feedback.setUserName(rs.getString("user_name"));
                feedback.setProductId(rs.getInt("product_id"));
                feedback.setProductName(rs.getString("product_name"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setComment(rs.getString("comment"));
                feedback.setFeedbackDate(rs.getTimestamp("feedback_date"));
                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        return feedbackList;
    }

    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
