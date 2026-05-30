package com.greenmart.repository;

import com.greenmart.db.DBConfig;
import com.greenmart.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public class UserAccountRepository {
    private final DBConfig dbConfig = new DBConfig();

    public boolean saveUser(String username, String password, String email) {
        boolean isSaved = false;
        String query = "INSERT INTO user_tbl (user_name, password, email) VALUES (?, ?, ?)";
        try (Connection conn = dbConfig.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            isSaved = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSaved;
    }




    private void closeResources(Connection con, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.err.println("Error while closing resources: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public Integer checkUserExits(String username) {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet resultSet = null;
        DBConfig dbconfig = new DBConfig();
        String sql = "SELECT user_id FROM user_tbl WHERE user_name = ?  AND role = 'user'";

        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	con = dbconfig.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            resultSet = pst.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("user_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(con, pst, resultSet);
        }
        return null;
    }
    public boolean isUserBanned(String username) {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet resultSet = null;
        DBConfig dbconfig = new DBConfig();
        String sql = "SELECT user_id FROM user_tbl WHERE user_name = ? AND role = 'user' AND status = 0";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = dbconfig.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            resultSet = pst.executeQuery();

            return resultSet.next();         } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(con, pst, resultSet);
        }
        return false;
    }

    public Integer checkUserDetailsAndGetUserId(String username, String password) {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet resultSet = null;
        DBConfig dbconfig = new DBConfig();
        String sql = "SELECT user_id FROM user_tbl WHERE user_name = ? AND password = ? AND role = 'user'";

        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	con = dbconfig.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            resultSet = pst.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("user_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(con, pst, resultSet);
        }
        return null;
    }

    public User getUserDataById(int userId) {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet resultSet = null;
        DBConfig dbconfig = new DBConfig();  
        User user = new User();
        String sql = "SELECT * FROM user_tbl WHERE user_id=? AND role = 'user'";

        try {
            con = dbconfig.getConnection();  
            pst = con.prepareStatement(sql);
            pst.setInt(1, userId);
            resultSet = pst.executeQuery();

            if (resultSet.next()) {
                user.setUserid(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setPassword(resultSet.getString("password"));
                user.setAddress(resultSet.getString("address"));
                user.setRole(resultSet.getString("role"));
                user.setCreated_at(resultSet.getTimestamp("created_at"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                        closeResources(con, pst, resultSet);
        }

        return user;      }
	/*
	 * public boolean updateUserContactDetails(int userId, String phoneNumber,
	 * String address) { PreparedStatement pst = null; Connection con = null;
	 * DBConfig dbconfig = new DBConfig(); boolean isUpdated = false;
	 * 
	 * String sql =
	 * "UPDATE user_tbl SET phone_number = ?, address = ? WHERE user_id = ? AND role = 'user'"
	 * ;
	 * 
	 * try { con = dbconfig.getConnection(); pst = con.prepareStatement(sql);
	 * pst.setString(1, phoneNumber); pst.setString(2, address); pst.setInt(3,
	 * userId);
	 * 
	 * int rowsAffected = pst.executeUpdate(); isUpdated = rowsAffected > 0; }
	 * catch (SQLException e) { e.printStackTrace(); } finally {
	 * closeResources(con, pst, null); }
	 * 
	 * return isUpdated; }
	 */

    public boolean verifyUser(String verificationCode) {
        String query = "UPDATE user_tbl SET is_verified = 1, verification_code = NULL WHERE verification_code = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dbConfig.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, verificationCode);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
        return false;
    }
    public boolean isDuplicateUsername(String username) {
        String query = "SELECT COUNT(*) FROM user_tbl WHERE user_name = ? ";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dbConfig.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Duplicate found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }

        return false;
    }




	public String getEmailByUsername(String username) 
	{PreparedStatement pst = null;
    Connection con = null;
    ResultSet resultSet = null;
    DBConfig dbconfig = new DBConfig();
    String sql = "SELECT email FROM user_tbl WHERE user_name = ?  AND role = 'user'";

    try {
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	con = dbconfig.getConnection();
        pst = con.prepareStatement(sql);
        pst.setString(1, username);
        resultSet = pst.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("email");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        closeResources(con, pst, resultSet);
    }
    return null;
	}




	public boolean updatePassword(String username, String newPassword) {
	    PreparedStatement pst = null;
	    Connection con = null;
	    DBConfig dbconfig = new DBConfig();
	    String sql = "UPDATE user_tbl SET password=? WHERE user_name=? AND role='user'";
	    int rowsUpdated = 0;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        con = dbconfig.getConnection();
	        pst = con.prepareStatement(sql);
	        pst.setString(1, newPassword);
	        pst.setString(2, username);
	        rowsUpdated = pst.executeUpdate();

	        System.out.println("Repository: Rows updated: " + rowsUpdated);
	        return rowsUpdated > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeResources(con, pst, null);
	    }
	    return false;
	}





	}
