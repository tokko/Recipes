package com.tokko.recipes.backend.services;

import com.google.api.server.spi.response.NotFoundException;
import com.google.inject.Inject;
import com.tokko.recipes.backend.entities.Recipe;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.registration.MessageSender;
import com.tokko.recipes.backend.resourceaccess.RecipeRa;
import com.tokko.recipes.backend.resourceaccess.RegistrationRA;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class RecipeService {

    private RecipeRa recipeRa;
    private MessageSender messageSender;
    private RegistrationRA registrationRA;

    @Inject
    public RecipeService(RecipeRa recipeRa, MessageSender messageSender, RegistrationRA registrationRA) {
        this.recipeRa = recipeRa;
        this.messageSender = messageSender;
        this.registrationRA = registrationRA;
    }

    public Recipe getRecipe(Long id) throws NotFoundException {
        Recipe recipe = recipeRa.getRecipe(id);
        if (recipe == null) {
            throw new NotFoundException("Could not find Recipe with ID: " + id);
        }
        return recipe;
    }

    public Recipe insertRecipe(Recipe recipe, String email) {
        //RecipeUser user = registrationRA.getUser(email); //TODO: set user parent for recipe
        recipeRa.saveRecipe(recipe, email);
        if(recipe.getId() != null)
            messageSender.sendMessage(recipe, email);
        return recipeRa.getRecipe(recipe.getId());

    }
}
