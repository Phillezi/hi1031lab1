package se.kth.hi1031.lab1.ui.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object representing a property of a product.
 *
 * <p>
 * The PropertyDTO class encapsulates a key-value pair that represents
 * a specific characteristic or feature of a product.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
public class PropertyDTO {
    private String key;
    private String value;
}
