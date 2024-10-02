package se.kth.hi1031.lab1.db;

import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.bo.Role;
import se.kth.hi1031.lab1.bo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

@Getter
@Setter
public class DBUser extends User implements DBObject<DBUser>  {

    private Integer id;

    private DBUser(Integer id,
                  String name,
                  String email,
                  String password,
                  ArrayList<Role> roles) {
        super(name, email, password, roles);
        this.id = id;
    }

    @Override
    public DBUser update() throws SQLException {
        Connection conn = DBConnectionManager.getInstance().getConnection();
        conn.setAutoCommit(false);
        try {
            String query = "UPDATE user_t SET name = ?, email = ?, password = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, getName());
            ps.setString(3, getEmail());
            ps.setString(4, getPassword());
            ps.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
        }
        return this;
    }

    @Override
    public DBUser add() throws SQLException {
        Connection conn = DBConnectionManager.getInstance().getConnection();
        conn.setAutoCommit(false);
        try {
            String query = "INSERT INTO user_t (name, email, password) VALUES (?, ?, ?) RETURNING id";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, getName());
            ps.setString(2, getEmail());
            ps.setString(3, getPassword());
            int id = ps.executeUpdate();
            this.setId(id);

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
        }
        return this;
    }

    @Override
    public DBUser save() throws SQLException {
        if (this.getId() == null) {
            return this.add();
        } else {
            return this.update();
        }
    }

    @Override
    public DBUser load() throws SQLException {
        return null;
    }
}
