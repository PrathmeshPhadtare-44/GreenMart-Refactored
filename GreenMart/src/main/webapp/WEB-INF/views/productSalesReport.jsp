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
    <title>📊 Product Sales Report - GreenMart</title>
    <style>
    .report-container {
        max-width: 100%;
        padding: 20px;
        background-color: #f5fdf0;
        font-family: Arial, sans-serif;
    }
    .report-table {
        width: 100%;
        margin: 0;
    }
    .report-table th, .report-table td {
        white-space: nowrap;
        padding: 12px;
        text-align: center;
        font-size: 14px;
    }
    </style>
</head>
<body>
    <div class="report-container mt-5">
        <h2 class="text-center">📊 Product Sales Report</h2>

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

        <form action="${pageContext.request.contextPath}/admin/reports/product-sales" method="get" class="mb-3">
            <label for="reportFilter">Filter Report:</label>
            <select class="custom-select" name="filter" id="reportFilter" style="width:18%;">
                <option value="" ${selectedFilter == '' ? 'selected' : ''}>Overall</option>  
                <option value="15_days" ${selectedFilter == '15_days' ? 'selected' : ''}>Last 15 Days</option>
                <option value="monthly" ${selectedFilter == 'monthly' ? 'selected' : ''}>This Month</option>
                <option value="yearly" ${selectedFilter == 'yearly' ? 'selected' : ''}>This Year</option>
            </select>
            <button type="submit" class="btn btn-success">Generate Report</button>
        </form>

        <div id="summaryReport">
            <div class="alert alert-info text-center">
                <h4>📊 Sales Summary</h4>
                <p><b>🔢 Total Products Sold:</b> ${totalQuantitySold}</p>
                <p><b>💰 Total Revenue:</b> ₹${totalRevenue}</p>
                <p><b>👥 Unique Customers:</b> ${totalCustomers}</p>
            </div>
        </div>			

        <table class="table table-striped table-bordered report-table">
            <thead class="thead-dark">
                <tr>
                    <th style="width: 8%;">🖼️ Image</th>
                    <th style="width: 15%;">📌 Product Name</th>
                    <th style="width: 12%;">📂 Category</th>
                    <th style="width: 10%;">🔢 Total Quantity Sold</th>
                    <th style="width: 12%;">💰 Total Revenue</th>
                    <th style="width: 12%;">💵 Avg Price per Unit</th>
                    <th style="width: 12%;">👥 Unique Customers</th>
                    <th style="width: 10%;">⭐ Avg Rating</th>
                    <th style="width: 10%;">📦 Stock</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${productSalesReport}">
                    <tr>
                        <td>
                            <img src="${pageContext.request.contextPath}${product.image}" 
                                 alt="${product.productName}" 
                                 style="width: 50px; height: 50px; object-fit: cover;">
                        </td>
                        <td>${product.productName}</td>
                        <td>${product.category}</td>
                        <td>${product.totalQuantitySold}</td>
                        <td>₹${product.totalRevenue}</td>
                        <td>₹${product.avgPricePerUnit}</td>
                        <td>${product.totalUniqueCustomers}</td>
                        <td>⭐ ${product.averageRating}</td>
                        <td>${product.currentStock}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/admin/panel" class="btn btn-primary btn-lg">🏠 Back to Admin Dashboard</a>
        </div>
    </div>
</body>
</html>
