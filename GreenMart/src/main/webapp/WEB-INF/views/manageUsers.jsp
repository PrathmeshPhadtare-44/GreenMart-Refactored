<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.greenmart.model.ManagedUser" %>
<%@ include file="adminHeader.jsp" %>
<%@ include file="loadingLayout.jsp" %>
<%@ include file="checkAdminLogin.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>👥 Manage Users</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css">
    
</head>
<body>

<div class="container mt-4">
    <h2 class="text-center">👤 Manage Users</h2>
    <table class="table table-bordered table-striped">
        <thead class="table-dark">
            <tr>
                <th>🆔 User ID</th>
                <th>👤 Name</th>
                <th>📧 Email</th>
                <th>🔰 Role</th>
                <th>🔵 Status</th>
                <th>⚙️ Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<ManagedUser> users = (List<ManagedUser>) request.getAttribute("users");
                if (users != null) {
                    for (ManagedUser user : users) {
            %>
            <tr>
                <td><%= user.getUserId() %></td>
                <td><%= user.getUserName() %></td>
                <td><%= user.getEmail() %></td>
                <td><%= user.getRole() %></td>
                <td>
                    <% if (user.getStatus() == 1) { %>
                        <span class="badge bg-success">✅ Active</span>
                    <% } else { %>
                        <span class="badge bg-danger">⛔ Blocked</span>
                    <% } %>
                </td>
                <td>
                    <% if (user.getStatus() == 1) { %>
                        <form action="block-user" method="post" style="display:inline;">
                            <input type="hidden" name="userId" value="<%= user.getUserId() %>">
                            <button type="submit" class="btn btn-danger btn-sm">🚫 Block</button>
                        </form>
                    <% } else { %>
                        <form action="unblock-user" method="post" style="display:inline;">
                            <input type="hidden" name="userId" value="<%= user.getUserId() %>">
                            <button type="submit" class="btn btn-success btn-sm">✅ Unblock</button>
                        </form>
                    <% } %>
                    <a href="user-profile?userId=<%= user.getUserId() %>" class="btn btn-primary btn-sm">🔍 View Profile</a>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
</div>

</body>
</html>
