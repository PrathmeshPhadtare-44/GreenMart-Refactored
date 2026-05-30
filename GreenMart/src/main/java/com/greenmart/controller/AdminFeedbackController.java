package com.greenmart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greenmart.model.AdminFeedback;
import com.greenmart.service.AdminFeedbackService;

@Controller
@RequestMapping("/admin/")
public class AdminFeedbackController {
@Autowired
public AdminFeedbackService feedbackService;
	@RequestMapping("feedback")
	public String feedbackAdminPage(HttpSession session,Model model) {
		if (session.getAttribute("adminName") == null) {
            return "redirect:/admin/login";
        }
        List<AdminFeedback> feedbackList = feedbackService.getAllFeedback();
        model.addAttribute("feedbackList", feedbackList);
        return "feedback";
	}
}
