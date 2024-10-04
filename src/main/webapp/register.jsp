<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<body>
<div class="container">
    <div class="modal-overlay">
        <div class="modal">
            <form action="controller?action=register" method="POST">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" placeholder="name" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" placeholder="email" required>

                <label for="password">Password:</label>
                <input type="password" id="password" name="password" placeholder="password" required>
                <br><br>

                <label for="role">Role:</label>
                <input type="text" id="role" name="role" placeholder="warehouse or admin" required>
                <br><br>

                <input type="submit" value="Register">
            </form>
            <div style="min-height: 1rem;"></div>
            <hr style="border: 1px solid #000; width: 100%;">
            <div style="min-height: 1rem;"></div>
            <form action="${pageContext.request.contextPath}/login.jsp.jsp" method="get">
                <button type="submit">Login instead</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
