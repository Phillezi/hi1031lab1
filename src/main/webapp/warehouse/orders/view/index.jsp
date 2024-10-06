<%@ page import="java.util.List" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.order.OrderDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.order.OrderService" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View orders</title>
</head>
<jsp:include page="/components/header.jsp"/>
<body>
<jsp:include page="/components/errors/error.jsp"/>
<%
    UserDTO user = (UserDTO) session.getAttribute("user");
    List<OrderDTO> orders = OrderService.getAllOrders(user);
    request.setAttribute("orders", orders);
%>
<div class="container">
    <div class="table-container">
        <jsp:include page="/components/tables/order.jsp"/>
    </div>
</div>
</body>
</html>
