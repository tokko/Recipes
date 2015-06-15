package com.tokko.recipes.backend.services;

import com.google.appengine.api.users.User;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.entities.Registration;
import com.tokko.recipes.backend.resourceaccess.RegistrationRA;
import com.tokko.recipes.backend.util.TestsWithObjectifyStorage;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RegistrationServiceTests extends TestsWithObjectifyStorage {
    private static final String email = "angus@fife.sc";
    private static final String regid = "gweagaewgaewgae";
    private RegistrationService registrationService;
    private RegistrationRA mockRegistrationRa;

    @Before
    public void setup() {
        super.setup();
        mockRegistrationRa = mock(RegistrationRA.class);
        Injector injector = Guice.createInjector(new TestModule());
        registrationService = injector.getInstance(RegistrationService.class);
        injector.injectMembers(registrationService);
    }

    @Test
    public void registration_UserDoesNotExists_UserIsCreated() {
        when(mockRegistrationRa.getUser(any(String.class))).thenReturn(null);
        when(mockRegistrationRa.saveUser(any(RecipeUser.class))).thenReturn(new RecipeUser(email).setId(1));
        Registration registration = new Registration();
        registration.setRegId(regid);
        registrationService.register(registration, email);

        verify(mockRegistrationRa).saveUser(new RecipeUser(email));
    }

    @Test
    public void registration_UserExists_UserIsNotCreatedAgain() {
        when(mockRegistrationRa.getUser(email)).thenReturn(new RecipeUser(email).setId(1));

        Registration registration = new Registration();
        registration.setRegId(regid);
        registrationService.register(registration, email);

        verify(mockRegistrationRa, never()).saveUser(any(RecipeUser.class));
    }

    @Test
    public void registration_RegIdIsSaved() {
        when(mockRegistrationRa.getUser(email)).thenReturn(new RecipeUser(email).setId(1));

        Registration registration = new Registration();
        registration.setRegId(regid);

        registrationService.register(registration, email);

        verify(mockRegistrationRa).saveRegistration(registration);
    }

    private class TestModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(RegistrationRA.class).toInstance(mockRegistrationRa);
        }
    }
}
