package se.kth.hi1031.lab1.bo.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.bo.product.Product;
import se.kth.hi1031.lab1.bo.user.User;

import java.sql.Timestamp;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class Order {
    private Integer id;
    private Timestamp created;
    private Timestamp delivered;
    private String deliveryAddress;
    private User customer;
    private ArrayList<Product> products;
    private ArrayList<Status> statuses;
}
