package se.kth.hi1031.lab1.ui.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.ui.dto.product.ProductDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;
import java.sql.Timestamp;
import java.util.List;

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
