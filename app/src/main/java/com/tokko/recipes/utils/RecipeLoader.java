package com.tokko.recipes.utils;

import android.content.Context;

import com.tokko.recipes.backend.recipeApi.RecipeApi;
import com.tokko.recipes.backend.recipeApi.model.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeLoader extends AbstractLoader<List<Recipe>> {
    private final RecipeApi api;

    public RecipeLoader(Context context) {
        super(context);
        api = ApiFactory.createRecipeApi();
    }

    @Override
    public List<Recipe> loadInBackground() {
        try {
            return api.list().execute().getItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
