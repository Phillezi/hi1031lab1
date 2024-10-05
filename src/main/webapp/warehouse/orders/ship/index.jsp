<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.order.OrderDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.order.StatusDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.UserDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.order.OrderService" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.ProductDTO" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Warehouse - Pack Orders</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<div class="root">
    <jsp:include page="/components/errors/error.jsp"/>
    <h1>Ship Orders</h1>
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
            List<OrderDTO> orders = OrderService.getOrdersWithStatus(user, "packed", null, "");
            if (orders == null || orders.isEmpty()) {
                out.print("no orders present");
            } else {
                for (OrderDTO order : orders) {
                    String latestStatus = null;
                    List<StatusDTO> statuses = order.getStatuses();
                    if (statuses != null && !statuses.isEmpty()) {
                        latestStatus = statuses.get(statuses.size() - 1).getStatus();
                    }
        %>
        <div class="product">
            <h2>Order ID: <%= order.getId() %>
            </h2>
            <h3>Status: <%= latestStatus != null ? latestStatus : "no status" %>
            </h3>
            <h3>Customer: <%= order.getCustomer().getName() %>
            </h3>

            <h4>Delivery Address:</h4>
            <p><%= order.getDeliveryAddress() != null ? order.getDeliveryAddress() : "No address provided" %>
            </p>

            <h4>Ordered Products:</h4>
            <ul>
                <%
                    List<ProductDTO> products = order.getProducts(); // Assuming getProducts() returns a List<ProductDTO>
                    if (products != null && !products.isEmpty()) {
                        for (ProductDTO product : products) {
                %>
                <li>
                    <strong><%= product.getName() %>
                    </strong> - Quantity: <%= product.getQuantity() %>
                </li>
                <%
                    }
                } else {
                %>
                <li>No products ordered.</li>
                <%
                    }
                %>
            </ul>

            <a href="#packOrder<%= order.getId() %>">Ship Order</a>
        </div>

        <div id="packOrder<%= order.getId() %>">
            <h3>Pack Order - <%= order.getId() %>
            </h3>
            <form method="post"
                  action="${pageContext.request.contextPath}/controller?action=warehouse&status=shipped&orderId=<%= order.getId() %>">
                <input type="hidden" name="orderId" value="<%= order.getId() %>"/>
                <button type="submit">Confirm Shipment</button>
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
