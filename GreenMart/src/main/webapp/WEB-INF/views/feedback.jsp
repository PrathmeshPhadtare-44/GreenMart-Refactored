<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="adminHeader.jsp" %>
<%@ include file="loadingLayout.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="checkAdminLogin.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>📝 User Feedback - GreenMart</title>
    <style>
        .feedback-container {
            max-width: 100%;
            padding: 20px;
        }
        .feedback-table {
            width: 100%;
            margin: 0;
        }
        .feedback-table th, .feedback-table td {
            white-space: nowrap;
            padding: 12px;
            text-align: center;
            font-size: 14px;
        }
        .feedback-heading {
            text-align: center;
        }
        .feedback-back-btn {
            display: block;
            width: fit-content;
            margin: 20px auto;
            text-align: center;
        }
    </style>
</head>
<body style="background-color: #f5fdf0; font-family: Arial, sans-serif;">
    <div class="feedback-container">
        <h2 class="feedback-heading">📝 User Feedback</h2>

        <table class="feedback-table table-striped table-bordered">
            <thead class="thead-dark">
                <tr>
                    <th>👤 User</th>
                    <th>📌 Product</th>
                    <th>⭐ Rating</th>
                    <th>💬 Comment</th>
                    <th>📅 Date</th>
                    <th>🔍 Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="feedback" items="${feedbackList}">
                    <tr>
                        <td>${feedback.userName}</td>
                        <td>${feedback.productName}</td>
                        <td>⭐ ${feedback.rating}/5</td>
                        <td>${feedback.comment}</td>
                        <td>${feedback.feedbackDate}</td>
                        <td>
                            <a href="user-profile?userId=${feedback.userId}" class="btn btn-primary btn-sm">🔍 View Profile</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="feedback-back-btn">
            <a href="${pageContext.request.contextPath}/admin/panel" class="btn btn-primary btn-lg">🏠 Back to Admin Dashboard</a>
        </div>
    </div>
</body>
</html>

