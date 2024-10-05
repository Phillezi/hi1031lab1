<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.RoleDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.PermissionDTO" %>

<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Permissions</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<RoleDTO> roles = (List<RoleDTO>) request.getAttribute("roles");
        if (roles != null && !roles.isEmpty()) {
            for (RoleDTO role : roles) {
    %>
    <tr>
        <td><%= role.getName() %></td>
        <td>
            <ul>
                <%
                    for (PermissionDTO permission : role.getPermissions()) {
                        if (permission != null) {
                %>
                <li><%= permission.getName() %></li>
                <%
                        }
                    }
                %>
            </ul>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/admin/users/roles/edit.jsp" method="get" style="display:inline;">
                <input type="hidden" name="roleName" value="<%= role.getName() %>" />
                <button class="edit-btn" type="submit">Edit</button>
            </form>
            <form action="${pageContext.request.contextPath}/admin/users/roles/remove.jsp" method="post" style="display:inline;">
                <input type="hidden" name="roleName" value="<%= role.getName() %>" />
                <button class="remove-btn" type="submit">Remove</button>
            </form>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="3">No roles available.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
