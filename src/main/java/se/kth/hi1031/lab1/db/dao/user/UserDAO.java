package se.kth.hi1031.lab1.db.dao.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.DBConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserDAO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private List<RoleDAO> roles;
    private List<PermissionDAO> permissions;

    public static List<UserDAO> getUsers() throws DAOException {
        List<UserDAO> users = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            String query = "SELECT " +
                    "u.id AS user_id, u.name AS user_name, u.email AS user_email, u.hashed_pw AS user_hashed_pw, " +
                    "ARRAY_AGG(DISTINCT r.role) AS user_roles, " +
                    "ARRAY_AGG(DISTINCT p.permission) AS user_role_permissions, " +
                    "FROM user_t u " +
                    "LEFT JOIN roles r ON u.id = r.user_id " +
                    "LEFT JOIN permissions_t p ON r.role = p.role" +
                    "GROUP BY u.id";

            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(toDAO(rs));
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
        return users;
    }

    public static Optional<UserDAO> getUserByid(int id) throws DAOException {
        Optional<UserDAO> user = Optional.empty();
        Connection conn = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            String query = "SELECT " +
                    "u.id AS user_id, " +
                    "u.name AS user_name, " +
                    "u.email AS user_email, " +
                    "u.hashed_pw AS user_hashed_pw, " +
                    "ARRAY_AGG(DISTINCT r.role) AS user_roles, " +
                    "ARRAY_AGG(DISTINCT p.permission) AS user_role_permissions, " +
                    "FROM user_t u " +
                    "LEFT JOIN roles r ON u.id = r.user_id " +
                    "LEFT JOIN permissions_t p ON r.role = p.role" +
                    "WHERE u.id = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                user = Optional.of(toDAO(rs));
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
        return user;
    }

    public static UserDAO toDAO(ResultSet rs) throws SQLException {

        Array permsArr = rs.getArray("user_role_permissions");
        List<PermissionDAO> permissions = Arrays.stream((String[]) permsArr.getArray())
                .map(PermissionDAO::new)
                .collect(Collectors.toList());

        Array rolesArr = rs.getArray("user_roles");
        List<RoleDAO> roles = Arrays.stream((String[]) rolesArr.getArray())
                .map((String role) -> new RoleDAO(role, new ArrayList<>()))
                .collect(Collectors.toList());

        return new UserDAO(
                rs.getInt("user_id"),
                rs.getString("user_name"),
                rs.getString("user_email"),
                rs.getString("user_hashed_pw"),
                roles,
                permissions
        );
    }

    /**
     * Gets multiple Users from a ResultSet
     * TODO: get roles and permissions for each user somehow (array in array)
     * @param rs The resultset.
     * @return A List of the UserDAOs
     * @throws SQLException
     */
    public static List<UserDAO> toDAOs(ResultSet rs) throws SQLException {
        Integer[] ids = (Integer[])rs.getArray("users_id").getArray();
        String[] emails = (String[]) rs.getArray("users_email").getArray();
        String[] names = (String[]) rs.getArray("users_name").getArray();
        String[] passwords = (String[]) rs.getArray("users_password").getArray();

        if (ids == null ||
                names == null ||
                emails == null ||
                passwords == null
        ) {
            return null;
        }

        List<UserDAO> daos = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            daos.add(new UserDAO(
                    ids[i],
                    emails[i],
                    names[i],
                    passwords[i],
                    null,
                    null
                    ));
        }

        return daos;
    }
}
