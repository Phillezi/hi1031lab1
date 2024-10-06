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
     * Closes resources used by DAO classes, checks if not null before trying to close to prevent NullPointerExeptions.
     * @param conn Connection or null
     * @param stmt Connection or null
     * @param rs Connection or null
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
