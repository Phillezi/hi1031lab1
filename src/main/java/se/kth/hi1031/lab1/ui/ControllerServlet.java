package se.kth.hi1031.lab1.ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.kth.hi1031.lab1.ui.controllers.LoginController;
import se.kth.hi1031.lab1.ui.controllers.RegisterController;
import se.kth.hi1031.lab1.ui.dto.user.PermissionDTO;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

import java.io.IOException;

@WebServlet(name = "MainServlet", urlPatterns = { "/admin/*", "/users/*", "/login", "/logout", "/" })
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        HttpSession session = req.getSession(false);

        UserDTO user = null;
        if (session != null) {
            user = (UserDTO) session.getAttribute("user");
        }
        boolean isLoggedIn = user != null;

        switch (path) {
            case "/admin": {
                if (isLoggedIn && hasRole(user, "admin")) {
                    req.getRequestDispatcher("admin.jsp").forward(req, resp);
                } else {
                    handleUnauthorizedAccess(isLoggedIn, req, resp);
                }
                break;
            }
            case "/users": {
                if (isLoggedIn) {
                    req.getRequestDispatcher("users.jsp").forward(req, resp);
                } else {
                    redirectToLogin(resp, req);
                }
                break;
            }
            case "/login": {
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                break;
            }
            case "/logout": {
                if (isLoggedIn) {
                    session.invalidate();
                }
                redirectToLogin(resp, req);
                break;
            }
            case "/":
            default: {
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        UserDTO currentUser = getCurrentUser(req);

        switch (action) {
            case "register":
                RegisterController.post(req, resp);
                break;
            case "login":
                LoginController.post(req, resp);
                break;
            default:
                resp.getWriter().write("Invalid action!");
        }
    }

    private UserDTO getCurrentUser(HttpServletRequest req) {
        return (UserDTO) req.getSession().getAttribute("user");
    }

    private boolean hasRole(UserDTO user, String role) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getName().equals(role));
    }

    private boolean hasPermission(UserDTO user, String permission) {
        return user.getPermissions().contains(new PermissionDTO(permission));
    }

    private void redirectToLogin(HttpServletResponse resp, HttpServletRequest req) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

    private void handleUnauthorizedAccess(boolean isLoggedIn, HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        if (!isLoggedIn) {
            redirectToLogin(resp, req);
        } else {
            resp.sendRedirect(req.getContextPath() + "/403.jsp");
        }
    }
}
