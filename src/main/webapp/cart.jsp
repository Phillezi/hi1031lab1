<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.ProductDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.product.ProductService" %>

<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>
    <h2>Your Shopping Cart</h2>
    <%
    // No need to declare the session object, as it's implicitly available
    Map<Integer, Integer> cart = null;
    if (session != null) {  // Using the implicit session object
        cart = (Map<Integer, Integer>) session.getAttribute("cart");
    }
    
    List<ProductDTO> products = null;
    if (cart != null) {
        products = ProductService.getProducts(new ArrayList(cart.keySet()));
    }
    
    %>

    <table border="1">
        <tr>
            <th>Product Name</th>
            <th>Price</th>
            <th>Quantity</th>
        </tr>
        <%
        if (cart != null && products != null && !products.isEmpty()) {
            for (ProductDTO product : products) {
        %>
        <tr>
            <td><%= product.getName() %></td>
            <td><%= product.getPrice() %></td>
            <td><%= cart.get(product) %></td>
        </tr>
        <%
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
</body>
</html>
