<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="adminHeader.jsp" %>
<%@ include file="loadingLayout.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="checkAdminLogin.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>📦 Admin Orders - GreenMart</title>
<!--     <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
 --></head>
<body style="background-color: #f5fdf0; font-family: Arial, sans-serif;">
    <div class="container mt-5">
        <h2 class="text-center">📋 Admin - Manage Orders</h2>

        <%-- <c:if test="${not empty success}">
            <div class="alert alert-success" role="alert">
                ✅ ${success}
            </div>
        </c:if> --%>

        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                ❌ ${error}
            </div>
        </c:if>

        <h4 class="mt-4">🛒 All Orders</h4>

        <c:forEach var="order" items="${orders}">
            <div class="order-table mb-5 p-3 border">
                <h5>🆔 Order #${order.orderId} - 💰 &#8377; ${order.totalAmount}</h5>
                <p><strong>👤 Customer:</strong> ${order.username}</p>
				<p><strong> 🏠 Address:</strong> ${order.address}</p>
			<p><strong>📅 Order Date & Time:</strong> ${order.orderDate}</p>
                <p><strong>🚚 Status:</strong> 
    <span class="badge">
        <c:choose>
            <c:when test="${order.status == 'delivered'}">
                ✅ Delivered
            </c:when>
            <c:when test="${order.status == 'shipped'}">
                📦 Shipped
            </c:when>
            <c:when test="${order.status == 'processing'}">
                ⏳ Processing
            </c:when>
            <c:when test="${order.status == 'canceled'}">
                ❌ Cancelled
            </c:when>
            <c:otherwise>
                🔴 Unknown
            </c:otherwise>
        </c:choose>
    </span>
</p>


		<c:if test="${order.status != 'canceled'}">
                <form action="${pageContext.request.contextPath}/admin/updateOrderStatus" method="post">
                    <input type="hidden" name="orderId" value="${order.orderId}">
                    <label for="status-${order.orderId}">🔄 Change Status:</label>
                    <select name="status" id="status-${order.orderId}" class="form-control d-inline-block w-auto">
                        <option value="processing" ${order.status == 'processing' ? 'selected' : ''}>⏳ Processing</option>
                        <option value="shipped" ${order.status == 'shipped' ? 'selected' : ''}>📦 Shipped</option>
                        <option value="delivered" ${order.status == 'delivered' ? 'selected' : ''}>✅ Delivered</option>
                    </select>
                    <button type="submit" class="btn btn-primary btn-sm ml-2">🔁 Update</button>
                </form>
                </c:if>

                <!-- Show Delete Button if Order is Cancelled -->
                <c:if test="${order.status == 'canceled'}">
                    <form action="${pageContext.request.contextPath}/admin/orders/delete" method="post"
                          onsubmit="return confirm('⚠️ Are you sure you want to delete this order? This action cannot be undone.');">
                        <input type="hidden" name="orderId" value="${order.orderId}">
                        <button type="submit" class="btn btn-danger mt-3">🗑️ Delete Order</button>
                    </form>
                </c:if>

                <table class="table table-striped table-bordered mt-4">
                    <thead>
                        <tr>
                            <th>🖼️ Image</th>
                            <th>📌 Product Name</th>
                            <th>🔢 Quantity</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${order.items}">
                            <tr>
                                <td>
                                    <img src="${pageContext.request.contextPath}${item.image}" 
                                         alt="${item.productName}" 
                                         style="width: 50px; height: 50px; object-fit: cover;">
                                </td>
                                <td>${item.productName}</td>
                                <td> ${item.quantity}</td>
                            </tr>
                           
                        </c:forEach>
                    </tbody>
                </table>
                <p><strong>💵 Total Amount:</strong>  ${order.totalAmount}</p>
               
                 
                
            </div>
        </c:forEach>

        <div class="text-center">
            <a href="${pageContext.request.contextPath}/admin/panel" class="btn btn-primary btn-lg">🏠 Back to Admin Dashboard</a>
        </div>
    </div>
</body>
</html>
