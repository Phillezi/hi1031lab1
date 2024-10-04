<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.order.OrderDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.UserDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.order.OrderService" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Warehouse - Pack Orders</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css" />
  </head>
  <body>
    <header>
      <a href="/"><div class="logo">Warehouse Panel</div></a>
    </header>
    
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
        if (user != null) {
          OrderService.getAllOrders(user);
        } else {
          orders = new ArrayList<>();
        }
        if (orders == null) {
          out.print("no orders present");
        } else {
        for (OrderDTO order : orders) {
        %>
          <div class="product">
            <h2><%= order.getId() %></h2>

            <h3><%= order.getStatuses() %></h3>
            <h3><%= order.getCustomer().toString() %></h3>

            <!-- "Pack Order" Button -->
            <a href="#packOrder<%= order.getId() %>">Pack Order</a>
          </div>

          <!-- Pack Order Form for this product (initially hidden) -->
          <div id="packOrder<%= order.getId() %>">
            <h3>Pack Order - <%= order.getId() %></h3>
            <form method="post" action="${pageContext.request.contextPath}/controller?action=packOrder">    <!--will need to update the action...-->
              <input type="hidden" name="productId" value="<%= order.getId() %>" />   

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
