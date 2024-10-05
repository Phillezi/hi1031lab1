<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<div class="container">
    <div class="modal-overlay">
        <div class="modal">
            <h1>401</h1>
            <p>Unauthorized.</p>
            <form action="${pageContext.request.contextPath}/login.jsp" method="get">
                <button type="submit">Login</button>
            </form>
            <form action="${pageContext.request.contextPath}/" method="get">
                <button type="submit">Take me back</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
