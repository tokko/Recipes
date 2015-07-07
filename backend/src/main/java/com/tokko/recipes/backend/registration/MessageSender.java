package com.tokko.recipes.backend.registration;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.inject.Inject;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.entities.Registration;
import com.tokko.recipes.backend.resourceaccess.RegistrationRA;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class MessageSender {
    public static final String API_KEY = "AIzaSyAcfYjzlHQaAuroVdB26hczjVkZ0PKqDNc";
    private static final Logger logger = Logger.getLogger(MessageSender.class.getName());
    private RegistrationRA registrationRA;
    private Sender sender;

    @Inject
    public MessageSender(RegistrationRA registrationRA, Sender sender) {
        this.registrationRA = registrationRA;
        this.sender = sender;
    }

    public <T> void sendMessage(T entity, String email) {
        Message msg = new Message.Builder().addData("message", new Gson().toJson(entity)).build();
        RecipeUser recipeUser = registrationRA.getUser(email);

        List<Registration> records = registrationRA.getRegistrationsForUser(recipeUser);
        logger.info("Sending messages to devices for user: " + email);
        try {
            for (Registration record : records) {
                logger.info("sending message: " + entity + " to device " + record.getRegId());
                Result res = sender.send(msg, record.getRegId(), 5);

            /* TODO: look into this
                if(res == null) continue;
                if (res.getCanonicalRegistrationId() == null)
                    registrationRA.deleteRegistration(record);
                else if (!record.getRegId().equals(res.getCanonicalRegistrationId())) {
                    record.setRegId(res.getCanonicalRegistrationId());
                    registrationRA.saveRegistration(record);
                }
                */
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
