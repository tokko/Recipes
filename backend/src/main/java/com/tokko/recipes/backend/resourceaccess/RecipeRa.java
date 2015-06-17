package com.tokko.recipes.backend.resourceaccess;

import com.google.inject.Inject;
import com.googlecode.objectify.Objectify;
import com.tokko.recipes.backend.entities.Recipe;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class RecipeRa {

    private Objectify ofy;

    @Inject
    public RecipeRa(Objectify ofy) {
        this.ofy = ofy;
    }

    public Recipe getRecipe(Long id) {
        return ofy.load().type(Recipe.class).id(id).now();
    }

    public void saveRecipe(Recipe recipe, String email) {
        ofy().save().entity(recipe).now();
    }
}
