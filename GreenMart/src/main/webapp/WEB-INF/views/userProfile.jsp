<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.greenmart.model.UserOrder, com.greenmart.model.UserFeedback, com.greenmart.model.ManagedUser" %>
<%@ include file="adminHeader.jsp" %>
<%@ include file="loadingLayout.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>👤 User Profile</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="container mt-4">
    <h2>👤 User Profile</h2>

    <div class="card p-3 mb-4">
        <h4>📄 User Details</h4>
        <p><strong>👤 Name:</strong> ${user.userName}</p>
        <p><strong>📧 Email:</strong> ${user.email}</p>
        <p><strong>🔰 Role:</strong> ${user.role}</p>
        <p><strong>🔵 Status:</strong> ${user.status == 1 ? "✅ Active" : "⛔ Blocked"}</p>
        <a href="manage-users" class="btn btn-primary">⬅️ Back to Users</a>
    </div>

    <div class="card p-3 mb-4">
        <h4>📦 Order History</h4>
        <table class="table table-bordered" id="orderTable">
            <thead>
                <tr>
                    <th>🆔 Order ID</th>
                    <th>💰 Total Amount</th>
                    <th>🚀 Status</th>
                    <th>📅 Order Date</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>${order.orderId}</td>
                        <td class="order-amount">₹${order.totalAmount}</td>
                        <td class="order-status">${order.orderStatus}</td>
                        <td>${order.orderDate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <p><strong>💸 Total Amount Spent on Delivered Orders:</strong> ₹<span id="totalSpent">0</span></p>
    </div>

    <div class="card p-3 mb-4">
        <h4>📝 Feedback</h4>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>🆔 Product ID</th>
                    <th>⭐ Rating</th>
                    <th>💬 Comment</th>
                    <th>📅 Date</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="feedback" items="${feedbacks}">
                    <tr>
                        <td>${feedback.productId}</td>
                        <td>⭐ ${feedback.rating}</td>
                        <td>${feedback.comment}</td>
                        <td>${feedback.feedbackDate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    $(document).ready(function () {
        let totalSpent = 0;
        $("#orderTable tbody tr").each(function () {
            let status = $(this).find(".order-status").text().trim(); 
            let amount = parseFloat($(this).find(".order-amount").text().trim().replace("₹", "")); 
            if (status.toLowerCase() === "delivered" && !isNaN(amount)) {
                totalSpent += amount;
            }
        });
        $("#totalSpent").text(totalSpent.toFixed(2));
    });
</script>

</body>
</html>
