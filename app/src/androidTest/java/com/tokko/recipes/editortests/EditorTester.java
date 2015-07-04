package com.tokko.recipes.editortests;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.robotium.solo.Solo;
import com.tokko.recipes.R;
import com.tokko.recipes.abstractdetails.GenericDetailActivity;
import com.tokko.recipes.abstractdetails.ResourceResolver;
import com.tokko.recipes.recipes.RecipeWrapper;


import org.junit.Ignore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class EditorTester extends ActivityInstrumentationTestCase2<GenericDetailActivity> {
    private Solo solo;

    public EditorTester() {
        super(GenericDetailActivity.class);
    }

    protected abstract int getResource();
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent startIntent = new Intent(getInstrumentation().getContext(), GenericDetailActivity.class).putExtra("class", ResourceResolver.getResourceClass(getResource())).putExtra(ResourceResolver.RESOURCE_EXTRA, getResource()).putExtra("entity", new Gson().toJson(ResourceResolver.getResourceInstance(getResource())));
        setActivityIntent(startIntent);
        solo = new Solo(getInstrumentation(), getActivity());
        solo.waitForActivity(GenericDetailActivity.class);
    }

    @Override
    protected void tearDown() throws Exception {
        solo.setActivityOrientation(Solo.PORTRAIT);
        solo.waitForActivity(GenericDetailActivity.class);
        super.tearDown();
    }

    public void testDeviceRotation_EditTexts_SavesAndRestoresState() {
        ArrayList<View> views = solo.getViews();
        List<String> map = new ArrayList<>();
        for (View view : views) {
            map.add(populateEditTextWithRandomString(view));
        }
        assertTrue(map.size() > 0);
        solo.setActivityOrientation(Solo.LANDSCAPE);
        solo.waitForActivity(GenericDetailActivity.class);
        for (String s : map) {
            if (s == null) continue;
            assertTrue(solo.searchText(s));
        }
    }

    private String populateEditTextWithRandomString(View view) {
        if (view instanceof EditText) {
            String s = UUID.randomUUID().toString();
            solo.typeText((EditText) view, s);
            return s;
        }
        return null;
    }

    public void testClickOnCancel_DiscardsChanges() {
        ArrayList<View> views = solo.getViews();
        List<String> map = new ArrayList<>();
        for (View view : views) {
            map.add(populateEditTextWithRandomString(view));
        }
        solo.clickOnText(solo.getString(android.R.string.cancel));
        getInstrumentation().waitForIdleSync();
        for (String s : map) {
            if (s == null) continue;
            assertTrue(!solo.searchText(s));
        }
    }

    public void testOkButtonClicked_ButtonBarIsHidden() {
        solo.clickOnText("Cancel");
        assertTrue(assertButtonBarNotShown());
    }

    /*
    @Ignore //TODO: test this on edit, not in create
    public void testDeleteButtonClicked_ButtonBarIsHidden(){
        solo.clickOnText("Delete");
        assertTrue(assertButtonBarNotShown());
    }
*/
    public void testCancelButtonClicked_ButtonBarIsHidden() {
        solo.clickOnText("Cancel");
        assertTrue(assertButtonBarNotShown());
    }

    private boolean assertButtonBarNotShown() {
        return solo.getView(R.id.edit_buttonBar).getVisibility() != View.VISIBLE;
    }
}
