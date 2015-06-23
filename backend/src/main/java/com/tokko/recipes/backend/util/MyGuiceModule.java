package com.tokko.recipes.backend.util;

import com.google.android.gcm.server.Sender;
import com.google.inject.AbstractModule;
import com.googlecode.objectify.Objectify;
import com.tokko.recipes.backend.registration.MessageSender;
import com.tokko.recipes.backend.resourceaccess.OfyService;

public class MyGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Sender.class).toInstance(new Sender(MessageSender.API_KEY));
        bind(Objectify.class).toInstance(OfyService.ofy());
    }
}
