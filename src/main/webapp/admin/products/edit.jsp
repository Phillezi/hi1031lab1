<%@ page import="se.kth.hi1031.lab1.ui.dto.product.ProductDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.product.ProductService" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.CategoryDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.product.CategoryService" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.PropertyDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Product</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<jsp:include page="/components/header.jsp"/>
<body>
<jsp:include page="/components/errors/error.jsp"/>
<%
    String productIdStr = request.getParameter("productId");
    if (productIdStr == null || productIdStr.isEmpty()) {
        out.println("<p>Error: Product ID is missing.</p>");
        return;
    }

    int productId = Integer.parseInt(productIdStr);
    ProductDTO product = ProductService.getProductById(productId);
    if (product == null) {
        out.println("<p>Error: Product not found.</p>");
        return;
    }
%>
<div class="container">
    <div class="modal-overlay">
        <div class="modal">
            <div style="display: flex; flex-direction: column; overflow: scroll; max-height: 80vh;">

                <h2>Edit Product: <%= product.getName() %>
                </h2>

                <form action="${pageContext.request.contextPath}/controller?action=products&operation=update"
                      method="post">
                    <input type="hidden" name="productId" value="<%= product.getId() %>"/>

                    <label for="name">Name:</label>
                    <input type="text" name="name" id="name" value="<%= product.getName() %>" required/>
                    <br/>

                    <label for="description">Description:</label>
                    <textarea name="description" id="description" rows="5"
                              required><%= product.getDescription() %></textarea>
                    <br/>

                    <label for="price">Price:</label>
                    <input type="number" step="0.01" name="price" id="price" value="<%= product.getPrice() %>"
                           required/>
                    <br/>

                    <label for="quantity">Quantity:</label>
                    <input type="number" name="quantity" id="quantity" value="<%= product.getQuantity() %>" required/>
                    <br/>

                    <label for="removed">Removed:</label>
                    <input type="checkbox" name="removed" id="removed" <%= product.isRemoved() ? "checked" : "" %>/>
                    <br/>

                    <h3>Categories:</h3>
                    <table>
                        <thead>
                        <tr>
                            <th>Category</th>
                            <th>Assigned</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            List<String> availableCategories = CategoryService.getAvailableCategories();
                            List<String> assignedCategories = product.getCategories().stream().map(CategoryDTO::getName).toList();
                            Map<String, Boolean> productCategories = new HashMap<>();
                            availableCategories.forEach(category -> productCategories.put(category, assignedCategories.contains(category)));
                            if (!availableCategories.isEmpty()) {
                                for (String category : availableCategories) {
                        %>
                        <tr>
                            <td><label for="<%= category %>"><%= category %>
                            </label></td>
                            <td>
                                <input id="<%= category %>" type="checkbox" name="categories" value="<%= category %>"
                                        <%= productCategories.get(category) ? "checked" : "" %>/>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="2">No categories available.</td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>

                    <h3>Images:</h3>
                    <%
                        if (product.getImages() != null && !product.getImages().isEmpty()) {
                            for (String image : product.getImages()) {
                    %>
                    <div>
                        <img src="<%= image %>" alt="Product Image" width="100"/>
                    </div>
                    <%
                        }
                    } else {
                    %>
                    <p>No images available.</p>
                    <%
                        }
                    %>

                    <label for="imageURL">Add Image URL:</label>
                    <input type="text" id="imageURL" name="images" placeholder="Enter image URLs separated by commas"
                           value="<%= product.getImages() != null && !product.getImages().isEmpty() ? product.getImages().getFirst() : "" %>"/>
                    <br/>

                    <h3 style="display: none;">Properties:</h3>
                    <table style="display: none;">
                        <thead>
                        <tr>
                            <th>Property</th>
                            <th>Value</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            if (product.getProperties() != null && !product.getProperties().isEmpty()) {
                                for (PropertyDTO property : product.getProperties()) {
                        %>
                        <tr>
                            <td><%= property.getKey() %>
                            </td>
                            <td><input type="text" name="property_<%= property.getKey() %>"
                                       value="<%= property.getValue() %>"/></td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="2">No properties available.</td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>

                    <button type="submit">Update Product</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
