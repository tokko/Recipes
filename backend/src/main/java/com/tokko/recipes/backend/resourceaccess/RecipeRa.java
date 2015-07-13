package com.tokko.recipes.backend.resourceaccess;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.tokko.recipes.backend.entities.Recipe;
import com.tokko.recipes.backend.entities.RecipeUser;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class RecipeRa extends CrudRa<Recipe, RecipeUser>{

    @Inject
    public RecipeRa(Objectify ofy, Class<Recipe> recipeClass, Class<RecipeUser> recipeUserClass) {
        super(ofy, recipeClass, recipeUserClass);
    }

    public Recipe getRecipe(Long id, RecipeUser user) {
        return super.get(id, user.getId());
    }

    public Recipe saveRecipe(Recipe recipe) {
        return super.save(recipe);
    }

    public List<Recipe> getRecipesForUser(RecipeUser user) {
        return super.getForAncestor(user);
    }

    public void removeRecipe(Long id, RecipeUser recipeUser) {
        super.remove(id, recipeUser);
    }
}
