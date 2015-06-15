package com.tokko.recipes.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.tokko.recipes.backend.entities.Ingredient;

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
        name = "ingredientApi",
        version = "v1",
        resource = "ingredient",
        namespace = @ApiNamespace(
                ownerDomain = "ingredients.backend.recipes.tokko.com",
                ownerName = "ingredients.backend.recipes.tokko.com",
                packagePath = ""
        )
)
public class IngredientEndpoint {

    private static final Logger logger = Logger.getLogger(IngredientEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Ingredient.class);
    }

    /**
     * Returns the {@link Ingredient} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Ingredient} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "ingredient/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Ingredient get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Ingredient with ID: " + id);
        Ingredient ingredient = ofy().load().type(Ingredient.class).id(id).now();
        if (ingredient == null) {
            throw new NotFoundException("Could not find Ingredient with ID: " + id);
        }
        return ingredient;
    }

    /**
     * Inserts a new {@code Ingredient}.
     */
    @ApiMethod(
            name = "insert",
            path = "ingredient",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Ingredient insert(Ingredient ingredient) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that ingredient.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(ingredient).now();
        logger.info("Created Ingredient with ID: " + ingredient.getId());

        return ofy().load().entity(ingredient).now();
    }

    /**
     * Updates an existing {@code Ingredient}.
     *
     * @param id         the ID of the entity to be updated
     * @param ingredient the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Ingredient}
     */
    @ApiMethod(
            name = "update",
            path = "ingredient/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Ingredient update(@Named("id") Long id, Ingredient ingredient) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(ingredient).now();
        logger.info("Updated Ingredient: " + ingredient);
        return ofy().load().entity(ingredient).now();
    }

    /**
     * Deletes the specified {@code Ingredient}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Ingredient}
     */
    @ApiMethod(
            name = "remove",
            path = "ingredient/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Ingredient.class).id(id).now();
        logger.info("Deleted Ingredient with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "ingredient",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Ingredient> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Ingredient> query = ofy().load().type(Ingredient.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Ingredient> queryIterator = query.iterator();
        List<Ingredient> ingredientList = new ArrayList<Ingredient>(limit);
        while (queryIterator.hasNext()) {
            ingredientList.add(queryIterator.next());
        }
        return CollectionResponse.<Ingredient>builder().setItems(ingredientList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Ingredient.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Ingredient with ID: " + id);
        }
    }
}