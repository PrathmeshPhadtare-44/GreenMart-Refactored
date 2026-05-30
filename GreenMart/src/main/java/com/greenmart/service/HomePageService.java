package com.greenmart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenmart.model.Product;
import com.greenmart.repository.HomePageRepository;

@Service
public class HomePageService {
@Autowired
private HomePageRepository homePageRepository;
public List<Product> getAllProducts() {
	
	return homePageRepository.getAllProducts();
}
}
