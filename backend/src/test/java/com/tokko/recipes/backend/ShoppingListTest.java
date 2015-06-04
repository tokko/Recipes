package com.tokko.recipes.backend;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Objectify;
import com.tokko.recipes.backend.ingredients.Ingredient;
import com.tokko.recipes.backend.ingredients.ShoppingList;
import com.tokko.recipes.backend.resourceaccess.OfyService;
import com.tokko.recipes.backend.util.Mocks;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class ShoppingListTest {

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
