package com.tokko.recipes.backend.util;

import com.google.android.gcm.server.Sender;
import com.google.inject.AbstractModule;
import com.tokko.recipes.backend.registration.MessageSender;

public class MyGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Sender.class).toInstance(new Sender(MessageSender.API_KEY));
    }
}
