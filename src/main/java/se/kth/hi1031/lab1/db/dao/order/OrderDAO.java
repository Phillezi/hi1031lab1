package se.kth.hi1031.lab1.db.dao.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.kth.hi1031.lab1.bo.model.order.Order;
import se.kth.hi1031.lab1.bo.model.order.Status;
import se.kth.hi1031.lab1.bo.model.product.Product;
import se.kth.hi1031.lab1.bo.model.user.User;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.DBConnectionManager;
import se.kth.hi1031.lab1.db.dao.product.ProductDAO;
import se.kth.hi1031.lab1.db.dao.user.UserDAO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * DAO (Data Access Object) class for managing the persistence and retrieval of
 * {@link Order} entities.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class OrderDAO {
    private Integer id;
    private Timestamp created;
    private Timestamp delivered;
    private String deliveryAddress;
    private UserDAO customer;
    private List<ProductDAO> products;
    private List<StatusDAO> statuses;

    /**
     * Constructs an OrderDAO object from an {@link Order} object.
     *
     * @param order The order object to be transformed into DAO representation.
     */
    public OrderDAO(Order order) {
        this.id = order.getId();
        this.created = order.getCreated();
        this.delivered = order.getDelivered();
        this.deliveryAddress = order.getDeliveryAddress();
        this.customer = order.getCustomer().toDAO();
        this.products = order.getProducts().stream().map(Product::toDAO).toList();
        this.statuses = order.getStatuses().stream().map(Status::toDAO).toList();
    }

    /**
     * Retrieves a list of all orders from the database.
     *
     * @return A list of {@link OrderDAO} objects representing all orders.
     */
    public static List<OrderDAO> getOrders() {
        List<OrderDAO> orders = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            String query = "SELECT " +
                    "o.id AS order_id, " +
                    "o.created_at AS order_created_at, " +
                    "o.delivered_at AS order_delivered_at, " +
                    "o.delivery_address AS order_delivery_address, " +
                    "ARRAY_AGG(DISTINCT p.id) AS products_id, " +
                    "ARRAY_AGG(DISTINCT p.name) AS products_name, " +
                    "ARRAY_AGG(DISTINCT p.description) AS products_description, " +
                    "ARRAY_AGG(DISTINCT op.product_price) AS products_price, " +
                    "ARRAY_AGG(DISTINCT p.quantity) AS products_quantity, " +
                    "ARRAY_AGG(DISTINCT p.removed) AS products_removed, " +
                    "ARRAY_AGG(DISTINCT os.status) AS statuses_status, " +
                    "ARRAY_AGG(DISTINCT os.timestamp) AS statuses_timestamp, " +
                    "u.id AS user_id, u.email AS user_email, u.name AS user_name, u.hashed_pw AS user_hashed_pw " +
                    "FROM orders o " +
                    "LEFT JOIN ordered_products op ON o.id = op.order_id " +
                    "LEFT JOIN products p ON op.product_id = p.id " +
                    "LEFT JOIN order_status os ON o.id = os.order_id " +
                    "LEFT JOIN user_t u ON o.customer_id = u.id " +
                    "GROUP BY o.id, u.id";

            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(toDAO(rs));
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
        return orders;
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id The ID of the order.
     * @return An {@link Optional} containing the {@link OrderDAO} if found,
     *         otherwise an empty optional.
     */
    public static Optional<OrderDAO> getOrderById(int id) {
        Optional<OrderDAO> order = Optional.empty();
        Connection conn = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            String query = "SELECT " +
                    "o.id AS order_id, " +
                    "o.created_at AS order_created_at, " +
                    "o.delivered_at AS order_delivered_at, " +
                    "o.delivery_address AS order_delivery_address, " +
                    "ARRAY_AGG(DISTINCT p.id) AS products_id, " +
                    "ARRAY_AGG(DISTINCT p.name) AS products_name, " +
                    "ARRAY_AGG(DISTINCT p.description) AS products_description, " +
                    "ARRAY_AGG(DISTINCT op.product_price) AS products_price, " +
                    "ARRAY_AGG(DISTINCT op.product_quantity) AS products_quantity, " +
                    "ARRAY_AGG(DISTINCT p.removed) AS products_removed, " +
                    "ARRAY_AGG(DISTINCT os.status) AS statuses_status, " +
                    "ARRAY_AGG(DISTINCT os.timestamp) AS statuses_timestamp, " +
                    "u.id AS user_id, u.email AS user_email, u.name AS user_name, u.hashed_pw AS user_hashed_pw " +
                    "FROM orders o " +
                    "LEFT JOIN ordered_products op ON o.id = op.order_id " +
                    "LEFT JOIN products p ON op.product_id = p.id " +
                    "LEFT JOIN order_status os ON o.id = os.order_id " +
                    "LEFT JOIN user_t u ON o.customer_id = u.id " +
                    "WHERE o.id = ? " +
                    "GROUP BY o.id, u.id";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                order = Optional.of(toDAO(rs));
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
        return order;
    }

    /**
     * Retrieves all orders for a specific customer.
     *
     * @param customer The customer whose orders should be retrieved.
     * @return A list of {@link OrderDAO} objects representing the customer's
     *         orders.
     */
    public static List<OrderDAO> getOrdersByCustomer(User customer) {
        List<OrderDAO> orders = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            String query = "SELECT " +
                    "o.id AS order_id, " +
                    "o.created_at AS order_created_at, " +
                    "o.delivered_at AS order_delivered_at, " +
                    "o.delivery_address AS order_delivery_address, " +
                    "ARRAY_AGG(DISTINCT p.id) AS products_id, " +
                    "ARRAY_AGG(DISTINCT p.name) AS products_name, " +
                    "ARRAY_AGG(DISTINCT p.description) AS products_description, " +
                    "ARRAY_AGG(DISTINCT op.product_price) AS products_price, " +
                    "ARRAY_AGG(DISTINCT op.product_quantity) AS products_quantity, " +
                    "ARRAY_AGG(DISTINCT p.removed) AS products_removed, " +
                    "ARRAY_AGG(os.status ORDER BY os.timestamp ASC) AS statuses_status, " +
                    "ARRAY_AGG(os.timestamp ORDER BY os.timestamp ASC) AS statuses_timestamp, " +
                    "u.id AS user_id, u.email AS user_email, u.name AS user_name, u.hashed_pw AS user_hashed_pw " +
                    "FROM orders o " +
                    "LEFT JOIN ordered_products op ON o.id = op.order_id " +
                    "LEFT JOIN products p ON op.product_id = p.id " +
                    "LEFT JOIN order_status os ON o.id = os.order_id " +
                    "LEFT JOIN user_t u ON o.customer_id = u.id " +
                    "WHERE o.customer_id = ? " +
                    "GROUP BY o.id, u.id";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customer.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(toDAO(rs));
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
        return orders;
    }

    /**
     * Creates a new order in the database.
     *
     * @param order The {@link Order} object to be persisted.
     * @return The persisted {@link OrderDAO} object.
     * @throws DAOException If an error occurs during the database operation.
     */
    public static OrderDAO createOrder(Order order) throws DAOException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            conn.setAutoCommit(false);

            String query = "INSERT INTO orders (created_at, delivered_at, delivery_address, customer_id) VALUES (?, ?, ?, ?) RETURNING id";
            stmt = conn.prepareStatement(query);
            stmt.setTimestamp(1, order.getCreated());
            stmt.setTimestamp(2, order.getDelivered());
            stmt.setString(3, order.getDeliveryAddress());
            stmt.setInt(4, order.getCustomer().getId());

            rs = stmt.executeQuery();
            if (rs.next()) {
                Integer id = rs.getInt("id");
                order.setId(id);

                for (Product product : order.getProducts()) {
                    String productsQuery = "INSERT INTO ordered_products (order_id, product_id, product_price, product_quantity) VALUES (?, ?, ?, ?)";
                    stmt = conn.prepareStatement(productsQuery);
                    stmt.setInt(1, id);
                    stmt.setInt(2, product.getId());
                    stmt.setBigDecimal(3, BigDecimal.valueOf(product.getPrice()));
                    stmt.setInt(4, product.getQuantity());

                    stmt.executeUpdate();

                    int quantity = ProductDAO.getProductQuantity(product.getId(), conn);
                    int newQuantity = quantity - product.getQuantity();
                    ProductDAO.updateProductQuantity(product, newQuantity, conn);
                }

                for (Status status : order.getStatuses()) {
                    StatusDAO.createStatus(id, status, conn);
                }
            }

            conn.commit();
        } catch (DAOException | SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
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
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return order.toDAO();
    }

    /**
     * Retrieves a list of orders based on the provided statuses.
     *
     * @param statuses The statuses to filter the orders.
     * @return A list of {@link OrderDAO} objects representing orders with the
     *         specified statuses.
     * @throws DAOException If an error occurs while accessing the database.
     */
    public static List<OrderDAO> getOrdersByStatus(String... statuses) throws DAOException {
        return OrderDAO.getOrdersByStatus(null, statuses);
    }

    /**
     * Retrieves a list of orders based on the provided statuses, using the given
     * database connection.
     *
     * @param conn     The database connection to use. If null, a new connection
     *                 will be created.
     * @param statuses The statuses to filter the orders.
     * @return A list of {@link OrderDAO} objects representing orders with the
     *         specified statuses.
     * @throws DAOException If an error occurs while accessing the database.
     */
    public static List<OrderDAO> getOrdersByStatus(Connection conn, String... statuses) throws DAOException {
        boolean isChild = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<OrderDAO> orders = new ArrayList<>();

        try {
            if (conn == null) {
                conn = DBConnectionManager.getInstance().getConnection();
            } else {
                isChild = true;
            }

            String query = "SELECT " +
                    "o.id AS order_id, " +
                    "o.created_at AS order_created_at, " +
                    "o.delivered_at AS order_delivered_at, " +
                    "o.delivery_address AS order_delivery_address, " +
                    "ARRAY_AGG(DISTINCT p.id) AS products_id, " +
                    "ARRAY_AGG(DISTINCT p.name) AS products_name, " +
                    "ARRAY_AGG(DISTINCT p.description) AS products_description, " +
                    "ARRAY_AGG(DISTINCT op.product_price) AS products_price, " +
                    "ARRAY_AGG(DISTINCT op.product_quantity) AS products_quantity, " +
                    "ARRAY_AGG(DISTINCT p.removed) AS products_removed, " +
                    "ARRAY_AGG(os.status ORDER BY os.timestamp ASC) AS statuses_status, " +
                    "ARRAY_AGG(os.timestamp ORDER BY os.timestamp ASC) AS statuses_timestamp, " +
                    "u.id AS user_id, u.email AS user_email, u.name AS user_name, u.hashed_pw AS user_hashed_pw " +
                    "FROM orders o " +
                    "LEFT JOIN ordered_products op ON o.id = op.order_id " +
                    "LEFT JOIN products p ON op.product_id = p.id " +
                    "LEFT JOIN order_status os ON o.id = os.order_id " +
                    "LEFT JOIN user_t u ON o.customer_id = u.id " +
                    "INNER JOIN ( " +
                    "SELECT os_inner.order_id, MAX(os_inner.timestamp) AS latest_timestamp " +
                    "FROM order_status os_inner " +
                    "GROUP BY os_inner.order_id " +
                    ") latest_status ON os.order_id = latest_status.order_id " +
                    "AND os.timestamp = latest_status.latest_timestamp " +
                    "WHERE os.status IN (" +
                    String.join(",", Collections.nCopies(statuses.length, "?")) +
                    ") GROUP BY o.id, u.id";
            stmt = conn.prepareStatement(query);

            for (int i = 0; i < statuses.length; i++) {
                stmt.setString(i + 1, statuses[i]);
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(toDAO(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (conn != null && !isChild) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orders;
    }

    /**
     * Transforms a ResultSet into an OrderDAO object.
     *
     * @param rs The ResultSet containing the order data.
     * @return An {@link OrderDAO} object populated with the data from the
     *         ResultSet.
     * @throws SQLException If an error occurs while accessing the ResultSet.
     */
    public static OrderDAO toDAO(ResultSet rs) throws SQLException {
        UserDAO customer = UserDAO.toDAO(rs);
        List<ProductDAO> products = ProductDAO.toDAOs(rs);
        List<StatusDAO> statuses = StatusDAO.toDAOs(rs);

        return new OrderDAO(
                rs.getInt("order_id"),
                rs.getTimestamp("order_created_at"),
                rs.getTimestamp("order_delivered_at"),
                rs.getString("order_delivery_address"),
                customer,
                products,
                statuses);
    }

    /**
     * Converts this OrderDAO instance back into an Order object.
     *
     * @return An {@link Order} object representing this OrderDAO.
     */
    public Order toOrder() {
        return new Order(
                this.id,
                this.created,
                this.delivered,
                this.deliveryAddress,
                this.customer != null ? this.customer.toUser() : null,
                this.products != null ? this.products.stream().map(ProductDAO::toProduct).toList() : null,
                this.statuses != null ? this.statuses.stream().map(StatusDAO::toStatus).toList() : null);
    }
}