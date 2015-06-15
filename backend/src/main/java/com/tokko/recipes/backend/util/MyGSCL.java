package com.tokko.recipes.backend.util;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class MyGSCL extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new GuiceSSSModule(), new MyGuiceModule());
    }
}

