<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="checkAdminLogin.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>✏️ Edit Product</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-warning" role="alert">
        ${errorMessage}
    </div>
</c:if>


<body style="background-color: #f4f8f2; font-family: 'Arial', sans-serif;">
    <div class="container" style="background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);">
        <h2 style="color: #2e8b57; text-align: center;">✏️ Edit Product</h2>
        <form:form method="POST" action="updateProductDetails" enctype="multipart/form-data" modelAttribute="product">
            <input type="hidden" name="productId" value="${product.productId}" />
            <table class="table table-bordered" style="width: 100%; margin-top: 20px;">
                <tr>
                    <td><label for="productName" style="color: #2e8b57;">📌 Product Name:</label></td>
                    <td><input type="text" class="form-control" id="productName" name="productName" value="${product.productName}" required /></td>
                </tr>
                <tr>
                    <td><label for="description" style="color: #2e8b57;">📝 Description:</label></td>
                    <td><textarea class="form-control" id="description" name="description" required>${product.description}</textarea></td>
                </tr>
                <tr>
                    <td><label for="price" style="color: #2e8b57;">💰 Price:</label></td>
                    <td><input type="number" class="form-control" id="price" name="price" value="${product.price}" required /></td>
                </tr>
                <tr>
                    <td><label for="stock" style="color: #2e8b57;">📦 Stock:</label></td>
                    <td><input type="number" class="form-control" id="stock" name="stock" value="${product.stock}" required /></td>
                </tr>
                <tr>
                    <td><label for="status" style="color: #2e8b57;">🔄 Status:</label></td>
                    <td>
                        <select class="form-control" id="status" name="status">
                            <option value="1" ${product.status == 1 ? 'selected' : ''}>✅ Active</option>
                            <option value="0" ${product.status == 0 ? 'selected' : ''}>❌ Inactive</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="category" style="color: #2e8b57;">📂 Category:</label></td>
                    <td>
                        <select class="form-control" id="categoryId" name="categoryId" required>
    <c:choose>
        <c:when test="${empty product.categoryId}">
            <option value="" disabled selected>Select Category</option>
        </c:when>
    </c:choose>
    <c:forEach items="${categories}" var="category">
        <option value="${category.categoryId}" 
            ${category.categoryId == product.categoryId ? 'selected="selected"' : ''}>
            ${category.categoryName}
        </option>
    </c:forEach>
</select>


                    </td>
                </tr>
                <tr>
                    <td><label for="image" style="color: #2e8b57;">🖼️ Image:</label></td>
                    <td>
                        <div class="row">
                            <div class="col-md-6">
                                <label>📷 Current Image:</label>
                                <c:if test="${product.image != null}">
                                    <img id="imagePreview" src="${pageContext.request.contextPath}${product.image}" class="img-thumbnail" style="max-width: 300px;" />
                                </c:if>
                            </div>
                            <div class="col-md-6">
                                <label>📤 New Image (optional):</label>
                                <input type="file" class="form-control-file" id="image" name="image" accept="image/*" />

                                <br>
                                <img id="newImagePreview" class="img-thumbnail" style="max-width: 300px; display: none;" />
                                <div id="imageChangeMessage" style="display: none; color: red;">
                                    <p><strong>⚠️ Notice:</strong> The image will be changed to the new one you select.</p>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
            <button type="submit" class="btn btn-success" style="width: 100%; margin-top: 20px;">💾 Save Changes</button>
        </form:form>
                <a href="products" class="btn btn-secondary mt-3" style="margin-top: 10px; width: 100%;">Back to Product List</a>
        
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        $(document).ready(function() {
            $('#image').on('change', function(event) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    $('#newImagePreview').attr('src', e.target.result).show();
                    $('#imageChangeMessage').show(); 
                }
                reader.readAsDataURL(event.target.files[0]);
            });
        });
    </script>
</body>
</html>
