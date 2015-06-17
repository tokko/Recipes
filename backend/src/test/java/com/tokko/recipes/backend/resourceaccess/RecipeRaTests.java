package com.tokko.recipes.backend.resourceaccess;

import com.tokko.recipes.backend.entities.Recipe;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.util.TestsWithObjectifyStorage;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RecipeRaTests extends TestsWithObjectifyStorage {


    private RecipeRa recipeRa;
    private RegistrationRA registrationRa;

    @Before
    public void setup(){
        super.setup();
        recipeRa = new RecipeRa(ofy);
        registrationRa = new RegistrationRA(ofy);
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

    @Test
    public void getRecipesForUse_OnlyUsersRecipesAreFetched(){
        final RecipeUser user1 = new RecipeUser("user1");
        RecipeUser user2 = new RecipeUser("user2");

        registrationRa.saveUser(user1);
        registrationRa.saveUser(user2);

        List<Recipe> recipes1 = Arrays.asList(new Recipe("recipe1"), new Recipe("recipe2"));
        List<Recipe> recipes2 = Arrays.asList(new Recipe("recipe2"), new Recipe("recipe2"));
        //TODO: finish this test
    }
}
