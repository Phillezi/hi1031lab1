package se.kth.hi1031.lab1.ui.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private boolean removed;
    private ArrayList<CategoryDTO> categories;
    private ArrayList<String> images;
    private ArrayList<PropertyDTO> properties;
}
