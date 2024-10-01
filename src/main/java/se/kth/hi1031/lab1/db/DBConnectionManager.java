package se.kth.hi1031.lab1.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBConnectionManager {

    private static DBConnectionManager instance;
    private final List<DBConnection> connectionPool;
    private final int MAX_POOL_SIZE;
    private int currentConnections = 0;

    private DBConnectionManager() {
        String maxPoolSizeEnv = System.getenv("DB_MAX_POOL_SIZE");
        if (maxPoolSizeEnv != null) {
            int candidatePoolSize = -1;
            try {
                candidatePoolSize = Integer.parseInt(maxPoolSizeEnv);
            } catch (NumberFormatException e) {
                System.err.println("Invalid DB_MAX_POOL_SIZE value, using default of 10.");
            } finally {
                if (candidatePoolSize > 0 && candidatePoolSize <= 1024) {
                    MAX_POOL_SIZE = candidatePoolSize;
                } else {
                    MAX_POOL_SIZE = 10;
                }
            }
        } else {
            MAX_POOL_SIZE = 10;
        }
        connectionPool = new ArrayList<>(MAX_POOL_SIZE);
        initializePool();
    }

    public static synchronized DBConnectionManager getInstance() {
        if (instance == null) {
            instance = new DBConnectionManager();
        }
        return instance;
    }

    private void initializePool() {
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            try {
                DBConnection dbConnection = new DBConnection();
                connectionPool.add(dbConnection);
                currentConnections++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty() && currentConnections < MAX_POOL_SIZE) {
            connectionPool.add(new DBConnection());
            currentConnections++;
        }

        if (connectionPool.isEmpty()) {
            throw new SQLException("No available connections");
        }

        DBConnection dbConnection = connectionPool.remove(connectionPool.size() - 1);
        return dbConnection.getConnection();
    }

    public synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            for (DBConnection dbConnection : connectionPool) {
                if (dbConnection.getConnection() == connection) {
                    return; // Connection is already in the pool
                }
            }
            try {
                // Wrap the released connection in a new DBConnection
                DBConnection dbConnection = new DBConnection();
                connectionPool.add(dbConnection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closePool() {
        for (DBConnection dbConnection : connectionPool) {
            try {
                dbConnection.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        connectionPool.clear();
    }
}
