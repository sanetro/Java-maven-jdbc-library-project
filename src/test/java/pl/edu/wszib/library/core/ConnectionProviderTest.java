package pl.edu.wszib.library.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionProviderTest {
    @Test
    public void DatabaseConnectionSuccessTest() {
        ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
        connectionProvider.setUrl("jdbc:mysql://localhost:3306/librarydb");
        connectionProvider.setUser("root");
        connectionProvider.setPassword("");
        Assertions.assertDoesNotThrow(() -> connectionProvider.connect());
    }

    @Test
    public void DatabaseConnectionFailedBadDatabaseTest() {
        ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
        connectionProvider.setUrl("jdbc:mysql://localhost:3306/NotExists");
        connectionProvider.setUser("root");
        connectionProvider.setPassword("");
        Assertions.assertThrows(RuntimeException.class, () -> connectionProvider.connect());
    }

    @Test
    public void DatabaseConnectionFailedBadUserTest() {
        ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
        connectionProvider.setUrl("jdbc:mysql://localhost:3306/librarydb");
        connectionProvider.setUser("NotExists");
        connectionProvider.setPassword("");
        Assertions.assertThrows(RuntimeException.class, () -> connectionProvider.connect());
    }

    @Test
    public void DatabaseConnectionFailedBadPasswordTest() {
        ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
        connectionProvider.setUrl("jdbc:mysql://localhost:3306/librarydb");
        connectionProvider.setUser("root");
        connectionProvider.setPassword("TotalyNOtPassword");
        Assertions.assertThrows(RuntimeException.class, () -> connectionProvider.connect());
    }

    @Test
    public void ConnectMethodSuccessfullyObtain() {
        ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
        Connection actual = connectionProvider.connect();
        Assertions.assertTrue(actual instanceof Connection);
    }

}
