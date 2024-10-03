package se.kth.hi1031.lab1.bo.service.product;

import se.kth.hi1031.lab1.bo.model.product.Category;
import se.kth.hi1031.lab1.bo.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryService {
    private final List<Category> categories = new ArrayList<>();

    public Category createCategory(String name, String description) {
        Category category = new Category(name, description);
        categories.add(category);
        return category;
    }

    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }

    public Optional<Category> getCategoryByName(String name) {
        return categories.stream().filter(category -> category.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<Category> updateCategory(String name, String newDescription) {
        Optional<Category> categoryOptional = getCategoryByName(name);
        categoryOptional.ifPresent(category -> category.setDescription(newDescription));
        return categoryOptional;
    }

    public boolean deleteCategoryByName(String name) {
        return categories.removeIf(category -> category.getName().equalsIgnoreCase(name));
    }

    public Optional<Product> addCategoryToProduct(Product product, Category category) {
        if (product != null && category != null) {
            product.getCategories().add(category);
            return Optional.of(product);
        }
        return Optional.empty();
    }

    public Optional<Product> removeCategoryFromProduct(Product product, Category category) {
        if (product != null && category != null) {
            product.getCategories().remove(category);
            return Optional.of(product);
        }
        return Optional.empty();
    }
}
