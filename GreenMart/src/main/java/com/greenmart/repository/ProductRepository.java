package com.greenmart.repository;

import com.greenmart.db.DBConfig;
import com.greenmart.model.Category;
import com.greenmart.model.Product;
import com.greenmart.model.ProductDetails;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private final DBConfig dbConfig = new DBConfig();

    public boolean deleteProduct(int productId) {
        String query = "DELETE FROM product_tbl WHERE product_id = ?";
        Connection conn= dbConfig.getConnection();
        PreparedStatement stmt = null;

        try {
            
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, productId);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;  // Return true if the product was deleted
        } catch (SQLException e) {
            System.err.println("Error while deleting product: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    public Product findById(int id) {
        String query = "SELECT * FROM product_tbl WHERE product_id = ?";
        Product product = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dbConfig.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setProductId(id);
                product.setCategoryId(rs.getInt("category_id"));
                product.setProductName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setImage(rs.getString("image"));
                product.setStatus(rs.getInt("status"));
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching product by ID: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return product;
    }

    public void saveProduct(Product product) {
        String query = "INSERT INTO product_tbl (category_id, product_name, description, price, stock, image, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbConfig.getConnection();
            stmt = conn.prepareStatement(query);

            stmt.setInt(1, product.getCategoryId());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getStock());
            stmt.setString(6, product.getImage());
            stmt.setInt(7, product.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while saving product: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    public void updateProduct(Product product) {
        String query = "UPDATE product_tbl SET product_name = ?, description = ?, price = ?, stock = ?, image = ?, status = ? , category_id = ? WHERE product_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbConfig.getConnection();
            stmt = conn.prepareStatement(query);

            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getStock());
            stmt.setString(5, product.getImage());
            stmt.setInt(6, product.getStatus());
            stmt.setInt(7, product.getCategoryId());           
            stmt.setInt(8, product.getProductId());
System.out.println("Repo --------"+product.getCategoryId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating product: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    public List<Category> getActiveCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT category_id, category_name FROM category_tbl WHERE status = 1";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dbConfig.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                categories.add(category);
        }
        } catch (SQLException e) {
            System.err.println("Error while fetching active categories: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return categories;
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
    public boolean toggleStatus(Product product) {
        String query = "UPDATE product_tbl SET status = ? WHERE product_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
        	System.out.println("status tiggling in repo");
            conn = dbConfig.getConnection();
            stmt = conn.prepareStatement(query);

            stmt.setInt(1, product.getStatus());
            stmt.setInt(2, product.getProductId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            System.out.println("Error while updating product status: " + e.getMessage());
            return false; 
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            DBConfig dbConfig = new DBConfig();
            conn = dbConfig.getConnection();
            String sql = "SELECT p.*, c.category_name FROM product_tbl p "
                    + "JOIN category_tbl c ON p.category_id = c.category_id "
                    + "WHERE c.status = 1 "
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
                product.setCategoryId(rs.getInt("category_id"));
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

    public ProductDetails findProductDetailsById(int id) {
        String productQuery = "SELECT p.*, c.category_name FROM product_tbl p " +
                              "JOIN category_tbl c ON p.category_id = c.category_id " +
                              "WHERE p.product_id = ?";

        String feedbackQuery = "SELECT u.user_name, f.rating, f.comment FROM feedback_tbl f " +
                               "JOIN user_tbl u ON f.user_id = u.user_id " +
                               "WHERE f.product_id = ?";

        ProductDetails productDetails = null;
        List<String> userNames = new ArrayList<>();
        List<Integer> ratings = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        double averageRating = 0.0;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
        	DBConfig dbConfig = new DBConfig();
            conn = dbConfig.getConnection();

            stmt = conn.prepareStatement(productQuery);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                productDetails = new ProductDetails();
                productDetails.setProductId(id);
                productDetails.setCategoryId(rs.getInt("category_id"));
                productDetails.setProductName(rs.getString("product_name"));
                productDetails.setDescription(rs.getString("description"));
                productDetails.setPrice(rs.getDouble("price"));
                productDetails.setStock(rs.getInt("stock"));
                productDetails.setImage(rs.getString("image"));
                productDetails.setStatus(rs.getInt("status"));
                productDetails.setCategoryName(rs.getString("category_name"));
            }

            rs.close();
            stmt.close();

            // Fetch feedback details
            stmt = conn.prepareStatement(feedbackQuery);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            int totalRatings = 0;
            int ratingSum = 0;

            while (rs.next()) {
                userNames.add(rs.getString("user_name"));
                int rating = rs.getInt("rating");
                ratings.add(rating);
                comments.add(rs.getString("comment"));

                totalRatings++;
                ratingSum += rating;
            }

            if (totalRatings > 0) {
                averageRating = (double) ratingSum / totalRatings;
            }

            if (productDetails != null) {
                productDetails.setUserNames(userNames);
                productDetails.setRatings(ratings);
                productDetails.setComments(comments);
                productDetails.setAverageRating(averageRating);
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching product details with feedback: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return productDetails;
    }
	
}
