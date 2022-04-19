package by.academy.util.util;

import by.academy.util.JDBCConnection;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

public class JDBCConnectionTest {
    public static final String DATABASE_TEST = "H2";
    public static final String URL_TEST = "url";
    public static final String USER_TEST = "user";
    public static final String PASSWORD_TEST = "password";

    @Test
    public void getConnectTest() throws SQLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DATABASE_TEST);
        String url = resourceBundle.getString(URL_TEST);
        String user = resourceBundle.getString(USER_TEST);
        String pass = resourceBundle.getString(PASSWORD_TEST);
        Connection connection = DriverManager.getConnection(url, user, pass);
        assertNotNull(connection);
        assertFalse(connection.isClosed());
        String expected = connection.getMetaData().getURL();
        String actual = "jdbc:h2:mem:H2_db";
        assertEquals(expected, actual);
        connection.close();
        assertTrue(connection.isClosed());
    }

}