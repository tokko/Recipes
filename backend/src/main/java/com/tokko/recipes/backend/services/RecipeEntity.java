package com.tokko.recipes.backend.services;

public interface RecipeEntity<TId, TAncestor> {

    TId getId();

    void setAncestor(TAncestor ancestor);
}
