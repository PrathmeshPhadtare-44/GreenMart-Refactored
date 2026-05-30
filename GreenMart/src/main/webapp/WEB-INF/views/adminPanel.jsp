<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="loadingLayout.jsp" %>
<%@ include file="checkAdminLogin.jsp" %>
<%@ include file="adminHeader.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel</title>

    <style>
        body {
            background-color: #f8f9fa;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        .main-content {
            flex: 1;
        }
        .dashboard-container {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
            height: 85vh;
            display: grid;
            grid-template-rows: auto 1fr;
        }
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-template-rows: repeat(2, 1fr);
            gap: 10px;
            height: 100%;
        }
        .dashboard-card {
            background: white;
            border-radius: 10px;
            box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.2);
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            text-align: center;
            padding: 10px;
            transition: transform 0.3s ease;
            height: 100%;
        }
        .dashboard-card:hover {
            transform: scale(1.05);
        }
        .dashboard-card img, .dashboard-card span {
            width: 60px;
            height: 60px;
            object-fit: contain;
        }
        .dashboard-card span {
            font-size: 1.2rem;
            font-weight: bold;
            color: #333;
            margin-top: 5px;
        }
        @media (max-width: 992px) {
            .dashboard-grid {
                grid-template-columns: repeat(2, 1fr);
                grid-template-rows: repeat(3, 1fr);
            }
        }
        @media (max-width: 768px) {
            .dashboard-grid {
                grid-template-columns: 1fr;
                grid-template-rows: auto;
            }
        }
    </style>
</head>

<body>
<div class="main-content ">
    <div class="container mt-4 ">
        <div class="dashboard-container">
            <h2 class="text-success fw-bold text-center"><i class="fas fa-tachometer-alt"></i> Admin Dashboard</h2>
            
            <div class="dashboard-grid">
                <a href="${pageContext.request.contextPath}/admin/products" class="dashboard-card">
                    <span style="font-size: 60px;">🪴</span>
                    <span>Manage Products</span>
                </a>
                <a href="${pageContext.request.contextPath}/admin/categories" class="dashboard-card">
                    <span style="font-size: 60px;">📂</span>
                    <span>Manage Categories</span>
                </a>
                <a href="${pageContext.request.contextPath}/admin/orders" class="dashboard-card">
                    <img src="https://cdn-icons-png.flaticon.com/512/3081/3081559.png" alt="Orders">
                    <span>Manage Orders</span>
                </a>
                <a href="${pageContext.request.contextPath}/admin/manage-users" class="dashboard-card">
                    <img src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png" alt="Users">
                    <span>View Users</span>
                </a>
                <a href="${pageContext.request.contextPath}/admin/reports/product-sales" class="dashboard-card">
                    <span style="font-size: 60px;">📊</span>
                    <span>View Reports</span>
                </a>
                <a href="${pageContext.request.contextPath}/admin/feedback" class="dashboard-card">
                    <span style="font-size: 60px;">💬</span>
                    <span>View Feedback</span>
                </a>
            </div>
        </div>
    </div>
</div>

<%@ include file="adminFooter.jsp" %>
</body>
</html>
