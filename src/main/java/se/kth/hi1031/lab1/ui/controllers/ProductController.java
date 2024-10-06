package se.kth.hi1031.lab1.ui.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.kth.hi1031.lab1.bo.service.PermissionException;
import se.kth.hi1031.lab1.bo.service.ServiceException;
import se.kth.hi1031.lab1.bo.service.product.ProductService;
import se.kth.hi1031.lab1.ui.dto.product.CategoryDTO;
import se.kth.hi1031.lab1.ui.dto.product.ProductDTO;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * UI Controller that handles incoming requests that are related to managing products.
 * Converts from UI related data formats into DTO classes that are handled by the service classes.
 */
public class ProductController extends HttpServlet {
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
                addProduct(req, resp);
                return;
            case "update":
                updateProduct(req, resp);
                return;
            default:
                session.setAttribute("error", "Invalid operation");
                break;

        }

        resp.sendRedirect(req.getHeader("Referer"));
    }

    private static void updateProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productIdStr = req.getParameter("productId");
        HttpSession session = req.getSession(false);
        if (session == null) {
            return;
        }
        if (productIdStr == null || productIdStr.isEmpty()) {
            session.setAttribute("error", "Invalid operation");
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        int productId = Integer.parseInt(productIdStr);

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String priceStr = req.getParameter("price");
        String quantityStr = req.getParameter("quantity");
        String isRemovedStr = req.getParameter("removed");

        Double price = Double.valueOf(priceStr);
        Integer quantity = Integer.valueOf(quantityStr);
        Boolean isRemoved = Boolean.valueOf(isRemovedStr);

        String[] selectedCategories = req.getParameterValues("categories");
        List<String> categories = (selectedCategories != null) ? Arrays.asList(selectedCategories) : new ArrayList<>();
        List<CategoryDTO> productCategories = categories.stream().map((String category) -> new CategoryDTO(category, null)).toList();

        String[] selectedImages = req.getParameterValues("images");
        List<String> images = (selectedImages != null) ? Arrays.asList(selectedImages) : new ArrayList<>();

        ProductDTO productToUpdate = new ProductDTO(productId, name, description, price, quantity, isRemoved, productCategories, images, new ArrayList<>());

        try {
            ProductService.updateProduct(user, productToUpdate);
        } catch (PermissionException | ServiceException e) {
            session.setAttribute("error", e.getMessage());
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }

        resp.sendRedirect("/admin/products/");
    }

    private static void addProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String priceStr = req.getParameter("price");
        String quantityStr = req.getParameter("quantity");
        String isRemovedStr = req.getParameter("removed");

        Double price = Double.valueOf(priceStr);
        Integer quantity = Integer.valueOf(quantityStr);
        Boolean isRemoved = Boolean.valueOf(isRemovedStr);

        String[] selectedCategories = req.getParameterValues("categories");
        List<String> categories = (selectedCategories != null) ? Arrays.asList(selectedCategories) : new ArrayList<>();
        List<CategoryDTO> productCategories = categories.stream().map((String category) -> new CategoryDTO(category, null)).toList();

        String[] selectedImages = req.getParameterValues("images");
        List<String> images = (selectedImages != null) ? Arrays.asList(selectedImages) : new ArrayList<>();

        ProductDTO product = new ProductDTO(null, name, description, price, quantity, isRemoved, productCategories, images, new ArrayList<>());

        try {
            ProductService.createProduct(user, product);
        } catch (PermissionException | ServiceException e) {
            session.setAttribute("error", e.getMessage());
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }

        resp.sendRedirect("/admin/products/");
    }
}
