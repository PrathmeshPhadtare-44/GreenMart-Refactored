<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="userHeader.jsp" %>
<%@ include file="loadingLayout.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>🛒 Checkout - GreenMart</title>
    <style>
        input[type="number"]::-webkit-outer-spin-button,
        input[type="number"]::-webkit-inner-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }
        input[type="number"] {
            -moz-appearance: textfield;
        }
        .section-title {
            background-color: #81c784;
            color: #ffffff;
            padding: 10px;
            border-radius: 5px;
            font-weight: bold;
        }
    </style>
</head>
<body style="background-color: #f5fdf0; font-family: Arial, sans-serif;">
    <div class="container mt-5">
        <h2 class="text-center">🛍️ Checkout</h2>
        <form method="post" action="${pageContext.request.contextPath}/user/placeOrder">
            <div class="row">
                <div class="col-md-6">
                    <div class="user-details mb-4">
                        <h4 class="section-title">👤 User Details</h4>
                        <div class="form-group">
                            <label for="userName">📝 Customer Name:</label>
                            <input type="text" class="form-control" id="customerName" name="customerName" required>
                        </div>

                        <div class="form-group">
                            <label for="phoneNumber">📞 Phone Number:</label>
                            <div class="input-group mt-2">
                                <span class="input-group-text">+91</span>
                                <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber"
                                    maxlength="10" pattern="\d{10}"
                                    title="Phone number must have exactly 10 digits"
                                    inputmode="numeric" required
                                    oninput="this.value = this.value.replace(/\D/g, '').substring(0,10);">
                            </div>
                        </div>
                    </div>

                    <hr>

                    <h4 class="section-title">📍 Shipping Address</h4>
                    <div class="form-group">
                        <label for="flat">🏢 Flat, House no., Building</label>
                        <input type="text" class="form-control" id="flat" name="flat" required>
                    </div>
                    <div class="form-group">
                        <label for="area">🛣️ Area, Street, Sector</label>
                        <input type="text" class="form-control" id="area" name="area" required>
                    </div>
                    <div class="form-group">
                        <label for="landmark">📍 Landmark</label>
                        <input type="text" class="form-control" id="landmark" name="landmark" required>
                    </div>
                    <div class="form-group">
                        <label for="city">🏙️ City</label>
                        <input type="text" class="form-control" id="city" name="city" required>
                    </div>
                    <div class="form-group">
                        <label for="state">🌍 State</label>
                        <input type="text" class="form-control" id="state" name="state" required>
                    </div>
                    <div class="form-group">
                        <label for="pincode">📮 Pincode</label>
                        <input type="text" class="form-control" id="pincode" name="pincode"
                               maxlength="6" pattern="^\d{6}$"
                               title="Pincode must be a 6-digit number" required>
                    </div>
                </div>

                <div class="col-md-6">
                    <h4 class="section-title">🛒 Your Cart</h4>
                    <table class="table table-bordered">
                        <thead class="thead-light">
                            <tr>
                                <th>📷 Product Image</th>
                                <th>📦 Product Name</th>
                                <th>🔢 Quantity</th>
                                <th>💰 Single Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${cartItems}">
                                <tr>
                                    <td>
                                        <img src="${pageContext.request.contextPath}${item.image}" alt="${item.productName}" 
                                             style="width: 50px; height: 50px; object-fit: cover;">
                                    </td>
                                    <td>${item.productName}</td>
                                    <td>${item.quantity}</td>
                                    <td>💵 &#8377; ${item.singlePrice}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <hr>

                    <div class="d-flex justify-content-between">
                        <h4>💳 Total Price: 💵 &#8377; ${totalAmount}</h4>
                    </div>
<div class="alert alert-info text-center">
    💵 Payment Type: <strong>Cash on Delivery (COD) Only</strong> <p>Estimated delivery:<strong> 5-7 business days</strong>.</p>
</div>
                    <div class="text-center mt-4 row">
                        <div class="col">
                            <a href="${pageContext.request.contextPath}/user/viewCart" class="btn btn-primary btn-lg w-100">🔙 Go to Cart</a>
                        </div>
                        <div class="col">
                            <button type="submit" class="btn btn-success btn-lg w-100" 
                                <c:if test="${empty cartItems}">disabled</c:if>>✅ Place Order</button>
                        </div>
                    </div>

                </div>
            </div>
        </form>
    </div>
</body>
</html>
