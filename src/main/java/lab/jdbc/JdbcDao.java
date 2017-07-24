package lab.jdbc;

import lombok.SneakyThrows;
import lombok.val;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Function;
import java.util.function.Supplier;

public interface JdbcDao extends Supplier<Connection> {

    @SneakyThrows
    default <T> T mapConnection(Function<Connection, T> connectionFunction){
        try(Connection connection = get()){
           return connectionFunction.apply(connection);
        }
    }

    @SneakyThrows
    default <T> T mapPreparedStatement(String sql, Function<PreparedStatement, T> preparedStatementFunction, Object... params) {
        return mapConnection(connection -> {
            try(val preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < params.length; i++)
                    preparedStatement.setObject(i, params[i]);
                return preparedStatementFunction.apply(preparedStatement);
            } catch (SQLException ex){
                throw new RuntimeException(ex);
            }
        });
    }

    @SneakyThrows
    default <T> T mapResultSet() {
        return null;
    }
}
