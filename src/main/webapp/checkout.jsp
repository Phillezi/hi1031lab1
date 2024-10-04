<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.ProductDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.UserDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.order.OrderDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.product.ProductService" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.order.OrderService" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.PermissionException" %>

<html>
<head>
    <title>Checkout</title>
</head>
<body>
    <h2>Checkout</h2>
    <%
    
    double totalPrice = 0;
    Map<Integer, Integer> cart = null;
    UserDTO user = null;
    if (session != null) {
        cart = (Map<Integer, Integer>) session.getAttribute("cart");
        user = (UserDTO) session.getAttribute("user");
    }

    if (user == null) {
        out.println("You must be logged in to place an order.");
        return;
    }

    List<ProductDTO> products = null;
    if (cart != null) {
        products = ProductService.getProducts(new ArrayList<>(cart.keySet()));
    }

    String deliveryAddress = request.getParameter("deliveryAddress");

    %>

    <form method="post" action="checkout.jsp">
        <h3>Delivery Address</h3>
        <input type="text" name="deliveryAddress" value="<%= deliveryAddress != null ? deliveryAddress : "" %>" required />
        
        <h3>Your Order</h3>
        <table border="1">
            <tr>
                <th>Product Name</th>
                <th>Price</th>
                <th>Quantity</th>
            </tr>
            <%
            if (cart != null && products != null && !products.isEmpty()) {
                for (ProductDTO product : products) {
                    Double price = product.getPrice();
                    Integer quantity = cart.get(product.getId());
                    if (quantity != null && price != null) {
                        totalPrice += (price * quantity);
                    }
            %>
                    <tr>
                        <td><%= product.getName() %></td>
                        <td><%= price %></td>
                        <td><%= quantity %></td>
                    </tr>
            <%
                }
            %>
                <tr>
                    <td colspan="2"><strong>Total Price</strong></td>
                    <td><strong><%= totalPrice %></strong></td>
                </tr>
            <%
            } else {
            %>
            <tr>
                <td colspan="3">Your cart is empty</td>
            </tr>
            <%
            }
            %>
        </table>

        <button type="submit">Place Order</button>
    </form>

    <%
    // Handle order placement after form submission (POST request)
    if ("POST".equalsIgnoreCase(request.getMethod()) && deliveryAddress != null && !deliveryAddress.isEmpty()) {
        try {
            ArrayList<ProductDTO> orderedProducts = new ArrayList<>(products);
            OrderDTO order = OrderService.createOrder(user, user, deliveryAddress, orderedProducts);
            out.println("<h3>Order successfully placed! Order ID: " + order.getId() + "</h3>");
            session.removeAttribute("cart"); // Clear cart after successful order
        } catch (PermissionException e) {
            out.println("<div style='color:red;'>You don't have permission to create this order.</div>");
        }
    }
    %>

</body>
</html>
