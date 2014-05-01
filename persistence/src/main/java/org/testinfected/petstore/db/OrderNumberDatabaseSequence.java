package org.testinfected.petstore.db;

import org.testinfected.petstore.order.OrderNumber;
import org.testinfected.petstore.order.OrderNumberSequence;
import org.testinfected.petstore.db.support.JDBCException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class OrderNumberDatabaseSequence implements OrderNumberSequence {
    private final Connection connection;

    public OrderNumberDatabaseSequence(Connection connection) {
        this.connection = connection;
    }

    public OrderNumber nextOrderNumber() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            int updateCount = statement.executeUpdate("insert into order_numbers() values()", RETURN_GENERATED_KEYS);
            if (updateCount != 1) {
                throw new SQLException("Unexpected row count of " + updateCount + "; expected was 1");
            }

            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            return new OrderNumber(keys.getLong(1));
        } catch (SQLException e) {
            throw new JDBCException("Could not generate order number", e);
        } finally {
            close(statement);
        }
    }

    private void close(Statement statement) {
        if (statement == null) return;
        try {
            statement.close();
        } catch (SQLException ignored) {
        }
    }
}
