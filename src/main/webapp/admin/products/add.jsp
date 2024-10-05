<%@ page import="se.kth.hi1031.lab1.bo.service.product.CategoryService" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Product</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css"/>
</head>
<jsp:include page="/components/header.jsp"/>
<body>
<jsp:include page="/components/errors/error.jsp"/>

<div class="container">
    <div class="modal-overlay">
        <div class="modal">
            <div style="display: flex; flex-direction: column; overflow: scroll; max-height: 80vh;">

                <h2>Add New Product</h2>

                <form action="${pageContext.request.contextPath}/controller?action=products&operation=add"
                      method="post">

                    <label for="name">Name:</label>
                    <input type="text" name="name" id="name" placeholder="Enter product name" required/>
                    <br/>

                    <label for="description">Description:</label>
                    <textarea name="description" id="description" rows="5" placeholder="Enter product description"
                              required></textarea>
                    <br/>

                    <label for="price">Price:</label>
                    <input type="number" step="0.01" name="price" id="price" placeholder="Enter price" required/>
                    <br/>

                    <label for="quantity">Quantity:</label>
                    <input type="number" name="quantity" id="quantity" placeholder="Enter quantity" required/>
                    <br/>

                    <label hidden for="removed">Removed:</label>
                    <input type="checkbox" name="removed" id="removed" value="false" hidden=""/>
                    <br/>

                    <h3>Categories:</h3>
                    <table>
                        <thead>
                        <tr>
                            <th>Category</th>
                            <th>Assign</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            List<String> availableCategories = CategoryService.getAvailableCategories();
                            if (!availableCategories.isEmpty()) {
                                for (String category : availableCategories) {
                        %>
                        <tr>
                            <td><label for="<%= category %>"><%= category %>
                            </label></td>
                            <td>
                                <input id="<%= category %>" type="checkbox" name="categories" value="<%= category %>"/>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="2">No categories available.</td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>

                    <h3>Images:</h3>
                    <label for="imageURL">Add Image URLs (Comma Separated):</label>
                    <input type="text" id="imageURL" name="images" placeholder="Enter image URLs separated by commas"/>
                    <br/>

                    <button type="submit">Add Product</button>
                </form>

            </div>
        </div>
    </div>
</div>
</body>
</html>
