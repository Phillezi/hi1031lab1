<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.order.OrderDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.ProductDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.order.StatusDTO" %>

<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>Created</th>
    <th>Delivered</th>
    <th>Delivery address</th>
    <th>Customer</th>
    <th>Products</th>
    <th>Statuses</th>
  </tr>
  </thead>
  <tbody>
  <%
    List<OrderDTO> orders = (List<OrderDTO>) request.getAttribute("orders");
    if (orders != null && !orders.isEmpty()) {
      for (OrderDTO order : orders) {
  %>
  <tr>
    <td><%= order.getId() %></td>
    <td><%= order.getCreated() != null ? order.getCreated().toString() : "N/A" %></td>
    <td><%= order.getDelivered() != null ? order.getDelivered().toString() : "N/A" %></td>
    <td><%= order.getDeliveryAddress() != null ? order.getDeliveryAddress() : "N/A" %></td>
    <td><%= order.getCustomer() != null ? order.getCustomer().getName() : "N/A" %></td>
    <td>
      <ul>
        <%
          if (order.getProducts() != null) {
            for (ProductDTO product : order.getProducts()) {
        %>
        <li><%= product.getName() %> (ID: <%= product.getId() %>) Amt: <%= product.getQuantity() %>/li>
        <%
            }
          }
        %>
      </ul>
    </td>
    <td>
      <ul>
        <%
          if (order.getStatuses() != null) {
            for (StatusDTO status : order.getStatuses()) {
        %>
        <li><%= status.getStatus() %></li>
        <%
            }
          }
        %>
      </ul>
    </td>
  </tr>
  <%
    }
  } else {
  %>
  <tr>
    <td colspan="8">No orders available.</td>
  </tr>
  <%
    }
  %>
  </tbody>
</table>
