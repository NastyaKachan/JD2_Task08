package by.academy.DAO;

import java.util.List;

public interface DAO {

    List<List<Object>> select();

//    Object selectById(Integer id);

    void update(Object obj, Integer id);

    void insert(Object obj);

    void delete(Object obj, Integer id);
}
