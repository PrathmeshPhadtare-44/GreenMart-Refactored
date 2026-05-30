package com.greenmart.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.greenmart.db.DBConfig;
@Repository
public class AdminLoginRepository {

	
	public boolean checkAdminCredentials(String userName, String email, String password) {
		PreparedStatement pst;
		Connection con;
        
        String sql = "SELECT * FROM user_tbl WHERE user_name = ? AND email = ? AND password = ? AND role = 'admin'";
        DBConfig db=new DBConfig();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

             con = db.getConnection();

             pst = con.prepareStatement(sql);
            pst.setString(1, userName);
            pst.setString(2, email);
            pst.setString(3, password);

            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();  
        }
        

        return false; 
    }
	
}
