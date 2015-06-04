package com.tokko.recipes.backend.resourceaccess;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.tokko.recipes.backend.ingredients.Grocery;
import com.tokko.recipes.backend.ingredients.Ingredient;
import com.tokko.recipes.backend.recipes.Recipe;
import com.tokko.recipes.backend.registration.RegistrationRecord;

/**
 * Objectify service wrapper so we can statically register our persistence classes
 * More on Objectify here : https://code.google.com/p/objectify-appengine/
 */
public class OfyService {

    static {
        ObjectifyService.register(RegistrationRecord.class);
        ObjectifyService.register(Recipe.class);
        ObjectifyService.register(Grocery.class);
        ObjectifyService.register(Ingredient.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
