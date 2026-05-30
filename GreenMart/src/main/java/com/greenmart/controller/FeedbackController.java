package com.greenmart.controller;

import com.greenmart.model.Feedback;
import com.greenmart.service.FeedbackService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/orders")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/feedback")
    public String submitFeedback(
            @RequestParam("productId") int productId,
            @RequestParam("rating") int rating,
            @RequestParam("comment") String comment,
            HttpSession session) {

        System.out.println("Received Feedback: productId=" + productId + ", rating=" + rating + ", comment=" + comment);

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            session.setAttribute("error", "You must be logged in to submit feedback.");
            return "redirect:/user/login";
        }

        Feedback feedback = new Feedback();
        feedback.setUserId(userId);
        feedback.setProductId(productId);
        feedback.setRating(rating);
        feedback.setComment(comment);

        boolean success = feedbackService.submitFeedback(feedback);

        if (success) {
            session.setAttribute("success", "Feedback submitted successfully!");
        } else {
            session.setAttribute("error", "Failed to submit feedback. Please try again.");
        }

        return "redirect:/user/orders"; 
        }
    
}
