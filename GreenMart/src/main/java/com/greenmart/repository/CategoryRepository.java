package com.greenmart.repository;

import com.greenmart.model.Category;
import com.greenmart.db.DBConfig;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepository {

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM category_tbl";
        DBConfig db = new DBConfig();

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                category.setStatus(rs.getInt("status"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return categories;
    }

    public Category getCategoryById(int id) {
        Category category = null;
        String sql = "SELECT * FROM category_tbl WHERE category_id = ?";
        DBConfig db = new DBConfig();

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                category.setStatus(rs.getInt("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return category;
    }

    public void addCategory(Category category) {
        String sql = "INSERT INTO category_tbl (category_name, status) VALUES (?, ?)";
        DBConfig db = new DBConfig();

        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, category.getCategoryName());
            pst.setInt(2, category.getStatus());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void deleteCategory(int id) {
        String sql = "DELETE FROM category_tbl WHERE category_id = ?";
        DBConfig db = new DBConfig();

        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void updateCategoryStatus(Category category) {
        String sql = "UPDATE category_tbl SET status = ? WHERE category_id = ?";
        DBConfig db = new DBConfig();

        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, category.getStatus());
            pst.setInt(2, category.getCategoryId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void editCategory(Category category) {
        String sql = "UPDATE category_tbl SET category_name = ?, status = ? WHERE category_id = ?";
        DBConfig db = new DBConfig();

        try {
            Connection con = db.getConnection();
            
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, category.getCategoryName());
            pst.setInt(2, category.getStatus());
            pst.setInt(3, category.getCategoryId());
            
            pst.executeUpdate();
            
            pst.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public List<Integer> getLinkedCategoryIds() {
        List<Integer> linkedCategoryIds = new ArrayList<>();
        String sql = "SELECT DISTINCT category_id FROM product_tbl";
        DBConfig db = new DBConfig();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                linkedCategoryIds.add(rs.getInt("category_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return linkedCategoryIds;
    }


}
