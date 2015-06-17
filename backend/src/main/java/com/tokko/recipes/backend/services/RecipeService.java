package com.tokko.recipes.backend.services;

import com.google.api.server.spi.response.NotFoundException;
import com.google.inject.Inject;
import com.tokko.recipes.backend.entities.Recipe;
import com.tokko.recipes.backend.resourceaccess.RecipeRa;

public class RecipeService {

    private RecipeRa recipeRa;

    @Inject
    public RecipeService(RecipeRa recipeRa) {
        this.recipeRa = recipeRa;
    }

    public Recipe getRecipe(Long id) throws NotFoundException {
        Recipe recipe = recipeRa.getRecipe(id);
        if (recipe == null) {
            throw new NotFoundException("Could not find Recipe with ID: " + id);
        }
        return recipe;
    }
}
