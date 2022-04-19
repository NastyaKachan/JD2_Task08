package by.academy.util;

import java.util.ResourceBundle;

public class JDBCUtils {
    public static final String DATABASE_RESOURCE = "liquibase";

    public static final String URL_KEY = "url";
    public static final String USERS = "username";
    public static final String PASS = "password";
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(DATABASE_RESOURCE);


    public static String getValue(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
}
