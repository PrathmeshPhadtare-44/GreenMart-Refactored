<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>🌿 GreenMart Admin | Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>

    <style>
        body {
            background-color: #ecf0f1; /* Light Gray Background */
        }

        .login-container {
            margin-top: 50px;
        }

        .card {
            border-radius: 12px;
        }

        .card-header {
            background-color: #2c3e50; /* Dark Blue-Gray */
            color: #1abc9c; /* Green Accent */
            font-weight: bold;
        }

        .btn-login {
            background-color: #1abc9c;
            border: none;
            transition: 0.3s;
        }

        .btn-login:hover {
            background-color: #16a085;
        }

        .alert-danger {
            background-color: #e74c3c;
            color: white;
        }
    </style>
</head>
<body>

    <!-- Error Alert -->
    <c:if test="${not empty sessionScope.loginAlert}">
        <div class="alert alert-danger text-center w-50 mx-auto mt-3" role="alert">
            ⚠️ <strong>Warning!</strong> ${sessionScope.loginAlert}
        </div>
        <c:remove var="loginAlert"/>
    </c:if>

    <div class="container login-container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-header text-center">
                        <h4>🌿 GreenMart Admin Login</h4>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger text-center">${errorMessage}</div>
                        </c:if>
                        <form action="verification" method="post">
                            <div class="mb-3">
                                <label for="adminName" class="form-label">👤 Name</label>
                                <input type="text" class="form-control" id="adminName" name="userName" placeholder="Enter your name" required>
                            </div>
                            <div class="mb-3">
                                <label for="adminEmail" class="form-label">📧 Email</label>
                                <input type="email" class="form-control" id="adminEmail" name="email" placeholder="Enter your email" required>
                            </div>
                            <div class="mb-3">
                                <label for="adminPassword" class="form-label">🔑 Password</label>
                                <input type="password" class="form-control" id="adminPassword" name="password" placeholder="Enter your password" required>
                            </div>
                            <div class="d-grid">
                                <button type="submit" class="btn btn-login">🚀 Login</button>
                            </div>
                        </form>
                    </div>
                    <div class="card-footer text-center">
                                        <p>                <a href="${pageContext.request.contextPath}/" class="btn btn-info w-100">🏠 Home</a>
                    
                        <small class="text-muted">© 2024 🌿 GreenMart Admin Panel</small>
                    </div>
                </div>
            </div>
        </div>
    </div>

<script src="<c:url value='/resources/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
</body>
</html>
