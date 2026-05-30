package com.greenmart.controller;

import com.greenmart.model.ManagedUser;
import com.greenmart.model.UserOrder;
import com.greenmart.model.UserFeedback;
import com.greenmart.service.AdminUserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserManageController {

    @Autowired
    private AdminUserManageService adminUserManageService;

    @RequestMapping("/manage-users")
    public String manageUsers(Model model) {
        List<ManagedUser> users = adminUserManageService.getAllUsers();
        model.addAttribute("users", users);
        return "manageUsers";     }

    @RequestMapping("/block-user")
    public String blockUser(@RequestParam("userId") int userId) {
        adminUserManageService.updateUserStatus(userId, 0);
        return "redirect:/admin/manage-users"; 
    }

    @RequestMapping("/unblock-user")
    public String unblockUser(@RequestParam("userId") int userId) {
        adminUserManageService.updateUserStatus(userId, 1);
        return "redirect:/admin/manage-users"; 
    }

    @RequestMapping("/user-profile")
    public String userProfile(@RequestParam("userId") int userId, Model model) {
        ManagedUser user = adminUserManageService.getUserProfile(userId);
        List<UserOrder> orders = adminUserManageService.getUserOrderHistory(userId);
        List<UserFeedback> feedbacks = adminUserManageService.getUserFeedback(userId);

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        model.addAttribute("feedbacks", feedbacks);

        return "userProfile";     }
}
