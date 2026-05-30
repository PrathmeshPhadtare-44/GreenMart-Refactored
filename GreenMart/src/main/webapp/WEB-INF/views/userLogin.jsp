<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>🌿 GreenMart | User Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #ecf0f1; /* Light Gray */
        }

        .card {
            border-radius: 12px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        }

        .card-header {
            background-color: #2c3e50; /* Dark Blue-Gray */
            color: #1abc9c; /* Green Accent */
            font-weight: bold;
            text-align: center;
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

        .toggle-password {
            cursor: pointer;
            position: absolute;
            right: 15px;
            top: 38px;
            color: gray;
        }
    </style>
</head>

<body>

<c:if test="${not empty successMessage}">
                <div class="alert alert-success text-center w-50 mx-auto mt-3">${successMessage}</div>
            </c:if>
    <c:if test="${not empty sessionScope.loginAlert}">
        <div class="alert alert-danger text-center w-50 mx-auto mt-3" role="alert">
            ⚠️ <strong>Warning!</strong> ${sessionScope.loginAlert}
        </div>
        <c:remove var="loginAlert"/>
    </c:if>
<c:if test="${not empty signupSuccess}">
    <div class="alert alert-success text-center w-50 mx-auto mt-3" role="alert">
        ✅ <strong>Success!</strong> ${signupSuccess}
    </div>    
</c:if>


    <div class="container d-flex justify-content-center align-items-center" style="height: 100vh;">
        <div class="card" style="width: 100%; max-width: 400px;">
            <div class="card-header">
                <h3>🌿 GreenMart Login</h3>
            </div>
            <div class="card-body">
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger text-center">${errorMessage}</div>
                </c:if>
                <form action="${pageContext.request.contextPath}/user/login/verification" method="post">
                    <div class="mb-3">
                        <label for="username" class="form-label">👤 Username</label>
                        <input type="text" class="form-control" id="username" name="username" value="${username}" required>
                    </div>
                    <div class="mb-3 position-relative">
                        <label for="password" class="form-label">🔑 Password</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                        <span toggle="#password" class="fa fa-eye toggle-password"></span> 
                    </div>
                        <c:if test="${not empty notpass}">
                    
                    <div class="mb-3">
                    <a href="${pageContext.request.contextPath}/user/login/forgotPassword?username=${username}" class="text-success">Forgot Password?</a>
                    </div>
                    </c:if>
                    <button type="submit" class="btn btn-login w-100">🚀 Login</button>
                </form>

                <div class="text-center mt-3">
                    <p>Don't have an account? <a href="${pageContext.request.contextPath}/user/sign_up" class="text-success">Sign up</a></p>
                    <p><a href="${pageContext.request.contextPath}/admin/login" class="text-danger fw-bold" target="_blank">🔐 Admin Login</a></p>
                    <p>                <a href="${pageContext.request.contextPath}/" class="btn btn-info w-100">🏠 Home</a>
                    </p>
                </div>
            </div>
            <div class="card-footer text-center">
            
                <small class="text-muted">© 2024 🌿 GreenMart</small>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        $(document).ready(function() {
            $(".toggle-password").click(function() {
                $(this).toggleClass("fa-eye fa-eye-slash");
                var input = $($(this).attr("toggle"));
                if (input.attr("type") == "password") {
                    input.attr("type", "text");
                } else {
                    input.attr("type", "password");
                }
            });
        });
    </script>
</body>

</html>
