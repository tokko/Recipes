package com.tokko.recipes.backend.services;

import com.google.inject.Inject;
import com.tokko.recipes.backend.entities.Grocery;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.registration.MessageSender;
import com.tokko.recipes.backend.resourceaccess.CrudRaAncestor;
import com.tokko.recipes.backend.resourceaccess.RegistrationRA;
import com.tokko.recipes.backend.util.TestsWithObjectifyStorage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.tokko.recipes.backend.util.InjectStore.inject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class SimpleServiceTests extends TestsWithObjectifyStorage {

    private SimpleService<Grocery> service;
    @Mock
    private RegistrationRA registrationRA;
    @Mock
    private CrudRaAncestor<Grocery, RecipeUser> groceryRa;
    @Mock
    private MessageSender messageSender;

    private String email = "email";

    @Before
    public void setup() {
        super.setup();
        service = new SimpleService<>(groceryRa, registrationRA, messageSender);
        given(registrationRA.getUser(email)).willReturn(new RecipeUser(email, 1L));
    }

    @Test
    public void getGrocery(){
        Grocery banana = new Grocery("banana");
        banana.setId(1L);
        given(groceryRa.get(eq(1l), eq(1l))).willReturn(banana);

        Grocery received = service.get(banana.getId(), email);
        assertEquals(banana.getTitle(), received.getTitle());

        assertEquals(banana.getId(), received.getId());
    }
}
