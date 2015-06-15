package com.tokko.recipes.backend.registration;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.appengine.api.users.User;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.tokko.recipes.backend.entities.Registration;
import com.tokko.recipes.backend.entities.RecipeUser;

import java.io.IOException;
import java.util.List;

import static com.tokko.recipes.backend.resourceaccess.OfyService.ofy;

public class MessageSender {
    private static final String API_KEY = "AIzaSyAcfYjzlHQaAuroVdB26hczjVkZ0PKqDNc";

    public <T> void sendMessage(T entity, User user) {
        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("message", new Gson().toJson(entity)).build();
        RecipeUser recipeUser = ofy().load().type(RecipeUser.class).filterKey(user.getEmail()).first().now();

        List<Registration> records = ofy().load().type(Registration.class).ancestor(recipeUser).list();
        List<Registration> records1 = ofy().load().type(Registration.class).list();
        try {
            for (Registration record : records) {
                Result res = sender.send(msg, record.getRegId(), 5);
                if (res.getCanonicalRegistrationId() == null)
                    ofy().delete().entity(record).now();
                else if (!record.getRegId().equals(res.getCanonicalRegistrationId())) {
                    record.setRegId(res.getCanonicalRegistrationId());
                    ofy().save().entity(record).now();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
