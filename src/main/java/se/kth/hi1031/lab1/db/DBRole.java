package se.kth.hi1031.lab1.db;

import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.bo.Permission;
import se.kth.hi1031.lab1.bo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

@Getter
@Setter
public class DBRole extends Role implements DBObject<DBRole>{

    private Integer userId;

    public DBRole(Integer userId, String name, ArrayList<Permission> permissions) {
        super(name, permissions);
        this.userId = userId;
    }

    @Override
    public DBRole add() throws SQLException {
        Connection conn = DBConnectionManager.getInstance().getConnection();
        conn.setAutoCommit(false);
        try {
            String query = "INSERT INTO roles (name, userID) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, getName());
            ps.setInt(2, getUserId());
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
        }
        return this;
    }

    @Override
    public DBRole update() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DBRole save() throws SQLException {
        return null;
    }

    @Override
    public DBRole load() throws SQLException {
        return null;
    }
}
