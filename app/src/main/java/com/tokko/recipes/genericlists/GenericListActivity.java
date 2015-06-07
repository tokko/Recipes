package com.tokko.recipes.genericlists;

import android.content.Intent;
import android.os.Bundle;

import com.google.api.client.json.GenericJson;
import com.google.gson.Gson;
import com.tokko.recipes.R;
import com.tokko.recipes.abstractdetails.AbstractDetailActivity;
import com.tokko.recipes.abstractdetails.AbstractDetailFragment;
import com.tokko.recipes.abstractdetails.ResourceResolver;

import roboguice.activity.RoboActivity;


/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link AbstractDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link GenericListFragment} and the item details
 * (if present) is a {@link AbstractDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link GenericListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class GenericListActivity extends RoboActivity
        implements GenericListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private GenericListFragment listFragment;
    private int resource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            //  ((GenericListFragment) getFragmentManager()
            //         .findFragmentById(R.id.recipe_list))
        }
        if (savedInstanceState != null) {
            resource = savedInstanceState.getInt(ResourceResolver.RESOURCE_EXTRA);
        } else if (getIntent() != null && getIntent().getExtras() != null) {
            resource = getIntent().getIntExtra(ResourceResolver.RESOURCE_EXTRA, ResourceResolver.RESOURCE_RECIPES);
        }
        listFragment = ResourceResolver.getListFragment(resource);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //   getFragmentManager().beginTransaction().replace(R.id.recipe_list, GenericListFragment.newInstance(getAbstractListFragmentClass())).commit();
        getFragmentManager().beginTransaction().replace(R.id.recipe_list, listFragment).commit();
//        genericListFragment.setActivateOnItemClick(true);
    }

    /**
     * Callback method from {@link GenericListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(GenericJson entity) {
        if (mTwoPane) {
            AbstractDetailFragment fragment = ResourceResolver.getDetailFragment(entity, resource); // AbstractDetailFragment.newInstance(id, getAbstractDetailFragmentClass());
            getFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, AbstractDetailActivity.class);
            detailIntent.putExtra(AbstractDetailFragment.ARG_ITEM_ID, new Gson().toJson(entity));
            startActivity(detailIntent);
        }
    }

    // public abstract <T> Class<GenericListFragment<T>> getAbstractListFragmentClass();

    // public abstract Class<AbstractDetailFragment> getAbstractDetailFragmentClass();
}
