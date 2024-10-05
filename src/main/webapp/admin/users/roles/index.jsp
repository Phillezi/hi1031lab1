<%@ page import="java.util.List" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.user.RoleService" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.RoleDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.PermissionDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.user.PermissionService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Administer User Roles</title>
</head>
<jsp:include page="/components/header.jsp" />
<body>
<%
    String roleName = request.getParameter("role");
    RoleDTO role = RoleService.getRole(roleName);
%>
<div class="container">
    <div class="modal-overlay">
        <div class="modal">
    <h2>Role: <%= role.getName() %></h2>

    <h3>Permissions:</h3>
    <table>
        <thead>
        <tr>
            <th>Permission Name</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<PermissionDTO> permissions = role.getPermissions();
            if (permissions != null && !permissions.isEmpty()) {
                for (PermissionDTO permission : permissions) {
        %>
        <tr>
            <td><%= permission.getName() %></td>
            <td>
                <form action="${pageContext.request.contextPath}/controller?action=todop" method="post" style="display:inline;">
                    <input type="hidden" name="roleName" value="<%= role.getName() %>" />
                    <input type="hidden" name="permissionName" value="<%= permission.getName() %>" />
                    <button type="submit">Remove</button>
                </form>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="2">No permissions assigned to this role.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <h3>Add Permission:</h3>
    <form action="${pageContext.request.contextPath}/controller?action=todo" method="post">
        <input type="hidden" name="roleName" value="<%= role.getName() %>" />
        <label for="permissionName">Select Permission:</label>
        <select name="permissionName" id="permissionName" required>
            <option value="">Select a permission</option>
            <%
                List<String> allPermissions = PermissionService.getAvailablePermissions();
                for (String permission : allPermissions) {
            %>
            <option value="<%= permission %>"><%= permission%></option>
            <%
                }
            %>
        </select>
        <button type="submit">Add Permission</button>
    </form>
        </div>
    </div>
</div>
</body>
</html>
