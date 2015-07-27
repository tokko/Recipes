package com.tokko;

import java.lang.reflect.Field;

public class Util {
    public static void populate(Object o, Object o1) {
        Field[] entityFields = o1.getClass().getDeclaredFields();
        Field[] thisFields = o.getClass().getDeclaredFields();
        try {

            for (Field field : thisFields) {
                field.setAccessible(true);
                for (Field field1 : entityFields) {
                    field1.setAccessible(true);
                    if (field.getType().equals(field1.getType()) && field.getName().equals(field1.getName()))
                        field.set(o, field1.get(o1));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static  <T> T cloneTo(T src, Class<T> clx){
        try {
            T tnew = clx.newInstance();
            populate(tnew, src);
            return tnew;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
