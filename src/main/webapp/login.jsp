<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
    <form action="/main?action=login" method="POST">    
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        <br><br>
    
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <br><br>
    
        <input type="submit" value="Login">
    </form>
</body>
</html>
