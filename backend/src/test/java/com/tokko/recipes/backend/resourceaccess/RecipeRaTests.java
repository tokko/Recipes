package com.tokko.recipes.backend.resourceaccess;

import com.tokko.recipes.backend.entities.Recipe;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.util.TestsWithObjectifyStorage;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    @Test
    public void getRecipesForUser_UserDoesNotExists_ListIsEmpty(){
        List<Recipe> recipes = recipeRa.getRecipesForUser(new RecipeUser("56436", 1L));
        assertNotNull(recipes);
        assertEquals(0, recipes.size());
    }
}
