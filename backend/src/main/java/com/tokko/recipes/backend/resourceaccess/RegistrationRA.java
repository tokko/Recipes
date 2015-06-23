package com.tokko.recipes.backend.resourceaccess;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.entities.Registration;

import java.util.List;

import static com.tokko.recipes.backend.resourceaccess.OfyService.ofy;

public class RegistrationRA {
    Objectify ofy;

    @Inject
    public RegistrationRA(Objectify ofy) {
        this.ofy = ofy;
    }

    public RecipeUser getUser(String email) {
        return ofy.load().type(RecipeUser.class).filter("email", email).first().now();
    }

    public RecipeUser saveUser(RecipeUser recipeUser) {
        Key<RecipeUser> k = ofy.save().entity(recipeUser).now();
        return ofy.load().key(k).now();
    }

    public Registration saveRegistration(Registration registration) {
        Key<Registration> key = ofy.save().entity(registration).now();
        return ofy.load().key(key).now();
    }

    public Registration getRegistration(Registration registration) {
        return ofy.load().entity(registration).now();
    }

    public List<Registration> getRegistrationsForUser(RecipeUser user) {
        return ofy().load().type(Registration.class).ancestor(user).list();
    }

    public void deleteRegistration(Registration record) {
        ofy().delete().entity(record).now();
    }
}
