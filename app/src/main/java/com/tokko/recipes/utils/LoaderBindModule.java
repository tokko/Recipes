package com.tokko.recipes.utils;

import android.widget.EditText;
import android.widget.TextView;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.tokko.recipes.backend.recipeApi.RecipeApi;
import com.tokko.recipes.backend.recipeApi.model.Recipe;
import com.tokko.recipes.recipes.RecipeLoader;
import com.tokko.recipes.recipes.RecipeWrapper;

public class LoaderBindModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<Class<? extends AbstractLoader<RecipeWrapper>>>() {
        }).toInstance(RecipeLoader.class);

        bind(RecipeApi.class).toProvider(ApiFactory::createRecipeApi);

        bind(new TypeLiteral<Class<? extends AbstractWrapper<Recipe>>>() {
        }).toInstance(RecipeWrapper.class);
        bind(new TypeLiteral<Class<? extends EditText>>() {
        }).toInstance(EditText.class);
        bind(new TypeLiteral<Class<? extends TextView>>() {
        }).toInstance(TextView.class);
    }
}
