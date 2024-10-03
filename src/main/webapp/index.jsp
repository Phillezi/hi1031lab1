<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.ProductDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.CategoryDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.PropertyDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.product.ProductService" %>

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
    List<ProductDTO> products = ProductService.getProducts();
    for (ProductDTO product : products) {
      // Only display products that are not removed
      if (!product.isRemoved()) {
        %>
            <div class="product">
              <h2><%= product.getName() %></h2>
              <div class="product-images">
                <% 
                  List<String> images = product.getImages();
                  String image = "https://placehold.co/400x400";
                  if (images != null && images.size() > 0) {
                    for (String img : images) {
                      if (img != null) {
                        image = img;
                        break;
                      }
                    }
                  }
                %>
                <img src="<%= image %>" alt="<%= product.getName() %>" style="width: 10vw; height: 10vw;" />
              </div>
              <p class="product-description"><%= product.getDescription() %></p>
              <p class="product-price">Price: <%= product.getPrice() %> kr</p>
              <p class="product-quantity">Available Quantity: <%= product.getQuantity() %></p>
              <div class="product-categories">
                <strong>Categories:</strong>
                <ul>
                  <%
                    for (CategoryDTO category : product.getCategories()) {
                  %>
                      <li><%= category.getName() %></li>
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
                      <li><%= property.getKey() %>: <%= property.getValue() %></li>
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
  </body>
</html>

