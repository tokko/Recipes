package com.tokko.recipes.utils;

import android.content.Context;

import com.google.inject.Inject;
import com.tokko.recipes.backend.recipeApi.RecipeApi;
import com.tokko.recipes.backend.recipeApi.model.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeLoader extends AbstractLoader<List<RecipeWrapper>> {
    private final RecipeApi api;

    @Inject
    public RecipeLoader(Context context) {
        super(context);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wrappers;
    }
}
