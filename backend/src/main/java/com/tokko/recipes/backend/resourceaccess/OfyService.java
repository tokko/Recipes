package com.tokko.recipes.backend.resourceaccess;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.tokko.recipes.backend.entities.Grocery;
import com.tokko.recipes.backend.entities.Ingredient;
import com.tokko.recipes.backend.entities.Recipe;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.entities.Registration;

/**
 * Objectify service wrapper so we can statically register our persistence classes
 * More on Objectify here : https://code.google.com/p/objectify-appengine/
 */
public class OfyService {

    static {
        ObjectifyService.register(Registration.class);
        ObjectifyService.register(Recipe.class);
        ObjectifyService.register(Grocery.class);
        ObjectifyService.register(Ingredient.class);
        ObjectifyService.register(RecipeUser.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
