package se.kth.hi1031.lab1.bo.service.order;

import se.kth.hi1031.lab1.bo.model.order.Order;
import se.kth.hi1031.lab1.bo.model.product.Product;
import se.kth.hi1031.lab1.bo.model.user.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private final List<Order> orders = new ArrayList<>();

    public Order createOrder(User customer, String deliveryAddress, ArrayList<Product> products) {
        Order newOrder = new Order(null, new Timestamp(System.currentTimeMillis()), null, deliveryAddress, customer, products, new ArrayList<>());
        orders.add(newOrder);
        return newOrder;
    }
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public Optional<Order> getOrderById(Integer id) {
        return orders.stream().filter(order -> order.getId().equals(id)).findFirst();
    }

    public Optional<Order> markOrderAsDelivered(Integer orderId) {
        Optional<Order> orderOptional = getOrderById(orderId);
        orderOptional.ifPresent(order -> {
            order.setDelivered(new Timestamp(System.currentTimeMillis()));
        });
        return orderOptional;
    }

    public Optional<Order> updateDeliveryAddress(Integer orderId, String newAddress) {
        Optional<Order> orderOptional = getOrderById(orderId);
        orderOptional.ifPresent(order -> {
            order.setDeliveryAddress(newAddress);
        });
        return orderOptional;
    }

    public Optional<Order> addProductToOrder(Integer orderId, Product product) {
        Optional<Order> orderOptional = getOrderById(orderId);
        orderOptional.ifPresent(order -> order.getProducts().add(product));
        return orderOptional;
    }

    public Optional<Order> removeProductFromOrder(Integer orderId, Product product) {
        Optional<Order> orderOptional = getOrderById(orderId);
        orderOptional.ifPresent(order -> order.getProducts().remove(product));
        return orderOptional;
    }

    public boolean deleteOrderById(Integer id) {
        return orders.removeIf(order -> order.getId().equals(id));
    }
}