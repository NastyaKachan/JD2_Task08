package by.academy.DAO;

import by.academy.Entity.Car;
import by.academy.Entity.MyTable;
import by.academy.util.JDBCConnection;
import by.academy.util.Reflection;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DAOImplement implements DAO {

    private static final String SQL_SELECT = "SELECT * FROM %s";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM %s WHERE id = %s";
    private static final String SQL_INSERT = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String SQL_UPDATE = "UPDATE %s SET %s WHERE id = %s";
    private static final String SQL_DELETE = "DELETE FROM %s WHERE id = %s";
    private static final String TAB = "    ";

    Field[] declaredFields;
    Class[] typeFields;
    String tableName;
    String[] field;
    Connection connection = DAOImplement.this.setConnect();

    public Connection setConnect() throws SQLException {
        Connection connection = JDBCConnection.getConnect();
        return connection;
    }

    public DAOImplement(Car classDAO, Connection connection) throws SQLException {
        tableName = classDAO.getClass().getAnnotation(MyTable.class).name();
        field = Reflection.getFieldName(classDAO);
        declaredFields = classDAO.getClass().getDeclaredFields();
    }

    public String[] getFieldName() {
        return field;
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public List<List<Object>> select() {
        List<List<Object>> listAll = new LinkedList<>();
        try (//Connection con = JDBCConnection.getConnect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(String.format(SQL_SELECT, tableName))) {

            for (Field f : declaredFields) {
                f.setAccessible(true);
            }

            while (rs.next()) {
                List<Object> list = new LinkedList<>();
                for (int i = 1; i < getFieldName().length + 1; i++) {
                    list.add(rs.getObject(i));
                }
                listAll.add(list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listAll;
    }



    @Override
    public void update(Object t, Integer id) {
        tableName = t.getClass().getAnnotation(MyTable.class).name();
        StringBuilder fieldNames = new StringBuilder("");
        StringBuilder fieldValues = new StringBuilder("");
        try (//Connection con = JDBCConnection.getConnect();
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            declaredFields = t.getClass().getDeclaredFields();
            typeFields = new Class[declaredFields.length];
            int p = 0;
            for (Field f : declaredFields) {
                f.setAccessible(true);
                typeFields[p++] = f.getType();
            }

            String[] field = Reflection.getFieldName(t);
            for (int i = 0; i < field.length; i++) {
                String name = field[i];
                if (typeFields[i] == Integer.class) {
                    fieldNames.append(name)
                            .append(" = ")
                            .append(declaredFields[i].get(t));
                } else if (typeFields[i] == String.class) {
                    fieldNames.append(name)
                            .append(" = '")
                            .append(declaredFields[i].get(t))
                            .append("'");
                }

                if (i != field.length - 1) {
                    fieldNames.append(", ");
                    fieldValues.append(", ");
                }
            }

            statement.executeUpdate(String.format(SQL_UPDATE, tableName, fieldNames, id));
            connection.commit();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void insert(Object t) {
        tableName = t.getClass().getAnnotation(MyTable.class).name();
        StringBuilder fieldNames = new StringBuilder("");
        StringBuilder fieldValues = new StringBuilder("");
        try (//Connection con = JDBCConnection.getConnect();
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            declaredFields = t.getClass().getDeclaredFields();
            typeFields = new Class[declaredFields.length];
            int p = 0;
            for (Field f : declaredFields) {
                f.setAccessible(true);
                typeFields[p++] = f.getType();
            }

            String[] field = Reflection.getFieldName(t);
            for (int i = 0; i < field.length; i++) {
                String name = field[i];
                if (typeFields[i] == Integer.class) {
                    fieldValues.append(declaredFields[i].get(t));
                } else if (typeFields[i] == String.class) {
                    fieldValues.append('"').append(declaredFields[i].get(t)).append('"');
                }

                fieldNames.append(name);

                if (i != field.length - 1) {
                    fieldNames.append(", ");
                    fieldValues.append(", ");
                }
            }
            //System.out.println(String.format(SQL_INSERT, tableName, fieldNames, fieldValues));

            statement.executeUpdate(String.format(SQL_INSERT, tableName, fieldNames, fieldValues));
            connection.commit();

        } catch (SQLException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void delete(Object t, Integer id) {
        tableName = t.getClass().getAnnotation(MyTable.class).name();

        try (//Connection connection = JDBCConnection.getConnect();
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            //System.out.println(String.format(SQL_DELETE, tableName, id));
            statement.executeUpdate(String.format(SQL_DELETE, tableName, id));
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}



