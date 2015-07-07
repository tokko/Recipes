package com.tokko.recipes.backend.resourceaccess;

import com.tokko.recipes.backend.entities.Recipe;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.util.TestsWithObjectifyStorage;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

public class RecipeRaTests extends TestsWithObjectifyStorage {


    RecipeUser user1 = new RecipeUser("user1");
    RecipeUser user2 = new RecipeUser("user2");
    List<Recipe> recipes1 = Arrays.asList(new Recipe("recipe1"), new Recipe("recipe2"));
    List<Recipe> recipes2 = Arrays.asList(new Recipe("recipe2"), new Recipe("recipe2"));
    private RecipeRa recipeRa;
    private RegistrationRA registrationRa;

    @Before
    public void setup(){
        super.setup();
        recipeRa = new RecipeRa(ofy);
        registrationRa = new RegistrationRA(ofy);

        registrationRa.saveUser(user1);
        registrationRa.saveUser(user2);

        recipes1.forEach(r -> r.setUser(user1));
        recipes2.forEach(r -> r.setUser(user2));

        recipes1.forEach(recipeRa::saveRecipe);
        recipes2.forEach(recipeRa::saveRecipe);
    }

    @Override
    public void tearDown() throws IOException {
        super.tearDown();
    }

    @Test
    public void saveRecipe_savedRecipeIsGettable(){
        String title = "title";
        Recipe r = new Recipe(title);
        r.setUser(user1);
        recipeRa.saveRecipe(r);
        Recipe r1 = recipeRa.getRecipe(r.getId(), user1);
        assertEquals(r, r1);
    }

    @Test
    public void getRecipesForUser_UserDoesNotExists_ListIsEmpty(){
        List<Recipe> recipes = recipeRa.getRecipesForUser(new RecipeUser("56436", 900L));
        assertNotNull(recipes);
        assertEquals(0, recipes.size());
    }

    @Test
    public void getRecipesForUser_OnlyUsersRecipesAreFetched() {
        List<Recipe> recipes = recipeRa.getRecipesForUser(user1);
        assertEquals(recipes1.size(), recipes.size());
        recipes.forEach(r -> assertTrue(recipes1.contains(r)));

    }

    @Test
    public void removeRecipeFromUser_RecipeIsRemoved() {
        recipeRa.removeRecipe(recipes1.get(0).getId(), user1);

        List<Recipe> recipes = recipeRa.getRecipesForUser(user1);
        assertEquals(1, recipes.size());
        assertEquals(recipes1.get(1).getId(), recipes.get(0).getId());
    }

    @Test
    public void updateRecipe_RecipeIsUpdated() {
        Recipe recipe = recipes1.get(0);
        String title = "faaaaack";
        recipe.setTitle(title);
        recipeRa.saveRecipe(recipe);
        Recipe recipe1 = recipeRa.getRecipe(recipe.getId(), user1);
        assertEquals(recipe.getId(), recipe1.getId());
        assertEquals(recipe.getDescription(), recipe1.getDescription());
        assertEquals(title, recipe1.getTitle());
    }
}
