package se.kth.hi1031.lab1.bo.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.bo.model.product.Product;
import se.kth.hi1031.lab1.bo.model.user.User;
import se.kth.hi1031.lab1.ui.dto.order.OrderDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    public OrderDTO toDTO() {
        return new OrderDTO(
                this.id,
                this.created,
                this.delivered,
                this.deliveryAddress,
                this.customer.toDTO(),
                this.products.stream().map(Product::toDTO).toList(),
                this.statuses.stream().map(Status::toDTO).toList()
        );
    }
}
