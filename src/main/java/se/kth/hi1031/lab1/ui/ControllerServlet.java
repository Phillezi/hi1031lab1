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
        User currentUser = getCurrentUser(req); // Get the current user

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
        User currentUser = getCurrentUser(req); // Get the current user

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

    // Example of a method to get the current user from the request
    private User getCurrentUser(HttpServletRequest req) {
        // Placeholder: In a real application, this should retrieve the user from the session or security context
        return (User) req.getSession().getAttribute("currentUser");
    }

    // Check if the user has a specific permission
    private boolean hasPermission(User user, String permissionName) {
        return UserService.getUserRoles(user).stream()
                .anyMatch(permission -> permission.getName().equals(permissionName));
    }

    // Handle GET request for users
    private void handleGetUsers(HttpServletResponse resp) throws IOException {
        List<User> users = userService.getAllUsers();
        resp.setContentType("application/json");
        resp.getWriter().write(usersToJson(users));
    }

    // Handle GET request for products
    private void handleGetProducts(HttpServletResponse resp) throws IOException {
        List<Product> products = productService.getAllProducts();
        resp.setContentType("application/json");
        resp.getWriter().write(productsToJson(products));
    }

    // Handle GET request for orders
    private void handleGetOrders(HttpServletResponse resp) throws IOException {
        List<Order> orders = orderService.getAllOrders();
        resp.setContentType("application/json");
        resp.getWriter().write(ordersToJson(orders));
    }

    // Handle POST request to create a user
    private void handleCreateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = userService.createUser(null, name, email, password);
        resp.getWriter().write("User created: " + user.getName());
    }

    // Handle POST request to create a product
    private void handleCreateProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        double price = Double.parseDouble(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        ArrayList<Category> categories = new ArrayList<>(); // Populate categories as needed
        ArrayList<String> images = new ArrayList<>();       // Populate images as needed
        ArrayList<Property> properties = new ArrayList<>(); // Populate properties as needed

        Product product = productService.createProduct(name, description, price, quantity, categories, images, properties);
        resp.getWriter().write("Product created: " + product.getName());
    }

    // Handle POST request to create an order
    private void handleCreateOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer userId = Integer.parseInt(req.getParameter("userId"));
        String deliveryAddress = req.getParameter("deliveryAddress");

        User user = userService.getUserById(userId).orElse(null);
        if (user != null) {
            ArrayList<Product> products = new ArrayList<>(); // Populate products as needed

            Order order = orderService.createOrder(user, deliveryAddress, products);
            resp.getWriter().write("Order created for user: " + user.getName());
        } else {
            resp.getWriter().write("User not found!");
        }
    }

    // Helper methods to convert objects to JSON-like format (for simplicity)

    private String usersToJson(List<User> users) {
        StringBuilder sb = new StringBuilder("[");
        for (User user : users) {
            sb.append("{")
                    .append("\"id\":").append(user.getId()).append(",")
                    .append("\"name\":\"").append(user.getName()).append("\",")
                    .append("\"email\":\"").append(user.getEmail()).append("\"")
                    .append("},");
        }
        if (!users.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1); // Remove trailing comma
        }
        sb.append("]");
        return sb.toString();
    }

    private String productsToJson(List<Product> products) {
        StringBuilder sb = new StringBuilder("[");
        for (Product product : products) {
            sb.append("{")
                    .append("\"id\":").append(product.getId()).append(",")
                    .append("\"name\":\"").append(product.getName()).append("\",")
                    .append("\"price\":").append(product.getPrice())
                    .append("},");
        }
        if (!products.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1); // Remove trailing comma
        }
        sb.append("]");
        return sb.toString();
    }

    private String ordersToJson(List<Order> orders) {
        StringBuilder sb = new StringBuilder("[");
        for (Order order : orders) {
            sb.append("{")
                    .append("\"id\":").append(order.getId()).append(",")
                    .append("\"customer\":\"").append(order.getCustomer().getName()).append("\"")
                    .append("},");
        }
        if (!orders.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1); // Remove trailing comma
        }
        sb.append("]");
        return sb.toString();
    }
}
