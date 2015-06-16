package com.tokko.recipes.backend.util;

import com.google.android.gcm.server.Sender;
import com.google.inject.AbstractModule;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.tokko.recipes.backend.registration.MessageSender;

public class MyGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Sender.class).toInstance(new Sender(MessageSender.API_KEY));
        bind(Objectify.class).toInstance(ObjectifyService.ofy());
    }
}
