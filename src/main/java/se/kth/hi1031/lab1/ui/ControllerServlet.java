package se.kth.hi1031.lab1.ui;

import se.kth.hi1031.lab1.bo.model.order.Order;
import se.kth.hi1031.lab1.bo.model.product.Category;
import se.kth.hi1031.lab1.bo.model.product.Product;
import se.kth.hi1031.lab1.bo.model.product.Property;
import se.kth.hi1031.lab1.bo.model.user.Permission;
import se.kth.hi1031.lab1.bo.model.user.Role;
import se.kth.hi1031.lab1.bo.model.user.User;
import se.kth.hi1031.lab1.bo.service.order.OrderService;
import se.kth.hi1031.lab1.bo.service.product.ProductService;
import se.kth.hi1031.lab1.bo.service.user.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.kth.hi1031.lab1.ui.dto.user.PermissionDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "MainServlet", urlPatterns = {"/main"})
public class ControllerServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        UserDTO currentUser = getCurrentUser(req);

        switch (action) {
            case "getUsers":
                handleGetUsers(resp);
                break;
            case "getProducts":
                handleGetProducts(resp);
                break;
            case "getOrders":
                handleGetOrders(resp);
                break;
            default:
                resp.getWriter().write("Invalid action!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        UserDTO currentUser = getCurrentUser(req);

        switch (action) {
            case "createUser":
                if (hasPermission(currentUser, "administrate")) {
                    handleCreateUser(req, resp);
                } else {
                    resp.getWriter().write("Permission denied!");
                }
                break;
            case "createProduct":
                if (hasPermission(currentUser, "add_products")) {
                    handleCreateProduct(req, resp);
                } else {
                    resp.getWriter().write("Permission denied!");
                }
                break;
            case "createOrder":
                if (hasPermission(currentUser, "update_orders")) {
                    handleCreateOrder(req, resp);
                } else {
                    resp.getWriter().write("Permission denied!");
                }
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
