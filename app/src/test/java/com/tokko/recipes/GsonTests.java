package com.tokko.recipes;

import com.google.gson.Gson;
import com.tokko.recipes.backend.recipeApi.model.Recipe;

import junit.framework.Assert;

import org.junit.Test;

public class GsonTests {

    @Test
    public void gsonTest() {
        Gson gson = new Gson();
        Recipe recipe = new Recipe();
        recipe.setTitle("test");
        recipe.setId((long) 1234);

        String data = gson.toJson(recipe);

        Recipe r = gson.fromJson(data, Recipe.class);

        Assert.assertNotNull(r);
        Assert.assertEquals(recipe.getTitle(), r.getTitle());
        Assert.assertEquals(recipe.getId(), r.getId());

    }
}
