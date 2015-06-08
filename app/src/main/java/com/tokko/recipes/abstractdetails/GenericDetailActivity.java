package com.tokko.recipes.abstractdetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.tokko.recipes.R;
import com.tokko.recipes.genericlists.GenericListActivity;
import com.tokko.recipes.utils.AbstractWrapper;

import roboguice.activity.RoboActivity;


/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link GenericListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link AbstractDetailFragment}.
 */
public class GenericDetailActivity extends RoboActivity {

    private AbstractDetailFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Class<?> clz = (Class<?>) getIntent().getSerializableExtra("class");
            AbstractWrapper<?> entity = (AbstractWrapper<?>) new Gson().fromJson(getIntent().getStringExtra("entity"), clz);
            int resource = getIntent().getIntExtra(ResourceResolver.RESOURCE_EXTRA, ResourceResolver.RESOURCE_RECIPES);
            AbstractDetailFragment fragment = ResourceResolver.getDetailFragment(entity, resource); //AbstractDetailFragment.newInstance(getIntent().getLongExtra(AbstractDetailFragment.ARG_ITEM_ID, -1), null);
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, GenericListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // public abstract Class<AbstractDetailFragment> getDetailsFragmentClass();
}
