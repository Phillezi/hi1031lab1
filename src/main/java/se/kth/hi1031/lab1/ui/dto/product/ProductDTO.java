package se.kth.hi1031.lab1.ui.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private List<CategoryDTO> categories;
    private List<String> images;
    private List<PropertyDTO> properties;
}
