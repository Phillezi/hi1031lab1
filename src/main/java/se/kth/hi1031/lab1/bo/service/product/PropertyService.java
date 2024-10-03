package se.kth.hi1031.lab1.bo.service.product;

import se.kth.hi1031.lab1.bo.model.product.Property;
import se.kth.hi1031.lab1.bo.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PropertyService {
    public Optional<Product> addPropertyToProduct(Product product, String key, String value) {
        if (product != null) {
            Property newProperty = new Property(key, value);
            product.getProperties().add(newProperty);
            return Optional.of(product);
        }
        return Optional.empty();
    }

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

    public Optional<Product> removePropertyFromProduct(Product product, String key) {
        if (product != null) {
            product.getProperties().removeIf(property -> property.getKey().equals(key));
            return Optional.of(product);
        }
        return Optional.empty();
    }

    public List<Property> getPropertiesOfProduct(Product product) {
        if (product != null) {
            return new ArrayList<>(product.getProperties());
        }
        return new ArrayList<>();
    }

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
