package com.tokko.recipes.backend.resourceaccess;

import com.google.inject.Inject;
import com.googlecode.objectify.Objectify;
import com.tokko.recipes.backend.entities.Recipe;

public class RecipeRa {

    private Objectify ofy;

    @Inject
    public RecipeRa(Objectify ofy) {
        this.ofy = ofy;
    }

    public Recipe getRecipe(Long id) {
        return ofy.load().type(Recipe.class).id(id).now();
    }
}
