package se.kth.hi1031.lab1.db.dao.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import se.kth.hi1031.lab1.bo.model.user.Permission;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.DBConnectionManager;

import static se.kth.hi1031.lab1.db.dao.DBUtil.cleanUp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class PermissionDAO {
    private final String name;

    public static List<String> getAvailablePermissions() {
        List<String> availablePermissions = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            String query = "SELECT name FROM available_permissions";

            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                availablePermissions.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            cleanUp(conn, stmt, rs);
        }
        return availablePermissions;
    }

    public Permission toPermission() {
        return new Permission(name);
    }
}
