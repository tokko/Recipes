package com.tokko.recipes.backend.services;

import com.google.api.server.spi.response.NotFoundException;
import com.tokko.recipes.backend.resourceaccess.RecipeRa;
import com.tokko.recipes.backend.util.TestsWithObjectifyStorage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceTests extends TestsWithObjectifyStorage {
    @Mock
    private RecipeRa recipeRa;
    private RecipeService recipeService;

    @Before
    public void setup(){
        super.setup();
        recipeService = new RecipeService(recipeRa);
    }

    @Test(expected=NotFoundException.class)
    public void getRecipe_recipeDoesNotExists_ThrowsNotFoundException() throws com.google.api.server.spi.response.NotFoundException {
        given(recipeRa.getRecipe(anyLong())).willReturn(null);
        recipeService.getRecipe(0L);
    }
}
