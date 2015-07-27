package com.tokko.recipes.abstractdetails;

import com.tokko.recipes.backend.recipeApi.model.Recipe;
import com.tokko.recipes.genericlists.GenericListFragment;
import com.tokko.recipes.recipes.RecipeDetailFragment;
import com.tokko.recipes.recipes.RecipeLoader;

public class ResourceResolver {
    public static final String RESOURCE_EXTRA = "resource";

    public static final int RESOURCE_RECIPES = 0;

    public static GenericListFragment getListFragment(int resource) {
        switch (resource) {
            case RESOURCE_RECIPES:
                return GenericListFragment.newInstance(RecipeLoader.class);
            default:
                return null;
        }
    }

    public static <T> AbstractDetailFragment getDetailFragment(T entity, boolean edit) {
        if(entity instanceof Recipe)
                return AbstractDetailFragment.newInstance(entity, RecipeDetailFragment.class, edit);
        else return null;
    }
}
