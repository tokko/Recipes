package com.tokko.recipes.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.tokko.recipes.backend.registration.registrationApi.model.Registration;
import com.tokko.recipes.utils.ApiFactory;

import java.io.IOException;

public class GcmRegistrationService extends IntentService {

    public GcmRegistrationService() {
        super(GcmRegistrationService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            String token = instanceID.getToken("826803278070",
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Registration reg1 = new Registration();
            reg1.setRegId(token);
            Registration reg = ApiFactory.createRegistrationApi().insert(reg1).execute();
            Toast.makeText(this, "Registration token: " + reg.getRegId() + " for user " + reg.getUserId(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
