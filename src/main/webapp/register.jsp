<%@ page import="se.kth.hi1031.lab1.bo.service.user.RoleService" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<body>
<jsp:include page="/components/errors/error.jsp"/>
<div class="container">
    <div class="modal-overlay">
        <div class="modal">
            <form action="controller?action=register" method="POST">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" placeholder="name" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" placeholder="email" required>

                <label for="password">Password:</label>
                <input type="password" id="password" name="password" placeholder="password" required>
                <br><br>

                <label for="role">Role:</label>
                <select id="role" name="role" required>
                    <option value="">Select a role</option>
                    <%
                        List<String> roles = RoleService.getAvailableRoles();

                        for (String role : roles) {
                    %>
                    <option value="<%= role %>"><%= role %>
                    </option>
                    <%
                        }
                    %>
                </select>

                <input type="submit" value="Register">
            </form>
            <div style="min-height: 1rem;"></div>
            <hr style="border: 1px solid #000; width: 100%;">
            <div style="min-height: 1rem;"></div>
            <form action="${pageContext.request.contextPath}/login.jsp" method="get">
                <button type="submit">Login instead</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
