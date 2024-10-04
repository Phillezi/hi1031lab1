package se.kth.hi1031.lab1.bo.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.db.dao.product.PropertyDAO;
import se.kth.hi1031.lab1.ui.dto.product.PropertyDTO;

/**
 * Represents a key-value pair that defines a specific attribute or characteristic of a product.
 * 
 * <p>Each {@code Property} object is used to capture additional product details such as 
 * color, size, material, etc.</p>
 * 
 * <p>This class uses Lombok annotations to automatically generate getters, setters, 
 * and an all-arguments constructor.</p>
 */
@Getter
@Setter
@AllArgsConstructor
public class Property {
    private String key;
    private String value;

    public Property(PropertyDTO property) {
        this.key = property.getKey();
        this.value = property.getValue();
    }

    /**
     * Converts this {@code Property} object into a {@link PropertyDTO}, 
     * which is a Data Transfer Object (DTO) used for transferring property data across 
     * different layers of the application.
     *
     * @return a {@code PropertyDTO} containing the property's key and value.
     */
    public PropertyDTO toDTO() {
        return new PropertyDTO(key, value);
    }

    /**
     * Converts this {@code Property} object into a {@link PropertyDAO}, 
     * which is a Data Access Object (DAO) used for storing the property data in a database.
     *
     * @return a {@code PropertyDAO} containing the property's key and value for persistence.
     */
    public PropertyDAO toDAO() {
        return new PropertyDAO(key, value);
    }
}
