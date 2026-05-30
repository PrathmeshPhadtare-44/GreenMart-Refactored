<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="userHeader.jsp" %>
<%@ include file="loadingLayout.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>🛒 View Cart - GreenMart</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body style="background-color: #f5fdf0; font-family: Arial, sans-serif;">

    <div class="container mt-5">
        <div class="cart-header">
            👋 Welcome, <b>${sessionScope.userName}</b>!
        </div>
        <h2>🛍️ Your Cart</h2>
        <form action="${pageContext.request.contextPath}/user/updateCart" method="post">
            <table class="table table-bordered table-striped">
                <thead style="background-color: #81c784; color: #ffffff;">
                    <tr>
                        <th>📷 Product Image</th>
                        <th>📦 Product Name</th>
                        <th>💰 Single Price</th>
                        <th>🔢 Quantity</th>
                        <th>🛒 Total Price</th>
                        <th>🗑️ Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cartItems}">
                        <tr>
                            <td>
                                <img src="${pageContext.request.contextPath}${item.image}" alt="${item.productName}" style="width: 50px; height: 50px; object-fit: cover;">
                            </td>
                            <td>${item.productName}</td>
                            <td>💵 &#8377; ${item.singlePrice}</td>
                            <td>
                                <div class="input-group">
                                    <button class="btn btn-secondary decrease-btn" type="submit" name="action" value="decrease-${item.productId}" ${item.quantity == 1 ? 'disabled' : ''}>➖</button>
                                    <input type="text" class="form-control" value="${item.quantity}" readonly>
                                    <button class="btn btn-secondary increase-btn" type="submit" name="action" value="increase-${item.productId}" ${item.quantity == item.stock || item.stock == 0 ? 'disabled' : ''}>➕</button>
                                </div>
                            </td>
                            <td>💵 &#8377; ${item.totalPrice}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/user/removeItem?productId=${item.productId}&quantity=${item.quantity}" class="btn btn-danger" onclick="return confirm('❌ Are you sure you want to remove this product from the cart?');">🗑️ Remove</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
                <c:if test="${not empty totalAmount}">
                    <tfoot>
                        <tr style="font-weight: bold; background-color: #f1f8e9;">
                            <td colspan="4" style="text-align: right;">💳 Total Price:</td>
                            <td>💵 &#8377; <c:out value="${totalAmount}" /></td>
                            <td></td>
                        </tr>
                    </tfoot>
                </c:if>
            </table>

            <c:if test="${empty cartItems}">
                <div class="alert alert-warning" role="alert">
                    ⚠️ Your cart is empty! Please add some items before proceeding to checkout.
                </div>
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary">🛍️ Continue Shopping</a>
                <button type="submit" class="btn btn-success" disabled>🚀 Checkout</button>
            </c:if>

            <c:if test="${not empty cartItems}">
                <div class="checkout-section">
                    <a href="${pageContext.request.contextPath}/" class="btn btn-primary">🛍️ Continue Shopping</a>
                    <a href="${pageContext.request.contextPath}/user/checkout" class="btn btn-success">✅ Checkout</a>
                </div>
            </c:if>
        </form>
    </div>

</body>
</html>
