package com.tokko.recipes.backend.services;

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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

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
    private RecipeUser user = new RecipeUser(email, 1L);


    @Before
    public void setup() {
        super.setup();
        service = new SimpleService<>(groceryRa, registrationRA, messageSender);
        given(registrationRA.getUser(email)).willReturn(user);
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

    @Test(expected=IllegalArgumentException.class)
    public void remove_DoesNotExist_throwsException(){
        given(groceryRa.get(anyLong(), anyLong())).willReturn(null);
        service.remove(1L, email);
    }

    @Test
    public void remove_Exists_RemoveIsCalled(){
        given(groceryRa.get(1l, 1L)).willReturn(new Grocery(1L, "banana"));
        service.remove(1L, email);
        verify(groceryRa).remove(eq(1L), eq(1L));
    }

    @Test
    public void getForAncestor_AllEntitiesForAncestorReturned(){
        List<Grocery> groceries = Arrays.asList(new Grocery(1L, "1"), new Grocery(1L, "2"));
        given(groceryRa.getForAncestor(user)).willReturn(groceries);
        List<Grocery> list = service.getForAncestor(email);
        assertNotNull(list);
        assertEquals(groceries.size(), list.size());
        assertTrue(list.containsAll(groceries));
    }

    @Test
    public void getForAncestor_OnlyAncestorsReturned(){
        RecipeUser user1 = new RecipeUser("otheremail", 2L);
        Grocery g = new Grocery(1L, "1");
        g.setAncestor(user);
        Grocery g1 = new Grocery(2L, "2");
        g1.setAncestor(user1);
        given(registrationRA.getUser(user1.getEmail())).willReturn(user1);
        given(groceryRa.getForAncestor(user)).willReturn(Collections.singletonList(g));
        given(groceryRa.getForAncestor(user1)).willReturn(Collections.singletonList(g1));

        List<Grocery> list = service.getForAncestor(email);

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(g, list.get(0));
    }

    @Test
    public void insert_newEntity_isInserted() {
        String title = "banana";
        Grocery g = new Grocery(title);
        Grocery spy = spy(g);

        given(groceryRa.save(spy)).willAnswer(new Answer<Grocery>(){
            @Override
            public Grocery answer(InvocationOnMock invocation) throws Throwable{
                spy.setId(1L);
                return spy;
            }

        });
        Grocery newGrocery = service.insert(spy, email);

        verify(groceryRa).save(spy);
        verify(spy).setAncestor(user);
        assertEquals(1L, spy.getId().longValue());
    }

    @Test
    public void insert_updateEntity_isUpdated(){
        String title = "banana";
        Grocery g = new Grocery(1L, title);
        String newTitle = "banana1";
        Grocery newGrocery = new Grocery(1L, newTitle);
        newGrocery.setAncestor(user);
        given(groceryRa.get(1L, user.getId())).willReturn(g);
        given(groceryRa.save(newGrocery)).willReturn(newGrocery);
        Grocery insert = service.insert(newGrocery, email);

        verify(groceryRa).save(newGrocery);
        assertEquals(insert.getTitle(), newTitle);
    }
}
