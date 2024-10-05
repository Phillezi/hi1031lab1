<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.CategoryDTO" %>

<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<CategoryDTO> categories = (List<CategoryDTO>) request.getAttribute("categories");
        if (categories != null && !categories.isEmpty()) {
            for (CategoryDTO category : categories) {
    %>
    <tr>
        <td><%= category.getName() %></td>
        <td><%= category.getDescription() %></td>
        <td>
            <form action="${pageContext.request.contextPath}/admin/products/categories/edit.jsp" method="get" style="display:inline;">
                <input type="hidden" name="categoryName" value="<%= category.getName() %>" />
                <button class="edit-btn" type="submit">Edit</button>
            </form>
            <form action="${pageContext.request.contextPath}/admin/products/categories/remove.jsp" method="post" style="display:inline;">
                <input type="hidden" name="categoryName" value="<%= category.getName() %>" />
                <button class="remove-btn" type="submit">Remove</button>
            </form>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="3">No categories available.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
