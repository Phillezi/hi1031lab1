<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String errorMessage = (String) session.getAttribute("error");
    if (errorMessage != null) {
%>
<div class="error-message" style="
            position: absolute;
            left: 50%;
            transform: translateX(-50%);
            color: white;
            background-color: #f13e3e;
            padding: 10px 20px;
            border-radius: 5px;
            text-align: center;
            margin-top: 10px;
            width: fit-content;
            margin-left: auto;
            margin-right: auto;
            z-index: 9999;
        ">
    <%= errorMessage %>
    <form action="${pageContext.request.contextPath}/controller?action=clear-error" method="POST"
          style="display:inline;">
        <button type="submit" style="
          background: transparent;
          border: none;
          color: white;
          cursor: pointer;
          padding-left: 10px;
          font-weight: bold;
      ">X
        </button>
    </form>
</div>
<%
    }
    session.removeAttribute("error");
%>