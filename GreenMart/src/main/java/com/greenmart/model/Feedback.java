package com.greenmart.model;

import lombok.Data;

@Data
public class Feedback {
    private int feedbackId; 
    private int userId;
    private int productId;
    private int rating;
    private String comment;
    private String feedbackDate; 
    }
