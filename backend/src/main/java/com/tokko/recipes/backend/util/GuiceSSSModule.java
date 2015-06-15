package com.tokko.recipes.backend.util;

import com.google.api.server.spi.guice.GuiceSystemServiceServletModule;
import com.tokko.recipes.backend.endpoints.RecipeEndpoint;
import com.tokko.recipes.backend.entities.Registration;

import java.util.HashSet;
import java.util.Set;

public class GuiceSSSModule extends GuiceSystemServiceServletModule {

    protected void configureServlets() {

        Set<Class<?>> serviceClasses = new HashSet<Class<?>>();
        serviceClasses.add(RecipeEndpoint.class);
        serviceClasses.add(Registration.class);
        this.serveGuiceSystemServiceServlet("/_ah/spi/*", serviceClasses);
    }
}

