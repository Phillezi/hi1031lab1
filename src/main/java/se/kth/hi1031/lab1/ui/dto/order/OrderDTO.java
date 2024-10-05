package se.kth.hi1031.lab1.ui.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.ui.dto.product.ProductDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

import java.sql.Timestamp;
import java.util.List;

/**
 * Data Transfer Object representing an order in the system.
 *
 * <p>The OrderDTO class is used to encapsulate the details of an order, including
 * information about the customer, the products ordered, the order status, and the
 * timestamps for creation and delivery.</p>
 *
 * <p>This class uses Lombok annotations to automatically generate
 * getters, setters, and an all-arguments constructor.</p>
 */
@Getter
@Setter
@AllArgsConstructor
public class OrderDTO {
    private Integer id;
    private Timestamp created;
    private Timestamp delivered;
    private String deliveryAddress;
    private UserDTO customer;
    private List<ProductDTO> products;
    private List<StatusDTO> statuses;
}
