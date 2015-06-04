package com.tokko.recipes.backend.util;

import com.googlecode.objectify.Objectify;
import com.tokko.recipes.backend.ingredients.Grocery;
import com.tokko.recipes.backend.ingredients.Ingredient;
import com.tokko.recipes.backend.quantities.Quantity;
import com.tokko.recipes.backend.recipes.Recipe;
import com.tokko.recipes.backend.recipes.RecipeList;
import com.tokko.recipes.backend.units.Grams;

import java.util.ArrayList;
import java.util.List;

public class Mocks {

    private final RecipeList recipes;
    private final List<Grocery> groceries;
    private final List<Ingredient> ingredients;

    public Mocks(Objectify ofy) {
        this(ofy, 1);
    }

    public Mocks(Objectify ofy, int repeats) {
        recipes = new RecipeList();
        recipes.add(new Recipe());
        recipes.add(new Recipe());
        groceries = new ArrayList<>();
        ingredients = new ArrayList<>();
        String groceryTitlePrefix = "GROCERY";
        for (long i = 1; i <= 20; i++) {
            Grocery grocery = new Grocery();
            grocery.setTitle(groceryTitlePrefix + i);
            grocery.setId(i);
            ofy.save().entities(grocery).now();
            Ingredient ing = new Ingredient(grocery, new Quantity(1, new Grams()));
            ofy.save().entities(ing).now();
            ingredients.add(ing);
            for (int j = 0; j < repeats; j++) {
                recipes.get(0).addIngredient(ing);
                recipes.get(1).addIngredient(ing);
            }
            groceries.add(grocery);
        }
        ofy.save().entities(recipes).now();
    }

    public RecipeList getRecipes() {
        return recipes;
    }

    public List<Grocery> getGroceries() {
        return groceries;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
