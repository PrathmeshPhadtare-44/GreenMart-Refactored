<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - GreenMart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">
    <div class="container d-flex justify-content-center align-items-center" style="height: 100vh;">
        <div class="card text-center" style="max-width: 500px; width: 100%;">
            <div class="card-body">
                <h2 class="text-danger">Something Went Wrong</h2>
                <p class="mt-3">We couldn't process your request due to an unexpected error. Please try again later.</p>
                <p class="text-muted">If the issue persists, contact our support team at <a href="mailto:support@greenmart.com">support@greenmart.com</a>.</p>
                <a href="${pageContext.request.contextPath}/" class="btn btn-success mt-3">Return to Home</a>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
