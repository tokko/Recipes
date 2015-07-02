package com.tokko.recipes.utils;


import java.lang.reflect.Field;

public abstract class AbstractWrapper<T> {

    public AbstractWrapper(T entity) {
        if (entity != null)
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


    public AbstractWrapper<T> cloneEntity() {
        try {
            Class<?> clazz = this.getClass();
            @SuppressWarnings("unchecked") AbstractWrapper<T> ret = (AbstractWrapper<T>) clazz.newInstance();
            populate(ret, this);
            return ret;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

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

    public <E extends AbstractWrapper<?>> void populateWith(E t) {
        populate(this, t);
    }
}
