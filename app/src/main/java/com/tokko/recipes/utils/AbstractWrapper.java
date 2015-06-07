package com.tokko.recipes.utils;


import java.lang.reflect.Field;

public abstract class AbstractWrapper<T> {

    public AbstractWrapper(T entity) {
        populate(this, entity);
    }

    public final T getEntity() {
        try {
            @SuppressWarnings("unchecked") T entity = (T) getClazz().newInstance();
            populate(entity, this);
            return entity;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract Long getId();

    protected abstract Class<?> getClazz(); //TODO: think about this, is this really necessary?

    private void populate(Object o, Object o1) {
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

}
