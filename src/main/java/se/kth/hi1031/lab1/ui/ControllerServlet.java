package se.kth.hi1031.lab1.ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.kth.hi1031.lab1.ui.controllers.*;
import se.kth.hi1031.lab1.ui.dto.user.PermissionDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

import java.io.IOException;

/**
 * Servlet controller that manages application requests and user navigation.
 *
 * <p>This servlet routes incoming requests based on the requested URI and the 
 * action parameter. It handles both GET and POST requests for various actions 
 * such as login, registration, viewing user lists, and accessing the admin page.</p>
 */
@WebServlet(name = "controller", urlPatterns = { "/controller" })
public class ControllerServlet extends HttpServlet {

    /**
     * Handles HTTP GET requests for the application.
     *
     * <p>This method determines the appropriate action based on the request URI,
     * checking user authentication and roles to control access to resources.</p>
     *
     * @param req  The HttpServletRequest object that contains the request data.
     * @param resp The HttpServletResponse object that contains the response data.
     * @throws ServletException If an error occurs during the request processing.
     * @throws IOException      If an input or output error occurs during the 
     *                          forwarding process.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] pathParts = req.getRequestURI().split("/");
        String path = pathParts[pathParts.length - 1];
        HttpSession session = req.getSession(false);

        UserDTO user = null;
        if (session != null) {
            user = (UserDTO) session.getAttribute("user");
        }
        boolean isLoggedIn = user != null;
        System.out.println(path);

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

    /**
     * Handles HTTP POST requests for the application.
     *
     * <p>This method processes user actions based on the submitted action parameter, 
     * delegating the work to specific controller classes for registration, login, 
     * and cart management.</p>
     *
     * @param req  The HttpServletRequest object that contains the request data.
     * @param resp The HttpServletResponse object that contains the response data.
     * @throws ServletException If an error occurs during the request processing.
     * @throws IOException      If an input or output error occurs during the 
     *                          redirection process.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession(false);

        UserDTO user = null;
        if (session != null) {
            user = (UserDTO) session.getAttribute("user");
        }
        boolean isLoggedIn = user != null;
        System.out.println(action);

        switch (action) {
            case "clear-error": {
                ErrorController.post(req, resp);
                break;
            }
            case "register": {
                RegisterController.post(req, resp);
                break;
            }
            case "login": {
                LoginController.post(req, resp);
                break;
            }
            case "logout": {
                if (isLoggedIn) {
                    session.invalidate();
                }
                redirectToLogin(resp, req);
                break;
            }
            case "cart": {
                CartController.post(req, resp);
                break;
            }
            case "checkout": {
                CheckoutController.post(req, resp);
                break;
            }
            case "warehouse": {
                WarehouseController.post(req, resp);
                break;
            }
            case "/":
            default: {
                req.getRequestDispatcher("/errors/404.jsp").forward(req, resp);
                break;
            }
        }
    }

    /**
     * Retrieves the currently logged-in user from the session.
     *
     * @param req The HttpServletRequest object that contains the request data.
     * @return The UserDTO object representing the logged-in user, or null if no user is logged in.
     */
    private UserDTO getCurrentUser(HttpServletRequest req) {
        return (UserDTO) req.getSession().getAttribute("user");
    }

    /**
     * Checks if the specified user has the given role.
     *
     * @param user The UserDTO object representing the user to check.
     * @param role The role name to check against the user's roles.
     * @return True if the user has the specified role, false otherwise.
     */
    private boolean hasRole(UserDTO user, String role) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getName().equals(role));
    }

    /**
     * Checks if the specified user has the given permission.
     *
     * @param user      The UserDTO object representing the user to check.
     * @param permission The permission name to check against the user's permissions.
     * @return True if the user has the specified permission, false otherwise.
     */
    private boolean hasPermission(UserDTO user, String permission) {
        return user.getPermissions().contains(new PermissionDTO(permission));
    }

    /**
     * Redirects the user to the login page.
     *
     * @param resp The HttpServletResponse object that contains the response data.
     * @param req  The HttpServletRequest object that contains the request data.
     * @throws IOException If an input or output error occurs during the redirection.
     */
    private void redirectToLogin(HttpServletResponse resp, HttpServletRequest req) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

    /**
     * Handles unauthorized access by redirecting to the appropriate page.
     *
     * @param isLoggedIn Indicates whether the user is logged in or not.
     * @param req       The HttpServletRequest object that contains the request data.
     * @param resp      The HttpServletResponse object that contains the response data.
     * @throws IOException If an input or output error occurs during the redirection.
     */
    private void handleUnauthorizedAccess(boolean isLoggedIn, HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        if (!isLoggedIn) {
            redirectToLogin(resp, req);
        } else {
            resp.sendRedirect(req.getContextPath() + "/errors/401.jsp");
        }
    }
}
