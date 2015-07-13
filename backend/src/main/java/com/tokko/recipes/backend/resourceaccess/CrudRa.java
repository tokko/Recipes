package com.tokko.recipes.backend.resourceaccess;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class CrudRa<TEntity> {

    protected Objectify ofy;
    protected Class<TEntity> entityClass;

    @Inject
    public CrudRa(Objectify ofy, Class<TEntity> entityClass) {
        this.ofy = ofy;
        this.entityClass = entityClass;
    }

    public TEntity get(long id){
        return get(Key.create(entityClass, id));
    }

    protected TEntity get(Key<TEntity> key){
        return ofy.load().key(key).now();
    }

    public TEntity save(TEntity entity){
        Key<TEntity> now = ofy().save().entity(entity).now();
        return ofy.load().key(now).now();
    }

    protected void remove(Key<TEntity> key){
        ofy.delete().key(key).now();
    }
    public void remove(Long id) {
        remove(Key.create(entityClass, id));
    }
}
