package se.kth.hi1031.lab1.ui.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.kth.hi1031.lab1.bo.service.ServiceException;
import se.kth.hi1031.lab1.bo.service.user.UserService;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

import java.io.IOException;

/**
 * Servlet controller for managing user login functionality.
 *
 * <p>This controller handles HTTP GET and POST requests related to
 * user login, including displaying the login form and processing
 * login submissions.</p>
 */
public class LoginController extends HttpServlet {

    /**
     * Handles the HTTP GET request to display the login page.
     *
     * <p>This method forwards the request to the "login.jsp" page for
     * rendering the login form.</p>
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
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    /**
     * Handles the HTTP POST request to log a user in.
     *
     * <p>This method retrieves the email and password from the request,
     * attempts to log the user in via the UserService, and manages the
     * user session. If login is successful, the user is redirected to
     * the index page; otherwise, they are redirected to an error page.</p>
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
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        try {
            UserDTO user = UserService.login(new UserDTO(null, password, email, password, null, null));

            if (user != null) {
                session.setAttribute("user", user);
                resp.sendRedirect("/index.jsp");
                return;
            }
        } catch (ServiceException e) {
            session.setAttribute("error", e.getMessage());
        }
        resp.sendRedirect("/login.jsp");
    }
}
