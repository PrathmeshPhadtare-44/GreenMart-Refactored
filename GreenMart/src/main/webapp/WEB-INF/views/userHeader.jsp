<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GreenMart | Online Plant Nursery</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css">
    <link href="<c:url value='/resources/css/UserFooter.css'/>" rel="stylesheet">

</head>

<body>

    <!-- Top Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm ">
        <a class="navbar-brand font-weight-bold text-success" href="${pageContext.request.contextPath}/">🌿 GreenMart</a>

        <div class="ml-auto">
            <a href="${pageContext.request.contextPath}/user/orders" class="btn btn-outline-primary mr-2">📦 Orders</a>
            <a href="${pageContext.request.contextPath}/user/viewCart" class="btn btn-outline-warning mr-2">
                🛒 Cart <span class="badge badge-light">${sessionScope.cartCount}</span>
            </a>

            <c:choose>
                <c:when test="${sessionScope.logStatus == 'true'}">
                    <span class="mr-3 font-weight-bold">👤 ${sessionScope.userName}</span>
                    <a href="${pageContext.request.contextPath}/user/logout" class="btn btn-outline-danger">🚪 Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/user/login" class="btn btn-outline-success">🔑 Login</a>
                </c:otherwise>
            </c:choose>
        </div>
    </nav>

    <div class="container mt-4">
    </div>

</body>
