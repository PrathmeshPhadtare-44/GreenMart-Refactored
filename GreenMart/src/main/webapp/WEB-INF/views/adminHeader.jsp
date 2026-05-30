<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>🌿 GreenMart Admin | Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css">
    <link href="<c:url value='/resources/css/UserFooter.css'/>" rel="stylesheet">
    
    <style>
        .navbar {
            background-color: #2c3e50 !important;
        }

        .navbar-brand {
            font-weight: bold;
            color: #1abc9c !important; 
        }

        .nav-link {
            color: #ecf0f1 !important; 
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .nav-link:hover {
            color: #1abc9c !important; 
        }

        .dropdown-menu {
            background-color: #34495e; 
            border: none;
        }

        .dropdown-item:hover {
            background-color: #1abc9c !important; 
        }

        .admin-info {
            color: #ecf0f1;
            font-size: 14px;
            margin-right: 10px;
        }

        .btn-logout {
            border-color: #e74c3c;
            color: #e74c3c;
            transition: all 0.3s ease;
        }

        .btn-logout:hover {
            background-color: #e74c3c;
            color: white;
        }
    </style>
</head>

<nav class="navbar navbar-expand-lg navbar-dark shadow  "><!-- fixed-top md-5 -->
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/panel">
            🌿 GreenMart Admin
        </a>
        
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#adminNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="adminNavbar">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/panel">
                        🏠 Dashboard
                    </a>
                </li>
                

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="manageDropdown" role="button" data-toggle="dropdown">
                        ⚙️ Manage
                    </a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/products">📦 Products</a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/categories">📂 Categories</a>
                    </div>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/orders">📜 Orders</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/manage-users">👥 Users</a>
                </li>
                <li class="nav-item">
   					 <a class="nav-link" href="${pageContext.request.contextPath}/" target="_blank" rel="noopener noreferrer">🌿 Users Side</a>
				</li>

            </ul>

            <ul class="navbar-nav ml-auto">
                <li class="nav-item d-flex align-items-center">
                    <span class="admin-info">
                        <c:if test="${not empty sessionScope.adminName}">
                            <i class="fas fa-user-circle"></i> Hello, ${sessionScope.adminName} 👋
                        </c:if>
                        <c:if test="${empty sessionScope.adminName}">
                            <i class="fas fa-user-circle"></i> Hello, Admin 👋
                        </c:if>
                    </span>
                    <a href="${pageContext.request.contextPath}/admin/logout" class="btn btn-outline-danger btn-sm btn-logout">
                        🚪 Logout
                    </a>
                </li>
               
            </ul>
        </div>
    </div>
</nav>

<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
