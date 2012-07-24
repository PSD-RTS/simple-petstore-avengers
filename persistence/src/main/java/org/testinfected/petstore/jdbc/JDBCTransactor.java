package org.testinfected.petstore.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCTransactor implements Transactor {
    private final Connection connection;

    public JDBCTransactor(Connection connection) {
        this.connection = connection;
    }

    public void perform(UnitOfWork unitOfWork) throws Exception {
        boolean autoCommit = connection.getAutoCommit();
        
        try {
            connection.setAutoCommit(false);
            unitOfWork.execute();
            connection.commit();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            resetAutoCommitTo(autoCommit);
        }
    }

    private void resetAutoCommitTo(boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException ignored) {
        }
    }
}
