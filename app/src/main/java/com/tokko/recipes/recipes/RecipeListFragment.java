package com.tokko.recipes.recipes;

import android.content.Loader;
import android.os.Bundle;

import com.tokko.recipes.abstractlistdetailedits.AbstractListFragment;
import com.tokko.recipes.backend.recipeApi.model.Recipe;
import com.tokko.recipes.utils.RecipeLoader;

import java.util.List;

public class RecipeListFragment extends AbstractListFragment<Recipe> {
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new RecipeLoader(getActivity());
    }
}
