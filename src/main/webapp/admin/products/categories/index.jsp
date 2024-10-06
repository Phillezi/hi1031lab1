<%@ page import="se.kth.hi1031.lab1.ui.dto.product.ProductDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.product.ProductService" %>
<%@ page import="java.util.List" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.product.CategoryService" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.product.CategoryDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Administer products</title>
</head>
<jsp:include page="/components/header.jsp"/>
<body>
<jsp:include page="/components/errors/error.jsp"/>
<%
    List<CategoryDTO> availableCategories = CategoryService.getCategories();
    request.setAttribute("categories", availableCategories);
%>
<div class="container">
    <div class="table-container">
        <jsp:include page="/components/tables/categories.jsp"/>
        <form action="${pageContext.request.contextPath}/admin/products/categories/add.jsp" method="get">
            <button class="add-btn" type="submit">Add category</button>
        </form>
    </div>
</div>
</body>
</html>
