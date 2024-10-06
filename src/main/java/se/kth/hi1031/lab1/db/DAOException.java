package se.kth.hi1031.lab1.db;

/**
 * Exceptions thrown from the DAO classes.
 * Exist to decouple the bo layer from a SQL database since a DAOException can be thrown independently of the db used.
 */
public class DAOException extends RuntimeException {
    public DAOException(String message) {
        super(message);
    }
}
