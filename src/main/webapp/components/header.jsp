<%@ page import="se.kth.hi1031.lab1.ui.dto.user.UserDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.RoleDTO" %>
<%@ page import="se.kth.hi1031.lab1.ui.dto.user.PermissionDTO" %>
<head>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css" />
</head>
<header>
  <a href="${pageContext.request.contextPath}/">
    <div class="logo">WebSHOP</div>
  </a>
  <div class="search">
    <img class="icon" src="${pageContext.request.contextPath}/assets/magnifying-glass.svg" alt="search icon" />
    <label>
      <input
              type="text"
              class="search-field"
              placeholder="Search products..."
      />
    </label>
    <button class="search-button">Search</button>
  </div>
  <div class="navbar">
    <%
      UserDTO user = null;
      if (session != null) {
        user = (UserDTO) session.getAttribute("user");
      }
      if (user != null) {
        if (user.getRoles().stream().anyMatch((RoleDTO r) -> "admin".equals(r.getName()))) {
    %>
    <a href="${pageContext.request.contextPath}/admin/index.jsp">
      <img class="icon" src="${pageContext.request.contextPath}/assets/admin.svg" alt="icon" />
    </a>
    <%
      }
      if (user.getRoles().stream().anyMatch((RoleDTO r) -> "warehouse".equals(r.getName())) ||
              user.getPermissions().stream().anyMatch((PermissionDTO r) -> "view_orders".equals(r.getName()))) {
    %>
    <a href="${pageContext.request.contextPath}/warehouse/index.jsp">
      <img class="icon" src="${pageContext.request.contextPath}/assets/warehouse.svg" alt="icon" />
    </a>
    <%
      }
    %>
    <a href="${pageContext.request.contextPath}/customer/cart/index.jsp">
      <img class="icon" src="${pageContext.request.contextPath}/assets/shopping-cart.svg" alt="icon" />
    </a>
    <a href="${pageContext.request.contextPath}/logout.jsp">
      <img class="icon" src="${pageContext.request.contextPath}/assets/logout.svg" alt="icon" />
    </a>
    <%
    } else {
    %>
    <a href="${pageContext.request.contextPath}/login.jsp">
      <p>Login</p>
    </a>
    <%
      }
    %>
  </div>
</header>
