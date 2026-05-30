package com.greenmart.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.greenmart.db.DBConfig;
import com.greenmart.model.Product;

@Repository
public class HomePageRepository {
    private DBConfig db = new DBConfig();

	public List<Product> getAllProducts() {
		 List<Product> products = new ArrayList<>();
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        try {
	        	Class.forName("com.mysql.cj.jdbc.Driver");
	            conn = db.getConnection();
	            String sql = "SELECT p.*, c.category_name "  
	                    + "FROM product_tbl p "
	                    + "JOIN category_tbl c ON p.category_id = c.category_id "
	                    + "WHERE p.status = 1 AND c.status = 1 AND p.stock > 0 "
	                    + "ORDER BY p.product_name ASC";

	         
	         stmt = conn.prepareStatement(sql);
	         rs = stmt.executeQuery();

	         while (rs.next()) {
	             Product product = new Product();
	             product.setProductId(rs.getInt("product_id"));
	             product.setProductName(rs.getString("product_name"));
	             product.setDescription(rs.getString("description"));
	             product.setPrice(rs.getDouble("price"));
	             product.setStock(rs.getInt("stock"));
	             product.setImage(rs.getString("image"));
	             product.setStatus(rs.getInt("status"));
	             product.setCategoryName(rs.getString("category_name")); 
	             products.add(product);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            closeResources(conn, stmt, rs);
	        }
	        return products;
	    }
	 private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            System.err.println("Error while closing resources: " + e.getMessage());
	        }
	    }
}
