package by.academy.service;

import by.academy.DAO.DAO;
import by.academy.DAO.DAOImplement;
import by.academy.Entity.Car;
import by.academy.util.JDBCConnection;
import by.academy.util.Reflection;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

public class AppJDBC {
    public static void main(String[] args) throws SQLException {

        Connection con = JDBCConnection.getConnect();

        DAOImplement carDAO = new DAOImplement(new Car(),con);
        Car car = new Car();
        Car car2 = new Car(2, "audi", "white", 12000);
        Car car3 = new Car(3, "mercedes", "black", 19000);
        Car car4 = new Car(4, "lada", "brown", 7000);
        Car car5 = new Car(5, "geely", "brown", 7000);

        carDAO.insert(car2);
        carDAO.insert(car3);
        carDAO.insert(car4);
        carDAO.insert(car5);
        List<Object> list = carDAO.select().get(0);
        System.out.println(list);
        carDAO.update(new Car(4, "volvo", "red", 9000), 4);
        carDAO.delete(car4, 4);


    }
}
