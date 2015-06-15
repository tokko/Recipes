package com.tokko.recipes.backend;

import com.tokko.recipes.backend.entities.Ingredient;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RecipeListTests extends TestsWithObjectifyStorageAndRecipeMocks {

    @Test
    public void getIngredients_ReturnsIngredients() {
        List<Ingredient> ings = mocks.getRecipes().getIngredients();
        Assert.assertEquals(mocks.getGroceries().size() * 2, ings.size());
        for (Ingredient ing : ings) {
            Assert.assertTrue(mocks.getGroceries().contains(ing.getGrocery()));
        }
    }

}
