package com.tokko.recipes.backend.util;

import com.google.api.server.spi.guice.GuiceSystemServiceServletModule;
import com.tokko.recipes.backend.endpoints.RecipeEndpoint;
import com.tokko.recipes.backend.endpoints.RegistrationEndpoint;

import java.util.HashSet;
import java.util.Set;

public class GuiceSSSModule extends GuiceSystemServiceServletModule {

    protected void configureServlets() {
        super.configureServlets();
        /*
        Set<Class<?>> serviceClasses = new HashSet<>();
        serviceClasses.add(RecipeEndpoint.class);
        serviceClasses.add(RegistrationEndpoint.class);
        this.serveGuiceSystemServiceServlet("/_ah/api/*", serviceClasses);
        */
    }
}

