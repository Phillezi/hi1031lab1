<%@ page import="java.util.List" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.user.RoleService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add New User</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<jsp:include page="/components/header.jsp" />
<body>
<jsp:include page="/components/errors/error.jsp" />

<div class="container">
    <div class="modal-overlay">
        <div class="modal">

            <h2>Add New User</h2>

            <form action="${pageContext.request.contextPath}/controller?action=users&operation=add" method="post">

                <label for="name">Name:</label>
                <input type="text" name="name" id="name" required/>
                <br/>

                <label for="email">Email:</label>
                <input type="email" name="email" id="email" required/>
                <br/>

                <label for="password">Password:</label>
                <input type="password" name="password" id="password" required/>
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
                        if (!availableRoles.isEmpty()) {
                            for (String roleName : availableRoles) {
                    %>
                    <tr>
                        <td>
                            <label for="<%= roleName %>"><%= roleName %></label>
                        </td>
                        <td>
                            <input id="<%= roleName %>" type="checkbox" name="roles" value="<%= roleName %>"/>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="2">No roles available.</td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>

                <button type="submit">Add User</button>
            </form>

        </div>
    </div>
</div>
</body>
</html>
