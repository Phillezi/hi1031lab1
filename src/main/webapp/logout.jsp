<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Logout</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<body>
<div class="container">
  <div class="modal-overlay">
    <div class="modal">
      <%
      session.invalidate();
      %>
      <form action="${pageContext.request.contextPath}/" method="get">
        <button type="submit">Take me back</button>
      </form>
    </div>
  </div>
</div>
</body>
</html>
