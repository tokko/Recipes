package com.tokko.recipes.backend.resourceaccess;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.entities.Registration;

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

    public void saveRegistration(Registration registration) {
        ofy.save().entity(registration).now();
    }

    public Registration getRegistration(Registration registration) {
        return ofy.load().entity(registration).now();
    }

}
