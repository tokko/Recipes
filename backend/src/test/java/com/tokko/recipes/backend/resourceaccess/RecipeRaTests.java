package com.tokko.recipes.backend.resourceaccess;

import com.tokko.recipes.backend.entities.Recipe;
import com.tokko.recipes.backend.util.TestsWithObjectifyStorage;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RecipeRaTests extends TestsWithObjectifyStorage {


    private RecipeRa recipeRa;

    @Before
    public void setup(){
        super.setup();
        recipeRa = new RecipeRa(ofy);
    }

    @Test
    public void saveRecipe_savedRecipeIsGettable(){
        String title = "title";
        Recipe r = new Recipe(title);
        recipeRa.saveRecipe(r);
        Recipe r1 = recipeRa.getRecipe(r.getId());
        assertEquals(r, r1);
    }
}
