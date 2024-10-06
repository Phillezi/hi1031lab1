package se.kth.hi1031.lab1.db.dao.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.bo.model.product.Category;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.DBConnectionManager;
import se.kth.hi1031.lab1.db.dao.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDAO {
    private String name;
    private String description;

    public static List<String> getAvailableCategories() {
        List<String> availableCategories = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            String query = "SELECT name FROM available_categories";

            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                availableCategories.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DBUtil.cleanUp(conn, stmt, rs);

        }
        return availableCategories;
    }

    public static List<CategoryDAO> getCategories() {
        List<CategoryDAO> availableCategories = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            String query = "SELECT name, description FROM available_categories";

            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                availableCategories.add(new CategoryDAO(rs.getString("name"), rs.getString("description")));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DBUtil.cleanUp(conn, stmt, rs);
        }
        return availableCategories;
    }

    public static Optional<CategoryDAO> getCategoryByName(String name) {
        Optional<CategoryDAO> category = Optional.empty();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            String query = "SELECT name, description FROM available_categories WHERE name = ?";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            rs = stmt.executeQuery();

            if (rs.next()) {
                category = Optional.of(new CategoryDAO(rs.getString("name"), rs.getString("description")));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DBUtil.cleanUp(conn, stmt, rs);
        }
        return category;
    }

    public static void createCategory(Category category) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            String query = "INSERT INTO available_categories (name, description) VALUES (?, ?)";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DBUtil.cleanUp(conn, stmt, null);
        }
    }

    public static void updateCategory(Category category) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            String query = "UPDATE available_categories SET description = ? WHERE name = ?";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, category.getDescription());
            stmt.setString(2, category.getName());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DBUtil.cleanUp(conn, stmt, null);
        }
    }

    public Category toCategory() {
        return new Category(name, description);
    }
}
