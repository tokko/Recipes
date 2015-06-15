package com.tokko.recipes.backend;

import com.googlecode.objectify.Objectify;
import com.tokko.recipes.backend.recipes.RecipeUser;
import com.tokko.recipes.backend.registration.Registration;
import com.tokko.recipes.backend.resourceaccess.OfyService;

import org.junit.Before;

public class MessageSenderTests extends TestsWithObjectifyStorage{

    @Before
    public void setup(){
        super.setup();
        String email = "angus@fife.sc";
        String regid1 = "regid1";

        RecipeUser user = new RecipeUser(email);
        ofy.save().entity(user).now();

        Registration reg = new Registration();
        reg.setParent(user);
        reg.setRegId(regid1);
    }
}