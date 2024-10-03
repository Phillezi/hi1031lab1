package se.kth.hi1031.lab1.db.dao.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import se.kth.hi1031.lab1.bo.model.product.Property;

@Data
@AllArgsConstructor
public class PropertyDAO {
    private String key;
    private String value;

    public Property toProperty() {
        return new Property(key, value);
    }

}

