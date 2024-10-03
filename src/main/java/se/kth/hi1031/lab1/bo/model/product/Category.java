package se.kth.hi1031.lab1.bo.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.db.dao.product.CategoryDAO;
import se.kth.hi1031.lab1.ui.dto.product.CategoryDTO;

@Getter
@Setter
@AllArgsConstructor
public class Category {
    private String name;
    private String description;

    public CategoryDTO toDTO() {
        return new CategoryDTO(name, description);
    }

    public CategoryDAO toDAO() {
        return new CategoryDAO(name, description);
    }
}
