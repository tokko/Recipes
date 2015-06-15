package com.tokko.recipes.backend;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Objectify;
import com.tokko.recipes.backend.resourceaccess.OfyService;
import com.tokko.recipes.backend.util.Mocks;

import org.junit.After;
import org.junit.Before;

import java.io.IOException;

public abstract class TestsWithObjectifyStorage {
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(),
                    new LocalMemcacheServiceTestConfig());
    protected Objectify ofy;
    protected Mocks mocks;

    @Before
    public void setup() {
        helper.setUp();
        ofy = OfyService.ofy();
        mocks = new Mocks(ofy);
    }

    @After
    public void tearDown() throws IOException {
        helper.tearDown();
    }
}
