<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<body>
    <div class="container">
        <div class="modal-overlay">
            <div class="modal">
                <form action="controller?action=login" method="POST">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" placeholder="email" required>

                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" placeholder="password" required>
                    <br><br>

                    <input type="submit" value="Login">
                </form>
                <div style="min-height: 1rem;"></div>
                <hr style="border: 1px solid #000; width: 100%;">
                <div style="min-height: 1rem;"></div>
                <form action="${pageContext.request.contextPath}/register.jsp" method="get">
                    <button type="submit">Create an account</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
