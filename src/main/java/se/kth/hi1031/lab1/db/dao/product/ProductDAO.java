package se.kth.hi1031.lab1.db.dao.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.db.DBConnectionManager;

import java.sql.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class ProductDAO {
    private Integer id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private boolean removed;
    private List<CategoryDAO> categories;
    private List<String> images;
    private List<PropertyDAO> properties;

    public static List<ProductDAO> getProducts() {
        List<ProductDAO> products = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            String query = "SELECT " +
                    "p.id, p.name, p.description, p.price, p.quantity, p.removed, " +
                    "ARRAY_AGG(DISTINCT pc.category) AS categories, " +
                    "ARRAY_AGG(DISTINCT pi.image_url) AS images, " +
                    "ARRAY_AGG(DISTINCT pp.key) AS property_keys, " +
                    "ARRAY_AGG(DISTINCT pp.value) AS property_values " +
                    "FROM products p " +
                    "LEFT JOIN product_categories pc ON p.id = pc.product_id " +
                    "LEFT JOIN product_images pi ON p.id = pi.product_id " +
                    "LEFT JOIN product_properties pp ON p.id = pp.product_id " +
                    "GROUP BY p.id";

            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Array categoriesArray = rs.getArray("categories");
                List<CategoryDAO> categories = categoriesArray != null
                        ? Arrays.stream((String[]) categoriesArray.getArray())
                        .map(name -> new CategoryDAO(name, null))
                        .toList()
                        : new ArrayList<>();

                Array imagesArray = rs.getArray("images");
                List<String> images = Arrays.asList((String[]) imagesArray.getArray());

                Array keysArray = rs.getArray("property_keys");
                Array valuesArray = rs.getArray("property_values");
                String[] propertyKeys = (String[]) keysArray.getArray();
                String[] propertyValues = (String[]) valuesArray.getArray();

                List<PropertyDAO> properties = new ArrayList<>(propertyKeys.length);
                for (int i = 0; i < propertyKeys.length; i++) {
                    properties.add(new PropertyDAO(propertyKeys[i], propertyValues[i]));
                }

                products.add(new ProductDAO(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getBoolean("removed"),
                        categories,
                        images,
                        properties
                ));

            }
        } catch (SQLException e) {

        } finally {
            if (conn != null) {

            }
        }
        return products;
    }
}