<%@ page import="se.kth.hi1031.lab1.bo.service.user.UserService" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.UserDTO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: emil
  Date: 2024-10-03
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users temp</title>
</head>
<body>
<div class="users">
<%
    List<UserDTO> users = UserService.getUsers();
    if (users == null) {
        out.print("ERROR: Users are null");
        return;
    }
    for (UserDTO user : users) {
        %>
        <div class="user">
        <%
        out.print(user.toString());
        %>
        </div>
<%
    }
%>
</div>

</body>
</html>
