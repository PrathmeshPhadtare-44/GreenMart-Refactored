<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>
<%
    String logStatus = (String) session.getAttribute("logStaus");

    if (logStatus == null || !logStatus.equals("true")) {
        session.setAttribute("loginAlert", "Please login first!");  
        response.sendRedirect("/user/login"); 
    }
%>
