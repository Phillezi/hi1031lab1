package se.kth.hi1031.lab1.db.dao.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.bo.model.product.Category;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDAO {
    private String name;
    private String description;

    public Category toCategory() {
        return new Category(name, description);
    }
}
