package org.testinfected.petstore.jdbc;

import com.pyxis.petstore.domain.product.Product;
import com.pyxis.petstore.domain.product.ProductCatalog;
import org.testinfected.petstore.ExceptionImposter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductsDatabase implements ProductCatalog {

    private final Connection connection;

    public ProductsDatabase(Connection connection) {
        this.connection = connection;
    }

    public void add(Product product) {
        PreparedStatement insert = null;
        try {
            insert = connection.prepareStatement("INSERT INTO products(name, description, photo_file_name, number) values(?, ?, ?, ?)");
            insert.setString(1, product.getName());
            insert.setString(2, product.getDescription());
            insert.setString(3, product.getPhotoFileName());
            insert.setString(4, product.getNumber());
            int rowsInserted = insert.executeUpdate();
            if (rowsInserted != 1) {
                throw new SQLException("Unexpected row count: " + rowsInserted + "; expected: 1");
            }
        } catch (SQLException e) {
            throw ExceptionImposter.imposterize(e);
        } finally {
            if (insert != null) close(insert);
        }
    }

    public List<Product> findByKeyword(String keyword) {
        List<Product> matches = new ArrayList<Product>();
        PreparedStatement query = null;
        try {
            query = connection.prepareStatement("SELECT name, number FROM products WHERE LOWER(name) LIKE ? OR LOWER(description) LIKE ?");
            query.setString(1, matchAnywhere(keyword));
            query.setString(2, matchAnywhere(keyword));
            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                Product product = new Product(resultSet.getString("number"), resultSet.getString("name"));
                matches.add(product);
            }
        } catch (SQLException e) {
            throw ExceptionImposter.imposterize(e);
        } finally {
            if (query != null) close(query);

        }
        return matches;
    }

    private void close(Statement statement) {
        try {
            statement.close();
        } catch (SQLException ignored) {
            // todo monitor exceptions
        }
    }

    private String matchAnywhere(final String pattern) {
        return "%" + pattern + "%";
    }
}
