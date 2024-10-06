package se.kth.hi1031.lab1.ui.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.kth.hi1031.lab1.bo.service.PermissionException;
import se.kth.hi1031.lab1.bo.service.ServiceException;
import se.kth.hi1031.lab1.bo.service.product.CategoryService;
import se.kth.hi1031.lab1.ui.dto.product.CategoryDTO;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

import java.io.IOException;

/**
 * UI Controller that handles incoming requests for managing categories.
 * Converts from UI related data formats to DTO to send to related Service.
 */
public class CategoryController extends HttpServlet {

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
                addCategory(req, resp);
                return;
            case "update":
                updateCategory(req, resp);
                return;
            default:
                session.setAttribute("error", "Invalid operation");
                break;

        }

        resp.sendRedirect(req.getHeader("Referer"));
    }

    private static void addCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        try {
            CategoryService.createCategory(user, new CategoryDTO(name, description));
        } catch (PermissionException | ServiceException e) {
            session.setAttribute("error", e.getMessage());
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }
        resp.sendRedirect("/admin/products/categories");
    }

    private static void updateCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        try {
            CategoryService.updateCategory(user, new CategoryDTO(name, description));
        } catch (PermissionException | ServiceException e) {
            session.setAttribute("error", e.getMessage());
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }
        resp.sendRedirect("/admin/products/categories");
    }
}