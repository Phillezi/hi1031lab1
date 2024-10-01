package se.kth.hi1031.lab1.db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for connections with the database
 */
@Getter
public class DBConnection {

    private final Connection connection;

    public DBConnection() throws SQLException {
        String dbUri = System.getenv("DB_URI") != null ? System.getenv("DB_URI")
                : "jdbc:postgresql://localhost:5432/postgres";
        String dbUser = System.getenv("DB_USER") != null ? System.getenv("DB_USER")
                : "myuser";
        String dbPassword = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD")
                : "mypassword";

        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(dbUri, dbUser, dbPassword);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found.", e);
        } catch (SQLException e) {
            throw new SQLException("Connection failed. Check DB credentials or URL.", e);
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
