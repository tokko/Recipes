package com.tokko.recipes.backend.util;

import com.google.inject.Guice;

public class InjectStore {

    public static final void inject(Object o) {
        Guice.createInjector(new MyGuiceModule()).injectMembers(o);
    }
}
