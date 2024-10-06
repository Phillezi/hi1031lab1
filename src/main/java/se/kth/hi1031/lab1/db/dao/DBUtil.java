package se.kth.hi1031.lab1.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class to clean up after db calls.
 */
public class DBUtil {
    /**
     *
     * @param conn Connection or null, checks if not null before trying to close to prevent 
     * @param stmt
     * @param rs
     */
    public static void cleanUp(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
