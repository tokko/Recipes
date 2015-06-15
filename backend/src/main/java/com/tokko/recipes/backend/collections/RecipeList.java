package com.tokko.recipes.backend.collections;

import com.tokko.recipes.backend.entities.Ingredient;
import com.tokko.recipes.backend.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeList extends ArrayList<Recipe> {

    public List<Ingredient> getIngredients() {
        List<Ingredient> groceries = new ArrayList<>();
        for (Recipe recipe : this) {
            for (Ingredient ingredient : recipe) {
                groceries.add(ingredient);
            }
        }
        return groceries;
    }
}