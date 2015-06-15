package com.tokko.recipes.backend;

import org.junit.Before;

public abstract class TestsWithObjectifyStorageAndRecipeMocks extends TestsWithObjectifyStorage {
    protected Mocks mocks;

    @Before
    public void setup(){
        super.setup();
        mocks = new Mocks(ofy);
    }
}
