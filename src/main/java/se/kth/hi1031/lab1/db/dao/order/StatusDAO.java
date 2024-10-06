package se.kth.hi1031.lab1.db.dao.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.kth.hi1031.lab1.bo.model.order.Status;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.DBConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * DAO (Data Access Object) class for managing the persistence and retrieval of {@link Status} entities.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class StatusDAO {
    private String status;
    private Timestamp timestamp;

    /**
     * Creates a new StatusDAO instance with the specified order ID and Status.
     *
     * @param orderId The ID of the order associated with the status.
     * @param status  The Status object containing the status information.
     * @return A new StatusDAO instance representing the specified status.
     */
    public static StatusDAO createStatus(int orderId, Status status) {
        return createStatus(orderId, status, null);
    }

    /**
     * Creates a new status in the database for the specified order.
     *
     * @param orderId The ID of the order associated with the status.
     * @param status  The Status object containing the status information.
     * @param conn    The database connection to use. If null, a new connection will be created.
     * @return A new StatusDAO instance representing the created status.
     * @throws DAOException If an error occurs while accessing the database.
     */
    public static StatusDAO createStatus(int orderId, Status status, Connection conn) {
        boolean isChild = false;
        PreparedStatement stmt = null;
        try {
            if (conn == null) {
                conn = DBConnectionManager.getInstance().getConnection();
            } else {
                isChild = true;
            }

            String query = "INSERT INTO order_status (order_id, status, timestamp) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderId);
            stmt.setString(2, status.getStatus());
            stmt.setTimestamp(3, status.getTimestamp());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (conn != null) {
                if (!isChild) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return status.toDAO();
    }

    /**
     * Transforms a ResultSet into a StatusDAO object.
     *
     * @param rs The ResultSet containing the status data.
     * @return A StatusDAO object populated with the data from the ResultSet.
     * @throws SQLException If an error occurs while accessing the ResultSet.
     */
    public static StatusDAO toDAO(ResultSet rs) throws SQLException {
        return new StatusDAO(
                rs.getString("status_status"),
                rs.getTimestamp("status_timestamp")
        );
    }

    /**
     * Converts the statuses stored in a ResultSet into a list of StatusDAO objects.
     *
     * @param rs The ResultSet containing the statuses.
     * @return A list of StatusDAO objects representing the statuses in the ResultSet.
     * @throws SQLException If an error occurs while accessing the ResultSet.
     */
    public static List<StatusDAO> toDAOs(ResultSet rs) throws SQLException {
        String[] statusArr = (String[]) rs.getArray("statuses_status").getArray();
        Timestamp[] timestampArr = (Timestamp[]) rs.getArray("statuses_timestamp").getArray();

        if (statusArr == null || timestampArr == null) {
            return null;
        }

        List<StatusDAO> daos = new ArrayList<>();
        for (int i = 0; i < statusArr.length; i++) {
            if (statusArr[i] != null && timestampArr[i] != null) {
                daos.add(new StatusDAO(statusArr[i], timestampArr[i]));
            }
        }

        return daos;
    }

    /**
     * Converts this StatusDAO instance back into a Status object.
     *
     * @return A Status object representing this StatusDAO.
     */
    public Status toStatus() {
        return new Status(this.status, this.timestamp);
    }

}
