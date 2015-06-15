package com.tokko.recipes.backend.resourceaccess;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.objectify.Objectify;
import com.tokko.recipes.backend.entities.Recipe;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.entities.Registration;
import com.tokko.recipes.backend.registration.MessageSender;
import com.tokko.recipes.backend.util.TestsWithObjectifyStorage;


import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.*;

public class RegistrationRATests extends TestsWithObjectifyStorage {

    private static final String email = "angus@fife.sc";
    private RegistrationRA registrationRa;
    private MessageSender messageSender;

    @Before
    public void setup() {
        super.setup();
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Objectify.class).toInstance(ofy);
            }
        });
        registrationRa = injector.getInstance(RegistrationRA.class);
        messageSender = injector.getInstance(MessageSender.class);
    }

    @Test
    public void saveUser_UserIsSaved() {
        RecipeUser user = new RecipeUser(email);
        registrationRa.saveUser(user);
        RecipeUser user1 = registrationRa.getUser(email);
        Assert.assertEquals(user, user1);
    }

    @Test
    public void saveRegistration_GroupedByParent() {
        RecipeUser user = new RecipeUser(email);
        RecipeUser user1 = new RecipeUser("gaega");
        user = registrationRa.saveUser(user);
        user1 = registrationRa.saveUser(user1);

        String regid = "12341";
        String regid1 = "4513512";

        Registration reg = new Registration(regid);
        Registration reg1 = new Registration(regid1);
        Registration reg2 = new Registration("123141251251");

        reg.setParent(user);
        reg1.setParent(user);
        reg2.setParent(user1);

        reg = registrationRa.saveRegistration(reg);
        reg1 = registrationRa.saveRegistration(reg1);
        registrationRa.saveRegistration(reg2);

        List<Registration> registrations = registrationRa.getRegistrationsForUser(user);
        Assert.assertEquals(2, registrations.size());
        Assert.assertThat(registrations, containsInAnyOrder(reg, reg1));
    }

    @Test
    public void deleteRegistration_removesRegistration() {
        RecipeUser user = new RecipeUser(email);
        user = registrationRa.saveUser(user);
        String regid = "12341";
        Registration reg = new Registration(regid);
        reg.setParent(user);
        reg = registrationRa.saveRegistration(reg);
        registrationRa.deleteRegistration(reg);
        Registration postDelete = registrationRa.getRegistration(reg);
        Assert.assertNull(postDelete);
    }
}
