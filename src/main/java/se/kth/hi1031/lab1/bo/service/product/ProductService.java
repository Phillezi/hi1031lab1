package se.kth.hi1031.lab1.bo.service.product;

import se.kth.hi1031.lab1.bo.model.product.Product;
import se.kth.hi1031.lab1.bo.model.product.Category;
import se.kth.hi1031.lab1.bo.model.product.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final List<Product> products = new ArrayList<>();

    public Product createProduct(String name, String description, double price, int quantity, ArrayList<Category> categories, ArrayList<String> images, ArrayList<Property> properties) {
        Product newProduct = new Product(null ,name, description, price, quantity, false, categories, images, properties);
        products.add(newProduct);
        return newProduct;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public Optional<Product> getProductById(Integer productId) {
        return products.stream().filter(product -> product.getId().equals(productId)).findFirst();
    }

    public Optional<Product> updateProduct(Integer productId, String name, String description, double price, int quantity) {
        Optional<Product> productOptional = getProductById(productId);
        productOptional.ifPresent(product -> {
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setQuantity(quantity);
        });
        return productOptional;
    }

    public Optional<Product> removeProduct(Integer productId) {
        Optional<Product> productOptional = getProductById(productId);
        productOptional.ifPresent(product -> product.setRemoved(true));
        return productOptional;
    }

    public Optional<Product> addCategoryToProduct(Integer productId, Category category) {
        Optional<Product> productOptional = getProductById(productId);
        productOptional.ifPresent(product -> product.getCategories().add(category));
        return productOptional;
    }

    public Optional<Product> removeCategoryFromProduct(Integer productId, Category category) {
        Optional<Product> productOptional = getProductById(productId);
        productOptional.ifPresent(product -> product.getCategories().remove(category));
        return productOptional;
    }

    public Optional<Product> addImageToProduct(Integer productId, String imageUrl) {
        Optional<Product> productOptional = getProductById(productId);
        productOptional.ifPresent(product -> product.getImages().add(imageUrl));
        return productOptional;
    }

    public Optional<Product> removeImageFromProduct(Integer productId, String imageUrl) {
        Optional<Product> productOptional = getProductById(productId);
        productOptional.ifPresent(product -> product.getImages().remove(imageUrl));
        return productOptional;
    }

    public Optional<Product> addPropertyToProduct(Integer productId, Property property) {
        Optional<Product> productOptional = getProductById(productId);
        productOptional.ifPresent(product -> product.getProperties().add(property));
        return productOptional;
    }

    public Optional<Product> removePropertyFromProduct(Integer productId, Property property) {
        Optional<Product> productOptional = getProductById(productId);
        productOptional.ifPresent(product -> product.getProperties().remove(property));
        return productOptional;
    }

    public boolean deleteProductById(Integer productId) {
        return products.removeIf(product -> product.getId().equals(productId));
    }
}
