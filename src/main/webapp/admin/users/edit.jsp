<%@ page import="se.kth.hi1031.lab1.ui.dto.user.UserDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.user.UserService" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.RoleDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.user.RoleService" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit User</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<jsp:include page="/components/header.jsp"/>
<body>
<jsp:include page="/components/errors/error.jsp"/>
<%
    String userIdStr = request.getParameter("userId");
    if (userIdStr == null || userIdStr.isEmpty()) {
        out.println("<p>Error: User ID is missing.</p>");
        return;
    }

    int userId = Integer.parseInt(userIdStr);
    UserDTO user = UserService.getUserById(userId);
    if (user == null) {
        out.println("<p>Error: User not found.</p>");
        return;
    }
%>
<div class="container">
    <div class="modal-overlay">
        <div class="modal">


            <h2>Edit User: <%= user.getName() %>
            </h2>

            <form action="${pageContext.request.contextPath}/controller?action=users&operation=update" method="post">
                <input type="hidden" name="userId" value="<%= user.getId() %>"/>

                <label for="name">Name:</label>
                <input type="text" name="name" id="name" value="<%= user.getName() %>" required/>
                <br/>

                <label for="email">Email:</label>
                <input type="email" name="email" id="email" value="<%= user.getEmail() %>" required/>
                <br/>

                <label for="password">Password:</label>
                <input type="password" name="password" id="password"
                       placeholder="Leave blank to keep current password"/>
                <br/>

                <h3>Roles:</h3>
                <table>
                    <thead>
                    <tr>
                        <th>Role</th>
                        <th>Assigned</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<String> availableRoles = RoleService.getAvailableRoles();
                        List<String> roles = user.getRoles().stream().map(RoleDTO::getName).toList();
                        Map<String, Boolean> userRoles = new HashMap<String, Boolean>();
                        availableRoles.forEach((String s) -> userRoles.put(s, roles.contains(s)));
                        if (!availableRoles.isEmpty()) {
                            for (String roleName : availableRoles) {
                    %>
                    <tr>
                        <td>
                            <label for="<%= roleName %>"><%= roleName %>
                            </label>
                        </td>
                        <td>
                            <input id="<%= roleName %>" type="checkbox" name="roles" value="<%= roleName %>"
                                    <%= userRoles.get(roleName) ? "checked" : "" %> />
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="2">No roles assigned.</td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>


                <button type="submit">Update User</button>
            </form>

        </div>
    </div>
</div>
</body>
</html>
