package se.kth.hi1031.lab1.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class Order {
    private final int id;
    private final Timestamp timestamp;
    private final User customer;
    private final ArrayList<Product> products;
}
