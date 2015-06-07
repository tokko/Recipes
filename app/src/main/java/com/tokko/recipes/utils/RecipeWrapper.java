package com.tokko.recipes.utils;

import com.tokko.recipes.backend.recipeApi.model.Recipe;

public class RecipeWrapper extends AbstractWrapper<Recipe> {

    private Long id;
    private String title;

    public RecipeWrapper(Recipe recipe) {
        super(recipe);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
