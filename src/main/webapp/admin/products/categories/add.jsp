<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Category</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<jsp:include page="/components/header.jsp"/>
<body>
<jsp:include page="/components/errors/error.jsp"/>

<div class="container">
    <div class="modal-overlay">
        <div class="modal">
            <div style="display: flex; flex-direction: column; overflow: scroll; max-height: 80vh;">

                <h2>Add New Category</h2>

                <form action="${pageContext.request.contextPath}/controller?action=categories&operation=add" method="post">

                    <label for="name">Name:</label>
                    <input type="text" name="name" id="name" placeholder="Enter category name" required/>
                    <br/>

                    <label for="description">Description:</label>
                    <textarea name="description" id="description" rows="5" placeholder="Enter category description" required></textarea>
                    <br/>

                    <button type="submit">Add Category</button>
                </form>

            </div>
        </div>
    </div>
</div>
</body>
</html>
