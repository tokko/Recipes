package com.tokko.recipes.backend.util;

import com.google.android.gcm.server.Sender;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.googlecode.objectify.Objectify;
import com.tokko.recipes.backend.entities.Grocery;
import com.tokko.recipes.backend.entities.Recipe;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.entities.Registration;
import com.tokko.recipes.backend.registration.MessageSender;
import com.tokko.recipes.backend.resourceaccess.OfyService;

public class MyGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Sender.class).toInstance(new Sender(MessageSender.API_KEY));
        bind(Objectify.class).toInstance(OfyService.ofy());

        bind(new TypeLiteral<Class<Recipe>>(){}).toInstance(Recipe.class);
        bind(new TypeLiteral<Class<RecipeUser>>(){}).toInstance(RecipeUser.class);

        bind(new TypeLiteral<Class<Registration>>(){}).toInstance(Registration.class);
        bind(new TypeLiteral<Class<Grocery>>(){}).toInstance(Grocery.class);
    }
}
