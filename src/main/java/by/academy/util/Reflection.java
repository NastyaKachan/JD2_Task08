package by.academy.util;

import by.academy.Entity.MyColumn;

import java.lang.reflect.Field;

public class Reflection {


    private static Field[] declaredFields;

    public static String[] getFieldName(Object object){
        Field[] declaredFields = object.getClass().getDeclaredFields();
        int count = 0;
        for (Field f : declaredFields) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(MyColumn.class)) {
                count++;
            }
            f.setAccessible(false);
        }

        String[] fieldsName = new String[count];
        int i = 0;
        for (Field f : declaredFields) {
            if (f.isAnnotationPresent(MyColumn.class)) {
                f.setAccessible(true);
                fieldsName[i++] = f.getName();
            }
        }
        return fieldsName;
    }


    public static Field[] getDeclaredFields() {
        return declaredFields;
    }
}


