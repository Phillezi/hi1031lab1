package se.kth.hi1031.lab1.bo.service.product;

import java.util.List;
import java.util.Optional;

import se.kth.hi1031.lab1.bo.middleware.AuthMiddleware;
import se.kth.hi1031.lab1.bo.model.product.Product;
import se.kth.hi1031.lab1.bo.model.user.Permission;
import se.kth.hi1031.lab1.bo.model.user.Role;
import se.kth.hi1031.lab1.bo.service.PermissionException;
import se.kth.hi1031.lab1.bo.service.ServiceException;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.dao.product.ProductDAO;
import se.kth.hi1031.lab1.ui.dto.product.ProductDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

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

    public static ProductDTO getProductById(int id) {
        try {
            Optional<ProductDAO> productOptional = ProductDAO.getProductById(id);
            if (productOptional.isPresent()) {
                return productOptional.get().toProduct().toDTO();
            } else {
                throw new ServiceException("No product found with id " + id);
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public static void updateProduct(UserDTO user, ProductDTO productToUpdate) {
        if (!AuthMiddleware.userHasOneOf(user, new Role("admin"))) {
            throw new PermissionException("User " + user.getName() + " needs to be admin to update products.");
        }
        try {
            Product product = new Product(productToUpdate);
            ProductDAO.updateProduct(product);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public static ProductDTO createProduct(UserDTO user, ProductDTO product) {
        if (!AuthMiddleware.userHasOneOf(user, new Role("admin"))) {
            throw new PermissionException("User " + user.getName() + " needs to be admin to create products.");
        }
        return ProductDAO.createProduct(new Product(product)).toProduct().toDTO();
    }
}
