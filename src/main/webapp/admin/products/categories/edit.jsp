<%@ page import="se.kth.hi1031.lab1.ui.dto.product.CategoryDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.product.CategoryService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Category</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<jsp:include page="/components/header.jsp"/>
<body>
<jsp:include page="/components/errors/error.jsp"/>

<%
    String categoryName = request.getParameter("categoryName");
    if (categoryName == null || categoryName.isEmpty()) {
        out.println("<p>Error: Category name is missing.</p>");
        return;
    }

    CategoryDTO category = CategoryService.getCategoryByName(categoryName);
    if (category == null) {
        out.println("<p>Error: Category not found.</p>");
        return;
    }
%>

<div class="container">
    <div class="modal-overlay">
        <div class="modal">
            <div style="display: flex; flex-direction: column; overflow: scroll; max-height: 80vh;">

                <h2>Edit Category: <%= category.getName() %>
                </h2>

                <form action="${pageContext.request.contextPath}/controller?action=categories&operation=update"
                      method="post">

                    <input type="hidden" name="name" value="<%= category.getName() %>"/>

                    <label for="description">Description:</label>
                    <textarea name="description" id="description" rows="5"
                              required><%= category.getDescription() %></textarea>
                    <br/>

                    <button type="submit">Update Category</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
