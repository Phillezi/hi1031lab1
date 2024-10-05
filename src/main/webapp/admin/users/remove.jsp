<%@ page import="se.kth.hi1031.lab1.bo.service.user.UserService" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Remove user</title>
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
            <h1>Are you sure you want to delete user: <%= user.getName() %>
            </h1>
            <form action="${pageContext.request.contextPath}/controller?action=users&operation=delete" method="post">
                <input type="hidden" name="userId" value="<%= user.getId() %>"/>
                <button style="background-color: #2b801c; color: #ccc" type="submit">Yes</button>
            </form>
            <form action="${pageContext.request.contextPath}/admin/users" method="get">
                <button style="background-color: #ed3939; color: #ccc" type="submit">No</button>
            </form>

        </div>
    </div>
</div>
</body>
</html>
