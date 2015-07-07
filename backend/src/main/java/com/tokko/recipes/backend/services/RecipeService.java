package com.tokko.recipes.backend.services;

import com.google.api.server.spi.response.NotFoundException;
import com.google.inject.Inject;
import com.tokko.recipes.backend.entities.Recipe;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.registration.MessageSender;
import com.tokko.recipes.backend.resourceaccess.RecipeRa;
import com.tokko.recipes.backend.resourceaccess.RegistrationRA;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecipeService {
    private static final Logger logger = Logger.getLogger(RecipeService.class.getName());

    private RecipeRa recipeRa;
    private MessageSender messageSender;
    private RegistrationRA registrationRA;

    @Inject
    public RecipeService(RecipeRa recipeRa, MessageSender messageSender, RegistrationRA registrationRA) {
        this.recipeRa = recipeRa;
        this.messageSender = messageSender;
        this.registrationRA = registrationRA;
    }

    public Recipe getRecipe(Long id, String email) throws NotFoundException {
        RecipeUser user = registrationRA.getUser(email);
        Recipe recipe = recipeRa.getRecipe(id, user);
        if (recipe == null) {
            throw new NotFoundException("Could not find Recipe with ID: " + id);
        }
        return recipe;
    }

    public Recipe insertRecipe(Recipe recipe, String email) {
        RecipeUser user = registrationRA.getUser(email);
        if (recipe.getId() == null) {
            logger.log(Level.INFO, "Updating recipe: " + recipe.getTitle());
            recipe.setUser(user);
        } else {
            logger.log(Level.INFO, "Inserting recipe: " + recipe.getTitle());
            Recipe r = recipeRa.getRecipe(recipe.getId(), user);
            r.populate(recipe);
            recipe = r;
        }
        recipe = recipeRa.saveRecipe(recipe);
        if(recipe.getId() != null)
            messageSender.sendMessage(recipe, email);
        return recipe;

    }

    public List<Recipe> getRecipesForUser(String email) {
        RecipeUser user = registrationRA.getUser(email);
        return recipeRa.getRecipesForUser(user);
    }

    public void removeRecipe(Long id, String email) {
        RecipeUser recipeUser = registrationRA.getUser(email);
        recipeRa.removeRecipe(id, recipeUser);
        messageSender.sendMessage(null, email);
    }

    public Recipe updateRecipe(Long id, Recipe recipe, String email) throws NotFoundException {
        Recipe existing = getRecipe(id, email);
        if (existing == null) throw new NotFoundException("not found");
        insertRecipe(recipe, email);
        return recipe;
    }
}
