package se.kth.hi1031.lab1.dao;

import se.kth.hi1031.lab1.DBConnection;
import se.kth.hi1031.lab1.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductDAO is a Data Access Object for products.
 */
public class ProductDAO {
    private final DBConnection dbConnection;

    public ProductDAO() throws SQLException {
        this.dbConnection = new DBConnection();
    }

    public void addProduct(Product product) throws SQLException {
        String query = "INSERT INTO products (id, name, description, price, quantity) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, product.getId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getQuantity());
            stmt.executeUpdate();
        }
    }

    public Product getProductById(int id) throws SQLException {
        String query = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"));
            }
        }
        return null;
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"));
                products.add(product);
            }
        }
        return products;
    }

    public void updateProduct(Product product) throws SQLException {
        String query = "UPDATE products SET name = ?, description = ?, price = ?, quantity = ? WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantity());
            stmt.setInt(5, product.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new IllegalArgumentException("Product not found");
            }
        }
    }

    public void deleteProduct(int id) throws SQLException {
        String query = "DELETE FROM products WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
