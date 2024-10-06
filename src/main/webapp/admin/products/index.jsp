<%@ page import="se.kth.hi1031.lab1.ui.dto.product.ProductDTO" %>
<%@ page import="se.kth.hi1031.lab1.bo.service.product.ProductService" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Administer products</title>
</head>
<jsp:include page="/components/header.jsp"/>
<body>
<jsp:include page="/components/errors/error.jsp"/>
<%
    List<ProductDTO> productList = ProductService.getProducts();
    request.setAttribute("products", productList);
%>
<div class="container">
    <div class="table-container">
        <jsp:include page="/components/tables/product.jsp"/>
        <div style="display: flex; flex-direction: row; justify-content: space-evenly;">
            <form action="${pageContext.request.contextPath}/admin/products/add.jsp" method="get">
                <button class="add-btn" type="submit">Add product</button>
            </form>
            <form action="${pageContext.request.contextPath}/admin/products/categories" method="get">
                <button class="add-btn" type="submit">Manage Categories</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
