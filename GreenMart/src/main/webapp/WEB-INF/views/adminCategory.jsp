<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="adminHeader.jsp" %>
<%@ include file="checkAdminLogin.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>📂 Category Management</title>
<!--     <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
 --></head>
<body>
    <div class="container mt-5">
        <h1>📂 Category Management</h1>
        <button class="btn btn-success mb-3" data-toggle="modal" data-target="#addCategoryModal">➕ Add Category</button>

        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>📌 Category Name</th>
                    <th>📊 Status</th>
                    <th>⚙️ Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="category" items="${categories}">
                    <tr>
                        <td>${category.categoryName}</td>
                        <td>
                            <a href="categories/enableDisable?id=${category.categoryId}" class="btn btn-${category.status == 1 ? 'success' : 'secondary'}">
                                ${category.status == 1 ? '✅ Enabled' : '🚫 Disabled'}
                            </a>
                        </td>
                        <td>
   						 	<button class="btn btn-primary" data-toggle="modal" data-target="#editModal-${category.categoryId}">✏️ Edit</button>
    						<a href="categories/delete?id=${category.categoryId}" 
 							    class="btn btn-danger ${categoryUsageMap[category.categoryId] ? 'disabled' : ''}" 
						  	    onclick="return confirm('⚠️ Are you sure you want to delete this category?')">
   								🗑️ Delete
							</a>

						</td>

                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <c:forEach var="category" items="${categories}">
        <div class="modal fade" id="editModal-${category.categoryId}" tabindex="-1" aria-labelledby="editModalLabel-${category.categoryId}" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="categories/update" method="post">
                        <div class="modal-header">
                            <h5 class="modal-title" id="editModalLabel-${category.categoryId}">✏️ Edit Category</h5>
                            <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="categoryId" value="${category.categoryId}">
                            <div class="form-group">
                                <label for="categoryName-${category.categoryId}">📌 Category Name</label>
                                <input type="text" id="categoryName-${category.categoryId}" name="categoryName" class="form-control" value="${category.categoryName}" required>
                            </div>
                            <div class="form-group">
                                <label for="status-${category.categoryId}">📊 Status</label>
                                <select id="status-${category.categoryId}" name="status" class="form-control">
                                    <option value="1" <c:if test="${category.status == 1}">selected</c:if>>✅ Active</option>
                                    <option value="0" <c:if test="${category.status == 0}">selected</c:if>>🚫 Inactive</option>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">💾 Update Category</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        </c:forEach>

        <div class="modal fade" id="addCategoryModal" tabindex="-1" aria-labelledby="addCategoryModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="categories/addCategory" method="post">
    <div class="form-group">
        <label for="categoryName">📌 Category Name</label>
        <input type="text" id="categoryName" name="categoryName" class="form-control" required>
    </div>
    <div class="form-group">
        <label for="status">📊 Status</label>
        <select id="status" name="status" class="form-control">
            <option value="1">✅ Active</option>
            <option value="0">🚫 Inactive</option>
        </select>
    </div>
    <button type="submit" class="btn btn-success">➕ Add Category</button>
</form>

                </div>
            </div>
        </div>

    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</body>
</html>

<%@ include file="adminFooter.jsp" %>
