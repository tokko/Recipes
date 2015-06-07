package com.tokko.recipes.utils;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.tokko.recipes.backend.recipeApi.model.Recipe;
import com.tokko.recipes.genericlists.GenericListFragment;
import com.tokko.recipes.recipes.RecipeListFragment;

public class LoaderBindModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<GenericListFragment<Recipe>>() {
        }).to(RecipeListFragment.class);
        //  bind(new TypeLiteral<Class<? extends AbstractLoader<List<Recipe>>>>() {}).toInstance(RecipeLoader.class);
    }
}
