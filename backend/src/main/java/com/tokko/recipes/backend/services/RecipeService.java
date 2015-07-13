package com.tokko.recipes.backend.services;

import com.google.api.server.spi.response.NotFoundException;
import com.google.inject.Inject;
import com.tokko.recipes.backend.entities.Recipe;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.registration.MessageSender;
import com.tokko.recipes.backend.resourceaccess.CrudRaAncestor;
import com.tokko.recipes.backend.resourceaccess.RegistrationRA;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecipeService {
    private static final Logger logger = Logger.getLogger(RecipeService.class.getName());

    private CrudRaAncestor<Recipe, RecipeUser> recipeRa;
    private MessageSender messageSender;
    private RegistrationRA registrationRA;

    @Inject
    public RecipeService(CrudRaAncestor<Recipe, RecipeUser> recipeRa, MessageSender messageSender, RegistrationRA registrationRA) {
        this.recipeRa = recipeRa;
        this.messageSender = messageSender;
        this.registrationRA = registrationRA;
    }

    public Recipe getRecipe(Long id, String email) throws NotFoundException {
        RecipeUser user = registrationRA.getUser(email);
        Recipe recipe = recipeRa.get(id, user.getId());
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
            Recipe r = recipeRa.get(recipe.getId(), user.getId());
            r.populate(recipe);
            recipe = r;
        }
        recipe = recipeRa.save(recipe);
        if(recipe.getId() != null)
            messageSender.sendMessage(recipe, email);
        return recipe;

    }

    public List<Recipe> getRecipesForUser(String email) {
        RecipeUser user = registrationRA.getUser(email);
        return recipeRa.getForAncestor(user);
    }

    public void removeRecipe(Long id, String email) {
        RecipeUser recipeUser = registrationRA.getUser(email);
        recipeRa.remove(id, recipeUser.getId());
        messageSender.sendMessage(null, email);
    }

    public Recipe updateRecipe(Long id, Recipe recipe, String email) throws NotFoundException {
        Recipe existing = getRecipe(id, email);
        if (existing == null) throw new NotFoundException("not found");
        insertRecipe(recipe, email);
        return recipe;
    }
}
