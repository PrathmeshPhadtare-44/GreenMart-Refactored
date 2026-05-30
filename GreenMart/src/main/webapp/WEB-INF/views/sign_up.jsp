<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>🌿 GreenMart | Registration & Email Verification</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body {
            background-color: #ecf0f1;
        }

        .card {
            border-radius: 12px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        }

        .card-header {
            background-color: #2c3e50;
            color: #1abc9c;
            font-weight: bold;
            text-align: center;
        }

        .btn-register, .btn-verify {
            background-color: #1abc9c;
            border: none;
            transition: 0.3s;
        }

        .btn-register:hover, .btn-verify:hover {
            background-color: #16a085;
        }

        .alert-danger {
            background-color: #e74c3c;
            color: white;
        }

        .timer {
            font-size: 16px;
            color: #e74c3c;
        }

        .field-icon {
            cursor: pointer;
            position: absolute;
            right: 15px;
            top: 38px;
            color: gray;
        }
    </style>
</head>

<body>

    <div class="container d-flex justify-content-center align-items-center" style="height: 100vh;">
        <div class="card" style="width: 100%; max-width: 500px;">
            <div class="card-header">
                <h3>🌿 GreenMart Registration & Verification</h3>
            </div>

            <div class="card-body">

                <c:choose>
                    <c:when test="${empty verificationCodeSent}">
                        <form action="${pageContext.request.contextPath}/user/sign_up_data" method="post" id="registrationForm">
                            <c:if test="${not empty errorMessage}">
                                <div class="alert alert-danger" role="alert">
                                    ${errorMessage}
                                </div>
                            </c:if>
                            <div class="mb-3">
    							<label for="user_name" class="form-label">👤 Username</label>
   										 <input type="text" class="form-control" id="user_name" name="user_name" 
        						   required 
         							  pattern="^\S+$" title="Username cannot contain spaces">
									</div>

                            <div class="mb-3 position-relative">
                                <label for="password" class="form-label">🔑 Password</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                                <span toggle="#password" class="fa fa-eye field-icon toggle-password"></span>
                            </div>
                            <div class="mb-3 position-relative">
                                <label for="confirm_password" class="form-label">🔁 Confirm Password</label>
                                <input type="password" class="form-control" id="confirm_password" name="confirm_password" required>
                                <span toggle="#confirm_password" class="fa fa-eye field-icon toggle-password"></span>
                                <small id="passwordHelp" class="form-text text-danger" style="display:none;">⚠️ Passwords do not match</small>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">📧 Email</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                                <small class="form-text text-muted">Your email will be used for communication only, not for login.</small>
                            </div>
                            <button type="submit" class="btn btn-register w-100">✅ Register</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                                          <form action="${pageContext.request.contextPath}/user/verify_code" method="post" id="verificationForm">
                           <c:if test="${not empty errorMessage}">
                               <div class="alert alert-danger" role="alert">
                                   ${errorMessage}
                               </div>
                           </c:if>
                           <div class="mb-3">
                               <label for="verificationCode" class="form-label">Enter Verification Code</label>
                               <input type="text" class="form-control" id="verificationCode" name="verificationCode" required pattern="\d{6}" title="Please enter a 6-digit code">
                           </div>
                           <button type="submit" class="btn btn-verify w-100" id="verifyBtn">Verify Code</button>
                           <div class="timer text-center mt-2" id="timer">Time left: <span id="timeLeft">04:00</span></div>
                       </form>
                   
                       <div class="text-center mt-3">
                           <form action="${pageContext.request.contextPath}/user/resend_otp" method="post">
                                   <button type="submit" class="btn btn-warning w-100" id="resendOtpBtn">🔄 Resend OTP</button>
                               </form>
                           <a href="${pageContext.request.contextPath}/user/sign_up" class="btn btn-info w-100 mt-2">🔙 Go to Signup</a>
                       </div>
                   </c:otherwise>
 
                </c:choose>

                <div class="text-center mt-3">
                    <c:if test="${empty verificationCodeSent}">
                        <p>Already have an account? <a href="${pageContext.request.contextPath}/user/login" class="text-success">Login</a></p>
                    </c:if>
                </div>

            </div>
            <div class="card-footer text-center">
    <a href="${pageContext.request.contextPath}/" class="btn btn-info w-100">🏠 Home</a>
    <br>
    <small class="text-muted">© 2024 🌿 GreenMart</small>
</div>

        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#confirm_password').on('keyup', function() {
                var password = $('#password').val();
                var confirmPassword = $('#confirm_password').val();

                if (password !== confirmPassword) {
                    $('#passwordHelp').show();
                    $('#registrationForm button').prop('disabled', true);
                } else {
                    $('#passwordHelp').hide();
                    $('#registrationForm button').prop('disabled', false);
                }
            });

            $(".toggle-password").click(function() {
                $(this).toggleClass("fa-eye fa-eye-slash");
                var input = $($(this).attr("toggle"));
                var inputType = input.attr("type") === "password" ? "text" : "password";
                input.attr("type", inputType);
            });


            var countdown = 240;
            var timerInterval;

            function updateTimer() {
                var minutes = Math.floor(countdown / 60);
                var seconds = countdown % 60;
                $('#timeLeft').text(padZero(minutes) + ":" + padZero(seconds));

                if (countdown <= 0) {
                    clearInterval(timerInterval);
                    $('#verificationCode').prop('disabled', true);
                    $('#verifyBtn').prop('disabled', true);
                    $('#timer').text('Time is up!');
                    $('#resendOtpBtn').show();
                } else {
                    countdown--;
                }
            }

            function padZero(number) {
                return number < 10 ? '0' + number : number;
            }

            timerInterval = setInterval(updateTimer, 1000);

            $('#verificationForm').on('submit', function (e) {
                if ($('#verificationCode').prop('disabled')) {
                    e.preventDefault();
                }
            });
        });
        $(document).ready(function() {
            $("#user_name").on("input", function() {
                $(this).val($(this).val().replace(/\s/g, ''));
            });
        });
        $('#resendOtpBtn').hide();
    </script>
</body>

</html>
