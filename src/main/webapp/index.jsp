<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="se.kth.hi1031.lab1.dao.ProductDAO" %>
<%@ page import="se.kth.hi1031.lab1.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Webshop</title>
    <link rel="stylesheet" href="/styles/styles.css" />
  </head>
  <body>
    <header>
      <a href="/"><div class="logo">WebSHOP</div></a>
      <div class="search">
        <img class="icon" src="/assets/magnifying-glass.svg"></img>
        <input
          type="text"
          class="search-field"
          placeholder="Search products..."
        />
        <button class="search-button">Search</button>
      </div>
      <div class="navbar">
        <a href="/shop"><img class="icon"></img></a>
        <a href="/contact"><img class="icon" src="/assets/contact.svg"></img></a>
        <a href="/cart"><img class="icon" src="/assets/shopping-cart.svg"></img></a>
      </div>
    </header>
    <div class="root">
        <div class="featured">
            <h2>Featured Products</h2>
            <div class="featured-products">
              <%
                  ProductDAO productDAO = null;

                  try {
                    productDAO = new ProductDAO();
                  } catch (SQLException e) {
                    out.println("<div class=\"error\">Could not connect to the database: " + e.getMessage() + "</div>");
                    return;
                  }

                  List<Product> products = null;
      
                  try {
                      products = productDAO.getAllProducts();
                  } catch (SQLException e) {
                      e.printStackTrace();
                      out.println("<p>Error retrieving products.</p>");
                  }
      
                  if (products != null && !products.isEmpty()) {
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

