package com.tokko.recipes.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.tokko.recipes.backend.registration.Registration;
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
            Registration reg = ApiFactory.createRegistrationApi();
            reg.register(token).execute();
            Toast.makeText(this, "Registration token: " + token, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
