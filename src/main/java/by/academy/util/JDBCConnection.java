package by.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JDBCConnection {

    public static final String DATABASE_RESOURCE = "database";

    public static final String URL_KEY = "url";
    public static final String USER_KEY = "user";
    public static final String PASSWORD_KEY = "password";


    private static final String URL = getValue(URL_KEY);
    private static final String USER = getValue(USER_KEY);
    private static final String PASSWORD = getValue(PASSWORD_KEY);

    public static String getValue(String key){
        ResourceBundle resource = ResourceBundle.getBundle(DATABASE_RESOURCE);
        return resource.getString(key);
    }

    public static Connection getConnect() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle(DATABASE_RESOURCE);
        String url = resource.getString("url");
        String user = resource.getString("user");
        String pass = resource.getString("password");
        return DriverManager.getConnection(url, user, pass);
    }


}
