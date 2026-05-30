<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forgot Password</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="container d-flex justify-content-center align-items-center" style="height: 100vh;">
    <div class="card" style="width: 100%; max-width: 500px;">
        <div class="card-header text-center">
            <h3>Forgot Password</h3>
        </div>
        <div class="card-body">
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">${errorMessage}</div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success">${successMessage}</div>
            </c:if>
            <c:if test="${empty emailVerified && empty resetAllowed}">
                <form action="${pageContext.request.contextPath}/user/login/forgotPassword/verifyEmail" method="post">
                    <input type="hidden" name="username" value="${username}">
                    <p>Email associated: <strong>${maskedEmail}</strong></p>
                    <div class="mb-3">
                        <label>Enter Email:</label>
                        <input type="email" class="form-control" name="email" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Verify Email</button>
                </form>
            </c:if>
            <c:if test="${not empty emailVerified && empty resetAllowed}">
                <form action="${pageContext.request.contextPath}/user/login/forgotPassword/verifyCode" method="post">
                    <input type="hidden" name="username" value="${username}">
                    <input type="hidden" name="actualCode" value="${verificationCode}">
                    <div class="mb-3">
                        <label>Verification Code</label>
                        <input type="text" class="form-control" name="verificationCode" id="verificationCode" required pattern="\d{6}">
                        <div class="timer" id="timer">Time left: <span id="timeLeft">04:00</span></div>
                    </div>
                    <button type="submit" class="btn btn-success w-100" id="verifyBtn">Verify Code</button>
                </form>
            </c:if>
            <c:if test="${not empty resetAllowed}">
                <form action="${pageContext.request.contextPath}/user/login/forgotPassword/resetPassword" method="post">
                    <input type="hidden" name="username" value="${username}">
                    <div class="mb-3">
                        <label>New Password</label>
                        <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                    </div>
                    <div class="mb-3">
                        <label>Confirm New Password</label>
                        <input type="password" class="form-control" id="confirmPassword" required>
                        <div id="passwordMatchMsg" class="form-text text-danger"></div>
                    </div>
                    <button type="submit" class="btn btn-warning w-100" id="resetPasswordBtn" disabled>Reset Password</button>
                </form>
            </c:if>
        </div>
        <div class="card-footer text-center">
            <a href="${pageContext.request.contextPath}/user/login" class="text-success">Back to Login</a>
        </div>
    </div>
</div>

<script>
    $(document).ready(function() {
        var countdown = 240;
        function updateTimer() {
            var minutes = Math.floor(countdown / 60);
            var seconds = countdown % 60;
            $('#timeLeft').text((minutes < 10 ? '0' : '') + minutes + ":" + (seconds < 10 ? '0' : '') + seconds);
            if (countdown <= 0) {
                clearInterval(timerInterval);
                $('#verificationCode').prop('disabled', true);
                $('#verifyBtn').prop('disabled', true);
                $('#timer').text('Time is up!');
            } else {
                countdown--;
            }
        }
        var timerInterval = setInterval(updateTimer, 1000);
        $('#newPassword, #confirmPassword').on('input', function() {
            const newPassword = $('#newPassword').val();
            const confirmPassword = $('#confirmPassword').val();
            if (newPassword !== '' && newPassword === confirmPassword) {
                $('#passwordMatchMsg').text('');
                $('#resetPasswordBtn').prop('disabled', false);
            } else {
                $('#passwordMatchMsg').text('Passwords do not match.');
                $('#resetPasswordBtn').prop('disabled', true);
            }
        });
    });
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
