package by.academy.util.util;

import by.academy.Entity.Car;
import by.academy.util.Reflection;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReflectionTest {

    @Test
    public void getDeclaredFields() {
        Car carTest = new Car(100, "honda","red",6200);
        String[] expectedFields = {"id","name", "colour","price"};
        String[] actualFields = Reflection.getFieldName(carTest);
        assertArrayEquals(expectedFields,actualFields);
    }

}