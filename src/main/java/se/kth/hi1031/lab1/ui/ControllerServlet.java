package se.kth.hi1031.lab1.ui;

import se.kth.hi1031.lab1.bo.service.order.OrderService;
import se.kth.hi1031.lab1.bo.service.user.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.kth.hi1031.lab1.ui.controllers.RegisterController;
import se.kth.hi1031.lab1.ui.dto.user.PermissionDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

import java.io.IOException;

@WebServlet(name = "MainServlet", urlPatterns = { "/main" })
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        UserDTO currentUser = getCurrentUser(req);

        switch (action) {
            case "register":
                RegisterController.post(req, resp);
                break;
            case "users":
                resp.sendRedirect(req.getContextPath() + "/users");
                break;
            default:
                resp.getWriter().write("Invalid action!");
                break;
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
            default:
                resp.getWriter().write("Invalid action!");
        }
    }

    private UserDTO getCurrentUser(HttpServletRequest req) {
        return (UserDTO) req.getSession().getAttribute("user");
    }

    private boolean hasPermission(UserDTO user, String permission) {
        return user.getPermissions().contains(new PermissionDTO(permission));
    }
}
