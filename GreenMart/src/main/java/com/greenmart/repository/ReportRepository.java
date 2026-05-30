package com.greenmart.repository;

import java.sql.*;
import java.util.*;

import org.springframework.stereotype.Repository;

import com.greenmart.db.DBConfig;
import com.greenmart.model.ProductSalesReport;

@Repository
public class ReportRepository {
	/*
	 * public List<ProductSalesReport> getProductSalesReport() {
	 * List<ProductSalesReport> reportList = new ArrayList<>();
	 * 
	 * String query =
	 * "SELECT p.image AS 'image', p.product_name AS 'Product Name', c.category_name AS 'Category', "
	 * + "COALESCE(SUM(oi.quantity), 0) AS 'Total Quantity Sold', " +
	 * "COALESCE(SUM(oi.total_price), 0.00) AS 'Total Revenue', " +
	 * "ROUND(COALESCE(SUM(oi.total_price) / NULLIF(SUM(oi.quantity), 0), 0), 2) AS 'Avg Price per Unit', "
	 * + "COALESCE(COUNT(DISTINCT ob.user_id), 0) AS 'Total Unique Customers', "
	 * + "ROUND(COALESCE(AVG(f.rating), 0), 2) AS 'Average Rating (/5)', " +
	 * "p.stock AS 'Current Stock' " + "FROM product_tbl p " +
	 * "JOIN category_tbl c ON p.category_id = c.category_id " +
	 * "LEFT JOIN order_item_tbl oi ON p.product_id = oi.product_id " +
	 * "LEFT JOIN order_bill_tbl ob ON oi.order_id = ob.order_id " +
	 * "LEFT JOIN feedback_tbl f ON p.product_id = f.product_id " +
	 * "GROUP BY p.product_id, c.category_name, p.stock, p.status " +
	 * "ORDER BY `Total Revenue` DESC;";
	 * 
	 * DBConfig dbConfig = new DBConfig(); Connection conn =
	 * dbConfig.getConnection(); PreparedStatement stmt = null; ResultSet rs =
	 * null;
	 * 
	 * try { stmt = conn.prepareStatement(query); rs = stmt.executeQuery();
	 * 
	 * while (rs.next()) { ProductSalesReport report = new ProductSalesReport();
	 * report.setImage(rs.getString("image"));
	 * report.setProductName(rs.getString("Product Name"));
	 * report.setCategory(rs.getString("Category"));
	 * report.setTotalQuantitySold(rs.getInt("Total Quantity Sold"));
	 * report.setTotalRevenue(rs.getDouble("Total Revenue"));
	 * report.setAvgPricePerUnit(rs.getDouble("Avg Price per Unit"));
	 * report.setTotalUniqueCustomers(rs.getInt("Total Unique Customers"));
	 * report.setAverageRating(rs.getDouble("Average Rating (/5)"));
	 * report.setCurrentStock(rs.getInt("Current Stock"));
	 * 
	 * reportList.add(report); }
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } finally {
	 * closeResources(conn, stmt, rs); }
	 * 
	 * return reportList; }
	 */
	
	public List<ProductSalesReport> getProductSalesReport(String filter) {
	    List<ProductSalesReport> reportList = new ArrayList<>();

	    String query = "SELECT p.image AS 'image', p.product_name AS 'Product Name', c.category_name AS 'Category', " +
	                   "COALESCE(SUM(oi.quantity), 0) AS 'Total Quantity Sold', " +
	                   "COALESCE(SUM(oi.total_price), 0.00) AS 'Total Revenue', " +
	                   "ROUND(COALESCE(SUM(oi.total_price) / NULLIF(SUM(oi.quantity), 0), 0), 2) AS 'Avg Price per Unit', " +
	                   "COALESCE(COUNT(DISTINCT ob.user_id), 0) AS 'Total Unique Customers', " +
	                   "ROUND(COALESCE(AVG(f.rating), 0), 2) AS 'Average Rating (/5)', " +
	                   "p.stock AS 'Current Stock' " +
	                   "FROM product_tbl p " +
	                   "JOIN category_tbl c ON p.category_id = c.category_id " +
	                   "LEFT JOIN order_item_tbl oi ON p.product_id = oi.product_id " +
	                   "LEFT JOIN order_bill_tbl ob ON oi.order_id = ob.order_id " +
	                   "LEFT JOIN feedback_tbl f ON p.product_id = f.product_id ";

	    if (filter != null) {
	        switch (filter) {
	            case "15_days":
	                query += " WHERE ob.order_date >= CURDATE() - INTERVAL 15 DAY ";
	                break;
	            case "monthly":
	                query += " WHERE MONTH(ob.order_date) = MONTH(CURDATE()) " +
	                         "AND YEAR(ob.order_date) = YEAR(CURDATE()) ";
	                break;
	            case "yearly":
	                query += " WHERE YEAR(ob.order_date) = YEAR(CURDATE()) ";
	                break;
	            default:
	                break;
	        }
	    }

	    query += " GROUP BY p.product_id, c.category_name, p.stock, p.status " +
	             " ORDER BY `Total Revenue` DESC;";

	    DBConfig dbConfig = new DBConfig();
	    Connection conn = dbConfig.getConnection();
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        stmt = conn.prepareStatement(query);
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            ProductSalesReport report = new ProductSalesReport();
	            report.setImage(rs.getString("image"));
	            report.setProductName(rs.getString("Product Name"));
	            report.setCategory(rs.getString("Category"));
	            report.setTotalQuantitySold(rs.getInt("Total Quantity Sold"));
	            report.setTotalRevenue(rs.getDouble("Total Revenue"));
	            report.setAvgPricePerUnit(rs.getDouble("Avg Price per Unit"));
	            report.setTotalUniqueCustomers(rs.getInt("Total Unique Customers"));
	            report.setAverageRating(rs.getDouble("Average Rating (/5)"));
	            report.setCurrentStock(rs.getInt("Current Stock"));

	            reportList.add(report);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeResources(conn, stmt, rs);
	    }

	    return reportList;
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