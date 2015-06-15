package com.tokko.recipes.backend.collections;

import com.tokko.recipes.backend.entities.Ingredient;
import com.tokko.recipes.backend.collections.ShoppingList;
import com.tokko.recipes.backend.util.TestsWithObjectifyStorageAndRecipeMocks;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

public class ShoppingListTest extends TestsWithObjectifyStorageAndRecipeMocks {

    @Test
    public void getShoppingList_AggregationSuccessful() {
        ShoppingList shoppingList = new ShoppingList(mocks.getRecipes());
        Assert.assertEquals(mocks.getIngredients().size(), shoppingList.size());
        List<Ingredient> ingredients = shoppingList.getShoppingList();
        Assert.assertEquals(ingredients.size(), new HashSet<>(ingredients).size());
        for (Ingredient ingredient : mocks.getIngredients()) {
            Assert.assertTrue(ingredients.contains(ingredient));
            Assert.assertEquals(ingredient.getGrocery().getTitle(), 2.0, ingredient.getQuantity().getQuantity());
        }
    }
}
