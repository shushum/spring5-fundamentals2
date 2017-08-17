package practice;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConnectionPoolTest {

    public static final String PROPERTIES_FILE_PATH =
            "/Users/admin/IdeaProjects/experiments/spring5-fundamentals2/src/test/resources/jdbc.properties";

    static ConnectionPool connectionPool =
            ConnectionPool.byProperties(PROPERTIES_FILE_PATH);

    @BeforeAll
    @SneakyThrows
    static void setUp() {
        try (Connection con = connectionPool.get();
             Statement st = con.createStatement()) {
            st.executeUpdate("CREATE TABLE country (" +
                    "  id        INT PRIMARY KEY AUTO_INCREMENT," +
                    "  name      VARCHAR(255)," +
                    "  code_name VARCHAR(255))");
        }
    }

    @BeforeEach
    @SneakyThrows
    void initCountry() {
        try (Connection con = connectionPool.get();
             Statement st = con.createStatement()) {
            st.executeUpdate(
                    "INSERT INTO country (name, code_name) VALUES ('Russia', 'Ru')");
        }
    }

    @Test
    @SneakyThrows
    void getCountry() {
        connectionPool.withConnection(connection -> {
            try (Statement st = connection.createStatement();
                 ResultSet rs = st.executeQuery("SELECT * FROM country WHERE code_name='Ru'")) {
                assertTrue(rs.next());
                assertThat(rs.getInt("id"), is(1));
                assertThat(rs.getString("name"), is("Russia"));
                assertThat(rs.getString("code_name"), is("Ru"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}