package com.tokko.recipes;

import android.app.Activity;
import android.content.Intent;

import com.google.gson.Gson;
import com.tokko.recipes.abstractdetails.GenericDetailActivity;
import com.tokko.recipes.abstractdetails.ResourceResolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19, manifest = "src/main/AndroidManifest.xml")
public class AbstractDetailFragmentTest {

    private Activity activity;

    @Before
    public void setup() {
        Intent startIntent = new Intent(RuntimeEnvironment.application.getApplicationContext(), GenericDetailActivity.class)
                .putExtra("class", ResourceResolver.getResourceClass(0)).putExtra(ResourceResolver.RESOURCE_EXTRA, 0)
                .putExtra("entity", new Gson().toJson(ResourceResolver.getResourceInstance(0)));
        activity = Robolectric.buildActivity(GenericDetailActivity.class).withIntent(startIntent).create().start().resume().get();
    }

    @Test
    public void dummy() {
    }
}
