<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.order.OrderDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.order.StatusDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.UserDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.order.OrderService" %>
<%@ page import="java.util.Objects" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Warehouse - Pack Orders</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<body>
<jsp:include page="/components/header.jsp" />
<div class="root">
  <h1>Pack Orders</h1>
  <div class="product-list">
    <%
      UserDTO user = null;
      if (session != null) {
        user = (UserDTO) session.getAttribute("user");
      }
      if (user == null) {
        out.print("not logged in");
        return;
      }
      List<OrderDTO> orders = null;
        orders = OrderService.getOrdersWithStatus("received", null, "");
        if (orders == null) {
        out.print("no orders present");
      } else {
        for (OrderDTO order : orders) {
          String latestStatus = null;
          List<StatusDTO> statuses = order.getStatuses();
          if (statuses != null && !statuses.isEmpty()) {
            latestStatus = statuses.getLast().getStatus();
          }
    %>
    <div class="product">
      <h2><%= order.getId() %></h2>

      <h3><%= latestStatus != null ? latestStatus : "no status" %></h3>
      <h3><%= order.getCustomer().getName() %></h3>

      <a href="#packOrder<%= order.getId() %>">Pack Order</a>
    </div>

    <div id="packOrder<%= order.getId() %>">
      <h3>Pack Order - <%= order.getId() %></h3>
      <form method="post" action="${pageContext.request.contextPath}/controller?action=warehouse&status=packed&orderid=<%= order.getId() %>">
        <input type="hidden" name="orderId" value="<%= order.getId() %>" />
        <button type="submit">Confirm Packing</button>
      </form>
    </div>
    <%
          }
      }
    %>
  </div>
</div>

<footer>
  <div class="footer-content">
  </div>
</footer>
</body>
</html>
