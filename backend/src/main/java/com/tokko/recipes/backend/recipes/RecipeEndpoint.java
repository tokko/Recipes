package com.tokko.recipes.backend.recipes;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.tokko.recipes.backend.registration.MessagingEndpoint;
import com.tokko.recipes.backend.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "recipeApi",
        version = "v1",
        resource = "recipe",
        clientIds = {Constants.ANDROID_CLIENT_ID},
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

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Recipe.class);
    }

    /**
     * Returns the {@link Recipe} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Recipe} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "recipe/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Recipe get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Recipe with ID: " + id);
        Recipe recipe = ofy().load().type(Recipe.class).id(id).now();
        if (recipe == null) {
            throw new NotFoundException("Could not find Recipe with ID: " + id);
        }
        return recipe;
    }

    /**
     * Inserts a new {@code Recipe}.
     */
    @ApiMethod(
            name = "insert",
            path = "recipe",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Recipe insert(Recipe recipe, User user) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that recipe.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(recipe).now();
        logger.info("Created Recipe.");
        try {
            new MessagingEndpoint().sendMessage("recipe added!", user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ofy().load().entity(recipe).now();
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
    public CollectionResponse<Recipe> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit, User user) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Recipe> query = ofy().load().type(Recipe.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Recipe> queryIterator = query.iterator();
        List<Recipe> ingredientList = new ArrayList<>(limit);
        while (queryIterator.hasNext()) {
            ingredientList.add(queryIterator.next());
        }
        return CollectionResponse.<Recipe>builder().setItems(ingredientList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
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