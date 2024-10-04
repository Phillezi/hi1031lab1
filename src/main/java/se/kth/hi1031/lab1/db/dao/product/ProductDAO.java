package se.kth.hi1031.lab1.db.dao.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.bo.model.product.Product;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.DBConnectionManager;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

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

    public static List<ProductDAO> getAllProducts() throws DAOException {
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
                products.add(toDAO(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (conn != null) {
                // TODO: mark as free to use
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return products;
    }

public static Optional<ProductDAO> getProductById(int id) throws DAOException {
    Optional<ProductDAO> product = Optional.empty();
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
                "WHERE p.id = ?";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            product = Optional.of(toDAO(rs));
        }
    } catch (SQLException e) {
        throw new DAOException(e.getMessage());
    } finally {
        if (conn != null) {
            // TODO: mark as free to use
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    return product;
}

public static List<ProductDAO> getProductsByIds(List<Integer> ids) throws DAOException {
    List<ProductDAO> products = new ArrayList<>();
    Connection conn = null;
    
    if (ids == null || ids.isEmpty()) {
        return products;
    }

    try {
        conn = DBConnectionManager.getInstance().getConnection();

        String placeholders = ids.stream()
                                 .map(id -> "?")
                                 .collect(Collectors.joining(","));
        
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
                "WHERE p.id IN (" + placeholders + ") " +
                "GROUP BY p.id";
        
        PreparedStatement stmt = conn.prepareStatement(query);
        
        for (int i = 0; i < ids.size(); i++) {
            stmt.setInt(i + 1, ids.get(i));
        }
        
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            products.add(toDAO(rs));
        }

    } catch (SQLException e) {
        throw new DAOException(e.getMessage());
    } finally {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    return products;
}


public static ProductDAO toDAO(ResultSet rs) throws SQLException {
    Array categoriesArray = rs.getArray("categories");
    List<CategoryDAO> categories = categoriesArray != null
            ? Arrays.stream((String[]) categoriesArray.getArray())
            .map(name -> new CategoryDAO(name, null))
            .collect(Collectors.toList())
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

    return new ProductDAO(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getDouble("price"),
            rs.getInt("quantity"),
            rs.getBoolean("removed"),
            categories,
            images,
            properties
    );
}

    public static List<ProductDAO> toDAOs(ResultSet rs) throws SQLException {
        Integer[] ids = (Integer[])rs.getArray("products_id").getArray();
        String[] names = (String[]) rs.getArray("products_name").getArray();
        String[] descriptions = (String[]) rs.getArray("products_description").getArray();
        Double[] prices = (Double[]) rs.getArray("products_prices").getArray();
        Integer[] quantities = (Integer[]) rs.getArray("products_quantities").getArray();
        Boolean[] isRemoveds = (Boolean[]) rs.getArray("products_isremoveds").getArray();

        if (ids == null ||
                names == null ||
                descriptions == null ||
                prices == null ||
                quantities == null ||
                isRemoveds == null
        ) {
            return null;
        }

        List<ProductDAO> daos = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            daos.add(new ProductDAO(
                    ids[i],
                    names[i],
                    descriptions[i],
                    prices[i],
                    quantities[i],
                    isRemoveds[i],
                    null,
                    null,
                    null
            ));
        }

        return daos;
    }

    public Product toProduct() {
        return new Product(
                this.id,
                this.name,
                this.description,
                this.price,
                this.quantity,
                this.removed,
                this.categories.stream().map(CategoryDAO::toCategory).toList(),
                this.images,
                this.properties.stream().map(PropertyDAO::toProperty).toList()
        );
    }

}