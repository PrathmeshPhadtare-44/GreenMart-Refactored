package com.greenmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenmart.repository.AdminLoginRepository;
@Service
public class AdminLoginService {
	@Autowired
	private AdminLoginRepository adminLoginRepository;
	public boolean checkAdminDetails(String userName, String email, String password) {
		return adminLoginRepository.checkAdminCredentials(userName,email,password);
	}
}
