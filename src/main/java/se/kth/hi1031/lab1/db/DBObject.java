package se.kth.hi1031.lab1.db;

import java.sql.SQLException;

public interface DBObject<T> {
    public T add() throws SQLException;
    public T update() throws SQLException;
    public T save() throws SQLException;
    public T load() throws SQLException;;
}
