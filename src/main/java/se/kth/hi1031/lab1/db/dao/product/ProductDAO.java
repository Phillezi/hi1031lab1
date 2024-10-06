package se.kth.hi1031.lab1.db.dao.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.bo.model.product.Product;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.DBConnectionManager;
import se.kth.hi1031.lab1.bo.model.product.*;

import java.math.BigDecimal;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static se.kth.hi1031.lab1.db.dao.DBUtil.cleanUp;

/**
 * DAO (Data Access Object) class for managing the persistence and retrieval of products.
 */
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

    /**
     * Retrieves all products from the database.
     *
     * @return A list of ProductDAO objects representing all available products.
     * @throws DAOException If there is an error accessing the database.
     */
    public static List<ProductDAO> getAllProducts() throws DAOException {
        List<ProductDAO> products = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
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

            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(toDAO(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            cleanUp(conn, stmt, rs);
        }
        return products;
    }

    /**
     * Retrieves a product by its ID from the database.
     *
     * @param id The ID of the product to retrieve.
     * @return An Optional containing the ProductDAO object if found, or empty if not found.
     * @throws DAOException If there is an error accessing the database.
     */
    public static Optional<ProductDAO> getProductById(int id) throws DAOException {
        Optional<ProductDAO> product = Optional.empty();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
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
                    "WHERE p.id = ? " +
                    "GROUP BY p.id";

            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                product = Optional.of(toDAO(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            cleanUp(conn, stmt, rs);
        }
        return product;
    }

    /**
     * Creates a provided product.
     * @param product The product to create.
     * @return The created product.
     */
    public static ProductDAO createProduct(Product product) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            conn.setAutoCommit(false);

            String query = "INSERT INTO products (name, description, price, quantity) VALUES (?, ?, ?, ?) RETURNING id";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setBigDecimal(3, BigDecimal.valueOf(product.getPrice()));
            stmt.setInt(4, product.getQuantity());

            rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                product.setId(id);

                for (Category category : product.getCategories()) {
                    String availableCategoriesQuery = "SELECT name FROM available_categories WHERE name = ?";
                    stmt = conn.prepareStatement(availableCategoriesQuery);
                    stmt.setString(1, category.getName());
                    rs = stmt.executeQuery();
                    if (!rs.next()) {
                        String insertCategoryQuery = "INSERT INTO available_categories (name, description) VALUES (?, ?)";
                        stmt = conn.prepareStatement(insertCategoryQuery);
                        stmt.setString(1, category.getName());
                        stmt.setString(2, category.getDescription());

                        stmt.executeUpdate();
                    }

                    String categoriesQuery = "INSERT INTO product_categories (product_id, category) VALUES (?, ?)";
                    stmt = conn.prepareStatement(categoriesQuery);
                    stmt.setInt(1, id);
                    stmt.setString(2, category.getName());

                    stmt.executeUpdate();
                }

                for (Property property : product.getProperties()) {
                    String propertyQuery = "INSERT INTO product_properties (product_id, key, value) VALUES (?, ?, ? )";
                    stmt = conn.prepareStatement(propertyQuery);
                    stmt.setInt(1, id);
                    stmt.setString(2, property.getKey());
                    stmt.setString(3, property.getValue());

                    stmt.executeUpdate();
                }

                for (String imageURL : product.getImages()) {
                    if (imageURL.isEmpty()) {
                        continue;
                    }
                    String imageQuery = "INSERT INTO product_images (product_id, image_url) VALUES (?, ? )";
                    stmt = conn.prepareStatement(imageQuery);
                    stmt.setInt(1, id);
                    stmt.setString(2, imageURL);

                    stmt.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new DAOException(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            cleanUp(conn, stmt, rs);
        }
        return product.toDAO();
    }

    /**
     * Wrapper to update products but not provide a connection to use.
     * @param product The product to update.
     * @param quantity The new quanity of the product.
     * @return A boolean indicating the result.
     */
    public static boolean updateProductQuantity(Product product, int quantity) {
        return ProductDAO.updateProductQuantity(product, quantity, null);
    }

    /**
     * Updates a products quantity.
     * @param product The product to update.
     * @param quantity The new quantity to set.
     * @param conn A connection or null, if connection is provided it will be used else a new connection will be created.
     *             If connection is provided it is dependent on the upstream caller to commit the update.
     * @return a boolean to indicate result.
     * @throws DAOException on failure.
     */
    public static boolean updateProductQuantity(Product product, int quantity, Connection conn) throws DAOException {
        boolean isChild = false;
        PreparedStatement stmt = null;
        try {
            if (conn == null) {
                conn = DBConnectionManager.getInstance().getConnection();
            } else {
                isChild = true;
            }
            String query = "UPDATE products SET quantity = ? WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, quantity);
            stmt.setInt(2, product.getId());
            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (!isChild) {
                        conn.close();
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Gets the available quantity of a product with a given id.
     * @param productId The id of the product to get quantity for.
     * @return The quantity of the product.
     * @throws DAOException on failure.
     */
    public static int getProductQuantity(int productId) throws DAOException {
        return getProductQuantity(productId, null);
    }

    /**
     * Gets the available quantity of a product with a given id.
     * @param productId The product id to check quantity of.
     * @param conn An existing connection or null. If connection is provided it will be used, else a new connection will be created.
     * @return The quantity of the product.
     * @throws DAOException on failure.
     */
    public static int getProductQuantity(int productId, Connection conn) throws DAOException {
        boolean isChild = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            if (conn == null) {
                conn = DBConnectionManager.getInstance().getConnection();
            } else {
                isChild = true;
            }
            String query = "SELECT quantity FROM products WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, productId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity");
            }
            throw new DAOException("no product found");
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (!isChild) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            cleanUp(null, stmt, rs);
        }
    }

    /**
     * Gets all products by a list of given ids.
     * @param ids The ids to query products for.
     * @return A List of all the products.
     * @throws DAOException on failure.
     */
    public static List<ProductDAO> getProductsByIds(List<Integer> ids) throws DAOException {
        List<ProductDAO> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

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

            stmt = conn.prepareStatement(query);

            for (int i = 0; i < ids.size(); i++) {
                stmt.setInt(i + 1, ids.get(i));
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(toDAO(rs));
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            cleanUp(conn, stmt, rs);

        }

        return products;
    }

    /**
     * Updates a product.
     * @param product The updated product.
     * @throws DAOException on failure.
     */
    public static void updateProduct(Product product) throws DAOException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            conn.setAutoCommit(false);

            StringBuilder queryBuilder = new StringBuilder("UPDATE products SET ");
            List<String> setClauses = new ArrayList<>();
            List<Object> parameters = new ArrayList<>();

            if (product.getName() != null && !product.getName().isEmpty()) {
                setClauses.add("name = ?");
                parameters.add(product.getName());
            }

            setClauses.add("description = ?");
            parameters.add(product.getDescription());

            if (product.getPrice() != -1) {
                setClauses.add("price = ?");
                parameters.add(BigDecimal.valueOf(product.getPrice()));
            }
            if (product.getQuantity() != -1) {
                setClauses.add("quantity = ?");
                parameters.add(product.getQuantity());
            }

            if (setClauses.isEmpty()) {
                throw new DAOException("No fields to update.");
            }

            queryBuilder.append(String.join(", ", setClauses));
            queryBuilder.append(" WHERE id = ?");
            parameters.add(product.getId());

            stmt = conn.prepareStatement(queryBuilder.toString());
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            stmt.executeUpdate();

            String deleteCategoriesQuery = "DELETE FROM product_categories WHERE product_id = ?";
            stmt = conn.prepareStatement(deleteCategoriesQuery);
            stmt.setInt(1, product.getId());
            stmt.executeUpdate();

            String deletePropertiesQuery = "DELETE FROM product_properties WHERE product_id = ?";
            stmt = conn.prepareStatement(deletePropertiesQuery);
            stmt.setInt(1, product.getId());
            stmt.executeUpdate();

            String deleteImagesQuery = "DELETE FROM product_images WHERE product_id = ?";
            stmt = conn.prepareStatement(deleteImagesQuery);
            stmt.setInt(1, product.getId());
            stmt.executeUpdate();

            for (Category category : product.getCategories()) {
                String availableCategoriesQuery = "SELECT name FROM available_categories WHERE name = ?";
                stmt = conn.prepareStatement(availableCategoriesQuery);
                stmt.setString(1, category.getName());
                rs = stmt.executeQuery();
                if (!rs.next()) {
                    String insertCategoryQuery = "INSERT INTO available_categories (name, description) VALUES (?, ?)";
                    stmt = conn.prepareStatement(insertCategoryQuery);
                    stmt.setString(1, category.getName());
                    stmt.setString(2, category.getDescription());
                    stmt.executeUpdate();
                }

                String categoriesQuery = "INSERT INTO product_categories (product_id, category) VALUES (?, ?)";
                stmt = conn.prepareStatement(categoriesQuery);
                stmt.setInt(1, product.getId());
                stmt.setString(2, category.getName());
                stmt.executeUpdate();
            }

            for (Property property : product.getProperties()) {
                String propertyQuery = "INSERT INTO product_properties (product_id, key, value) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(propertyQuery);
                stmt.setInt(1, product.getId());
                stmt.setString(2, property.getKey());
                stmt.setString(3, property.getValue());
                stmt.executeUpdate();
            }

            for (String imageURL : product.getImages()) {
                if (imageURL.isEmpty()) {
                    continue;
                }
                String imageQuery = "INSERT INTO product_images (product_id, image_url) VALUES (?, ?)";
                stmt = conn.prepareStatement(imageQuery);
                stmt.setInt(1, product.getId());
                stmt.setString(2, imageURL);
                stmt.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new DAOException(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            cleanUp(conn, stmt, rs);
        }
    }

    /**
     * Gets a product from a resultset.
     * @param rs The resultset to get the product from.
     * @return The product.
     */
    public static ProductDAO toDAO(ResultSet rs) throws SQLException {
        Array categoriesArray = rs.getArray("categories");
        List<CategoryDAO> categories = categoriesArray != null
                ? Arrays.stream((String[]) categoriesArray.getArray())
                .filter(Objects::nonNull)
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
            if (propertyKeys[i] != null && propertyValues[i] != null) {
                properties.add(new PropertyDAO(propertyKeys[i], propertyValues[i]));
            }
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

    /**
     * Gets multiple products from a resultset.
     * @param rs The resultset to get the products from.
     * @return A List containing all the products.
     */
    public static List<ProductDAO> toDAOs(ResultSet rs) throws SQLException {
        Integer[] ids = (Integer[]) rs.getArray("products_id").getArray();
        String[] names = (String[]) rs.getArray("products_name").getArray();
        String[] descriptions = (String[]) rs.getArray("products_description").getArray();
        BigDecimal[] prices = (BigDecimal[]) rs.getArray("products_price").getArray();
        Integer[] quantities = (Integer[]) rs.getArray("products_quantity").getArray();
        Boolean[] isRemoved = (Boolean[]) rs.getArray("products_removed").getArray();

        if (ids == null ||
                names == null ||
                descriptions == null ||
                prices == null ||
                quantities == null ||
                isRemoved == null
        ) {
            return null;
        }

        List<ProductDAO> daos = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            daos.add(new ProductDAO(
                    ids[i],
                    names[i],
                    descriptions[i],
                    prices.length > i && prices[i] != null ? prices[i].doubleValue() : Double.valueOf(0),
                    quantities.length > i && quantities[i] != null ? quantities[i] : 0,
                    isRemoved.length > i && isRemoved[i] != null ? isRemoved[i] : false,
                    null,
                    null,
                    null
            ));
        }

        return daos;
    }
    /**
     * Conversion method to convert from a DAO object to a BO object.
     * @return A BO object of the same attributes, (deep copied).
     */
    public Product toProduct() {
        return new Product(
                this.id,
                this.name,
                this.description,
                this.price,
                this.quantity,
                this.removed,
                this.categories != null ? this.categories.stream().map(CategoryDAO::toCategory).toList() : null,
                new ArrayList<>(this.images),
                this.properties != null ? this.properties.stream().map(PropertyDAO::toProperty).toList() : null
        );
    }

}