package se.kth.hi1031.lab1.db.dao.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.bo.model.order.Order;
import se.kth.hi1031.lab1.bo.model.order.Status;
import se.kth.hi1031.lab1.bo.model.product.Product;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.DBConnectionManager;
import se.kth.hi1031.lab1.db.dao.product.ProductDAO;
import se.kth.hi1031.lab1.db.dao.user.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class OrderDAO {
    private Integer id;
    private Timestamp created;
    private Timestamp delivered;
    private String deliveryAddress;
    private UserDAO customer;
    private List<ProductDAO> products;
    private List<StatusDAO> statuses;

    public OrderDAO(Order order) {
        this.id = order.getId();
        this.created = order.getCreated();
        this.delivered = order.getDelivered();
        this.deliveryAddress = order.getDeliveryAddress();
        this.customer = order.getCustomer();
        this.products = order.getProducts();
        this.statuses = order.getStatuses();
    }

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
                    "ARRAY_AGG(DISTINCT p.price) AS products_price, " +
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
                    "GROUP BY o.id";

            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(toDAO(rs));
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
        return orders;
    }

    public static Optional<OrderDAO> getOrdersById(int id) {
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
                    "ARRAY_AGG(DISTINCT p.price) AS products_price, " +
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
                    "WHERE o.id = ?";

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
                // TODO: mark as free to use
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return order;
    }

    public static OrderDAO createOrder(Order order) throws DAOException {
        Connection conn = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            conn.setAutoCommit(false);

            String query = "INSERT INTO orders (created_at, delivered_at, delivery_address, customer_id) VALUES (?, ?, ?, ?) RETURNING id";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setTimestamp(1, order.getCreated());
            stmt.setTimestamp(2, order.getDelivered());
            stmt.setString(3, order.getDeliveryAddress());
            stmt.setInt(4, order.getCustomer().getId());

            int id = stmt.executeUpdate();

            for (Product product : order.getProducts()) {
                String productsQuery = "INSERT INTO ordered_products (order_id, product_id) VALUES (?, ?)";
                PreparedStatement productsStmt = conn.prepareStatement(productsQuery);
                productsStmt.setInt(1, id);
                productsStmt.setInt(2, product.getId());

                productsStmt.executeUpdate();
            }

            // TODO: maybe insert into available status if not present there before inserting
            for (Status status : order.getStatuses()) {
                String statusQuery = "INSERT INTO order_status (order_id, status, timestamp) VALUES (?, ?, ?)";
                PreparedStatement statusStmt = conn.prepareStatement(statusQuery);
                statusStmt.setInt(1, id);
                statusStmt.setString(2, status.getStatus());
                statusStmt.setTimestamp(3, status.getTimestamp());

                statusStmt.executeUpdate();
            }

            conn.commit();
        } catch(SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return new OrderDAO(order);
    }

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
                statuses
        );
    }
}