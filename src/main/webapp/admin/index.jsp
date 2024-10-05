<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
</head>
<jsp:include page="/components/header.jsp"/>
<body>
<div class="container">
    <div class="modal-overlay">
        <h1>Admin Dashboard</h1>

        <div class="modal">
            <form action="${pageContext.request.contextPath}/admin/products" method="get">
                <button type="submit" class="admin-button">Manage Products</button>
            </form>

            <form action="${pageContext.request.contextPath}/admin/users" method="get">
                <button type="submit" class="admin-button">Manage Users</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
