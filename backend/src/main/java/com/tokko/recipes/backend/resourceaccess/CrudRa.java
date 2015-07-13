package com.tokko.recipes.backend.resourceaccess;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.tokko.recipes.backend.entities.Recipe;
import com.tokko.recipes.backend.entities.RecipeUser;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class CrudRa<TEntity, TAncestor> {

    private Objectify ofy;
    private Class<TEntity> entityClass;
    private Class<TAncestor> ancestorClass;

    @Inject
    public CrudRa(Objectify ofy, Class<TEntity> entityClass, Class<TAncestor> ancestorClass) {
        this.ofy = ofy;
        this.entityClass = entityClass;
        this.ancestorClass = ancestorClass;
    }

    public TEntity get(long id, long ancestorId){
        return ofy.load().key(Key.create(Key.create(ancestorClass, ancestorId), entityClass, id)).now();
    }

    public TEntity save(TEntity entity){
        Key<TEntity> now = ofy().save().entity(entity).now();
        return ofy.load().key(now).now();
    }

    public List<TEntity> getForAncestor(TAncestor ancestor) {
        return ofy.load().type(entityClass).ancestor(ancestor).list();
    }

    public void remove(Long id, TAncestor ancestor) {
        ofy.delete().type(entityClass).parent(ancestor).id(id).now();
    }
}
