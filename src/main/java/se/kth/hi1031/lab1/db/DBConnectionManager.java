package se.kth.hi1031.lab1.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBConnectionManager {

    private static DBConnectionManager instance;
    private final List<DBConnection> connectionPool;
    private final List<DBConnection> usedConnections;
    private final int MAX_POOL_SIZE;

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
        usedConnections = new ArrayList<>(MAX_POOL_SIZE / 2);
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
                DBConnection dbConnection = new DBConnection(DBConnectionManager.instance);
                connectionPool.add(dbConnection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty() && usedConnections.size() < MAX_POOL_SIZE) {
            DBConnection dbConnection = new DBConnection(DBConnectionManager.instance);
            usedConnections.add(dbConnection);
            return dbConnection;

        } else if (!connectionPool.isEmpty()) {
            DBConnection connection = connectionPool.removeLast();
            usedConnections.add(connection);
            return connection;
        } else {
            throw new SQLException("No available connections");
        }
    }

    public synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            DBConnection dbConnectionToRelease = null;
            for (DBConnection dbConnection : usedConnections) {
                if (dbConnection == connection) {
                    dbConnectionToRelease = dbConnection;
                    break;
                }
            }

            if (dbConnectionToRelease != null) {
                usedConnections.remove(dbConnectionToRelease);
                connectionPool.add(dbConnectionToRelease);
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
