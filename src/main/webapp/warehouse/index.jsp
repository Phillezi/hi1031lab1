<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Warehouse</title>
</head>
<jsp:include page="/components/header.jsp" />
<body>
<div class="container">
  <div class="modal-overlay">
    <h1>Warehouse Dashboard</h1>

    <div class="modal">
      <form action="${pageContext.request.contextPath}/warehouse/orders/pack" method="get">
        <button type="submit" class="admin-button">Pack orders</button>
      </form>
      <form action="${pageContext.request.contextPath}/warehouse/orders/ship" method="get">
        <button type="submit" class="admin-button">Ship orders</button>
      </form>
    </div>

  </div>
</div>
</body>
</html>