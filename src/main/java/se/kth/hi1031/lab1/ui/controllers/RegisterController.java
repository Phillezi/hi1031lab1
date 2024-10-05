package se.kth.hi1031.lab1.ui.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.kth.hi1031.lab1.bo.service.ServiceException;
import se.kth.hi1031.lab1.bo.service.user.RoleService;
import se.kth.hi1031.lab1.bo.service.user.UserService;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet controller for managing user registration functionality.
 *
 * <p>This controller handles HTTP GET and POST requests related to
 * user registration, including displaying the registration form and
 * processing registration submissions.</p>
 */
public class RegisterController extends HttpServlet {

    /**
     * Handles the HTTP GET request to display the registration page.
     *
     * <p>This method forwards the request to the "register.jsp" page for
     * rendering the registration form.</p>
     *
     * @param req  The HttpServletRequest object that contains the request
     *             data.
     * @param resp The HttpServletResponse object that contains the response
     *             data.
     * @throws ServletException If an error occurs during the request
     *                          dispatching.
     * @throws IOException      If an input or output error occurs during
     *                          the forwarding process.
     */
    public static void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    /**
     * Handles the HTTP POST request to register a new user.
     *
     * <p>This method retrieves user information from the request,
     * creates a new user using the UserService, and redirects
     * the user to the home page upon successful registration.</p>
     *
     * @param req  The HttpServletRequest object that contains the request
     *             data.
     * @param resp The HttpServletResponse object that contains the response
     *             data.
     * @throws ServletException If an error occurs during the request
     *                          processing.
     * @throws IOException      If an input or output error occurs during
     *                          the redirection.
     */
    public static void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role").toLowerCase();
        HttpSession session = req.getSession();
        if (RoleService.getAvailableRoles().stream().noneMatch((String r) -> r.equals(role))) {
            session.setAttribute("error", "Couldn't create user. Role " + role + " is not available.");
            resp.sendRedirect("/register.jsp");
            return;
        }

        ArrayList<RoleDTO> roles = new ArrayList<>(1);
        roles.add(new RoleDTO(role, null));

        try {
            // Todo: this doesnt contain all permissions of the user causing bugs, make sure it does
            UserDTO user = UserService.createUser(new UserDTO(null, name, email, password, roles, new ArrayList<>()));
            if (user != null) {
                session.setAttribute("user", user);
                resp.sendRedirect("/");
                return;
            }
        } catch (ServiceException e) {
            session.setAttribute("error", "Couldn't create user. " + e.getMessage());
        }
        resp.sendRedirect("/register.jsp");
    }
}
