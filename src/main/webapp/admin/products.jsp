<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.ProductDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.product.ProductService" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Admin - Product Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css" />
  </head>
  <body>
  <jsp:include page="/components/header.jsp" />
    
    <div class="root">
      <h1>Manage Products</h1>
      <div class="product-list">
        <%
          List<ProductDTO> products = ProductService.getProducts();
          for (ProductDTO product : products) {
            if (!product.isRemoved()) {
        %>
        <div class="product">
          <h2><%= product.getName() %></h2>
          <p class="product-description"><%= product.getDescription() %></p>
          <p class="product-price">Price: <%= product.getPrice() %> kr</p>
          <p class="product-quantity">Available Quantity: <%= product.getQuantity() %></p>

          <!-- "Edit" Button -->
          <a href="#editProduct<%= product.getId() %>">Edit</a>
        </div>

        <!-- Edit Form for this product (initially hidden) -->
        <div id="editProduct<%= product.getId() %>">
          <h3>Edit Product - <%= product.getName() %></h3>
          <form method="post" action="${pageContext.request.contextPath}/controller?action=updateProduct">
            <input type="hidden" name="productId" value="<%= product.getId() %>" />

            <label for="name">Product Name:</label>
            <input type="text" name="name" id="name" value="<%= product.getName() %>" required />

            <label for="price">Price:</label>
            <input type="number" name="price" id="price" value="<%= product.getPrice() %>" step="0.01" required />

            <label for="quantity">Quantity:</label>
            <input type="number" name="quantity" id="quantity" value="<%= product.getQuantity() %>" required />

            <label for="category">Category:</label>
            <input type="text" name="category" id="category" value="<%= product.getCategories().size() > 0 ? product.getCategories().get(0).getName() : "" %>" required />

            <button type="submit">Save Changes</button>
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
