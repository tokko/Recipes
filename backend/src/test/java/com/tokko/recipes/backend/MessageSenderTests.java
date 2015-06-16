package com.tokko.recipes.backend;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tokko.recipes.backend.entities.Recipe;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.entities.Registration;
import com.tokko.recipes.backend.registration.MessageSender;
import com.tokko.recipes.backend.resourceaccess.RegistrationRA;
import com.tokko.recipes.backend.util.TestsWithObjectifyStorage;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MessageSenderTests extends TestsWithObjectifyStorage {
    private RegistrationRA mockRegistrationRa;
    private MessageSender messageSender;
    private Sender mockSender;

    @Before
    public void setup(){
        super.setup();
        mockRegistrationRa = mock(RegistrationRA.class);
        mockSender = mock(Sender.class);
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(RegistrationRA.class).toInstance(mockRegistrationRa);
                bind(Sender.class).toInstance(mockSender);
            }
        });
        messageSender = injector.getInstance(MessageSender.class);
        injector.injectMembers(messageSender);
    }

    @Test
    public void sendMessage_NoRegisteredDevices_NoMessageSent() {
        when(mockRegistrationRa.getRegistrationsForUser(any(RecipeUser.class))).thenReturn(new LinkedList<Registration>());
        messageSender.sendMessage(new Recipe(), "");
        try {
            verify(mockSender, never()).send(any(Message.class), anyString(), anyInt());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void sendMessage_RegistrationsExist_MessageSentToAllDevices() throws IllegalAccessException, InstantiationException {
        try {
            List<Registration> registrations = Arrays.asList(new Registration("regid1"), new Registration("regid2"));
            when(mockRegistrationRa.getRegistrationsForUser(any(RecipeUser.class))).thenReturn(registrations);
            messageSender.sendMessage(new Recipe(), "");

            verify(mockSender).send(any(Message.class), eq(registrations.get(0).getRegId()), anyInt());
            verify(mockSender).send(any(Message.class), eq(registrations.get(1).getRegId()), anyInt());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
