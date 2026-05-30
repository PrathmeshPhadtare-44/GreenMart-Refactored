package com.greenmart.model;

import java.sql.Timestamp;

import lombok.Data;
@Data
public class AdminFeedback {
	private int feedbackId;
    private int userId;
    private int productId;
    private int rating;
    private String comment;
    private Timestamp feedbackDate;
    private String userName;
    private String productName;}
