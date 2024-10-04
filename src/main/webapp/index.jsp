<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.ProductDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.CategoryDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.PropertyDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.product.ProductService" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.UserDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.RoleDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.PermissionDTO" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Web shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<body>
<jsp:include page="/components/header.jsp" />
<div class="root">
    <div class="featured">
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
                    <img src="<%= image %>" alt="<%= product.getName() %>" style="width: 100%; height: 100%;"/>
                </div>
                <p class="product-description"><%= product.getDescription() %>
                </p>
                <p class="product-price">Price: <%= product.getPrice() %> kr</p>
                <p class="product-quantity">Available Quantity: <%= product.getQuantity() %>
                </p>
                <%
                    if (product.getCategories() != null && !product.getCategories().isEmpty()) {
                %>
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
                <%
                    }
                    if (product.getProperties() != null && !product.getProperties().isEmpty()) {
                %>
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
                <%
                    }
                %>

                <form method="post" action="${pageContext.request.contextPath}/controller?action=cart">
                    <input type="hidden" name="productId" value="<%= product.getId() %>"/>
                    <label for="quantity<%= product.getId() %>">Quantity:</label>
                    <label for="quantity"></label>
                    <input type="number" name="quantity" id="quantity" value="1" min="1"
                                                         max="<%= Math.max(product.getQuantity(), 0) %>"/>
                    <button type="submit" <%= product.getQuantity() <= 0 ? "disabled" : "" %> >Add to Cart</button>
                </form>
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

