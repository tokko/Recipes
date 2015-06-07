package com.tokko.recipes.recipes;

import com.tokko.recipes.abstractlistdetailedits.AbstractDetailFragment;
import com.tokko.recipes.abstractlistdetailedits.AbstractListActivity;
import com.tokko.recipes.abstractlistdetailedits.AbstractListFragment;

public class RecipeListActivity extends AbstractListActivity {

    @Override
    public Class<? extends AbstractListFragment<?>> getAbstractListFragmentClass() {
        return RecipeListFragment.class;
    }

    @Override
    public Class<AbstractDetailFragment> getAbstractDetailFragmentClass() {
        return null;
    }
}
