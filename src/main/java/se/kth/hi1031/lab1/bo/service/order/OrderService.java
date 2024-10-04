package se.kth.hi1031.lab1.bo.service.order;

import se.kth.hi1031.lab1.bo.model.order.Order;
import se.kth.hi1031.lab1.bo.model.product.Product;
import se.kth.hi1031.lab1.bo.model.user.Permission;
import se.kth.hi1031.lab1.bo.model.user.User;
import se.kth.hi1031.lab1.bo.service.PermissionException;
import se.kth.hi1031.lab1.db.dao.order.OrderDAO;
import se.kth.hi1031.lab1.ui.dto.order.OrderDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;
import se.kth.hi1031.lab1.bo.model.user.Role;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;
import se.kth.hi1031.lab1.ui.dto.user.PermissionDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderService {

    public static OrderDTO createOrder(User user, User customer, String deliveryAddress, ArrayList<Product> products) {
        if (user.equals(customer)) {
            // order is created by the person ordering the items

        } else if (
                user.getPermissions()
                .contains(new Permission("update_orders"))
        ) {
            // user has permission to edit orders
        } else {
            throw new PermissionException("User " + user + " does not have permission update_orders but tried to create order on customer that is not self.");
        }

        Order newOrder = new Order(null, new Timestamp(System.currentTimeMillis()), null, deliveryAddress, customer, products, new ArrayList<>());
        OrderDAO.createOrder(newOrder);
        return newOrder.toDTO();
    }
    public static List<OrderDTO> getAllOrders(UserDTO user) {
        System.out.println("getallorders: " +user);
        if (
                user.getPermissions()
                        .contains(new Permission("view_orders"))
        ) {
            List<OrderDAO> orders = OrderDAO.getOrders();
            System.out.println("found: " +orders);
            return orders.stream().map(OrderDAO::toOrder).map(Order::toDTO).toList();
        } else if (user != null && user.getId() != null) {
            List<Permission> permissions = user.getPermissions().stream().map((PermissionDTO p) -> new Permission(p.getName())).toList();
            List<Role> roles = user.getRoles().stream().map((RoleDTO r) -> new Role(r.getName(), permissions)).toList();
            User user_ = new User(user.getId(), user.getName(), user.getEmail(), user.getPassword(), roles, permissions);
            List<OrderDAO> orders = OrderDAO.getOrdersByCustomer(user_);
            System.out.println("found: " +orders);
            return orders.stream().map(OrderDAO::toOrder).map(Order::toDTO).toList();
        }
        throw new PermissionException("User " + user + " cant view orders");
    }

    public Optional<OrderDTO> getOrderById(int id) {
        Optional<OrderDAO> order = OrderDAO.getOrdersById(id);
        if (order.isPresent()) {
            return Optional.of(order.get().toOrder().toDTO());
        }
        return Optional.empty();
    }

}