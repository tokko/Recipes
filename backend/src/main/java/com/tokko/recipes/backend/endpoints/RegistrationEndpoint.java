package com.tokko.recipes.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;
import com.tokko.recipes.backend.recipes.RecipeUser;
import com.tokko.recipes.backend.registration.Registration;
import com.tokko.recipes.backend.util.Constants;

import java.util.logging.Logger;

import javax.inject.Named;

import static com.tokko.recipes.backend.resourceaccess.OfyService.ofy;


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

    @ApiMethod(
            name = "insert",
            path = "registration",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Registration insert(Registration registration, User user) {
        RecipeUser recipeUser = ofy().load().type(RecipeUser.class).filter("email", user.getEmail()).first().now();

        if (recipeUser == null) {
            recipeUser = new RecipeUser(user.getEmail());
            ofy().save().entity(recipeUser).now();
        }
        registration.setParent(recipeUser);
        ofy().save().entity(registration).now();
        logger.info("Created Registration.");

        return ofy().load().entity(registration).now();
    }

    @ApiMethod(
            name = "remove",
            path = "registration/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Registration.class).id(id).now();
        logger.info("Deleted Registration with ID: " + id);
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Registration.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Registration with ID: " + id);
        }
    }
}