<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.ProductDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.product.ProductService" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.UserDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.RoleDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.PermissionDTO" %>

<html>
<head>
    <title>Shopping Cart</title>
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
        </div>
    </div>
</div>
</body>
</html>
