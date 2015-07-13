package com.tokko.recipes.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.users.User;
import com.google.inject.Inject;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.tokko.recipes.backend.entities.Grocery;
import com.tokko.recipes.backend.services.SimpleService;
import com.tokko.recipes.backend.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static com.tokko.recipes.backend.util.InjectStore.inject;

@Api(
        name = "groceryApi",
        version = "v1",
        resource = "grocery",
        clientIds = {Constants.ANDROID_CLIENT_ID, Constants.WEB_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE},
        namespace = @ApiNamespace(
                ownerDomain = "entities.backend.recipes.tokko.com",
                ownerName = "entities.backend.recipes.tokko.com",
                packagePath = ""
        )
)
public class GroceryEndpoint {

    private static final Logger logger = Logger.getLogger(GroceryEndpoint.class.getName());
    @Inject
    private SimpleService<Grocery> groceryService;

    public GroceryEndpoint() {
        inject(this);
    }

    @ApiMethod(
            name = "get",
            path = "grocery/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Grocery get(@Named("id") Long id, User user) throws NotFoundException {
        logger.info("Getting Grocery with ID: " + id);
        return groceryService.get(id, user.getEmail());
    }

    @ApiMethod(
            name = "insert",
            path = "grocery",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Grocery insert(Grocery grocery, User user) {
        return groceryService.insert(grocery, user.getEmail());
    }

    @ApiMethod(
            name = "update",
            path = "grocery/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Grocery update(Grocery grocery, User user) throws NotFoundException {
        return groceryService.insert(grocery, user.getEmail());
    }

    @ApiMethod(
            name = "remove",
            path = "grocery/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id, User user) throws NotFoundException {
        groceryService.remove(id, user.getEmail());
    }

    @ApiMethod(
            name = "list",
            path = "grocery",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Grocery> list(User user) {
        List<Grocery> groceryList = groceryService.getForAncestor(user.getEmail());
        return CollectionResponse.<Grocery>builder().setItems(groceryList).build();
    }
}