<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>
<%
    String adminName = (String) session.getAttribute("adminName");

    if (adminName == null || adminName.isEmpty()) {
        session.setAttribute("loginAlert", "Please login first!");  
        response.sendRedirect("login"); 
    }
%>
