<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.UserDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.RoleDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.PermissionDTO" %>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Password</th>
        <th>Roles</th>
        <th>Permissions</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<UserDTO> users = (List<UserDTO>) request.getAttribute("users");
        if (users != null && !users.isEmpty()) {
            for (UserDTO user : users) {
    %>
    <tr>
        <td><%= user.getId() %>
        </td>
        <td><%= user.getName() %>
        </td>
        <td><%= user.getEmail() %>
        </td>
        <td><%= user.getPassword() %>
        </td>
        <td>
            <ul>
                <%
                    for (RoleDTO role : user.getRoles()) {
                        if (role != null) {
                %>
                <a href="${pageContext.request.contextPath}/admin/users/roles?role=<%= role.getName() %>"
                   class="clickable-attribute" style="color: inherit;"><%= role.getName() %>
                </a>
                <%
                        }
                    }
                %>
            </ul>
        </td>
        <td>
            <ul>
                <%
                    for (PermissionDTO permission : user.getPermissions()) {
                        if (permission != null) {
                %>
                <li><%= permission.getName() %>
                </li>
                <%
                        }
                    }
                %>
            </ul>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/admin/users/edit.jsp" method="get" style="display:inline;">
                <input type="hidden" name="userId" value="<%= user.getId() %>"/>
                <button class="edit-btn" type="submit">Edit</button>
            </form>
            <form action="${pageContext.request.contextPath}/admin/users/remove.jsp" method="post"
                  style="display:inline;">
                <input type="hidden" name="userId" value="<%= user.getId() %>"/>
                <button class="remove-btn" type="submit">Remove</button>
            </form>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="7">No users available.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
