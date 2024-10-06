package se.kth.hi1031.lab1.db.dao.user;

import lombok.Data;
import se.kth.hi1031.lab1.bo.model.user.Permission;
import se.kth.hi1031.lab1.bo.model.user.Role;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.DBConnectionManager;

import static se.kth.hi1031.lab1.db.dao.DBUtil.cleanUp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoleDAO {
    private final String name;
    private final List<PermissionDAO> permissions;

    public Role toRole() {
        List<Permission> permissions = this.permissions != null ? this.permissions.stream().map(PermissionDAO::toPermission).toList() : null;
        return new Role(this.name, permissions);
    }

    public static List<String> getAvailableRoles() {
        List<String> availableRoles = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            String query = "SELECT name FROM available_roles";

            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                availableRoles.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            cleanUp(conn, stmt, rs);
        }
        return availableRoles;
    }

    public static RoleDAO getRole(String name) {
        List<PermissionDAO> permissions = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            String query = "SELECT permission FROM permissions_t WHERE role = ?";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            rs = stmt.executeQuery();

            while (rs.next()) {
                permissions.add(new PermissionDAO(rs.getString("permission")));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            cleanUp(conn, stmt, rs);
        }
        return new RoleDAO(name, permissions);
    }
}
