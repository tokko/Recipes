package com.tokko.recipes.backend.recipes;

import com.tokko.recipes.backend.ingredients.Ingredient;

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
