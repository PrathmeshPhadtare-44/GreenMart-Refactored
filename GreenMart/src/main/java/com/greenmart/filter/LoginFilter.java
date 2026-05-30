package com.greenmart.filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("LoginFilter: Entering filter...");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && "true".equals(session.getAttribute("logStatus")));
        System.out.println("LoginFilter: isLoggedIn = " + isLoggedIn);

        if (!isLoggedIn) {
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                String username = null;
                String userId = null;

                for (Cookie cookie : cookies) {
                    System.out.println("LoginFilter: Found cookie " + cookie.getName() + " = " + cookie.getValue());
                    if ("userLogin".equals(cookie.getName())) {
                        username = cookie.getValue();
                    }
                    if ("userId".equals(cookie.getName())) {
                        userId = cookie.getValue();
                    }
                }

                System.out.println("LoginFilter: username = " + username + ", userId = " + userId);

                if (username != null && userId != null) {
                    session = httpRequest.getSession(true);
                    session.setAttribute("logStatus", "true");
                    session.setAttribute("userName", username);
                    session.setAttribute("userId", Integer.valueOf(userId));
                    System.out.println("LoginFilter: Session updated with username and userId.");
                }
            }
        }

        chain.doFilter(request, response);
        System.out.println("LoginFilter: Exiting filter...");
    }
}
