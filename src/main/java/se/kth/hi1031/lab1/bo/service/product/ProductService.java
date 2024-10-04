package se.kth.hi1031.lab1.bo.service.product;

import java.util.List;

import se.kth.hi1031.lab1.bo.model.product.Product;
import se.kth.hi1031.lab1.db.dao.product.ProductDAO;
import se.kth.hi1031.lab1.ui.dto.product.ProductDTO;

/**
 * Service class for managing products in the application.
 * 
 * <p>The {@code ProductService} class provides methods to retrieve product information. 
 * It interacts with the data access layer to fetch product data and convert it to DTOs for 
 * easier use within the application.</p>
 */
public class ProductService {
    public static List<ProductDTO> getProducts() {
        List<ProductDAO> products = ProductDAO.getAllProducts();
        return products.stream().map(ProductDAO::toProduct).map(Product::toDTO).toList();
    }

    public static List<ProductDTO> getProducts(List<Integer> ids) {
        List<ProductDAO> products = ProductDAO.getProductsByIds(ids);
        return products.stream().map(ProductDAO::toProduct).map(Product::toDTO).toList();
    }
}
