package se.kth.hi1031.lab1.ui.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        String role = req.getParameter("role");
        System.out.println("registering user: " + name + " , " + email + " , " + password + " , " + role);

        ArrayList<RoleDTO> roles = new ArrayList<>(1);
        roles.add(new RoleDTO(role, null));

        UserService.createUser(new UserDTO(null, name, email, password, roles, new ArrayList<>()));
        // add session here
        resp.sendRedirect("/");
    }
}
