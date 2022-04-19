package by.academy.util.DAO;

import by.academy.DAO.DAOImplement;
import by.academy.Entity.Car;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DAOImplementTest {
    public static final String DATABASE_TEST = "H2";
    public static final String URL_TEST = "url";
    public static final String USER_TEST = "user";
    public static final String PASSWORD_TEST = "password";
    Car carTest1 = new Car(1, "opel", "red", 5500);
    Car carTest2 = new Car(2, "ford", "white", 4000);
    Car carTest3 = new Car(3, "opel", "red", 5500);
    String car1 = "[1, opel, red, 5500]";
    String car2 = "[2, audi, black, 9500]";

    ResourceBundle resourceBundle = ResourceBundle.getBundle(DATABASE_TEST);
    String url_t = resourceBundle.getString(URL_TEST);
    String user_t = resourceBundle.getString(USER_TEST);
    String pass_t = resourceBundle.getString(PASSWORD_TEST);
    Connection connection = DriverManager.getConnection(url_t, user_t, pass_t);
    DAOImplement daoImplement = new DAOImplement(new Car(), connection);

    public DAOImplementTest() throws SQLException {
    }


    @BeforeClass
    public static void beforeTest() throws SQLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DATABASE_TEST);
        String url_t = resourceBundle.getString(URL_TEST);
        String user_t = resourceBundle.getString(USER_TEST);
        String pass_t = resourceBundle.getString(PASSWORD_TEST);
        Connection connectionTest = DriverManager.getConnection(url_t, user_t, pass_t);
        connectionTest.setAutoCommit(false);
        Statement statement = connectionTest.createStatement();
        DAOImplement daoImplement = new DAOImplement(new Car(), connectionTest);

        String SQL_CREATE_TABLE = "create table carTest\n" +
                "(\n" +
                "    id    int         not null\n" +
                "        primary key,\n" +
                "    name  varchar(50) not null,\n" +
                "    color varchar(50) not null,\n" +
                "    price int         not null\n" +
                ")";
        statement.executeUpdate(SQL_CREATE_TABLE);
        connectionTest.commit();
    }

    @Test
    public void aInsertTest() throws SQLException {
        String expected = carTest1.toString();
        daoImplement.insert(carTest1);
        String actual = daoImplement.select().get(0).toString();
        Assert.assertEquals(car1, actual);
    }

    @Test
    public void bUpdateTest() {
        daoImplement.insert(carTest3);
        daoImplement.update(new Car(2, "audi", "black", 9500), 2);
        String expected = car2;
        String actual = daoImplement.select().get(1).toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void cDeleteTest() {
        daoImplement.insert(carTest1);
        try {
            daoImplement.delete(carTest1, 1);
        } catch (Exception exception) {
            System.out.println("Object successfully delete");
        }

    }
}