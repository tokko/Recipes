package com.tokko.recipes.backend.registration;

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
import com.tokko.recipes.backend.util.Constants;

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
        name = "registrationApi",
        version = "v1",
        resource = "registration",
        clientIds = {Constants.ANDROID_CLIENT_ID, Constants.WEB_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE},
        namespace = @ApiNamespace(
                ownerDomain = "registration.backend.recipes.tokko.com",
                ownerName = "registration.backend.recipes.tokko.com",
                packagePath = ""
        )
)
public class RegistrationEndpoint {

    private static final Logger logger = Logger.getLogger(RegistrationEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Registration.class);
    }

    /**
     * Returns the {@link Registration} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Registration} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "registration/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Registration get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Registration with ID: " + id);
        Registration registration = ofy().load().type(Registration.class).id(id).now();
        if (registration == null) {
            throw new NotFoundException("Could not find Registration with ID: " + id);
        }
        return registration;
    }

    /**
     * Inserts a new {@code Registration}.
     */
    @ApiMethod(
            name = "insert",
            path = "registration",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Registration insert(Registration registration, User user) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that registration.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        registration.setUserId(user.getEmail());
        ofy().save().entity(registration).now();
        logger.info("Created Registration.");

        return ofy().load().entity(registration).now();
    }

    /**
     * Updates an existing {@code Registration}.
     *
     * @param id           the ID of the entity to be updated
     * @param registration the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Registration}
     */
    @ApiMethod(
            name = "update",
            path = "registration/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Registration update(@Named("id") Long id, Registration registration) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(registration).now();
        logger.info("Updated Registration: " + registration);
        return ofy().load().entity(registration).now();
    }

    /**
     * Deletes the specified {@code Registration}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Registration}
     */
    @ApiMethod(
            name = "remove",
            path = "registration/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Registration.class).id(id).now();
        logger.info("Deleted Registration with ID: " + id);
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
            path = "registration",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Registration> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Registration> query = ofy().load().type(Registration.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Registration> queryIterator = query.iterator();
        List<Registration> registrationList = new ArrayList<Registration>(limit);
        while (queryIterator.hasNext()) {
            registrationList.add(queryIterator.next());
        }
        return CollectionResponse.<Registration>builder().setItems(registrationList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Registration.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Registration with ID: " + id);
        }
    }
}