package com.greenmart.service;


import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenmart.repository.UserAccountRepository;

@Service
public class UserAccountService {
	@Autowired
	UserAccountRepository userAccountRepository;
	@Autowired
	EmailService emailService;
	public boolean registerUser(String username, String password, String email) {
        

        boolean isSaved = userAccountRepository.saveUser(username, password, email);

        if (isSaved) {
        	String subject = "Welcome to GreenMart! Your Registration Was Successful!";
        	String message = "" +
        	        "<!DOCTYPE html>" +
        	        "<html>" +
        	        "<head>" +
        	        "<style>" +
        	        "  body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }" +
        	        "  .container { background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); max-width: 500px; margin: auto; text-align: center; }" +
        	        "  .header { font-size: 24px; font-weight: bold; color: #2E8B57; }" +  // Green header
        	        "  .content { font-size: 18px; color: #333; margin-top: 15px; line-height: 1.5; }" +  // Darker text for readability
        	        "  .highlight { font-size: 20px; font-weight: bold; color: #2E8B57; }" +  // Green highlight
        	        "  .footer { font-size: 14px; color: #777; margin-top: 25px; }" +
        	        "</style>" +
        	        "</head>" +
        	        "<body>" +
        	        "  <div class='container'>" +
        	        "    <div class='header'>Welcome to GreenMart, " + username + "!</div>" +
        	        "    <p class='content'>We're excited to have you as part of our community.</p>" +
        	        "    <p class='content'>Your registration was successful, and you’re all set to explore a wide range of fresh and organic products.</p>" +
        	        "    <p class='highlight'>Start shopping today and enjoy the best of GreenMart!</p>" +
        	        "    <div class='footer'>Warm regards,<br><strong>The GreenMart Team</strong></div>" +
        	        "  </div>" +
        	        "</body>" +
        	        "</html>";




            if (!emailService.sendEmail(email, subject, message)) {
                return false;
            }
        }

        return isSaved;
    }
	public boolean sendVerificationCode(String email, String verificationCode) {
	    String subject = "Your GreenMart Email Verification Code";
	    String message = "<!DOCTYPE html>\n"
	            + "<html>\n"
	            + "<head>\n"
	            + "<style>\n"
	            + "  body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }\n"
	            + "  .container { background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); max-width: 500px; margin: auto; }\n"
	            + "  .header { font-size: 20px; font-weight: bold; color: #333; text-align: center; }\n"
	            + "  .code { font-size: 24px; font-weight: bold; color: #007bff; text-align: center; padding: 10px; background: #eef2ff; border-radius: 5px; }\n"
	            + "  .footer { font-size: 14px; color: #666; text-align: center; margin-top: 20px; }\n"
	            + "</style>\n"
	            + "</head>\n"
	            + "<body>\n"
	            + "  <div class='container'>\n"
	            + "    <div class='header'>Hello,</div>\n"
	            + "    <p>Please use the following code to verify your email address:</p>\n"
	            + "    <div class='code'>" + verificationCode + "</div>\n"
	            + "    <p>If you did not request this, please ignore this email.</p>\n"
	            + "    <div class='footer'>Regards,<br>The GreenMart Team</div>\n"
	            + "  </div>\n"
	            + "</body>\n"
	            + "</html>";

	    return emailService.sendEmail(email, subject, message); // Send email
	}
	public boolean sendVerificationCodeForFP(String email, String verificationCode) {
        String subject = "Password Reset Verification Code - GreenMart";
        String message = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "  body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }\n"
                + "  .container { background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); max-width: 500px; margin: auto; }\n"
                + "  .header { font-size: 20px; font-weight: bold; color: #333; text-align: center; }\n"
                + "  .subheader { font-size: 16px; color: #555; text-align: center; margin-bottom: 20px; }\n"
                + "  .code { font-size: 24px; font-weight: bold; color: #007bff; text-align: center; padding: 10px; background: #eef2ff; border-radius: 5px; letter-spacing: 2px; }\n"
                + "  .message { font-size: 16px; color: #333; margin-top: 20px; line-height: 1.5; }\n"
                + "  .footer { font-size: 14px; color: #666; text-align: center; margin-top: 30px; }\n"
                + "</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <div class='container'>\n"
                + "    <div class='header'>Forgot Your Password?</div>\n"
                + "    <div class='subheader'>No worries! We've got you covered.</div>\n"
                + "    <p class='message'>Please use the verification code below to reset your password. This code is valid for a limited time, so make sure to use it promptly.</p>\n"
                + "    <div class='code'>" + verificationCode + "</div>\n"
                + "    <p class='message'>If you did not request a password reset, please ignore this email. Your account will remain secure.</p>\n"
                + "    <div class='footer'>Regards,<br>The GreenMart Team</div>\n"
                + "  </div>\n"
                + "</body>\n"
                + "</html>";

        return emailService.sendEmail(email, subject, message);
    }

	public boolean isDuplicateUsername(String username) {
        return userAccountRepository.isDuplicateUsername(username);
    }

    public Integer checkUserExits(String username) {
    	return userAccountRepository.checkUserExits(username);
    }
    public boolean isUserBanned(String username) {
    	return userAccountRepository.isUserBanned(username);
    }
    
    
	public Integer checkUserDetailsAndGetUserId(String username, String password) {
    	
	    return userAccountRepository.checkUserDetailsAndGetUserId(username, password);
	}
	public boolean verifyUser(String verificationCode) {
	    return userAccountRepository.verifyUser(verificationCode);
	}
	public String generateVerificationCode() {
	    Random random = new Random();
	    StringBuilder code = new StringBuilder();
	    for (int i = 0; i < 6; i++) {
	        code.append(random.nextInt(10)); 
	    }
	    return code.toString();
	}
	public String getEmailByUsername(String username) {
		return userAccountRepository.getEmailByUsername(username);
	}
	public boolean updatePassword(String username, String newPassword) {
	    System.out.println("Service: Updating password for user: " + username);
	    return userAccountRepository.updatePassword(username, newPassword);
	}

	


}
