package com.tokko.recipes.recipes;

import android.content.Context;

import com.google.inject.Inject;
import com.tokko.recipes.backend.recipeApi.RecipeApi;
import com.tokko.recipes.backend.recipeApi.model.Recipe;
import com.tokko.recipes.utils.AbstractLoader;
import com.tokko.recipes.utils.ApiFactory;

import java.util.ArrayList;
import java.util.List;

public class RecipeLoader extends AbstractLoader<RecipeWrapper> {
    private final RecipeApi api;

    @Inject
    public RecipeLoader(Context context) {
        super(context, RecipeWrapper.class);
        api = ApiFactory.createRecipeApi();
    }

    @Override
    public List<RecipeWrapper> loadInBackground() {
        List<RecipeWrapper> wrappers = new ArrayList<>();
        try {
            List<Recipe> list = api.list().execute().getItems();
            for (Recipe recipe : list) {
                wrappers.add(new RecipeWrapper(recipe));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wrappers;
    }
}
