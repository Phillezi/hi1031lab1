package se.kth.hi1031.lab1.bo.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.db.dao.product.CategoryDAO;
import se.kth.hi1031.lab1.ui.dto.product.CategoryDTO;


/**
 * Represents a product category in the system.
 * 
 * <p> Each category has a name and an optional description that provides more details about it.</p>
 * 
 * <p>This class uses Lombok annotations to automatically generate
 * getters, setters, and an all-arguments constructor.</p>
 */
@Getter
@Setter
@AllArgsConstructor
public class Category {
    private String name;
    private String description;


    /**
     * Converts this {@code Category} object into a {@link CategoryDTO}, which is a Data Transfer Object (DTO)
     * used to transfer category data across different layers of the application.
     *
     * @return a {@code CategoryDTO} containing the category's name and description.
     */
    public CategoryDTO toDTO() {
        return new CategoryDTO(name, description);
    }

    /**
     * Converts this {@code Category} object into a {@link CategoryDAO}, which is a Data Access Object (DAO)
     * used for storing the category data in a database.
     *
     * @return a {@code CategoryDAO} containing the category's name and description for persistence.
     */
    public CategoryDAO toDAO() {
        return new CategoryDAO(name, description);
    }
}
