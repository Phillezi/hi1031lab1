package se.kth.hi1031.lab1.bo.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.bo.model.product.Product;
import se.kth.hi1031.lab1.bo.model.user.User;
import se.kth.hi1031.lab1.db.dao.order.OrderDAO;
import se.kth.hi1031.lab1.ui.dto.order.OrderDTO;
import se.kth.hi1031.lab1.ui.dto.product.ProductDTO;
import se.kth.hi1031.lab1.ui.dto.order.StatusDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;



/**
 * Represents an order placed by a customer, containing key details such as
 * order ID, timestamps for creation and delivery, delivery address,
 * customer information, and the products and statuses associated with the order.
 * 
 * <p>This class uses Lombok annotations to automatically generate
 * getters, setters, and an all-arguments constructor.</p>
 */
@Getter
@Setter
@AllArgsConstructor
public class Order {
    private Integer id;
    private Timestamp created;
    private Timestamp delivered;
    private String deliveryAddress;
    private User customer;
    private List<Product> products;
    private List<Status> statuses;

    public Order(OrderDTO order) {
        this.id = order.getId();
        this.created = order.getCreated();
        this.delivered = order.getDelivered();
        this.deliveryAddress = order.getDeliveryAddress();
        this.customer = order.getCustomer() != null ? new User(order.getCustomer()) : null;
        this.products = order.getProducts() != null ? order.getProducts()
                                .stream()
                                .map((ProductDTO p) -> new Product(p))
                                .toList() : null;
        this.statuses = order.getStatuses() != null ? order.getStatuses()
                                .stream()
                                .map((StatusDTO s) -> new Status(s))
                                .toList() : null;
    }
    

    /**
     * Converts the current {@code Order} object to a {@link OrderDTO}.
     *
     * @return an {@code OrderDTO} containing the order's data for transfer.
     */
    public OrderDTO toDTO() {
        return new OrderDTO(
                this.id,
                this.created,
                this.delivered,
                this.deliveryAddress,
                this.customer != null ? this.customer.toDTO() : null,
                this.products != null ? this.products.stream().map(Product::toDTO).toList() : null,
                this.statuses != null ? this.statuses.stream().map(Status::toDTO).toList() : null
        );
    }

    /**
     * Converts the current {@code Order} object to a {@link OrderDAO}.
     *
     * @return an {@code OrderDAO} containing the order's data for database storage.
     */
    public OrderDAO toDAO() {
        return new OrderDAO(
                this.id,
                this.created,
                this.delivered,
                this.deliveryAddress,
                this.customer != null ? this.customer.toDAO() : null,
                this.products != null ? this.products.stream().map(Product::toDAO).toList() : null,
                this.statuses != null ? this.statuses.stream().map(Status::toDAO).toList() : null
        );
    }
}
