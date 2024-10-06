package se.kth.hi1031.lab1.db.dao.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import se.kth.hi1031.lab1.bo.model.product.Property;

@Data
@AllArgsConstructor
public class PropertyDAO {
    private String key;
    private String value;

    /**
     * Conversion method to convert from a DAO object to a BO object.
     * @return A BO object of the same attributes, (deep copied).
     */
    public Property toProperty() {
        return new Property(key, value);
    }

}

