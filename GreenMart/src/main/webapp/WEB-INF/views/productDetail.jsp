<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="userHeader.jsp" %>
<%@ include file="loadingLayout.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${productDetails.productName} - Product Details</title>
</head>
<body class="bg-light">

<div class="container py-1 ">
    <div class="card shadow-lg p-4">
        <div class="row g-4">
            <div class="col-md-5 text-center">
                <img src="${pageContext.request.contextPath}${productDetails.image}" alt="${productDetails.productName}" class="img-fluid rounded shadow-sm">
            </div>

            <div class="col-md-7">
                <h2 class="fw-bold">🛍️ ${productDetails.productName}</h2>
                <p><strong>📂 Category:</strong> <span class="badge bg-secondary">${productDetails.categoryName}</span></p>
                <p><strong>📝 Description:</strong> ${productDetails.description}</p>
                
                <h4 class="text-danger fw-bold">💰 ₹${productDetails.price}</h4>
                
                <p class="fw-semibold ${productDetails.stock > 0 ? 'text-success' : 'text-danger'}">
                    <strong>📦 Stock:</strong> ${productDetails.stock > 0 ? "In Stock ✅" : "Out of Stock ❌"}
                </p>

                <p class="fw-semibold">
                    <strong>⚡ Status:</strong> 
                    <span class="badge ${productDetails.status == 1 ? 'bg-success' : 'bg-secondary'}">
                        ${productDetails.status == 1 ? "Available ✅" : "Not Available ❌"}
                    </span>
                </p>

                <p class="fw-semibold">
                    <strong>⭐ Average Rating:</strong> 
                    <span class="badge bg-warning text-dark">${productDetails.averageRating} ★</span>
                </p>

                <c:choose>
                    <c:when test="${productDetails.stock > 0}">
                        <a href="${pageContext.request.contextPath}/user/addToCartFromProductDetails?productId=${productDetails.productId}" class="btn btn-warning btn-lg w-100">
                            🛒 Add to Cart
                        </a>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-secondary btn-lg w-100" disabled>❌ Out of Stock</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <hr>

    <div class="mt-4">
        <h4>💬 Customer Reviews</h4>

        <c:choose>
            <c:when test="${not empty productDetails.userNames}">
                <ul class="list-group">
                    <c:forEach var="index" begin="0" end="${fn:length(productDetails.userNames) - 1}">
                        <li class="list-group-item">
                            <strong>👤 ${productDetails.userNames[index]}</strong> 
                            <span class="badge bg-primary">${productDetails.ratings[index]} ★</span>
                            <p class="mb-0">📝 ${productDetails.comments[index]}</p>
                        </li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <p class="text-muted">🚫 No reviews yet.</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="userFooter.jsp" %>
<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
 -->
</body>
</html>
