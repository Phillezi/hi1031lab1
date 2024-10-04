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

    /**
     * Retrieves all available products.
     * 
     * <p>This method fetches all products from the data source and converts them into a 
     * list of {@link ProductDTO} for presentation or further processing.</p>
     * 
     * @return A list of {@link ProductDTO} representing all available products.
     */
    public static List<ProductDTO> getProducts() {
        List<ProductDAO> products = ProductDAO.getAllProducts();
        return products.stream().map(ProductDAO::toProduct).map(Product::toDTO).toList();
    }

    /**
     * Retrieves specific products based on the provided list of IDs.
     * 
     * <p>This method fetches products corresponding to the given IDs from the data source. 
     * It converts the retrieved data into a list of {@link ProductDTO} for presentation or 
     * further processing.</p>
     * 
     * @param ids A list of product IDs to retrieve.
     * @return A list of {@link ProductDTO} representing the specified products.
     */
    public static List<ProductDTO> getProducts(List<Integer> ids) {
        List<ProductDAO> products = ProductDAO.getProductsByIds(ids);
        return products.stream().map(ProductDAO::toProduct).map(Product::toDTO).toList();
    }
}
