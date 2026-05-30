<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.sql.*, javax.sql.*, java.util.*, com.greenmart.db.DBConfig" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body style="background-color: #f4f8f2; font-family: 'Arial', sans-serif;">
    <div class="container" style="background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);">
        <h2 style="color: #2e8b57; text-align: center;">Add New Product</h2>
        <form:form action="saveNewProduct" method="POST" enctype="multipart/form-data">
            <table class="table table-bordered" style="width: 100%; margin-top: 20px;">
                <tr>
                    <td style="background-color: #eaf1eb; color: #2e8b57;"><label for="productName">Product Name:</label></td>
                    <td><input type="text" class="form-control" id="productName" name="productName" required /></td>
                </tr>
                <tr>
                    <td style="background-color: #eaf1eb; color: #2e8b57;"><label for="description">Description:</label></td>
                    <td><input type="text" class="form-control" id="description" name="description" required /></td>
                </tr>
                <tr>
                    <td style="background-color: #eaf1eb; color: #2e8b57;"><label for="price">Price:</label></td>
                    <td><input type="number" class="form-control" id="price" name="price" required /></td>
                </tr>
                <tr>
                    <td style="background-color: #eaf1eb; color: #2e8b57;"><label for="stock">Stock:</label></td>
                    <td><input type="number" class="form-control" id="stock" name="stock" required /></td>
                </tr>
                <tr>
                    <td style="background-color: #eaf1eb; color: #2e8b57;"><label for="status">Status:</label></td>
                    <td>
                        <select class="form-control" id="status" name="status">
                            <option value="1">Active</option>
                            <option value="0">Inactive</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="background-color: #eaf1eb; color: #2e8b57;"><label for="categoryId">Category:</label></td>
                    <td>
                      <select name="categoryId" id="category" class="form-control" required>
    <option value="" disabled selected>Select a Category</option>
    <c:forEach items="${categories}" var="category">
        <option value="${category.categoryId}">
            ${category.categoryName}
        </option>
    </c:forEach>
</select>

                    </td>
                </tr>
                <tr>
                    <td style="background-color: #eaf1eb; color: #2e8b57;"><label for="image">Image:</label></td>
                    <td>
                        <input type="file" class="form-control-file" id="image" name="image" accept="image/*" required/>
                        <br>
                        <img id="imagePreview" src="#" alt="Image Preview" style="display: none; width: 200px; height: auto; margin-top: 10px;"/>
                    </td>
                </tr>
            </table>
            <button type="submit" class="btn btn-success" style="margin-top: 20px; width: 100%;">Save Product</button>
        </form:form>
        <a href="products" class="btn btn-secondary mt-3" style="margin-top: 10px; width: 100%;">Back to Product List</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#image').change(function(e) {
                var reader = new FileReader();
                reader.onload = function(event) {
                    $('#imagePreview').attr('src', event.target.result);
                    $('#imagePreview').show();
                };
                reader.readAsDataURL(e.target.files[0]);
            });
        });
    </script>
</body>
</html>
