package com.greenmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.greenmart.model.Feedback;
import com.greenmart.repository.FeedbackRepository;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public boolean submitFeedback(Feedback feedback) {
        return feedbackRepository.saveFeedback(feedback);
    }
    public boolean hasUserFeedback(int userId, int productId) {
        return feedbackRepository.hasFeedback(userId, productId);
    }

}
