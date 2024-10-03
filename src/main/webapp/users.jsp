<%@ page import="se.kth.hi1031.lab1.bo.service.user.UserService" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.UserDTO" %>
<%@ page import="java.util.List" %>
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
