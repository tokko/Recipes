package com.tokko.recipes.abstractdetails;

import com.tokko.recipes.genericlists.GenericListFragment;
import com.tokko.recipes.recipes.RecipeDetailFragment;
import com.tokko.recipes.utils.AbstractWrapper;
import com.tokko.recipes.recipes.RecipeLoader;
import com.tokko.recipes.recipes.RecipeWrapper;

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

    public static Class<? extends AbstractWrapper<?>> getResourceClass(int resource) {
        switch (resource) {
            case RESOURCE_RECIPES:
                return RecipeWrapper.class;
            default:
                return RecipeWrapper.class;
        }
    }

    public static <T extends AbstractWrapper<?>> AbstractDetailFragment getDetailFragment(T entity, int resource, boolean edit) {
        switch (resource) {
            case RESOURCE_RECIPES:
                return AbstractDetailFragment.newInstance(entity, RecipeDetailFragment.class, edit);
            default:
                return null;
        }
    }
}
