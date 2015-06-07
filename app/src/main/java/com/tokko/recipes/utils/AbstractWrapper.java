package com.tokko.recipes.utils;


import java.lang.reflect.Field;

public abstract class AbstractWrapper<T> {

    public AbstractWrapper(T entity) {
        Field[] entityFields = entity.getClass().getDeclaredFields();
        Field[] thisFields = getClass().getDeclaredFields();
        try {

            for (Field field : thisFields) {
                field.setAccessible(true);
                for (Field field1 : entityFields) {
                    field1.setAccessible(true);
                    if (field.getType().equals(field1.getType()) && field.getName().equals(field1.getName()))
                        field.set(this, field1.get(entity));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
