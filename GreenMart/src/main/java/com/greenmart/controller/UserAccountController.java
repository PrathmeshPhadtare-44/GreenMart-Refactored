package com.greenmart.controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenmart.service.EmailService;
import com.greenmart.service.UserAccountService;

@Controller
@RequestMapping("user")
public class UserAccountController {
	@Autowired
    private UserAccountService userAccountService;
	@Autowired
    EmailService emailService;

    @RequestMapping("login")
    public String userLogin() {
        return "userLogin"; 
    }
    @RequestMapping("sign_up")
    public String userSignup() {
    	
    	return "sign_up";
    }
    @PostMapping("sign_up_data")
    public String signUpData(@RequestParam String user_name, @RequestParam String password, @RequestParam String email, HttpSession session, Model model) {
        if (userAccountService.isDuplicateUsername(user_name)) {
            model.addAttribute("errorMessage", "⚠️ Username already exists!");
            return "sign_up"; 
        }

        String verificationCode = userAccountService.generateVerificationCode(); 
        session.setAttribute("verificationCode", verificationCode); 
        session.setAttribute("email", email); 
        session.setAttribute("password", password); 
        session.setAttribute("user_nametemp", user_name); 

        boolean isSent = userAccountService.sendVerificationCode(email, verificationCode); 

        if (!isSent) {
            model.addAttribute("errorMessage", "⚠️ Failed to send verification code. Please try again.");
            return "sign_up"; 
        }
        session.setAttribute("email", email);
        model.addAttribute("verificationCodeSent", true); 
        return "sign_up";    
        }

    @PostMapping("verify_code")
    public String verifyCode(@RequestParam String verificationCode, HttpSession session, Model model) {
        String storedCode = (String) session.getAttribute("verificationCode"); 
        String email = (String) session.getAttribute("email"); 
        String password = (String) session.getAttribute("password"); 
        String user_nametemp = (String) session.getAttribute("user_nametemp"); 
        
        if (storedCode != null && storedCode.equals(verificationCode)) {
            userAccountService.registerUser(user_nametemp, password, email);
            session.removeAttribute("verificationCode");
            session.removeAttribute("password");
            session.removeAttribute("user_nametemp");
            model.addAttribute("signupSuccess", "Your account has been created successfully! 🎉");
            return "userLogin"; 
            } else {
            model.addAttribute("errorMessage", "⚠️ Invalid verification code. Please try again.");
            model.addAttribute("verificationCodeSent", true); 

            return "sign_up"; 
            }
    }

    @PostMapping("resend_otp")
    public String resendOtp(HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");

        if (email == null) {
        	
            model.addAttribute("errorMessage", "⚠️ Session expired. Please sign up again.");
            return "sign_up"; 
        }

        String newVerificationCode = userAccountService.generateVerificationCode();
        session.setAttribute("verificationCode", newVerificationCode); // Update session with new OTP

        boolean isSent = userAccountService.sendVerificationCode(email, newVerificationCode);

        if (!isSent) {
            model.addAttribute("errorMessage", "⚠️ Failed to resend verification code. Please try again.");
            return "verification_page"; // Show the verification page again
        }

        model.addAttribute("verificationCodeSent", true);
        model.addAttribute("successMessage", "✅ A new verification code has been sent to your email.");
        
        return "sign_up"; 
    }


    @PostMapping("login/verification")
    public String loginVerification(@RequestParam("username") String username,
                                    @RequestParam("password") String password,
                                    HttpSession session,
                                    HttpServletResponse response,
                                    Model model) {

        Integer userIdByUserName = userAccountService.checkUserExits(username);
        if (userIdByUserName == null) {
            model.addAttribute("errorMessage", "Invalid credentials. Please try again.");
            return "userLogin";
        }
        boolean isUserBan = userAccountService.isUserBanned(username);
        if (isUserBan) {
            model.addAttribute("errorMessage", "Your account has been banned. Please contact support.");
            return "userLogin";
        }
        Integer userId = userAccountService.checkUserDetailsAndGetUserId(username, password);
        if (userId != null) {
            session.setAttribute("logStatus", "true");
            session.setAttribute("userName", username);
            session.setAttribute("userId", userId);

            Cookie loginCookie = new Cookie("userLogin", username);
            loginCookie.setMaxAge(7 * 24 * 60 * 60);
            loginCookie.setPath("/");
            response.addCookie(loginCookie);

            Cookie userIdCookie = new Cookie("userId", userId.toString());
            userIdCookie.setMaxAge(7 * 24 * 60 * 60);
            userIdCookie.setPath("/");
            response.addCookie(userIdCookie);

            return "redirect:/";
        } else {
        	model.addAttribute("username", username);

            model.addAttribute("notpass", "true");
            model.addAttribute("errorMessage", "Password is incorrect. Please try again.");
            return "userLogin";
        }
    }



    @RequestMapping("/logout")
    public String userLogout(HttpSession session, HttpServletResponse response) {
        session.invalidate();
        Cookie loginCookie = new Cookie("userLogin", null);
        loginCookie.setMaxAge(0);
        loginCookie.setPath("/");
        response.addCookie(loginCookie);

        return "redirect:/";
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("code") String code, Model model) {
        boolean verified = userAccountService.verifyUser(code);
        
        if (verified) {
            model.addAttribute("message", "Email verified successfully. You can now log in.");
            return "userLogin";
        } else {
            model.addAttribute("error", "Invalid or expired verification link.");
            return "errorPage";
        }
    }
    @RequestMapping("/login/forgotPassword")
    public String forgotPassword(@RequestParam("username") String username, Model model) {
        String email = userAccountService.getEmailByUsername(username.trim());
        if (email != null) {
            String maskedEmail = "*****" + email.substring(email.indexOf("@") - 4);
            model.addAttribute("username", username);
            model.addAttribute("maskedEmail", maskedEmail);
        }
        return "forgotPassword";
    }

    @PostMapping("login/forgotPassword/verifyEmail")
    public String verifyEmail(@RequestParam String username, @RequestParam String email, Model model) {
        String actualEmail = userAccountService.getEmailByUsername(username);
        if (actualEmail != null && actualEmail.equals(email)) {
            String verificationCode = userAccountService.generateVerificationCode();
            boolean isSent = userAccountService.sendVerificationCodeForFP(email, verificationCode);
            if (isSent) {
                model.addAttribute("verificationCode", verificationCode);
                model.addAttribute("emailVerified", true);
                model.addAttribute("username", username);
            } else {
                model.addAttribute("errorMessage", "Failed to send verification code.");
            }
        } else {
            model.addAttribute("errorMessage", "Email doesn't match our records.");
           email = userAccountService.getEmailByUsername(username.trim());
            if (email != null) {
                String maskedEmail = "*****" + email.substring(email.indexOf("@") - 4);
                model.addAttribute("username", username);
                model.addAttribute("maskedEmail", maskedEmail);
            }
        }
        
        return "forgotPassword";
    }

    @PostMapping("login/forgotPassword/verifyCode")
    public String verifyCode(@RequestParam String username, @RequestParam String verificationCode, @RequestParam String actualCode, Model model) {
        if (verificationCode.equals(actualCode)) {
            model.addAttribute("resetAllowed", true);
            model.addAttribute("username", username);
        } else {
            model.addAttribute("emailVerified", true);
            model.addAttribute("errorMessage", "Invalid verification code.");
            model.addAttribute("username", username);
            model.addAttribute("verificationCode", actualCode);
        }
        return "forgotPassword";
    }

    @PostMapping("login/forgotPassword/resetPassword")
    public String resetPassword(@RequestParam String username, @RequestParam String newPassword, Model model) {
        boolean isUpdated = userAccountService.updatePassword(username, newPassword);
        if (isUpdated) {
            model.addAttribute("username", username);
            model.addAttribute("successMessage", "✅ Password reset successfully! Please login.");
            return "userLogin";
        } else {
            model.addAttribute("errorMessage", "❌ Password reset failed. Please try again.");
            model.addAttribute("resetAllowed", true);
            model.addAttribute("username", username);
            return "forgotPassword";
        }
    }





}
