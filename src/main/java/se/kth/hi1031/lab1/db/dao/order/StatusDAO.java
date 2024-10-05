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

@Getter
@Setter
@AllArgsConstructor
@ToString
public class StatusDAO {
    private String status;
    private Timestamp timestamp;

    public static StatusDAO createStatus(int orderId, Status status) {
        return createStatus(orderId, status, null);
    }

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

    public static StatusDAO toDAO(ResultSet rs) throws SQLException {
        return new StatusDAO(
                rs.getString("status_status"),
                rs.getTimestamp("status_timestamp")
        );
    }

    public static List<StatusDAO> toDAOs(ResultSet rs) throws SQLException {
        String[] statusArr = (String[])rs.getArray("statuses_status").getArray();
        Timestamp[] timestampArr = (Timestamp[]) rs.getArray("statuses_timestamp").getArray();

        if (statusArr == null || timestampArr == null) {
            return null;
        }

        List<StatusDAO> daos = new ArrayList<>();
        for (int i = 0; i < statusArr.length; i++) {
            if(statusArr[i] != null && timestampArr[i] != null) {
                daos.add(new StatusDAO(statusArr[i], timestampArr[i]));
            }
        }

        return daos;
    }

    public Status toStatus() {
        return new Status(this.status, this.timestamp);
    }

}
