package com.tokko.recipes.backend.resourceaccess;

import com.google.inject.Inject;
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

    public void saveUser(RecipeUser recipeUser) {
        ofy.save().entity(recipeUser).now();
    }

    public void saveRegistration(Registration registration) {
        ofy.save().entity(registration).now();
    }

    public Registration getRegistration(Registration registration) {
        return ofy.load().entity(registration).now();
    }
}
