package com.tokko.recipes.backend;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Objectify;
import com.tokko.recipes.backend.ingredients.Ingredient;
import com.tokko.recipes.backend.resourceaccess.OfyService;
import com.tokko.recipes.backend.util.Mocks;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class RecipeListTests {

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(),
                    new LocalMemcacheServiceTestConfig());
    Objectify ofy;
    private Mocks mocks;

    @Before
    public void setup() {
        helper.setUp();
        ofy = OfyService.ofy();
        mocks = new Mocks(ofy);
    }

    @After
    public void tearDown() throws IOException {
        helper.tearDown();
    }

    @Test
    public void getIngredients_ReturnsIngredients() {
        List<Ingredient> ings = mocks.getRecipes().getIngredients();
        Assert.assertEquals(mocks.getGroceries().size() * 2, ings.size());
        for (Ingredient ing : ings) {
            Assert.assertTrue(mocks.getGroceries().contains(ing.getGrocery()));
        }
    }

}
