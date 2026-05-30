package com.greenmart.controller;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.greenmart.model.User;
import com.greenmart.service.AdminLoginService;

@Controller
public class AdminLogin {
	@Autowired
	private AdminLoginService adminLoginService;
    @RequestMapping("/admin/login")
    public String adminLogin() {
        System.out.println("Admin login page");
        return "adminLogin";  
    }

    @RequestMapping(value = "/admin/verification", method = RequestMethod.POST)
    public String adminVerification(@ModelAttribute("user") User user, Model model,HttpSession session) {
        System.out.println("Admin verification");

        boolean isAdmin = adminLoginService.checkAdminDetails(user.getUserName(), user.getEmail(), user.getPassword());

        if (isAdmin) {
        	session.setAttribute("adminName", user.getUserName());
        	return "redirect:/admin/panel";
        } else {
            model.addAttribute("errorMessage", "Invalid credentials. Please try again.");
            return "adminLogin";  
        }
        
        
        
    }
    @RequestMapping("admin/logout")
    public String adminLogout(HttpSession session) {
    	User user=new User();
    	session.setAttribute("adminName", user.getUserName());
    	session.removeAttribute("adminName");
    	return "redirect:/";
    }
}
	






/*
	 * private boolean checkAdminCredentials(String userName, String email,
	 * String password) { String jdbcUrl =
	 * "jdbc:mysql://localhost:3306/greenmart"; String dbUser = "root"; String
	 * dbPassword = "12345678";
	 * 
	 * String sql =
	 * "SELECT * FROM user_tbl WHERE user_name = ? AND email = ? AND password = ? AND role = 'admin'"
	 * ;
	 * 
	 * try { Class.forName("com.mysql.cj.jdbc.Driver");
	 * 
	 * Connection con = DriverManager.getConnection(jdbcUrl, dbUser,
	 * dbPassword);
	 * 
	 * PreparedStatement preparedStatement = con.prepareStatement(sql);
	 * preparedStatement.setString(1, userName); preparedStatement.setString(2,
	 * email); preparedStatement.setString(3, password);
	 * 
	 * ResultSet resultSet = preparedStatement.executeQuery();
	 * 
	 * if (resultSet.next()) { return true; } } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * return false; }
	 */
