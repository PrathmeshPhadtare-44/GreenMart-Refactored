package com.greenmart.service;

import com.greenmart.model.ManagedUser;
import com.greenmart.model.UserOrder;
import com.greenmart.model.UserFeedback;
import com.greenmart.repository.AdminUserManageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserManageService {

    @Autowired
    private AdminUserManageRepository adminUserManageRepository;

    public List<ManagedUser> getAllUsers() {
        return adminUserManageRepository.getAllUsers();
    }

    public void updateUserStatus(int userId, int status) {
        adminUserManageRepository.updateUserStatus(userId, status);
    }

    public ManagedUser getUserProfile(int userId) {
        return adminUserManageRepository.getUserProfile(userId);
    }

    public List<UserOrder> getUserOrderHistory(int userId) {
        return adminUserManageRepository.getUserOrderHistory(userId);
    }

    public List<UserFeedback> getUserFeedback(int userId) {
        return adminUserManageRepository.getUserFeedback(userId);
    }
}
