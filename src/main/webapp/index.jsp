<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.ProductDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.CategoryDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.PropertyDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.product.ProductService" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.UserDTO" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Web shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<body>
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
            if(user != null) {
                %>
        <a href="${pageContext.request.contextPath}/cart.jsp"><img class="icon"
                                                                   src="${pageContext.request.contextPath}/assets/shopping-cart.svg"
                                                                   alt="icon"></img></a>
        <a href="${pageContext.request.contextPath}/user.jsp"><img class="icon"
                                                                   src="${pageContext.request.contextPath}/assets/person.svg"
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
<div class="root">
    <div class="featured">
        <h2>Featured Products</h2>
        <div class="featured-products">
            <%
                List<ProductDTO> products = ProductService.getProducts();
                for (ProductDTO product : products) {
                    if (!product.isRemoved()) {
            %>
            <div class="product">
                <h2><%= product.getName() %>
                </h2>
                <div class="product-images">
                    <%
                        List<String> images = product.getImages();
                        String image = "https://placehold.co/400x400";
                        if (images != null && !images.isEmpty()) {
                            for (String img : images) {
                                if (img != null) {
                                    image = img;
                                    break;
                                }
                            }
                        }
                    %>
                    <img src="<%= image %>" alt="<%= product.getName() %>" style="width: 10vw; height: 10vw;"/>
                </div>
                <p class="product-description"><%= product.getDescription() %>
                </p>
                <p class="product-price">Price: <%= product.getPrice() %> kr</p>
                <p class="product-quantity">Available Quantity: <%= product.getQuantity() %>
                </p>

                <!-- Add to Cart Form -->
                <form method="post" action="${pageContext.request.contextPath}/controller?action=cart">
                    <input type="hidden" name="productId" value="<%= product.getId() %>"/>
                    <label for="quantity<%= product.getId() %>">Quantity:</label>
                    <input type="number" name="quantity" id="quantity" value="1" min="1"
                           max="<%= product.getQuantity() %>"/>
                    <button type="submit">Add to Cart</button>
                </form>

                <div class="product-categories">
                    <strong>Categories:</strong>
                    <ul>
                        <%
                            for (CategoryDTO category : product.getCategories()) {
                        %>
                        <li><%= category.getName() %>
                        </li>
                        <%
                            }
                        %>
                    </ul>
                </div>
                <div class="product-properties">
                    <strong>Properties:</strong>
                    <ul>
                        <%
                            for (PropertyDTO property : product.getProperties()) {
                        %>
                        <li><%= property.getKey() %>: <%= property.getValue() %>
                        </li>
                        <%
                            }
                        %>
                    </ul>
                </div>
            </div>
            <%
                    }
                }
            %>

        </div>
    </div>

</div>
<footer>
    <div class="footer-content">
    </div>
</footer>
</body>
</html>

