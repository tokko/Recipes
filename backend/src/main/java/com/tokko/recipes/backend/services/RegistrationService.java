package com.tokko.recipes.backend.services;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.entities.Registration;
import com.tokko.recipes.backend.resourceaccess.RegistrationRA;

import java.util.logging.Logger;

public class RegistrationService {
    private static final Logger logger = Logger.getLogger(RegistrationService.class.getName());

    RegistrationRA registrationRA;

    @Inject
    public RegistrationService(RegistrationRA registrationRA) {
        this.registrationRA = registrationRA;
    }

    public Registration register(Registration registration, String email) {
        RecipeUser recipeUser = registrationRA.getUser(email);

        if (recipeUser == null) {
            recipeUser = new RecipeUser(email);
            registrationRA.saveUser(recipeUser);
        }
        registration.setParent(recipeUser);
        registrationRA.saveRegistration(registration);
        logger.info("Created Registration.");

        return registrationRA.getRegistration(registration);
    }
}
