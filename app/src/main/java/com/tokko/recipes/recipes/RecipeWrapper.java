package com.tokko.recipes.recipes;

import com.tokko.recipes.backend.recipeApi.model.Recipe;
import com.tokko.recipes.utils.AbstractWrapper;

public class RecipeWrapper extends AbstractWrapper<Recipe> {

    private Long id;
    private String title;
    private String description;

    public RecipeWrapper() {
        this(null);
    }
    public RecipeWrapper(Recipe recipe) {
        super(recipe);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    protected Class<?> getClazz() {
        return Recipe.class;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RecipeWrapper)) return false;
        RecipeWrapper other = (RecipeWrapper) o;
        return id.equals(other.getId());
    }
}
