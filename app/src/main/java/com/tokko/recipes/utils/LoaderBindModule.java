package com.tokko.recipes.utils;


import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.tokko.recipes.backend.recipeApi.model.Recipe;

import java.util.List;

public class LoaderBindModule implements Module {


    @Override
    public void configure(Binder binder) {
        binder.bind(new TypeLiteral<AbstractLoader<List<Recipe>>>() {
        }).to(RecipeLoader.class);

    }
}
