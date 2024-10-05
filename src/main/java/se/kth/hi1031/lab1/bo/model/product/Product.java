package se.kth.hi1031.lab1.bo.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.db.dao.product.ProductDAO;
import se.kth.hi1031.lab1.ui.dto.product.ProductDTO;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a product in the system, including essential details such as its name, price,
 * description, quantity, and other attributes like categories, images, and properties.
 *
 * <p>This class uses Lombok annotations to automatically generate getters, setters,
 * and an all-arguments constructor.</p>
 */
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

    public Product(ProductDTO product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.removed = product.isRemoved();
        this.categories = product.getCategories() != null ? product.getCategories()
                .stream()
                .map(Category::new)
                .toList() : null;
        this.images = product.getImages() != null ? new ArrayList<>(product.getImages()) : null;
        this.properties = product.getProperties() != null ? product.getProperties()
                .stream()
                .map(Property::new)
                .toList() : null;
    }


    /**
     * Converts this {@code Product} object into a {@link ProductDTO},
     * which is a Data Transfer Object (DTO) used for transferring product data across
     * different layers of the application.
     *
     * @return a {@code ProductDTO} containing the product's data.
     */
    public ProductDTO toDTO() {
        return new ProductDTO(
                this.id,
                this.name,
                this.description,
                this.price,
                this.quantity,
                this.removed,
                this.categories != null ? this.categories.stream().map(Category::toDTO).toList() : null,
                this.images != null ? new ArrayList<>(this.images) : null,
                this.properties != null ? this.properties.stream().map(Property::toDTO).toList() : null
        );
    }

    /**
     * Converts this {@code Product} object into a {@link ProductDAO},
     * which is a Data Access Object (DAO) used for storing the product data in a database.
     *
     * @return a {@code ProductDAO} containing the product's data for persistence.
     */
    public ProductDAO toDAO() {
        return new ProductDAO(
                this.id,
                this.name,
                this.description,
                this.price,
                this.quantity,
                this.removed,
                this.categories != null ? this.categories.stream().map(Category::toDAO).toList() : null,
                this.images != null ? new ArrayList<>(this.images) : null,
                this.properties != null ? this.properties.stream().map(Property::toDAO).toList() : null
        );
    }
}
