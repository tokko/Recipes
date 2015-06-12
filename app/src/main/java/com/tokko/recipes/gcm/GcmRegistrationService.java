package com.tokko.recipes.gcm;

import android.app.IntentService;
import android.content.Intent;

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
            getSharedPreferences("RegistrationData", MODE_PRIVATE).edit().putString("regid", reg.getRegId()).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
