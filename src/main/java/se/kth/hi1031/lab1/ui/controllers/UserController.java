package se.kth.hi1031.lab1.ui.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.kth.hi1031.lab1.bo.service.PermissionException;
import se.kth.hi1031.lab1.bo.service.ServiceException;
import se.kth.hi1031.lab1.bo.service.user.UserService;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * UI Controller that handles actions related to managing users.
 * Converts from UI related data formats into DTO classes that are handled by the service classes.
 */
public class UserController extends HttpServlet {
    /**
     * Handles post requests from forms with different user operations.
     */
    public static void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("/errors/401.jsp");
            return;
        }
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/errors/401.jsp");
            return;
        }
        if (operation == null || operation.isEmpty()) {
            session.setAttribute("error", "Invalid operation");
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }
        if (user.getRoles().stream().noneMatch((RoleDTO r) -> "admin".equalsIgnoreCase(r.getName()))) {
            resp.sendRedirect("/errors/403.jsp");
            return;
        }

        switch (operation) {
            case "add":
                addUser(req, resp);
                return;
            case "update":
                updateUser(req, resp);
                return;
            case "delete":
                deleteUser(req, resp);
                return;
            default:
                session.setAttribute("error", "Invalid operation");
                break;

        }

        resp.sendRedirect(req.getHeader("Referer"));
    }

    /**
     * Handles update user requests.
     */
    private static void updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        String userIdStr = req.getParameter("userId");
        if (userIdStr == null || userIdStr.isEmpty()) {
            session.setAttribute("error", "Invalid operation");
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }

        int userId = Integer.parseInt(userIdStr);

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        String[] selectedRoles = req.getParameterValues("roles");
        List<String> roles = (selectedRoles != null) ? Arrays.asList(selectedRoles) : new ArrayList<>();
        List<RoleDTO> userRoles = roles.stream().map((String role) -> new RoleDTO(role, null)).toList();

        UserDTO userToUpdate = new UserDTO(userId, name, email, password, userRoles, new ArrayList<>());

        try {
            UserService.updateUser(user, userToUpdate);
        } catch (PermissionException | ServiceException e) {
            session.setAttribute("error", e.getMessage());
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }

        resp.sendRedirect("/admin/users/");
    }

    /**
     * Handles delete user requests.
     */
    private static void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        String userIdStr = req.getParameter("userId");
        if (userIdStr == null || userIdStr.isEmpty()) {
            session.setAttribute("error", "Invalid operation");
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }

        int userId = Integer.parseInt(userIdStr);
        try {
            UserService.deleteUserById(user, userId);
        } catch (PermissionException | ServiceException e) {
            session.setAttribute("error", e.getMessage());
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }

        resp.sendRedirect("/admin/users/");
    }

    /**
     * Handles add user requests.
     */
    private static void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        HttpSession session = req.getSession(false);

        String[] selectedRoles = req.getParameterValues("roles");
        List<String> roles = (selectedRoles != null) ? Arrays.asList(selectedRoles) : new ArrayList<>();
        List<RoleDTO> userRoles = roles.stream().map((String role) -> new RoleDTO(role, null)).toList();

        UserDTO userToAdd = new UserDTO(null, name, email, password, userRoles, new ArrayList<>());

        try {
            UserService.createUser(userToAdd);
        } catch (PermissionException | ServiceException e) {
            session.setAttribute("error", e.getMessage());
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }

        resp.sendRedirect("/admin/users/");
    }
}
