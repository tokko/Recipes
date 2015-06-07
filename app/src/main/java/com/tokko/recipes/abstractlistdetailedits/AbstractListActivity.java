package com.tokko.recipes.abstractlistdetailedits;

import android.content.Intent;
import android.os.Bundle;

import com.tokko.recipes.R;

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
 * {@link AbstractListFragment} and the item details
 * (if present) is a {@link AbstractDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link AbstractListFragment.Callbacks} interface
 * to listen for item selections.
 */
public abstract class AbstractListActivity extends RoboActivity
        implements AbstractListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

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
            //  ((AbstractListFragment) getFragmentManager()
            //         .findFragmentById(R.id.recipe_list))
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    @Override
    protected void onStart() {
        super.onStart();
        getFragmentManager().beginTransaction().replace(R.id.recipe_list, AbstractListFragment.newInstance(getAbstractListFragmentClass())).commit();
//        genericListFragment.setActivateOnItemClick(true);
    }

    /**
     * Callback method from {@link AbstractListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(Long id) {
        if (mTwoPane) {
            AbstractDetailFragment fragment = AbstractDetailFragment.newInstance(id, getAbstractDetailFragmentClass());
            getFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, AbstractDetailActivity.class);
            detailIntent.putExtra(AbstractDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    public abstract <T> Class<AbstractListFragment<T>> getAbstractListFragmentClass();

    public abstract Class<AbstractDetailFragment> getAbstractDetailFragmentClass();
}
