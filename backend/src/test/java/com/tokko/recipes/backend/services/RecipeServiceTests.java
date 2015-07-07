package com.tokko.recipes.backend.services;

import com.google.api.server.spi.response.NotFoundException;
import com.tokko.recipes.backend.entities.Recipe;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.registration.MessageSender;
import com.tokko.recipes.backend.resourceaccess.RecipeRa;
import com.tokko.recipes.backend.resourceaccess.RegistrationRA;
import com.tokko.recipes.backend.util.TestsWithObjectifyStorage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceTests extends TestsWithObjectifyStorage {
    @Mock
    private RecipeRa recipeRa;
    @Mock private MessageSender messageSender;
    @Mock private RegistrationRA registrationRa;
    private RecipeService recipeService;

    @Before
    public void setup(){
        super.setup();
        recipeService = new RecipeService(recipeRa, messageSender, registrationRa);
        given(registrationRa.getUser(anyString())).willReturn(new RecipeUser("email"));
    }

    @Test(expected=NotFoundException.class)
    public void getRecipe_recipeDoesNotExists_ThrowsNotFoundException() throws com.google.api.server.spi.response.NotFoundException {
        given(recipeRa.getRecipe(anyLong(), any(RecipeUser.class))).willReturn(null);
        recipeService.getRecipe(0L, "email");
    }

    @Test
    public void getRecipe_RecipeExists_ReturnsExists(){
        Recipe recipe = new Recipe("title");
        recipe.setId(0L);
        given(recipeRa.getRecipe(eq(0L), any(RecipeUser.class))).willReturn(recipe);
        try {
            Recipe getRecipe = recipeService.getRecipe(0L, "email");
            assertEquals(recipe, getRecipe);
        } catch (NotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void insertRecipe_insertSucceeds_MessageIsSent(){
        given(registrationRa.getUser(anyString())).willReturn(new RecipeUser("email", 1L));
        Recipe recipe = new Recipe("title");
        Recipe recipe1 = new Recipe(recipe.getTitle()).setId(1L);
        given(recipeRa.saveRecipe(any(Recipe.class))).willReturn(recipe1);
        String email = "email";
        recipeService.insertRecipe(recipe, email);

        verify(messageSender).sendMessage(recipe1, email);
    }
}
