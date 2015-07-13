package com.tokko.recipes.backend.resourceaccess;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

import java.util.List;

public class CrudRaAncestor<TEntity, TAncestor>  extends CrudRa<TEntity>{

    protected Class<TAncestor> ancestorClass;

    public CrudRaAncestor(Objectify ofy, Class<TEntity> tEntityClass, Class<TAncestor> ancestorClass) {
        super(ofy, tEntityClass);
        this.ancestorClass = ancestorClass;
    }

    public List<TEntity> getForAncestor(TAncestor ancestor) {
        return ofy.load().type(entityClass).ancestor(ancestor).list();
    }

    public TEntity get(long entityId, long ancestorId){
        Key<TEntity> key = Key.create(Key.create(ancestorClass, ancestorId), entityClass, entityId);
        return get(key);
    }

    public void remove(long entityId, long ancestorId){
        Key<TEntity> key = Key.create(Key.create(ancestorClass, ancestorId), entityClass, entityId);
        remove(key);
    }
}
