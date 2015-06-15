package com.tokko.recipes.backend.resourceaccess;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.googlecode.objectify.Objectify;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.util.TestsWithObjectifyStorage;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegistrationRATests extends TestsWithObjectifyStorage {

    private static final String email = "angus@fife.sc";
    private RegistrationRA registrationRa;

    @Before
    public void setup() {
        super.setup();
        registrationRa = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Objectify.class).toInstance(ofy);
            }
        }).getInstance(RegistrationRA.class);
    }

    @Test
    public void saveUser_UserIsSaved() {
        RecipeUser user = new RecipeUser(email);
        registrationRa.saveUser(user);
        RecipeUser user1 = registrationRa.getUser(email);
        Assert.assertEquals(user, user1);
    }
}
