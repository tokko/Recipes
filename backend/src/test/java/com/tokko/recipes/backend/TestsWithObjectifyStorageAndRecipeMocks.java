package com.tokko.recipes.backend;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.googlecode.objectify.Objectify;
import com.tokko.recipes.backend.resourceaccess.OfyService;

import org.junit.Before;

public abstract class TestsWithObjectifyStorageAndRecipeMocks extends TestsWithObjectifyStorage {
    @Inject
    protected Mocks mocks;

    @Before
    public void setup(){
        super.setup();
        Injector injector = Guice.createInjector(new MockModule());
        injector.injectMembers(this);
    }

    public class MockModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(Mocks.class);
            bind(Objectify.class).toProvider(new Provider<Objectify>() {
                @Override
                public Objectify get() {
                    return OfyService.ofy();
                }
            });
        }
    }
}
