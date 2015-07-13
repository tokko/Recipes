package com.tokko.recipes.backend.services;

import com.google.inject.Inject;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.registration.MessageSender;
import com.tokko.recipes.backend.resourceaccess.CrudRaAncestor;
import com.tokko.recipes.backend.resourceaccess.RegistrationRA;

import java.util.List;

import static com.tokko.Util.populate;

public class SimpleService<TEntity extends RecipeEntity<Long, RecipeUser>> {
    protected CrudRaAncestor<TEntity, RecipeUser> crudRa;
    protected RegistrationRA registrationRA;
    protected MessageSender messageSender;
    @Inject
    public SimpleService(CrudRaAncestor<TEntity, RecipeUser> crudRa, RegistrationRA registrationRA, MessageSender messageSender) {
        this.crudRa = crudRa;
        this.registrationRA = registrationRA;
        this.messageSender = messageSender;
    }

    public TEntity get(long id, String email){
        RecipeUser user = registrationRA.getUser(email);
        return crudRa.get(id, user.getId());
    }

    public void remove(long id, String email){
        crudRa.remove(id, registrationRA.getUser(email).getId());
    }

    public List<TEntity> getForAncestor(String email){
        return crudRa.getForAncestor(registrationRA.getUser(email));
    }

    public TEntity insert(TEntity entity, String email){
        RecipeUser user = registrationRA.getUser(email);
        if(entity.getId() == null){
            entity.setAncestor(user);
        }
        else{
            TEntity e = crudRa.get(entity.getId(), user.getId());
            populate(e, entity);
            entity = e;
        }
        if(entity.getId() != null)
            messageSender.sendMessage(entity, email);
        return crudRa.save(entity);
    }
}
