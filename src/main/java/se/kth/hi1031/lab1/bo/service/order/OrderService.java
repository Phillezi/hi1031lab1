package se.kth.hi1031.lab1.bo.service.order;

import se.kth.hi1031.lab1.bo.model.order.Order;
import se.kth.hi1031.lab1.bo.model.order.Status;
import se.kth.hi1031.lab1.bo.model.product.Product;
import se.kth.hi1031.lab1.bo.model.product.Property;
import se.kth.hi1031.lab1.bo.model.product.Category;
import se.kth.hi1031.lab1.bo.model.user.Permission;
import se.kth.hi1031.lab1.bo.model.user.User;
import se.kth.hi1031.lab1.bo.service.PermissionException;
import se.kth.hi1031.lab1.bo.service.ServiceException;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.dao.order.OrderDAO;
import se.kth.hi1031.lab1.db.dao.product.ProductDAO;
import se.kth.hi1031.lab1.ui.dto.order.OrderDTO;
import se.kth.hi1031.lab1.ui.dto.order.StatusDTO;
import se.kth.hi1031.lab1.ui.dto.product.PropertyDTO;
import se.kth.hi1031.lab1.ui.dto.product.ProductDTO;
import se.kth.hi1031.lab1.ui.dto.product.CategoryDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;
import se.kth.hi1031.lab1.bo.model.user.Role;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;
import se.kth.hi1031.lab1.ui.dto.user.PermissionDTO;

import java.sql.Timestamp;
import java.util.*;

/**
 * Service class for managing orders within the application.
 * This class provides methods to create and retrieve orders based on user permissions and roles.
 *
 * <p>The {@code OrderService} class encapsulates business logic related to orders, including
 * creating new orders and fetching existing ones. It ensures that appropriate permission checks
 * are in place to enforce access control based on the user's roles and permissions.</p>
 */
public class OrderService {

    /**
     * Creates a new order for a customer.
     *
     * <p>The method verifies whether the user has the necessary permissions to create the order.
     * If the user is the same as the customer, the order can be created directly. If the user has
     * the "update_orders" permission, they can create an order on behalf of another customer.</p>
     *
     * @param user            The user attempting to create the order.
     * @param customer        The customer for whom the order is being created.
     * @param deliveryAddress The delivery address for the order.
     * @param products        A list of products included in the order.
     * @return An {@link OrderDTO} representing the created order.
     * @throws PermissionException if the user lacks the necessary permissions to create the order.
     */
    public static OrderDTO createOrder(UserDTO user, UserDTO customer, String deliveryAddress, List<ProductDTO> products) {
        if (user.equals(customer)) {
            // order is created by the person ordering the items

        } else if (
                user.getPermissions()
                        .contains(new PermissionDTO("update_orders"))
        ) {
            // user has permission to edit orders
        } else {
            throw new PermissionException("User " + user + " does not have permission update_orders but tried to create order on customer that is not self.");
        }

        Map<Integer, Integer> idAndQuantity = new HashMap<>();
        for (ProductDTO product : products) {
            idAndQuantity.put(product.getId(), product.getQuantity());
        }

        List<Product> products_ = ProductDAO.getProductsByIds(idAndQuantity
                        .keySet()
                        .stream()
                        .toList()).stream()
                .map((ProductDAO p) -> {
                    p.setQuantity(idAndQuantity.get(p.getId()));
                    return p.toProduct();
                })
                .toList();

        Order newOrder = new Order(null,
                new Timestamp(System.currentTimeMillis()),
                null,
                deliveryAddress,
                new User(customer),
                products_,
                List.of(new Status("received", new Timestamp(System.currentTimeMillis())))
        );
        try {
            OrderDAO.createOrder(newOrder);
            return newOrder.toDTO();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Retrieves all orders accessible to the specified user.
     *
     * <p>This method checks the user's permissions to determine if they can view all orders or only
     * their own. It constructs a {@code User} object from the provided {@link UserDTO}, including roles
     * and permissions, and performs the necessary permission checks.</p>
     *
     * @param user The user for whom to retrieve orders.
     * @return A list of {@link OrderDTO} representing all accessible orders.
     * @throws PermissionException if the user lacks permission to view orders.
     */
    public static List<OrderDTO> getAllOrders(UserDTO user) {
        List<Permission> permissions = user.getPermissions().stream().map((PermissionDTO p) -> new Permission(p.getName())).toList();
        List<Role> roles = user.getRoles().stream().map((RoleDTO r) -> new Role(r.getName(), permissions)).toList();
        User user_ = new User(user.getId(), user.getName(), user.getEmail(), user.getPassword(), roles, permissions);
        System.out.println("getallorders: " + user);
        System.out.println("permissions: " + permissions);
        if (
                permissions
                        .stream()
                        .anyMatch((Permission p) -> p.getName().equalsIgnoreCase("view_orders"))
        ) {
            System.out.println("has permission!!");
            List<OrderDAO> orders = OrderDAO.getOrders();
            System.out.println("found: " + orders);
            return orders.stream().map(OrderDAO::toOrder).map(Order::toDTO).toList();
        } else if (user != null && user.getId() != null) {
            List<OrderDAO> orders = OrderDAO.getOrdersByCustomer(user_);
            System.out.println("found: " + orders);
            return orders.stream().map(OrderDAO::toOrder).map(Order::toDTO).toList();
        }
        throw new PermissionException("User " + user + " cant view orders");
    }

    /**
     * Retrieves an order by its ID.
     *
     * <p>This method attempts to fetch an order from the database based on the provided ID.
     * If the order is found, it is returned as an {@link OrderDTO}. If the order is not found,
     * an empty {@link Optional} is returned.</p>
     *
     * @param id The ID of the order to retrieve.
     * @return An {@link Optional<OrderDTO>} containing the order if found, or empty if not found.
     */
    // todo auth
    public static Optional<OrderDTO> getOrderById(int id) {
        Optional<OrderDAO> order = OrderDAO.getOrderById(id);
        if (order.isPresent()) {
            return Optional.of(order.get().toOrder().toDTO());
        }
        return Optional.empty();
    }

    // todo auth
    public static List<OrderDTO> getOrdersWithStatus(String... statuses) {
        try {
            List<OrderDAO> orders = OrderDAO.getOrdersByStatus(statuses);
            return orders.stream().map(OrderDAO::toOrder).map(Order::toDTO).toList();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}