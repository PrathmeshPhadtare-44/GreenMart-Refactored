package com.greenmart.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.greenmart.db.DBConfig;
import com.greenmart.model.CartItem;

@Repository
public class CartRepository {

    private DBConfig db = new DBConfig();
    public boolean checkIfExists(int userId, int productId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT quantity FROM cart_tbl WHERE user_id = ? AND product_id = ?";
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        return false;
    }

    public boolean updateQuantity(int userId, int productId, int quantity) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "UPDATE cart_tbl SET quantity = quantity + ? WHERE user_id = ? AND product_id = ?";
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, quantity);
            stmt.setInt(2, userId);
            stmt.setInt(3, productId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
        return false;
    }

    public boolean addNewProduct(int userId, int productId, int quantity) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "INSERT INTO cart_tbl (user_id, product_id, quantity) VALUES (?, ?, ?)";
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
        return false;
    }

    public boolean addToCart(int userId, int productId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "INSERT INTO cart_tbl (user_id, product_id) VALUES (?, ?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = db.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
        return false;
    }

    public int getCartCount(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(DISTINCT product_id) AS cartCount FROM cart_tbl WHERE user_id = ?";
        int cartCount = 0;

        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                cartCount = rs.getInt("cartCount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }

        return cartCount;
    }


    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
    public List<CartItem> getCartItemsByUserId(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT c.product_id, c.quantity AS total_quantity, p.product_name, " +
                       "p.price AS single_price, p.image, " +
                       "(c.quantity * p.price) AS total_price " +
                       "FROM cart_tbl c " +
                       "JOIN product_tbl p ON c.product_id = p.product_id " +
                       "WHERE c.user_id = ? " +
                       "GROUP BY c.product_id, p.product_name, p.price, p.image, c.quantity";

        System.out.println("Executing query: " + query);

        try {
            conn = new DBConfig().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            System.out.println("Executing statement with user ID: " + userId);

            rs = stmt.executeQuery();
            while (rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setProductId(rs.getInt("product_id"));
                cartItem.setQuantity(rs.getInt("total_quantity"));
                cartItem.setProductName(rs.getString("product_name"));
                cartItem.setSinglePrice(rs.getDouble("single_price"));
                cartItem.setImage(rs.getString("image"));
                cartItem.setTotalPrice(rs.getDouble("total_price"));
                cartItems.add(cartItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred in repository while fetching cart items: " + e.getMessage());
            throw new RuntimeException("Error fetching cart items: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        System.out.println("Fetched Cart Items from repository: " + cartItems);
        return cartItems;
    }

    
    public void removeItem(int productId, int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "DELETE FROM cart_tbl WHERE product_id = ? AND user_id = ?";

        try {
            conn = new DBConfig().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, productId);
            stmt.setInt(2, userId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No item found to remove");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error removing item from cart: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    
    public CartItem getCartItem(int userId, int productId) {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT product_id, quantity FROM cart_tbl WHERE user_id = ? AND product_id = ?";
        
        try {
            conn = new DBConfig().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                CartItem item = new CartItem();
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                return item;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching cart item: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    public void updateCartQuantity(int userId, int productId, int quantity) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "UPDATE cart_tbl SET quantity = ? WHERE user_id = ? AND product_id = ?";
        
        try {
            conn = new DBConfig().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, quantity);
            stmt.setInt(2, userId);
            stmt.setInt(3, productId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating cart quantity: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    public int getProductStock(int productId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT stock FROM product_tbl WHERE product_id = ?";
        
        try {
            conn = new DBConfig().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, productId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("stock");
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching product stock: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    public void updateProductStock(int productId, int newStock) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String query = "UPDATE product_tbl SET stock = ? WHERE product_id = ?";
        
        try {
            conn = new DBConfig().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, newStock);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating product stock: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
    }

}

    



