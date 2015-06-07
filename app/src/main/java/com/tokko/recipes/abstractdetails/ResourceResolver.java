package com.tokko.recipes.abstractdetails;

import com.google.api.client.json.GenericJson;
import com.tokko.recipes.genericlists.GenericListFragment;
import com.tokko.recipes.recipes.RecipeDetailFragment;
import com.tokko.recipes.utils.RecipeLoader;

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

    public static <T extends GenericJson> AbstractDetailFragment getDetailFragment(T entity, int resource) {
        switch (resource) {
            case RESOURCE_RECIPES:
                return AbstractDetailFragment.newInstance(entity, RecipeDetailFragment.class);
            default:
                return null;
        }
    }
}
