package se.kth.hi1031.lab1.db.dao.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import se.kth.hi1031.lab1.bo.model.user.Permission;
import se.kth.hi1031.lab1.bo.model.user.Role;
import se.kth.hi1031.lab1.bo.model.user.User;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.DBConnectionManager;

import static se.kth.hi1031.lab1.db.dao.DBUtil.cleanUp;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;

@Data
@AllArgsConstructor
public class UserDAO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private List<RoleDAO> roles;
    private List<PermissionDAO> permissions;

    /**
     * Gets all users with all joint attributes related to the user from the database.
     * @return A list containing all the users in the database.
     */
    public static List<UserDAO> getUsers() throws DAOException {
        List<UserDAO> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            String query = "SELECT " + "u.id AS user_id, u.name AS user_name, u.email AS user_email, u.hashed_pw AS user_hashed_pw, " + "ARRAY_AGG(DISTINCT r.role) AS user_roles, " + "ARRAY_AGG(DISTINCT p.permission) AS user_role_permissions " + "FROM user_t u " + "LEFT JOIN roles r ON u.id = r.user_id " + "LEFT JOIN permissions_t p ON r.role = p.role " + "GROUP BY u.id";

            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(toDAO(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            cleanUp(conn, stmt, rs);
        }
        return users;
    }

    /**
     * Wrapper to get user by given id and do it on a new connection.
     * @param id the id of the user to get.
     * @return An optional containing the user if found.
     */
    public static Optional<UserDAO> getUserByid(int id) throws DAOException {
        return getUserByid(id, null);
    }

    /**
     * Get user by id.
     * @param id The id of the user to get.
     * @param conn The connection to use, null if new connection is wanted. Will not commit if connection is provided.
     *             Is dependant on upstream to commit the transaction.
     * @return An Optional containing the user if found.
     */
    public static Optional<UserDAO> getUserByid(int id, Connection conn) throws DAOException {
        Optional<UserDAO> user = Optional.empty();
        boolean isChild = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            if (conn == null) {
                conn = DBConnectionManager.getInstance().getConnection();
            } else {
                isChild = true;
            }
            String query = "SELECT " + "u.id AS user_id, " + "u.name AS user_name, " + "u.email AS user_email, " + "u.hashed_pw AS user_hashed_pw, " + "ARRAY_AGG(DISTINCT r.role) AS user_roles, " + "ARRAY_AGG(DISTINCT p.permission) AS user_role_permissions " + "FROM user_t u " + "LEFT JOIN roles r ON u.id = r.user_id " + "LEFT JOIN permissions_t p ON r.role = p.role " + "WHERE u.id = ? " + "GROUP BY u.id";

            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                user = Optional.of(toDAO(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (!isChild) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            cleanUp(null, stmt, rs);
        }
        return user;
    }

    /**
     * Method to try to get a user that matches the credentials.
     * @param credentials The user attributes to match (must contain a cleartext password to compare to the hashed password).
     * @return An optional containing the user if found and password matches.
     */
    public static Optional<UserDAO> login(User credentials) throws DAOException {
        Optional<UserDAO> user = Optional.empty();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            String query = "SELECT " + "u.id AS user_id, " + "u.name AS user_name, " + "u.email AS user_email, " + "u.hashed_pw AS user_hashed_pw, " + "ARRAY_AGG(DISTINCT r.role) AS user_roles, " + "ARRAY_AGG(DISTINCT p.permission) AS user_role_permissions " + "FROM user_t u " + "LEFT JOIN roles r ON u.id = r.user_id " + "LEFT JOIN permissions_t p ON r.role = p.role " + "WHERE u.email = ? " + "GROUP BY u.id";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, credentials.getEmail());
            rs = stmt.executeQuery();

            while (rs.next()) {
                String hashedPW = rs.getString("user_hashed_pw");
                if (BCrypt.checkpw(credentials.getPassword(), hashedPW)) {
                    user = Optional.of(toDAO(rs));
                } else {
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            cleanUp(conn, stmt, rs);
        }
        return user;
    }

    /**
     * Creates the given user in the database.
     *
     * @param user The user to create.
     * @return The created user.
     */
    public static UserDAO createUser(User user) throws DAOException {
        UserDAO created = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            conn.setAutoCommit(false);

            String query = "INSERT INTO user_t (name, email, hashed_pw) VALUES (?, ?, ?) RETURNING id";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());

            rs = stmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                user.setId(userId);

                String roleQuery = "INSERT INTO roles (role, user_id) VALUES (?, ?)";
                for (Role role : user.getRoles()) {
                    stmt = conn.prepareStatement(roleQuery);
                    stmt.setString(1, role.getName());
                    stmt.setInt(2, userId);
                    stmt.executeUpdate();
                }
                conn.commit();

                Optional<UserDAO> userOptional = getUserByid(userId);
                if (userOptional.isPresent()) {
                    created = userOptional.get();
                }
            } else {
                conn.commit();
            }
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
            throw new DAOException(e.getMessage());
        } finally {
            cleanUp(conn, stmt, rs);
        }

        return created;
    }

    /**
     * Updates a user
     * @param user the updated user.
     */
    public static void updateUser(User user) throws DAOException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            conn.setAutoCommit(false);

            StringBuilder queryBuilder = new StringBuilder("UPDATE user_t SET ");
            List<String> setClauses = new ArrayList<>();
            List<Object> parameters = new ArrayList<>();

            if (user.getName() != null && !user.getName().isEmpty()) {
                setClauses.add("name = ?");
                parameters.add(user.getName());
            }
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                setClauses.add("email = ?");
                parameters.add(user.getEmail());
            }
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                setClauses.add("hashed_pw = ?");
                parameters.add(user.getPassword());
            }

            if (setClauses.isEmpty()) {
                throw new DAOException("No fields to update.");
            }

            queryBuilder.append(String.join(", ", setClauses));
            queryBuilder.append(" WHERE id = ?");
            parameters.add(user.getId());

            stmt = conn.prepareStatement(queryBuilder.toString());

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                String deleteRolesQuery = "DELETE FROM roles WHERE user_id = ?";
                stmt = conn.prepareStatement(deleteRolesQuery);
                stmt.setInt(1, user.getId());
                stmt.executeUpdate();

                String roleQuery = "INSERT INTO roles (role, user_id) VALUES (?, ?)";
                for (Role role : user.getRoles()) {
                    stmt = conn.prepareStatement(roleQuery);
                    stmt.setString(1, role.getName());
                    stmt.setInt(2, user.getId());
                    stmt.executeUpdate();
                }

                conn.commit();
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
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
            cleanUp(conn, stmt, null);
        }
    }

    /**
     * Deletes a user by the given id.
     * @param id the id of the user to delete.
     */
    public static void deleteUserById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            conn.setAutoCommit(false);

            String query = "DELETE FROM user_t WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                conn.commit();
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
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
            cleanUp(conn, stmt, null);
        }
    }

    /**
     * Get a user from a resultset.
     * @param rs The resultset.
     * @return A List of the UserDAOs
     * @throws SQLException
     */
    public static UserDAO toDAO(ResultSet rs) throws SQLException {
        List<PermissionDAO> permissions = null;
        try {
            Array permsArr = rs.getArray("user_role_permissions");
            permissions = Arrays.stream((String[]) permsArr.getArray()).map(PermissionDAO::new).collect(Collectors.toList());
        } catch (SQLException ignored) {

        }

        List<RoleDAO> roles = null;
        try {
            Array rolesArr = rs.getArray("user_roles");
            roles = Arrays.stream((String[]) rolesArr.getArray()).map((String role) -> new RoleDAO(role, new ArrayList<>())).collect(Collectors.toList());
        } catch (SQLException ignored) {

        }

        return new UserDAO(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("user_email"), rs.getString("user_hashed_pw"), roles, permissions);
    }

    /**
     * Gets multiple Users from a ResultSet
     * @param rs The resultset.
     * @return A List of the UserDAOs
     * @throws SQLException
     */
    public static List<UserDAO> toDAOs(ResultSet rs) throws SQLException {
        Integer[] ids = (Integer[]) rs.getArray("users_id").getArray();
        String[] emails = (String[]) rs.getArray("users_email").getArray();
        String[] names = (String[]) rs.getArray("users_name").getArray();
        String[] passwords = (String[]) rs.getArray("users_password").getArray();

        if (ids == null || names == null || emails == null || passwords == null) {
            return null;
        }

        List<UserDAO> daos = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            daos.add(new UserDAO(ids[i], emails[i], names[i], passwords[i], null, null));
        }

        return daos;
    }

    /**
     * Conversion method to convert from a DAO object to a BO object.
     * @return A BO object of the same attributes, (deep copied).
     */
    public User toUser() {
        List<Role> roles = this.roles != null ? this.roles.stream().map(RoleDAO::toRole).toList() : null;
        List<Permission> permissions = this.permissions != null ? this.permissions.stream().map(PermissionDAO::toPermission).toList() : null;
        return new User(this.id, this.name, this.email, this.password, roles, permissions);
    }
}
