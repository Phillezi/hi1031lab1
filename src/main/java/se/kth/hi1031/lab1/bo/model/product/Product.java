package se.kth.hi1031.lab1.bo.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.ui.dto.product.ProductDTO;

import java.util.ArrayList;
import java.util.List;

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
    private List<Category> categories;
    private List<String> images;
    private List<Property> properties;

    public ProductDTO toDTO() {
        return new ProductDTO(
                this.id,
                this.name,
                this.description,
                this.price,
                this.quantity,
                this.removed,
                this.categories.stream().map(Category::toDTO).toList(),
                new ArrayList<>(this.images),
                this.properties.stream().map(Property::toDTO).toList()
        );
    }
}
