package com.greenmart.repository;

import com.greenmart.db.DBConfig;
import com.greenmart.model.Feedback;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class FeedbackRepository {

    public boolean saveFeedback(Feedback feedback) {
        String query = "INSERT INTO feedback_tbl (user_id, product_id, rating, comment) VALUES (?, ?, ?,?)";
        DBConfig dbConfig = new DBConfig();
        Connection conn = dbConfig.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, feedback.getUserId());
            stmt.setInt(2, feedback.getProductId());
            stmt.setInt(3, feedback.getRating());
            stmt.setString(4, feedback.getComment());

            int rows = stmt.executeUpdate();
            System.out.println("Rows inserted: " + rows);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt,null);
        }
    }
    public boolean hasFeedback(int userId, int productId) {
        String query = "SELECT COUNT(*) FROM feedback_tbl WHERE user_id = ? AND product_id = ?";
        DBConfig dbConfig = new DBConfig();
        Connection conn = dbConfig.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Feedback exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        return false;
    }

    private void closeResources(Connection conn, PreparedStatement stmt,ResultSet rs) {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
            if (rs != null) rs.close();
            
        } catch (SQLException e) {
            System.err.println("Error while closing resources: " + e.getMessage());
        }
    }
}
