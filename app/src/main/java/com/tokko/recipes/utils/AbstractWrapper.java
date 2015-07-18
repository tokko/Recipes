package com.tokko.recipes.utils;


import static com.tokko.Util.populate;

public abstract class AbstractWrapper<T> {

    public AbstractWrapper(T entity) {
        if (entity != null)
            populate(this, entity);
    }

    public final T getEntity() {
        try {
            @SuppressWarnings("unchecked") T entity = (T) getWrappedClass().newInstance();
            populate(entity, this);
            return entity;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract Class<?> getWrappedClass(); //TODO: think about this, is this really necessary?

    public abstract Long getId();

    public abstract void setId(Long id);

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


    public <E extends AbstractWrapper<?>> void populateWith(E t) {
        populate(this, t);
    }
}
