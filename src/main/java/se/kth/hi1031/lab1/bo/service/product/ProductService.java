package se.kth.hi1031.lab1.bo.service.product;

import java.util.List;

import se.kth.hi1031.lab1.bo.model.product.Product;
import se.kth.hi1031.lab1.db.dao.product.ProductDAO;
import se.kth.hi1031.lab1.ui.dto.product.ProductDTO;

public class ProductService {
    public static List<ProductDTO> getProducts() {
        List<ProductDAO> products = ProductDAO.getAllProducts();
        return products.stream().map(ProductDAO::toProduct).map(Product::toDTO).toList();
    }
}
