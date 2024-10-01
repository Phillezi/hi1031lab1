<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="se.kth.hi1031.lab1.db.ProductDAO" %>
<%@ page import="se.kth.hi1031.lab1.bo.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Web shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css" />
  </head>
  <body>
    <header>
      <a href="/"><div class="logo">WebSHOP</div></a>
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
        <a href="${pageContext.request.contextPath}/shop"><img class="icon" alt="icon"></img></a>
        <a href="${pageContext.request.contextPath}/contact"><img class="icon" src="${pageContext.request.contextPath}/assets/contact.svg" alt="icon"></img></a>
        <a href="${pageContext.request.contextPath}/cart"><img class="icon" src="${pageContext.request.contextPath}/assets/shopping-cart.svg" alt="icon"></img></a>
      </div>
    </header>
    <div class="root">
        <div class="featured">
            <h2>Featured Products</h2>
            <div class="featured-products">
              <%
                  List<Product> products = null;
      
                  try {
                      products = ProductDAO.getAllProducts();
                  } catch (SQLException e) {
                      out.println("<p>Error retrieving products.</p>");
                      return;
                  }
      
                  if (!products.isEmpty()) {
                      for (Product product : products) {
              %>
                          <div class="product-item">
                              <h3><%= product.getName() %></h3>
                              <p><%= product.getDescription() %></p>
                              <p>Price: <%= product.getPrice() %></p>
                              <p>Quantity: <%= product.getQuantity() %></p>
                          </div>
              <%
                      }
                  } else {
                      out.println("<p>No featured products available.</p>");
                  }
              %>
            </div>
          </div>
          

      <div class="about">
        <h2>About Us</h2>
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Repellat repudiandae iusto quis quod cum nobis, quae harum exercitationem nisi accusamus, pariatur perferendis excepturi eius iste neque modi. Reiciendis, at non.
        </p>
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Inventore in ipsa accusamus quia dolorem distinctio cupiditate cumque voluptates molestiae aut officia eum, hic maxime amet nemo alias at doloribus tempora?
        </p>
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Repellendus illo, ea voluptates cupiditate corporis nobis libero sed incidunt excepturi veritatis ut quaerat voluptatum, autem accusantium adipisci! Porro voluptas sequi quaerat.
        </p>
      </div>
    </div>
    <footer>
      <div class="footer-content">
      </div>
    </footer>
</html>

