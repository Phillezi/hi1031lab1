package se.kth.hi1031.lab1.bo.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private boolean removed;
    private ArrayList<Category> categories;
    private ArrayList<String> images;
    private ArrayList<Property> properties;
}
