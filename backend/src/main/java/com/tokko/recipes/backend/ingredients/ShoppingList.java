package com.tokko.recipes.backend.ingredients;

import com.tokko.recipes.backend.recipes.Recipe;
import com.tokko.recipes.backend.recipes.RecipeList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ShoppingList extends ArrayList<Ingredient> {

    public ShoppingList(RecipeList recipes) {
        for (Recipe recipe : recipes) {
            addAll(recipe.iterator());
        }
    }

    public boolean addAll(Iterator<? extends Ingredient> iterator) {
        while (iterator.hasNext()) {
            if (!add(iterator.next())) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Ingredient> c) {
        return addAll(c.iterator());
    }

    @Override
    public boolean add(Ingredient ingredient) {
        for (Ingredient ingredient1 : this) {
            if (ingredient.equals(ingredient1)) {
                ingredient1.getQuantity().add(ingredient.getQuantity());
                return true;
            }
        }
        return super.add(ingredient);
    }

    public List<Ingredient> getShoppingList() {
        List<Ingredient> ingredients = new ArrayList<>();
        for (Ingredient ingredient : this) {
            ingredients.add(ingredient);
        }
        return ingredients;
    }
}
