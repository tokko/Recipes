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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RecipeRaTests extends TestsWithObjectifyStorage {


    RecipeUser user1 = new RecipeUser("user1");
    RecipeUser user2 = new RecipeUser("user2");
    List<Recipe> recipes1 = Arrays.asList(new Recipe("recipe1"), new Recipe("recipe2"));
    List<Recipe> recipes2 = Arrays.asList(new Recipe("recipe2"), new Recipe("recipe2"));
    private CrudRaAncestor<Recipe, RecipeUser> recipeRa;
    private RegistrationRA registrationRa;

    @Before
    public void setup(){
        super.setup();
        recipeRa = new CrudRaAncestor<>(ofy, Recipe.class, RecipeUser.class);
        registrationRa = new RegistrationRA(ofy);

        registrationRa.saveUser(user1);
        registrationRa.saveUser(user2);

        for(Recipe recipe : recipes1){
            recipe.setUser(user1);
        }
        for(Recipe recipe : recipes2){
            recipe.setUser(user2);
        }
        //recipes1.forEach(r -> r.setUser(user1));
        //recipes2.forEach(r -> r.setUser(user2));

        for(Recipe recipe : recipes1){
            recipeRa.save(recipe);
        }
        for(Recipe recipe : recipes2){
            recipeRa.save(recipe);
        }
        /*
        recipes1.forEach(recipeRa::save);
        recipes2.forEach(recipeRa::save);
        */
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
        recipeRa.save(r);
        Recipe r1 = recipeRa.get(r.getId(), user1.getId());
        assertEquals(r, r1);
    }

    @Test
    public void getRecipesForUser_UserDoesNotExists_ListIsEmpty(){
        List<Recipe> recipes = recipeRa.getForAncestor(new RecipeUser("56436", 900L));
        assertNotNull(recipes);
        assertEquals(0, recipes.size());
    }

    @Test
    public void getRecipesForUser_OnlyUsersRecipesAreFetched() {
        List<Recipe> recipes = recipeRa.getForAncestor(user1);
        assertEquals(recipes1.size(), recipes.size());
        //recipes.forEach(r -> assertTrue(recipes1.contains(r)));
        for(Recipe recipe : recipes1){
            assertTrue(recipes1.contains(recipe));
        }

    }

    @Test
    public void removeRecipeFromUser_RecipeIsRemoved() {
        recipeRa.remove(recipes1.get(0).getId(), user1.getId());

        List<Recipe> recipes = recipeRa.getForAncestor(user1);
        assertEquals(1, recipes.size());
        assertEquals(recipes1.get(1).getId(), recipes.get(0).getId());
    }

    @Test
    public void updateRecipe_RecipeIsUpdated() {
        Recipe recipe = recipes1.get(0);
        String title = "faaaaack";
        recipe.setTitle(title);
        recipeRa.save(recipe);
        Recipe recipe1 = recipeRa.get(recipe.getId(), user1.getId());
        assertEquals(recipe.getId(), recipe1.getId());
        assertEquals(recipe.getDescription(), recipe1.getDescription());
        assertEquals(title, recipe1.getTitle());
    }
}
