package practice;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ConnectionPool extends Supplier<Connection>, AutoCloseable {
    String DB_DRIVER = "driver";
    String DB_URL = "url";
    String DB_POLL_SIZE = "poolSize";

    @SneakyThrows
    static ConnectionPool byProperties(String propertiesFilePath) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(propertiesFilePath)) {
            properties.load(fileInputStream);
        }

        Class.forName((String) properties.remove(DB_DRIVER));
        String url = (String) properties.remove(DB_URL);
        int poolSize = Integer.parseInt((String) properties.remove(DB_POLL_SIZE));

        return new ArrayBlockingQueueConnectionPool(poolSize, () -> getConnection(url, properties));
    }

    // getConnection(url, properties)

    @Private
    @SneakyThrows
    static Connection getConnection(String url, Properties properties) {
        assert properties.containsKey("user");
        assert properties.containsKey("password");
        assert properties.size() == 2;
        return DriverManager.getConnection(url, properties);
    }

    default <T> T mapConnection(Function<Connection, T> connectionTFunction) {
        try(Connection connection = get()) {
            return connectionTFunction.apply(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    default void withConnection(Consumer<Connection> connectionTFunction) {
        try(Connection connection = get()) {
            connectionTFunction.accept(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
