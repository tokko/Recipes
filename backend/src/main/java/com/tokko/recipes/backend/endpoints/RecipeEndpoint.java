package com.tokko.recipes.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;
import com.google.inject.Inject;
import com.googlecode.objectify.ObjectifyService;
import com.tokko.recipes.backend.entities.Recipe;
import com.tokko.recipes.backend.registration.MessageSender;
import com.tokko.recipes.backend.services.RecipeService;
import com.tokko.recipes.backend.util.Constants;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Api(
        name = "recipeApi",
        version = "v1",
        resource = "recipe",
        clientIds = {Constants.ANDROID_CLIENT_ID, Constants.WEB_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE},
        namespace = @ApiNamespace(
                ownerDomain = "backend.recipes.tokko.com",
                ownerName = "backend.recipes.tokko.com",
                packagePath = ""
        )
)
public class RecipeEndpoint {

    private static final Logger logger = Logger.getLogger(RecipeEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    @Inject
    private RecipeService recipeService;
    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Recipe.class);
    }

    @Inject
    private MessageSender messageSender;

    public RecipeEndpoint(){
    }

    @ApiMethod(
            name = "get",
            path = "recipe/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Recipe get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Recipe with ID: " + id);
        return recipeService.getRecipe(id);
    }

    /**
     * Inserts a new {@code Recipe}.
     */
    @ApiMethod(
            name = "insert",
            path = "recipe",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Recipe insert(Recipe recipe, User user) {
        logger.info("Created Recipe.");
        return recipeService.insertRecipe(recipe, user.getEmail());
    }

    /**
     * Updates an existing {@code Recipe}.
     *
     * @param id     the ID of the entity to be updated
     * @param recipe the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Recipe}
     */
    @ApiMethod(
            name = "update",
            path = "recipe/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Recipe update(@Named("id") Long id, Recipe recipe) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(recipe).now();
        logger.info("Updated Recipe: " + recipe);
        return ofy().load().entity(recipe).now();
    }

    /**
     * Deletes the specified {@code Recipe}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Recipe}
     */
    @ApiMethod(
            name = "remove",
            path = "recipe/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Recipe.class).id(id).now();
        logger.info("Deleted Recipe with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "recipe",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Recipe> list(User user) {
        List<Recipe> recipeList = recipeService.getRecipesForUser(user.getEmail());
        return CollectionResponse.<Recipe>builder().setItems(recipeList).build();
    }

    /*
    public CollectionResponse<Recipe> list() {
            List<Recipe> list = ofy().load().type(Recipe.class).list();
            return CollectionResponse.<Recipe>builder().setItems(list).build();
        }

        */
    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Recipe.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Recipe with ID: " + id);
        }
    }
}