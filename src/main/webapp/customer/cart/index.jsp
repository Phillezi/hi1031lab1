<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.ProductDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.product.ProductService" %>

<html>
<head>
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<jsp:include page="/components/header.jsp"/>
<body>
<%

    double totalprice = 0;
    Map<Integer, Integer> cart = null;
    if (session != null) {
        cart = (Map<Integer, Integer>) session.getAttribute("cart");
    }

    List<ProductDTO> products = null;
    if (cart != null) {
        products = ProductService.getProducts(new ArrayList(cart.keySet()));
    }

%>
<div class="container">
    <div class="modal-overlay">
        <div class="modal">
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
                    <td><%= totalprice %> kr</td>
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
            <%
                if (cart != null && products != null && !products.isEmpty()) {
            %>
            <form action="${pageContext.request.contextPath}/customer/checkout" method="get">
                <button type="submit">Go to checkout</button>
            </form>
            <br />
            <%
                }
            %>
            <form action="${pageContext.request.contextPath}/customer/orders" method="get">
                <button type="submit">Your Orders</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
