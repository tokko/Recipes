package com.tokko.recipes.backend.registration;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.google.appengine.api.users.User;
import com.google.appengine.repackaged.com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import static com.tokko.recipes.backend.resourceaccess.OfyService.ofy;

public class MessageSender {
    private static final String API_KEY = "AIzaSyAcfYjzlHQaAuroVdB26hczjVkZ0PKqDNc";

    public static <T> void sendMessage(T entity, User user) {
        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("message", new Gson().toJson(entity)).build();
        List<Registration> records = ofy().load().type(Registration.class).list(); //filter("userId=", user.getEmail()).list(); //.filter("userId=", user.getUserEmail()).list();
        try {
            for (Registration record : records) {
                sender.send(msg, record.getRegId(), 5);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
