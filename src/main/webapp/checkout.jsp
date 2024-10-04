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
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.RoleDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.PermissionDTO" %>

<html>
<head>
    <title>Checkout</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<header>
    <a href="${pageContext.request.contextPath}/">
        <div class="logo">WebSHOP</div>
    </a>
    <div class="search">
        <img class="icon" src="${pageContext.request.contextPath}/assets/magnifying-glass.svg" alt="search icon"></img>
        <label>
            <input
                    type="text"
                    class="search-field"
                    placeholder="Search products..."
            />
        </label>
        <button class="search-button">Search</button>
    </div>
    <div class="navbar">
        <%
            UserDTO user = null;
            if (session != null) {
                user = (UserDTO) session.getAttribute("user");
            }
            if (user != null) {
                if (user.getRoles().stream().anyMatch((RoleDTO r) -> "admin".equals(r.getName()))) {
        %>
        <a href="${pageContext.request.contextPath}/admin/index.jsp"><img class="icon"
                                                                          src="${pageContext.request.contextPath}/assets/admin.svg"
                                                                          alt="icon"></img></a>
        <%
            }
            if (user.getPermissions().stream().anyMatch((PermissionDTO p) -> "update_orders".equals(p.getName()))) {
        %>
        <a href="${pageContext.request.contextPath}/warehouse.jsp"><img class="icon"
                                                                        src="${pageContext.request.contextPath}/assets/warehouse.svg"
                                                                        alt="icon"></img></a>
        <%
            }
        %>
        <a href="${pageContext.request.contextPath}/cart.jsp"><img class="icon"
                                                                   src="${pageContext.request.contextPath}/assets/shopping-cart.svg"
                                                                   alt="icon"></img></a>
        <a href="${pageContext.request.contextPath}/logout.jsp"><img class="icon"
                                                                     src="${pageContext.request.contextPath}/assets/logout.svg"
                                                                     alt="icon"></img></a>
        <%
        } else {
        %>
        <a href="${pageContext.request.contextPath}/login.jsp"><p>
            Login
        </p></a>
        <%
            }
        %>
    </div>
</header>
<body>
<%

    double totalprice = 0;
    Map<Integer, Integer> cart = null;
    if (session != null) {
        cart = (Map<Integer, Integer>) session.getAttribute("cart");
    }

    List<ProductDTO> products = null;
    if (cart != null) {
        products = ProductService.getProducts(new ArrayList<>(cart.keySet()));
    }

    String deliveryAddress = "";
%>
<div class="container">
    <div class="modal-overlay">
        <div class="modal">
            <form method="post" action="checkout.jsp">
                <h3>Delivery Address</h3>
                <label>
                    <input type="text" name="deliveryAddress" value="<%= deliveryAddress %>" required />
                </label>
            <table>
                <tr>
                    <th>Product Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                </tr>
                <%
                    if (cart != null && products != null && !products.isEmpty()) {
                        for (int i = 0; i < products.size(); i++) {
                            Double price = products.get(i).getPrice();
                            Integer quantity = cart.get(products.get(i).getId());
                            if (quantity != null) {
                                totalprice += (price * quantity);
                            }
                %>
                <tr>
                    <td><%= products.get(i).getName() %>
                    </td>
                    <td><%= price %>
                    </td>
                    <td><%= quantity %>
                    </td>
                </tr>
                <%
                    if (i == products.size() - 1) {
                %>
                <tr>
                    <td></td>
                    <td></td>
                    <td><%= totalprice %>
                    </td>
                </tr>
                <%
                        }
                    }
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
        </div>
    </div>
</div>
</body>
</html>