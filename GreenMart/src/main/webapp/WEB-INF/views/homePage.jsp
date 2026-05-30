<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="userHeader.jsp" %>
<%-- <%@ include file="loadingLayout.jsp" %>
 --%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>🌿 GreenMart | Browse Products</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5fdf0;
        }

        .container {
            max-width: 1200px;
            margin: auto;
            padding: 20px;
        }

        h2 {
            text-align: center;
            color: #2c3e50;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .product-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            padding: 20px 0;
        }

        .product-card {
        
            background: white;
            border-radius: 12px;
            padding: 15px;
            text-align: center;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        }

        .product-card img {
            width: 250px;
            height: 250px;
            object-fit: cover;
            border-radius: 12px;
            transition: transform 0.3s ease;
            object-fit: cover;
        }

        .product-card img:hover {
            transform: scale(1.05);
        }

        .product-title {
            font-size: 16px;
            font-weight: bold;
            color: #333;
            margin-top: 10px;
        }

        .product-price {
            font-size: 18px;
            color: #27ae60;
            font-weight: bold;
            margin: 5px 0;
        }

        /* Add to Cart Button */
        .add-to-cart {
            display: inline-block;
            background-color: #1abc9c;
            color: white;
            text-decoration: none;
            padding: 10px 20px;
            border-radius: 8px;
            font-size: 15px;
            font-weight: bold;
            margin-top: 10px;
            transition: background 0.3s ease, transform 0.2s ease;
            box-shadow: 0 3px 6px rgba(0, 0, 0, 0.15);
        }

        .add-to-cart:hover {
            background-color: #16a085;
            transform: scale(1.05);
        }

        /* Buy Now Button */
        .buy-now {
            display: inline-block;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            padding: 10px 20px;
            border-radius: 8px;
            font-size: 15px;
            font-weight: bold;
            margin-top: 10px;
            transition: background 0.3s ease, transform 0.2s ease;
            box-shadow: 0 3px 6px rgba(0, 0, 0, 0.15);
        }

        .buy-now:hover {
            background-color: #0056b3;
            transform: scale(1.05);
        }
    </style>
</head>
<script>
function filterProducts() {
    let input = document.getElementById("searchBox").value.toLowerCase();
    let productCards = document.querySelectorAll(".product-card");

    productCards.forEach(card => {
        let title = card.querySelector(".product-title").innerText.toLowerCase();

        let matches = title.includes(input);

        if (matches) {
            card.style.display = "block"; 
        } else {
            card.style.display = "none"; 
        }
    });
}



</script>

<body>
    <div class="container">
        <h2>🌿 Browse Products</h2>
		<input type="text" id="searchBox" placeholder="Search Products..." 
       onkeyup="filterProducts()" 
       style="width: 100%; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #ccc;">
		
        <div class="product-grid">
            <c:forEach var="product" items="${products}">
                <div class="product-card">
    <a href="${pageContext.request.contextPath}/user/productDetail?productId=${product.productId}"> 
        <img src="${pageContext.request.contextPath}${product.image}" alt="${product.productName}">
<h5 class="product-title">🌿 ${product.productName} <br> 
    <span style="font-size: 14px; color: #666;">(${product.categoryName})</span>
</h5>
        <p class="product-price">💰 ₹${product.price}</p>

        <p>📦 Stock: ${product.stock > 0 ? product.stock : 'Out of Stock 🚫'}</p>
    </a>
    
    <!-- Add to Cart Button (Fixed unclosed <a> tag) -->
    <a href="${pageContext.request.contextPath}/user/addToCart?productId=${product.productId}" 
       class="add-to-cart" onclick="event.stopPropagation();">
       🛒 Add to Cart
    </a> 

    <%--  
        <a href="${pageContext.request.contextPath}/user/addToCart?productId=${product.productId}" class="add-to-cart">🛒 Add to Cart</a>
    --%>
    
    <%--  
        <a href="${pageContext.request.contextPath}/user/buynow?productId=${product.productId}" class="add-to-cart">Buy Now</a>
    --%>
</div>

            </c:forEach>
        </div>
    </div>
</body>

<%@ include file="userFooter.jsp" %>
</html>
