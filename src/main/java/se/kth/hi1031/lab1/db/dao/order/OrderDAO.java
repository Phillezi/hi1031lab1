package se.kth.hi1031.lab1.db.dao.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.bo.model.user.User;
import se.kth.hi1031.lab1.db.dao.product.ProductDAO;

import java.sql.Timestamp;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class OrderDAO {
    private Integer id;
    private Timestamp created;
    private Timestamp delivered;
    private String deliveryAddress;
    private User customer;
    private ArrayList<ProductDAO> products;
    private ArrayList<StatusDAO> statuses;
}