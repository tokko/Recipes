package com.tokko.recipes.backend.registration;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.appengine.api.users.User;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.inject.Inject;
import com.tokko.recipes.backend.entities.Registration;
import com.tokko.recipes.backend.entities.RecipeUser;
import com.tokko.recipes.backend.resourceaccess.RegistrationRA;

import java.io.IOException;
import java.util.List;

import static com.tokko.recipes.backend.resourceaccess.OfyService.ofy;

public class MessageSender {
    public static final String API_KEY = "AIzaSyAcfYjzlHQaAuroVdB26hczjVkZ0PKqDNc";
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
        try {
            for (Registration record : records) {
                Result res = sender.send(msg, record.getRegId(), 5);
                if(res == null) continue;
                if (res.getCanonicalRegistrationId() == null)
                    registrationRA.deleteRegistration(record);
                else if (!record.getRegId().equals(res.getCanonicalRegistrationId())) {
                    record.setRegId(res.getCanonicalRegistrationId());
                    registrationRA.saveRegistration(record);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
