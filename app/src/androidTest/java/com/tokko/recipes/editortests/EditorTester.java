package com.tokko.recipes.editortests;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.robotium.solo.Solo;
import com.tokko.recipes.abstractdetails.GenericDetailActivity;
import com.tokko.recipes.abstractdetails.ResourceResolver;
import com.tokko.recipes.recipes.RecipeWrapper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import java8.util.stream.Stream;

public class EditorTester extends ActivityInstrumentationTestCase2<GenericDetailActivity> {
    private Solo solo;

    public EditorTester() {
        super(GenericDetailActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent startIntent = new Intent(getInstrumentation().getContext(), GenericDetailActivity.class).putExtra("class", RecipeWrapper.class).putExtra(ResourceResolver.RESOURCE_EXTRA, 0).putExtra("entity", new Gson().toJson(new RecipeWrapper()));
        setActivityIntent(startIntent);
        solo = new Solo(getInstrumentation(), getActivity());
        solo.waitForActivity(GenericDetailActivity.class);
    }

    @Override
    protected void tearDown() throws Exception {
        solo.setActivityOrientation(Solo.PORTRAIT);
        super.tearDown();
    }

    public void testDeviceRotation_EditTexts_SavesAndRestoresState() {
        ArrayList<View> views = solo.getViews();
        List<String> map = new ArrayList<>();
        for (View view : views) {
            if (view instanceof EditText) {
                String s = UUID.randomUUID().toString();
                map.add(s);
                solo.typeText((EditText) view, s);
            }
        }
        assertTrue(map.size() > 0);
        solo.setActivityOrientation(Solo.LANDSCAPE);
        for (String s : map) {
            assertTrue(solo.searchText(s));
        }
    }
}
