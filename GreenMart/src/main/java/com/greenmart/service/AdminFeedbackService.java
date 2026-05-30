package com.greenmart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenmart.model.AdminFeedback;
import com.greenmart.repository.*;
@Service
public class AdminFeedbackService {
@Autowired
public AdminFeedbackRepository feedbackRepository;
	public List<AdminFeedback> getAllFeedback() {
        return feedbackRepository.getAllFeedback();
    }
}
