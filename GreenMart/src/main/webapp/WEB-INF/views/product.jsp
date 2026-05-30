<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="adminHeader.jsp" %>
<%@ include file="checkAdminLogin.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>🛍️ Manage Products</title>
    <style>
        .product-card-wrapper {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            justify-content: space-evenly;
        }

        .product-card {
            width: calc(25% - 15px);
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
        }

        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
        }

        .product-card img {
            width: 250px;
            height: 200px;
            object-fit: cover;
            border-top-left-radius: 8px;
            border-top-right-radius: 8px;
        }

        .product-card-body {
            padding: 1.25rem;
            color: #333;
        }

        .product-card-title {
            font-size: 1.1rem;
            font-weight: 600;
            color: #007bff;
        }

        .product-card-text {
            font-size: 1rem;
            color: #6c757d;
            margin-bottom: 1.5rem;
        }

        .product-btn-wrapper {
            display: flex;
            justify-content: space-between;
            margin-top: 10px;
            margin-bottom: 5px;
        }

        .product-btn {
            width: 48%;
            padding: 5px 10px;
        }

        @media (max-width: 1200px) {
            .product-card {
                width: calc(25% - 15px);
            }
        }

        @media (max-width: 992px) {
            .product-card {
                width: calc(33.33% - 15px);
            }
        }

        @media (max-width: 768px) {
            .product-card {
                width: calc(50% - 15px);
            }
        }

        @media (max-width: 576px) {
            .product-card {
                width: 100%;
            }
        }
    </style>
    <script>
        function confirmDelete() {
            return confirm("⚠️ Are you sure you want to delete this product? This action cannot be undone!");
        }

        function filterProducts() {
            let input = document.getElementById("searchBox").value.toLowerCase();
            let productCards = document.querySelectorAll(".product-card");

            productCards.forEach(card => {
                let title = card.querySelector(".product-card-title").innerText.toLowerCase();
                let matches = title.includes(input);
                card.style.display = matches ? "block" : "none";
            });
        }
    </script>
</head>
<body class="d-flex flex-column">
<c:if test="${not empty errorMessage}">
    <div class="alert alert-warning" role="alert">
        ${successMessage}
    </div>
</c:if>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-warning" role="alert">
        ${errorMessage}
    </div>
</c:if>

    <div class="container mt-5">
        <h2 class="mb-4">🛍️ Manage Products</h2>
        <input type="text" id="searchBox" placeholder="Search Products..." 
       onkeyup="filterProducts()" 
       style="width: 100%; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #ccc;">
		
        <a href="addProduct" class="btn btn-primary mb-3">➕ Add New Product</a>

        <div class="product-card-wrapper">
            <c:forEach var="product" items="${products}">
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}${product.image}" alt="Product Image">
                    <div class="product-card-body">
                        <h5 class="product-card-title">📦 ${product.productName} <br> 
    <span style="font-size: 14px; color: #666;">(${product.categoryName})</span></h5>
                        <p class="product-card-text">
                            <strong>💰 Price:</strong> ₹${product.price}<br>
                            <strong>📝 Description:</strong> ${product.description}<br>
                            <strong>📦 Stock:</strong> ${product.stock}<br>
                            <strong>🏷️ Category:</strong> ${product.categoryName}<br>
                            <strong>🔴 Status:</strong> ${product.status == 1 ? "✅ Active" : "❌ Inactive"}
                        </p>
                        <div class="product-btn-wrapper">
                            <a href="editProductAndSave?productId=${product.productId}" class="btn btn-warning product-btn">✏️ Edit</a>
                            <a href="deleteProduct?productId=${product.productId}" class="btn btn-danger product-btn" onclick="return confirmDelete()">🗑️ Delete</a>
                            <a href="enabledisableProduct?productId=${product.productId}" 
                               class="btn ${product.status == 1 ? 'btn-primary' : 'btn-secondary'} product-btn">
                                ${product.status == 0 ? "🚫 Unavailable" : "✅ Available"}
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
<%@ include file="adminFooter.jsp" %>
