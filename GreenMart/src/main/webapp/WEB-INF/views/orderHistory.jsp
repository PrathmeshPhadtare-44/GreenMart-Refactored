<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="userHeader.jsp" %>
<%@ include file="loadingLayout.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>📜 Order History - GreenMart</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        .order-card {
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            padding: 15px;
            margin-bottom: 20px;
        }
        .status-badge {
            padding: 8px 12px;
            border-radius: 5px;
            font-weight: bold;
            text-transform: capitalize;
        }
        .status-processing { background-color: #ffc107; color: #000; }
        .status-shipped { background-color: #007bff; color: #fff; }
        .status-delivered { background-color: #28a745; color: #fff; }
        .status-cancelled { background-color: #6c757d; color: #fff; }
    </style>
</head>
<body style="background-color: #f5fdf0; font-family: Arial, sans-serif;">
    <div class="container mt-5">
        <h2 class="text-center">📜 Order History</h2>

       

        <h4 class="mt-4">🛍️ Your Orders</h4>

        <c:choose>
            <c:when test="${empty orders}">
                <p class="text-center text-muted mt-4">You have no orders yet! Start shopping now. 🛒</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="order" items="${orders}">
                    <div class="order-card">
                        <h5>📦 Order #${order.orderId} - 💰 &#8377; ${order.totalAmount}</h5>
                        <p><strong>📅 Order Date:</strong> ${order.orderDate}</p>
                        <p><strong>🚚 Status:</strong> 
                            <span class="status-badge 
                                ${order.orderStatus == 'delivered' ? 'status-delivered' : 
                                  order.orderStatus == 'shipped' ? 'status-shipped' : 
                                  order.orderStatus == 'processing' ? 'status-processing' : 'status-cancelled'}">
                                ${order.orderStatus}
                            </span>
                        </p>
                       
                        
    					<p ><strong>💵 Payment Type: Cash on Delivery (COD) Only Estimated delivery: 5-7 business days.</strong></p>
						
						<p><strong>📍 Shipping Address:</strong> ${order.address}</p>
                        
<%--                         <p><strong>📍 Shipping Address:</strong> ${order.address}</p>
 --%>	
 	    <c:if test="${order.orderStatus ne 'canceled'}">
    <a href="${pageContext.request.contextPath}/user/orders/${order.orderId}/download" 
       class="btn btn-success mt-2">
        📥 Download Bill
    </a>
</c:if>

                       <c:if test="${order.orderStatus == 'processing'}">
    <form action="${pageContext.request.contextPath}/user/cancelOrder" method="post"
          onsubmit="return confirm('⚠️ Are you sure you want to cancel this order? This action cannot be undone.');">
        <input type="hidden" name="orderId" value="${order.orderId}">

        <c:forEach var="item" items="${order.items}">
            <input type="hidden" name="products" value="${item.productId}-${item.quantity}">
        </c:forEach>

        <button type="submit" class="btn btn-danger">❌ Cancel Order</button>
    </form>

    <p class="text-muted">🚫 Order cancellation is only available while the order is in 'Processing' status.</p>
</c:if>
                 


                        <table class="table table-striped table-bordered mt-4">
                            <thead class="thead-light">
                                <tr>
                                    <th>📷 Product</th>
                                    <th>📦 Name</th>
                                    <th>🔢 Quantity</th>
                                    <th>💵 Price</th>
                                    <th>💰 Total</th>
                                    <th>⭐ Feedback</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${order.items}">
                                    <tr>
                                        <td><img src="${pageContext.request.contextPath}${item.image}" 
                                                 alt="${item.productName}" 
                                                 style="width: 50px; height: 50px; object-fit: cover;"></td>
                                        <td>${item.productName}</td>
                                        <td>${item.quantity}</td>
                                        <td>💵 &#8377; ${item.price}</td>
                                        <td>💰 &#8377; ${item.totalPrice}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty item.comment}">
                                                    <span>⭐ ${item.rating}/5</span><br>
                                                    <span>💬 ${item.comment}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:if test="${order.orderStatus == 'delivered'}">
                                                        <button class="btn btn-primary btn-sm feedback-btn" 
                                                                data-productid="${item.productId}" 
                                                                data-productname="${item.productName}"
                                                                data-orderid="${order.orderId}">
                                                            ✍️ Give Feedback
                                                        </button>
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary btn-lg">🏠 Back to Home</a>
        </div>
    </div>

    <!-- Feedback Modal -->
    <div class="modal fade" id="feedbackModal" tabindex="-1" aria-labelledby="feedbackModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/user/orders/feedback" method="post">
                    <div class="modal-header">
                        <h5 class="modal-title" id="feedbackModalLabel">✍️ Submit Feedback</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" name="productId" id="productId">
                        <input type="hidden" name="orderId" id="orderId">
                        <label>📦 Product:</label>
                        <input type="text" id="productName" class="form-control" readonly>
                        <label>⭐ Rating:</label>
                        <select name="rating" class="form-control">
                            <option value="5">⭐⭐⭐⭐⭐ - Excellent</option>
                            <option value="4">⭐⭐⭐⭐ - Good</option>
                            <option value="3">⭐⭐⭐ - Average</option>
                            <option value="2">⭐⭐ - Poor</option>
                            <option value="1">⭐ - Very Bad</option>
                        </select>
                        <label>💬 Comment:</label>
<textarea name="comment" class="form-control" required></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">✅ Submit</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">❌ Close</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        $(".feedback-btn").click(function() {
            $("#productId").val($(this).data("productid"));
            $("#orderId").val($(this).data("orderid"));
            $("#productName").val($(this).data("productname"));
            $("#feedbackModal").modal("show");
        });
    </script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>

</body>
</html>
