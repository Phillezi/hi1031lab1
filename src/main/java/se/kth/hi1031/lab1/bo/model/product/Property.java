package se.kth.hi1031.lab1.bo.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.ui.dto.product.PropertyDTO;

@Getter
@Setter
@AllArgsConstructor
public class Property {
    private String key;
    private String value;

    public PropertyDTO toDTO() {
        return new PropertyDTO(key, value);
    }
}
