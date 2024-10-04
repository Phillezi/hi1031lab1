package se.kth.hi1031.lab1.bo.service.product;

import se.kth.hi1031.lab1.bo.model.product.Property;
import se.kth.hi1031.lab1.bo.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing properties associated with products.
 * 
 * <p>The {@code PropertyService} class provides methods for adding, updating, removing, 
 * and retrieving properties for a given product. It serves as an intermediary to handle 
 * property-related operations.</p>
 */
public class PropertyService {

    /**
     * Adds a new property to the specified product.
     * 
     * <p>This method creates a new {@link Property} with the specified key and value 
     * and adds it to the product's list of properties.</p>
     * 
     * @param product The product to which the property will be added.
     * @param key The key for the new property.
     * @param value The value for the new property.
     * @return An {@link Optional} containing the updated product if successful, or 
     *         an empty {@link Optional} if the product is null.
     */
    public Optional<Product> addPropertyToProduct(Product product, String key, String value) {
        if (product != null) {
            Property newProperty = new Property(key, value);
            product.getProperties().add(newProperty);
            return Optional.of(product);
        }
        return Optional.empty();
    }

    /**
     * Updates the value of an existing property for the specified product.
     * 
     * <p>This method searches for a property with the given key in the product's 
     * properties. If found, it updates the property's value to the new value.</p>
     * 
     * @param product The product containing the property to update.
     * @param key The key of the property to update.
     * @param newValue The new value to set for the property.
     * @return An {@link Optional} containing the updated product if the property was found 
     *         and updated, or an empty {@link Optional} if the product is null or the 
     *         property key does not exist.
     */
    public Optional<Product> updatePropertyOfProduct(Product product, String key, String newValue) {
        if (product != null) {
            for (Property property : product.getProperties()) {
                if (property.getKey().equals(key)) {
                    property.setValue(newValue);
                    return Optional.of(product);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Removes a property from the specified product.
     * 
     * <p>This method removes the property with the given key from the product's 
     * list of properties.</p>
     * 
     * @param product The product from which to remove the property.
     * @param key The key of the property to remove.
     * @return An {@link Optional} containing the updated product if the property was removed, 
     *         or an empty {@link Optional} if the product is null.
     */
    public Optional<Product> removePropertyFromProduct(Product product, String key) {
        if (product != null) {
            product.getProperties().removeIf(property -> property.getKey().equals(key));
            return Optional.of(product);
        }
        return Optional.empty();
    }

    /**
     * Retrieves all properties of the specified product.
     * 
     * <p>This method returns a list of properties associated with the given product. 
     * If the product is null, an empty list is returned.</p>
     * 
     * @param product The product whose properties are to be retrieved.
     * @return A list of {@link Property} representing the properties of the product, 
     *         or an empty list if the product is null.
     */
    public List<Property> getPropertiesOfProduct(Product product) {
        if (product != null) {
            return new ArrayList<>(product.getProperties());
        }
        return new ArrayList<>();
    }

    /**
     * Retrieves a specific property by its key from the specified product.
     * 
     * <p>This method searches for the property with the given key in the product's 
     * properties. If found, it returns the property wrapped in an {@link Optional}.</p>
     * 
     * @param product The product from which to retrieve the property.
     * @param key The key of the property to retrieve.
     * @return An {@link Optional} containing the found {@link Property}, or an empty 
     *         {@link Optional} if the product is null or the property does not exist.
     */
    public Optional<Property> getPropertyByKey(Product product, String key) {
        if (product != null) {
            for (Property property : product.getProperties()) {
                if (property.getKey().equals(key)) {
                    return Optional.of(property);
                }
            }
        }
        return Optional.empty();
    }
}
