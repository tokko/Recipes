package com.tokko.recipes.recipes;

import com.tokko.recipes.abstractlistdetailedits.AbstractDetailFragment;
import com.tokko.recipes.genericlists.GenericListActivity;
import com.tokko.recipes.genericlists.GenericListFragment;

public class RecipeListActivity extends GenericListActivity {

    @Override
    public Class<? extends GenericListFragment<?>> getAbstractListFragmentClass() {
        return RecipeListFragment.class;
    }

    @Override
    public Class<AbstractDetailFragment> getAbstractDetailFragmentClass() {
        return null;
    }
}
