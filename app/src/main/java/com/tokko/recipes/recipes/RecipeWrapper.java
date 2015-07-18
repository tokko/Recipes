package com.tokko.recipes.recipes;

import com.tokko.recipes.backend.recipeApi.model.Recipe;
import com.tokko.recipes.utils.AbstractWrapper;

public class RecipeWrapper extends AbstractWrapper<Recipe> {

    private Long id;
    private String title;
    private String description;

    public RecipeWrapper(long id) {
        this();
        this.id = id;
    }

    public RecipeWrapper(){
        this(null);
    }
    public RecipeWrapper(Recipe recipe) {
        super(recipe);
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof RecipeWrapper)) return false;
        RecipeWrapper other = (RecipeWrapper) o;
        return id.equals(other.getId());
    }

    @Override
    public String toString(){
        return getTitle();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    protected Class<?> getWrappedClass(){
        return Recipe.class;
    }

    @Override
    public Long getId(){
        return id;
    }

    @Override
    public void setId(Long id){
        if(this.id == null)
            this.id = id;
    }
}
