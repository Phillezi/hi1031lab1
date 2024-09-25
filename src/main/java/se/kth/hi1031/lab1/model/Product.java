package se.kth.hi1031.lab1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
}
