package com.tokko.recipes.utils;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

import java.util.List;

public class LoaderBindModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<Class<? extends AbstractLoader<List<RecipeWrapper>>>>() {
        }).toInstance(RecipeLoader.class);
    }
}
