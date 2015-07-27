package com.tokko.recipes.recipes;

import android.content.Context;

import com.google.inject.Inject;
import com.tokko.recipes.backend.recipeApi.RecipeApi;
import com.tokko.recipes.backend.recipeApi.model.Recipe;
import com.tokko.recipes.utils.AbstractLoader;
import com.tokko.recipes.utils.ApiFactory;

import java.util.List;

public class RecipeLoader extends AbstractLoader<Recipe> {
    private final RecipeApi api;

    @Inject
    public RecipeLoader(Context context) {
        super(context, Recipe.class);
        api = ApiFactory.createRecipeApi();
    }

    @Override
    public List<Recipe> loadInBackground() {
        try {
            return api.list().execute().getItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
