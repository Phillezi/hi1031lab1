<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.PropertyDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.CategoryDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.ProductDTO" %>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Removed</th>
        <th>Categories</th>
        <th>Images</th>
        <th>Properties</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<ProductDTO> products = (List<ProductDTO>) request.getAttribute("products");
        if (products != null && !products.isEmpty()) {
            for (ProductDTO product : products) {
    %>
    <tr>
        <td><%= product.getId() %>
        </td>
        <td><%= product.getName() %>
        </td>
        <td><%= product.getDescription() %>
        </td>
        <td><%= product.getPrice() %>
        </td>
        <td><%= product.getQuantity() %>
        </td>
        <td><%= product.isRemoved() %>
        </td>
        <td>
            <ul>
                <%
                    for (CategoryDTO category : product.getCategories()) {
                        if (category != null) {
                %>
                <p style="color: inherit;"><%= category.getName() %>
                </p>
                <%
                        }
                    }
                %>
            </ul>
        </td>
        <td>
            <ul>
                <%
                    for (String image : product.getImages()) {
                        if (image != null) {
                %>
                <li><img src="<%= image %>" alt="Product Image" style="width: 50px; height: auto;"/></li>
                <%
                        }
                    }
                %>
            </ul>
        </td>
        <td>
            <ul>
                <%
                    for (PropertyDTO property : product.getProperties()) {
                        if (property != null) {
                %>
                <li><%= property.getKey() + ": " + property.getValue() %>
                </li>
                <%
                        }
                    }
                %>
            </ul>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/admin/products/edit.jsp" method="get"
                  style="display:inline;">
                <input type="hidden" name="productId" value="<%= product.getId() %>"/>
                <button class="edit-btn" type="submit">Edit</button>
            </form>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="10">No products available.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
