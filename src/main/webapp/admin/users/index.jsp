<%@ page import="java.util.List" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.UserDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.user.UserService" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.PermissionException" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.ServiceException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Administer users</title>
</head>
<jsp:include page="/components/header.jsp"/>
<body>
<jsp:include page="/components/errors/error.jsp"/>
<%
    UserDTO user = (UserDTO) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("/errors/401.jsp");
        return;
    }
    List<UserDTO> userList = null;
    try {
         userList = UserService.getUsers(user);
    } catch(PermissionException | ServiceException e) {
        session.setAttribute("error", e.getMessage());
        response.sendRedirect("/admin/users/");
        return;
    }
    request.setAttribute("users", userList);
%>
<div class="container">
    <div class="table-container">
        <jsp:include page="/components/tables/users.jsp"/>
        <form action="${pageContext.request.contextPath}/admin/users/add.jsp" method="get">
            <button class="add-btn" type="submit">Add user</button>
        </form>
    </div>
</div>
</body>
</html>
